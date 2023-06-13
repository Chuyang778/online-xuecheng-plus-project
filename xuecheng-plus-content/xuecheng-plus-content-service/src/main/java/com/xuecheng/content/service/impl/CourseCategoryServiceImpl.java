package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.model.po.CourseCategory;
import com.xuecheng.content.service.CourseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ChuYang
 * @version 1.0
 */
@Service
public class CourseCategoryServiceImpl extends ServiceImpl<CourseCategoryMapper, CourseCategory> implements CourseCategoryService {
    @Autowired
    private CourseCategoryMapper courseCategoryMapper;

    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryMapper.selectTreeNodes(id);
        Map<String, CourseCategoryTreeDto> mapTemp = courseCategoryTreeDtos.stream().filter(item -> !(item.getId().equals(id)))
                .collect(Collectors.toMap(key -> key.getId(), value -> value, (key1, key2) -> key2));
        List<CourseCategoryTreeDto> categoryTreeDtos = new ArrayList<>();
        courseCategoryTreeDtos.stream().filter(item -> !id.equals(item.getId())).forEach(
                item -> {
                    if (item.getParentid().equals(id)) categoryTreeDtos.add(item);
                    CourseCategoryTreeDto courseCategoryTreeDto = mapTemp.get(item.getParentid());
                    if (courseCategoryTreeDto != null) {
                        if (courseCategoryTreeDto.getChildrenTreeNodes() == null)
                            courseCategoryTreeDto.setChildrenTreeNodes(new ArrayList<CourseCategoryTreeDto>());
                        courseCategoryTreeDto.getChildrenTreeNodes().add(item);
                    }

                });
        return categoryTreeDtos;
    }
}
