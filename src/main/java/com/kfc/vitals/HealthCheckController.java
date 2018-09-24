package com.kfc.vitals;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kfc.vitals.sf.SfNotificationListener;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Controller
@AllArgsConstructor
@Getter
public class HealthCheckController {

	private HealthCheckerRunner runner;
//	private SfNotificationListener listener;

	@RequestMapping("/check")
	@ResponseBody
	ResponseEntity<?> checkHealth() {
//		runner.addListener(listener);
		runner.healthCheckAsync();
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	
}