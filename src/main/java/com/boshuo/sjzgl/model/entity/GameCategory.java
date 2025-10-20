package com.boshuo.sjzgl.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class GameCategory {
    private Integer id;
    private String name;
    private String status;
    private Date createdAt;
    private Date updatedAt;
}
