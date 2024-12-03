package com.oasys.uppcl_user__master_management.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.entity.VillageMasterEntity;


public interface VillageMasterDAO {
	
	VillageMasterEntity save(VillageMasterEntity villageMasterEntity);

	BaseDTO getAll();

	BaseDTO getAllActive();

	Optional<VillageMasterEntity> getById(UUID id);

	BaseDTO delete(UUID id);

	BaseDTO getLazyList(PaginationRequestDTO requestData);

	BaseDTO softDelete(UUID id);
	
	VillageMasterEntity check(String villageName, String code, UUID id);
	
	BaseDTO getByTalukId(UUID id);

	List<VillageMasterEntity> checkCodeUpdate(String code, UUID id);


}
