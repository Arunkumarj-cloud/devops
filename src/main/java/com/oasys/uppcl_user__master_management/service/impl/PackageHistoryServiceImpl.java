package com.oasys.uppcl_user__master_management.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.oasys.cexception.NoRecoerdFoundException;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.PackageHistoryResponseDTO;
import com.oasys.uppcl_user__master_management.entity.PackageHistory;
import com.oasys.uppcl_user__master_management.mapper.PackageHistoryMapper;
import com.oasys.uppcl_user__master_management.repository.PackageHistoryRepositoryImpl;
import com.oasys.uppcl_user__master_management.response.ResponseMessageConstant;
import com.oasys.uppcl_user__master_management.service.PackageHistoryService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class PackageHistoryServiceImpl implements PackageHistoryService{
	
	@Autowired
	private PackageHistoryRepositoryImpl packageHistoryRepositoryImpl;
	
	@Autowired
	private PackageHistoryMapper mapper;
	
	
	public BaseDTO getBySearchFilter(PaginationRequestDTO requestDTO) {
		log.info("ServiceFeeMappingServiceImpl getBySearchFilter() :: {}", requestDTO);
		BaseDTO baseDTO = new BaseDTO();
		try {
			Long count = packageHistoryRepositoryImpl.getCountBySearchFields(requestDTO);
			log.info("total count :: {}", count);
			if (count > 0) {
				List<PackageHistory> packageHistoryList = packageHistoryRepositoryImpl
						.getRecordsByFilterDTO(requestDTO);
				if (CollectionUtils.isEmpty(packageHistoryList)) {
					log.info("no data found corresponding to :: {}", requestDTO.toString());
					throw new NoRecoerdFoundException(ResponseMessageConstant.NO_RECOERD_FOUND.getMessage());
				}
				List<PackageHistoryResponseDTO> responseList = packageHistoryList.stream()
						.map(mapper::convertEntityToResponseDTO).collect(Collectors.toList());
				baseDTO.setResponseContents(responseList);
				baseDTO.setTotalRecords(Objects.nonNull(count) ? count.longValue() : null);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				baseDTO.setMessage(ResponseMessageConstant.SEARCH_RESPONSE.getMessage());
				return baseDTO;
			} else {
				log.info("no data found corresponding to :: {}", requestDTO.toString());
				throw new NoRecoerdFoundException(ResponseMessageConstant.NO_RECOERD_FOUND.getMessage());
			}
		} catch (Exception e) {
			log.error("error occured in ServiceCategoryFeeMappingServiceImpl:: getBySearchFilter():{}",
					e);
			baseDTO.setStatusCode(ResponseMessageConstant.NOT_FOUND.getErrorCode());
			baseDTO.setMessage(ResponseMessageConstant.NOT_FOUND.getMessage());
			return baseDTO;
		}
	}
}
