package com.chelun.soa.client.protocol;

public class Message {
	private Header header;
    private String content;

    public Message(Header header, String content) {
        this.header = header;
        this.content = content;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return String.format("[bodylength=%d,type=%d,userid=%s,sessionId=%s,content=%s]",
            header.getBodyLength(),
            header.getSerializeType(),
            header.getUserId(),
            header.getSessionId(),
            content);
    }
}
