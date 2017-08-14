package com.chelun.soa;

public class Server {
	private String host = "127.0.0.1";
	private int port = 8803;
	private int weight = 100;
	private String status = "online";
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Server [host=" + host + ", port=" + port + ", weight=" + weight + ", status=" + status + "]";
	}

}
