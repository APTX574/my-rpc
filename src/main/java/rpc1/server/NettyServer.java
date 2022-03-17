package rpc1.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc2.ServerRegistry;
import rpc2.handle.NettyServerHandler;
import rpc2.handle.RequestHandle;
import rpc2.serialize.ByteArrayToBinaryEncoder;
import rpc2.serialize.DecodeHandle;
import rpc2.serialize.EncodeHandle;
import rpc2.serialize.HessianSerializeImpl;

import java.util.concurrent.ExecutorService;

public class NettyServer implements RpcServer {


    private final Logger logger = LoggerFactory.getLogger(SocketServer.class);

    private final ServerRegistry serverRegistry;


    @Override
    public void start(int port) {

        //先创建爱NioEventLoopGroup里面是NioEventLoop组 可以传入参数选择创建的组数
        //bossGroup 为分发组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //workerGroup为处理的组
        EventLoopGroup workerGroup = new NioEventLoopGroup();


        try {
            // 配置启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //添x加启动组
            serverBootstrap.group(bossGroup, workerGroup)
                    //设置服务类
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_BACKLOG, 256)
//                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    //配置初始化
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            HessianSerializeImpl hessianSerialize = new HessianSerializeImpl();
                            //添加编码器
                            pipeline.addLast(new EncodeHandle(hessianSerialize));
                            //添加解码器
                            pipeline.addLast(new DecodeHandle(hessianSerialize));
                            pipeline.addLast(new ByteArrayToBinaryEncoder());
                            //添加处理器
                            pipeline.addLast(new NettyServerHandler(serverRegistry,new RequestHandle()));
                        }
                    });
            ChannelFuture future = serverBootstrap.bind(port).sync();
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            logger.error("启动服务器时有错误发生: ", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
    public NettyServer(ServerRegistry s) {
        serverRegistry = s;
    }

}
