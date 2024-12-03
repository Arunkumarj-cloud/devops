package com.oasys.uppcl_user__master_management.dao;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.entity.ReligionMaster;

public interface ReligionMasterDao {

	public BaseDTO getAll();

	ReligionMaster save(ReligionMaster religionMaster);

	Optional<ReligionMaster> getById(UUID id);

	public BaseDTO delete(UUID id);

	public BaseDTO getAllActive();

	public BaseDTO getAllLazy(PaginationRequestDTO pageData);

	ReligionMaster findByReligionName(String religion);

	List<ReligionMaster> findByIdNotIn(UUID id);
}
