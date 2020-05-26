package club.gclmit.chaos.core.io;

/**
 * <p>
 * Stream进度条
 * </p>
 *
 * @author gclm
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
