package com.xuecheng.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuecheng.content.model.dto.CoursePreviewDto;
import com.xuecheng.content.model.po.CoursePublish;

import java.io.File;

/**
 * @author ChuYang
 * @version 1.0
 */
public interface CoursePublishService extends IService<CoursePublish> {
    CoursePreviewDto getCoursePreviewInfo(Long courseId);

    void commitAudit(Long companyId,Long courseId);

    void publish(Long companyId,Long courseId);

    File generateCourseHtml(Long courseId);

    void uploadCourseHtml(Long courseId, File file);
}
