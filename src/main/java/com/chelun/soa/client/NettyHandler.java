package com.chelun.soa.client;

import com.chelun.soa.ConnectManage;
import com.chelun.soa.RPCFuture;
import com.chelun.soa.Response;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;

import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chelun.soa.Request;
import com.chelun.soa.client.protocol.Message;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

@Sharable
public class NettyHandler extends SimpleChannelInboundHandler<Message> {
    private static Logger logger = LoggerFactory.getLogger(NettyHandler.class);

    private volatile Channel channel;
    private SocketAddress remotePeer;

    private ConcurrentHashMap<String, RPCFuture> pendingRPC = new ConcurrentHashMap<>();


    public Channel getChannel() {
        return channel;
    }

    public SocketAddress getRemotePeer() {
        return remotePeer;
    }

    public RPCFuture sendRequest(Request request) {
        RPCFuture rpcFuture = new RPCFuture(request);
        pendingRPC.put(String.valueOf(request.getMessage().getHeader().getSessionId()), rpcFuture);
        channel.writeAndFlush(request.getMessage());
        return rpcFuture;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.remotePeer = this.channel.remoteAddress();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Message msg) {
        String sessionId = String.valueOf(msg.getHeader().getSessionId());
        RPCFuture rpcFuture = pendingRPC.get(sessionId);
        if (rpcFuture != null) {
            pendingRPC.remove(sessionId);
            rpcFuture.done(msg);
        }
    }

    public void close() {
        channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error(cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }


}
