package com.kfc.vitals.gecko;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.kfc.vitals.HealthCheckResult;
import com.kfc.vitals.HealthNotificationListener;
import com.kfc.vitals.ServiceStatus;

import lombok.AllArgsConstructor;

/**
 * Sends Current Service Status to Gecko
 * 
 * @author Girish Lakshmanan
 *
 */
@Component
@AllArgsConstructor
public class GeckoNotificationListener implements HealthNotificationListener<String> {

	private GeckoApiService apiService;

	@Override
	public void notify(HealthCheckResult result) {

		GeckoCurrentData dataItems = GeckoCurrentData.builder()
				.build();

		GeckoHistoryData dataItemsHistory = GeckoHistoryData.builder()
				.build();

		dataItems.data.add(GeckoCurrentDatum.builder()
				.provider(result.getProvider()
						.getName())
				.service(result.getName()
						.toString())
				.serviceproviderid(result.getProvider()
						.getId())
				.status(result.getStatus()
						.toString())
				.availability(result.getStatus()
						.equals(ServiceStatus.UP) ? 1 : 0)
				.build());

		dataItemsHistory.data.add(GeckoHistoryDatum.builder()
				.provider(result.getProvider()
						.getName())
				.service(result.getName()
						.toString())
				.serviceproviderid(result.getProvider()
						.getId())
				.status(result.getStatus()
						.toString())
				.availability(result.getStatus()
						.equals(ServiceStatus.UP) ? 1 : 0)
				.timestamp(ZonedDateTime.now( ZoneOffset.UTC ).format( DateTimeFormatter.ISO_INSTANT ))
				.build());

		apiService.sendCurrentStatus(dataItems);
		apiService.sendHistoryStatus(dataItemsHistory);

	}

	@Override
	public String getListenerState() {
		return "ok";
	}

}
