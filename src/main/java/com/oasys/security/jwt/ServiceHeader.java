package com.oasys.security.jwt;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;


@Component
public class ServiceHeader {

	@Autowired
	private LoadBalancerClient loadBalancer;
	
	public List<ClientHttpRequestInterceptor> getHeader(String token) {
		List<ClientHttpRequestInterceptor> interceptors = new CopyOnWriteArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new HeaderRequestInterceptor("crossOrigin", "true"));
		interceptors.add(new HeaderRequestInterceptor("Access-Control-Allow-Origin", "*"));
		interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
		interceptors.add(
				new HeaderRequestInterceptor("Access-Control-Allow-Methods", "HEAD,OPTIONS,GET,POST,PUT,PATCH,DELETE"));
		interceptors.add(new HeaderRequestInterceptor("Access-Control-Allow-Headers",
				"X-Requested-With,Origin,Content-Type, Accept, x-device-user-agent, Content-Type"));
		interceptors.add(new HeaderRequestInterceptor("Access-Control-Allow-Credentials", "true"));
		interceptors.add(new HeaderRequestInterceptor("X-Authorization", token));

		return interceptors;
	}
	
	
	public URI getServiceEndPointByServiceName(String serviceName,String contextPath){
		//using eureka client
		ServiceInstance service2 = loadBalancer.choose(serviceName);
		URI micro2URI = service2.getUri().resolve(contextPath);
		return micro2URI;
	}
}
