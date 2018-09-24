package com.kfc.vitals.sf.auth;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Configuration
@ConfigurationProperties(prefix = "kfc.vitals.jwt")
@AllArgsConstructor
@NoArgsConstructor
@ConditionalOnProperty("vitals.sf.enabled")
public class SfJwtProps {

	private String authUrl;
	private String privateKeyPem;
	private String certPem;
	private String consumerKey;
	private String sfUser;
	
}
