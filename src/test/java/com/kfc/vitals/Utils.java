package com.kfc.vitals;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

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
			return mapper.readValue(jsonString, new TypeReference<List<T>>(){});
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(
					"Error while converting String to Object: " + jsonString);
		}

	}

}
