package com.xuecheng.content.api;


import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ChuYang
 * @version 1.0
 * 课程分类接口
 */
@RestController
@Slf4j
@Api(value = "课程分类接口",tags = "课程分类接口")
public class CourseCategoryController {

    @Autowired
    private CourseCategoryService courseCategoryService;
    @ApiOperation("查询树状课程信息")
    @GetMapping("/course-category/tree-nodes")
    public List<CourseCategoryTreeDto> queryTreeNodes() {
        return courseCategoryService.queryTreeNodes("1");
    }
}
