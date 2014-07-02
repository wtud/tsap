package org.tribler.tsap.XMLRPC.tests;

import java.util.HashMap;
import java.util.Map;

import org.tribler.tsap.XMLRPC.XMLRPCConnection;
import org.tribler.tsap.XMLRPC.XMLRPCConnection.IConnectionListener;

public class StubbedXMLRPCConnection extends XMLRPCConnection{
	private Map<String, Object> returnValues;
	private Map<String, Integer> timesCalled;
	private static StubbedXMLRPCConnection mInstance = null;
	
	private StubbedXMLRPCConnection () {
	}
	
	public static StubbedXMLRPCConnection getInstance() {
		if (mInstance == null) {
			mInstance = new StubbedXMLRPCConnection();
		}
		return mInstance;
	}
	
	public void setupStub() {
		returnValues = new HashMap<String, Object>();
		timesCalled = new HashMap<String, Integer>();
	}
	public void clearReturnValues() {
		returnValues.clear();
		timesCalled.clear();
	}
	
	public void addReturnValue(String functionName, Object returnValue) {
		returnValues.put(functionName, returnValue);
		timesCalled.put(functionName, 0);
	}
	public int getTimesCalled(String functionName) {
		if (timesCalled.containsKey(functionName)) {
			return timesCalled.get(functionName);
		} else {
			return 0;
		}
	}
	
	@Override
	public Object call(String functionName, Object... params) {
		assert(returnValues.containsKey(functionName));
		timesCalled.put(functionName, timesCalled.get(functionName)+1);
		return returnValues.get(functionName);
	}
	
	public void addListener(IConnectionListener listener) { }
}