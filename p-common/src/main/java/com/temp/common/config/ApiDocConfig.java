package com.temp.common.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author yangmin
 * @date 2018/8/15
 * @desc
 */
@Configuration
@EnableSwagger2
public class ApiDocConfig {
    @Bean
    public Docket adminApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Admin API")
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .paths(paths())
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);
    }

    private Predicate<String> paths(){
        return PathSelectors.regex("^/(?!error).*$");
    }

    private ApiInfo apiInfo(){
        Contact contact = new Contact("yjy个人所有", "https://github.com/yjysanshu", "1109563194@qq.com");
        return new ApiInfoBuilder()
                .title("测评后端系统")
                .description("后台API文档")
                .contact(contact)
                .version("1.0")
                .build();
    }
}
