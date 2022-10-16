package com.banking.projectserver.util;

import com.banking.projectserver.response.FileUploadResult;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;

/**
 * TODO:上传文件service类
 *
 * @author 孟繁霖
 * @date 2021/11/26 9:57
 */
@Component
public class UploadFile {


    @Value("${file.secretId}")
    private String secretId;

    @Value("${file.secretKey}")
    private String secretKey;

    @Value("${file.regionName}")
    private String regionName;

    @Value("${file.bucketName}")
    private String bucketName;



    /**
     * TODO:上传文件COS(COS配置绑定)
     * @param multipart 上传文件MultipartFile
     * @param dir 存储目录 如：folder
     * @param newFileName 文件存储的重命名
     * @return : com.se.easylab.Result.FileUploadResult
     * @author Covenant
     * @date 2021-12-02 13:22
     */
    public FileUploadResult uploadFile(MultipartFile multipart, String dir, String newFileName)  {
        // 1 初始化用户身份信息（secretId, secretKey）。
        // SECRETID和SECRETKEY请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
//        String secretId = "AKIDCF2YnmVx83i6D3dxzlGUQ5TcU5PSFl0x";
//        String secretKey = "iALGuHMAi00jCOJyMUP3QWLtABwPbXCb";
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(regionName);
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.http);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);


        String originalFilename = multipart.getOriginalFilename();
        String suffix=originalFilename.substring(originalFilename.lastIndexOf("."));//获取文件后缀，带"."
        String newFileFullName=newFileName+suffix;//新文件名，带后缀

        //TODO: 检验文件格式，大小



        // 指定文件将要存放的存储桶
//        String bucketName = "covenant-1308013334";
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        String key = dir+"/"+newFileFullName;
        try {
            cosClient.putObject(bucketName,key,multipart.getInputStream(),null);
        } catch (IOException e) {
            e.printStackTrace();
            return FileUploadResult.getFileUploadResult().fail("服务器内部错误");
        }
        finally{
            cosClient.shutdown();
        }
//        <bucketname-appid>.cos.<region>.myqcloud.com
        String fileUrl="https://"+bucketName+".cos."+region.getRegionName()+".myqcloud.com/"+dir+"/"+newFileFullName;
        return FileUploadResult.getFileUploadResult().success(fileUrl);
    }
}
