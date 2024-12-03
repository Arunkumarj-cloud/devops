package com.oasys.uppcl_user__master_management;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oasys.uppcl_user__master_management.response.AttributeConstant;
import com.oasys.uppcl_user__master_management.response.ResponseMessageConstant;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Autowired
	private MessageSource messageSource;
	@Bean
    public Docket productApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.oasys"))
//                .paths(PathSelectors.any())
//                .build()
//                .apiInfo(metaData());
    	
    	ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder.name("X-Authorization")                 // name of header
                         .modelRef(new ModelRef("string"))
                         .parameterType("header")               // type - header
                         .defaultValue("")        // based64 of - zone:mypassword
                         .required(false)                // for compulsory
                         .build();
        
        List<springfox.documentation.service.Parameter> aParameters = new ArrayList<>();
        aParameters.add(aParameterBuilder.build());   
		
		
        return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.basePackage("com.oasys"))
            .paths(PathSelectors.regex("/.*"))
            .build().pathMapping("").globalOperationParameters(aParameters);
    }
    private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                "Master Management",
                "Oasys API",
                "1.0",
                "Terms of service",
                new Contact("", "http://www.oasys.co/index.html",""),
               "",
                "");
        return apiInfo;
    }
    
    
	
	@PostConstruct
	public void init()
	{
		for(ResponseMessageConstant errorCode:ResponseMessageConstant.values())
		{
			errorCode.setMessageSource(messageSource);
		}
	}
	@PostConstruct
	public void init1()
	{
		for(AttributeConstant attributeConstant:AttributeConstant.values())
		{
			attributeConstant.setMessageSource(messageSource);
		}
	}
}