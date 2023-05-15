package edu.bistu.copyright.protect.util;

import lombok.RequiredArgsConstructor;
import org.fisco.bcos.sdk.v3.BcosSDK;
import org.fisco.bcos.sdk.v3.client.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class Fisco {

    @Value("${fisco.default-group}")
    private String defaultGroup;

    @Value("${fisco.config-file}")
    private String configFile;

    private final ResourceLoader resourceLoader;

    public Client init() throws IOException {
        String absConfigFile = resourceLoader.getResource("classpath:" + configFile).getFile().getAbsolutePath();
        BcosSDK sdk = BcosSDK.build(absConfigFile);
        // 为群组group初始化client
        return sdk.getClient(defaultGroup);
    }
}