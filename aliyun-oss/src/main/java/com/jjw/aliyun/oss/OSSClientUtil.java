package com.jjw.aliyun.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.BucketInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by jwang on 2017/3/9.
 */
public class OSSClientUtil implements IOSSBasicOperation {
    private static Logger logger = LoggerFactory.getLogger(OSSClientUtil.class);
    private OSSClient ossClient = null;
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String firstKey;

    public OSSClientUtil(String endpoint, String accessKeyId, String accessKeySecret, String bucketName, String firstKey) {
        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.bucketName = bucketName;
        this.firstKey = firstKey;
        init();
    }

    public void init() {
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        if (ossClient.doesBucketExist(bucketName)) {
            logger.info("您已经创建Bucket：" + bucketName + "。");
        } else {
            logger.info("您的Bucket不存在，创建Bucket：" + bucketName + "。");
            ossClient.createBucket(bucketName);
        }

        // 查看Bucket信息。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
        // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
        BucketInfo info = ossClient.getBucketInfo(bucketName);
        /*System.out.println("Bucket " + bucketName + "的信息如下：");
        System.out.println("\t数据中心：" + info.getBucket().getLocation());
        System.out.println("\t创建时间：" + info.getBucket().getCreationDate());
        System.out.println("\t用户标志：" + info.getBucket().getOwner());*/
    }

    public boolean putFile(String bucketName, String pathName, File file) {
        ossClient.putObject(bucketName,pathName,file);
        return false;
    }
}
