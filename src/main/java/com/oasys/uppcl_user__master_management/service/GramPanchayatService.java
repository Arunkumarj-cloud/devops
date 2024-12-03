package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.GramPanchayatDTO;
@Service
public interface GramPanchayatService {
	public BaseDTO create(GramPanchayatDTO gramPanchayatDTO);

	public BaseDTO getGramPanchayat();

	public BaseDTO getGramPanchayatById(UUID id);

	public BaseDTO updateGramPanchayat(UUID id, GramPanchayatDTO gramPanchayatDTO);

	public BaseDTO deleteGramPanchayat(UUID id);

	public BaseDTO getAllPanchayatlazy(PaginationRequestDTO pageData);

	public BaseDTO getByBlockId(UUID id);

	BaseDTO softDelete(UUID id);
}
