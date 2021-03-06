package com.chelun.soa;

public class ResponseData<T>{
	private int 		code;
	private String	 	msg;
	private T         	data;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Override
	public String toString() {
		return "ResponseData [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}
	
}
