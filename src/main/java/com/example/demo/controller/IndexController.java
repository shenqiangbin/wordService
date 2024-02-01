package com.example.demo.controller;

import cn.hutool.core.io.FileUtil;
import com.example.demo.base.BaseResponse;
import com.example.demo.base.StringUtil;
import com.example.demo.base.UploadFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jodconverter.core.DocumentConverter;
import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.document.DocumentFormat;
import org.jodconverter.core.office.OfficeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
@Validated
public class IndexController {

    private Logger log = LoggerFactory.getLogger(IndexController.class);

    @Resource
    private DocumentConverter documentConverter;

    @ResponseBody
    @GetMapping("/")
    public String index() {
        return "word-service";
    }

    /**
     *  上传文件转图片
     * @param file
     * @return
     * @throws IOException
     */
    @ResponseBody
    @PostMapping("/file/upload")
    public BaseResponse getList(@NotNull(message = "file不能为空") MultipartFile file) throws IOException {
        BaseResponse baseResponse = new BaseResponse();
        try {
            String dir = System.getProperty("user.dir");
            String filePath = new UploadFile(dir, new MultipartFile[]{file}, (fileName, ext, fileSize) -> {
                if (!ext.equals("doc") && !ext.equals("docx")) {
                    throw new RuntimeException(String.format("%s 类型不对。请上传 doc、docx 类型的文件", fileName));
                }
                long fileSizeM = (fileSize / 1024 / 1024);
                long sizeLimit = 1000;
                if (fileSizeM > sizeLimit) {
                    throw new RuntimeException(String.format("%s(%sM) 文件不能大于%sM", fileName, fileSizeM, sizeLimit));
                }
            }).save().get(0);

            // 转换
            DocumentFormat doc = DefaultDocumentFormatRegistry.DOCX;
            DocumentFormat png = DefaultDocumentFormatRegistry.PNG;

            String guid = java.util.UUID.randomUUID().toString().replaceAll("-", "");
            String pngName = guid + ".png";
            String pngPath = FilenameUtils.concat(dir, pngName);

            try {
                documentConverter.convert(new File(filePath)).as(doc).to(new File(pngPath)).as(png).execute();
            } catch (OfficeException e) {
                throw new RuntimeException(e);
            }

            baseResponse.setCode(200);
            baseResponse.setData(pngName);

            Files.deleteIfExists(Path.of(filePath));

        } catch (Exception e) {
            baseResponse.setCode(500);
            baseResponse.setData(e.getMessage());
        }

        return baseResponse;
    }

    //http://localhost:9998/file/get?filename=1_990b4d9c62a04c1db09a4b450197123d.docx
    @GetMapping("/file/get")
    public boolean download(String filename, HttpServletResponse response, String contentType) {
        try {
            String dir = System.getProperty("user.dir");
            String filePath = FilenameUtils.concat(dir, filename);
            byte[] content = FileUtils.readFileToByteArray(FileUtils.getFile(filePath));
            if (content != null && content.length > 0) {
                String fileType = "";
                response.setContentLengthLong(content.length);
                response.setContentType(!StringUtil.isEmpty(contentType) ? contentType : "application/octet-stream");
                try (OutputStream toClient = response.getOutputStream()) {
                    try (InputStream is = new ByteArrayInputStream(content)) {
                        //将请求返回的页面的流先放到buffer中，放一个buffer，输出response就写入一个buffer。
                        byte[] buffer = new byte[4096];
                        int r = 0;
                        while ((r = is.read(buffer)) > 0) {
                            toClient.write(buffer, 0, r);
                        }
                        toClient.flush();
                        return true;
                    }
                }
            }
        } catch (Exception ex) {
            log.error("error:", ex);
        }
        return false;
    }


    /**
     * 获取 word 文档的全文
     * @param file
     * @return
     */
    @ResponseBody
    @PostMapping("/file/word2Text")
    public BaseResponse word2Text(@NotNull(message = "file不能为空") MultipartFile file){

        BaseResponse baseResponse = new BaseResponse();
        try {
            String dir = System.getProperty("user.dir");
            String filePath = new UploadFile(dir, new MultipartFile[]{file}, (fileName, ext, fileSize) -> {
                if (!ext.equals("doc") && !ext.equals("docx")) {
                    throw new RuntimeException(String.format("%s 类型不对。请上传 doc、docx 类型的文件", fileName));
                }
                long fileSizeM = (fileSize / 1024 / 1024);
                long sizeLimit = 1000;
                if (fileSizeM > sizeLimit) {
                    throw new RuntimeException(String.format("%s(%sM) 文件不能大于%sM", fileName, fileSizeM, sizeLimit));
                }
            }).save().get(0);

            // 转换
            DocumentFormat doc = DefaultDocumentFormatRegistry.DOCX;
            DocumentFormat txt = DefaultDocumentFormatRegistry.TXT;

            String guid = java.util.UUID.randomUUID().toString().replaceAll("-", "");
            String txtName = guid + ".txt";
            String txtPath = FilenameUtils.concat(dir, txtName);

            try {
                documentConverter.convert(new File(filePath)).as(doc).to(new File(txtPath)).as(txt).execute();
            } catch (OfficeException e) {
                throw new RuntimeException(e);
            }

            String content = Files.readString(Path.of(txtPath), Charset.forName("UTF-8"));

            baseResponse.setCode(200);
            baseResponse.setData(content);

            Files.deleteIfExists(Path.of(filePath));
            Files.deleteIfExists(Path.of(txtPath));


        } catch (Exception e) {
            baseResponse.setCode(500);
            baseResponse.setData(e.getMessage());
        }

        return baseResponse;
    }
}
