package edu.bistu.copyright.protect.config;

import org.fisco.bcos.sdk.v3.BcosSDK;
import org.fisco.bcos.sdk.v3.client.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Chanvo
 * @date 2023/5/19 10:47
 * @description
 */

@Configuration
public class BcosConfig {

    @Value("${fisco.default-group}")
    private String defaultGroup;

    @Value("${fisco.config-file}")
    private String configFile;

    @Bean
    public BcosSDK bcosSDK() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:" + configFile);
        return context.getBean(BcosSDK.class);
    }

    @Bean
    public Client bcosClient() {
        return bcosSDK().getClient(defaultGroup);
    }

}
