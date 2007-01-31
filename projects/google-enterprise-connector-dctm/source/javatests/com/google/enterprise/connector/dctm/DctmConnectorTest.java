package com.google.enterprise.connector.dctm;

import com.google.enterprise.connector.spi.RepositoryException;

import junit.framework.TestCase;

public class DctmConnectorTest extends TestCase {

	/*
	 * Test method for 'com.google.enterprise.connector.dctm.DctmConnector.login()'
	 */
	public void testLogin() {
		try {
			DctmConnector connector = new DctmConnector();
			((DctmConnector) connector).setLogin(DmInitialize.DM_LOGIN_OK1);
			((DctmConnector) connector).setPassword(DmInitialize.DM_PWD_OK1);
			((DctmConnector) connector).setDocbase(DmInitialize.DM_DOCBASE);
			((DctmConnector) connector).setClientX(DmInitialize.DM_CLIENTX);
			((DctmConnector) connector).setQueryStringUnboundedDefault(DmInitialize.DM_QUERY_STRING_UNBOUNDED_DEFAULT);
			((DctmConnector) connector).setWebtopServerUrl(DmInitialize.DM_WEBTOP_SERVER_URL);
			((DctmConnector) connector).setQueryStringBoundedDefault(DmInitialize.DM_QUERY_STRING_BOUNDED_DEFAULT);
			((DctmConnector) connector).setAttributeName(DmInitialize.DM_ATTRIBUTE_NAME);
			((DctmConnector) connector).setQueryStringAuthoriseDefault(DmInitialize.DM_QUERY_STRING_AUTHORISE_DEFAULT);
			DctmSession dctmSes=(DctmSession)connector.login();
			assertNotNull(dctmSes);
			assertEquals(dctmSes.queryStringUnboundedDefault,DmInitialize.DM_QUERY_STRING_UNBOUNDED_DEFAULT);
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
