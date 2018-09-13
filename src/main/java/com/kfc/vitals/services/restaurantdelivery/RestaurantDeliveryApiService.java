package com.kfc.vitals.services.restaurantdelivery;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.kfc.vitals.ApiService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RestaurantDeliveryApiService implements ApiService<RestaurantDeliveryServiceInput,Restaurant>  {

	private static final String baseUrl = "https://order.kfc.co.uk/";
	private static final String getRestaurantsByDeliveryPostcodeEndpoint = baseUrl + "getRestaurantsByDeliveryPostcode";
	
	/* (non-Javadoc)
	 * @see com.kfc.vitals.ApiService#invokeApi(com.kfc.vitals.RestaurantDeliveryServiceInput)
	 */
	@Override
	public List<Restaurant> invokeApi(RestaurantDeliveryServiceInput input) {
	
		HttpEntity<RestaurantDeliveryServiceInput> request = new HttpEntity<>(input, getHttpHeader());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Restaurant>> response = restTemplate.exchange(getRestaurantsByDeliveryPostcodeEndpoint,
				HttpMethod.POST, request, new ParameterizedTypeReference<List<Restaurant>>() {
				});
	
		List<Restaurant> restaurants = response.getBody();
		log.info(">>>>>>>>>>>> Response is {}", restaurants);
		return restaurants;
	}


	private HttpHeaders getHttpHeader() {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		return requestHeaders;
	}

	
	
}
