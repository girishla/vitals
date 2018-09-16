package com.kfc.vitals.sf;

import com.sforce.ws.ConnectionException;

public class SfConnectionException extends RuntimeException {

	private static final long serialVersionUID = -5560958267951721706L;

	public SfConnectionException(ConnectionException e) {
		super(e);
	}
	
	public SfConnectionException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}

}
