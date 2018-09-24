package com.kfc.vitals.gecko;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeckoHistoryData implements Serializable {

	@Builder.Default
	public List<GeckoHistoryDatum> data = new ArrayList<>();
	
	private final static long serialVersionUID = 497249041538202803L;

}