package com.oasys.uppcl_user__master_management.dao;


import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.DataTypeDTO;
import com.oasys.uppcl_user__master_management.entity.DataTypeEntity;

@Service
public interface DataTypeDao {
		
		 public BaseDTO create( DataTypeDTO dataTypeDTO);

			public BaseDTO getAll();

			public BaseDTO update(UUID id, DataTypeDTO dataTypeDTO);

			public BaseDTO getById(UUID id);

			public BaseDTO delete(UUID id);

			public BaseDTO softDelete(UUID id);

			public BaseDTO getAllActive();

			public Page<DataTypeEntity> getLazyList(PaginationRequestDTO requestData);

			 }


