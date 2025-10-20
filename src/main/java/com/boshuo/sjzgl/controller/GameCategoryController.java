package com.boshuo.sjzgl.controller;

import com.boshuo.sjzgl.model.dto.ApiResponse;
import com.boshuo.sjzgl.model.entity.GameCategory;
import com.boshuo.sjzgl.service.GameCategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "*")
@Validated
public class GameCategoryController {

    @Autowired
    private GameCategoryService gameCategoryService;

    /**
     * 获取所有分类
     */
    @GetMapping
    public ApiResponse<List<GameCategory>> getCategories() {
        List<GameCategory> categories = gameCategoryService.getAllCategories();
        return ApiResponse.success(categories);
    }

    /**
     * 获取活跃分类
     */
    @GetMapping("/active")
    public ApiResponse<List<GameCategory>> getActiveCategories() {
        List<GameCategory> categories = gameCategoryService.getActiveCategories();
        return ApiResponse.success(categories);
    }

    /**
     * 获取分类详情
     */
    @GetMapping("/{id}")
    public ApiResponse<GameCategory> getCategory(
            @PathVariable @Min(value = 1, message = "分类ID必须大于0") Integer id) {
        GameCategory category = gameCategoryService.getCategoryById(id);
        return ApiResponse.success(category);
    }

    /**
     * 创建分类
     */
    @PostMapping
    public ApiResponse<Integer> createCategory(@Valid @RequestBody GameCategory category) {
        Integer categoryId = gameCategoryService.createCategory(category);
        return ApiResponse.success("分类创建成功", categoryId);
    }

    /**
     * 更新分类
     */
    @PutMapping("/{id}")
    public ApiResponse<Boolean> updateCategory(
            @PathVariable @Min(value = 1, message = "分类ID必须大于0") Integer id,
            @Valid @RequestBody GameCategory category) {

        category.setId(id);
        boolean success = gameCategoryService.updateCategory(category);
        return ApiResponse.success("分类更新成功", success);
    }

    /**
     * 删除分类
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> deleteCategory(
            @PathVariable @Min(value = 1, message = "分类ID必须大于0") Integer id) {
        boolean success = gameCategoryService.deleteCategory(id);
        return ApiResponse.success("分类删除成功", success);
    }

    /**
     * 更新分类状态
     */
    @PutMapping("/{id}/status")
    public ApiResponse<Boolean> updateCategoryStatus(
            @PathVariable @Min(value = 1, message = "分类ID必须大于0") Integer id,
            @RequestParam String status) {

        boolean success = gameCategoryService.updateCategoryStatus(id, status);
        return ApiResponse.success("分类状态更新成功", success);
    }
}