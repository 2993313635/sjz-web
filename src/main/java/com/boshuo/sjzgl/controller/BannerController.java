package com.boshuo.sjzgl.controller;

import com.boshuo.sjzgl.model.dto.ApiResponse;
import com.boshuo.sjzgl.model.entity.Banner;
import com.boshuo.sjzgl.service.BannerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banners")
@CrossOrigin(origins = "*")
@Validated
public class BannerController {

    @Autowired
    private BannerService bannerService;

    /**
     * 获取所有激活的Banner
     */
    @GetMapping
    public ApiResponse<List<Banner>> getActiveBanners() {
        List<Banner> banners = bannerService.getActiveBanners();
        return ApiResponse.success(banners);
    }

    /**
     * 获取Banner详情
     */
    @GetMapping("/{id}")
    public ApiResponse<Banner> getBanner(
            @PathVariable @Min(value = 1, message = "Banner ID必须大于0") Integer id) {
        Banner banner = bannerService.getBannerById(id);
        return ApiResponse.success(banner);
    }

    /**
     * 创建Banner
     */
    @PostMapping
    public ApiResponse<Integer> createBanner(@Valid @RequestBody Banner banner) {
        Integer bannerId = bannerService.createBanner(banner);
        return ApiResponse.success("Banner创建成功", bannerId);
    }

    /**
     * 更新Banner
     */
    @PutMapping("/{id}")
    public ApiResponse<Boolean> updateBanner(
            @PathVariable @Min(value = 1, message = "Banner ID必须大于0") Integer id,
            @Valid @RequestBody Banner banner) {

        banner.setId(id);
        boolean success = bannerService.updateBanner(banner);
        return ApiResponse.success("Banner更新成功", success);
    }

    /**
     * 删除Banner
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> deleteBanner(
            @PathVariable @Min(value = 1, message = "Banner ID必须大于0") Integer id) {
        boolean success = bannerService.deleteBanner(id);
        return ApiResponse.success("Banner删除成功", success);
    }

    /**
     * 更新Banner状态
     */
    @PutMapping("/{id}/status")
    public ApiResponse<Boolean> updateBannerStatus(
            @PathVariable @Min(value = 1, message = "Banner ID必须大于0") Integer id,
            @RequestParam Boolean isActive) {

        boolean success = bannerService.updateBannerStatus(id, isActive);
        return ApiResponse.success("Banner状态更新成功", success);
    }
}
