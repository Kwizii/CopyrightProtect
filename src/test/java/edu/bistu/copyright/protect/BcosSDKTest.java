package edu.bistu.copyright.protect;

import edu.bistu.copyright.protect.common.HashType;
import edu.bistu.copyright.protect.contract.CopyrightRepository;
import edu.bistu.copyright.protect.util.HashUtil;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.v3.BcosSDK;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.CryptoType;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

//@SpringBootTest
@Slf4j
public class BcosSDKTest {
    // 获取配置文件路径
    public final String configFile = BcosSDKTest.class.getClassLoader().getResource("config.toml").getPath();

    public Client getClient() {
        BcosSDK sdk = BcosSDK.build(configFile);
        // 为群组group初始化client
        return sdk.getClient("group0");
    }

    public CryptoKeyPair loadAccount(String privateHexKey) {
        CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
        cryptoSuite.loadKeyPair(privateHexKey);
        return cryptoSuite.getCryptoKeyPair();
    }

    @Test
    public void config() {
        log.info(configFile);
    }

    @Test
    public void createUser() {
//        Client client = getClient();
        CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
        CryptoKeyPair cryptoKeyPair = cryptoSuite.getCryptoKeyPair();
        log.info(cryptoKeyPair.getAddress());
        log.info(cryptoKeyPair.getHexPrivateKey());
        log.info(cryptoKeyPair.getHexPublicKey());
    }

    @Test
    public void setUser() {
//        Client client = getClient();
        CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
        cryptoSuite.loadKeyPair("7db3a5b801fbba366d3667ce839495b3c89f388c83acb04e9ad98a28bdd72960");
        CryptoKeyPair keyPair = cryptoSuite.getCryptoKeyPair();
        // 0x21f339faf89f75d47f23107566b08f3668bb41b2
        log.info(keyPair.getAddress());
        log.info(keyPair.getHexPrivateKey());
        log.info(keyPair.getHexPublicKey());
    }

    @Test
    public void storeCopy() throws ContractException {
        Client client = getClient();
        CryptoKeyPair keyPair = loadAccount("2b53a0e3f05295add92a4a75761eb56eb626c417e92b1a1bf4bc38e4959974c0");
//        log.info(keyPair.getAddress());
//        CopyrightRepository copyrightRepository = CopyrightRepository.deploy(client, keyPair);
//        log.info(copyrightRepository.getContractAddress());
//        log.info(copyrightRepository._owner());
//        List<String> hashes = List.of(
//                HashUtil.string2HashHex("1", HashType.MD5),
//                HashUtil.string2HashHex("2", HashType.SHA1),
//                HashUtil.string2HashHex("3", HashType.SHA256));
//        TransactionReceipt transactionReceipt = copyrightRepository.requestStore(BigInteger.valueOf(1),
//                "test",
//                "ok",
//                hashes,
//                BigInteger.valueOf(Instant.now(Clock.system(ZoneId.of("Asia/Shanghai"))).getEpochSecond())
//        );
//        String copyAddress = transactionReceipt.getOutput();
//        log.info("Address: {}, Length: {}", copyAddress, copyAddress.length());
        CopyrightRepository copyrightRepository = CopyrightRepository.load("0xf5e297ed6556898b1fa58b352fce789e77eb156f", client, keyPair);
        CopyrightRepository.CopyrightSol copyrightSol = copyrightRepository.getCopyright("0x00000000000000000000000066856f4874da130a868e0aac01454f4743db386b");
//        CopyrightRepository.CopyrightSol copyrightSol = copyrightRepository.getCopyright(copyAddress);
        log.info("CopyrightSol: {} {} {} {} {}", copyrightSol.id, copyrightSol.content, copyrightSol.description, copyrightSol.hashes, copyrightSol.createTime);
    }
}