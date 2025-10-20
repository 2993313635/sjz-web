package com.boshuo.sjzgl.model.dto;

import lombok.Data;

@Data
public class HomeStatsDTO {
    private Long totalGuides;    // 攻略总数
    private Long totalNews;      // 资讯总数
    private Long totalViews;     // 总浏览量
}
