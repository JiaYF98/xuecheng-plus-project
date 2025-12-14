package com.xuecheng.media.mapper;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.media.model.po.MediaFiles;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 媒资信息 Mapper 接口
 * </p>
 */
@Mapper
public interface MediaFilesMapper {

    MediaFiles selectById(String fileMd5);

    int insert(MediaFiles mediaFiles);

    List<MediaFiles> selectByPage(PageParams pageParams);
}
