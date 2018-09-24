package com.kfc.vitals.sf.auth;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.heroku.sdk.EnvKeyStore;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT Auth Implementation. We could also implement a username/pwd
 * implementation of the same interface but that would be less secure
 * 
 *
 */
@Slf4j
@Component
@PropertySource("file:secure/secure.properties")
public class SfJwtAuthenticator implements SfAuthenticator<SfJwtAuthResponse> {

	private static final String ALIAS = "alias";
	private static final String GRANT_TYPE = "urn:ietf:params:oauth:grant-type:jwt-bearer";

	private String jwt;

	
	private SfJwtProps jwtProps;
	
	public SfJwtAuthenticator(SfJwtProps jwtProps) {

		this.jwtProps=jwtProps;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kfc.vitals.sf.auth.SfAuthenticator#getToken()
	 */
	@Override
	public String getToken() {

		try {
			EnvKeyStore eks = EnvKeyStore.createFromPEMStrings(jwtProps.getPrivateKeyPem(), jwtProps.getCertPem(), getRandomPassword());

			PrivateKey key = (PrivateKey) eks.keyStore()
					.getKey(ALIAS, eks.password()
							.toCharArray());

			String jwt = Jwts.builder()
					.setIssuer(jwtProps.getConsumerKey())
					.setSubject(jwtProps.getSfUser())
					.setExpiration(Date.from(LocalDate.now()
							.plusDays(365)
							.atStartOfDay(ZoneId.systemDefault())
							.toInstant()))
					.setAudience(jwtProps.getAuthUrl())
					.signWith(SignatureAlgorithm.RS256, key)
					.compact();
			return jwt;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new SfAuthException("Unexpected Error trying to get a JWT.", e);
		}
	}

	private String getRandomPassword() {
		return new BigInteger(130, new SecureRandom()).toString(32);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kfc.vitals.sf.auth.SfAuthenticator#authenticate()
	 */
	@Override
	public SfJwtAuthResponse authenticate() {

		if (jwt == null || jwt.isEmpty()) {
			jwt = this.getToken();
		}

		SfAuthApiService apiService = new SfAuthApiService();
		List<SfJwtAuthResponse> authResp = apiService.invokeApi(SfJwtAuthRequest.builder()
				.grantType(GRANT_TYPE)
				.assertion(jwt)
				.clientId(jwtProps.getConsumerKey())
				.build());
		SfJwtAuthResponse resp = authResp.get(0);
		return resp;
	}

}
