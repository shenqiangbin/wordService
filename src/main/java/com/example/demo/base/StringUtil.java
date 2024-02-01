package com.example.demo.base;

import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static String toEmptyIfNULL(String str) {
        return str == null ? "" : str;
    }

    public static String toEmptyIfNULL(Object str) {
        return str == null ? "" : str.toString();
    }

    public static boolean isEmpty(@Nullable Object str) {
        return str == null || "".equals(str) || str.toString().trim().equals("");
    }

    /**
     *      * 匹配是否包含数字
     *      * @param str 可能为中文，也可能是-19162431.1254，不使用BigDecimal的话，变成-1.91624311254E7
     *      * @return
     *      * @author yutao
     *      * @date 2016年11月14日下午7:41:22
     *      
     */
    public static boolean isNumeric(String str) {
        // 该正则表达式可以匹配所有的数字 包括负数
        Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }

        Matcher isNum = pattern.matcher(bigStr); // matcher是全匹配
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static boolean containHanZi(String str){
        return  str.length() != str.getBytes().length;
    }
}
