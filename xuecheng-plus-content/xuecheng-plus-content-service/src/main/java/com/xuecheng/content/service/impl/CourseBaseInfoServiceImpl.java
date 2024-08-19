package com.xuecheng.content.service.impl;

import com.xuecheng.base.enums.ChargeType;
import com.xuecheng.base.enums.CommonError;
import com.xuecheng.base.enums.CourseAuditStatus;
import com.xuecheng.base.enums.CourseStatus;
import com.xuecheng.base.execption.XueChengPlusException;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseMarketMapper;
import com.xuecheng.content.model.dto.AddCourseDTO;
import com.xuecheng.content.model.dto.CourseBaseInfoDTO;
import com.xuecheng.content.model.dto.QueryCourseParamsDTO;
import com.xuecheng.content.model.dto.UpdateCourseDTO;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseMarket;
import com.xuecheng.content.service.CourseBaseInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {
    @Autowired
    private CourseBaseMapper courseBaseMapper;
    @Autowired
    private CourseMarketMapper courseMarketMapper;

    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDTO queryCourseParamsDTO) {
        Long count = courseBaseMapper.selectCount();
        List<CourseBase> courseBases = courseBaseMapper.selectByCondition(pageParams,
                queryCourseParamsDTO.getCourseName(), queryCourseParamsDTO.getAuditStatus(), queryCourseParamsDTO.getPublishStatus());
        return new PageResult<>(courseBases, count, pageParams.getPageNo(), pageParams.getPageSize());
    }

    @Transactional
    @Override
    public CourseBaseInfoDTO addCourseBase(AddCourseDTO addCourseDTO) {
        ChargeType chargeType = ChargeType.of(addCourseDTO.getCharge());
        if (ChargeType.PAID.equals(chargeType)) {
            if (addCourseDTO.getOriginalPrice() <= 0) {
                XueChengPlusException.cast("收费课程原价必须大于0");
            }
            if (addCourseDTO.getPrice() <= 0) {
                XueChengPlusException.cast("收费课程现价必须大于0");
            }
        } else if (ChargeType.FREE.equals(chargeType)) {
            if (addCourseDTO.getOriginalPrice() <= 0) {
                XueChengPlusException.cast("收费课程原价必须大于0");
            }
            if (addCourseDTO.getPrice() != 0) {
                XueChengPlusException.cast("免费课程价格必须为0");
            }

        } else {
            XueChengPlusException.cast("课程收费参数异常");
        }

        CourseBase courseBase = new CourseBase();
        BeanUtils.copyProperties(addCourseDTO, courseBase);
        courseBase.setStatus(CourseStatus.UNPUBLISHED.getCode());
        courseBase.setAuditStatus(CourseAuditStatus.NOT_SUBMITTED.getCode());
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
        if (id == null) {
            XueChengPlusException.cast(CommonError.REQUEST_NULL);
        }

        CourseBase courseBase = courseBaseMapper.selectById(id);
        if (courseBase == null) {
            XueChengPlusException.cast(CommonError.QUERY_NULL);
        }
        CourseMarket courseMarket = Optional.ofNullable(courseMarketMapper.selectById(id))
                .orElseGet(() -> {
                    CourseMarket emptyCourseMarket = new CourseMarket();
                    emptyCourseMarket.setId(courseBase.getId());
                    return emptyCourseMarket;
                });

        CourseBaseInfoDTO courseBaseInfoDTO = new CourseBaseInfoDTO();
        BeanUtils.copyProperties(courseBase, courseBaseInfoDTO);
        BeanUtils.copyProperties(courseMarket, courseBaseInfoDTO);
        return courseBaseInfoDTO;
    }

    @Transactional
    @Override
    public CourseBaseInfoDTO updateCourseBaseInfo(UpdateCourseDTO updateCourseDTO) {
        ChargeType chargeType = ChargeType.of(updateCourseDTO.getCharge());
        if (ChargeType.PAID.equals(chargeType)) {
            if (updateCourseDTO.getOriginalPrice() <= 0) {
                XueChengPlusException.cast("收费课程原价必须大于0");
            }

            if (updateCourseDTO.getPrice() <= 0) {
                XueChengPlusException.cast("收费课程现价必须大于0");
            }
        }

        CourseBase courseBase = new CourseBase();
        BeanUtils.copyProperties(updateCourseDTO, courseBase);
        if (courseBaseMapper.updateCourseBase(courseBase) <= 0) {
            XueChengPlusException.cast("课程不存在");
        }
        CourseMarket courseMarket = new CourseMarket();
        BeanUtils.copyProperties(updateCourseDTO, courseMarket);
        if (courseMarketMapper.updateCourseMarket(courseMarket) == 0) {
            courseMarketMapper.insertCourseMarket(courseMarket);
        }

        CourseBaseInfoDTO courseBaseInfoDTO = new CourseBaseInfoDTO();
        BeanUtils.copyProperties(courseBase, courseBaseInfoDTO);
        BeanUtils.copyProperties(courseMarket, courseBaseInfoDTO);
        return courseBaseInfoDTO;
    }
}
