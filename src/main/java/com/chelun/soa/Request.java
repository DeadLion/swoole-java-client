package com.chelun.soa;

import com.chelun.soa.client.protocol.Header;
import com.chelun.soa.client.protocol.Message;
import com.chelun.soa.protocol.serializer.JsonSerializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.UUID;

public class Request<T>{
	private static JsonSerializer 	serializer = new JsonSerializer();
	private static Logger  				logger = LoggerFactory.getLogger(Request.class);
	
	private String 						serviceName;
	private Object[]                    parameters;
	private long                        createMillisTime;
	private Message						message;
	private Response<T> 				response;

	public Request() {}

    public Request(String serviceName, Object[] parameters) {
        this.serviceName = serviceName;
        this.parameters = parameters;
        this.response = new Response<T>();

        String content;
		try {
			content = new String(serializer.serialize(new RequestEntity(serviceName, parameters, null)));
			content=content.replace("\\\\u","\\u");
	        Header header = new Header(content.length(), 2, 0, UUID.randomUUID().hashCode());
	        this.message = new Message(header, content);
		} catch (Exception e) {
			logger.info("json serializer fail!");
		}
    }
    
    @SuppressWarnings("unchecked")
	public void parseResponse(String jsonStr){
    	try {
    		ObjectMapper objectMapper = new ObjectMapper();
    		JsonNode node = objectMapper.readTree(jsonStr);
    		this.response.setErrno(node.get("errno").asInt());
    		
    		ResponseData<T> data;
            Class<?> clz = getGenericClass();
            if(clz == null){
            	String json = node.get("data").toString();
            	ResponseData responseData = (ResponseData) serializer.deserialize(json.getBytes(), ResponseData.class);
            	data = (ResponseData<T>) responseData;
            }else{
            	data = (ResponseData<T>) serializer.deserialize(node.get("data").toString(), ResponseData.class, clz);
            }
            
    		this.response.setData(data);
    		
		} catch (Exception e) {
			logger.info("channelRead0 deserialize fail!", e);
		}
    }
    
    public Class<T> getGenericClass() {
        try {
            Type sooper = getClass().getGenericSuperclass();
            Type t = ((ParameterizedType) sooper).getActualTypeArguments()[0];
            Class<T> c = (Class<T>) t;
            return c;
        } catch (Exception e) {
            return null;
        }
    }

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
	
	public long getCreateMillisTime() {
		return createMillisTime;
	}

	public void setCreateMillisTime(long createMillisTime) {
		this.createMillisTime = createMillisTime;
	}

	public Response<T> getResponse() {
		return response;
	}

	public void setResponse(Response<T> response) {
		this.response = response;
	}

	/** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Request[").append("serviceName: ").append(serviceName).append(createMillisTime).append("]");
        return sb.toString();
    }
}
