package edu.bistu.copyright.protect.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.bistu.copyright.protect.dto.query.CopyrightPageQuery;
import edu.bistu.copyright.protect.entity.Copyright;
import edu.bistu.copyright.protect.mapper.CopyrightMapper;
import edu.bistu.copyright.protect.service.ICopyrightService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Chanvo
 * @since 2023-05-08
 */
@Service
public class CopyrightServiceImpl extends ServiceImpl<CopyrightMapper, Copyright> implements ICopyrightService {

    @Override
    public IPage<Copyright> page(Integer index, Integer limit, CopyrightPageQuery query) {
        IPage<Copyright> page = new Page<>(index, limit);
        if (query == null) {
            return page(page);
        }
        LambdaQueryWrapper<Copyright> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(query.getContent()),
                        Copyright::getContent, query.getContent())
                .or()
                .eq(StringUtils.hasLength(query.getChainAddress())
                        , Copyright::getChainAddress, query.getChainAddress());
        return page(page, wrapper);
    }
}
