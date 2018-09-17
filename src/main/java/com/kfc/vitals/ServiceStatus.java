package com.kfc.vitals;

public enum ServiceStatus {
	UP("Up"), DOWN("Down"), ERROR("Error"), UNKNOWN("Unknown"), WARN("Warn");

	private final String name;

	private ServiceStatus(String s) {
		name = s;
	}

	public boolean equalsName(String otherName) {
		return name.equals(otherName);
	}

	public String toString() {
		return this.name;
	}

}
