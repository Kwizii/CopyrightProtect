package edu.bistu.copyright.protect.service.impl;

import edu.bistu.copyright.protect.common.ServiceException;
import edu.bistu.copyright.protect.contract.CopyrightRepository;
import edu.bistu.copyright.protect.entity.Copyright;
import edu.bistu.copyright.protect.service.IContractService;
import edu.bistu.copyright.protect.util.FiscoClient;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.CryptoType;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * @author Chanvo
 * @date 2023/5/12 22:36
 * @description
 */
@Slf4j
@Service
public class ContractServiceImpl implements IContractService {

    private final Client client;

    public ContractServiceImpl() {
        this.client = FiscoClient.instance();
    }

    private CryptoKeyPair getKeypair(String privateKey) {
        CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
        return cryptoSuite.loadKeyPair(privateKey);
    }

    @Override
    public String deployContract(String privateKey) throws ContractException {
        CopyrightRepository copyrightRepository = CopyrightRepository.deploy(client, getKeypair(privateKey));
        return copyrightRepository.getContractAddress();
    }

    @Override
    public String saveCopyright(String privateKey, String contractAddress, Copyright copyright) {
        CopyrightRepository repository = CopyrightRepository.load(contractAddress, client, getKeypair(privateKey));
        TransactionReceipt receipt = repository.requestStore(
                BigInteger.valueOf(copyright.getId()),
                copyright.getDescription(),
                copyright.getContent(),
                List.of(copyright.getMd5Hash(), copyright.getSha1Hash(), copyright.getSha256Hash()),
                BigInteger.valueOf(Instant.now(Clock.system(ZoneId.of("Asia/Shanghai"))).getEpochSecond()));
        String copyAddress = receipt.getOutput();
        log.info("Copyright Saved In Blockchain: {}", copyAddress);
        return copyAddress;
    }

    @Override
    public Copyright getCopyright(String privateKey, String contractAddress, String copyAddress) throws ContractException {
        CopyrightRepository repository = CopyrightRepository.load(contractAddress, client, getKeypair(privateKey));
        CopyrightRepository.CopyrightSol copyrightSol = repository.getCopyright(copyAddress);
        if (copyrightSol.id.equals(BigInteger.ZERO)) {
            throw new ServiceException("对象不存在");
        }
        return Copyright.builder()
                .id(copyrightSol.id.intValue())
                .content(copyrightSol.content)
                .description(copyrightSol.description)
                .md5Hash(copyrightSol.hashes.get(0))
                .sha1Hash(copyrightSol.hashes.get(1))
                .sha256Hash(copyrightSol.hashes.get(2))
                .createTime(LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(copyrightSol.createTime.longValue()),
                        ZoneId.of("Asia/Shanghai")
                ))
                .build();
    }
}
