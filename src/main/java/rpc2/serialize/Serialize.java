package rpc2.serialize;

public interface Serialize {

    byte[] serialize(Object o);
    Object deserialize(byte[] msg);
    int getCode();
}
