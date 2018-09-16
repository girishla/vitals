package com.kfc.vitals.sf.auth;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SfJwtAuthRequest implements Serializable {

	private String grantType;
	private String assertion;
	private String clientId;
	private String clientSecret;
	private final static long serialVersionUID = 6011404918746933128L;

}