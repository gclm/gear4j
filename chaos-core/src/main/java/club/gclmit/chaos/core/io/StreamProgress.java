package club.gclmit.chaos.core.io;

/**
 * <p>
 * Stream进度条
 * </p>
 *
 * @author: gclm
 * @date: 2020/4/15 4:57 下午
 * @version: V1.0
 * @since 1.8
 */
public interface StreamProgress {

    /**
     * 开始
     */
    void start();

    /**
     * 进行中
     * @param progressSize 已经进行的大小
     */
    void progress(long progressSize);

    /**
     * 结束
     */
    void finish();
}
