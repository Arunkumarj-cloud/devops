package com.oasys.uppcl_user__master_management.cache.serlizable;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import com.oasys.uppcl_user__master_management.cache.dto.ServiceSlotCacheDTO;


@Service
public class SlotStreamSerializer implements StreamSerializer<ServiceSlotCacheDTO> {

	@Override
	public void write(ObjectDataOutput out, ServiceSlotCacheDTO serviceSlotEntity) throws IOException {
		out.writeUTF(serviceSlotEntity.getId().toString());
		out.writeUTF(serviceSlotEntity.getSlot());
		out.writeUTF(serviceSlotEntity.getRemarks());
		out.writeLong(serviceSlotEntity.getStartRange());
		out.writeLong(serviceSlotEntity.getEndRange());
		out.writeBoolean(serviceSlotEntity.getStatus());
	}

	@Override
	public ServiceSlotCacheDTO read(ObjectDataInput in) throws IOException {
		ServiceSlotCacheDTO serviceSlotEntity = new ServiceSlotCacheDTO();
		serviceSlotEntity.setId(UUID.fromString(in.readUTF()));
		serviceSlotEntity.setSlot(in.readUTF());
		serviceSlotEntity.setRemarks(in.readUTF());
		serviceSlotEntity.setStartRange(in.readLong());
		serviceSlotEntity.setEndRange(in.readLong());
		serviceSlotEntity.setStatus(in.readBoolean());
		return serviceSlotEntity;
	}

	@Override
	public int getTypeId() {
		return 3;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
