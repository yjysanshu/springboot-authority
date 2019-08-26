package com.temp.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * api页面 /swagger-ui.html
 * @author yuanjy
 * @date 2018/8/15
 */
@Configuration
@EnableSwagger2
public class ApiDocConfig {
    @Bean
    public Docket adminApi(){
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        ticketPar.name("X-TOKEN").description("token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .defaultValue("")
                .required(true)
                .build();
        pars.add(ticketPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Admin API")
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .paths(PathSelectors.regex("^/(?!error).*$"))
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalOperationParameters(pars);
    }

    private ApiInfo apiInfo(){
        Contact contact = new Contact("yjy个人所有", "https://github.com/yjysanshu", "1109563194@qq.com");
        return new ApiInfoBuilder()
                .title("简单权限管理系统")
                .description("后台API文档")
                .contact(contact)
                .version("1.0")
                .build();
    }
}
