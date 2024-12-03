package com.oasys.feign.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.oasys.config.BaseDTO;
import com.oasys.feign.config.InternalFeginAPIConfig;
import com.oasys.uppcl_user__master_management.dto.ServiceProviderDTO;

@FeignClient(name = "RoutingEngineFeignClient", url = "${routing.domain}", configuration = InternalFeginAPIConfig.class)

public interface RoutingEngineFeignClient {
String AUTH_TOKEN = "X-Authorization";
	
	@PutMapping("/v1/routingengine/getByProviderId/{providerId}/{serviceId}")
	public BaseDTO updateByProviderId(@PathVariable ("providerId") UUID providerId,@PathVariable ("serviceId") UUID serviceId,@RequestBody ServiceProviderDTO dto);


}
