package rpc2.handle;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.netty.channel.*;
import io.netty.util.ReferenceCountUtil;
import rpc1.entity.RpcRequest;
import rpc1.entity.RpcResponse;
import rpc2.ServerRegistry;
import rpc2.ServerRegistryImpl;

public class NettyServerHandler extends SimpleChannelInboundHandler {
    Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    private  final ServerRegistry serverRegistry;
    private  final RequestHandle requestHandle;


    public NettyServerHandler(ServerRegistry serverRegistry, RequestHandle requestHandle) {
        this.serverRegistry = serverRegistry;
        this.requestHandle = requestHandle;
    }



    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("开始处理消息");
        try{
            if (!(msg instanceof RpcRequest)) {
                logger.error("错误的类型");
                throw new IllegalArgumentException("错误的类型");
            }
            RpcRequest request = (RpcRequest) msg;
            Object ret = requestHandle.handle(request, serverRegistry);
            RpcResponse<Object> success = RpcResponse.success(ret);
            ChannelFuture channelFuture = ctx.writeAndFlush(success);
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

}
