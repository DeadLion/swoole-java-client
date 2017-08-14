package com.chelun.soa;

public class Response<T>{
	private int 					errno;
	private ResponseData<T>         data;
	
	public T getResult() {
		if(this.getErrno() == 0 && this.data != null){
			return this.data.getData();
		}else{
			return null;
		}
	}
	
	public int getErrno() {
		return errno;
	}
	public void setErrno(int errno) {
		this.errno = errno;
	}
	public ResponseData<T> getData() {
		return data;
	}
	public void setData(ResponseData<T> data) {
		this.data = data;
	}
}
