package com.kfc.vitals;

import java.util.List;

public interface ServiceProvider<T> {
		
	String getName();
	String getId();
	List<T> getProviderCheckInputList();

}
