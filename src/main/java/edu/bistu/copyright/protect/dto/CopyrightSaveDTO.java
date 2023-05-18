package edu.bistu.copyright.protect.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author Chanvo
 * @date 2023/5/13 23:44
 * @description
 */
@Data
public class CopyrightSaveDTO {

    @NotBlank(message = "图像标题不能为空")
    private String title;

    @NotBlank(message = "水印内容不能为空")
    private String content;

    @NotBlank(message = "图像不能为空")
    private String url;
}
