package com.chelun.soa;

import java.io.Serializable;
import java.util.Map;

public class RequestEntity implements Serializable {
	private static final long serialVersionUID = 7058453583629064334L;
	
	private String 							call;
	private Object[]                    	params;
	private Map<String, String> 			env;
	
    public RequestEntity(String call, Object[] params, Map<String, String> env) {
        this.call = call;
        this.params = params;
        this.env = env;
    }
	
	public String getCall() {
		return call;
	}

	public void setCall(String call) {
		this.call = call;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

	public Map<String, String> getEnv() {
		return env;
	}

	public void setEnv(Map<String, String> env) {
		this.env = env;
	}

	/** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Request[").append("call: ").append(call).append("]");
        return sb.toString();
    }
}
