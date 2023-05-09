package edu.bistu.copyright.protect;

import edu.bistu.copyright.protect.contact.HelloWorld;
import org.fisco.bcos.sdk.v3.BcosSDK;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.client.protocol.response.BlockNumber;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;
import org.junit.jupiter.api.Test;

//@SpringBootTest
public class BcosSDKTest {
    // 获取配置文件路径
    public final String configFile = BcosSDKTest.class.getClassLoader().getResource("config.toml").getPath();

    @Test
    public void testClient() throws ContractException {
        // 初始化BcosSDK
        BcosSDK sdk = BcosSDK.build(configFile);
        // 为群组group初始化client
        Client client = sdk.getClient("group0");

        // 获取群组1的块高0
        BlockNumber blockNumber = client.getBlockNumber();

        // 向群组1部署HelloWorld合约
        CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().getCryptoKeyPair();
        HelloWorld helloWorld = HelloWorld.deploy(client, cryptoKeyPair);

        // 调用HelloWorld合约的get接口
        String getValue = helloWorld.get();

        // 调用HelloWorld合约的set接口
        TransactionReceipt receipt = helloWorld.set("Hello, fisco");
    }
}