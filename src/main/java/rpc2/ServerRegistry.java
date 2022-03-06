package rpc2;

/**
 * 服务注册类
 *
 * @author aptx
 */
public interface ServerRegistry {
    /**
     * 注册服务，返回服务对象
     *
     * @param server 需要注册的服务
     * @param <T>    返回的服务对象
     */
    public <T> void registry(T server);

    /**
     * 通过服务名称获取到服务对象
     *
     * @param serverName
     * @return
     */
    Object getServer(String serverName);
}
