word 转图片服务提供的功能有：
1、word 转图片
2、提取 word 中的文本内容

word 转图片分两步：

1 、word 文件上传
	curl --location 'http://localhost:9998/file/upload' --form 'file=@"/C:/Users/cnki52/Desktop/fsdownload/createTemplate/1.docx"'
	
2、图片文件下载
	curl http://localhost:9998/file/get?filename=46f33d0eb2f740a79f7db65a2c07004d.png
![image](https://github.com/shenqiangbin/wordService/assets/23185639/3d55d7bc-79a4-458f-b7db-f7aa2e04956a)
