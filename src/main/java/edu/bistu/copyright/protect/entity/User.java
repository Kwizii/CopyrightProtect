package edu.bistu.copyright.protect.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author Chanvo
 * @since 2023-05-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "User对象")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "区块链用户私钥")
    private String privateKey;

    @Schema(description = "区块链用户公钥")
    private String pubKey;

    @Schema(description = "区块链用户地址")
    private String userChainAddress;

    @Schema(description = "版权库智能合约地址")
    private String contractChainAddress;
}
