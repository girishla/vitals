package com.kfc.vitals.sf;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SfTimeoutException extends RuntimeException {
	
	/** Constant for serializing instances of this class. */
	private static final long serialVersionUID = 5624776387174310551L;

	/**
	 * Creates a new empty exception object.
	 */
	public SfTimeoutException() {
		super();
	}

	/**
	 * Creates a new exception object.
	 * 
	 * @param msg
	 *            The exception message
	 */
	public SfTimeoutException(String msg) {
		super(msg);
    	log.error(msg);

	}

	/**
	 * Creates a new exception object.
	 * 
	 * @param baseEx
	 *            The base exception
	 */
	public SfTimeoutException(Throwable baseEx) {
		super(baseEx);
	}

	/**
	 * Creates a new exception object.
	 * 
	 * @param msg
	 *            The exception message
	 * @param baseEx
	 *            The base exception
	 */
	public SfTimeoutException(String msg, Throwable baseEx) {
		super(msg, baseEx);
    	log.error(msg,baseEx);
	}

}
