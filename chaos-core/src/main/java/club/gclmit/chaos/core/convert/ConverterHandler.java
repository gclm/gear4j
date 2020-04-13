package club.gclmit.chaos.core.convert;

/**
 * 从F类型转到T类型
 */
public interface ConverterHandler<T> {

	/**
	 * 
	 * @param value
	 * @return
	 */
	public T convert(Object value);

}
