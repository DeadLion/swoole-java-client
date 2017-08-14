package com.chelun.soa;

import java.util.List;
import java.util.Random;

public class ServerConfig {
	private String namespace = "KJZ";
	private List<Server> servers;
	
	public Server getServer(){
		if(servers != null){
			Random rand = new Random();
			return servers.get(rand.nextInt(servers.size()));
		}
		
		return null;
	}
	
	public String getNamespace() {
		return namespace;
	}


	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}


	public List<Server> getServers() {
		return servers;
	}


	public void setServers(List<Server> servers) {
		this.servers = servers;
	}
}
