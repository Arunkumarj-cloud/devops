package com.oasys.uppcl_user__master_management.cache.serlizable;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import com.oasys.uppcl_user__master_management.cache.dto.ServiceProviderCacheDTO;

@Service
public class ServiceProviderStreamSerializer implements StreamSerializer<ServiceProviderCacheDTO> {

	@Override
	public void write(ObjectDataOutput out, ServiceProviderCacheDTO serviceProviderEntity) throws IOException {
		out.writeUTF(serviceProviderEntity.getId().toString());
		out.writeUTF(serviceProviderEntity.getServiceProviderName());
		out.writeBoolean(serviceProviderEntity.getStatus());
	}

	@Override
	public ServiceProviderCacheDTO read(ObjectDataInput in) throws IOException {
		ServiceProviderCacheDTO serviceProviderEntity = new ServiceProviderCacheDTO();
		serviceProviderEntity.setId(UUID.fromString(in.readUTF()));
		serviceProviderEntity.setServiceProviderName(in.readUTF());
		serviceProviderEntity.setStatus(in.readBoolean());
		return serviceProviderEntity;
	}

	@Override
	public int getTypeId() {
		return 2;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
