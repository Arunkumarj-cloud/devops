package com.oasys.uppcl_user__master_management.cache.serlizable;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import com.oasys.uppcl_user__master_management.cache.dto.SubCategoryCacheDTO;

@Service
public class SubCategoryStreamSerializer implements StreamSerializer<SubCategoryCacheDTO> {

	@Override
	public void write(ObjectDataOutput out, SubCategoryCacheDTO serviceSlotEntity) throws IOException {
		out.writeUTF(serviceSlotEntity.getId().toString());
		out.writeUTF(serviceSlotEntity.getConstantName());
		out.writeUTF(serviceSlotEntity.getLength());
		out.writeUTF(serviceSlotEntity.getName());
		out.writeUTF(serviceSlotEntity.getSubscriberId());
		out.writeDouble(serviceSlotEntity.getMinAmount());
		out.writeDouble(serviceSlotEntity.getMaxAmount());
		out.writeBoolean(serviceSlotEntity.getStatus());
	}

	@Override
	public SubCategoryCacheDTO read(ObjectDataInput in) throws IOException {
		SubCategoryCacheDTO serviceMasterEntity = new SubCategoryCacheDTO();
		serviceMasterEntity.setId(UUID.fromString(in.readUTF()));
		serviceMasterEntity.setConstantName(in.readUTF());
		serviceMasterEntity.setLength(in.readUTF());
		serviceMasterEntity.setName(in.readUTF());
		serviceMasterEntity.setSubscriberId(in.readUTF());
		serviceMasterEntity.setMinAmount(in.readDouble());
		serviceMasterEntity.setMaxAmount(in.readDouble());
		serviceMasterEntity.setStatus(in.readBoolean());
		return serviceMasterEntity;
	}

	@Override
	public int getTypeId() {
		return 4;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
