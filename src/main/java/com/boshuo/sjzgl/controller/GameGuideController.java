package com.boshuo.sjzgl.controller;

import com.boshuo.sjzgl.model.dto.*;
import com.boshuo.sjzgl.model.entity.GameGuide;
import com.boshuo.sjzgl.service.GameGuideService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guides")
@CrossOrigin(origins = "*")
@Validated
public class GameGuideController {

    @Autowired
    private GameGuideService gameGuideService;

    /**
     * 获取攻略详情
     */
    @GetMapping("/{id}")
    public ApiResponse<GameGuide> getGuide(
            @PathVariable @Min(value = 1, message = "攻略ID必须大于0") Integer id) {
        GameGuide guide = gameGuideService.getGuideById(id);
        return ApiResponse.success(guide);
    }

    /**
     * 分页查询攻略列表
     */
    @GetMapping
    public ApiResponse<PageResult<GameGuide>> getGuides(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(defaultValue = "time") String sortBy,
            @RequestParam(defaultValue = "1") @Min(1) Integer page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer size) {

        GuideQueryDTO queryDTO = new GuideQueryDTO();
        queryDTO.setKeyword(keyword);
        queryDTO.setCategoryId(categoryId);
        queryDTO.setSortBy(sortBy);
        queryDTO.setPage(page);
        queryDTO.setSize(size);

        PageResult<GameGuide> result = gameGuideService.getGuides(queryDTO);
        return ApiResponse.success(result);
    }

    /**
     * 创建攻略
     */
    @PostMapping
    public ApiResponse<Integer> createGuide(@Valid @RequestBody CreateGuideDTO createDTO) {
        // 转换为实体对象
        GameGuide guide = new GameGuide();
        guide.setTitle(createDTO.getTitle());
        guide.setContent(createDTO.getContent());
        guide.setCategoryId(createDTO.getCategoryId());
        guide.setCoverImage(createDTO.getCoverImage());
        guide.setVideoUrl(createDTO.getVideoUrl());
        Integer guideId = gameGuideService.createGuide(guide);
        return ApiResponse.success("攻略创建成功", guideId);
    }

    /**
     * 更新攻略
     */
    @PutMapping("/{id}")
    public ApiResponse<Boolean> updateGuide(
            @PathVariable @Min(value = 1, message = "攻略ID必须大于0") Integer id,
            @Valid @RequestBody UpdateGuideDTO updateDTO) {

        // 验证路径ID和请求体ID一致
        if (!id.equals(updateDTO.getId())) {
            return ApiResponse.error("路径ID和请求体ID不一致", 400);
        }

        GameGuide guide = new GameGuide();
        guide.setId(updateDTO.getId());
        guide.setTitle(updateDTO.getTitle());
        guide.setContent(updateDTO.getContent());
        guide.setCategoryId(updateDTO.getCategoryId());
        guide.setCoverImage(updateDTO.getCoverImage());
        guide.setVideoUrl(updateDTO.getVideoUrl());

        boolean success = gameGuideService.updateGuide(guide);
        return ApiResponse.success("攻略更新成功", success);
    }

    /**
     * 删除攻略
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> deleteGuide(
            @PathVariable @Min(value = 1, message = "攻略ID必须大于0") Integer id) {
        boolean success = gameGuideService.deleteGuide(id);
        return ApiResponse.success("攻略删除成功", success);
    }

    /**
     * 点赞攻略
     */
    @PostMapping("/{id}/like")
    public ApiResponse<Boolean> likeGuide(
            @PathVariable @Min(value = 1, message = "攻略ID必须大于0") Integer id) {
        boolean success = gameGuideService.likeGuide(id);
        return ApiResponse.success("点赞成功", success);
    }

    /**
     * 获取热门攻略
     */
    @GetMapping("/hot")
    public ApiResponse<List<GameGuide>> getHotGuides(
            @RequestParam(defaultValue = "8") @Min(1) @Max(50) Integer limit) {
        List<GameGuide> guides = gameGuideService.getHotGuides(limit);
        return ApiResponse.success(guides);
    }

    /**
     * 获取最新攻略
     */
    @GetMapping("/latest")
    public ApiResponse<List<GameGuide>> getLatestGuides(
            @RequestParam(defaultValue = "6") @Min(1) @Max(50) Integer limit) {
        List<GameGuide> guides = gameGuideService.getLatestGuides(limit);
        return ApiResponse.success(guides);
    }

    /**
     * 根据分类获取攻略
     */
    @GetMapping("/category/{categoryId}")
    public ApiResponse<PageResult<GameGuide>> getGuidesByCategory(
            @PathVariable @Min(1) Integer categoryId,
            @RequestParam(defaultValue = "1") @Min(1) Integer page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer size) {

        PageResult<GameGuide> result = gameGuideService.getGuidesByCategory(categoryId, page, size);
        return ApiResponse.success(result);
    }

    /**
     * 搜索攻略
     */
    @GetMapping("/search")
    public ApiResponse<PageResult<GameGuide>> searchGuides(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") @Min(1) Integer page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer size) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return ApiResponse.error("搜索关键词不能为空", 400);
        }

        PageResult<GameGuide> result = gameGuideService.searchGuides(keyword.trim(), page, size);
        return ApiResponse.success(result);
    }
}
