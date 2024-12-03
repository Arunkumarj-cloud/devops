package com.oasys.uppcl_user__master_management.service.impl;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oasys.cexception.NoRecoerdFoundException;
import com.oasys.clients.userservice.utils.CommonUtil;
import com.oasys.config.BaseDTO;
import com.oasys.config.Constants;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.constant.ActionType;
import com.oasys.security.Oauth2UserDetails;
import com.oasys.uppcl_user__master_management.dto.ServiceCategoryFeeMappingRequestDTO;
import com.oasys.uppcl_user__master_management.dto.ServiceCategoryFeeMappingResponseDTO;
import com.oasys.uppcl_user__master_management.entity.ServiceCategoryEntity;
import com.oasys.uppcl_user__master_management.entity.ServiceCategoryFeeMappingEntity;
import com.oasys.uppcl_user__master_management.entity.ServiceCategoryFeeMappingHistoryEntity;
import com.oasys.uppcl_user__master_management.mapper.ServiceFeeMapper;
import com.oasys.uppcl_user__master_management.repository.PackageRepository;
import com.oasys.uppcl_user__master_management.repository.ServiceCategoryFeeMappingHistoryRepository;
import com.oasys.uppcl_user__master_management.repository.ServiceCategoryFeeMappingRepository;
import com.oasys.uppcl_user__master_management.repository.ServiceCategoryFeeMappingRepositoryImpl;
import com.oasys.uppcl_user__master_management.repository.ServiceCategoryRepository;
import com.oasys.uppcl_user__master_management.response.ResponseMessageConstant;
import com.oasys.uppcl_user__master_management.service.ServiceCategoryFeeMappingService;
import com.oasys.uppcl_user__master_management.validation.ServiceCategoryFeeMappingValidator;

import lombok.extern.log4j.Log4j2;
@Service
@Log4j2
public class ServiceCategoryFeeMappingServiceImpl implements ServiceCategoryFeeMappingService {

	@Autowired
	private ServiceCategoryFeeMappingValidator serviceFeeMappingValidator;

	@Autowired
	private ServiceCategoryFeeMappingRepository serviceFeeMappingRepository;

	@Autowired
	private ServiceCategoryFeeMappingRepositoryImpl serviceFeeMappingRepositoryImpl;

	@Autowired
	private Oauth2UserDetails oauth2UserDetails;

	@Autowired
	private ServiceFeeMapper serviceFeeMapper;

	@Autowired
	private ServiceCategoryRepository serviceRepository;

	@Autowired
	private PackageRepository packageRepository;
	
	@Autowired
	private ServiceCategoryFeeMappingHistoryRepository feeMappingHistoryRepository;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private HttpServletRequest httpRequest;

	@Transactional
	
	@Override
	public BaseDTO save(ServiceCategoryFeeMappingRequestDTO requestDTO) {
		BaseDTO responseDTO = null;
		try {
			responseDTO = serviceFeeMappingValidator.validate(requestDTO, Boolean.FALSE);
			if (ResponseMessageConstant.SUCCESS_RESPONSE.getErrorCode() != responseDTO.getStatusCode()) {
				return responseDTO;
			}
			Boolean isServiceExistInDefaultPackage = packageRepository
					.isServiceExistInDefaultPackage(requestDTO.getServiceCategoryId());
			if (isServiceExistInDefaultPackage && requestDTO.getAmount() > 0) {
				responseDTO.setStatusCode(
						ResponseMessageConstant.SERVICES_AMOUNT_GREATER_THAN_ZERO_NOT_ALLOWED.getErrorCode());
				responseDTO
						.setMessage(ResponseMessageConstant.SERVICES_AMOUNT_GREATER_THAN_ZERO_NOT_ALLOWED.getMessage());
				return responseDTO;
			}
			
			ServiceCategoryFeeMappingEntity entity = new ServiceCategoryFeeMappingEntity();
			entity.setAmount(requestDTO.getAmount());
			ServiceCategoryEntity serviceCategoryEntity = serviceRepository.findById(requestDTO.getServiceCategoryId()).get();
			entity.setServiceCategoryEntity(serviceCategoryEntity);
			entity.setCreatedBy(oauth2UserDetails.getId());
			serviceFeeMappingRepository.save(entity);
			ServiceCategoryFeeMappingHistoryEntity feeMappingHistoryEntity = new ServiceCategoryFeeMappingHistoryEntity();
			feeMappingHistoryEntity.setServiceCategoryEntity(serviceCategoryEntity);
			feeMappingHistoryEntity.setUpdatedAmount(requestDTO.getAmount());
			feeMappingHistoryEntity.setActionType(ActionType.ADD);
			feeMappingHistoryEntity.setCreatedBy(oauth2UserDetails.getId());
			if (StringUtils.isBlank(requestDTO.getRemarks())) {
				feeMappingHistoryEntity.setRemarks("New Service Fee Configuration Added.");
			} else {
				feeMappingHistoryEntity.setRemarks(requestDTO.getRemarks());
			}
			feeMappingHistoryEntity.setIpAddress(commonUtil.getClientIP(httpRequest));
			feeMappingHistoryRepository.save(feeMappingHistoryEntity);
			responseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			responseDTO.setMessage(ResponseMessageConstant.CREATE_SUCCESS_RESPONSE.getMessage());
			responseDTO.setResponseContent(serviceFeeMapper.convertEntityToResponseDTO(entity));
		} catch (Exception e) {
			log.error("error occured in ServiceCategoryFeeMappingServiceImpl:: save():{}",
					e);
			responseDTO.setStatusCode(ResponseMessageConstant.FAILED_TO_ADD.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.FAILED_TO_ADD.getMessage());
			return responseDTO;
		}
		return responseDTO;
	}

