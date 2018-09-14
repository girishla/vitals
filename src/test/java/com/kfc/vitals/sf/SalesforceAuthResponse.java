package com.kfc.vitals.sf;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Builder
@Data
public class SalesforceAuthResponse implements Serializable
{

@JsonProperty("access_token")
public String accessToken;
@JsonProperty("scope")
public String scope;
@JsonProperty("instance_url")
public String instanceUrl;
@JsonProperty("id")
public String id;
@JsonProperty("token_type")
public String tokenType;
private final static long serialVersionUID = 6011404918746933128L;

}