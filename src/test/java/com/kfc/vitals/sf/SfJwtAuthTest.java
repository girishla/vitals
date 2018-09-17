package com.kfc.vitals.sf;

import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.junit.Assert;
import org.junit.Test;

import com.kfc.vitals.sf.auth.SfJwtAuthResponse;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.GetUserInfoResult;
import com.sforce.ws.ConnectionException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SfJwtAuthTest extends AbstractSfTest {



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

/*	@Test
	public void canReadUserDetailsAfterAuth() throws UnrecoverableKeyException, CertificateException,
			NoSuchAlgorithmException, KeyStoreException, IOException, ConnectionException {
		SfJwtAuthResponse resp = auth.authenticate();
		EnterpriseConnection conn = createEntConnection(resp.getAccessToken(), resp.getInstanceUrl());
		GetUserInfoResult userInfo = conn.getUserInfo();
		log.info("Logged in user is {}", userInfo.getUserFullName());
		Assert.assertThat(userInfo.getUserFullName(), notNullValue());

	}
*/
}
