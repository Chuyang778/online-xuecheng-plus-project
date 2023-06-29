package com.xuecheng.content.feignclient;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.io.IOException;

/**
 * @author ChuYang
 * @version 1.0
 */
@Slf4j
@Component
public class MediaServiceClientFallbackFactory implements FallbackFactory<MediaServiceClient> {
    @Override
    public MediaServiceClient create(Throwable throwable) {
        return (filedata, folder, objectName) -> {
            log.debug("远程调用上传文件的接口发生熔断:{}", throwable.toString(), throwable);
            return null;
        };
    }
}
