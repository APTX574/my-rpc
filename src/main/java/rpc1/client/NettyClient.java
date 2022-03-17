package rpc1.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.EmptyByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc1.entity.RpcRequest;
import rpc1.entity.RpcResponse;
import rpc2.handle.NettyClientHandle;
import rpc2.serialize.*;

public class NettyClient implements RpcClient {

    Logger logger = LoggerFactory.getLogger(NettyClient.class);
    private String host;
    private int port;

    private static final Bootstrap bootstrap;

    static {
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new DecodeHandle(new HessianSerializeImpl()))
                                .addLast(new EncodeHandle(new HessianSerializeImpl()))
                                .addLast(new NettyClientHandle());
                    }
                });

    }

    @Override
    public <T> RpcResponse sentRequest(RpcRequest request) {
        try {
            ChannelFuture connect = bootstrap.connect(host, port).sync();
            logger.info("客户端连接到服务器 {}:{}", host, port);
            Channel channel = connect.channel();
            if (channel != null) {
                channel.writeAndFlush(request).addListener(future -> {
                    if (future.isSuccess()) {
                        logger.info(String.format("客户端发送消息: %s", request.toString()));
                    } else {
                        logger.error("发送消息时有错误发生: ", future.cause());
                    }
                });

                channel.closeFuture().sync();
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("RpcResponse");
                return channel.attr(key).get();

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
}
