package com.boshuo.sjzgl.model.dto;


import com.boshuo.sjzgl.model.entity.Banner;
import com.boshuo.sjzgl.model.entity.GameGuide;
import com.boshuo.sjzgl.model.entity.GameNew;
import lombok.Data;

import java.util.List;

@Data
public class    HomeDataDTO {
    private List<Banner> banners;           //Banner列表
    private List<GameGuide> hotGuides;      //热门攻略
    private List<GameGuide> latestGuides;   //最新攻略
    private List<GameNew> latestNews;      //最新资讯
    private HomeStatsDTO stats;             // 首页统计信息（新增）


}
