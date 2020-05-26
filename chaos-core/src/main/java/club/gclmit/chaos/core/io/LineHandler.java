package club.gclmit.chaos.core.io;

/**
 * <p>
 * 行处理器
 * </p>
 *
 * @author gclm
 */
@FunctionalInterface
public interface LineHandler {

    /**
     * 处理一行数据，可以编辑后存入指定地方
     * @param line 行
     */
    void handle(String line);
}

