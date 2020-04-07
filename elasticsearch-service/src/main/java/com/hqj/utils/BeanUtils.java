package com.hqj.utils;

import com.google.common.collect.Lists;
import org.dozer.DozerBeanMapper;

import java.util.Collection;
import java.util.List;

/**
 * bean工具
 */
public class BeanUtils {
    private static DozerBeanMapper dozer = new DozerBeanMapper();

    /**
     * 基于Dozer转换对象的类型

     * @param source 资源数据
     * @param destinationClass 目标对象类型
     * @return
     */
    public static <T> T map(Object source, Class<T> destinationClass) {
        return dozer.map(source, destinationClass);
    }

    /**
     * 基于Dozer转换Collection中对象的类型

     * @param sourceList 资源数据列表
     * @param destinationClass 目标对象类型
     * @return
     */
    public static <T, V> List<V> mapList(Collection<T> sourceList, Class<V> destinationClass) {
        List<V> destinationList = Lists.newArrayList();
        for (Object sourceObject : sourceList) {
            V destinationObject = dozer.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        return destinationList;
    }

}
