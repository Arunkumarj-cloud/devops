package com.oasys.uppcl_user__master_management.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.entity.TypeOfAccountMaster;

public interface TypeOfAccountDao {

	TypeOfAccountMaster save(TypeOfAccountMaster typeOfAccountDTO);

	Optional<TypeOfAccountMaster> getById(UUID id);

	BaseDTO getAll();

	BaseDTO getAllActive();

	BaseDTO getLazyList(PaginationRequestDTO requestData);

	BaseDTO delete(UUID id);

	BaseDTO softDelete(UUID id);

	List<TypeOfAccountMaster> findByIdNotIn(UUID id);

	TypeOfAccountMaster findByAccountType(String type);

}
