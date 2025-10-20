package com.boshuo.sjzgl.service;

import com.boshuo.sjzgl.exception.BusinessException;
import com.boshuo.sjzgl.mapper.GameNewMapper;
import com.boshuo.sjzgl.model.dto.NewQueryDTO;
import com.boshuo.sjzgl.model.dto.PageResult;
import com.boshuo.sjzgl.model.entity.GameNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class GameNewService {

    @Autowired
    private GameNewMapper gameNewMapper;

    /**
     * 根据ID获取资讯详情
     */
    public GameNew getNewsById(Integer id) {
        if (id == null || id <= 0) {
            throw new BusinessException("资讯ID不能为空");
        }

        GameNew news = gameNewMapper.selectById(id);
        if (news == null) {
            throw new BusinessException(404, "资讯不存在");
        }

        return news;
    }

    /**
     * 条件分页查询资讯列表
     */
    public PageResult<GameNew> getNewsList(NewQueryDTO queryDTO) {
        if (queryDTO == null) {
            queryDTO = new NewQueryDTO();
        }

        // 设置默认值
        if (queryDTO.getPage() == null || queryDTO.getPage() <= 0) {
            queryDTO.setPage(1);
        }
        if (queryDTO.getSize() == null || queryDTO.getSize() <= 0) {
            queryDTO.setSize(10);
        }
        if (queryDTO.getSize() > 100) {
            queryDTO.setSize(100);
        }

        // 计算分页偏移量
        queryDTO.calculateOffset();

        // 查询数据
        List<GameNew> newsList = gameNewMapper.selectByCondition(queryDTO);
        Integer total = gameNewMapper.countByCondition(queryDTO);

        return new PageResult<>(newsList, total, queryDTO.getPage(), queryDTO.getSize());
    }

    /**
     * 创建资讯
     */
    public Integer createNews(GameNew news) {
        // 参数验证
        if (news == null) {
            throw new BusinessException("资讯信息不能为空");
        }
        if (news.getTitle() == null || news.getTitle().trim().isEmpty()) {
            throw new BusinessException("资讯标题不能为空");
        }
        if (news.getContent() == null || news.getContent().trim().isEmpty()) {
            throw new BusinessException("资讯内容不能为空");
        }

        news.setCreatedAt(new Date());

        int result = gameNewMapper.insert(news);
        if (result <= 0) {
            throw new BusinessException("创建资讯失败");
        }

        return news.getId();
    }

    /**
     * 更新资讯
     */
    public boolean updateNews(GameNew news) {
        if (news == null || news.getId() == null) {
            throw new BusinessException("资讯信息不能为空");
        }

        // 检查资讯是否存在
        GameNew existingNews = gameNewMapper.selectById(news.getId());
        if (existingNews == null) {
            throw new BusinessException(404, "资讯不存在");
        }

        int result = gameNewMapper.update(news);
        return result > 0;
    }

    /**
     * 删除资讯
     */
    public boolean deleteNews(Integer id) {
        if (id == null || id <= 0) {
            throw new BusinessException("资讯ID不能为空");
        }

        // 检查资讯是否存在
        GameNew existingNews = gameNewMapper.selectById(id);
        if (existingNews == null) {
            throw new BusinessException(404, "资讯不存在");
        }

        int result = gameNewMapper.deleteById(id);
        return result > 0;
    }

    /**
     * 获取最新资讯
     */
    public List<GameNew> getLatestNews(Integer limit) {
        if (limit == null || limit <= 0) {
            limit = 5; // 默认5条
        }
        if (limit > 50) {
            limit = 50;
        }

        return gameNewMapper.selectLatestNews(limit);
    }

    /**
     * 搜索资讯
     */
    public PageResult<GameNew> searchNews(String keyword, Integer page, Integer size) {
        NewQueryDTO queryDTO = new NewQueryDTO();
        queryDTO.setKeyword(keyword);
        queryDTO.setPage(page);
        queryDTO.setSize(size);

        return getNewsList(queryDTO);
    }
}