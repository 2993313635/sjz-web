package com.boshuo.sjzgl.model.entity;

import lombok.Data;

@Data
public class Banner {
    private Integer id;
    private String title;
    private String imageUrl;
    private String linkUrl;
    private Integer sortOrder;
    private Integer isActive;
}
