package com.xuecheng.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author ChuYang
 * @version 1.0
 */
public interface CourseBaseInfoService extends IService<CourseBase> {
    PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto);

    CourseBaseInfoDto createCourseBase(Long companyId, @RequestBody AddCourseDto addCourseDto);

    CourseBaseInfoDto getCourseBaseInfo(Long courseId);


    CourseBaseInfoDto updateCourseBaseInfo(Long companyId, EditCourseDto editCourseDto);

    void deleteCourse(Long companyId, Long courseId);
}