package com.jjw.aliyun.oss;

import java.io.File;

/**
 * Created by jwang on 2017/3/9.
 */
public interface IOSSBasicOperation {
    boolean putFile(String bucketName, String pathName, File o);
}
