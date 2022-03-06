package rpc2;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 注册服务实现类
 *
 * @author aptx
 */
public class ServerRegistryImpl implements ServerRegistry {

    private final Logger logger = LoggerFactory.getLogger(ServerRegistryImpl.class);
    private final Map<String, Object> serverMap = new HashMap<>();
    private final Set<String> registeredServer = new ConcurrentSkipListSet<>();

    @Override
    public synchronized <T> void registry(T server) {
        Class<?> clazz = server.getClass();
        String className = clazz.getCanonicalName();
        if (registeredServer.contains(className)) {
            return;
        }
        Class<?>[] interfaces = clazz.getInterfaces();
        if (interfaces.length == 0) {
            logger.error("需要注册服务的对象没有接口:" + className);
            throw new RuntimeException("需要注册服务的对象没有接口");
        }

        for (Class<?> i : interfaces) {
            serverMap.put(i.getCanonicalName(), server);
        }
        registeredServer.add(className);
        logger.info("接口:{} 注册了服务{}", interfaces, className);
    }

    @Override
    public synchronized Object getServer(String serverName) {
        Object o = serverMap.get(serverName);
        if (o == null) {
            logger.error("该服务未注册：" + serverName);
            throw new RuntimeException("该服务未注册：" + serverName);
        }
        return o;
    }
}
