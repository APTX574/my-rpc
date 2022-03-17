package rpc2.serialize;


import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

import java.util.*;

public class ByteArrayToBinaryEncoder extends MessageToMessageEncoder<byte[]> {
    @Override
    protected void encode(ChannelHandlerContext ctx, byte[] msg, List<Object> out) throws Exception {
        out.add( new BinaryWebSocketFrame(Unpooled.wrappedBuffer(msg)));
    }
}