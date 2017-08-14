package com.chelun.soa;

import java.util.regex.Pattern;

public class Constants {
	public static final String  SERIALIZE_HESSIAN                       = "hessian";
    public static final String  SERIALIZE_JAVA                          = "java";
    public static final String  SERIALIZE_JSON                          = "json";

    public static final byte    SERILIZABLE_HESSIAN                     = 1;
    public static final byte    SERILIZABLE_JAVA                        = 2;
    public static final byte    SERILIZABLE_JSON                        = 9;

    public static final String  CALL_SYNC                               = "sync";
    public static final String  CALL_CALLBACK                           = "callback";
    public static final String  CALL_FUTURE                             = "future";
    public static final String  CALL_ONEWAY                             = "oneway";

    public static final int     CALLTYPE_REPLY                          = 1;
    public static final int     CALLTYPE_NOREPLY                        = 2;

    public static final byte    MESSAGE_TYPE_HEARTBEAT                  = 1;
    public static final byte    MESSAGE_TYPE_SERVICE                    = 2;
    public static final byte    MESSAGE_TYPE_EXCEPTION                  = 3;
    public static final byte    MESSAGE_TYPE_SERVICE_EXCEPTION          = 4;

    public static final String  CONFIG_CLUSTER_CLUSTER                  = "cluster";
    public static final String  CONFIG_CLUSTER_RETRY                    = "retry";
    public static final String  CONFIG_CLUSTER_TIMEOUT_RETRY            = "timeout-retry";

    public static final String  REQ_ATTACH_FLOW                         = "req-attach-flow";
    public static final String  REQ_ATTACH_WRITE_BUFF_LIMIT             = "req-attach-writebuff-limit";
    public static final String  REQ_ATTACH_SOURCE_IP                    = "req-source-ip";

    public static final String  REQ_CONTEXT_CREATE_TIME                 = "req-context-createtime";
    public static final String  REQ_CONTEXT_TIMEOUT                     = "req-context-timeout";
    public static final String  REQ_CONTEXT_MESSAGE                     = "req-context-message";
    public static final String  REQ_INTERN_CONTEXT_KEEPALIVE            = "_req-context-http-keepalive";

    public static final String  REQ_TRANSFER_CONTEXT_TRACKER            = "req-transfer-context-tracker";
    public static final String  RES_TRANSFER_CONTEXT_TRACKER            = "res-transfer-context-tracker";
    public static final String  RES_INTERN_CONTEXT_REWRITE_RES          = "_res-context-http-rewrite-resp";

    public static final String  THREAD_GROUP_NAME                       = "soa-thread-group";
    public static final String  THREAD_DEFAULT_PREFIX                   = "soa-";
    public static final String  THREAD_HEARTBEAT_TASK                   = "heartbeat-task-";
    public static final String  THREAD_RECONNECT_TASK                   = "reconnect-task-";
    public static final String  THREAD_CLIENT_NETTY_BOSS                = THREAD_DEFAULT_PREFIX + "netty-binary-boss-client-";
    public static final String  THREAD_CLIENT_NETTY_WORKER              = THREAD_DEFAULT_PREFIX + "netty-binary-worker-client-";
    public static final String  THREAD_CLIENT_REQ_TIMEOUT_CHECKER       = THREAD_DEFAULT_PREFIX + "request-timeoutchecker-client-";
    public static final String  THREAD_CLIENT_RES_HANDLER               = THREAD_DEFAULT_PREFIX + "response-handler-";
    public static final String  THREAD_CLIENT_SERVICE_CALLBACK_EXECUTOR = THREAD_DEFAULT_PREFIX + "service-callback-executor-";
    public static final String  THREAD_USELESS_CLIENT_CLOSER            = THREAD_DEFAULT_PREFIX + "useless-client-closer-";
    public static final String  THREAD_BARREL_EXPIRED_REQ_CHECKER       = THREAD_DEFAULT_PREFIX + "barrel-expired-request-checker-";

    public static final String  THREAD_SERVER_NETTY_BINARY_BOSS         = THREAD_DEFAULT_PREFIX + "netty-binary-boss-server-";
    public static final String  THREAD_SERVER_NETTY_BINARY_WORKER       = THREAD_DEFAULT_PREFIX + "netty-binary-worker-server-";
    public static final String  THREAD_SERVER_NETTY_HTTP_BOSS           = THREAD_DEFAULT_PREFIX + "netty-http-boss-server-";
    public static final String  THREAD_SERVER_NETTY_HTTP_WORKER         = THREAD_DEFAULT_PREFIX + "netty-http-worker-server-";
    public static final String  THREAD_SERVER_REQ_PROCESSOR             = THREAD_DEFAULT_PREFIX + "request-processor-";
    public static final String  THREAD_SERVER_REQ_TIMEOUT_CHECKER       = THREAD_DEFAULT_PREFIX + "request-timeoutchecker-server-";

