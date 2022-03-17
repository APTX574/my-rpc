package rpc2.serialize;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lib.RpcConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc1.entity.RpcRequest;

public class EncodeHandle extends MessageToByteEncoder {

    Logger logger= LoggerFactory.getLogger(EncodeHandle.class);
    private final static int MAGIC = 0x114514BB;

    private final Serialize serialize;

    public EncodeHandle(Serialize s) {
        serialize = s;
    }

    /*
     * +---------------+---------------+-----------------+-------------+
     * |  Magic Number |  Package Type | Serializer Type | Data Length |
     * |    4 bytes    |    4 bytes    |     4 bytes     |   4 bytes   |
     * +---------------+---------------+-----------------+-------------+
     * |                          Data Bytes                           |
     * |                   Length: ${Data Length}                      |
     * +---------------------------------------------------------------+
     * */

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        logger.info("处理编码:{}",o.toString());
        byteBuf.writeInt(MAGIC);
        if (o instanceof RpcRequest) {
            byteBuf.writeInt(RpcConstant.PACKAGE_TYPE_REQUEST);
        } else {
            byteBuf.writeInt(RpcConstant.PACKAGE_TYPE_RESPONSE);
        }
        byteBuf.writeInt(serialize.getCode());
        byte[] serialize = this.serialize.serialize(o);
        byteBuf.writeInt(serialize.length);
        byteBuf.writeBytes(serialize);
    }

}
