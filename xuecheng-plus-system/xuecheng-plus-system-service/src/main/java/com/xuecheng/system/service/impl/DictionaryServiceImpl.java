package com.xuecheng.system.service.impl;


import com.xuecheng.system.mapper.DictionaryMapper;
import com.xuecheng.system.model.po.Dictionary;
import com.xuecheng.system.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据字典 服务实现类
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {
    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Override
    public List<Dictionary> queryAll() {
        return dictionaryMapper.queryAll();
    }

    @Override
    public Dictionary selectByCode(String code) {
        return dictionaryMapper.selectByCode(code);
    }
}
