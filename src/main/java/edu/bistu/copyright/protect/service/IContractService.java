package edu.bistu.copyright.protect.service;

import edu.bistu.copyright.protect.entity.Copyright;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;

import java.io.IOException;

/**
 * @author Chanvo
 * @date 2023/5/12 22:35
 * @description
 */
public interface IContractService {
    String deployContract(String privateKey) throws IOException, ContractException;

    String saveCopyright(String privateKey, String contractAddress, Copyright copyright);

    Copyright getCopyright(String privateKey, String contractAddress, String chainAddress) throws ContractException;
}
