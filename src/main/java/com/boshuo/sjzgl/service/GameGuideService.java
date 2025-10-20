package com.boshuo.sjzgl.service;

import com.boshuo.sjzgl.exception.BusinessException;
import com.boshuo.sjzgl.mapper.GameGuideMapper;
import com.boshuo.sjzgl.model.dto.GuideQueryDTO;
import com.boshuo.sjzgl.model.dto.PageResult;
import com.boshuo.sjzgl.model.entity.GameGuide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class GameGuideService {

    @Autowired
    private GameGuideMapper gameGuideMapper;

    /**
     * 根据ID获取攻略详情（同时增加浏览量）
     */
    public GameGuide getGuideById(Integer id) {
        if (id == null || id <= 0) {
            throw new BusinessException("攻略ID不能为空");
        }

        GameGuide guide = gameGuideMapper.selectById(id);
        if (guide == null) {
            throw new BusinessException(404, "攻略不存在");
        }

        // 增加浏览量
        gameGuideMapper.incrementViewCount(id);
        guide.setViewCount(guide.getViewCount() + 1);

        return guide;
    }

    /**
     * 条件分页查询攻略列表
     */
    public PageResult<GameGuide> getGuides(GuideQueryDTO queryDTO) {
        if (queryDTO == null) {
            queryDTO = new GuideQueryDTO();
        }

        // 设置默认值
        if (queryDTO.getPage() == null || queryDTO.getPage() <= 0) {
            queryDTO.setPage(1);
        }
        if (queryDTO.getSize() == null || queryDTO.getSize() <= 0) {
            queryDTO.setSize(10);
        }
        if (queryDTO.getSize() > 100) {
            queryDTO.setSize(100); // 限制每页最大100条
        }

        // 计算分页偏移量
        queryDTO.calculateOffset();

        // 查询数据
        List<GameGuide> guides = gameGuideMapper.selectByCondition(queryDTO);
        Integer total = gameGuideMapper.countByCondition(queryDTO);

        return new PageResult<>(guides, total, queryDTO.getPage(), queryDTO.getSize());
    }

    /**
     * 创建攻略
     */
    public Integer createGuide(GameGuide guide) {
        // 参数验证
        if (guide == null) {
            throw new BusinessException("攻略信息不能为空");
        }
        if (guide.getTitle() == null || guide.getTitle().trim().isEmpty()) {
            throw new BusinessException("攻略标题不能为空");
        }
        if (guide.getContent() == null || guide.getContent().trim().isEmpty()) {
            throw new BusinessException("攻略内容不能为空");
        }

        // 设置默认值
        if (guide.getViewCount() == null) {
            guide.setViewCount(0);
        }
        if (guide.getLikeCount() == null) {
            guide.setLikeCount(0);
        }
        if (guide.getStatus() == null) {
            guide.setStatus("A");
        }

        guide.setCreatedAt(new Date());
        guide.setUpdatedAt(new Date());

        int result = gameGuideMapper.insert(guide);
        if (result <= 0) {
            throw new BusinessException("创建攻略失败");
        }

        return guide.getId();
    }

    /**
     * 更新攻略
     */
    public boolean updateGuide(GameGuide guide) {
        if (guide == null || guide.getId() == null) {
            throw new BusinessException("攻略信息不能为空");
        }

        // 检查攻略是否存在
        GameGuide existingGuide = gameGuideMapper.selectById(guide.getId());
        if (existingGuide == null) {
            throw new BusinessException(404, "攻略不存在");
        }

        guide.setUpdatedAt(new Date());
        int result = gameGuideMapper.update(guide);

        return result > 0;
    }

    /**
     * 删除攻略
     */
    public boolean deleteGuide(Integer id) {
        if (id == null || id <= 0) {
            throw new BusinessException("攻略ID不能为空");
        }

        // 检查攻略是否存在
        GameGuide existingGuide = gameGuideMapper.selectById(id);
        if (existingGuide == null) {
            throw new BusinessException(404, "攻略不存在");
        }

        int result = gameGuideMapper.deleteById(id);
        return result > 0;
    }

    /**
     * 点赞攻略
     */
    public boolean likeGuide(Integer id) {
        if (id == null || id <= 0) {
            throw new BusinessException("攻略ID不能为空");
        }

        // 检查攻略是否存在
        GameGuide existingGuide = gameGuideMapper.selectById(id);
        if (existingGuide == null) {
            throw new BusinessException(404, "攻略不存在");
        }

        int result = gameGuideMapper.incrementLikeCount(id);
        return result > 0;
    }

    /**
     * 获取热门攻略（按浏览量排序）
     */
    public List<GameGuide> getHotGuides(Integer limit) {
        if (limit == null || limit <= 0) {
            limit = 8; // 默认8条
        }
        if (limit > 50) {
            limit = 50; // 限制最大50条
        }

        return gameGuideMapper.selectHotGuides(limit);
    }

    /**
     * 获取最新攻略
     */
    public List<GameGuide> getLatestGuides(Integer limit) {
        if (limit == null || limit <= 0) {
            limit = 6; // 默认6条
        }
        if (limit > 50) {
            limit = 50; // 限制最大50条
        }

        return gameGuideMapper.selectLatestGuides(limit);
    }

    /**
     * 根据分类获取攻略
     */
    public PageResult<GameGuide> getGuidesByCategory(Integer categoryId, Integer page, Integer size) {
        GuideQueryDTO queryDTO = new GuideQueryDTO();
        queryDTO.setCategoryId(categoryId);
        queryDTO.setPage(page);
        queryDTO.setSize(size);

        return getGuides(queryDTO);
    }

    /**
     * 搜索攻略
     */
    public PageResult<GameGuide> searchGuides(String keyword, Integer page, Integer size) {
        GuideQueryDTO queryDTO = new GuideQueryDTO();
        queryDTO.setKeyword(keyword);
        queryDTO.setPage(page);
        queryDTO.setSize(size);

        return getGuides(queryDTO);
    }
}
