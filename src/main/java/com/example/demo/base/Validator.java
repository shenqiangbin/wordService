package com.example.demo.base;

import org.springframework.util.StringUtils;

import java.util.List;

public class Validator {
    public static void check(List<Object> list) throws Exception {
        for (Object item : list) {
            if (item instanceof String) {
                if (StringUtils.isEmpty(item)) {
                    throw new Exception("存在空数据:" + JSONUtil.toString(list));
                }
            }
            if(item == null) {
                throw new Exception("存在空数据" + JSONUtil.toString(list));
            }
        }
    }
}
