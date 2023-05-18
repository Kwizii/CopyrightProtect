package edu.bistu.copyright.protect.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * @author Chanvo
 * @date 2023/5/16 19:51
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CopyrightVO {

    private Integer id;

    private Integer userId;

    private String txHash;

    private BigInteger blockNum;

    private String originFileUrl;

    private String watermarkFileUrl;

    private String title;

    private String content;

    private String md5Hash;

    private String sha1Hash;

    private String sha256Hash;

    private LocalDateTime createTime;

    private LocalDateTime chainCreateTime;

}
