package com.zk.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @program: big-event
 * @description: 阿里云OSS工具类
 * @author: 张恺
 * @create: 2025-07-18 09:42
 * @version: 1.0
 **/
public class AliOssUtils {

    // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
    private static final String ENDPOINT = "https://oss-cn-hangzhou.aliyuncs.com";
    
    // 从环境变量或系统属性中获取访问凭证
    private static final String ACCESSKEYID = System.getenv("ALIYUN_ACCESS_KEY_ID") != null ? 
        System.getenv("ALIYUN_ACCESS_KEY_ID") : 
        System.getProperty("aliyun.access.key.id", "your_access_key_id");
    
    private static final String ACCESSKEYSECRET = System.getenv("ALIYUN_ACCESS_KEY_SECRET") != null ? 
        System.getenv("ALIYUN_ACCESS_KEY_SECRET") : 
        System.getProperty("aliyun.access.key.secret", "your_access_key_secret");
    
    private static final String BUCKETNAME = "zksks";

    public static String main(String objectName , InputStream in) {

        String url = "";
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        // 填写Bucket所在地域。以华东1（杭州）为例，Region填写为cn-hangzhou。
        String region = "cn-hangzhou";

        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);

        try {
            // 创建PutObjectRequest对象
            PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKETNAME, objectName, in);

            // 上传文件
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            url = "https://" + BUCKETNAME + "." + ENDPOINT.substring(ENDPOINT.lastIndexOf("/") + 1) + "/" + objectName;
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
        return url;
    }
}
