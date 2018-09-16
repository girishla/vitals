package com.kfc.vitals;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kfc.vitals.services.restaurantdelivery.Restaurant;
import com.kfc.vitals.services.restaurantdelivery.RestaurantDeliveryServiceInput;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {

	public static String readFilePathAsString(String file) {
		try {
			return new String(Files.readAllBytes(Paths.get(file)));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to get string from file!" + e.toString());
		}
	}

	
	public static String getValueFromEnvOrProperty(String key) {
		return System.getenv(key) == null ? System.getProperty(key) : System.getenv(key);
	}
	
	
	public static String readFileAsString(String resourcePath) {

		try {
			return StreamUtils.copyToString(new ClassPathResource(resourcePath).getInputStream(),
					Charset.defaultCharset());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Error Reading file:" + resourcePath);
		}
	}

	public static <T> T readJsonObject(String jsonString) {

		final ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(jsonString, new TypeReference<T>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Error while converting String to Object: " + jsonString);
		}

	}

	public static <T> T readJsonObjectList(String jsonString) {

		final ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(jsonString, new TypeReference<T>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Error while converting String to Object: " + jsonString);
		}

	}

	public static List<RestaurantDeliveryServiceInput> readRestaurantDeliveryInput(String jsonString) {

		final ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(jsonString, new TypeReference<List<RestaurantDeliveryServiceInput>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Error while converting String to Object: " + jsonString);
		}

	}

	public static List<Restaurant> readRestaurants(String jsonString) {

		final ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(jsonString, new TypeReference<List<Restaurant>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Error while converting String to Object: " + jsonString);
		}

	}

}
