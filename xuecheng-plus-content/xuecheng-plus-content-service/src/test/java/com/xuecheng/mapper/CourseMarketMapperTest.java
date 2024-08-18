package com.xuecheng.mapper;

import com.xuecheng.content.mapper.CourseMarketMapper;
import com.xuecheng.content.model.po.CourseMarket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CourseMarketMapperTest {
    @Autowired
    private CourseMarketMapper courseMarketMapper;

    @Test
    public void testInsertCourseMarket() {
        CourseMarket courseMarket = new CourseMarket();
        courseMarket.setId(124L);
        courseMarket.setCharge("202001");
        courseMarket.setPrice(1.0);
        courseMarket.setOriginalPrice(2.0);
        courseMarket.setQq("test-qq");
        courseMarket.setWechat("test-wechat");
        courseMarket.setPhone("test-phone");
        courseMarket.setValidDays(100);
        courseMarketMapper.insertCourseMarket(courseMarket);
    }
}
