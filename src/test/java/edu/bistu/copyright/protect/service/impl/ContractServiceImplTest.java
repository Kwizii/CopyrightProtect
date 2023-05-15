package edu.bistu.copyright.protect.service.impl;

import edu.bistu.copyright.protect.entity.Copyright;
import edu.bistu.copyright.protect.service.IContractService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Chanvo
 * @date 2023/5/15 13:41
 * @description
 */
@Slf4j
@SpringBootTest
class ContractServiceImplTest {

    @Resource
    private IContractService contractService;

    @Test
    public void getCopyright() throws ContractException {
        Copyright copyright = contractService.getCopyright("2b53a0e3f05295add92a4a75761eb56eb626c417e92b1a1bf4bc38e4959974c0",
                "0xf5e297ed6556898b1fa58b352fce789e77eb156f",
                "0x00000000000000000000000066856f4874da130a868e0aac01454f4743db386b");
        log.info("{}", copyright);
    }

}