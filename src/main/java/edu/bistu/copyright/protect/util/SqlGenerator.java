package edu.bistu.copyright.protect.util;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @author ChanvoBook
 */
public class SqlGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://10.1.19.19:3306/patentprotection?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true", "root", "123123")
                .globalConfig(builder -> {
                    builder.author("Chanvo") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .outputDir("C:\\Users\\ChanvoBook\\IdeaProjects\\PatentProtection\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("edu.bistu.copyright.protect") // 设置父包名
//                            .moduleName("system") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "C:\\Users\\ChanvoBook\\IdeaProjects\\PatentProtection\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(StrategyConfig.Builder::build)
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
