package rpc2.serialize;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HessianUtil {


    public static byte[] serialize(Object o) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Hessian2Output output = new Hessian2Output(baos);
        byte[] bytes;
        try {
            output.writeObject(o);
            output.flush();
            bytes = baos.toByteArray();
            output.close();
            baos.close();
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object deserialize(byte[] bytes) {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        Hessian2Input input = new Hessian2Input(bais);
        Object o;
        try {
            o = input.readObject();
            input.close();
            bais.close();
            return o;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
