package com.boshuo.sjzgl.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    private List<T> list;
    private int total;
    private int page;
    private int size;
    private int pages;

    public PageResult(List<T> list, int total, int page, int size) {
        this.list = list;
        this.total = total;
        this.page = page;
        this.size = size;
        this.pages = (int) Math.ceil((double) total / size);
    }

    //无参构造函数
    public PageResult() {

    }

    //静态工厂方法
    public static <T> PageResult<T> of(List<T> list, int total, int page, int size) {
        return new PageResult<>(list, total, page, size);
    }
}
