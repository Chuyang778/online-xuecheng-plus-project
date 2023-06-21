package com.xuecheng.media.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuecheng.media.model.po.MediaProcess;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author ChuYang
 * @version 1.0
 */
public interface MediaFileProcessService extends IService<MediaProcess> {

    List<MediaProcess> getMediaProcessList(int shardIndex,int shardTotal,int count);

    boolean startTask(long id);

    void saveProcessFinishStatus(Long taskId,String status,String fileId,String url,String errorMsg);
}
