package com.xuecheng.content;

import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Mr.M
 * @version 1.0
 * @description TODO
 * @date 2023/2/12 9:24
 */
@SpringBootTest
public class CourseCategoryMapperTests {

    @Autowired
    private CourseCategoryMapper courseCategoryMapper;

    @Test
    public void testCourseCategoryMapper() {
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryMapper.selectTreeNodes("1");
        courseCategoryTreeDtos.forEach(System.out::println);
        Map<String, CourseCategoryTreeDto> mapTemp = courseCategoryTreeDtos.stream().filter(item -> !(item.getId().equals("1")))
                .collect(Collectors.toMap(key -> key.getId(), value -> value, (key1, key2) -> key2));
        mapTemp.forEach(((s, courseCategoryTreeDto) -> System.out.println("id:" + s + " " + courseCategoryTreeDto)));
        List<CourseCategoryTreeDto> categoryTreeDtos = new ArrayList<>();
        courseCategoryTreeDtos.stream().filter(item -> !"1".equals(item.getId())).forEach(
                item -> {
                    if (item.getParentid().equals("1")) categoryTreeDtos.add(item);
                    CourseCategoryTreeDto courseCategoryTreeDto = mapTemp.get(item.getParentid());
                    if (courseCategoryTreeDto != null) {
                        if (courseCategoryTreeDto.getChildrenTreeNodes() == null)
                            courseCategoryTreeDto.setChildrenTreeNodes(new ArrayList<CourseCategoryTreeDto>());
                        courseCategoryTreeDto.getChildrenTreeNodes().add(item);
                    }
                }
        );
        categoryTreeDtos.forEach(System.out::println);
    }
}
