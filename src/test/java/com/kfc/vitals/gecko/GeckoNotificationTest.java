package com.kfc.vitals.gecko;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.kfc.vitals.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
public class GeckoNotificationTest {

	private static final String GECKO_CURRENTDATASET_JSON = "gecko/currentdataset.json";
	private static final String VITALS_CURRENT_DATASET_NAME = "vitals.current";

	@TestConfiguration
	static class GeckoApiServiceConfig {

		@Bean
		public GeckoProps props() {
			return GeckoProps.builder()
					.key("NjI2MjYxMTVmN2ExN2Q2YjFlZjVkOTA0Y2MwODNlMGI6")
					.url("https://api.geckoboard.com/")
					.build();
		}

		@Bean
		public RestTemplate restTemplate() {
			return new RestTemplate();
		}

		@Bean
		public GeckoApiService apiService(GeckoProps props, RestTemplate restTemplate) {
			return new GeckoApiService(props, restTemplate);
		}
	}

	@Autowired
	GeckoApiService apiService;

	@Test
	public void canConnectToGecko() throws Exception {
		Assert.assertTrue(apiService.testConnectivity());
	}

	@Test
	public void canCreateDataSet() throws Exception {
		Assert.assertNotNull(apiService.createDataSet(VITALS_CURRENT_DATASET_NAME,
				Utils.readFileAsString(GECKO_CURRENTDATASET_JSON)));
	}

	@Test
	public void canSendCurrentServiceStatus() throws Exception {

		GeckoCurrentData dataItems = GeckoCurrentData.builder()
				.build();

		dataItems.data.add(GeckoCurrentDatum.builder()
				.provider("Test Provider")
				.service("Test Service")
				.serviceproviderid("Test_sp_id")
				.status("Down")
				.availability(0)
				.build());

		apiService.sendCurrentStatus(dataItems);

	}

}
