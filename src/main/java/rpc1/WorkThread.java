package rpc1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc1.entity.RpcRequest;
import rpc1.entity.RpcResponse;
import rpc2.RequestWork;
import rpc2.ServerRegistry;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class WorkThread implements Runnable {


    private final Logger logger = LoggerFactory.getLogger(WorkThread.class);
    private final Socket socket;
    private final ServerRegistry serverRegistry;


    @Override
    public void run() {
        try (ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {

            RpcRequest request = (RpcRequest) inputStream.readObject();
            Object server = serverRegistry.getServer(request.getInterfaceName());
            Object invoke = new RequestWork().handle(server, request);
            outputStream.writeObject(RpcResponse.success(invoke));
            outputStream.flush();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            logger.error("服务端调用时发生错误", e);
        }
    }

    public WorkThread(Socket socket, ServerRegistry serverRegistry) {
        this.socket = socket;
        this.serverRegistry = serverRegistry;
    }
}
