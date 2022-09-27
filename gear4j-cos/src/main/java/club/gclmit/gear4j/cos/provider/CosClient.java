package club.gclmit.gear4j.cos.provider;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import club.gclmit.gear4j.cos.domain.FileInfo;

/**
 * 使用抽象工厂模式
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since jdk11
 */
public interface CosClient {

	/**
	 * 上传文件
	 *
	 * @param file 文件
	 * @return {@link FileInfo} 文件信息
	 */
    FileInfo upload(File file);

	/**
	 * 上传文件
	 *
	 * @param file 文件
	 * @return {@link FileInfo} 文件信息
	 */
    FileInfo upload(MultipartFile file);

	/**
     * 上传字节数组
     *
     * @param data 字节数组
     * @return {@link FileInfo} 文件信息
     */
    FileInfo upload(byte[] data);

	/**
     * 上传字符串
     *
     * @param content 字符串内容
     * @return {@link FileInfo} 文件信息
     */
    FileInfo upload(String content);

	/**
     * 上传文件使用默认配置
     *
     * @param inputStream InputStream
     * @param fileInfo 文件消息
     * @return {@link FileInfo}
     */
    FileInfo upload(InputStream inputStream, FileInfo fileInfo);

	/**
	 * 批量删除
	 *
	 * @param keys 文件keys
	 */
    void batchDelete(List<String> keys);

	/**
	 * 删除单个
	 *
	 * @param key 文件key
	 */
    void delete(String key);
}
