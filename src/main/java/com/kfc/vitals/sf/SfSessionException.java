package com.kfc.vitals.sf;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SfSessionException extends RuntimeException {
	
	/** Constant for serializing instances of this class. */
	private static final long serialVersionUID = 5624776387174310551L;

	/**
	 * Creates a new empty exception object.
	 */
	public SfSessionException() {
		super();
	}

	/**
	 * Creates a new exception object.
	 * 
	 * @param msg
	 *            The exception message
	 */
	public SfSessionException(String msg) {
		super(msg);
    	log.error(msg);

	}

	/**
	 * Creates a new exception object.
	 * 
	 * @param baseEx
	 *            The base exception
	 */
	public SfSessionException(Throwable baseEx) {
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
	public SfSessionException(String msg, Throwable baseEx) {
		super(msg, baseEx);
    	log.error(msg,baseEx);
	}

}
