package com.kfc.vitals;

import java.util.List;

public interface Service {
	
	ServiceName getName();
	List<ServiceProvider<?>> getProviders();

}
