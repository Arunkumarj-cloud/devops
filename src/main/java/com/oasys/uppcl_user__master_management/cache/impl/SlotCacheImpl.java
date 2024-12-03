package com.oasys.uppcl_user__master_management.cache.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.oasys.clients.userservice.utils.CommonUtil;
import com.oasys.constant.HazelCastMapConstant;
import com.oasys.uppcl_user__master_management.cache.dto.ServiceSlotCacheDTO;
import com.oasys.uppcl_user__master_management.entity.ServiceSlotEntity;
import com.oasys.uppcl_user__master_management.repository.ServiceSlotRepository;

@Service
public class SlotCacheImpl {

	@Autowired
	HazelcastInstance hazelcastInstance;

	@Autowired
	ServiceSlotRepository serviceSlotRepository;

	IMap<String, ServiceSlotCacheDTO> map;
	
	@Autowired
	CommonUtil commonUtil;

	@PostConstruct
	public void loadAllActiveCache() {
		this.map = hazelcastInstance.getMap(HazelCastMapConstant.SLOT.toString());
		List<ServiceSlotEntity> serviceSlotEntities = serviceSlotRepository.getAllActive();

		serviceSlotEntities.forEach(obj -> {
			this.put(obj.getId().toString(),commonUtil.modalMap(obj, ServiceSlotCacheDTO.class));
		});

	}

	public ServiceSlotCacheDTO put(String id, ServiceSlotCacheDTO serviceSlotCacheDTO) {

		return map.put(id, serviceSlotCacheDTO);
	}

	public Optional<ServiceSlotCacheDTO> get(String key) {

		if (map.containsKey(key)) {
			return Optional.of(map.get(key));
		}

		return Optional.empty();
	}

	public void delete(String key) {
		map.delete(key);
	}

}
