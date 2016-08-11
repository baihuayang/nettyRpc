package ybh.rpc.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import ybh.rpc.ProxyHandler.RPCHelloHandler;
import ybh.rpc.nettyEncode.RPCDecoder;
import ybh.rpc.nettyEncode.RPCEncoder;
import ybh.rpc.rpcImpl.HelloImpl;
import ybh.rpc.rpcInterface.HelloRpc;

import java.lang.reflect.Proxy;

/**
 * Created by yangbaihua on 2016/8/10.
 */
public class RPCClient {
    public static void main(String[] args) throws Exception {
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
//            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.option(ChannelOption.TCP_NODELAY, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
//                    ch.pipeline().addLast(new RPCClientHandler());
//                    ch.pipeline().addLast(new RPCEncoder(),new RPCClientHandler(),new RPCDecoder());
                    ChannelPipeline pipeline = ch.pipeline();
                    ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(this.getClass().getClassLoader())),new ObjectEncoder());
                    pipeline.addLast(new RPCClientHandler());
                }
            });

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync(); // (5)
            //todo send method invoke by server,use jdk proxy
            HelloRpc helloRpc = new HelloImpl();
            RPCHelloHandler rpcHelloHandler = new RPCHelloHandler(helloRpc, f, helloRpc.getClass().getName());
            HelloRpc proxy = (HelloRpc) Proxy.newProxyInstance(helloRpc.getClass().getClassLoader(),helloRpc.getClass().getInterfaces(),rpcHelloHandler);
            proxy.say("this is my rpc");
            // todo end
            // f.channel().writeAndFlush();
            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
