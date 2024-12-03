package com.oasys.uppcl_user__master_management.dao;



import java.util.Date;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.oasys.config.BaseDTO;
import com.oasys.uppcl_user__master_management.dto.TalukMasterDTO;
import com.oasys.uppcl_user__master_management.entity.TalukMasterEntity;


public interface TalukMasterDao {
	
	BaseDTO create(TalukMasterDTO talukMasterDTO);
	BaseDTO getAll();
	BaseDTO update(UUID id, TalukMasterDTO talukMasterDTO);
	BaseDTO getById(UUID id);
	BaseDTO getAllActive();
	BaseDTO softDelete(UUID id);
	BaseDTO getByDistrictId(UUID id);
	
	Page<TalukMasterEntity> getAllWithPageAndSearchAndTime(Pageable pageable, String search,
			Date fromDate, Date toDate);
	Page<TalukMasterEntity> getAllWithPageAndTime(Pageable pageable, Date fromDate,
			Date toDate);
	Page<TalukMasterEntity> getAllWithPageAndSearch(Pageable pageable, String search);
	Page<TalukMasterEntity> getAllWithPage(Pageable pageable);
    Integer getCount();

	

}
