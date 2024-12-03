package com.oasys.uppcl_user__master_management.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.GramPanchayatDTO;
import com.oasys.uppcl_user__master_management.entity.GramPanchayat;

@Service
public interface GramPanchayatDao {
	public BaseDTO createGramPanchayat(GramPanchayatDTO gramPanchayatDTO);

	public BaseDTO getGrampanchayat();

	Optional<GramPanchayat> getGramPanchayatById(UUID id);

	public BaseDTO updateGramPanchayat(UUID id, GramPanchayatDTO gramPanchayatDTO);

	public BaseDTO deleteGramPanchayat(UUID id);

	public BaseDTO getAllPanchayatlazy(PaginationRequestDTO pageData);

	
	public BaseDTO getByBlockId(UUID id);
	

}
