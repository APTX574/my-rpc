package rpc1.entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Rpc请求对象，包含请求的接口名，方法名，参数类型和参数，可以唯一确定一个方法
 *
 * @author aptx
 */
public class RpcRequest implements Serializable {

    private String interfaceName;
    private String methodName;
    private Object[] params;
    private Class<?>[] paramsTypes;

    @Override
    public String toString() {
        return "RpcRequest{" +
                "interfaceName='" + interfaceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", params=" + Arrays.toString(params) +
                ", paramsTypes=" + Arrays.toString(paramsTypes) +
                '}';
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public RpcRequest setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public RpcRequest setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public Object[] getParams() {
        return params;
    }

    public RpcRequest setParams(Object[] params) {
        this.params = params;
        return this;
    }

    public Class<?>[] getParamsTypes() {
        return paramsTypes;
    }

    public RpcRequest setParamsTypes(Class<?>[] paramsTypes) {
        this.paramsTypes = paramsTypes;
        return this;
    }
}
