package edu.bistu.copyright.protect;

import edu.bistu.copyright.protect.common.HashType;
import edu.bistu.copyright.protect.contract.CopyrightRepository;
import edu.bistu.copyright.protect.util.HashUtil;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.v3.BcosSDK;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.client.protocol.response.BlockNumber;
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
public class HashTest {

    @Test
    public void testHash() throws NoSuchAlgorithmException {
        String content = "1";
        log.info("{}\n{}\n{}", HashUtil.string2HashHex(content, HashType.MD5),
                HashUtil.string2HashHex(content, HashType.SHA1),
                HashUtil.string2HashHex(content, HashType.SHA256));
    }
}