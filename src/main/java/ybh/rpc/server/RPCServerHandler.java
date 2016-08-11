package ybh.rpc.server;


import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import ybh.rpc.dtos.BaseDto;
import ybh.rpc.dtos.HelloDto;

import java.lang.reflect.Method;

public class RPCServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println("server : from client " + (String)msg);
        // todo return result to client, use reflect
        BaseDto baseDto = (BaseDto) msg;
        System.out.println(baseDto);
        Class clazz = Class.forName(baseDto.getClassName());
        //todo check
        Method method = clazz.getMethods()[0];
        Object res = method.invoke(clazz.newInstance(), baseDto.getArgs());
        ChannelFuture f = ctx.writeAndFlush(res);
        f.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client connected...");
    }

    //    ChannelFuture f = ctx.writeAndFlush(new UnixTime());
//        f.addListener(ChannelFutureListener.CLOSE);
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
