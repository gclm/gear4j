package club.gclmit.chaos.http.test;

import club.gclmit.chaos.http.exception.ChaosCoreException;
import club.gclmit.chaos.http.pojo.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author: gclm
 * @date: 2020/5/2 3:38 下午
 * @version: V1.0
 * @since 1.8
 */
public class ObjectTest {



    public static void main(String[] args) {

        Test test = new Test();


        List<Field> fieldList = new ArrayList<>() ;
        Class tempClass = test.getClass();

        while (tempClass != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass(); //得到父类,然后赋给自己
        }

        for (Field field : fieldList) {
            field.setAccessible(true);
            try {
                Class<?> type = field.getType();
                Object val = field.get(test);
                if (ObjectUtils.isEmpty(val)) {
                    val = "";
                }
                System.out.println(type.isPrimitive());
                System.out.println("type: " + type + "\tname:" + field.getName() +"\tvalue:" + val);
            } catch (IllegalAccessException e) {
                throw new ChaosCoreException("通过反射拼接ToString异常",e);
            }
            field.setAccessible(false);
        }

        System.out.println("=========================");
        System.out.println(ObjectUtils.toString(test));
    }
}
