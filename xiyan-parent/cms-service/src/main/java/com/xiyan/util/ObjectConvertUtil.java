package com.xiyan.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ******Title:程序的奥秘 ******
 * ******Description: DO转换为VO******
 * ******Company: ******
 *
 * @version V1.0:
 * @author: 刘 明
 * @date:Created in 2019/6/9 0009 19:32
 */
public class ObjectConvertUtil<T> {

    /**
     * DO转换为DTO
     * DTO转换为DO
     * VO转换为DTO
     * DTO转换为VO
     *
     * @param startObj
     * @return
     */
    public List<T> objectConvert(List<T> startObj, Class<T> clazz) {
        List<T> endObj = new ArrayList<>();

        for (T obj : startObj) {
            T t = null;
            try {
                t = clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            BeanUtils.copyProperties(obj, t);
            endObj.add(t);
        }
        return endObj;
    }

    @SuppressWarnings("rawtypes")
    public static ObjectConvertUtil convertInstance() {
        return new ObjectConvertUtil();
    }
}
