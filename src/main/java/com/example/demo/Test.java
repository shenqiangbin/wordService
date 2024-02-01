package com.example.demo;

import com.alibaba.fastjson2.JSON;
import com.example.demo.base.HttpHelper;
import com.example.demo.base.JSONUtil;
import com.example.demo.base.StringUtil;
import com.example.demo.model.bll.DomainInfo;
import com.example.demo.model.bll.ResultInfo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;

public class Test {
    public static void main(String[] args) throws Exception {
        boolean b = pingService();
        System.out.println(b ? "服务正常": "word-service 服务不可用");
        //testDoc2Png();
        //testDoc2Txt();
    }

    static boolean pingService()  {
        String serverUrl = "http://10.120.130.49:9998";
        String s = null;
        try {
            s = HttpHelper.httpGet(serverUrl);
            return s.equals("word-service");
        } catch (IOException e) {
            return false;
        }
    }

    static void testDoc2Png() throws Exception {
        // 上传 word 转化成图片测试
        //String serverUrl = "http://localhost:9998/file/upload";
        String serverUrl = "http://10.120.130.49:9998/file/upload";
        String localFilePath = "e:/1.docx";
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        String result = HttpHelper.uploadFile(serverUrl, localFilePath, "file", map, null);
        System.out.println(result);

        Result theResult = JSON.parseObject(result, Result.class);
        if (theResult.getCode() != 200) {
            throw new Exception("上传失败了");
        }

        String pngName = theResult.getData().toString();
        String url = "http://10.120.130.49:9998/file/get?filename=" + pngName;
        HttpHelper.downloadFile(url, null, "e:/1.png");
    }

    static void testDoc2Txt() throws Exception {
        String serverUrl = "http://10.120.130.49:9998/file/word2Text";
        String localFilePath = "e:/1.docx";
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        String result = HttpHelper.uploadFile(serverUrl, localFilePath, "file", map, null);
        //System.out.println(result);

        Result theResult = JSON.parseObject(result, Result.class);
        if (theResult.getCode() != 200) {
            throw new Exception("上传失败了");
        }
        System.out.println("here");
        System.out.println(theResult.getData());
    }

}

class Result {
    private Integer code;
    private String message;
    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}