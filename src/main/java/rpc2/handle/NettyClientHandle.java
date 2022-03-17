package rpc2.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;
import rpc1.entity.RpcResponse;

public class NettyClientHandle extends SimpleChannelInboundHandler {
    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            AttributeKey<RpcResponse> key = AttributeKey.valueOf("RpcResponse");
            ctx.channel().attr(key).set((RpcResponse) msg);
            ctx.channel().close();
        } finally {
            ReferenceCountUtil.release(msg);
        }

    }
}
