package club.gclmit.chaos.core.io.file;

import club.gclmit.chaos.core.util.StringUtils;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * <p>
 * 资源工具类
 * </p>
 *
 * @author: gclm
 * @date: 2020/4/13 10:05 上午
 * @version: V1.0
 * @since 1.8
 */
public class ResourceUtils {

	private static String CLASSPATH_PRE = "classpath:";

	/**
	 * 获取ClassPath绝对路径
	 * @param path classpath路径
	 * @return 绝对路径
	 */
	public static String getAbsolutePath(String path) {
		return getDecodedPath(getResource(path));
	}

	/**
	 * 获得资源相对路径对应的URL
	 * 
	 * @param path 资源相对路径
	 * @return {@link URL}
	 */
	public static URL getResource(String path) {
		if (StringUtils.startWithIgnoreCase(path, CLASSPATH_PRE)) {
			path = path.substring(CLASSPATH_PRE.length());
		}
		return getClassLoader().getResource(path);
	}

	/**
	 * 获取ClassPath下的资源做为流
	 * 
	 * @param path 相对于ClassPath路径，可以以classpath:开头
	 * @return {@link InputStream}资源
	 */
	public static InputStream getResourceAsStream(String path) {
		if (StringUtils.startWithIgnoreCase(path, CLASSPATH_PRE)) {
			path = path.substring(CLASSPATH_PRE.length());
		}
		return getClassLoader().getResourceAsStream(path);
	}

	/**
	 * 获取{@link ClassLoader}<br>
	 * 获取顺序如下：<br>
	 * 
	 * <pre>
	 * 1、获取当前线程的ContextClassLoader
	 * 2、获取{@link ClassLoaderUtil}类对应的ClassLoader
	 * 3、获取系统ClassLoader（{@link ClassLoader#getSystemClassLoader()}）
	 * </pre>
	 * 
	 * @return 类加载器
	 */
	private static ClassLoader getClassLoader() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			classLoader = getClassLoader();
			if (null == classLoader) {
				classLoader = ClassLoader.getSystemClassLoader();
			}
		}
		return classLoader;
	}

	/**
	 * 从URL对象中获取不被编码的路径Path<br>
	 * 对于本地路径，URL对象的getPath方法对于包含中文或空格时会被编码，导致本读路径读取错误。<br>
	 * 此方法将URL转为URI后获取路径用于解决路径被编码的问题
	 * 
	 * @param url {@link URL}
	 * @return 路径
	 */
	private static String getDecodedPath(URL url) {
		if (null == url) {
			return null;
		}

		String path = null;
		try {
			// URL对象的getPath方法对于包含中文或空格的问题
			path = url.toURI().getPath();
		} catch (URISyntaxException e) {
			// ignore
		}
		return (null != path) ? path : url.getPath();
	}
}
