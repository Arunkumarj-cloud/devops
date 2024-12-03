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
import com.oasys.uppcl_user__master_management.dto.ServiceCategoryFeeMappingHistoryResponseDTO;
import com.oasys.uppcl_user__master_management.entity.ServiceCategoryFeeMappingHistoryEntity;
import com.oasys.uppcl_user__master_management.mapper.ServiceFeeHistoryMapper;
import com.oasys.uppcl_user__master_management.repository.ServiceCategoryFeeMappingHistoryRepositoryImpl;
import com.oasys.uppcl_user__master_management.response.ResponseMessageConstant;
import com.oasys.uppcl_user__master_management.service.ServiceCategoryFeeMappingHistoryService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ServiceCategoryFeeMappingHistoryServiceImpl implements ServiceCategoryFeeMappingHistoryService{

	@Autowired
	private ServiceCategoryFeeMappingHistoryRepositoryImpl feeMappingHistoryRepositoryImpl;
	
	@Autowired
	private ServiceFeeHistoryMapper serviceHistoryFeeMapper;
	
	@Override
	public BaseDTO getBySearchFilter(PaginationRequestDTO requestDTO) {
		log.info("ServiceFeeMappingServiceImpl getBySearchFilter() :: {}", requestDTO);
		BaseDTO baseDTO = new BaseDTO();
		try {
			Long count = feeMappingHistoryRepositoryImpl.getCountBySearchFields(requestDTO);
			log.info("total count :: {}", count);
			if (count > 0) {
				List<ServiceCategoryFeeMappingHistoryEntity> serviceFeeMappingList = feeMappingHistoryRepositoryImpl
						.getRecordsByFilterDTO(requestDTO);
				if (CollectionUtils.isEmpty(serviceFeeMappingList)) {
					log.info("no data found corresponding to :: {}", requestDTO.toString());
					throw new NoRecoerdFoundException(ResponseMessageConstant.NO_RECOERD_FOUND.getMessage());
				}
				List<ServiceCategoryFeeMappingHistoryResponseDTO> responseList = serviceFeeMappingList.stream()
						.map(serviceHistoryFeeMapper::convertEntityToResponseDTO).collect(Collectors.toList());
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
