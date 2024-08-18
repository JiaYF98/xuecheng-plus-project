package com.xuecheng.content.service.impl;

import com.xuecheng.base.enums.AuditStatus;
import com.xuecheng.base.enums.ChargeType;
import com.xuecheng.base.enums.CourseStatus;
import com.xuecheng.base.execption.XueChengPlusException;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseMarketMapper;
import com.xuecheng.content.model.dto.AddCourseDTO;
import com.xuecheng.content.model.dto.CourseBaseInfoDTO;
import com.xuecheng.content.model.dto.QueryCourseParamsDTO;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseMarket;
import com.xuecheng.content.service.CourseBaseInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {
    @Autowired
    private CourseBaseMapper courseBaseMapper;
    @Autowired
    private CourseMarketMapper courseMarketMapper;

    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDTO queryCourseParamsDTO) {
        List<CourseBase> courseBases = courseBaseMapper.selectByCondition(pageParams,
                queryCourseParamsDTO.getCourseName(), queryCourseParamsDTO.getAuditStatus(), queryCourseParamsDTO.getPublishStatus());
        return new PageResult<>(courseBases, courseBases.size(), pageParams.getPageNo(), pageParams.getPageSize());
    }

    @Override
    public CourseBaseInfoDTO addCourseBase(AddCourseDTO addCourseDTO) {
        ChargeType chargeType = ChargeType.of(addCourseDTO.getCharge());
        if (ChargeType.PAID.equals(chargeType)) {
            if (addCourseDTO.getPrice() <= 0) {
                XueChengPlusException.cast("收费课程价格不能小于等于0");
            }
        } else if (ChargeType.UNKNOWN.equals(chargeType)) {
            XueChengPlusException.cast("课程收费参数异常");
        }

        CourseBase courseBase = new CourseBase();
        BeanUtils.copyProperties(addCourseDTO, courseBase);
        courseBase.setStatus(CourseStatus.UNPUBLISHED.getCode());
        courseBase.setAuditStatus(AuditStatus.UNAUDITED.getCode());
        courseBaseMapper.insertCourseBase(courseBase);

        CourseMarket courseMarket = new CourseMarket();
        BeanUtils.copyProperties(addCourseDTO, courseMarket);
        courseMarket.setId(courseBase.getId());
        courseMarketMapper.insertCourseMarket(courseMarket);

        CourseBaseInfoDTO courseBaseInfoDTO = new CourseBaseInfoDTO();
        BeanUtils.copyProperties(courseBase, courseBaseInfoDTO);
        BeanUtils.copyProperties(courseMarket, courseBaseInfoDTO);
        return courseBaseInfoDTO;
    }

    @Override
    public CourseBaseInfoDTO getCourseBaseInfo(Long id) {
        CourseBase courseBase = courseBaseMapper.selectById(id);
        if (courseBase == null) {
            throw new XueChengPlusException("课程不存在");
        }
        CourseMarket courseMarket = courseMarketMapper.selectById(id);

        CourseBaseInfoDTO courseBaseInfoDTO = new CourseBaseInfoDTO();
        BeanUtils.copyProperties(courseBase, courseBaseInfoDTO);
        BeanUtils.copyProperties(courseMarket, courseBaseInfoDTO);
        return courseBaseInfoDTO;
    }
}
