package club.gclmit.chaos.web.filter.xss;

/**
 * 利用 ThreadLocal 缓存线程间的数据
 *
 * @author L.cm
 */
class XssHolder {
	private static final ThreadLocal<Boolean> TL = new ThreadLocal<>();

	public static boolean isEnabled() {
		return Boolean.TRUE.equals(TL.get());
	}

	public static void setEnable() {
		TL.set(Boolean.TRUE);
	}

	public static void remove() {
		TL.remove();
	}

}
