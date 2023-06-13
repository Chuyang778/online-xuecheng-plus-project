package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.CourseCategory;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author ChuYang
 * @version 1.0
 */
@Data
public class CourseCategoryTreeDto extends CourseCategory implements Serializable {
    private List<CourseCategoryTreeDto> childrenTreeNodes;

    @Override
    public String toString() {
        return super.toString()  + "childrenTreeNodes'" + childrenTreeNodes + '\'' ;
    }
}
