package rpc1.entity;

import java.io.Serializable;

/**
 * rpc回应对象
 *
 * @author aptx
 */
public class RpcResponse<T> implements Serializable {

    private Integer statusCode;
    private String msg;
    private T data;

    public static <T> RpcResponse<T> success(T data) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setData(data);
        response.setMsg("响应成功");
        response.setStatusCode(ResponseCode.SUCCESS);
        return response;
    }

    public static <T> RpcResponse<T> fail(ResponseCode responseCode){
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(responseCode.getCode());
        response.setMsg(responseCode.getMsg());
        return response;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
