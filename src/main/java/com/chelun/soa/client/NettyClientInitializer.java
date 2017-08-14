package com.chelun.soa.client;

import com.chelun.soa.Request;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.socket.SocketChannel;

public class NettyClientInitializer extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel channel) throws Exception {

		ChannelPipeline pipeline = channel.pipeline();

        pipeline.addLast(new NettyEncoder());
        pipeline.addLast(new NettyDecoder());
        pipeline.addLast(new NettyHandler());
		
	}

}
