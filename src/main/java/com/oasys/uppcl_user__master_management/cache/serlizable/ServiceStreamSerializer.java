package com.oasys.uppcl_user__master_management.cache.serlizable;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import com.oasys.uppcl_user__master_management.dto.ServiceMasterCacheDTO;


@Service
public class ServiceStreamSerializer implements StreamSerializer<ServiceMasterCacheDTO> {

	@Override
	public void write(ObjectDataOutput out, ServiceMasterCacheDTO serviceSlotEntity) throws IOException {
		out.writeUTF(serviceSlotEntity.getId().toString());
		out.writeUTF(serviceSlotEntity.getName());
		out.writeUTF(serviceSlotEntity.getChargeType());
		out.writeBoolean(serviceSlotEntity.getStatus());
	}

	@Override
	public ServiceMasterCacheDTO read(ObjectDataInput in) throws IOException {
		ServiceMasterCacheDTO serviceMasterEntity = new ServiceMasterCacheDTO();
		serviceMasterEntity.setId(UUID.fromString(in.readUTF()));
		serviceMasterEntity.setName(in.readUTF());
		serviceMasterEntity.setChargeType(in.readUTF());
		serviceMasterEntity.setStatus(in.readBoolean());
		return serviceMasterEntity;
	}

	@Override
	public int getTypeId() {
		return 1;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
