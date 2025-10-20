package com.boshuo.sjzgl.service;

import com.boshuo.sjzgl.exception.BusinessException;
import com.boshuo.sjzgl.mapper.GameGuideMapper;
import com.boshuo.sjzgl.model.dto.GuideQueryDTO;
import com.boshuo.sjzgl.model.dto.PageResult;
import com.boshuo.sjzgl.model.entity.GameGuide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class GameGuideServiceTest {

    @Mock
    private GameGuideMapper gameGuideMapper;

    @InjectMocks
    private GameGuideService gameGuideService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetGuideById() {
        // 准备测试数据
        Integer guideId = 1;
        GameGuide mockGuide = new GameGuide();
        mockGuide.setId(guideId);
        mockGuide.setTitle("测试攻略");
        mockGuide.setViewCount(100);

        // 配置mock行为
        when(gameGuideMapper.selectById(guideId)).thenReturn(mockGuide);
        when(gameGuideMapper.incrementViewCount(guideId)).thenReturn(1);

        // 执行测试
        GameGuide result = gameGuideService.getGuideById(guideId);

        // 验证结果
        assertNotNull(result);
        assertEquals(guideId, result.getId());
        assertEquals(101, result.getViewCount()); // 验证浏览量增加了1
        verify(gameGuideMapper, times(1)).selectById(guideId);
        verify(gameGuideMapper, times(1)).incrementViewCount(guideId);
    }

    @Test
    void testGetGuideByIdNotFound() {
        // 配置mock行为，返回null表示找不到攻略
        when(gameGuideMapper.selectById(1)).thenReturn(null);

        // 验证是否抛出正确的异常
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            gameGuideService.getGuideById(1);
        });
        assertEquals(404, exception.getCode());
        assertEquals("攻略不存在", exception.getMessage());
    }

    @Test
    void testCreateGuide() {
        // 准备测试数据
        GameGuide guide = new GameGuide();
        guide.setTitle("新攻略");
        guide.setContent("攻略内容");
        guide.setCategoryId(1);

        // 配置mock行为
        when(gameGuideMapper.insert(any(GameGuide.class))).thenReturn(1);

        // 执行测试
        Integer guideId = gameGuideService.createGuide(guide);

        // 验证结果
        assertNotNull(guideId);
        assertEquals(guideId, guide.getId()); // 假设ID是在insert后设置的
        assertNotNull(guide.getCreatedAt());
        assertNotNull(guide.getUpdatedAt());
        assertEquals(0, guide.getViewCount());
        assertEquals(0, guide.getLikeCount());
        assertEquals("PUBLISHED", guide.getStatus());
        verify(gameGuideMapper, times(1)).insert(guide);
    }

    @Test
    void testGetGuides() {
        // 准备测试数据
        GuideQueryDTO queryDTO = new GuideQueryDTO();
        queryDTO.setPage(1);
        queryDTO.setSize(10);

        List<GameGuide> mockGuides = new ArrayList<>();
        mockGuides.add(new GameGuide());
        mockGuides.add(new GameGuide());

        // 配置mock行为
        when(gameGuideMapper.selectByCondition(queryDTO)).thenReturn(mockGuides);
        when(gameGuideMapper.countByCondition(queryDTO)).thenReturn(2);

        // 执行测试
        PageResult<GameGuide> result = gameGuideService.getGuides(queryDTO);

        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.getTotal());
        assertEquals(1, result.getPage());
        assertEquals(10, result.getSize());
        assertEquals(1, result.getPages());
        assertEquals(2, result.getList().size());
        verify(gameGuideMapper, times(1)).selectByCondition(queryDTO);
        verify(gameGuideMapper, times(1)).countByCondition(queryDTO);
    }
}