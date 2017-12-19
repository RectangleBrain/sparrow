package cn.jh.datasync.client.netty;
import java.util.List;

import cn.jh.datasync.client.utils.ByteArrayUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;  
  
public class ByteDecoder extends ByteToMessageDecoder {  
	
    @Override  
    protected void decode(ChannelHandlerContext ctx, ByteBuf in,  
            List<Object> out) throws Exception {  
        	if(in.readableBytes()>4){
    			in.markReaderIndex();
    			//获取前4个字节
    			byte[] intBuf=new byte[4];
    			in.readBytes(intBuf);
    			
    			int length=ByteArrayUtil.bytesToInt(intBuf,0);
    			if(length<0){
    				System.out.println("error for recieved package with data length less than zero");
    				return;
    			}
    			
    			if(length>300000000){
    				System.out.println("error for recieved package with data length max than 3000");
    				return;
    			}
    			
    			if(in.readableBytes()<length){
    				in.resetReaderIndex();
    				return;
    			}
    			//获取length个字节
    			byte[] data=new byte[length];
    			in.readBytes(data);
    			
    			out.add(data);
        	}
        }  
} 