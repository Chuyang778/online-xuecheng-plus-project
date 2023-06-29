package com.xuecheng;

import com.xuecheng.content.config.MultipartSupportConfig;
import com.xuecheng.content.feignclient.MediaServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author ChuYang
 * @version 1.0
 */
@SpringBootApplication
@EnableFeignClients(basePackages={"com.xuecheng.content.feignclient"})
public class XuechengPlusContentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(XuechengPlusContentServiceApplication.class, args);
    }
}
