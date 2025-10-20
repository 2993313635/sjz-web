package com.boshuo.sjzgl.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateNewsDTO {
    @NotBlank(message = "资讯标题不能为空")
    @Size(max = 200, message = "资讯标题长度不能超过200个字符")
    private String title;

    @Size(max = 500, message = "摘要长度不能超过500个字符")
    private String summary;

    @NotBlank(message = "资讯内容不能为空")
    private String content;

    @Size(max = 500, message = "封面图URL长度不能超过500个字符")
    private String coverImage;
}