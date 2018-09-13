package com.kfc.vitals;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Controller
@AllArgsConstructor
public class HealthCheckController {

	@Getter
	private HealthCheckerRunner runner;

	@RequestMapping("/check")
	@ResponseBody
	String checkHealth() {
		HealthNotificationListener<String> listener = new HealthNotificationListener<String>() {

			public String returnValue="";

			@Override
			public void notify(HealthCheckResult result) {
				returnValue = returnValue + "<br />"+ result.toString();
			}

			@Override
			public String getListenerState() {
				return returnValue;
			}

		};
		runner.addListener(listener);
		runner.healthCheck();
		return listener.getListenerState();

	}

}