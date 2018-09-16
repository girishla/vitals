package com.kfc.vitals.sf.auth;

public class SfAuthException extends RuntimeException {

	private static final long serialVersionUID = 7799307946714447415L;

	public SfAuthException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}
}