package com.kfc.vitals.sf;

import org.junit.Before;

import com.kfc.vitals.Utils;
import com.kfc.vitals.sf.auth.SfAuthenticator;
import com.kfc.vitals.sf.auth.SfJwtAuthResponse;
import com.kfc.vitals.sf.auth.SfJwtAuthenticator;
import com.kfc.vitals.sf.auth.SfJwtProps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractSfTest {

	protected static final String PRIVATE_KEY_ENV_VAR = "PRIVATE_KEY";
	protected static final String SF_CONSUMER_KEY_VAR = "SF_CONSUMER_KEY";
	protected static final String SF_USER_VAR = "SF_USER";
	protected static final String CERT_VAR = "CERT";
	private static final String SF_AUTH_URL = "https://test.salesforce.com";
	private static final String SF_API_VERSION = "42.0";
	protected SfAuthenticator<SfJwtAuthResponse> auth;
	protected SfEnterpriseConnection conn;

	static {

		System.setProperty(PRIVATE_KEY_ENV_VAR, Utils.readFilePathAsString("secure/cert/private.pem"));
		System.setProperty(CERT_VAR, Utils.readFilePathAsString("secure/cert/server.cert"));
		System.setProperty(SF_CONSUMER_KEY_VAR, Utils.readFilePathAsString("secure/cert/consumerkey.txt"));
		System.setProperty(SF_USER_VAR, Utils.readFilePathAsString("secure/cert/user.txt"));

	}

	@Before
	public void setup() {

		log.info("Setting up Connection....");
		auth = new SfJwtAuthenticator(new SfJwtProps(SF_AUTH_URL, Utils.getValueFromEnvOrProperty(PRIVATE_KEY_ENV_VAR),
				Utils.getValueFromEnvOrProperty(CERT_VAR), Utils.getValueFromEnvOrProperty(SF_CONSUMER_KEY_VAR),
				Utils.getValueFromEnvOrProperty(SF_USER_VAR)));
		conn = new SfEnterpriseConnection((SfJwtAuthenticator) auth, SF_API_VERSION);
		conn.connect();
	}
	

	/*
	 * protected EnterpriseConnection createEntConnection(String accessToken,
	 * String instanceUrl) throws ConnectionException {
	 * 
	 * ConnectorConfig config = new ConnectorConfig();
	 * config.setSessionId(accessToken); config.setServiceEndpoint(instanceUrl +
	 * "/services/Soap/c/" + SF_API_VERSION); return
	 * Connector.newConnection(config);
	 * 
	 * }
	 */

}
