package rpc2.serialize;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import lib.RpcConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc1.server.SocketServer;

import java.util.List;

public class DecodeHandle extends ReplayingDecoder {

    private final Logger logger = LoggerFactory.getLogger(SocketServer.class);
    private final static int MAGIC = 0x114514BB;
    private final Serialize serialize;

    public DecodeHandle(Serialize s) {
        serialize = s;
    }


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int magic = byteBuf.readInt();
        if (magic != MAGIC) {
            logger.error("未能识别的协议");
            throw new IllegalArgumentException("错误的协议");
        }
        int msgType = byteBuf.readInt();
        int serializeType = byteBuf.readInt();
        if (serializeType != serialize.getCode()) {
            logger.error("未能识别的编码协议");
            throw new IllegalArgumentException("错误的编码协议");
        }
        int length = byteBuf.readInt();
        byte[] bytes=new byte[length];
        byteBuf.readBytes(bytes);
        Object deserialize = serialize.deserialize(bytes);
        list.add(deserialize);
    }
}
