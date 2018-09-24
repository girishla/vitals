package com.kfc.vitals.sf.auth;

/**
 * Any mechanism to get a valid session token can be valid implementations of
 * this interface A ConnectorConfig instance can be created using a valid
 * session token - which then can be used to create specific types of connection
 * like SOAP or Bulk;
 * 
 *
 */
public interface SfAuthenticator<R extends SfSessionInfo> {

	String getToken();
	R authenticate();

}