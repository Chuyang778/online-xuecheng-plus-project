package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.content.mapper.CourseTeacherMapper;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseTeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ChuYang
 * @version 1.0
 */
@Service
@Slf4j
public class CourseTeacherServiceImpl extends ServiceImpl<CourseTeacherMapper, CourseTeacher> implements CourseTeacherService {

    @Autowired
    private CourseTeacherMapper courseTeacherMapper;

    @Override
    public List<CourseTeacher> getCourseTeacherList(Long courseId) {
        LambdaQueryWrapper<CourseTeacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CourseTeacher::getCourseId, courseId);
        List<CourseTeacher> courseTeacherList = this.list(queryWrapper);
        return courseTeacherList;
    }

    @Override
    @Transactional
    public CourseTeacher saveoreditCourseTeacher(CourseTeacher courseTeacher) {
        Long id = courseTeacher.getId();
        if (id == null) {
            CourseTeacher teacher = new CourseTeacher();
            BeanUtils.copyProperties(courseTeacher, teacher);
            teacher.setCreateDate(LocalDateTime.now());
            boolean result = this.save(teacher);
            if (!result) XueChengPlusException.cast("新增失败");
            return getCourseTeacher(teacher);
        } else {
            CourseTeacher teacher = courseTeacherMapper.selectById(id);
            BeanUtils.copyProperties(courseTeacher, teacher);
            int flag = courseTeacherMapper.updateById(teacher);
            if (flag <= 0)
                XueChengPlusException.cast("修改失败");
            return getCourseTeacher(teacher);
        }
    }

    @Override
    public void deleteCourseTeacher(Long courseId, Long teacherId) {
        LambdaQueryWrapper<CourseTeacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CourseTeacher::getId,teacherId);
        queryWrapper.eq(CourseTeacher::getCourseId,courseId);
        boolean result = this.remove(queryWrapper);
        if(!result) XueChengPlusException.cast("删除失败");
    }

    private CourseTeacher getCourseTeacher(CourseTeacher courseTeacher) {
        return this.getById(courseTeacher.getId());
    }
}
