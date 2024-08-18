package com.xuecheng.content.model.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TeachplanMedia {
    private Long id;
    private String mediaId;
    private Long teachplanId;
    private Long courseId;
    private String mediaFileName;
    private LocalDateTime createDate;
    private String createPeople;
    private String changePeople;
}
