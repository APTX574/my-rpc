package rpc1.client;

import rpc1.entity.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理对象
 * @author aptx
 */
public class RpcClientProxy implements InvocationHandler {
    private final String host;
    private final int port;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest()
                .setParams(args)
                .setMethodName(method.getName())
                .setParamsTypes(method.getParameterTypes())
                .setInterfaceName(method.getDeclaringClass().getName());

        SocketClient rpcClient = new SocketClient();
        return rpcClient.sentRequest(request, host, port).getData();


    }

    public RpcClientProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }
}
