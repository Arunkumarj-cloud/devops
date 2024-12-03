package com.oasys.uppcl_user__master_management.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.MuncipalityDTO;
import com.oasys.uppcl_user__master_management.entity.Muncipality;

@Service
public interface MuncipalityDao {

	public BaseDTO createMuncipality(MuncipalityDTO muncipalityDTO);

	public BaseDTO getMuncipality();

	public Optional<Muncipality> getMuncipalityById(UUID id);

	public BaseDTO updateMuncipality(UUID id, MuncipalityDTO muncipalityDTO);

	public BaseDTO softDelete(UUID id);

	public BaseDTO getLazyList(PaginationRequestDTO dto);
	
	public BaseDTO getDistrictById(UUID id);

	public BaseDTO deleteMunicipality(UUID id);

}
