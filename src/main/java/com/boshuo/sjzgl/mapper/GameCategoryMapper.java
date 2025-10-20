package com.boshuo.sjzgl.mapper;

import com.boshuo.sjzgl.model.entity.GameCategory;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface GameCategoryMapper {
    int insert(GameCategory category);
    int update(GameCategory category);
    int deleteById(Integer id);
    GameCategory selectById(Integer id);
    List<GameCategory> selectAll();
    List<GameCategory> selectActiveCategories();
}