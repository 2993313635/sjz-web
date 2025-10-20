package com.boshuo.sjzgl.service;

import com.boshuo.sjzgl.exception.BusinessException;
import com.boshuo.sjzgl.mapper.GameCategoryMapper;
import com.boshuo.sjzgl.model.entity.GameCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class GameCategoryService {

    @Autowired
    private GameCategoryMapper gameCategoryMapper;

    /**
     * 获取所有分类
     */
    public List<GameCategory> getAllCategories() {
        return gameCategoryMapper.selectAll();
    }

    /**
     * 获取活跃分类
     */
    public List<GameCategory> getActiveCategories() {
        return gameCategoryMapper.selectActiveCategories();
    }

    /**
     * 根据ID获取分类
     */
    public GameCategory getCategoryById(Integer id) {
        if (id == null || id <= 0) {
            throw new BusinessException("分类ID不能为空");
        }

        GameCategory category = gameCategoryMapper.selectById(id);
        if (category == null) {
            throw new BusinessException(404, "分类不存在");
        }

        return category;
    }

    /**
     * 创建分类
     */
    public Integer createCategory(GameCategory category) {
        // 参数验证
        if (category == null) {
            throw new BusinessException("分类信息不能为空");
        }
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new BusinessException("分类名称不能为空");
        }

        // 检查分类名称是否重复
        List<GameCategory> existingCategories = gameCategoryMapper.selectAll();
        boolean nameExists = existingCategories.stream()
                .anyMatch(c -> c.getName().equals(category.getName()));
        if (nameExists) {
            throw new BusinessException("分类名称已存在");
        }

        // 设置默认值
        if (category.getStatus() == null) {
            category.setStatus("A");
        }

        category.setCreatedAt(new Date());
        category.setUpdatedAt(new Date());

        int result = gameCategoryMapper.insert(category);
        if (result <= 0) {
            throw new BusinessException("创建分类失败");
        }

        return category.getId();
    }

    /**
     * 更新分类
     */
    public boolean updateCategory(GameCategory category) {
        if (category == null || category.getId() == null) {
            throw new BusinessException("分类信息不能为空");
        }

        // 检查分类是否存在
        GameCategory existingCategory = gameCategoryMapper.selectById(category.getId());
        if (existingCategory == null) {
            throw new BusinessException(404, "分类不存在");
        }

        // 检查分类名称是否重复（排除自身）
        List<GameCategory> existingCategories = gameCategoryMapper.selectAll();
        boolean nameExists = existingCategories.stream()
                .anyMatch(c -> c.getName().equals(category.getName()) && !c.getId().equals(category.getId()));
        if (nameExists) {
            throw new BusinessException("分类名称已存在");
        }

        category.setUpdatedAt(new Date());
        int result = gameCategoryMapper.update(category);

        return result > 0;
    }

    /**
     * 删除分类
     */
    public boolean deleteCategory(Integer id) {
        if (id == null || id <= 0) {
            throw new BusinessException("分类ID不能为空");
        }

        // 检查分类是否存在
        GameCategory existingCategory = gameCategoryMapper.selectById(id);
        if (existingCategory == null) {
            throw new BusinessException(404, "分类不存在");
        }

        // 检查分类下是否有攻略（这里需要扩展Mapper来检查关联数据）
        // 暂时先删除，后续可以添加关联检查

        int result = gameCategoryMapper.deleteById(id);
        return result > 0;
    }

    /**
     * 更新分类状态
     */
    public boolean updateCategoryStatus(Integer id, String status) {
        if (id == null || id <= 0) {
            throw new BusinessException("分类ID不能为空");
        }
        if (status == null || (!status.equals("A") && !status.equals("O"))) {
            throw new BusinessException("状态值不合法");
        }

        GameCategory category = gameCategoryMapper.selectById(id);
        if (category == null) {
            throw new BusinessException(404, "分类不存在");
        }

        category.setStatus(status);
        category.setUpdatedAt(new Date());

        int result = gameCategoryMapper.update(category);
        return result > 0;
    }
}