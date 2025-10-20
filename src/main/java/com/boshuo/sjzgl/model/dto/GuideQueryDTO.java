package com.boshuo.sjzgl.model.dto;

import lombok.Data;

@Data
public class GuideQueryDTO {
    private String keyword;
    private Integer categoryId;
    private String sortBy;
    private Integer page=1;
    private Integer size=10;
    private Integer offset;

    //计算偏移量
    public void calculateOffset() {
        if(page != null && size != null) {
            this.offset = (page - 1) * size;
        }
    }
}
