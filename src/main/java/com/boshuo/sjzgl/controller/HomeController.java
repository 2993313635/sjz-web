package com.boshuo.sjzgl.controller;

import com.boshuo.sjzgl.model.dto.ApiResponse;
import com.boshuo.sjzgl.model.dto.HomeDataDTO;
import com.boshuo.sjzgl.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@CrossOrigin(origins = "*")
public class HomeController {

    @Autowired
    private HomeService homeService;

    /**
     * 获取首页数据
     */
    @GetMapping
    public ApiResponse<HomeDataDTO> getHomeData() {
        HomeDataDTO homeData = homeService.getHomeData();
        return ApiResponse.success(homeData);
    }
}