package ybh.rpc.nettyEncode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.serialization.ObjectDecoder;

import java.util.List;

/**
 * Created by yangbaihua on 2016/8/9.
 */
public class RPCDecoder extends ByteToMessageDecoder { // (1)
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { // (2)
        if (in.readableBytes() < 4) {
            return; // (3)
        }

//        out.add(in.readBytes(4)); // (4)
        out.add(new Object());
    }
}