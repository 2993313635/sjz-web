package com.boshuo.sjzgl.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class GameNew {
    private Integer id;
    private String title;
    private String summary;
    private String content;
    private String coverImage;
    private Date createdAt;
}
