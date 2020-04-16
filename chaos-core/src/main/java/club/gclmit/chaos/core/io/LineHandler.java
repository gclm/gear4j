package club.gclmit.chaos.core.io;

/**
 * <p>
 * 行处理器
 * </p>
 *
 * @author: gclm
 * @date: 2020/4/15 5:01 下午
 * @version: V1.0
 * @since 1.8
 */
@FunctionalInterface
public interface LineHandler {
    /**
     * 处理一行数据，可以编辑后存入指定地方
     * @param line 行
     */
    void handle(String line);
}

