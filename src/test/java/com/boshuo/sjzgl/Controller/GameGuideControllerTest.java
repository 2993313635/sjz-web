package com.boshuo.sjzgl.Controller;

import com.boshuo.sjzgl.controller.GameGuideController;
import com.boshuo.sjzgl.model.dto.CreateGuideDTO;
import com.boshuo.sjzgl.model.dto.UpdateGuideDTO;
import com.boshuo.sjzgl.model.entity.GameGuide;
import com.boshuo.sjzgl.service.GameGuideService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameGuideController.class)
class GameGuideControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameGuideService gameGuideService;

    @Autowired
    private ObjectMapper objectMapper;

    private GameGuide mockGuide;

    @BeforeEach
    void setUp() {
        mockGuide = new GameGuide();
        mockGuide.setId(1);
        mockGuide.setTitle("测试攻略");
        mockGuide.setContent("测试内容");
        mockGuide.setCategoryId(1);
    }

    @Test
    void testGetGuide() throws Exception {
        Mockito.when(gameGuideService.getGuideById(1)).thenReturn(mockGuide);

        mockMvc.perform(get("/api/guides/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.title").value("测试攻略"));
    }

    @Test
    void testCreateGuide() throws Exception {
        CreateGuideDTO createDTO = new CreateGuideDTO();
        createDTO.setTitle("新攻略");
        createDTO.setContent("新内容");
        createDTO.setCategoryId(1);

        Mockito.when(gameGuideService.createGuide(any(GameGuide.class))).thenReturn(2);

        mockMvc.perform(post("/api/guides")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(2));
    }

    @Test
    void testUpdateGuide() throws Exception {
        UpdateGuideDTO updateDTO = new UpdateGuideDTO();
        updateDTO.setId(1);
        updateDTO.setTitle("更新后攻略");
        updateDTO.setContent("更新后内容");
        updateDTO.setCategoryId(1);

        Mockito.when(gameGuideService.updateGuide(any(GameGuide.class))).thenReturn(true);

        mockMvc.perform(put("/api/guides/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true));
    }

    @Test
    void testInvalidGuideId() throws Exception {
        // 测试ID小于等于0的情况
        mockMvc.perform(get("/api/guides/0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateGuideWithInvalidData() throws Exception {
        // 创建一个缺少必要字段的DTO
        CreateGuideDTO invalidDTO = new CreateGuideDTO();
        // title和content为空

        mockMvc.perform(post("/api/guides")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());
    }
}