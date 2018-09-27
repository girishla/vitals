
package com.kfc.vitals.services.restaurantdelivery;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Restaurant implements Serializable {

	private final static int OpeningTime = 17;
	private final static int ClosingTime = 20;

	@JsonProperty("sfid")
	public String sfid;
	@JsonProperty("name")
	public String name;
	@JsonProperty("refid")
	public String refid;
	@JsonProperty("kfc_addressline3__pc")
	public String kfcAddressline3Pc;
	@JsonProperty("kfc_addressline1__pc")
	public String kfcAddressline1Pc;
	@JsonProperty("kfc_addressline2__pc")
	public String kfcAddressline2Pc;
	@JsonProperty("street")
	public String street;
	@JsonProperty("city")
	public String city;
	@JsonProperty("postalcode")
	public String postalcode;
	@JsonProperty("longitude")
	public Double longitude;
	@JsonProperty("latitude")
	public Double latitude;
	@JsonProperty("kfc_mondaydeliveryclosing__c")
	public String kfcMondaydeliveryclosingC;
	@JsonProperty("kfc_tuesdaydeliveryclosing__c")
	public String kfcTuesdaydeliveryclosingC;
	@JsonProperty("kfc_wednesdaydeliveryclosing__c")
	public String kfcWednesdaydeliveryclosingC;
	@JsonProperty("kfc_thursdaydeliveryclosing__c")
	public String kfcThursdaydeliveryclosingC;
	@JsonProperty("kfc_fridaydeliveryclosing__c")
	public String kfcFridaydeliveryclosingC;
	@JsonProperty("kfc_saturdaydeliveryclosing__c")
	public String kfcSaturdaydeliveryclosingC;
	@JsonProperty("kfc_sundaydeliveryclosing__c")
	public String kfcSundaydeliveryclosingC;
	@JsonProperty("kfc_mondaydeliveryopening__c")
	public String kfcMondaydeliveryopeningC;
	@JsonProperty("kfc_tuesdaydeliveryopening__c")
	public String kfcTuesdaydeliveryopeningC;
	@JsonProperty("kfc_wednesdaydeliveryopening__c")
	public String kfcWednesdaydeliveryopeningC;
	@JsonProperty("kfc_thursdaydeliveryopening__c")
	public String kfcThursdaydeliveryopeningC;
	@JsonProperty("kfc_fridaydeliveryopening__c")
	public String kfcFridaydeliveryopeningC;
	@JsonProperty("kfc_saturdaydeliveryopening__c")
	public String kfcSaturdaydeliveryopeningC;
	@JsonProperty("kfc_sundaydeliveryopening__c")
	public String kfcSundaydeliveryopeningC;
	@JsonProperty("fleetprovider")
	public String fleetprovider;
	@JsonProperty("judopayid")
	public String judopayid;
	@JsonProperty("deliveryHours")
	public String deliveryHours;
	@JsonProperty("closed")
	public Boolean closed;
	@JsonProperty("etaInSeconds")
	public Integer etaInSeconds;
	@JsonProperty("estimatedDelivery")
	public String estimatedDelivery;
	@JsonProperty("lastFleetCheckDate")
	public String lastFleetCheckDate;
	private final static long serialVersionUID = 3299894623227937177L;



public int getOpeningTime() {
	return this.OpeningTime;
}

public int getClosingTime() {
	return this.ClosingTime;
}
}