package com.kfc.vitals.sf.auth;

import java.util.Collections;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.kfc.vitals.ApiService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SfAuthApiService implements ApiService<SfJwtAuthRequest, SfJwtAuthResponse> {

	private static final String baseUrl = "https://bmdev1-dev-ed.my.salesforce.com/";
	private static final String endpoint = baseUrl + "services/oauth2/token";

	@Override
	public List<SfJwtAuthResponse> invokeApi(SfJwtAuthRequest input) {

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(endpoint)
				.queryParam("grant_type", input.getGrantType())
				.queryParam("assertion", input.getAssertion())
				.queryParam("client_id", input.getClientId())
				.queryParam("client_secret", input.getClientSecret());

		HttpEntity<SfJwtAuthRequest> request = new HttpEntity<>(input, getHttpHeader());

		
		log.info("****** Posting auth to {}, ",uriBuilder.toUriString());
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<SfJwtAuthResponse> response = restTemplate.exchange(uriBuilder.toUriString(),
				HttpMethod.POST, request, new ParameterizedTypeReference<SfJwtAuthResponse>() {
				});

		List<SfJwtAuthResponse> auth = Collections.singletonList(response.getBody());
		log.info(">>>>>>>>>>>> Response is {}", auth);
		return auth;
	}

	private HttpHeaders getHttpHeader() {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.setCacheControl("no-cache");

		return requestHeaders;
	}

}
