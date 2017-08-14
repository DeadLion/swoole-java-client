package com.chelun.soa.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chelun.soa.client.protocol.Header;
import com.chelun.soa.client.protocol.Message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NettyEncoder extends MessageToByteEncoder<Message> {
	private static final Logger         logger         = LoggerFactory.getLogger(NettyEncoder.class);

	@Override
	protected void encode(ChannelHandlerContext context, Message msg, ByteBuf out) throws Exception {
		logger.info("encode message:"+msg.toString());
		
        Header header = msg.getHeader();
        String content = msg.getContent();
//        logger.info("content="+content);
        out.writeInt(content.length());
        out.writeInt(header.getSerializeType());
        out.writeInt(header.getUserId());
        out.writeInt(header.getSessionId());

        out.writeBytes(content.getBytes());
	}


}
