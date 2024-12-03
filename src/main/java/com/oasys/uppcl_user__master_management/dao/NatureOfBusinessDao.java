package com.oasys.uppcl_user__master_management.dao;


import java.util.Optional;
import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.entity.NatureOfBusinessMasterEntity;

public interface NatureOfBusinessDao {
	NatureOfBusinessMasterEntity save(NatureOfBusinessMasterEntity natureOfBusiness);

	public BaseDTO getNatureOfBusiness();
//
	public BaseDTO deleteNature(UUID id);
//
	public BaseDTO getAllNatureOfBusinesslazy(PaginationRequestDTO pageData);
//
	public BaseDTO getAllActive();
//	
Optional<NatureOfBusinessMasterEntity> findById(UUID id);
//	
//	List<Nobmentity> findByIdNotIn(UUID id);
//	
//	Nobmentity findByNatureOfBussinessName(String name);
//
	NatureOfBusinessMasterEntity findByNameIgnoreCase(String name);

	


}