	@Transactional
	@Override
	public BaseDTO update(ServiceCategoryFeeMappingRequestDTO requestDTO) {
		BaseDTO responseDTO = serviceFeeMappingValidator.validate(requestDTO, Boolean.TRUE);
		Double previousAmount = null;
		try {
			if (ResponseMessageConstant.SUCCESS_RESPONSE.getErrorCode() != responseDTO.getStatusCode()) {
				return responseDTO;
			}
			Optional<ServiceCategoryFeeMappingEntity> serviceFeeMappingOptional = serviceFeeMappingRepository
					.findByServiceCategoryId(requestDTO.getServiceCategoryId());
			if (!serviceFeeMappingOptional.isPresent()) {
				responseDTO.setStatusCode(ResponseMessageConstant.NOT_FOUND.getErrorCode());
				responseDTO.setMessage(ResponseMessageConstant.SERVICE_FEE_MAPPING_DOES_NOT_EXIST.getMessage());
				return responseDTO;
			}
			ServiceCategoryEntity serviceCatgoryEntity = serviceFeeMappingOptional.get().getServiceCategoryEntity();
			if (Objects.isNull(serviceCatgoryEntity)) {
				responseDTO.setStatusCode(ResponseMessageConstant.SERVICE_CATEGORY_MISSING.getErrorCode());
				responseDTO.setMessage(ResponseMessageConstant.SERVICE_CATEGORY_MISSING.getMessage());
				return responseDTO;
			}
			
			Boolean isServiceExistInDefaultPackage = packageRepository
					.isServiceExistInDefaultPackage(serviceCatgoryEntity.getId());
			if (isServiceExistInDefaultPackage && requestDTO.getAmount() > 0) {
				responseDTO.setStatusCode(
						ResponseMessageConstant.SERVICES_AMOUNT_GREATER_THAN_ZERO_NOT_ALLOWED.getErrorCode());
				responseDTO
						.setMessage(ResponseMessageConstant.SERVICES_AMOUNT_GREATER_THAN_ZERO_NOT_ALLOWED.getMessage());
				return responseDTO;
			}
			ServiceCategoryFeeMappingEntity entity = serviceFeeMappingOptional.get();
			previousAmount= entity.getAmount();
			if(previousAmount.equals(requestDTO.getAmount())) {
				responseDTO.setStatusCode(
						ResponseMessageConstant.SAME_AMOUNT_ALREADY_CONFIGURED.getErrorCode());
				responseDTO
						.setMessage(ResponseMessageConstant.SAME_AMOUNT_ALREADY_CONFIGURED.getMessage());
				return responseDTO;
			}
			entity.setAmount(requestDTO.getAmount());
			entity.setModifiedBy(oauth2UserDetails.getId());
			serviceFeeMappingRepository.save(entity);
			ServiceCategoryFeeMappingHistoryEntity feeMappingHistoryEntity = new ServiceCategoryFeeMappingHistoryEntity();
			feeMappingHistoryEntity.setServiceCategoryEntity(entity.getServiceCategoryEntity());
			feeMappingHistoryEntity.setUpdatedAmount(requestDTO.getAmount());
			feeMappingHistoryEntity.setPreviousAmount(previousAmount);
			feeMappingHistoryEntity.setActionType(ActionType.MODIFY);
			feeMappingHistoryEntity.setCreatedBy(oauth2UserDetails.getId());
			if (StringUtils.isBlank(requestDTO.getRemarks())) {
				feeMappingHistoryEntity.setRemarks("Amount Updated.");
			} else {
				feeMappingHistoryEntity.setRemarks(requestDTO.getRemarks());
			}
			feeMappingHistoryEntity.setIpAddress(commonUtil.getClientIP(httpRequest));
			feeMappingHistoryRepository.save(feeMappingHistoryEntity);
			responseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			responseDTO.setMessage(ResponseMessageConstant.UPDAE_RESPONSE.getMessage());
			responseDTO.setResponseContent(serviceFeeMapper.convertEntityToResponseDTO(entity));
			return responseDTO;
		} catch (Exception e) {
			log.error("error occured in ServiceCategoryFeeMappingServiceImpl:: update():{}", e);
			responseDTO.setStatusCode(ResponseMessageConstant.FAILED_TO_UPDATE.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.FAILED_TO_UPDATE.getMessage());
			return responseDTO;
		}
	}

