package edu.bistu.copyright.protect.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.bistu.copyright.protect.dto.CopyrightPageQuery;
import edu.bistu.copyright.protect.dto.CopyrightSaveDTO;
import edu.bistu.copyright.protect.entity.Copyright;
import edu.bistu.copyright.protect.vo.CopyrightVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Chanvo
 * @since 2023-05-08
 */
public interface ICopyrightService extends IService<Copyright> {

    IPage<Copyright> page(Integer uid, Integer index, Integer limit, CopyrightPageQuery query);

    Copyright create(Integer uid, CopyrightSaveDTO input);

    Boolean del(Integer uid, Integer id);

    CopyrightVO getByTxHash(Integer uid, String txHash);

    String getWatermarkBase64(String url);
}
