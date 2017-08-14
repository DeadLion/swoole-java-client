package com.chelun.soa.client;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chelun.soa.client.protocol.Header;
import com.chelun.soa.client.protocol.Message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class NettyDecoder extends ByteToMessageDecoder {
	private static final Logger         logger         = LoggerFactory.getLogger(NettyDecoder.class);
	
	@Override
	protected void decode(ChannelHandlerContext context, ByteBuf in, List<Object> out) throws Exception {
		if (in.readableBytes() >= Header.HEADER_LEN) {

			in.markReaderIndex();

	        int contentLength = in.readInt();
	        int serializeType = in.readInt();
	        int userId = in.readInt();
	        int sessionId = in.readInt();


	        Header header = new Header(contentLength, serializeType, userId, sessionId);

			//如果 bytebuff 长度小于消息体长度，重置读下标继续读取
			if (in.readableBytes()< contentLength){
				in.resetReaderIndex();
				return;
			}

	        //按 contentLength 读取内容
	        byte[] content = in.readBytes(contentLength).array();

	        Message message = new Message(header, new String(content));

	        logger.info("decode message:{}",message.toString());
	        out.add(message);
		}
		
	}

}