    public static final String  THREAD_LISTENER_EXECUTOR                = THREAD_DEFAULT_PREFIX + "listener-executor-";

    public static final int     DEFAULT_FAILOVER_RETRY                  = 1;
    public static final boolean DEFAULT_FAILOVER_TIMEOUT_RETRY          = false;
    public static final int     DEFAULT_WRITE_BUFFER_HIGH_WATER         = 35 * 1024 * 1024;
    public static final int     DEFAULT_WRITE_BUFFER_LOW_WATER          = 25 * 1024 * 1024;
    public static final int     DEFAULT_IO_THREADS                      = Runtime.getRuntime().availableProcessors() + 1;

    public static final int     SERVER_BINARY_REQ_PROCESSOR_MINTHREAD   = 200;
    public static final int     SERVER_BINARY_REQ_PROCESSOR_MAXTHREAD   = 800;
    public static final int     SERVER_HTTP_REQ_PROCESSOR_MINTHREAD     = 150;
    public static final int     SERVER_HTTP_REQ_PROCESSOR_MAXTHREAD     = 600;

    public static final int     SOCKET_CONNECT_TIMEOUT                  = 1500;
    public static final boolean SOCKET_KEEP_ALIVE                       = true;
    public static final boolean SOCKET_TCP_NO_DELAY                     = true;
    public static final int     HTTP_MAX_CHUNK_TOTAL_SIZE               = 1024 * 1024;
    public static final String  HTTP_PARAM_SERVICE                      = "_service";
    public static final String  HTTP_PARAM_METHOD                       = "_method";
    public static final String  HTTP_PARAM_PARAM                        = "_param";

    public static final String  HEARTBEAT_SERVICE                       = "http://service.eclicks.com/soaService/heartbeatService";
    public static final String  HEARTBEAT_SERVICE_SHORT                 = "heartbeatService";
    public static final String  HEARTBEAT_METHOD                        = "heartbeat";
    public static final String  ECHO_METHOD                             = "$echo";
    public static final long    HEARTBEAT_INTERVAL                      = 3000;
    public static final long    HEARTBEAT_TIMEOUT                       = 5000;
    public static final long    HEARTBEAT_DEADVALVE                     = 5;                                                           
    public static final long    HEARTBEAT_HEALTHVALVE                   = 5;                                                          
    public static final boolean HEARTBEAT_AUTOPICKOFF                   = false;                                                       
    public static final long    RECONNECT_INTERVAL                      = 3000;

    public static final String  ERR_MSG_TIMEOUT                         = "remote service call timeout";

    //soa' transport protocol
    public static final byte    MESSAGE_HEADER_FIRST                    = -128;
    public static final byte    MESSAGE_HEADER_SECOND                   = -127;
    public static final byte[]  MESSAGE_HEADERS                         = new byte[] { MESSAGE_HEADER_FIRST, MESSAGE_HEADER_SECOND };
    public static final int     MESSAGE_EXPAND_REGION_LENGTH            = 8;
    public static final byte    MESSAGE_DEFAULT_EXPAND_BYTE             = -100;

    //soa' internal configuration
    public static final boolean IS_TIMEOUT_OPTIMIZE_ENABLED             = false;                                                       

    public static final String  SERVICE_TYPE_BINARY                     = "binary";
    public static final String  SERVICE_TYPE_HTTP                       = "http";
    public static final String  SERVICE_TYPE_HTTPS                      = "https";

    public static final String  COMP_CLIENT_MANAGER                     = "client_manager";
    public static final String  COMP_SERVICE_REPOS                      = "service_repository";

    public static final Pattern COMMA_SPLIT_PATTERN                     = Pattern.compile("\\s*[,]+\\s*");
    public static final String  TRANSFER_NULL                           = "NULL";
    public static final String  PORT_SEP                                = ":";
    public static final char    SPACE_CHAR                              = ' ';
    public static final String  UNKNOWN_IP                              = "0.0.0.0";

    public static String serializeName(byte serialize) {
        switch (serialize) {
            case SERILIZABLE_HESSIAN:
                return SERIALIZE_HESSIAN;
            case SERILIZABLE_JAVA:
                return SERIALIZE_JAVA;
            default:
                return String.valueOf(serialize);
        }
    }

    public static String messageType(byte messageType) {
        switch (messageType) {
            case MESSAGE_TYPE_HEARTBEAT:
                return "heartbeat";
            case MESSAGE_TYPE_SERVICE:
                return "service";
            case MESSAGE_TYPE_EXCEPTION:
                return "exception";
            case MESSAGE_TYPE_SERVICE_EXCEPTION:
                return "service-exception";
            default:
                return String.valueOf(messageType);
        }
    }
}
