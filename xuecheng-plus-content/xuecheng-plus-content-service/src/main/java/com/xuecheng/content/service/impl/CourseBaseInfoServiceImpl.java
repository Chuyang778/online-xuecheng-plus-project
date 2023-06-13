package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.mapper.CourseMarketMapper;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseCategory;
import com.xuecheng.content.model.po.CourseMarket;
import com.xuecheng.content.service.CourseBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ChuYang
 * @version 1.0
 */
@Slf4j
@Service
public class CourseBaseInfoServiceImpl extends ServiceImpl<CourseBaseMapper, CourseBase> implements CourseBaseInfoService {
    @Autowired
    private CourseBaseMapper courseBaseMapper;
    @Autowired
    private CourseMarketMapper courseMarketMapper;
    @Autowired
    private CourseCategoryMapper courseCategoryMapper;

    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto) {
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(queryCourseParamsDto.getCourseName()), CourseBase::getName, queryCourseParamsDto.getCourseName());
        queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getAuditStatus()), CourseBase::getAuditStatus, queryCourseParamsDto.getAuditStatus());
        //todo:按照课程分类
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        Page<CourseBase> pageResult = courseBaseMapper.selectPage(page, queryWrapper);
        List<CourseBase> items = pageResult.getRecords();
        long total = pageResult.getTotal();
        return new PageResult<CourseBase>(items, total, pageParams.getPageNo(), pageParams.getPageSize());
    }

    @Transactional
    @Override
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto) {
        if (StringUtils.isBlank(addCourseDto.getName()))
            throw new RuntimeException("课程名称为空");
        if (StringUtils.isBlank(addCourseDto.getMt()))
            throw new RuntimeException("课程分类为空");
        if (StringUtils.isBlank(addCourseDto.getSt()))
            throw new RuntimeException("课程分类为空");
        if (StringUtils.isBlank(addCourseDto.getGrade()))
            throw new RuntimeException("课程等级为空");
        if (StringUtils.isBlank(addCourseDto.getTeachmode()))
            throw new RuntimeException("教育模式为空");
        if (StringUtils.isBlank(addCourseDto.getUsers()))
            throw new RuntimeException("适应人群为空");
        if (StringUtils.isBlank(addCourseDto.getCharge()))
            throw new RuntimeException("收费规则为空");
        CourseBase courseBaseNew = new CourseBase();
        BeanUtils.copyProperties(addCourseDto, courseBaseNew);
        courseBaseNew.setAuditStatus("202002");
        courseBaseNew.setStatus("203001");
        courseBaseNew.setCompanyId(companyId);
        courseBaseNew.setCreateDate(LocalDateTime.now());
        int insert = courseBaseMapper.insert(courseBaseNew);
        if (insert <= 0)
            throw new RuntimeException("新增课程基本信息失败");
        CourseMarket courseMarketNew = new CourseMarket();
        Long courseId = courseBaseNew.getId();
        BeanUtils.copyProperties(addCourseDto, courseMarketNew);
        courseMarketNew.setId(courseId);
        int result = saveCourseMarket(courseMarketNew);
        if(result <= 0)
            throw new RuntimeException("保存课程营销信息失败");
        return getCourseBaseInfo(courseId);
    }

    private int saveCourseMarket(CourseMarket courseMarket) {
        String charge = courseMarket.getCharge();
        if (StringUtils.isBlank(charge)) {
            throw new RuntimeException("收费规则没有选择");
        }
        if (charge.equals("201001")) {
            if (courseMarket.getPrice() == null || courseMarket.getPrice().floatValue() <= 0) {
                throw new RuntimeException("课程为收费价格不能为空且必须大于0");
            }
        }
        CourseMarket courseMarketObj = courseMarketMapper.selectById(courseMarket.getId());
        if (courseMarketObj == null) {
            return courseMarketMapper.insert(courseMarket);
        } else {
            BeanUtils.copyProperties(courseMarket, courseMarketObj);
            courseMarketObj.setId(courseMarket.getId());
            return courseMarketMapper.updateById(courseMarketObj);
        }
    }

    //根据课程id查询课程基本信息，包括基本信息和营销信息
    public CourseBaseInfoDto getCourseBaseInfo(Long courseId) {
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase == null) return null;
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase, courseBaseInfoDto);
        if (courseMarket != null) {
            BeanUtils.copyProperties(courseMarket, courseBaseInfoDto);
        }
        CourseCategory courseCategoryByst = courseCategoryMapper.selectById(courseBase.getSt());
        courseBaseInfoDto.setStName(courseCategoryByst.getName());
        CourseCategory courseCategoryByMt = courseCategoryMapper.selectById(courseBase.getMt());
        courseBaseInfoDto.setMtName(courseCategoryByMt.getName());
        return courseBaseInfoDto;
    }

}
