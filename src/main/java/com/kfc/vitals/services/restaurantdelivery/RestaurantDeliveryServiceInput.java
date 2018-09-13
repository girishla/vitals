package com.kfc.vitals.services.restaurantdelivery;

import java.io.Serializable;

import lombok.Data;

@Data
public class RestaurantDeliveryServiceInput implements Serializable {

	public String postcode;
	public String date;
	public String address;
	public String countryCode;
	public String codeMarket;
	private final static long serialVersionUID = -3543352043623788113L;

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