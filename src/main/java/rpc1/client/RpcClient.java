package rpc1.client;

import rpc1.entity.RpcRequest;
import rpc1.entity.RpcResponse;

public interface RpcClient {
    public  <T> RpcResponse<T> sentRequest(RpcRequest request, String host, int port);
}
