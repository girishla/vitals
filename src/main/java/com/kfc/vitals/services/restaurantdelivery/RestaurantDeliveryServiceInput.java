package com.kfc.vitals.services.restaurantdelivery;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RestaurantDeliveryServiceInput implements Serializable
{

@JsonProperty("postcode")
public String postcode;
@JsonProperty("date")
public String date;
@JsonProperty("address")
public String address;
@JsonProperty("countryCode")
public String countryCode;
@JsonProperty("codeMarket")
public String codeMarket;
private final static long serialVersionUID = 2412386405922394470L;

public RestaurantDeliveryServiceInput withPostcode(String postcode) {
this.postcode = postcode;
return this;
}

	public RestaurantDeliveryServiceInput withDate(String date) {
		this.date = date;
return this;
}

public RestaurantDeliveryServiceInput withAddress(String address) {
this.address = address;
return this;
}

public RestaurantDeliveryServiceInput withCountryCode(String countryCode) {
this.countryCode = countryCode;
return this;
}

public RestaurantDeliveryServiceInput withCodeMarket(String codeMarket) {
this.codeMarket = codeMarket;
return this;
}

}