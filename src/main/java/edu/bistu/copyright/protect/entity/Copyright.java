package edu.bistu.copyright.protect.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

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
@Schema(description = "图像版权对象")
public class Copyright implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "ID")
    private Integer id;

    @Schema(description = "用户id")
    private Integer userId;

    @Schema(description = "存证交易哈希")
    private String txHash;

    @Schema(description = "原文件存储地址")
    private String originFileUrl;

    @Schema(description = "水印文件地址")
    private String watermarkFileUrl;

    @Schema(description = "图像标题")
    private String title;

    @Schema(description = "水印内容")
    private String content;

    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "数据库创建时间")
    private LocalDateTime createTime;
}
