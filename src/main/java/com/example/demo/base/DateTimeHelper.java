package com.example.demo.base;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeHelper {

    public static String getNow(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
