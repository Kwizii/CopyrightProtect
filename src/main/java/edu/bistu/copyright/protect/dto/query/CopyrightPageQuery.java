package edu.bistu.copyright.protect.dto.query;

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

    @Schema(description = "根据水印信息搜索",defaultValue = "test")
    private String content;

    @Schema(description = "根据区块链地址搜索",defaultValue = "0x123")
    private String chainAddress;
}
