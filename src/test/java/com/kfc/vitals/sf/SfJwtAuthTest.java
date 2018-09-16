package com.kfc.vitals.sf;

import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kfc.vitals.Utils;
import com.kfc.vitals.sf.auth.SfAuthenticator;
import com.kfc.vitals.sf.auth.SfJwtAuthResponse;
import com.kfc.vitals.sf.auth.SfJwtAuthenticator;
import com.sforce.soap.enterprise.Connector;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.GetUserInfoResult;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j

// @RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(classes = SfJwtAuthTest.class)
// @TestPropertySource(locations="classpath:application.properties")
public class SfJwtAuthTest {

	private static final String PRIVATE_KEY_ENV_VAR = "PRIVATE_KEY";
	private static final String SF_CONSUMER_KEY_VAR = "SF_CONSUMER_KEY";
	private static final String SF_USER_VAR = "SF_USER";
	private static final String CERT_VAR = "CERT";
	private static final String SF_AUTH_URL = "https://test.salesforce.com";
	private static final String SF_API_VERSION = "43.0";
	private SfAuthenticator<SfJwtAuthResponse> auth;

	static {

		System.setProperty(PRIVATE_KEY_ENV_VAR, Utils.readFilePathAsString("secure/cert/private.pem"));
		System.setProperty(CERT_VAR, Utils.readFilePathAsString("secure/cert/server.cert"));
		System.setProperty(SF_CONSUMER_KEY_VAR, Utils.readFilePathAsString("secure/cert/consumerkey.txt"));
		System.setProperty(SF_USER_VAR, Utils.readFilePathAsString("secure/cert/user.txt"));

	}

	@Before
	public void setup() {

		auth = new SfJwtAuthenticator(SF_AUTH_URL, Utils.getValueFromEnvOrProperty(PRIVATE_KEY_ENV_VAR),
				Utils.getValueFromEnvOrProperty(CERT_VAR), Utils.getValueFromEnvOrProperty(SF_CONSUMER_KEY_VAR),
				Utils.getValueFromEnvOrProperty(SF_USER_VAR));
	}

	@Test
	public void canGenerateJwt() throws Exception {

		String jwt = auth.getToken();
		Assert.assertThat(jwt, notNullValue());

	}

	@Test
	public void canAuthenticateSalesforceWithJwt() throws UnrecoverableKeyException, CertificateException,
			NoSuchAlgorithmException, KeyStoreException, IOException {
		SfJwtAuthResponse resp = auth.authenticate();
		String token = resp.getAccessToken();
		Assert.assertThat(token, notNullValue());

	}

	@Test
	public void canReadUserDetailsAfterAuth() throws UnrecoverableKeyException, CertificateException,
			NoSuchAlgorithmException, KeyStoreException, IOException, ConnectionException {
		SfJwtAuthResponse resp = auth.authenticate();
		EnterpriseConnection conn = createEntConnection(resp.getAccessToken(), resp.getInstanceUrl());
		GetUserInfoResult userInfo = conn.getUserInfo();
		log.info("Logged in user is {}", userInfo.getUserFullName());
		Assert.assertThat(userInfo.getUserFullName(), notNullValue());

	}

	private EnterpriseConnection createEntConnection(String accessToken, String instanceUrl)
			throws ConnectionException {

		ConnectorConfig config = new ConnectorConfig();
		config.setSessionId(accessToken);
		config.setServiceEndpoint(instanceUrl + "/services/Soap/c/" + SF_API_VERSION);
		return Connector.newConnection(config);

	}

}
