package com.oasys.feign.config;




import org.springframework.context.annotation.Bean;

import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
public class InternalFeginAPIConfig {
	@Bean
	public Decoder decode() {
		return new FeignInternalAPIDecoder();
	}

	@Bean
	public ErrorDecoder errorDecode() {
		return new FeignErrorDecoder();
	}

	@Bean
	public RequestInterceptor interceptor() {
		return new FeignClientInterceptor();
	}
	 @Bean
	    Logger.Level feignLoggerLevel() {
	        return Logger.Level.HEADERS;
	    }
}
