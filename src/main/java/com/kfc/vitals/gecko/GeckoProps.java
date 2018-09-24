package com.kfc.vitals.gecko;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Configuration
@ConfigurationProperties(prefix = "kfc.vitals.gecko")
@AllArgsConstructor
@NoArgsConstructor
@Builder
/**
 * Gecko board config props
 * @author Girish Lakshmanan
 *
 */
public class GeckoProps {

	private String key;
	private String url;
	
}
