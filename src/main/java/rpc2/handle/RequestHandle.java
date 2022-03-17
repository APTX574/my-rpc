package rpc2.handle;

import rpc1.entity.RpcRequest;
import rpc2.ServerRegistry;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RequestHandle {

    public Object handle(RpcRequest request, ServerRegistry serverRegistry) {

        try {
            String interfaceName = request.getInterfaceName();
            Object server = serverRegistry.getServer(interfaceName);
            String methodName = request.getMethodName();
            Class<?>[] paramsTypes = request.getParamsTypes();
            Method method = server.getClass().getMethod(methodName, paramsTypes);
            Object[] params = request.getParams();
            return method.invoke(server, params);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;

    }
}
