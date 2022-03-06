package rpc1.entityServer;

import rpc1.entity.HelloObject;

/**
 * 服务端
 *
 * @author aptx
 */
public interface HelloServer {
    String hello(HelloObject hello);
}
