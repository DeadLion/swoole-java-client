package com.chelun.soa.client.protocol;

public class Header {
	private int bodyLength;
	private int serializeType = 2;
	private int userId = 0;
	private int sessionId = 0;
	
	public static final int HEADER_LEN = 16;
	
	public Header(int bodyLength) {
        this.bodyLength = bodyLength;
    }
	
	public Header(int bodyLength, int serializeType, int userId, int sessionId) {
        this.bodyLength 		= bodyLength;
        this.serializeType 		= serializeType;
        this.userId			 	= userId;
        this.sessionId 			= sessionId;
    }
	
	public int getBodyLength() {
		return bodyLength;
	}
	public void setBodyLength(int bodyLength) {
		this.bodyLength = bodyLength;
	}
	public int getSerializeType() {
		return serializeType;
	}
	public void setSerializeType(int serializeType) {
		this.serializeType = serializeType;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getSessionId() {
		return sessionId;
	}
	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	@Override
	public String toString() {
		return "Header{" +
				"bodyLength=" + bodyLength +
				", serializeType=" + serializeType +
				", userId=" + userId +
				", sessionId=" + sessionId +
				'}';
	}
}
