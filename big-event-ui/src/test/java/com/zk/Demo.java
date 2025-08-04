package com.zk;

/**
 * @program: big-event
 * @description: 阿里云OSS测试类
 * @author: 张恺
 * @create: 2025-07-18 09:21
 * @version: 1.0
 **/
import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.*;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.*;
import java.io.ByteArrayInputStream;
import java.io.FilterInputStream;
import java.io.FileInputStream;

public class Demo {

    public static void main(String[] args) {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        
        // 从环境变量或系统属性中获取访问凭证
        String accessKeyId = System.getenv("ALIYUN_ACCESS_KEY_ID") != null ? 
            System.getenv("ALIYUN_ACCESS_KEY_ID") : 
            System.getProperty("aliyun.access.key.id", "your_access_key_id");
        
        String accessKeySecret = System.getenv("ALIYUN_ACCESS_KEY_SECRET") != null ? 
            System.getenv("ALIYUN_ACCESS_KEY_SECRET") : 
            System.getProperty("aliyun.access.key.secret", "your_access_key_secret");
        
        String bucketName = "zksks";
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        String objectName = "001.png";
        // 填写Bucket所在地域。以华东1（杭州）为例，Region填写为cn-hangzhou。
        String region = "cn-hangzhou";
        String localFilePath = "C:\\Users\\张恺\\Desktop\\big-event\\file\\001.png";

        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 创建PutObjectRequest对象
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, new FileInputStream(localFilePath));

            // 上传文件
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            System.out.println("上传成功！");
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught a ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}