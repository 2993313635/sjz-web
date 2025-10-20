package com.boshuo.sjzgl.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateGuideDTO {
    @NotNull(message = "攻略ID不能为空")
    private Integer id;

    @NotBlank(message = "攻略标题不能为空")
    private String title;

    @NotBlank(message = "攻略内容不能为空")
    private String content;

    @NotNull(message = "分类ID不能为空")
    private Integer categoryId;

    @Size(max = 500, message = "封面图URL长度不能超过500个字符")
    private String coverImage;

    @Size(max = 500, message = "视频URL长度不能超过500个字符")
    private String videoUrl;

}