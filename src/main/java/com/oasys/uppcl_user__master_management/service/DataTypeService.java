package com.oasys.uppcl_user__master_management.service;


import java.util.UUID;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.DataTypeDTO;

@Service
	public interface DataTypeService {

		public BaseDTO create(DataTypeDTO dataTypeDTO);

		public BaseDTO getAll();

		public BaseDTO update(UUID id, @Valid DataTypeDTO dataTypeDTO);

        public BaseDTO getById(UUID id);

		public BaseDTO delete(UUID id);

		public BaseDTO softDelete(UUID id);

		public BaseDTO getAllActive();

		public BaseDTO getLazyList(PaginationRequestDTO requestData);

		

	}

