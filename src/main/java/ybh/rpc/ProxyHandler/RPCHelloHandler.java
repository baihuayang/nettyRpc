package ybh.rpc.ProxyHandler;

import io.netty.channel.ChannelFuture;
import sun.reflect.generics.tree.BaseType;
import ybh.rpc.dtos.BaseDto;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by yangbaihua on 2016/8/11.
 */
public class RPCHelloHandler implements InvocationHandler {
    private Object target;
    private ChannelFuture future;
    private String className;

    public RPCHelloHandler(Object target) {
        this.target = target;
    }

    public RPCHelloHandler(Object target, ChannelFuture future, String className) {
        this.target = target;
        this.future = future;
        this.className = className;

    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //todo send method invoke to server
        BaseDto baseDto = new BaseDto(this.className, method.getName(),Arrays.asList(args).toArray(new String[args.length]));
        String message = "proxy message from client";
        future.channel().writeAndFlush(baseDto);
        return null;
    }
}
