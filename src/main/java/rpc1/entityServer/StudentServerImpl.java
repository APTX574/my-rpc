package rpc1.entityServer;

import rpc1.entity.Student;

/**
 * @author aptx
 */
public class StudentServerImpl implements StudentServer {
    @Override
    public Student getStudent() {
        Student student = new Student();
        student.setId(12312312);
        student.setName("这是我的名字rpcada");
        return student;
    }
}
