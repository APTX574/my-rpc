package rpc1.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc2.ServerRegistry;

import java.util.concurrent.ExecutorService;

public class NettyServer implements RpcServer {

    private final Logger logger = LoggerFactory.getLogger(SocketServer.class);
    private final ExecutorService threadPool;
    private final ServerRegistry serverRegistry;

    @Override
    public void start(int port) {

    }

    public NettyServer(ExecutorService threadPool, ServerRegistry serverRegistry) {
        this.threadPool = threadPool;
        this.serverRegistry = serverRegistry;
    }
}
