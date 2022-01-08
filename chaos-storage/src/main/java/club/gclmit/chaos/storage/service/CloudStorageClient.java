package club.gclmit.chaos.storage.service;

import club.gclmit.chaos.storage.pojo.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * 使用抽象工厂模式
 *
 * @author gclm
 * @since jdk11
 */
public interface CloudStorageClient {

	/**
	 * <p>
	 * 上传文件
	 * </p>
	 *
	 * @param file 文件
	 * @return {@link FileInfo} 文件信息
	 * @author 孤城落寞
	 */
	public FileInfo upload(File file);

	/**
	 * <p>
	 * 上传文件
	 * </p>
	 *
	 * @param file 文件
	 * @return {@link FileInfo} 文件信息
	 * @author 孤城落寞
	 */
	public FileInfo upload(MultipartFile file);


	/**
	 * <p>
	 * 上传字节数组
	 * </p>
	 *
	 * @param data     字节数组
	 * @param key      文件路径
	 * @param fileName 文件名
	 * @return {@link FileInfo} 文件信息
	 * @author 孤城落寞
	 */
	public FileInfo upload(byte[] data, String key, String fileName);

	/**
	 * 上传字符串
	 *
	 * @param content  字符串内容
	 * @param key      key
	 * @param fileName 文件名
	 * @return {@link FileInfo} 文件信息
	 * @author gclm
	 */
	public FileInfo upload(String content, String key, String fileName);

	/**
	 * 上传文件使用默认配置
	 *
	 * @param inputStream InputStream
	 * @param fileInfo    文件消息
	 * @return {@link FileInfo}
	 */
	public FileInfo upload(InputStream inputStream, FileInfo fileInfo);

	/**
	 * 批量删除
	 *
	 * @param keys 文件keys
	 */
	public void batchDelete(List<String> keys);

	/**
	 * 删除单个
	 *
	 * @param key 文件key
	 */
	public void delete(String key);
}
