package com.xuecheng.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.model.po.CourseCategory;

import java.util.List;

/**
 * @author ChuYang
 * @version 1.0
 */
public interface CourseCategoryService extends IService<CourseCategory> {
    List<CourseCategoryTreeDto> queryTreeNodes(String id);
}
