package edu.bistu.copyright.protect.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Chanvo
 * @date 2023/5/12 22:33
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "用户回显类")
public class UserVO {
    @Schema(description = "用户ID")
    Integer id;
    @Schema(description = "用户名")
    String username;
    @Schema(description = "用户区块链公钥")
    String pubKey;
    @Schema(description = "用户区块链地址")
    String userChainAddress;
    @Schema(description = "用户版权库合约地址")
    String contractChainAddress;
    @Schema(description = "认证TOKEN")
    String accessToken;
}
