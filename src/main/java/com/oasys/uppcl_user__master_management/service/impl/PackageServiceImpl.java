package com.oasys.uppcl_user__master_management.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hazelcast.util.CollectionUtil;
import com.oasys.cexception.NoRecoerdFoundException;
import com.oasys.clients.userservice.utils.CommonUtil;
import com.oasys.config.BaseDTO;
import com.oasys.config.Constants;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.constant.ActionType;

import com.oasys.uppcl_user__master_management.dto.PackageRequestDTO;
import com.oasys.uppcl_user__master_management.dto.PackageResponseDTO;
import com.oasys.uppcl_user__master_management.entity.PackageEntity;
import com.oasys.uppcl_user__master_management.entity.PackageHistory;
import com.oasys.uppcl_user__master_management.entity.ServiceCategoryEntity;
import com.oasys.uppcl_user__master_management.entity.ServiceCategoryFeeMappingEntity;
import com.oasys.uppcl_user__master_management.mapper.PackageMapper;
import com.oasys.uppcl_user__master_management.repository.PackageHistoryRepository;
import com.oasys.uppcl_user__master_management.repository.PackageRepository;
import com.oasys.uppcl_user__master_management.repository.PackageRepositoryImpl;
import com.oasys.uppcl_user__master_management.repository.ServiceCategoryFeeMappingRepository;
import com.oasys.uppcl_user__master_management.repository.ServiceCategoryRepository;
import com.oasys.uppcl_user__master_management.response.ResponseMessageConstant;
import com.oasys.uppcl_user__master_management.service.PackageService;
import com.oasys.uppcl_user__master_management.validation.PackageRequestValidator;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class PackageServiceImpl implements PackageService {

	@Autowired
	private PackageRepository packageRepository;

	@Autowired
	private PackageRequestValidator validator;

	@Autowired
	private ServiceCategoryRepository serviceCategoryRepository;

	@Autowired
	private PackageHistoryRepository packageHistoryRepository;

	//@Autowired
	//private Oauth2UserDetails oauth2UserDetails;

	@Autowired
	private PackageMapper packageMapper;

	@Autowired
	private PackageRepositoryImpl packageRepositoryImpl;

	@Autowired
	private ServiceCategoryFeeMappingRepository serviceCategoryFeeMappingRepository;

	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private HttpServletRequest request;

	@Transactional
	@Override
	public BaseDTO save(PackageRequestDTO requestDTO) {
		log.info("class :PackageServiceImpl, method: save() :: request data :: {}", requestDTO);
		BaseDTO responseDTO = validator.validate(requestDTO, Boolean.FALSE);
		Double serviceAmountTotal = 0.0;
		if (ResponseMessageConstant.SUCCESS_RESPONSE.getErrorCode() != responseDTO.getStatusCode()) {
			return responseDTO;
		}
		if (Objects.isNull(requestDTO.getIsDefaultPackage())) {
			requestDTO.setIsDefaultPackage(Boolean.FALSE);
		}
		requestDTO.setId(null);
		requestDTO.setName(requestDTO.getName().trim());
		if(StringUtils.isNotBlank(requestDTO.getRemarks())) {
			requestDTO.setRemarks(requestDTO.getRemarks().trim());
		}
		if (Boolean.TRUE.equals(requestDTO.getIsDefaultPackage())) {
			Optional<PackageEntity> packageEntityOptional = packageRepository.findByIsDefaultPackage(Boolean.TRUE);
			if (packageEntityOptional.isPresent()) {
				responseDTO.setStatusCode(ResponseMessageConstant.DEFAULT_PACKAGE_ALREADY_EXIST.getErrorCode());
				responseDTO.setMessage(ResponseMessageConstant.DEFAULT_PACKAGE_ALREADY_EXIST
						.getMessage(new Object[] { Constants.DEFAULT }));
				return responseDTO;
			}
			if (requestDTO.getAmount() > 0) {
				responseDTO
						.setStatusCode(ResponseMessageConstant.DEFAULT_PACKAGE_AMOUNT_GREATER_THAN_ZERO.getErrorCode());
				responseDTO.setMessage(ResponseMessageConstant.DEFAULT_PACKAGE_AMOUNT_GREATER_THAN_ZERO.getMessage());
				return responseDTO;
			}
		}
		Optional<PackageEntity> packageEntityOptional = packageRepository
				.findByPackageName(requestDTO.getName().trim().toUpperCase());
		if (packageEntityOptional.isPresent()) {
			responseDTO.setStatusCode(ResponseMessageConstant.ALREADY_EXISTS.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.ALREADY_EXIST
					.getMessage(new Object[] { Constants.PACKAGE, requestDTO.getName() }));
			return responseDTO;
		}
		Boolean isPackageAlreadyExist = this.isPackageAlreadyExistWithSameServices(requestDTO);
		if (Boolean.TRUE.equals(isPackageAlreadyExist)) {
			responseDTO.setStatusCode(ResponseMessageConstant.PACKAGE_SERVICE_MAPPING_ALREADY_EXIST.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.PACKAGE_SERVICE_MAPPING_ALREADY_EXIST.getMessage());
			return responseDTO;
		}
		if(Objects.isNull(requestDTO.getStatus())) {
			requestDTO.setStatus(Boolean.TRUE);	
		}
		PackageEntity packageEntity = new PackageEntity();
		packageEntity.setName(requestDTO.getName());
		packageEntity.setAmount(requestDTO.getAmount());
		packageEntity.setIsDefaultPackage(requestDTO.getIsDefaultPackage());
		packageEntity.setStatus(requestDTO.getStatus());
		packageEntity.setRemarks(requestDTO.getRemarks());
		Set<ServiceCategoryEntity> serviceCategoryList = new HashSet<>();
		Set<String> serviceNames = new HashSet<>();
		for (UUID serviceCategoryId : requestDTO.getServiceCategoryIds()) {
			Optional<ServiceCategoryEntity> ServiceCategoryEntityOptional = serviceCategoryRepository
					.findById(serviceCategoryId);

			if (ServiceCategoryEntityOptional.isPresent()) {
				Optional<ServiceCategoryFeeMappingEntity> serviceCategoryFeeMappingEntityOptional = serviceCategoryFeeMappingRepository
						.findByServiceCategoryId(serviceCategoryId);
				if (!serviceCategoryFeeMappingEntityOptional.isPresent()
						|| Objects.isNull(serviceCategoryFeeMappingEntityOptional.get().getAmount())) {
					responseDTO.setStatusCode(
							ResponseMessageConstant.SERVICE_FEE_MAPPING_NOT_CONFIGURED.getErrorCode());
					responseDTO.setMessage(ResponseMessageConstant.SERVICE_FEE_MAPPING_NOT_CONFIGURED
							.getMessage(new Object[] { ServiceCategoryEntityOptional.get().getName() }));
					return responseDTO;
				}
				serviceAmountTotal = serviceAmountTotal + serviceCategoryFeeMappingEntityOptional.get().getAmount();
				if (Boolean.TRUE.equals(requestDTO.getIsDefaultPackage())) {
					
					if (Objects.nonNull(serviceCategoryFeeMappingEntityOptional.get().getAmount())
							&& serviceCategoryFeeMappingEntityOptional.get().getAmount() > 0) {
						responseDTO.setStatusCode(
								ResponseMessageConstant.SERVICES_WITH_AMOUNT_ZERO_IS_ALLOWED_IN_DEFAULT_PACKAGE
										.getErrorCode());
						responseDTO.setMessage(
								ResponseMessageConstant.SERVICES_WITH_AMOUNT_ZERO_IS_ALLOWED_IN_DEFAULT_PACKAGE
										.getMessage(new Object[] { ServiceCategoryEntityOptional.get().getName() }));
						return responseDTO;
					}
				}
				serviceCategoryList.add(ServiceCategoryEntityOptional.get());
				serviceNames.add(ServiceCategoryEntityOptional.get().getName());
			} else {
				log.error("invalid serviceCategoryId passed :: {} ", serviceCategoryId);
			}
		}

		if (CollectionUtils.isEmpty(serviceCategoryList)) {
			responseDTO.setStatusCode(ResponseMessageConstant.INVALID_REQUEST_PARAMETER.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.INVALID_REQUEST_PARAMETER
					.getMessage(new Object[] { Constants.SERVICE_CATEGORY_IDS }));
			return responseDTO;
		}
		if (requestDTO.getAmount() > serviceAmountTotal) {
			responseDTO.setStatusCode(
					ResponseMessageConstant.PACKAGE_AMOUNT_GREATER_THAN_TOTAL_SERVICE_AMOUNT.getErrorCode());
			responseDTO
					.setMessage(ResponseMessageConstant.PACKAGE_AMOUNT_GREATER_THAN_TOTAL_SERVICE_AMOUNT.getMessage());
			return responseDTO;
		}
//		packageEntity.setCreatedBy(oauth2UserDetails.getId());
		packageEntity.setCreatedDate(new Date());
		packageEntity.setServceCategoryList(serviceCategoryList);
		packageRepository.save(packageEntity);
		if (!CollectionUtils.isEmpty(serviceNames)) {
			List<PackageHistory> packageHistoryList = new ArrayList<>();
			serviceNames.forEach(serviceName -> {
				PackageHistory packageHistory = new PackageHistory();
				packageHistory.setPackageName(requestDTO.getName());
				packageHistory.setPackageId(packageEntity.getId());
				packageHistory.setPackageAmount(requestDTO.getAmount());
				packageHistory.setPreviousAmount(null);
				packageHistory.setActionType(ActionType.ADD);
				if(StringUtils.isEmpty(requestDTO.getRemarks())) {
					packageHistory.setRemarks("New Package Added.");
				}else {
					packageHistory.setRemarks(requestDTO.getRemarks().trim());
				}
				
				packageHistory.setServiceName(serviceName);
				packageHistory.setPreviousStatus(null);
				packageHistory.setUpdatedStatus(requestDTO.getStatus());
				packageHistory.setActionDateTime(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
			//	packageHistory.setCreatedBy(oauth2UserDetails.getId());
				packageHistory.setCreatedDate(new Date());
				packageHistory.setIpAddress(commonUtil.getClientIP(request));
				packageHistoryList.add(packageHistory);
			});
			if (!CollectionUtils.isEmpty(packageHistoryList)) {
				packageHistoryRepository.saveAll(packageHistoryList);
			}
		}
		responseDTO.setStatusCode(ResponseMessageConstant.CREATE_SUCCESS_RESPONSE.getErrorCode());
		responseDTO.setMessage(ResponseMessageConstant.CREATE_SUCCESS_RESPONSE.getMessage());
		return responseDTO;
	}

	@Override
	@Transactional
	public BaseDTO update(PackageRequestDTO requestDTO) {
		log.info("class :PackageServiceImpl, method: update() :: request data :: {}", requestDTO);
		//Set<UUID> newServiceCategoryIds = null;
		//Set<UUID> toRemoveCategoryIds = null;
		Double previousAmount = null;
		BaseDTO responseDTO = validator.validate(requestDTO, Boolean.TRUE);
		
		if (ResponseMessageConstant.SUCCESS_RESPONSE.getErrorCode() != responseDTO.getStatusCode()) {
			return responseDTO;
		}
		Optional<PackageEntity> packageEntityOptional = packageRepository.findById(requestDTO.getId());
		if (!packageEntityOptional.isPresent()) {
			responseDTO.setStatusCode(ResponseMessageConstant.INVALID_REQUEST_PARAMETER.getErrorCode());
			responseDTO.setMessage(
					ResponseMessageConstant.INVALID_REQUEST_PARAMETER.getMessage(new Object[] { Constants.ID }));
			return responseDTO;
		}
		PackageEntity existingPackageEntity = packageEntityOptional.get();
		/*
		 * Optional<UUID> serviceIdWithAmountConfigMissing =
		 * requestDTO.getServiceCategoryIds().stream() .filter(id ->
		 * Objects.isNull(serviceCategoryFeeMappingService.getAmountByServiceCategoryId(
		 * id))) .findFirst(); if (serviceIdWithAmountConfigMissing.isPresent()) {
		 * responseDTO.setStatusCode(ResponseMessageConstant.
		 * SERVICE_FEE_MAPPING_NOT_CONFIGURED.getErrorCode());
		 * responseDTO.setMessage(ResponseMessageConstant.
		 * SERVICE_FEE_MAPPING_NOT_CONFIGURED .getMessage(new Object[] {
		 * serviceIdWithAmountConfigMissing.get() })); return responseDTO; } Boolean
		 * isPackageAlreadyExist =
		 * this.isPackageAlreadyExistWithSameServices(requestDTO); if
		 * (Boolean.TRUE.equals(isPackageAlreadyExist)) {
		 * responseDTO.setStatusCode(ResponseMessageConstant.
		 * PACKAGE_SERVICE_MAPPING_ALREADY_EXIST.getErrorCode());
		 * responseDTO.setMessage(ResponseMessageConstant.
		 * PACKAGE_SERVICE_MAPPING_ALREADY_EXIST.getMessage()); return responseDTO; }
		 */
		if (Boolean.TRUE.equals(existingPackageEntity.getIsDefaultPackage())) {
			/*
			 * Optional<UUID> serviceIdWithAmountGreaterThanZero =
			 * requestDTO.getServiceCategoryIds().stream() .filter(id ->
			 * serviceCategoryFeeMappingService.getAmountByServiceCategoryId(id) >
			 * 0).findFirst(); if (serviceIdWithAmountGreaterThanZero.isPresent()) {
			 * responseDTO.setStatusCode( ResponseMessageConstant.
			 * SERVICES_WITH_AMOUNT_ZERO_IS_ALLOWED_IN_DEFAULT_PACKAGE.getErrorCode());
			 * responseDTO.setMessage(ResponseMessageConstant.
			 * SERVICES_WITH_AMOUNT_ZERO_IS_ALLOWED_IN_DEFAULT_PACKAGE .getMessage(new
			 * Object[] { serviceIdWithAmountGreaterThanZero.get() })); return responseDTO;
			 * }
			 */
			if (requestDTO.getAmount() > 0) {
				responseDTO
						.setStatusCode(ResponseMessageConstant.DEFAULT_PACKAGE_AMOUNT_GREATER_THAN_ZERO.getErrorCode());
				responseDTO.setMessage(ResponseMessageConstant.DEFAULT_PACKAGE_AMOUNT_GREATER_THAN_ZERO.getMessage());
				return responseDTO;
			}
		}

		try {

			if (Objects.isNull(packageEntityOptional.get().getAmount())) {
				existingPackageEntity.setAmount(0.0);
			}
			final Double updatedAmount;
			Double serviceAmountTotal = 0.0;
			previousAmount=existingPackageEntity.getAmount();
			if(requestDTO.getAmount().equals(packageEntityOptional.get().getAmount())) {
				updatedAmount = null;
			}else {
				updatedAmount = requestDTO.getAmount();
			}
			if (requestDTO.getStatus().equals(existingPackageEntity.getStatus()) && Objects.isNull(updatedAmount)) {

				responseDTO.setStatusCode(ResponseMessageConstant.NO_CHANGE_FOUND_TO_UPDATE.getErrorCode());
				responseDTO.setMessage(
						ResponseMessageConstant.NO_CHANGE_FOUND_TO_UPDATE.getMessage(new Object[] { Constants.ID }));
				return responseDTO;

			}
			/*
			 * Set<UUID> existingServiceCategoryIds =
			 * packageEntityOptional.get().getServceCategoryList().stream() .map(p ->
			 * p.getId()).collect(Collectors.toSet()); if
			 * (!CollectionUtils.isEmpty(existingServiceCategoryIds)) { // add them
			 * newServiceCategoryIds = requestDTO.getServiceCategoryIds().stream()
			 * .filter(id ->
			 * !existingServiceCategoryIds.contains(id)).collect(Collectors.toSet());
			 * 
			 * // remove toRemoveCategoryIds = existingServiceCategoryIds.stream()
			 * .filter(id ->
			 * !requestDTO.getServiceCategoryIds().contains(id)).collect(Collectors.toSet())
			 * ; if (CollectionUtils.isEmpty(newServiceCategoryIds) &&
			 * CollectionUtils.isEmpty(toRemoveCategoryIds) &&
			 * Objects.isNull(updatedAmount)) {
			 * 
			 * responseDTO.setStatusCode(ResponseMessageConstant.NO_CHANGE_FOUND_TO_UPDATE.
			 * getErrorCode());
			 * responseDTO.setMessage(ResponseMessageConstant.NO_CHANGE_FOUND_TO_UPDATE
			 * .getMessage(new Object[] { Constants.ID })); return responseDTO;
			 * 
			 * } } else { newServiceCategoryIds = requestDTO.getServiceCategoryIds(); }
			 */
			if (Objects.nonNull(updatedAmount)) {
				PackageHistory packageHistory = new PackageHistory();
				packageHistory.setPackageName(existingPackageEntity.getName());
				packageHistory.setPackageId(existingPackageEntity.getId());
				packageHistory.setPackageAmount(updatedAmount);
				packageHistory.setPreviousAmount(previousAmount);
				packageHistory.setActionType(ActionType.MODIFY);
				packageHistory.setRemarks("Package Amount Updated.");
				packageHistory.setActionDateTime(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
		//		packageHistory.setCreatedBy(oauth2UserDetails.getId());
				packageHistory.setCreatedDate(new Date());
				packageHistory.setIpAddress(commonUtil.getClientIP(request));
				packageHistoryRepository.save(packageHistory);
			}
			for(ServiceCategoryEntity serviceCategoryEntity : existingPackageEntity.getServceCategoryList()) {
				Optional<ServiceCategoryFeeMappingEntity> serviceFeeMappingOptional = serviceCategoryFeeMappingRepository.findByServiceCategoryId(serviceCategoryEntity.getId());
				if(serviceFeeMappingOptional.isPresent()) {
					if (!serviceFeeMappingOptional.isPresent()
							|| Objects.isNull(serviceFeeMappingOptional.get().getAmount())) {
						responseDTO.setStatusCode(
								ResponseMessageConstant.SERVICE_FEE_MAPPING_NOT_CONFIGURED.getErrorCode());
						responseDTO.setMessage(ResponseMessageConstant.SERVICE_FEE_MAPPING_NOT_CONFIGURED
								.getMessage(new Object[] { serviceCategoryEntity.getName() }));
						return responseDTO;
					}
					serviceAmountTotal = serviceAmountTotal + serviceFeeMappingOptional.get().getAmount();

				}
			}
			if (requestDTO.getAmount() > serviceAmountTotal) {
				responseDTO.setStatusCode(
						ResponseMessageConstant.PACKAGE_AMOUNT_GREATER_THAN_TOTAL_SERVICE_AMOUNT.getErrorCode());
				responseDTO
						.setMessage(ResponseMessageConstant.PACKAGE_AMOUNT_GREATER_THAN_TOTAL_SERVICE_AMOUNT.getMessage());
				return responseDTO;
			}
			

			/*
			 * if (!CollectionUtils.isEmpty(toRemoveCategoryIds)) { List<PackageHistory>
			 * packageHistoryList = new ArrayList<>(); toRemoveCategoryIds.forEach(id -> {
			 * PackageHistory packageHistory = new PackageHistory();
			 * packageHistory.setPackageName(requestDTO.getName());
			 * packageHistory.setPackageAmount(Objects.nonNull(updatedAmount) ?
			 * requestDTO.getAmount() : existingPackageEntity.getAmount());
			 * packageHistory.setActionType(ActionType.DELETE);
			 * packageHistory.setRemarks("Service removed"); Optional<ServiceCategoryEntity>
			 * serviceCategoryEntityOptional = serviceCategoryRepository .findById(id); if
			 * (serviceCategoryEntityOptional.isPresent()) {
			 * packageHistory.setServiceName(serviceCategoryEntityOptional.get().getName());
			 * } else { log.error("invalid serviceCategoryId to remove :: {} ", id); return;
			 * }
			 * packageHistory.setActionDateTime(LocalDateTime.now(ZoneId.of("Asia/Kolkata"))
			 * ); packageHistory.setCreatedBy(oauth2UserDetails.getId());
			 * packageHistory.setCreatedDate(new Date());
			 * packageHistoryList.add(packageHistory); }); if
			 * (!CollectionUtils.isEmpty(packageHistoryList)) {
			 * packageHistoryRepository.saveAll(packageHistoryList); } } if
			 * (!CollectionUtils.isEmpty(newServiceCategoryIds)) { List<PackageHistory>
			 * packageHistoryList = new ArrayList<>(); newServiceCategoryIds.forEach(id -> {
			 * PackageHistory packageHistory = new PackageHistory();
			 * packageHistory.setPackageName(requestDTO.getName());
			 * packageHistory.setPackageAmount(Objects.nonNull(updatedAmount) ?
			 * requestDTO.getAmount() : existingPackageEntity.getAmount());
			 * packageHistory.setActionType(ActionType.MODIFY);
			 * packageHistory.setRemarks("New Service Added");
			 * Optional<ServiceCategoryEntity> serviceCategoryEntityOptional =
			 * serviceCategoryRepository .findById(id); if
			 * (serviceCategoryEntityOptional.isPresent()) {
			 * packageHistory.setServiceName(serviceCategoryEntityOptional.get().getName());
			 * } else { log.error("invalid serviceCategoryId to add :: {} ", id); return; }
			 * packageHistory.setActionDateTime(LocalDateTime.now(ZoneId.of("Asia/Kolkata"))
			 * ); packageHistory.setCreatedBy(oauth2UserDetails.getId());
			 * packageHistory.setCreatedDate(new Date());
			 * packageHistoryList.add(packageHistory);
			 * 
			 * }); if (!CollectionUtils.isEmpty(packageHistoryList)) {
			 * packageHistoryRepository.saveAll(packageHistoryList); } }
			 * Set<ServiceCategoryEntity> serviceCategoryList = new HashSet<>();
			 * requestDTO.getServiceCategoryIds().forEach(serviceCategoryId -> {
			 * Optional<ServiceCategoryEntity> ServiceCategoryEntityOptional =
			 * serviceCategoryRepository .findById(serviceCategoryId); if
			 * (ServiceCategoryEntityOptional.isPresent()) {
			 * serviceCategoryList.add(ServiceCategoryEntityOptional.get()); } else {
			 * log.error("invalid serviceCategoryId passed :: {} ", serviceCategoryId); }
			 * }); if (CollectionUtils.isEmpty(serviceCategoryList)) {
			 * responseDTO.setStatusCode(ResponseMessageConstant.INVALID_REQUEST_PARAMETER.
			 * getErrorCode());
			 * responseDTO.setMessage(ResponseMessageConstant.INVALID_REQUEST_PARAMETER
			 * .getMessage(new Object[] { Constants.SERVICE_CATEGORY_IDS })); return
			 * responseDTO; }
			 */
			if (Objects.nonNull(updatedAmount)) {
				existingPackageEntity.setAmount(updatedAmount);
			}
			if (!requestDTO.getStatus().equals(existingPackageEntity.getStatus())){
				PackageHistory packageHistory = new PackageHistory();
				packageHistory.setPackageName(existingPackageEntity.getName());
				packageHistory.setPackageId(existingPackageEntity.getId());
				packageHistory.setPackageAmount(updatedAmount);
				packageHistory.setPreviousAmount(previousAmount);
				packageHistory.setActionType(ActionType.MODIFY);
				packageHistory.setPreviousStatus(existingPackageEntity.getStatus());
				packageHistory.setUpdatedStatus(requestDTO.getStatus());
				packageHistory.setRemarks("Package Status Updated.");
				packageHistory.setActionDateTime(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
			//	packageHistory.setCreatedBy(oauth2UserDetails.getId());
				packageHistory.setCreatedDate(new Date());
				packageHistory.setIpAddress(commonUtil.getClientIP(request));
				packageHistoryRepository.save(packageHistory);
				existingPackageEntity.setStatus(requestDTO.getStatus());
			}
			
			//existingPackageEntity.setServceCategoryList(serviceCategoryList);
		//	existingPackageEntity.setModifiedBy(oauth2UserDetails.getId());
			existingPackageEntity.setModifiedDate(new Date());
			packageRepository.save(existingPackageEntity);
			responseDTO.setStatusCode(ResponseMessageConstant.UPDAE_RESPONSE.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.UPDAE_RESPONSE.getMessage());
			return responseDTO;
		} catch (Exception e) {
			responseDTO.setStatusCode(ResponseMessageConstant.FAILURE_RESPONSE.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.FAILED_TO_UPDATE.getMessage());
			return responseDTO;
		}
	}

	@Override
	public BaseDTO getByPackageId(UUID packageId) {
		log.info("class :PackageServiceImpl, method: getByPackageId() :: request data :: {}", packageId);

		BaseDTO responseDTO = new BaseDTO();
		Optional<PackageEntity> packageEntityOptional = packageRepository.findById(packageId);
		if (!packageEntityOptional.isPresent()) {
			responseDTO.setStatusCode(ResponseMessageConstant.NOT_FOUND.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.NOT_FOUND.getMessage());
			return responseDTO;
		}
		responseDTO.setMessage(ResponseMessageConstant.SEARCH_RESPONSE.getMessage());
		responseDTO.setStatusCode(ResponseMessageConstant.SEARCH_RESPONSE.getErrorCode());
		responseDTO.setResponseContent(packageMapper.convertEntityToResponseDTO(packageEntityOptional.get()));
		return responseDTO;
	}

	@Override
	public BaseDTO getBySearchFilter(PaginationRequestDTO requestDTO) {
		log.info("class :PackageServiceImpl, method: getBySearchFilter() :: request data :: {}", requestDTO);
		BaseDTO baseDTO = new BaseDTO();

		Long count = packageRepositoryImpl.getCountBySearchFields(requestDTO);
		log.info("total count :: {}", count);
		if (count > 0) {
			List<PackageEntity> packageEntityList = packageRepositoryImpl.getRecordsByFilterDTO(requestDTO);
			if (CollectionUtils.isEmpty(packageEntityList)) {
				log.info("no data found corresponding to :: {}", requestDTO.toString());
				throw new NoRecoerdFoundException(ResponseMessageConstant.NO_RECOERD_FOUND.getMessage());
			}
			List<PackageResponseDTO> responseList = packageEntityList.stream()
					.map(packageMapper::convertEntityToResponseDTO).collect(Collectors.toList());
			baseDTO.setResponseContents(responseList);
			baseDTO.setTotalRecords(Objects.nonNull(count) ? count.longValue() : null);
			baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			baseDTO.setMessage(ResponseMessageConstant.SEARCH_RESPONSE.getMessage());
			return baseDTO;
		} else {
			log.info("no data found corresponding to :: {}", requestDTO.toString());
			throw new NoRecoerdFoundException(ResponseMessageConstant.NO_RECOERD_FOUND.getMessage());
		}
	}

	@Override
	/*
	 * public BaseDTO getAllPackageListByStatus(Boolean status) { BaseDTO
	 * responseDTO = new BaseDTO(); List<PackageResponseDTO> responseList = new
	 * ArrayList<>(); try { List<PackageEntity> packageList =
	 * packageRepository.findByStatus(status); if
	 * (CollectionUtils.isEmpty(packageList)) {
	 * responseDTO.setStatusCode(ResponseMessageConstant.NOT_FOUND.getErrorCode());
	 * responseDTO.setMessage(ResponseMessageConstant.NOT_FOUND.getMessage());
	 * return responseDTO; } responseList = packageList.stream().map(p ->
	 * packageMapper.convertEntityToResponseDTO(p, Boolean.FALSE))
	 * .collect(Collectors.toList()); } catch (Exception e) {
	 * log.error("PackageServiceImpl getAllActivePackage :: {}", e);
	 * responseDTO.setStatusCode(ResponseMessageConstant.FAILED_TO_RETRIEVE.
	 * getErrorCode());
	 * responseDTO.setMessage(ResponseMessageConstant.FAILED_TO_RETRIEVE.getMessage(
	 * )); return responseDTO; } responseDTO.setResponseContent(responseList);
	 * responseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
	 * responseDTO.setMessage(ResponseMessageConstant.SEARCH_RESPONSE.getMessage());
	 * return responseDTO; }
	 */
	public BaseDTO getAllPackageListByStatus(Boolean status) {
		BaseDTO responseDTO = new BaseDTO();
		List<Map<String, String>> responseMap = null;
		try {
			if (Objects.isNull(status)) {
				responseMap = packageRepository.findAllPackages();
			} else {
				responseMap = packageRepository.findByStatus(status);
			}
			if (CollectionUtils.isEmpty(responseMap)) {
				responseDTO.setStatusCode(ResponseMessageConstant.NOT_FOUND.getErrorCode());
				responseDTO.setMessage(ResponseMessageConstant.NOT_FOUND.getMessage());
				return responseDTO;
			}

		} catch (Exception e) {
			log.error("PackageServiceImpl getAllActivePackage :: {}", e);
			responseDTO.setStatusCode(ResponseMessageConstant.FAILED_TO_RETRIEVE.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.FAILED_TO_RETRIEVE.getMessage());
			return responseDTO;
		}
		responseDTO.setResponseContents(responseMap);
		responseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
		responseDTO.setMessage(ResponseMessageConstant.SEARCH_RESPONSE.getMessage());
		return responseDTO;
	}

	public BaseDTO getDefaultPackage() {
		BaseDTO responseDTO = new BaseDTO();
		Optional<PackageEntity> packageEntityOptional = null;
		try {
			packageEntityOptional = packageRepository.findByIsDefaultPackage(Boolean.TRUE);

			if (!packageEntityOptional.isPresent()) {
				responseDTO.setStatusCode(ResponseMessageConstant.NOT_FOUND.getErrorCode());
				responseDTO.setMessage(ResponseMessageConstant.NOT_FOUND.getMessage());
				return responseDTO;
			}

		} catch (Exception e) {
			log.error("PackageServiceImpl getDefaultPackage :: {}", e);
			responseDTO.setStatusCode(ResponseMessageConstant.FAILED_TO_RETRIEVE.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.FAILED_TO_RETRIEVE.getMessage());
			return responseDTO;
		}
		responseDTO.setResponseContent(packageMapper.convertEntityToResponseDTO(packageEntityOptional.get()));
		responseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
		responseDTO.setMessage(ResponseMessageConstant.SEARCH_RESPONSE.getMessage());
		return responseDTO;
	}
	
	private Boolean isPackageAlreadyExistWithSameServices(PackageRequestDTO requestDTO) {
		List<PackageEntity> packageList = null;
		if (Objects.isNull(requestDTO.getId())) {
			packageList = packageRepository.findAll();
		} else {
			packageList = packageRepository.getAllPackagesNotInId(requestDTO.getId());
		}
		if (CollectionUtil.isEmpty(packageList)) {
			return false;
		}
		for (PackageEntity packageEntity : packageList) {
			if (packageEntity.getServceCategoryList().size() != requestDTO.getServiceCategoryIds().size()) {
				continue;
			}
			Set<UUID> existingServiceIds = packageEntity.getServceCategoryList().stream().map(ServiceCategoryEntity::getId).collect(Collectors.toSet());
			if (CollectionUtil.isEmpty(existingServiceIds)) {
				continue;
			}
			if (existingServiceIds.equals(requestDTO.getServiceCategoryIds())) {
				return true;
			}
		}

		return false;
	}

}
