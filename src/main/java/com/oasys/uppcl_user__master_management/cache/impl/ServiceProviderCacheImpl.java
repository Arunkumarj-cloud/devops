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
import com.oasys.uppcl_user__master_management.cache.dto.ServiceProviderCacheDTO;
import com.oasys.uppcl_user__master_management.entity.ServiceProviderEntity;
import com.oasys.uppcl_user__master_management.repository.ServiceProviderRepository;

@Service
public class ServiceProviderCacheImpl {

	@Autowired
	HazelcastInstance hazelcastInstance;

	@Autowired
	ServiceProviderRepository serviceProviderRepository;

	IMap<String, ServiceProviderCacheDTO> map;
	
	@Autowired
	CommonUtil commonUtil;

	@PostConstruct
	public void loadAllActiveCache() {
		this.map = hazelcastInstance.getMap(HazelCastMapConstant.SERVICE_PROVIDER.toString());
		List<ServiceProviderEntity> serviceSlotEntities = serviceProviderRepository.getAllActive();

		serviceSlotEntities.forEach(obj -> {
			System.out.println(obj.getServiceProviderName());
			this.put(obj.getId().toString(),commonUtil.modalMap(obj, ServiceProviderCacheDTO.class));
		});

	}

	public ServiceProviderCacheDTO put(String id, ServiceProviderCacheDTO serviceProviderCacheDTO) {

		return map.put(id, serviceProviderCacheDTO);
	}

	public Optional<ServiceProviderCacheDTO> get(String key) {

		if (map.containsKey(key)) {
			return Optional.of(map.get(key));
		}

		return Optional.empty();
	}

	public void delete(String key) {
		map.delete(key);
	}

}
