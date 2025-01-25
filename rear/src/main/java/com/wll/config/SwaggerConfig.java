package com.wll.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @time 2025/1/20 15:15 周一
 */
// http://localhost/swagger-ui.html
@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                // swagger 扫描包配置
                // select()获取Docket中的选择器，返回ApiSelectorBuilder构造选择器，如扫描扫描包的注解
                .select()
                /**
                 * requestHandlerSelectors：请求处理选择器
                 * basePackage()：扫描指定包下的所有接口
                 * any()：扫描所有的包
                 * none()：不扫描
                 * withClassAnnotation()：扫描指定类上的注解，参数是一个注解的放射对象
                 * withMethodAnnotation()：扫描方法上的注解
                 */
                // 指定扫描器扫描的规则（断言）
                .apis(RequestHandlerSelectors.basePackage("com.wll.controller"))
                /**
                 * pathSelectors：路径选择器，过滤路径
                 * ang()：选择所有路径
                 * none()：都不选择
                 * ant()：选择指定路径
                 * regex()：正则表达式
                 */
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("big-event")
                .description("接口文档")
                .version("v1")
                .contact(new Contact("吴良良", "xyz", "888"))
                .build();
    }
}
