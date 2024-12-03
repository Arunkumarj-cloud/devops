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
import com.oasys.uppcl_user__master_management.dto.ServiceMasterCacheDTO;
import com.oasys.uppcl_user__master_management.entity.ServiceCategoryEntity;
import com.oasys.uppcl_user__master_management.repository.ServiceCategoryRepository;


@Service
public class ServiceCacheImpl {

	@Autowired
	HazelcastInstance hazelcastInstance;

	@Autowired
	ServiceCategoryRepository serviceCastCategoryRepository;

	IMap<String, ServiceMasterCacheDTO> map;
	
	@Autowired
	CommonUtil commonUtil;
	

	@PostConstruct
	public void loadAllActiveCache() {
		this.map = hazelcastInstance.getMap(HazelCastMapConstant.SERVICE.toString());
		List<ServiceCategoryEntity> serviceSlotEntities = serviceCastCategoryRepository.getAllActive();

		
		serviceSlotEntities.forEach(obj -> {
			System.out.println(obj.getId());
			this.put(obj.getId().toString(), commonUtil.modalMap(obj, ServiceMasterCacheDTO.class));
		});

	}

	public ServiceMasterCacheDTO put(String id, ServiceMasterCacheDTO ServiceMasterCacheDTO) {

		return map.put(id, ServiceMasterCacheDTO);
	}

	public Optional<ServiceMasterCacheDTO> get(String key) {

		if (map.containsKey(key)) {
			return Optional.of(map.get(key));
		}

		return Optional.empty();
	}

	public void delete(String key) {
		map.delete(key);
	}


}
