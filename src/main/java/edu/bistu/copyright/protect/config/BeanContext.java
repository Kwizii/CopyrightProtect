package edu.bistu.copyright.protect.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author Chanvo
 * @date 2023/5/13 9:35
 * @description
 */
@Component
public class BeanContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        edu.bistu.copyright.protect.config.BeanContext.applicationContext = applicationContext;
    }

    public static Object getBean(Class clazz) {
        return applicationContext.getBean(clazz);
    }

}