package edu.bistu.copyright.protect.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import edu.bistu.copyright.protect.dto.CopyrightPageQuery;
import edu.bistu.copyright.protect.dto.CopyrightSaveDTO;
import edu.bistu.copyright.protect.entity.Copyright;
import edu.bistu.copyright.protect.service.ICopyrightService;
import edu.bistu.copyright.protect.vo.CopyrightVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Chanvo
 * @since 2023-05-08
 */
@Tag(name = "版权控制器")
@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/copyright")
public class CopyrightController {

    private final ICopyrightService copyrightService;

    @Operation(description = "分页条件查询")
    @GetMapping("/page/{index}/{limit}")
    public ResponseEntity<IPage<Copyright>> page(@PathVariable Integer index,
                                                 @PathVariable Integer limit,
                                                 @Nullable CopyrightPageQuery query) {
        return ResponseEntity.ok(copyrightService.page(StpUtil.getLoginIdAsInt(), index, limit, query));
    }

    @Valid
    @Operation(description = "获取图像水印")
    @GetMapping("/watermark")
    public ResponseEntity<String> watermark(@NotBlank(message = "请上传图像") String url) {
        return ResponseEntity.ok(copyrightService.getWatermarkBase64(url));
    }

    @Operation(description = "创建图像版权")
    @PostMapping("/create")
    public ResponseEntity<CopyrightVO> create(@RequestBody @Valid CopyrightSaveDTO input) {
        Copyright copyright = copyrightService.create(StpUtil.getLoginIdAsInt(), input);
        return ResponseEntity.ok(copyrightService.getByTxHash(StpUtil.getLoginIdAsInt(), copyright.getTxHash()));
    }

    @Operation(description = "从区块链获取图像版权")
    @GetMapping("/get/{txHash}")
    public ResponseEntity<CopyrightVO> getChainInfo(@PathVariable
                                                    @Size(min = 66, max = 66, message = "区块链地址必须为66位十六进制")
                                                    String txHash) {
        return ResponseEntity.ok(copyrightService.getByTxHash(StpUtil.getLoginIdAsInt(), txHash));
    }

    @Operation(description = "删除版权数据")
    @DeleteMapping("/del/{id}")
    public ResponseEntity<Boolean> del(@PathVariable Integer id) {
        return ResponseEntity.ok(copyrightService.del(StpUtil.getLoginIdAsInt(), id));
    }
}
