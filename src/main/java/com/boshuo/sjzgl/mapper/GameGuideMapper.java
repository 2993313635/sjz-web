package com.boshuo.sjzgl.mapper;

import com.boshuo.sjzgl.model.dto.GuideQueryDTO;
import com.boshuo.sjzgl.model.entity.GameGuide;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface GameGuideMapper {
    // 基础CRUD操作
    int insert(GameGuide gameGuide);
    int update(GameGuide gameGuide);
    int deleteById(Integer id);
    GameGuide selectById(Integer id);

    // 条件查询
    List<GameGuide> selectByCondition(GuideQueryDTO queryDTO);
    int countByCondition(GuideQueryDTO queryDTO);

    // 统计操作
    int incrementViewCount(Integer id);
    int incrementLikeCount(Integer id);

    // 首页推荐查询
    List<GameGuide> selectHotGuides(int limit);      // 热门攻略（按浏览量）
    List<GameGuide> selectLatestGuides(int limit);   // 最新攻略

    Long countTotalGuides();
    Long sumTotalViews();
}
