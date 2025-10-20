package com.boshuo.sjzgl.service;

import com.boshuo.sjzgl.model.dto.HomeDataDTO;
import com.boshuo.sjzgl.model.dto.HomeStatsDTO;
import com.boshuo.sjzgl.model.entity.Banner;
import com.boshuo.sjzgl.model.entity.GameGuide;
import com.boshuo.sjzgl.model.entity.GameNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

    @Autowired
    private BannerService bannerService;

    @Autowired
    private GameGuideService gameGuideService;

    @Autowired
    private GameNewService gameNewService;

    @Autowired
    private StatsService statsService;



    /**
     * 获取首页数据
     */
    public HomeDataDTO getHomeData() {
        HomeDataDTO homeData = new HomeDataDTO();

        try {
            // 获取Banner数据
            List<Banner> banners = bannerService.getActiveBanners();
            homeData.setBanners(banners);

            // 获取热门攻略（按浏览量前8条）
            List<GameGuide> hotGuides = gameGuideService.getHotGuides(8);
            homeData.setHotGuides(hotGuides);

            // 获取最新攻略（前6条）
            List<GameGuide> latestGuides = gameGuideService.getLatestGuides(6);
            homeData.setLatestGuides(latestGuides);

            // 获取最新资讯（前5条）
            List<GameNew> latestNews = gameNewService.getLatestNews(5);
            homeData.setLatestNews(latestNews);
            // 新增：获取统计信息
            HomeStatsDTO stats = statsService.getHomeStats();
            homeData.setStats(stats);

        } catch (Exception e) {
            // 记录日志，但不要让首页完全失败
            // 可以设置空列表或默认值
            homeData.setBanners(List.of());
            homeData.setHotGuides(List.of());
            homeData.setLatestGuides(List.of());
            homeData.setLatestNews(List.of());
            homeData.setStats(new HomeStatsDTO());
        }

        return homeData;
    }
}