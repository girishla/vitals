package com.kfc.vitals.order;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.kfc.vitals.services.restaurantdelivery.Restaurant;
import com.kfc.vitals.services.restaurantdelivery.RestaurantDeliveryServiceInput;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderApiTests {

	private static final String baseUrl = "https://order.kfc.co.uk/";
	private static final String getRestaurantsByDeliveryPostcodeEndpoint = baseUrl + "getRestaurantsByDeliveryPostcode";

	@Test
	public void canInvokeGetRestaurantsByPostCodeApi() {

//		DeliveryService service=new DeliveryService(ServiceName.RESTAURANT_DELIVERY,"JSJDJS");
//		
//		
//		RestaurantDeliveryServiceChecker serviceChecker = new RestaurantDeliveryServiceChecker();
//		ServiceStatus status=serviceChecker.getServiceStatus();

		RestaurantDeliveryServiceInput requestBody = (new RestaurantDeliveryServiceInput())
				.withAddress("2 Waldemar Avenue, London, Greater London, SW6 5NA")
				.withCodeMarket("UK")
				.withCountryCode("GB")
				.withDate("2018-09-12T10:25:27.970Z")
				.withPostcode("SW6 5NA");

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<RestaurantDeliveryServiceInput> request = new HttpEntity<>(requestBody, requestHeaders);

		ResponseEntity<List<Restaurant>> response = restTemplate.exchange(
				getRestaurantsByDeliveryPostcodeEndpoint, HttpMethod.POST, request,
				new ParameterizedTypeReference<List<Restaurant>>() {
				});

		List<Restaurant> restaurants = response.getBody();
		log.info("Response is {}", restaurants);
		assertThat(restaurants.get(0)
				.getName(), is(notNullValue()));

	}

	@Test
	public void whenAddressDeliveryNotAvailableApiErrors() {

		RestaurantDeliveryServiceInput requestBody = (new RestaurantDeliveryServiceInput())
				.withAddress("18 Ferndale Road, Woking, Surrey, GU21 4AJ")
				.withCodeMarket("UK")
				.withCountryCode("GB")
				.withDate("2018-09-12T10:25:27.970Z")
				.withPostcode("GU21 4AJ");

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<RestaurantDeliveryServiceInput> request = new HttpEntity<>(requestBody, requestHeaders);

		try {
			ResponseEntity<List<Restaurant>> response = restTemplate.exchange(
					getRestaurantsByDeliveryPostcodeEndpoint, HttpMethod.POST, request,
					new ParameterizedTypeReference<List<Restaurant>>() {
					});

			List<Restaurant> restaurants = response.getBody();
			log.info("Response is {}", restaurants);
			assertThat(restaurants.get(0)
					.getName(), is(notNullValue()));

		} catch (HttpClientErrorException e) {
			log.info("@@@@@@@@@@@@ API Error was {}", e.getResponseBodyAsString());
		}

	}

}
