package org.liu3.function.my;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.*;

/**
 * 期望用在属性复制的场景里
 * @Author: liutianshuo
 * @Date: 2020/11/18
 */
public class CopyUtil {

    private static Logger logger = LoggerFactory.getLogger(CopyUtil.class);

    private CopyUtil(){

    }
    /**
     * 用在两个list泛型不同时
     */
    public static <R, U> List<U> copyList(List<R> sourceList, Supplier<U> s){
        if(sourceList == null){
            return new ArrayList<>(0);
        }
        List<U> targetList = new ArrayList<>(sourceList.size());
        sourceList.forEach(t -> {
            U u = s.get();
            BeanUtils.copyProperties(t, u);
            targetList.add(u);
        });
        return targetList;
    }

    /**
     * 传进一个源List,返回一个目标list,使用fun进行组织对象
     * 用BeanCopier完全可以替代这个方法
     */
    public static <S, T> List<T> copyList(List<S> sourceList, Function<S, T> fun){
        if(sourceList == null){
            return new ArrayList<>(0);
        }
        List<T> targetList = new ArrayList<>(sourceList.size());
        sourceList.forEach(t -> {
            T target = fun.apply(t);
            targetList.add(target);
        });
        return targetList;
    }

    /**
     * List复制,使用sourceList-S和参数master,通过一些操作,返回一个List-T
     * @param sourceList 基础数据
     * @param master 条件数据
     * @param fun 使用S和O获取一个T类型
     * @param <S> 原始数据类型
     * @param <M> 组织List<T>时的公共必要数据
     * @param <T> 返回值的类型
     * @return
     */
    public static <S, M, T> List<T> copyList(List<S> sourceList, M master, BiFunction<S, M, T> fun){
        if(sourceList == null){
            return new ArrayList<>(0);
        }
        List<T> targetList = new ArrayList<>(sourceList.size());
        sourceList.forEach(s -> {
            T t = fun.apply(s, master);
            targetList.add(t);
        });
        return targetList;
    }


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
    public static boolean propertyCheck(Object obj, Function<PropertyDescriptor, Predicate<? extends Object>> selecter) throws InvocationTargetException, IllegalAccessException {

        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(obj.getClass());

        //现在只判断传进来的是不是List
        if(obj instanceof List){
            return propertyListCheck((List)obj, selecter, null);
        }

        for(PropertyDescriptor pd : pds) {

            //数组中第一个值是肯定Class
            if(pd.getPropertyType() == Class.class) {
                continue;
            }

            String name = pd.getName();
            Object value = pd.getReadMethod().invoke(obj);

            //判断是否是集合来选择对应方法
            if(value instanceof List){
                boolean res = propertyListCheck((List)value, selecter, pd);
                if(!res){
                    logger.error("属性校验失败1:{},{}",obj.getClass().getName(),name);
                    return false;
                }
            }else{

                boolean res = propertySingleCheck(value, selecter, pd);

                if(!res){
                    logger.error("属性校验失败2:{},{}",obj.getClass().getName(),name);
                    return false;
                }
            }
        }
        return true;
    }

    /** 对单个对象进行判断 */
    public static boolean propertySingleCheck(Object value, Function<PropertyDescriptor, Predicate<? extends Object>> selecter, PropertyDescriptor pd){

        Predicate checker = selecter.apply(pd);
        if(checker == null){
            checker = CopyUtil::propertyEmptyCheck;
        }

        boolean res = checker.test(value);

        if(!res){
            logger.error("属性校验Single失败:{},{}",value.getClass().getName(), pd.getName());
        }
        return res;
    }

    /** 指定是个List集合 */
    public static boolean propertyListCheck(List<?> list, Function<PropertyDescriptor, Predicate<? extends Object>> selecter, PropertyDescriptor pd) throws InvocationTargetException, IllegalAccessException {

        if(list.isEmpty()){
            return false;
        }

        Predicate checker = null;

        if(pd != null && (checker = selecter.apply(pd)) != null){
            if(!checker.test(list)){
                return false;
            }
        }else{
            for(Object value : list){
                boolean res = propertyCheck(value, selecter);
                if(!res){
                    return false;
                }
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

    /**
     * 使用检查器数组中的每个检查器去校验集合中的每个元素,
     * 用于检验条件不固定的场景
     * @param list 默认集合长度不为0
     * @param checkers 数组,其中的元素是校验器,传入V,返回错误信息
     * @param <V>
     * @return
     */
    public static <V> String checkList(Collection<V> list, Function<V, String>... checkers){
        String errMsg = null;
        for(V v : list){
            for(Function<V, String> check : checkers){
                errMsg = check.apply(v);
                if(errMsg != null){
                    return errMsg;
                }
            }
        }
        return null;
    }

    /** 查出map2在map1中不存在的对象集合 */
    public static <K> void getDifferenceSet(Map<K, ?> map1, Map<K, ?> map2, Consumer<Map.Entry<K, ? extends Object>> addFun){

        for(Map.Entry<K, ?> entry : map2.entrySet()){
            K key = entry.getKey();
            if(!map1.containsKey(key)){
                addFun.accept(entry);
            }
        }
    }
}
