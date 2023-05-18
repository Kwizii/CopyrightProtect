package edu.bistu.copyright.protect.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author Chanvo
 * @date 2023/5/9 15:35
 * @description
 */
@Schema(description = "版权分页查询条件")
@Data
public class CopyrightPageQuery {

    @Schema(description = "根据水印信息搜索")
    private String content;

    @Schema(description = "根据图像标题搜索")
    private String title;

    @Schema(description = "根据存证哈希搜索")
    private String txHash;

    @Schema(description = "根据创建时间倒序/升序")
    private Boolean createTimeAsc = false;
}
