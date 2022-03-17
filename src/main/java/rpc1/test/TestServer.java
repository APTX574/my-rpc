package rpc1.test;

import rpc1.server.NettyServer;
import rpc1.server.SocketServer;
import rpc1.entityServer.HelloServer;
import rpc1.entityServer.HelloServerImpl;
import rpc1.entityServer.StudentServer;
import rpc1.entityServer.StudentServerImpl;
import rpc2.ServerRegistry;
import rpc2.ServerRegistryImpl;

public class TestServer {
    public static void main(String[] args) {
        ServerRegistry serverRegistry = new ServerRegistryImpl();
        NettyServer rpcServer = new NettyServer(serverRegistry);

        HelloServer helloServer = new HelloServerImpl();
        StudentServer studentServer=new StudentServerImpl();

        serverRegistry.registry(helloServer);
        serverRegistry.registry(studentServer);

        rpcServer.start(9091);
    }


}
