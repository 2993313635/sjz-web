package com.boshuo.sjzgl.mapper;

import com.boshuo.sjzgl.model.entity.Banner;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface BannerMapper {
    int insert(Banner banner);
    int update(Banner banner);
    int deleteById(Integer id);
    Banner selectById(Integer id);
    List<Banner> selectActiveBanners();  // 查询激活的Banner
    List<Banner> selectAll();
    int updateStatus(Integer id, Boolean isActive);  // 更新状态
}