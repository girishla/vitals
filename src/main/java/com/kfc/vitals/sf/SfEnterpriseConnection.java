package com.kfc.vitals.sf;

import javax.xml.namespace.QName;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.kfc.vitals.sf.auth.SfJwtAuthenticator;
import com.kfc.vitals.sf.auth.SfSessionInfo;
import com.sforce.async.AsyncApiException;
import com.sforce.async.AsyncExceptionCode;
import com.sforce.soap.enterprise.Connector;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.GetServerTimestampResult;
import com.sforce.soap.enterprise.GetUserInfoResult;
import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.UpsertResult;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.soap.enterprise.sobject.ServiceStatusCurrent__c;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import com.sforce.ws.SessionRenewer;

import lombok.extern.slf4j.Slf4j;

/**
 * Provides a Enterprise connection to salesforce.com. Re-connects when
 * necessary if the connection is not alive.
 * 
 *
 */

@Slf4j
@Service
public class SfEnterpriseConnection implements InitializingBean {

	private EnterpriseConnection connection;
	private SfJwtAuthenticator jwtAuthService;
	private String apiVersion;

	public SfEnterpriseConnection(SfJwtAuthenticator jwtAuthService,
			@Value("${vitals.sf-api-version}") String apiVersion) {
		this.apiVersion=apiVersion;
		this.jwtAuthService = jwtAuthService;
	};

	/**
	 * Connects to SFDC. Auto assigns a service endpoint to be used by
	 * subsequent calls.
	 * 
	 * @return
	 * @throws ConnectionException
	 */
	public void connect() {

		ConnectorConfig config = getConfig();

		try {
			connection = Connector.newConnection(config);
		} catch (ConnectionException e) {
			throw new SfConnectionException(e);
		}

	}

	/**
	 * Returns the current connection if already connected. If not creates a new
	 * Connection.
	 * 
	 * @return
	 * @throws ConnectionException
	 */
	private EnterpriseConnection getConnection() {

		if (connection == null)
			this.connect();
		return connection;

	}

	private ConnectorConfig getConfig() {

		SfSessionInfo sessionInfo = jwtAuthService.authenticate();

		ConnectorConfig config = new ConnectorConfig();
		config.setSessionId(sessionInfo.getToken());
		config.setServiceEndpoint(sessionInfo.getInstanceUrl() + "/services/Soap/c/" + apiVersion);
		// config.setAuthEndpoint(authEndpoint);

		config.setCompression(true);
		config.setTraceMessage(false);
		config.setSessionRenewer(new ForceSessionRenewer());

		return config;
	}

	// Renew the Salesforce session on timeout
	public class ForceSessionRenewer implements SessionRenewer {
		@Override
		public SessionRenewalHeader renewSession(ConnectorConfig config) throws ConnectionException {
			log.info("Attempt to renew Salesforce session");
			try {
				connection = Connector.newConnection(getConfig());
			} catch (Exception e) {
				throw new SfConnectionException("Failed renewing session.", e);
			}

			SessionRenewalHeader header = new SessionRenewalHeader();
			header.name = new QName("urn:enterprise.soap.sforce.com", "SessionHeader");
			header.headerElement = connection.getSessionHeader();
			return header;
		}
	}

	/**
	 * Wraps calls to getUserInfo. Deals with Invalid Sessions and retries.
	 */
	@Retryable(value = { SfSessionException.class,
			SfTimeoutException.class }, maxAttempts = 3, backoff = @Backoff(delay = 10000))
	public GetUserInfoResult getUserInfo() {

		try {
			GetUserInfoResult userInfo = connection.getUserInfo();
			log.info("Logged in user is {}", userInfo.getUserFullName());

			return userInfo;
		} catch (ConnectionException e) {
			handleException(e);
			throw new SfConnectionException("getUserInfo call failed.", e);
		}
	}

	@Recover
	public void recover(SfSessionException exception) {
		this.connect();
	}

	private void handleException(Exception e) {

		log.error(e.getMessage());
		if (e instanceof AsyncApiException) {

			if (((AsyncApiException) e).getExceptionCode() == AsyncExceptionCode.InvalidSessionId) {
				throw new SfSessionException(e);
			}
			if (((AsyncApiException) e).getExceptionCode() == AsyncExceptionCode.Timeout) {
				throw new SfTimeoutException(e);
			}
		}

	}

	public GetServerTimestampResult getServerTimestamp() {

		try {
			return getConnection().getServerTimestamp();
		} catch (ConnectionException e) {
			throw new SfConnectionException(e);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.connect();
	}

	public SaveResult[] create(SObject[] sObjects) {
		try {
			return connection.create(sObjects);
		} catch (ConnectionException e) {
			handleException(e);
			throw new SfConnectionException("create call failed.", e);
		}
	}
	
	
	public UpsertResult[] upsert(String externIdFieldName,SObject[] objs) {
		try {
			return connection.upsert(externIdFieldName,objs);
		} catch (ConnectionException e) {
			e.printStackTrace();
			handleException(e);
			throw new SfConnectionException("upsert call failed.", e);
		}
	}

}
