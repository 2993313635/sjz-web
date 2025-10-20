package com.boshuo.sjzgl.model.dto;

import lombok.Data;

@Data

public class NewQueryDTO {
    private String keyword;
    private Integer page = 1;
    private Integer size = 10;
    private Integer offset;

    public void calculateOffset() {
        if(page != null && size != null) {
            this.offset = (page - 1) * size;
        }
    }
}
