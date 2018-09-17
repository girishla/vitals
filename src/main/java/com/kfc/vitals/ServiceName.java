package com.kfc.vitals;

public enum ServiceName {

	RESTAURANT_DELIVERY("Restaurant Delivery");

	private final String name;

	private ServiceName(String s) {
		name = s;
	}

	public boolean equalsName(String otherName) {
		return name.equals(otherName);
	}

	public String toString() {
		return this.name;
	}

}
