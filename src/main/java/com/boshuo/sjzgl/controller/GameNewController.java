package com.boshuo.sjzgl.controller;

import com.boshuo.sjzgl.model.dto.ApiResponse;
import com.boshuo.sjzgl.model.dto.CreateNewsDTO;
import com.boshuo.sjzgl.model.dto.NewQueryDTO;
import com.boshuo.sjzgl.model.dto.PageResult;
import com.boshuo.sjzgl.model.entity.GameNew;
import com.boshuo.sjzgl.service.GameNewService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
@CrossOrigin(origins = "*")
@Validated
public class GameNewController {

    @Autowired
    private GameNewService gameNewService;

    /**
     * 获取资讯详情
     */
    @GetMapping("/{id}")
    public ApiResponse<GameNew> getNews(
            @PathVariable @Min(value = 1, message = "资讯ID必须大于0") Integer id) {
        GameNew news = gameNewService.getNewsById(id);
        return ApiResponse.success(news);
    }

    /**
     * 分页查询资讯列表
     */
    @GetMapping
    public ApiResponse<PageResult<GameNew>> getNews(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") @Min(1) Integer page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer size) {

        NewQueryDTO queryDTO = new NewQueryDTO();
        queryDTO.setKeyword(keyword);
        queryDTO.setPage(page);
        queryDTO.setSize(size);

        PageResult<GameNew> result = gameNewService.getNewsList(queryDTO);
        return ApiResponse.success(result);
    }

    /**
     * 创建资讯
     */
    @PostMapping
    public ApiResponse<Integer> createNews(@Valid @RequestBody CreateNewsDTO createDTO) {
        GameNew news = new GameNew();
        news.setTitle(createDTO.getTitle());
        news.setSummary(createDTO.getSummary());
        news.setContent(createDTO.getContent());
        news.setCoverImage(createDTO.getCoverImage());

        Integer newsId = gameNewService.createNews(news);
        return ApiResponse.success("资讯创建成功", newsId);
    }

    /**
     * 更新资讯
     */
    @PutMapping("/{id}")
    public ApiResponse<Boolean> updateNews(
            @PathVariable @Min(value = 1, message = "资讯ID必须大于0") Integer id,
            @Valid @RequestBody CreateNewsDTO updateDTO) {

        GameNew news = new GameNew();
        news.setId(id);
        news.setTitle(updateDTO.getTitle());
        news.setSummary(updateDTO.getSummary());
        news.setContent(updateDTO.getContent());
        news.setCoverImage(updateDTO.getCoverImage());

        boolean success = gameNewService.updateNews(news);
        return ApiResponse.success("资讯更新成功", success);
    }

    /**
     * 删除资讯
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> deleteNews(
            @PathVariable @Min(value = 1, message = "资讯ID必须大于0") Integer id) {
        boolean success = gameNewService.deleteNews(id);
        return ApiResponse.success("资讯删除成功", success);
    }

    /**
     * 获取最新资讯
     */
    @GetMapping("/latest")
    public ApiResponse<List<GameNew>> getLatestNews(
            @RequestParam(defaultValue = "5") @Min(1) @Max(50) Integer limit) {
        List<GameNew> newsList = gameNewService.getLatestNews(limit);
        return ApiResponse.success(newsList);
    }

    /**
     * 搜索资讯
     */
    @GetMapping("/search")
    public ApiResponse<PageResult<GameNew>> searchNews(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") @Min(1) Integer page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer size) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return ApiResponse.error("搜索关键词不能为空", 400);
        }

        PageResult<GameNew> result = gameNewService.searchNews(keyword.trim(), page, size);
        return ApiResponse.success(result);
    }
}
