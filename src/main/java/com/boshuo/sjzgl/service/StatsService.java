package com.boshuo.sjzgl.service;

import com.boshuo.sjzgl.mapper.GameGuideMapper;
import com.boshuo.sjzgl.mapper.GameNewMapper;
import com.boshuo.sjzgl.model.dto.HomeStatsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    @Autowired
    private GameGuideMapper gameGuideMapper;

    @Autowired
    private GameNewMapper gameNewMapper;

    /**
     * 获取首页统计信息
     */
    public HomeStatsDTO getHomeStats() {
        HomeStatsDTO stats = new HomeStatsDTO();

        try {
            // 攻略总数
            Long totalGuides = gameGuideMapper.countTotalGuides();
            stats.setTotalGuides(totalGuides != null ? totalGuides : 0L);

            // 资讯总数
            Long totalNews = gameNewMapper.countTotalNews();
            stats.setTotalNews(totalNews != null ? totalNews : 0L);

            // 总浏览量
            Long totalViews = gameGuideMapper.sumTotalViews();
            stats.setTotalViews(totalViews != null ? totalViews : 0L);

        } catch (Exception e) {
            // 设置默认值，确保首页不会因为统计失败而崩溃
            stats.setTotalGuides(0L);
            stats.setTotalNews(0L);
            stats.setTotalViews(0L);
        }

        return stats;
    }
}
