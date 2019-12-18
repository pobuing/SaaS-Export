package com.itheima.saas.web.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wangxin
 * @date 2019/12/18 18:04
 * @description: TODO
 * GOOD LUCK！
 * String-->Data 转换器
 */
public class StringToDataConverter implements Converter<String, Date> {
    public Date convert(String source) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
