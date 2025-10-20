package com.boshuo.sjzgl.mapper;

import com.boshuo.sjzgl.model.dto.NewQueryDTO;
import com.boshuo.sjzgl.model.entity.GameNew;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface GameNewMapper {
    // 基础CRUD操作
    int insert(GameNew news);
    int update(GameNew news);
    int deleteById(Integer id);
    GameNew selectById(Integer id);

    // 条件查询
    List<GameNew> selectByCondition(NewQueryDTO queryDTO);
    Integer countByCondition(NewQueryDTO queryDTO);

    // 首页查询
    List<GameNew> selectLatestNews(int limit);

    Long countTotalNews();
}