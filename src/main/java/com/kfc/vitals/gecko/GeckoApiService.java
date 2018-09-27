package com.kfc.vitals.gecko;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class GeckoApiService {

	@Autowired
	private GeckoProps props;

	@Getter
	@Setter
	@Autowired
	private RestTemplate restTemplate;
	private static final String VITALS_CURRENT_DATASET_SCHEMA_URI = "datasets/";
	private static final String VITALS_CURRENT_DATASET_URI = VITALS_CURRENT_DATASET_SCHEMA_URI + "vitals.current/data";
	private static final String VITALS_HISTORY_DATASET_URI = VITALS_CURRENT_DATASET_SCHEMA_URI + "vitals.history/data";

	public String createDataSet(String datasetName, String input) {

		HttpEntity<String> request = new HttpEntity<>(input, getHeaders());
		ResponseEntity<String> response = restTemplate.exchange(
				props.getUrl() + VITALS_CURRENT_DATASET_SCHEMA_URI + datasetName, HttpMethod.PUT, request, String.class);

		String resp = response.getBody();
		log.info(">>>>>>>>>>>> Create Dataset Response is {}", resp);
		return resp;
	}

	public boolean testConnectivity() {
		ResponseEntity<Void> response = restTemplate.exchange(props.getUrl(), HttpMethod.GET,
				new HttpEntity<Void>(getHeaders()), Void.class);
		return response.getStatusCode()
				.equals(HttpStatus.OK);

	}

	HttpHeaders getHeaders() {
		return new HttpHeaders() {
			private static final long serialVersionUID = -2253133341817169428L;
			{  
				set("Authorization", "Basic " + props.getKey());

			}
		};
	}

	public String sendCurrentStatus(GeckoCurrentData data) {

		HttpEntity<GeckoCurrentData> request = new HttpEntity<>(data, getHeaders());
		ResponseEntity<String> response = restTemplate.exchange(
				props.getUrl() + VITALS_CURRENT_DATASET_URI, HttpMethod.POST,
				request, String.class);

		String resp = response.getBody();
		log.info(">>>>>>>>>>>> Send Current request to Gecko is {}", request);
		log.info(">>>>>>>>>>>> Send Current Status Response is {}", resp);
		return resp;

	}
	
	
	public String sendHistoryStatus(GeckoHistoryData data) {

		HttpEntity<GeckoHistoryData> request = new HttpEntity<>(data, getHeaders());
		ResponseEntity<String> response = restTemplate.exchange(
				props.getUrl() + VITALS_HISTORY_DATASET_URI, HttpMethod.POST,
				request, String.class);

		String resp = response.getBody();
		log.info(">>>>>>>>>>>> Send Historical request to Gecko is {}", request);
		log.info(">>>>>>>>>>>> Send Historical Status Response is {}", request);
		return resp;

	}


}
