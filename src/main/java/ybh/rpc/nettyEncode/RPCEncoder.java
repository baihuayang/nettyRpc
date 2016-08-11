package ybh.rpc.nettyEncode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class RPCEncoder extends ChannelHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        String m = (String) msg;
        ByteBuf encoded = ctx.alloc().buffer(4);
        encoded.writeBytes(m.getBytes());
        ctx.write(encoded, promise); // (1)
    }
}
