package com.boshuo.sjzgl.service;

import com.boshuo.sjzgl.exception.BusinessException;
import com.boshuo.sjzgl.mapper.BannerMapper;
import com.boshuo.sjzgl.model.entity.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    /**
     * 获取所有激活的Banner（按sortOrder排序）
     */
    public List<Banner> getActiveBanners() {
        return bannerMapper.selectActiveBanners();
    }

    /**
     * 获取所有Banner（管理用）
     */
    public List<Banner> getAllBanners() {
        // 这里可以添加分页逻辑，目前返回所有
        return bannerMapper.selectAll();  // 注意：实际项目中需要添加查询所有Banner的方法
    }

    /**
     * 根据ID获取Banner
     */
    public Banner getBannerById(Integer id) {
        if (id == null || id <= 0) {
            throw new BusinessException("Banner ID不能为空");
        }

        Banner banner = bannerMapper.selectById(id);
        if (banner == null) {
            throw new BusinessException(404, "Banner不存在");
        }

        return banner;
    }

    /**
     * 创建Banner
     */
    public Integer createBanner(Banner banner) {
        // 参数验证
        if (banner == null) {
            throw new BusinessException("Banner信息不能为空");
        }
        if (banner.getTitle() == null || banner.getTitle().trim().isEmpty()) {
            throw new BusinessException("Banner标题不能为空");
        }
        if (banner.getImageUrl() == null || banner.getImageUrl().trim().isEmpty()) {
            throw new BusinessException("Banner图片不能为空");
        }

        // 设置默认值
        if (banner.getSortOrder() == null) {
            banner.setSortOrder(0);
        }
        if (banner.getIsActive() == null) {
            banner.setIsActive(2);
        }

        int result = bannerMapper.insert(banner);
        if (result <= 0) {
            throw new BusinessException("创建Banner失败");
        }

        return banner.getId();
    }

    /**
     * 更新Banner
     */
    public boolean updateBanner(Banner banner) {
        if (banner == null || banner.getId() == null) {
            throw new BusinessException("Banner信息不能为空");
        }

        // 检查Banner是否存在
        Banner existingBanner = bannerMapper.selectById(banner.getId());
        if (existingBanner == null) {
            throw new BusinessException(404, "Banner不存在");
        }

        int result = bannerMapper.update(banner);
        return result > 0;
    }

    /**
     * 删除Banner
     */
    public boolean deleteBanner(Integer id) {
        if (id == null || id <= 0) {
            throw new BusinessException("Banner ID不能为空");
        }

        // 检查Banner是否存在
        Banner existingBanner = bannerMapper.selectById(id);
        if (existingBanner == null) {
            throw new BusinessException(404, "Banner不存在");
        }

        int result = bannerMapper.deleteById(id);
        return result > 0;
    }

    /**
     * 更新Banner状态
     */
    public boolean updateBannerStatus(Integer id, Boolean isActive) {
        if (id == null || id <= 0) {
            throw new BusinessException("Banner ID不能为空");
        }
        if (isActive == null) {
            throw new BusinessException("状态值不能为空");
        }

        Banner banner = bannerMapper.selectById(id);
        if (banner == null) {
            throw new BusinessException(404, "Banner不存在");
        }

        int result = bannerMapper.updateStatus(id, isActive);
        return result > 0;
    }
}
