package rpc1.entityServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc1.entity.HelloObject;

public class HelloServerImpl implements HelloServer {
    Logger logger = LoggerFactory.getLogger(HelloServerImpl.class);

    @Override
    public String hello(HelloObject hello) {
        logger.info("id：" + hello.getId() + "发出了消息" + hello.getMessage());
        return hello.toString();
    }
}
