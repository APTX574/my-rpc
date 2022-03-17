package rpc2.serialize;

public class HessianSerializeImpl implements Serialize{

    @Override
    public byte[] serialize(Object o) {
        return HessianUtil.serialize(o);
    }

    @Override
    public Object deserialize(byte[] msg) {
        return HessianUtil.deserialize(msg);
    }

    @Override
    public int getCode() {
        return 0;
    }
}
