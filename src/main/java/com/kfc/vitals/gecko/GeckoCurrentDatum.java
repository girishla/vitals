package com.kfc.vitals.gecko;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeckoCurrentDatum implements Serializable {

	public String serviceproviderid;
	public String service;
	public String provider;
	public String status;
	public Integer availability;
	private final static long serialVersionUID = -1079511884138568935L;

}
