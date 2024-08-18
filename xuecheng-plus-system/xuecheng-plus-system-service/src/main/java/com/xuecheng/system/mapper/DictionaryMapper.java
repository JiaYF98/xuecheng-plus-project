package com.xuecheng.system.mapper;

import com.xuecheng.system.model.po.Dictionary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 数据字典 Mapper 接口
 * </p>
 *
 * @author itcast
 */
@Mapper
public interface DictionaryMapper {
    List<Dictionary> queryAll();
    Dictionary selectByCode(String code);
}
