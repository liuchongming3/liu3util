package org.liu3.copy;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @Author: liutianshuo
 * @Date: 2021/2/2
 */
public class CopyUtil {

    /**
     * 简单的dto属性非空判断,没打算有太多嵌套
     * 如果属性没有指定的校验函数(checker),那么默认进行非空判断,String还进行isBlank判断
     * 不太通用,现在只能针对具体的类型传入组织好的selecter,通过获取的Predicate<Object>对象运行具体校验逻辑
     * 这样说来,这个方法就是提供了一个获取PropertyDescriptor[]并循环的场所
     * @author liuchongming
     * @param obj 被校验的对象
     * @param selecter 传入一个PropertyDescriptor对象,返回对应的校验函数方法
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static boolean propertyCheck(Object obj, Function<PropertyDescriptor, Predicate<Object>> selecter) throws InvocationTargetException, IllegalAccessException {

        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(obj.getClass());

        for(PropertyDescriptor pd : pds) {
            String name = pd.getName();
            Class<?> cls = pd.getPropertyType();

            Object property = pd.getReadMethod().invoke(obj);

            Predicate<Object> checker = selecter.apply(pd);
            if(checker == null){
                checker = CopyUtil::propertyEmptyCheck;
            }

            boolean res = checker.test(property);

            if(!res){
                System.out.println("属性校验失败:"+obj.getClass().getName()+" "+name);
                return false;
            }
        }
        return true;
    }

    public static boolean propertyListCheck(List<?> list, Function<PropertyDescriptor, Predicate<Object>> selecter) throws InvocationTargetException, IllegalAccessException {

        if(list.isEmpty()){
            return false;
        }

        for(Object item : list){
            boolean res = propertyCheck(item, selecter);
            if(!res){
                return false;
            }
        }
        return true;
    }

    /** 判断-对象不为null,如果是字符串不为空字符串 */
    public static boolean propertyEmptyCheck(Object obj){
        if(obj == null){
            return false;
        }
        if (obj instanceof String) {
            String item = (String) obj;
            if (StringUtils.isBlank(item)) {
                return false;
            }
        }
        return true;
    }
}
