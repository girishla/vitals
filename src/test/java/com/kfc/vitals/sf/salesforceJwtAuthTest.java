package com.kfc.vitals.sf;

import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.heroku.sdk.EnvKeyStore;
import com.sforce.soap.enterprise.Connector;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.GetUserInfoResult;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j

public class salesforceJwtAuthTest {

	private static final String PRIVATE_KEY_ENV_VAR = "PRIVATE_KEY";
	private static final String CERT_VAR = "CERT";
	private static final String SF_CONSUMER_KEY_VAR = "SF_CONSUMER_KEY";
	private static final String ALIAS = "alias";
	private static final String SF_USER_VAR = "SF_USER";
	private static final String SF_ENV = "https://test.salesforce.com";
	private static final String GRANT_TYPE = "urn:ietf:params:oauth:grant-type:jwt-bearer";
	private static final String SF_API_VERSION = "43.0";

	static {

		System.setProperty(PRIVATE_KEY_ENV_VAR, readFileAsString("secure/cert/private.pem"));
		System.setProperty(CERT_VAR, readFileAsString("secure/cert/server.cert"));
		System.setProperty(SF_CONSUMER_KEY_VAR, readFileAsString("secure/cert/consumerkey.txt"));
		System.setProperty(SF_USER_VAR, readFileAsString("secure/cert/user.txt"));

	}

	@Test
	public void canGenerateJwt() throws Exception {

		String jwt = getJwtToken();
		log.info("JWT is {}", jwt);
		Assert.assertThat(jwt, notNullValue());

	}

	@Test
	public void canAuthenticateSalesforceWithJwt() throws UnrecoverableKeyException, CertificateException,
			NoSuchAlgorithmException, KeyStoreException, IOException {

		String jwt = getJwtToken();

		SalesforceAuthResponse resp = authenticate(jwt);

		String token = resp.getAccessToken();
		Assert.assertThat(token, notNullValue());

	}

	@Test
	public void canReadUserDetailsAfterAuth() throws UnrecoverableKeyException, CertificateException,
			NoSuchAlgorithmException, KeyStoreException, IOException, ConnectionException {
		String jwt = getJwtToken();
		SalesforceAuthResponse resp = authenticate(jwt);
		EnterpriseConnection conn = createEntConnection(resp.getAccessToken(), resp.getInstanceUrl());
		GetUserInfoResult userInfo = conn.getUserInfo();
		log.info("Logged in user is {}", userInfo.getUserFullName());
		Assert.assertThat(userInfo.getUserFullName(), notNullValue());

	}

	private SalesforceAuthResponse authenticate(String jwt) {
		SalesforceAuthApiService apiService = new SalesforceAuthApiService();
		List<SalesforceAuthResponse> authResp = apiService.invokeApi(SalesforceAuthRequest.builder()
				.grantType(GRANT_TYPE)
				.assertion(jwt)
				.clientId(getValueFromEnvOrProperty(SF_CONSUMER_KEY_VAR))
				.build());
		SalesforceAuthResponse resp = authResp.get(0);
		return resp;
	}

	private String getJwtToken() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException,
			UnrecoverableKeyException {
		String privateKeyPem = getValueFromEnvOrProperty(PRIVATE_KEY_ENV_VAR);
		String certPem = getValueFromEnvOrProperty(CERT_VAR);

		EnvKeyStore eks = EnvKeyStore.createFromPEMStrings(privateKeyPem, certPem, getRandomPassword());

		PrivateKey key = (PrivateKey) eks.keyStore()
				.getKey(ALIAS, eks.password()
						.toCharArray());

		String jwt = Jwts.builder() //
				.setIssuer(getValueFromEnvOrProperty(SF_CONSUMER_KEY_VAR))
				.setSubject(getValueFromEnvOrProperty(SF_USER_VAR))
				.setExpiration(Date.from(LocalDate.now()
						.plusDays(365)
						.atStartOfDay(ZoneId.systemDefault())
						.toInstant()))
				.setAudience(SF_ENV)
				.signWith(SignatureAlgorithm.RS256, key)
				.compact();
		return jwt;
	}

	private String getValueFromEnvOrProperty(String key) {
		return System.getenv(key) == null ? System.getProperty(key) : System.getenv(key);
	}

	private String getRandomPassword() {
		return new BigInteger(130, new SecureRandom()).toString(32);
	}

	private static String readFileAsString(String file) {
		try {
			return new String(Files.readAllBytes(Paths.get(file)));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to get string from file!" + e.toString());
		}
	}

	private EnterpriseConnection createEntConnection(String accessToken, String instanceUrl)
			throws ConnectionException {
		ConnectorConfig config = new ConnectorConfig();
		config.setSessionId(accessToken);
		config.setServiceEndpoint(instanceUrl + "/services/Soap/c/" + SF_API_VERSION);

		return Connector.newConnection(config);
	}

}
