package rpc1.entity;


import java.io.Serializable;

/**
 * 消息对象
 *
 * @author aptx
 */
public class HelloObject implements Serializable {
    private int id;
    private String message;

    @Override
    public String toString() {
        return "HelloObject{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }

    public HelloObject(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
