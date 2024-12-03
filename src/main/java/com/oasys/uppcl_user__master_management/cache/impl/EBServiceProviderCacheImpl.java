//package com.oasys.uppcl_user__master_management.cache.impl;
//
//import java.util.List;
//import java.util.Optional;
//
//import javax.annotation.PostConstruct;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.hazelcast.core.HazelcastInstance;
//import com.hazelcast.core.IMap;
//import com.oasys.clients.userservice.utils.CommonUtil;
//import com.oasys.constant.HazelCastMapConstant;
//import com.oasys.uppcl_user__master_management.cache.dto.EBServiceProviderCacheDTO;
//import com.oasys.uppcl_user__master_management.cache.dto.ServiceProviderCacheDTO;
//import com.oasys.uppcl_user__master_management.entity.EBServiceProviderEntity;
//import com.oasys.uppcl_user__master_management.repository.EBServiceProviderRepository;
//
//@Service
//public class EBServiceProviderCacheImpl {
//	
//	@Autowired
//	HazelcastInstance hazelcastInstance;
//
//	@Autowired
//	EBServiceProviderRepository serviceProviderRepository;
//
//	IMap<String, EBServiceProviderCacheDTO> map;
//	
//	@Autowired
//	CommonUtil commonUtil;
//
//	@PostConstruct
//	public void loadAllActiveCache() {
//		this.map = hazelcastInstance.getMap(HazelCastMapConstant.SERVICE_PROVIDER.toString());
//		List<EBServiceProviderEntity> serviceSlotEntities = serviceProviderRepository.getAllActive();
//
//		serviceSlotEntities.forEach(obj -> {
//			System.out.println(obj.getServiceProviderName());
//			this.put(obj.getId().toString(),commonUtil.modalMap(obj, EBServiceProviderCacheDTO.class));
//		});
//
//	}
//
//	public EBServiceProviderCacheDTO put(String id, EBServiceProviderCacheDTO serviceProviderCacheDTO) {
//
//		return map.put(id, serviceProviderCacheDTO);
//	}
//
//	public Optional<EBServiceProviderCacheDTO> get(String key) {
//
//		if (map.containsKey(key)) {
//			return Optional.of(map.get(key));
//		}
//
//		return Optional.empty();
//	}
//
//	public void delete(String key) {
//		map.delete(key);
//	}
//
//}
