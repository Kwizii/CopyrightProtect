package edu.bistu.copyright.protect.util;

import edu.bistu.copyright.protect.config.BeanContext;
import lombok.RequiredArgsConstructor;
import org.fisco.bcos.sdk.v3.BcosSDK;
import org.fisco.bcos.sdk.v3.client.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Chanvo
 * @date 2023/5/12 21:40
 * @description
 */
public class FiscoClient {

    private static Client client;


    public static synchronized Client instance()  {
        if (FiscoClient.client == null) {
            try {
                FiscoClient.client = ((ClientFactory) BeanContext.getBean(ClientFactory.class)).init();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return FiscoClient.client;
        }
        return FiscoClient.client;
    }
}

@Component
@RequiredArgsConstructor
class ClientFactory {

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