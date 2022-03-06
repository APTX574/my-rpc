package rpc2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc1.entity.RpcRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 处理具体的通信操作的线程
 *
 * @author aptx
 */
public class RequestWork {

    Logger logger = LoggerFactory.getLogger(RequestWork.class);

    public Object handle(Object server, RpcRequest request) {
        try {
            Class<?> clazz = Class.forName(request.getInterfaceName());
            Method method = clazz.getMethod(request.getMethodName(),request.getParamsTypes());
            return method.invoke(server, request.getParams());
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            logger.error("执行操作错误："+request);
            e.printStackTrace();
        }
        return null;
    }
}