package com.kfc.vitals;

public interface ServiceProvider<T> {
		
	String getName();
	T getProviderInfo();

}
