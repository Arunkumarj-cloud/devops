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
import com.oasys.uppcl_user__master_management.cache.dto.SubCategoryCacheDTO;
import com.oasys.uppcl_user__master_management.entity.SubCategoryEntity;
import com.oasys.uppcl_user__master_management.repository.SubCategoryRepository;
@Service
public class SubCategoryCacheImpl {

	@Autowired
	HazelcastInstance hazelcastInstance;

	@Autowired
	SubCategoryRepository subCategoryRepository;

	IMap<String, SubCategoryCacheDTO> map;
	
	@Autowired
	CommonUtil commonUtil;

	@PostConstruct
	public void loadAllActiveCache() {
		this.map = hazelcastInstance.getMap(HazelCastMapConstant.SUBCATEGORY.toString());
		List<SubCategoryEntity> serviceSlotEntities = subCategoryRepository.getAllActive();

		serviceSlotEntities.forEach(obj -> {
			this.put(obj.getId().toString(), commonUtil.modalMap(obj, SubCategoryCacheDTO.class));
		});

	}

	public SubCategoryCacheDTO put(String id, SubCategoryCacheDTO subCategoryCacheDTO) {

		return map.put(id, subCategoryCacheDTO);
	}

	public Optional<SubCategoryCacheDTO> get(String key) {

		if (map.containsKey(key)) {
			return Optional.of(map.get(key));
		}

		return Optional.empty();
	}

	public void delete(String key) {
		map.delete(key);
	}

}
