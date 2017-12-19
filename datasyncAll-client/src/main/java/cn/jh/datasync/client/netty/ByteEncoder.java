package cn.jh.datasync.client.netty;

import cn.jh.datasync.client.utils.ByteArrayUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

public class ByteEncoder extends MessageToByteEncoder<String> {

	@Override
	protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
		byte[] msgBytes =encodeTransPrococol(msg);
		ByteBuf buffer = Unpooled.copiedBuffer(msgBytes);
		out.writeBytes(buffer);
	}
	
    /**
     * 将字符穿编码为 前4个字节为字符串字长，后接字符串字节
     * @param str
     * @return
     */
	public byte[] encodeTransPrococol(String str) {
		byte[] srcCon;
		try{
			srcCon = str.getBytes(CharsetUtil.UTF_8);
		}catch(Exception e){
			srcCon=new byte[1];
		}
		int length=srcCon.length;
		byte[] header=ByteArrayUtil.intToBytes(length);
		byte[] target=new byte[length+4];
		
		System.arraycopy(header, 0, target, 0, 4);
		System.arraycopy(srcCon, 0, target, 4, length);
		return target;
	}
	

}