	public BaseDTO getByServiceId(String serviceId) {
		BaseDTO responseDTO = new BaseDTO();
		try {
			if (!CommonUtil.isValidUUID(serviceId.trim())) {
				responseDTO.setStatusCode(ResponseMessageConstant.INVALID_REQUEST_PARAMETER.getErrorCode());
				responseDTO.setMessage(ResponseMessageConstant.INVALID_REQUEST_PARAMETER
						.getMessage(new Object[] { Constants.SERVICE_ID }));
				return responseDTO;
			}
			Optional<ServiceCategoryFeeMappingEntity> serviceFeeMappingOptional = serviceFeeMappingRepository
					.findByServiceCategoryId(UUID.fromString(serviceId.trim()));
			if (!serviceFeeMappingOptional.isPresent()) {
				responseDTO.setStatusCode(ResponseMessageConstant.NOT_FOUND.getErrorCode());
				responseDTO.setMessage(ResponseMessageConstant.SERVICE_FEE_MAPPING_DOES_NOT_EXIST.getMessage());
				return responseDTO;
			}
			responseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			responseDTO.setMessage(ResponseMessageConstant.SEARCH_RESPONSE.getMessage());
			responseDTO
					.setResponseContent(serviceFeeMapper.convertEntityToResponseDTO(serviceFeeMappingOptional.get()));
			return responseDTO;
		} catch (Exception e) {
			log.error("error occured in ServiceCategoryFeeMappingServiceImpl:: getByServiceId():{}",
					e);
			responseDTO.setStatusCode(ResponseMessageConstant.NOT_FOUND.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.NOT_FOUND.getMessage());
			return responseDTO;
		}
	}

	public BaseDTO getBySearchFilter(PaginationRequestDTO requestDTO) {
		log.info("ServiceFeeMappingServiceImpl getBySearchFilter() :: {}", requestDTO);
		BaseDTO baseDTO = new BaseDTO();
		try {
			Long count = serviceFeeMappingRepositoryImpl.getCountBySearchFields(requestDTO);
			log.info("total count :: {}", count);
			if (count > 0) {
				List<ServiceCategoryFeeMappingEntity> serviceFeeMappingList = serviceFeeMappingRepositoryImpl
						.getRecordsByFilterDTO(requestDTO);
				if (CollectionUtils.isEmpty(serviceFeeMappingList)) {
					log.info("no data found corresponding to :: {}", requestDTO.toString());
					throw new NoRecoerdFoundException(ResponseMessageConstant.NO_RECOERD_FOUND.getMessage());
				}
				List<ServiceCategoryFeeMappingResponseDTO> responseList = serviceFeeMappingList.stream()
						.map(serviceFeeMapper::convertEntityToResponseDTO).collect(Collectors.toList());
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

	@Override
	public Double getAmountByServiceCategoryId(UUID serviceCategoryId) {
		Optional<ServiceCategoryFeeMappingEntity> ServiceCategoryFeeMappingEntityOptional = serviceFeeMappingRepository
				.findByServiceCategoryId(serviceCategoryId);
		if (ServiceCategoryFeeMappingEntityOptional.isPresent()) {
			return ServiceCategoryFeeMappingEntityOptional.get().getAmount();
		}
		log.error("fee mapping not configured for : {}", serviceCategoryId);
		return null;
	}

	@Override
	public BaseDTO getTotalAmountByServiceCategoryIds(List<UUID> serviceCategoryIds) {
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO.setResponseContent(
					serviceFeeMappingRepository.getTotalAmountByServiceCategoryIds(serviceCategoryIds));
		} catch (Exception e) {
			log.error("error occured in ServiceCategoryFeeMappingServiceImpl:: getTotalAmountByServiceCategoryIds():{}",
					e);
			baseDTO.setStatusCode(ResponseMessageConstant.NOT_FOUND.getErrorCode());
			baseDTO.setMessage(ResponseMessageConstant.NOT_FOUND.getMessage());
			return baseDTO;
		}
		baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
		baseDTO.setMessage(ResponseMessageConstant.SEARCH_RESPONSE.getMessage());
		return baseDTO;
	}

	@Override
	public BaseDTO getAllRecords(Boolean status) {
		BaseDTO baseDTO = new BaseDTO();
		List<ServiceCategoryFeeMappingEntity> serviceFeepMappingEntityList = null;
		if(Objects.isNull(status)) {
			serviceFeepMappingEntityList = serviceFeeMappingRepository.findAll();
		}else {
			serviceFeepMappingEntityList = serviceFeeMappingRepository.findAllActive();
		}
		if (CollectionUtils.isEmpty(serviceFeepMappingEntityList)) {
			log.info("no data found corresponding to :: ");
			throw new NoRecoerdFoundException(ResponseMessageConstant.NO_RECOERD_FOUND.getMessage());
		}
		List<ServiceCategoryFeeMappingResponseDTO> responseList = serviceFeepMappingEntityList.stream()
				.map(serviceFeeMapper::convertEntityToResponseDTO).collect(Collectors.toList());
		baseDTO.setResponseContents(responseList);
		baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
		baseDTO.setMessage(ResponseMessageConstant.SEARCH_RESPONSE.getMessage());
		return baseDTO;
	}

}

	
