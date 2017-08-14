package com.chelun.soa.client;

import com.chelun.soa.*;
import com.chelun.soa.protocol.serializer.JsonSerializer;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

public class NettyClient {
    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);
    private static JsonSerializer serializer = new JsonSerializer();

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(16, 16, 600L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(65536));


    private volatile ConnectManage connectManage;

    private String ip;
    private int port;
    private String serivceNamespace = "";
    private String address;
    private volatile boolean active = true;

    private ConnectManage getConnectManage() {
        if (connectManage == null) {
            connectManage = new ConnectManage();
        }
        return connectManage;
    }


    public static void submit(Runnable task) {
        threadPoolExecutor.submit(task);
    }

    public static Map<String, NettyClient> instances = new HashMap<String, NettyClient>();

    public static NettyClient getInstance(String id) {
        if (!instances.containsKey(id)) {
            instances.put(id, new NettyClient(id));
        }

        return instances.get(id);
    }

    public Object call(String serviceName, Object... params) throws InterruptedException {
        Request<Object> request = new Request<Object>(this.getSerivceNamespace() + "\\" + serviceName, params);
        try {
            NettyHandler handler = this.getConnectManage().chooseHandler();
            if (!handler.getChannel().isOpen()) {
                this.getConnectManage().connectServerNode(handler.getChannel().remoteAddress());
                handler = this.getConnectManage().chooseHandler();
            }
            handler.sendRequest(request).get();
            return request.getResponse().getResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object callCustom(String serviceName, Object... params) throws InterruptedException {
        Request<Object> request = new Request<Object>(this.getSerivceNamespace() + "\\" + serviceName, params);
        try {
            NettyHandler handler = this.getConnectManage().chooseHandler();
            if (!handler.getChannel().isOpen()) {
                this.getConnectManage().connectServerNode(handler.getChannel().remoteAddress());
                handler = this.getConnectManage().chooseHandler();
            }
            handler.sendRequest(request).get();
            return request.getResponse().getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T callSimple(String serviceName, Object... params) throws InterruptedException {
        Request<T> request = new Request<T>(this.getSerivceNamespace() + "\\" + serviceName, params);
        try {
            NettyHandler handler = this.getConnectManage().chooseHandler();
            if (!handler.getChannel().isOpen()) {
                this.getConnectManage().connectServerNode(handler.getChannel().remoteAddress());
                handler = this.getConnectManage().chooseHandler();
            }
            handler.sendRequest(request).get();
            return request.getResponse().getResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param ip
     * @param port
     * @param
     */
    public NettyClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.address = ip + Constants.PORT_SEP + port;
    }

    public NettyClient(String serverName) {

        StringBuffer buffer = new StringBuffer();
        buffer.append("/data/config/service:");
        buffer.append(serverName);
        buffer.append(".conf");
        String configFile = buffer.toString();

        File file = new File(configFile);

        if (!file.exists()) {
            logger.error("service config file not exist!");
        } else {
            try {
                FileInputStream in = new FileInputStream(file);
                int size = in.available();
                byte[] buf = new byte[size];
                in.read(buf);
                in.close();

                try {
                    ServerConfig config = (ServerConfig) serializer.deserialize(buf, ServerConfig.class);

                    if (config != null) {
                        this.serivceNamespace = config.getNamespace();
                        Server server = config.getServer();

                        if (server != null) {
                            this.ip = server.getHost();
                            this.port = server.getPort();
                            this.address = ip + Constants.PORT_SEP + port;
                            this.connectManage = getConnectManage();

                            InetSocketAddress socketAddress = new InetSocketAddress(server.getHost(), server.getPort());
                            this.connectManage.connectServerNode(socketAddress);

                        } else {
                            logger.error("service config file is empty!");
                        }
                    } else {
                        logger.error("service config is wrong!");
                    }

                } catch (Exception e) {
                    logger.error("deserialize service config file failed!");
                }
            } catch (IOException e) {
                logger.error("read config file failed!");
            }
        }
    }


    public boolean isActive() {
        return this.active;
    }


    public void setActive(boolean active) {
        this.active = active;
    }

    public String getAddress() {
        return this.address;
    }

    public String getIp() {
        return this.ip;
    }


    public int getPort() {
        return this.port;
    }

    public String getSerivceNamespace() {
        return serivceNamespace;
    }

    public void setSerivceNamespace(String serivceNamespace) {
        this.serivceNamespace = serivceNamespace;
    }


    public boolean equals(Object obj) {
        if (obj instanceof NettyClient) {
            NettyClient client = (NettyClient) obj;
            return this.address.equals(client.getAddress());
        } else {
            return super.equals(obj);
        }
    }


    public int hashCode() {
        return address.hashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */

    public String toString() {
        return "NettyClient[ip: " + this.getIp() + ", port: " + this.getPort() + "]";
    }

    public static void main(String[] args) throws Exception {

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

        final Map tempCoachIdMap = new HashMap();
        tempCoachIdMap.put("coach_id", 10739);
        tempCoachIdMap.put("amount_point", 2);
        tempCoachIdMap.put("change_business", 3);
        tempCoachIdMap.put("change_desc", "11");


        for (int i = 1; i <= 30; i++) {
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                          ResponseData<LinkedHashMap> result = (ResponseData<LinkedHashMap>) NettyClient.getInstance("kaojiazhao").callCustom("Supercoach\\CoachPointRepo::add", tempCoachIdMap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }





    }
}

