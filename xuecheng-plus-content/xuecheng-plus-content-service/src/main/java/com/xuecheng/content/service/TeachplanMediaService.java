package com.xuecheng.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuecheng.content.model.dto.BindTeachplanMediaDto;
import com.xuecheng.content.model.po.TeachplanMedia;

/**
 * @author ChuYang
 * @version 1.0
 */
public interface TeachplanMediaService extends IService<TeachplanMedia> {
    TeachplanMedia associationMedia(BindTeachplanMediaDto bindTeachplanMediaDto);

    void unassociationMedia(Long TeachplanId,Long mediaId);
}
