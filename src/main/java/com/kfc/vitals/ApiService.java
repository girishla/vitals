package com.kfc.vitals;

import java.util.List;

public interface ApiService<T,R> {

	List<R> invokeApi(T input);

}