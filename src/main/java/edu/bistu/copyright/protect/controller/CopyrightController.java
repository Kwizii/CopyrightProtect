package edu.bistu.copyright.protect.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import edu.bistu.copyright.protect.dto.query.CopyrightPageQuery;
import edu.bistu.copyright.protect.entity.Copyright;
import edu.bistu.copyright.protect.service.ICopyrightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Chanvo
 * @since 2023-05-08
 */
@Tag(name = "版权控制器")
@Controller
@RequestMapping("/copyright")
public class CopyrightController {
    private final ICopyrightService copyrightService;

    public CopyrightController(ICopyrightService copyrightService) {
        this.copyrightService = copyrightService;
    }

    @Operation(description = "分页条件查询")
    @GetMapping("/page/{index}/{limit}")
    public ResponseEntity<IPage<Copyright>> page(@PathVariable Integer index,
                                                 @PathVariable Integer limit,
                                                 @Nullable CopyrightPageQuery query) {
        return ResponseEntity.ok(copyrightService. page(index, limit, query));
    }
}
