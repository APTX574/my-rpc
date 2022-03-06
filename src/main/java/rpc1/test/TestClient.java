package rpc1.test;

import rpc1.client.RpcClientProxy;
import rpc1.entity.HelloObject;
import rpc1.entity.Student;
import rpc1.entityServer.HelloServer;
import rpc1.entityServer.HelloServerImpl;
import rpc1.entityServer.StudentServer;
import rpc1.entityServer.StudentServerImpl;

public class TestClient {
    public static void main(String[] args) {
        RpcClientProxy proxy=new RpcClientProxy("127.0.0.1",9091);
        HelloServer helloServer = proxy.getProxy(HelloServerImpl.class);
        String s = helloServer.hello(new HelloObject(12, "你好rpc"));
        StudentServer studentServer=proxy.getProxy(StudentServerImpl.class);
        Student student = studentServer.getStudent();
        System.out.println(student);
        System.out.println(s);
    }
}