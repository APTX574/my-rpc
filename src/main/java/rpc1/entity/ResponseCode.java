package rpc1.entity;

import java.io.Serializable;

/**
 * 回应的回应码对象
 *
 * @author aptx
 */
public class ResponseCode implements Serializable {
    public static final int SUCCESS = 0;
    public static final int ERROR = 1;
    private String msg;
    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
