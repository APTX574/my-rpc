package rpc1.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc1.entity.RpcRequest;
import rpc1.entity.RpcResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author aptx
 */
public class SocketClient implements RpcClient {

    private final Logger logger = LoggerFactory.getLogger(SocketClient.class);

    @Override
    @SuppressWarnings("unchecked")
    public <T> RpcResponse<T> sentRequest(RpcRequest request, String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream.writeObject(request);
            outputStream.flush();
            return (RpcResponse<T>) inputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            logger.error("发送调用请求时出错："+request.toString());
        }
        return null;
    }
}
