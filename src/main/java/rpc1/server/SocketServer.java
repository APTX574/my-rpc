package rpc1.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc1.WorkThread;
import rpc2.ServerRegistry;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author aptx
 */
public class SocketServer implements RpcServer {
    private final Logger logger = LoggerFactory.getLogger(SocketServer.class);
    private final ExecutorService threadPool;

    private final ServerRegistry serverRegistry;

    public SocketServer(ServerRegistry registry) {
        int corePoolSize = 5;
        int maxiMunPoolSize = 10;
        int keepAliveTime = 60;
        serverRegistry = registry;
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        threadPool = new ThreadPoolExecutor(corePoolSize, maxiMunPoolSize, keepAliveTime,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), threadFactory);
    }

    @Override
    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Socket socket = null;
            while ((socket = serverSocket.accept()) != null) {
                threadPool.execute(new WorkThread(socket, serverRegistry));
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}