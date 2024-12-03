package com.oasys.uppcl_user__master_management.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.uppcl_user__master_management.UppclUserMasterManagementApplication;
import com.oasys.uppcl_user__master_management.dao.ServiceCategoryDao;
import com.oasys.uppcl_user__master_management.dto.BaseDTOMinMax;
import com.oasys.uppcl_user__master_management.dto.BaseRespServiceChrgDto;
import com.oasys.uppcl_user__master_management.dto.MinMaxChargeDto;
import com.oasys.uppcl_user__master_management.dto.ServiceCategoryDTO;
import com.oasys.uppcl_user__master_management.dto.ServiceChargesDTO;
import com.oasys.uppcl_user__master_management.dto.ServiceChargesResDTO;
import com.oasys.uppcl_user__master_management.dto.SlabDetailsDTO;
import com.oasys.uppcl_user__master_management.entity.ServiceCategoryEntity;
import com.oasys.uppcl_user__master_management.entity.ServiceProviderEntity;
import com.oasys.uppcl_user__master_management.entity.SubCategoryEntity;
import com.oasys.uppcl_user__master_management.repository.SubCategoryRepository;
import com.oasys.uppcl_user__master_management.response.GstResponse;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.ServiceCategoryService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ServiceCategoryServiceImpl implements ServiceCategoryService {

	@Value("${commision}")
	public String commision;

	@Value("${setting.api}")
	public String settingApi;

	@Autowired(required=true)
	ServiceCategoryDao serviceCategoryDao;

	@Autowired(required=true)
	SubCategoryRepository subCategoryRepository;

	@Autowired(required=true)
	MessageSource messageSource;

	@Autowired(required=true)
	RestTemplate restTemplate;

	@Autowired(required=true)
	HttpServletRequest headerRequest;

	@Autowired(required=true)
	UppclUserMasterManagementApplication uppclUserMasterManagementApplication;

	@Autowired(required=true)
	ObjectMapper mapper;

	@Override
	public BaseDTO create(ServiceCategoryDTO dto) {
		// log.info(" <----- ServiceCategoryServiceImpl.create() Service STARTED ----->O
		// ");
		BaseDTO response = new BaseDTO();
		try {
			response = serviceCategoryDao.create(dto);
		} catch (DataIntegrityViolationException e) {
			log.error("<---- ServiceCategoryServiceImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);

		} catch (Exception e) {
			log.error("<---- ServiceCategoryServiceImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		// log.info(" <----- ServiceCategoryServiceImpl.create() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getById(UUID id) {
		// log.info(" <----- ServiceCategoryServiceImpl.getById() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = serviceCategoryDao.getById(id);
		} catch (Exception e) {
			log.error("<---- ServiceCategoryServiceImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);

		}
		// log.info(" <----- ServiceCategoryServiceImpl.getById() END -----> ");
		return response;
	}

	@Override
	public BaseDTO update(UUID id, ServiceCategoryDTO dto) {
		// log.info(" <-----ServiceCategoryServiceImpl.update() Service STARTED ----->
		// ");
		BaseDTO response = new BaseDTO();
		try {
			response = serviceCategoryDao.update(id, dto);
		} catch (DataIntegrityViolationException e) {
			log.error("<---- ServiceCategoryServiceImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);

		} catch (Exception e) {
			log.error("<---- ServiceCategoryServiceImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		// log.info(" <-----ServiceCategoryServiceImpl.update() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		// log.info(" <-----ServiceCategoryServiceImpl.getAllActive() Service STARTED
		// -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = serviceCategoryDao.getAllActive();
		} catch (Exception e) {
			log.error("<---- ServiceCategoryServiceImpl.getAllActive() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,
					Locale.US);
			response.setMessage(msg);

		}
		// log.info(" <-----ServiceCategoryServiceImpl.getAllActive() Service END ----->
		// ");
		return response;
	}

	@Override
	public BaseDTO delete(UUID id) {
		// log.info(" <----- ServiceCategoryServiceImpl.delete()Service STARTED ----->
		// ");
		BaseDTO response = new BaseDTO();
		try {
			response = serviceCategoryDao.delete(id);
		} catch (Exception e) {
			log.error("<---- ServiceCategoryServiceImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);

		}
		// log.info(" <----- ServiceCategoryServiceImpl.delete()Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getByName(String name) {
		// log.info(" <----- ServiceCategoryServiceImpl.getById() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = serviceCategoryDao.getByName(name);
		} catch (Exception e) {
			log.error("<---- ServiceCategoryServiceImpl.getByName() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);

		}
		// log.info(" <----- ServiceCategoryServiceImpl.getById() END -----> ");
		return response;
	}

	@Override
	public BaseDTO getChargesByName(String userUUID, String name, Double amount, String subCategory) {
		// log.info(" <----- ServiceCategoryServiceImpl.getById() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			SubCategoryEntity subCategoryEntity = subCategoryRepository.findByConstantName(subCategory);
			if(name.equalsIgnoreCase("Razor Pay"))
			{
				name = "Razorpay";
			}
			ServiceProviderEntity serviceProviderEntity = serviceCategoryDao.getChargesByName(name);
			log.info("id ===============> " + serviceProviderEntity);
			if (subCategoryEntity != null) {
				response = getCommission(userUUID, serviceProviderEntity, subCategoryEntity, amount);

				log.info("response from commission ===> " + response);

			}

		} catch (Exception e) {
			log.error("<---- ServiceCategoryServiceImpl.getChargesByName() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);

		}
		// log.info(" <----- ServiceCategoryServiceImpl.getById() END -----> ");
		return response;
	}
	
	
	
	private BaseDTO getCommission(String userUUID, ServiceProviderEntity serviceProviderEntity, SubCategoryEntity subCategoryEntity, Double amount) {
		BaseDTO response = new BaseDTO();

		String stringCommisionResposne = null;
		try {
			String access_token = headerRequest.getHeader("X-Authorization");

			HttpHeaders header = new HttpHeaders();
			header.set("Content-type", "application/json");
			header.set("X-Authorization", access_token);

			HttpEntity<String> getCommisionResponse = new HttpEntity<>(header);

			

			String url = commision + "/commission/v2/applicableCommissionOrOffer/" + userUUID + "/"
					+ subCategoryEntity.getServiceCategoryId().getId()+"/"+subCategoryEntity.getId();

			String urlTemplate;
			if (serviceProviderEntity != null) {

				urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
						.queryParam("amount", String.valueOf(amount))
						.queryParam("serviceProvierId", String.valueOf(serviceProviderEntity.getId())).encode()
						.toUriString();
			} else {
				urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
						.queryParam("amount", String.valueOf(amount))
						.toUriString();
			}

			log.warn("commision api Request=====>{}", getCommisionResponse + " url ===> " + urlTemplate);
			uppclUserMasterManagementApplication.disableSSL();
			BaseRespServiceChrgDto commisionResponse = restTemplate
					.exchange(urlTemplate, HttpMethod.GET, getCommisionResponse, BaseRespServiceChrgDto.class)
					.getBody();
			log.warn("commision api Response =====>{}", commisionResponse);
			// int status = commisionResponse.get("statusCode").asInt();
			// String msg = commisionResponse.get("message").toString();
			// Object obj = commisionResponse.get("responseContent");
			if (commisionResponse.getStatusCode() != 200 || commisionResponse.getResponseContent() == null) {
				log.warn(
						"**************************** Commison Response Issue ********************************************************");
				response.setStatusCode(commisionResponse.getStatusCode());
				response.setMessage("Technical Issue. We can't process the transaction.");
				response.setResponseContent(null);
			} else {


				List<ServiceChargesDTO> serviceChargesDTO = commisionResponse.getResponseContent();

				log.info("serviceChargesDTO ===> "+serviceChargesDTO);
				for (ServiceChargesDTO dto : serviceChargesDTO) {
				
						SlabDetailsDTO slabDetailsDTO = new SlabDetailsDTO();

					
						if(dto.getCommission().getAsdrCommsionCharges() != null && Double.compare(dto.getCommission().getAsdrCommsionCharges(), 0.0) != 0 ) {
							slabDetailsDTO.setCharges(dto.getCommission().getAsdrCommsionCharges());
							}else if(dto.getCommission().getDdCommisionValue() != null &&  Double.compare(dto.getCommission().getDdCommisionValue(), 0.0) != 0)
							{
								slabDetailsDTO.setCharges(dto.getCommission().getDdCommisionValue());
							}else if(dto.getCommission().getPcdCommisionValue() != null &&  Double.compare(dto.getCommission().getPcdCommisionValue(), 0.0) != 0)
							{
								slabDetailsDTO.setCharges(dto.getCommission().getPcdCommisionValue());
							}else 
							{
								slabDetailsDTO.setCharges(dto.getCommission().getRCommisionValue());
							}

						
						
						response.setStatusCode(commisionResponse.getStatusCode());
						response.setMessage(commisionResponse.getMessage());
						response.setResponseContent(slabDetailsDTO);

					

				}

			}

		} catch (Exception e) {
			log.error("error in get commision ", e);
		}
		return response;

	}

	/*
	 * private BaseDTO getCommission(String userUUID, ServiceProviderEntity
	 * serviceProviderEntity, Double amount) { BaseDTO response = new BaseDTO();
	 * 
	 * String stringCommisionResposne = null; try { String access_token =
	 * headerRequest.getHeader("X-Authorization");
	 * 
	 * HttpHeaders header = new HttpHeaders(); header.set("Content-type",
	 * "application/json"); header.set("X-Authorization", access_token);
	 * 
	 * HttpEntity<String> getCommisionResponse = new HttpEntity<>(header);
	 * 
	 * Map<String, String> uriVariables = new HashMap<>();
	 * 
	 * if (amount != null) { uriVariables.put("amount", String.valueOf(amount)); }
	 * if (serviceProviderEntity.getId() != null) {
	 * log.info("service provider id --- > " + serviceProviderEntity.getId());
	 * uriVariables.put("serviceProvierId",
	 * String.valueOf(serviceProviderEntity.getId())); }
	 * 
	 * String url = commision + "/commission/v2/applicableCommissionOrOffer/" +
	 * userUUID + "/" + serviceProviderEntity.getServiceCategoryId().getId();
	 * 
	 * String urlTemplate; if (amount != null) {
	 * 
	 * urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
	 * 
	 * .queryParam("amount", String.valueOf(amount)) .queryParam("serviceProvierId",
	 * String.valueOf(serviceProviderEntity.getId())).encode() .toUriString(); }
	 * else { urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
	 * .queryParam("serviceProvierId",
	 * String.valueOf(serviceProviderEntity.getId())).encode() .toUriString(); }
	 * 
	 * log.warn("commision api Request=====>{}", getCommisionResponse + " url ===> "
	 * + urlTemplate); masterManagementApplication.disableSSL();
	 * BaseRespServiceChrgDto commisionResponse = restTemplate
	 * .exchange(urlTemplate, HttpMethod.GET, getCommisionResponse,
	 * BaseRespServiceChrgDto.class) .getBody();
	 * log.warn("commision api Response =====>{}", commisionResponse); // int status
	 * = commisionResponse.get("statusCode").asInt(); // String msg =
	 * commisionResponse.get("message").toString(); // Object obj =
	 * commisionResponse.get("responseContent"); if
	 * (commisionResponse.getStatusCode() != 200 ||
	 * commisionResponse.getResponseContent() == null) { log.warn(
	 * "**************************** Commison Response Issue ********************************************************"
	 * ); response.setStatusCode(commisionResponse.getStatusCode());
	 * response.setMessage("Technical Issue. We can't process the transaction.");
	 * response.setResponseContent(null); } else {
	 * 
	 * 
	 * MinMaxChargeDto minMaxChargeDto = new MinMaxChargeDto(); BaseDTOMinMax
	 * minMaxResponse =
	 * getFtMinMaxAmount(serviceProviderEntity.getServiceCategoryId().getId());
	 * 
	 * 
	 * 
	 * if(minMaxResponse.getStatusCode() == 200) { Object obj =
	 * minMaxResponse.getResponseContents(); List<ServiceCategoryGlobalSettingDto>
	 * participantJson = mapper.convertValue(obj, new
	 * TypeReference<List<ServiceCategoryGlobalSettingDto>>() { });
	 * log.info("Min Max charge dto Response ...." + participantJson);
	 * 
	 * if (!participantJson.isEmpty()) { for (ServiceCategoryGlobalSettingDto
	 * minMaxChargeDtoRes : participantJson) {
	 * if(minMaxChargeDtoRes.getSubCategoryName().equalsIgnoreCase("Debit Card")) {
	 * minMaxChargeDto.setDcsdMaximumAmount(minMaxChargeDtoRes.getSdMaxCharges());
	 * minMaxChargeDto.setDcsdMinimumAmount(minMaxChargeDtoRes.getSdMinCharges());
	 * minMaxChargeDto.setDcdMaximumAmount(minMaxChargeDtoRes.getDMaxCharges());
	 * minMaxChargeDto.setDcdMinimumAmount(minMaxChargeDtoRes.getDMinCharges());
	 * minMaxChargeDto.setDcrMaximumAmount(minMaxChargeDtoRes.getRMaxCharges());
	 * minMaxChargeDto.setDcrMinimumAmount(minMaxChargeDtoRes.getRMinCharges());
	 * }else if(minMaxChargeDtoRes.getSubCategoryName().
	 * equalsIgnoreCase("Debit Card_Rupay")) {
	 * minMaxChargeDto.setDcrsdMaximumAmount(minMaxChargeDtoRes.getSdMaxCharges());
	 * minMaxChargeDto.setDcrsdMinimumAmount(minMaxChargeDtoRes.getSdMinCharges());
	 * minMaxChargeDto.setDcrdMaximumAmount(minMaxChargeDtoRes.getDMaxCharges());
	 * minMaxChargeDto.setDcrdMinimumAmount(minMaxChargeDtoRes.getDMinCharges());
	 * minMaxChargeDto.setDcrrMaximumAmount(minMaxChargeDtoRes.getRMaxCharges());
	 * minMaxChargeDto.setDcrrMinimumAmount(minMaxChargeDtoRes.getRMinCharges());
	 * }else
	 * if(minMaxChargeDtoRes.getSubCategoryName().equalsIgnoreCase("Credit Card")) {
	 * minMaxChargeDto.setDcrsdMaximumAmount(minMaxChargeDtoRes.getSdMaxCharges());
	 * minMaxChargeDto.setCcsdMinimumAmount(minMaxChargeDtoRes.getSdMinCharges());
	 * minMaxChargeDto.setCcsdMaximumAmount(minMaxChargeDtoRes.getDMaxCharges());
	 * minMaxChargeDto.setCcdMinimumAmount(minMaxChargeDtoRes.getDMinCharges());
	 * minMaxChargeDto.setCcrMaximumAmount(minMaxChargeDtoRes.getRMaxCharges());
	 * minMaxChargeDto.setCcrMinimumAmount(minMaxChargeDtoRes.getRMinCharges());
	 * }else if(minMaxChargeDtoRes.getSubCategoryName().equalsIgnoreCase("Wallet"))
	 * {
	 * minMaxChargeDto.setWalletsdMaximumAmount(minMaxChargeDtoRes.getSdMaxCharges()
	 * );
	 * minMaxChargeDto.setWalletsdMinimumAmount(minMaxChargeDtoRes.getSdMinCharges()
	 * );
	 * minMaxChargeDto.setWalletdMaximumAmount(minMaxChargeDtoRes.getDMaxCharges());
	 * minMaxChargeDto.setWalletdMinimumAmount(minMaxChargeDtoRes.getDMinCharges());
	 * minMaxChargeDto.setWalletrMaximumAmount(minMaxChargeDtoRes.getRMaxCharges());
	 * minMaxChargeDto.setWalletrMinimumAmount(minMaxChargeDtoRes.getRMinCharges());
	 * }else if(minMaxChargeDtoRes.getSubCategoryName().equalsIgnoreCase("UPI")) {
	 * minMaxChargeDto.setUpisdMaximumAmount(minMaxChargeDtoRes.getSdMaxCharges());
	 * minMaxChargeDto.setUpisdMinimumAmount(minMaxChargeDtoRes.getSdMinCharges());
	 * minMaxChargeDto.setUpidMaximumAmount(minMaxChargeDtoRes.getDMaxCharges());
	 * minMaxChargeDto.setUpidMinimumAmount(minMaxChargeDtoRes.getDMinCharges());
	 * minMaxChargeDto.setUpirMaximumAmount(minMaxChargeDtoRes.getRMaxCharges());
	 * minMaxChargeDto.setUpirMinimumAmount(minMaxChargeDtoRes.getRMinCharges());
	 * }else
	 * if(minMaxChargeDtoRes.getSubCategoryName().equalsIgnoreCase("Netbanking")) {
	 * minMaxChargeDto.setNetbankingsdMaximumAmount(minMaxChargeDtoRes.
	 * getSdMaxCharges());
	 * minMaxChargeDto.setNetbankingsdMinimumAmount(minMaxChargeDtoRes.
	 * getSdMinCharges());
	 * minMaxChargeDto.setNetbankingdMaximumAmount(minMaxChargeDtoRes.getDMaxCharges
	 * ());
	 * minMaxChargeDto.setNetbankingdMinimumAmount(minMaxChargeDtoRes.getDMinCharges
	 * ());
	 * minMaxChargeDto.setNetbankingrMaximumAmount(minMaxChargeDtoRes.getRMaxCharges
	 * ());
	 * minMaxChargeDto.setNetbankingrMinimumAmount(minMaxChargeDtoRes.getRMinCharges
	 * ()); } } } }
	 * 
	 * Double gstAmount = getGstAmount();
	 * 
	 * 
	 * List<ServiceChargesDTO> serviceChargesDTO =
	 * commisionResponse.getResponseContent();
	 * 
	 * // ServiceChargesResDTO serviceChargesResDTO = null;
	 * List<ServiceChargesResDTO> serChargesResDTOs = new
	 * ArrayList<ServiceChargesResDTO>();
	 * 
	 * for (ServiceChargesDTO dto : serviceChargesDTO) { if
	 * (Boolean.FALSE.equals(CollectionUtils.isEmpty(serChargesResDTOs)) &&
	 * serChargesResDTOs.stream().anyMatch(element -> element.getConstantName()
	 * .equals(dto.getCommission().getSubCategoryConstantName()))) {
	 * 
	 * for (Iterator<ServiceChargesResDTO> it = serChargesResDTOs.iterator();
	 * it.hasNext();) { ServiceChargesResDTO record = it.next(); if
	 * (dto.getCommission().getSubCategoryConstantName().equals(record.
	 * getConstantName())) { SlabDetailsDTO slDto = new SlabDetailsDTO(); if
	 * (dto.getCommission().getSlot() != null) { String[] slot =
	 * dto.getCommission().getSlot().split("-");
	 * slDto.setMinSlot(String.valueOf(slot[0]));
	 * slDto.setMaxSlot(String.valueOf(slot[1])); }
	 * 
	 * slDto.setChargingType(dto.getCommission().getChargingType());
	 * slDto.setCharges(dto.getCommission().getAsdrCommsionCharges());
	 * slDto.setGstType(dto.getCommission().getGstType());
	 * slDto.setServiceType(dto.getCommission().getServiceCharge());
	 * 
	 * record.getSlabDetails().add(slDto); break;
	 * 
	 * }
	 * 
	 * } } else { ServiceChargesResDTO serviceChargesResDTO = new
	 * ServiceChargesResDTO(); List<SlabDetailsDTO> slabDTO = new
	 * ArrayList<SlabDetailsDTO>(); SlabDetailsDTO slabDetailsDTO = new
	 * SlabDetailsDTO();
	 * 
	 * if (dto.getCommission().getSubCategoryConstantName().
	 * equalsIgnoreCase("Debit Card")) {
	 * serviceChargesResDTO.setConstantName(dto.getCommission().
	 * getSubCategoryConstantName()); serviceChargesResDTO.setSdMinAmount((double)
	 * 10); serviceChargesResDTO.setSdMaxAmount((double) 25000);
	 * serviceChargesResDTO.setDMinAmount((double) 10);
	 * serviceChargesResDTO.setDMaxAmount((double) 25000);
	 * serviceChargesResDTO.setRMinAmount((double) 10);
	 * serviceChargesResDTO.setRMaxAmount((double) 25000);
	 * serviceChargesResDTO.setGst((double) 18); } else if
	 * (dto.getCommission().getSubCategoryConstantName()
	 * .equalsIgnoreCase("Debit Card Rupay")) {
	 * serviceChargesResDTO.setConstantName(dto.getCommission().
	 * getSubCategoryConstantName()); serviceChargesResDTO.setSdMinAmount((double)
	 * 10); serviceChargesResDTO.setSdMaxAmount((double) 25000);
	 * serviceChargesResDTO.setDMinAmount((double) 10);
	 * serviceChargesResDTO.setDMaxAmount((double) 25000);
	 * serviceChargesResDTO.setRMinAmount((double) 10);
	 * serviceChargesResDTO.setRMaxAmount((double) 25000);
	 * serviceChargesResDTO.setGst((double) 18); } else if
	 * (dto.getCommission().getSubCategoryConstantName().
	 * equalsIgnoreCase("Credit Card")) {
	 * serviceChargesResDTO.setConstantName(dto.getCommission().
	 * getSubCategoryConstantName()); serviceChargesResDTO.setSdMinAmount((double)
	 * 10); serviceChargesResDTO.setSdMaxAmount((double) 25000);
	 * serviceChargesResDTO.setDMinAmount((double) 10);
	 * serviceChargesResDTO.setDMaxAmount((double) 25000);
	 * serviceChargesResDTO.setRMinAmount((double) 10);
	 * serviceChargesResDTO.setRMaxAmount((double) 25000);
	 * serviceChargesResDTO.setGst((double) 18); } else if
	 * (dto.getCommission().getSubCategoryConstantName().equalsIgnoreCase("Wallet"))
	 * { serviceChargesResDTO.setConstantName(dto.getCommission().
	 * getSubCategoryConstantName()); serviceChargesResDTO.setSdMinAmount((double)
	 * 10); serviceChargesResDTO.setSdMaxAmount((double) 10000);
	 * serviceChargesResDTO.setDMinAmount((double) 10);
	 * serviceChargesResDTO.setDMaxAmount((double) 10000);
	 * serviceChargesResDTO.setRMinAmount((double) 10);
	 * serviceChargesResDTO.setRMaxAmount((double) 10000);
	 * serviceChargesResDTO.setGst((double) 18); } else if
	 * (dto.getCommission().getSubCategoryConstantName().equalsIgnoreCase("UPI")) {
	 * serviceChargesResDTO.setConstantName(dto.getCommission().
	 * getSubCategoryConstantName()); serviceChargesResDTO.setSdMinAmount((double)
	 * 1); serviceChargesResDTO.setSdMaxAmount((double) 100000);
	 * serviceChargesResDTO.setDMinAmount((double) 1);
	 * serviceChargesResDTO.setDMaxAmount((double) 100000);
	 * serviceChargesResDTO.setRMinAmount((double) 1);
	 * serviceChargesResDTO.setRMaxAmount((double) 100000);
	 * serviceChargesResDTO.setGst((double) 18); } else if
	 * (dto.getCommission().getSubCategoryConstantName().equalsIgnoreCase(
	 * "Netbanking")) { serviceChargesResDTO.setConstantName(dto.getCommission().
	 * getSubCategoryConstantName()); serviceChargesResDTO.setSdMinAmount((double)
	 * 10); serviceChargesResDTO.setSdMaxAmount((double) 25000);
	 * serviceChargesResDTO.setDMinAmount((double) 10);
	 * serviceChargesResDTO.setDMaxAmount((double) 25000);
	 * serviceChargesResDTO.setRMinAmount((double) 10);
	 * serviceChargesResDTO.setRMaxAmount((double) 25000);
	 * serviceChargesResDTO.setGst((double) 18); }
	 * 
	 * if (dto.getCommission().getSlot() != null) { String[] slot =
	 * dto.getCommission().getSlot().split("-");
	 * slabDetailsDTO.setMinSlot(String.valueOf(slot[0]));
	 * slabDetailsDTO.setMaxSlot(String.valueOf(slot[1])); }
	 * 
	 * slabDetailsDTO.setChargingType(dto.getCommission().getChargingType());
	 * slabDetailsDTO.setCharges(dto.getCommission().getAsdrCommsionCharges());
	 * slabDetailsDTO.setGstType(dto.getCommission().getGstType());
	 * slabDetailsDTO.setServiceType(dto.getCommission().getServiceCharge());
	 * 
	 * slabDTO.add(slabDetailsDTO); serviceChargesResDTO.setSlabDetails(slabDTO);
	 * 
	 * serChargesResDTOs.add(serviceChargesResDTO);
	 * 
	 * response.setStatusCode(commisionResponse.getStatusCode());
	 * response.setMessage(commisionResponse.getMessage());
	 * response.setResponseContent(serChargesResDTOs);
	 * 
	 * }
	 * 
	 * }
	 * 
	 * }
	 * 
	 * } catch (Exception e) { log.error("error in get commision ", e); } return
	 * response;
	 * 
	 * }
	 */

	@Override
	public BaseDTO getAdhaarPayCharges(String userUUID, String name, Double amount,String subCategory) {
		// log.info(" <----- ServiceCategoryServiceImpl.getById() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			SubCategoryEntity subCategoryEntity = subCategoryRepository.findByConstantName(subCategory);
			ServiceCategoryEntity serviceCategoryEntity = serviceCategoryDao.getByNameIgnoreCase("Aadhaar Pay");
			if (subCategoryEntity != null) {
				ServiceProviderEntity serviceProviderEntity = serviceCategoryDao.getAadhaarChargesByName(name,
						subCategoryEntity.getServiceCategoryId().getId());
				log.info("id ===============> " + serviceProviderEntity);

				if (serviceProviderEntity != null) {
					response = getCommissionForAadhaarPay(userUUID, serviceProviderEntity, subCategoryEntity, amount);

					log.info("response from commission ===> " + response);
				}
			}

		} catch (Exception e) {
			log.error("<---- ServiceCategoryServiceImpl.getChargesByName() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);

		}
		// log.info(" <----- ServiceCategoryServiceImpl.getById() END -----> ");
		return response;
	}

	private BaseDTO getCommissionForAadhaarPay(String userUUID, ServiceProviderEntity serviceProviderEntity,
			SubCategoryEntity subCategoryEntity, Double amount) {
		BaseDTO response = new BaseDTO();

		String stringCommisionResposne = null;
		try {
			String access_token = headerRequest.getHeader("X-Authorization");

			HttpHeaders header = new HttpHeaders();
			header.set("Content-type", "application/json");
			header.set("X-Authorization", access_token);

			HttpEntity<String> getCommisionResponse = new HttpEntity<>(header);

			// Map<String, String> uriVariables = new HashMap<>();

			/*
			 * if(amount != null) { uriVariables.put("amount", String.valueOf(amount)); }
			 * if(serviceProviderEntity.getId() != null) {
			 * log.info("service provider id --- > "+serviceProviderEntity.getId());
			 * uriVariables.put("serviceProvierId",
			 * String.valueOf(serviceProviderEntity.getId())); }
			 */

			String url = commision + "/commission/v2/applicableCommissionOrOffer/" + userUUID + "/"
					+ subCategoryEntity.getServiceCategoryId().getId()+"/"+subCategoryEntity.getId();

			String urlTemplate;
			if (serviceProviderEntity != null) {

				urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
						.queryParam("amount", String.valueOf(amount))
						.queryParam("serviceProvierId", String.valueOf(serviceProviderEntity.getId())).encode()
						.toUriString();
			} else {
				urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
						.queryParam("amount", String.valueOf(amount))
						.toUriString();
			}

			log.warn("commision api Request=====>{}", getCommisionResponse + " url ===> " + urlTemplate);
			uppclUserMasterManagementApplication.disableSSL();
			BaseRespServiceChrgDto commisionResponse = restTemplate
					.exchange(urlTemplate, HttpMethod.GET, getCommisionResponse, BaseRespServiceChrgDto.class)
					.getBody();
			log.warn("commision api Response =====>{}", commisionResponse);
			// int status = commisionResponse.get("statusCode").asInt();
			// String msg = commisionResponse.get("message").toString();
			// Object obj = commisionResponse.get("responseContent");
			if (commisionResponse.getStatusCode() != 200 || commisionResponse.getResponseContent() == null) {
				log.warn(
						"**************************** Commison Response Issue ********************************************************");
				response.setStatusCode(commisionResponse.getStatusCode());
				response.setMessage("Technical Issue. We can't process the transaction.");
				response.setResponseContent(null);
			} else {
				List<ServiceChargesDTO> serviceChargesDTO = commisionResponse.getResponseContent();

				// ServiceChargesResDTO serviceChargesResDTO = null;
				List<ServiceChargesResDTO> serChargesResDTOs = new ArrayList<ServiceChargesResDTO>();

				for (ServiceChargesDTO dto : serviceChargesDTO) {

					SlabDetailsDTO slabDetailsDTO = new SlabDetailsDTO();

					if(dto.getCommission().getAsdrCommsionCharges() != null && Double.compare(dto.getCommission().getAsdrCommsionCharges(), 0.0) != 0 ) {
						slabDetailsDTO.setCharges(dto.getCommission().getAsdrCommsionCharges());
						}else if(dto.getCommission().getDdCommisionValue() != null &&  Double.compare(dto.getCommission().getDdCommisionValue(), 0.0) != 0)
						{
							slabDetailsDTO.setCharges(dto.getCommission().getDdCommisionValue());
						}else if(dto.getCommission().getPcdCommisionValue() != null &&  Double.compare(dto.getCommission().getPcdCommisionValue(), 0.0) != 0)
						{
							slabDetailsDTO.setCharges(dto.getCommission().getPcdCommisionValue());
						}else 
						{
							slabDetailsDTO.setCharges(dto.getCommission().getRCommisionValue());
						}


					response.setStatusCode(commisionResponse.getStatusCode());
					response.setMessage(commisionResponse.getMessage());
					response.setResponseContent(slabDetailsDTO);

				}

			}

		} catch (Exception e) {
			log.error("error in get commision for aadhaar pay", e);
		}
		return response;
	}

	/*
	 * private BaseDTO getCommissionForAadhaarPay(String userUUID,
	 * ServiceProviderEntity serviceProviderEntity, Double amount) { BaseDTO
	 * response = new BaseDTO();
	 * 
	 * String stringCommisionResposne = null; try { String access_token =
	 * headerRequest.getHeader("X-Authorization");
	 * 
	 * HttpHeaders header = new HttpHeaders(); header.set("Content-type",
	 * "application/json"); header.set("X-Authorization", access_token);
	 * 
	 * HttpEntity<String> getCommisionResponse = new HttpEntity<>(header);
	 * 
	 * // Map<String, String> uriVariables = new HashMap<>();
	 * 
	 * 
	 * if(amount != null) { uriVariables.put("amount", String.valueOf(amount)); }
	 * if(serviceProviderEntity.getId() != null) {
	 * log.info("service provider id --- > "+serviceProviderEntity.getId());
	 * uriVariables.put("serviceProvierId",
	 * String.valueOf(serviceProviderEntity.getId())); }
	 * 
	 * 
	 * String url = commision + "/commission/v2/applicableCommissionOrOffer/" +
	 * userUUID + "/" + serviceProviderEntity.getServiceCategoryId().getId();
	 * 
	 * String urlTemplate; if (amount != null) {
	 * 
	 * urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
	 * 
	 * .queryParam("amount", String.valueOf(amount)) .queryParam("serviceProvierId",
	 * String.valueOf(serviceProviderEntity.getId())).encode() .toUriString(); }
	 * else { urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
	 * .queryParam("serviceProvierId",
	 * String.valueOf(serviceProviderEntity.getId())).encode() .toUriString(); }
	 * 
	 * log.warn("commision api Request=====>{}", getCommisionResponse + " url ===> "
	 * + urlTemplate); masterManagementApplication.disableSSL();
	 * BaseRespServiceChrgDto commisionResponse = restTemplate
	 * .exchange(urlTemplate, HttpMethod.GET, getCommisionResponse,
	 * BaseRespServiceChrgDto.class) .getBody();
	 * log.warn("commision api Response =====>{}", commisionResponse); // int status
	 * = commisionResponse.get("statusCode").asInt(); // String msg =
	 * commisionResponse.get("message").toString(); // Object obj =
	 * commisionResponse.get("responseContent"); if
	 * (commisionResponse.getStatusCode() != 200 ||
	 * commisionResponse.getResponseContent() == null) { log.warn(
	 * "**************************** Commison Response Issue ********************************************************"
	 * ); response.setStatusCode(commisionResponse.getStatusCode());
	 * response.setMessage("Technical Issue. We can't process the transaction.");
	 * response.setResponseContent(null); } else { List<ServiceChargesDTO>
	 * serviceChargesDTO = commisionResponse.getResponseContent();
	 * 
	 * // ServiceChargesResDTO serviceChargesResDTO = null;
	 * List<ServiceChargesResDTO> serChargesResDTOs = new
	 * ArrayList<ServiceChargesResDTO>();
	 * 
	 * for (ServiceChargesDTO dto : serviceChargesDTO) { if
	 * (Boolean.FALSE.equals(CollectionUtils.isEmpty(serChargesResDTOs)) &&
	 * serChargesResDTOs.stream().anyMatch(element -> element.getConstantName()
	 * .equals(dto.getCommission().getSubCategoryConstantName()))) {
	 * 
	 * for (Iterator<ServiceChargesResDTO> it = serChargesResDTOs.iterator();
	 * it.hasNext();) { ServiceChargesResDTO record = it.next(); if
	 * (dto.getCommission().getSubCategoryConstantName().equals(record.
	 * getConstantName())) { SlabDetailsDTO slDto = new SlabDetailsDTO(); if
	 * (dto.getCommission().getSlot() != null) { String[] slot =
	 * dto.getCommission().getSlot().split("-");
	 * slDto.setMinSlot(String.valueOf(slot[0]));
	 * slDto.setMaxSlot(String.valueOf(slot[1])); }
	 * 
	 * slDto.setChargingType(dto.getCommission().getChargingType());
	 * slDto.setCharges(dto.getCommission().getAsdrCommsionCharges());
	 * slDto.setGstType(dto.getCommission().getGstType());
	 * slDto.setServiceType(dto.getCommission().getServiceCharge());
	 * 
	 * record.getSlabDetails().add(slDto); break;
	 * 
	 * }
	 * 
	 * } } else {
	 * 
	 * MinMaxChargeDto minMaxChargeDto = new MinMaxChargeDto(); BaseDTOMinMax
	 * minMaxResponse =
	 * getFtMinMaxAmount(serviceProviderEntity.getServiceCategoryId().getId());
	 * 
	 * 
	 * 
	 * if(minMaxResponse.getStatusCode() == 200) { Object obj =
	 * minMaxResponse.getResponseContents(); List<ServiceCategoryGlobalSettingDto>
	 * participantJson = mapper.convertValue(obj, new
	 * TypeReference<List<ServiceCategoryGlobalSettingDto>>() { });
	 * log.info("Min Max charge dto Response ...." + participantJson);
	 * 
	 * if (!participantJson.isEmpty()) { for (ServiceCategoryGlobalSettingDto
	 * minMaxChargeDtoRes : participantJson) {
	 * if(minMaxChargeDtoRes.getSubCategoryName().equalsIgnoreCase("Aadhaar Pay MDR"
	 * )) {
	 * minMaxChargeDto.setApsdMaximumAmount(minMaxChargeDtoRes.getSdMaxCharges());
	 * minMaxChargeDto.setApsdMinimumAmount(minMaxChargeDtoRes.getSdMinCharges());
	 * minMaxChargeDto.setApdMaximumAmount(minMaxChargeDtoRes.getSdMaxCharges());
	 * minMaxChargeDto.setApdMinimumAmount(minMaxChargeDtoRes.getSdMinCharges());
	 * minMaxChargeDto.setAprMaximumAmount(minMaxChargeDtoRes.getSdMaxCharges());
	 * minMaxChargeDto.setAprMinimumAmount(minMaxChargeDtoRes.getSdMinCharges()); }
	 * } } } Double gstAmount = getGstAmount();
	 * 
	 * 
	 * ServiceChargesResDTO serviceChargesResDTO = new ServiceChargesResDTO();
	 * List<SlabDetailsDTO> slabDTO = new ArrayList<SlabDetailsDTO>();
	 * SlabDetailsDTO slabDetailsDTO = new SlabDetailsDTO();
	 * 
	 * if (dto.getCommission().getSubCategoryConstantName().
	 * equalsIgnoreCase("Aadhaar Pay MDR")) {
	 * serviceChargesResDTO.setConstantName(dto.getCommission().
	 * getSubCategoryConstantName()); serviceChargesResDTO.setSdMinAmount((double)
	 * 1); serviceChargesResDTO.setSdMaxAmount((double) 10000);
	 * serviceChargesResDTO.setDMinAmount((double) 1);
	 * serviceChargesResDTO.setDMaxAmount((double) 10000);
	 * serviceChargesResDTO.setRMinAmount((double) 1);
	 * serviceChargesResDTO.setRMaxAmount((double) 10000);
	 * serviceChargesResDTO.setGst((double) 18); } if (dto.getCommission().getSlot()
	 * != null) { String[] slot = dto.getCommission().getSlot().split("-");
	 * slabDetailsDTO.setMinSlot(String.valueOf(slot[0]));
	 * slabDetailsDTO.setMaxSlot(String.valueOf(slot[1])); }
	 * 
	 * slabDetailsDTO.setChargingType(dto.getCommission().getChargingType());
	 * slabDetailsDTO.setCharges(dto.getCommission().getAsdrCommsionCharges());
	 * slabDetailsDTO.setGstType(dto.getCommission().getGstType());
	 * slabDetailsDTO.setServiceType(dto.getCommission().getServiceCharge());
	 * 
	 * slabDTO.add(slabDetailsDTO); serviceChargesResDTO.setSlabDetails(slabDTO);
	 * 
	 * serChargesResDTOs.add(serviceChargesResDTO);
	 * 
	 * response.setStatusCode(commisionResponse.getStatusCode());
	 * response.setMessage(commisionResponse.getMessage());
	 * response.setResponseContent(serChargesResDTOs);
	 * 
	 * }
	 * 
	 * }
	 * 
	 * }
	 * 
	 * } catch (Exception e) { log.error("error in get commision for aadhaar pay",
	 * e); } return response; }
	 */

	@Override
	public BaseDTO getFundTransferCharges(String userUUID, String name, Double amount, String subCategory) {
		// log.info(" <----- ServiceCategoryServiceImpl.getById() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			SubCategoryEntity subCategoryEntity = subCategoryRepository.findByConstantName(subCategory);
			ServiceCategoryEntity serviceCategoryEntity = serviceCategoryDao.getByNameIgnoreCase("Fund Transfer");
			if (subCategoryEntity != null) {
				ServiceProviderEntity serviceProviderEntity = serviceCategoryDao.getAadhaarChargesByName(name,
						subCategoryEntity.getServiceCategoryId().getId());
				log.info("id ===============> " + serviceProviderEntity);

				if (serviceProviderEntity != null) {
					response = getCommissionForFundTransfer(userUUID, serviceProviderEntity, subCategoryEntity, amount);

					log.info("response from commission ===> " + response);
				}
			}

		} catch (Exception e) {
			log.error("<---- ServiceCategoryServiceImpl.getChargesByName() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);

		}
		// log.info(" <----- ServiceCategoryServiceImpl.getById() END -----> ");
		return response;
	}

	private BaseDTO getCommissionForFundTransfer(String userUUID, ServiceProviderEntity serviceProviderEntity,
			SubCategoryEntity subCategoryEntity, Double amount) {
		BaseDTO response = new BaseDTO();

		String stringCommisionResposne = null;
		try {
			String access_token = headerRequest.getHeader("X-Authorization");

			HttpHeaders header = new HttpHeaders();
			header.set("Content-type", "application/json");
			header.set("X-Authorization", access_token);

			HttpEntity<String> getCommisionResponse = new HttpEntity<>(header);

			// Map<String, String> uriVariables = new HashMap<>();

			/*
			 * if(amount != null) { uriVariables.put("amount", String.valueOf(amount)); }
			 * if(serviceProviderEntity.getId() != null) {
			 * log.info("service provider id --- > "+serviceProviderEntity.getId());
			 * uriVariables.put("serviceProvierId",
			 * String.valueOf(serviceProviderEntity.getId())); }
			 */
			//api call param : {userUUId}/{serviceId}/{subcategoryId}/{amount}

			String url = commision + "/commission/v2/applicableCommissionOrOffer/" + userUUID + "/"
					+ subCategoryEntity.getServiceCategoryId().getId()+"/"+subCategoryEntity.getId();

			String urlTemplate;
			if (serviceProviderEntity.getId() != null) {

				urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
						.queryParam("amount", String.valueOf(amount))
						.queryParam("serviceProvierId", String.valueOf(serviceProviderEntity.getId())).encode()
						.toUriString();
			} else {
				urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
						.queryParam("amount", String.valueOf(amount))
						.toUriString();
			}

			log.warn("commision api Request=====>{}", getCommisionResponse + " url ===> " + urlTemplate);
			uppclUserMasterManagementApplication.disableSSL();
			BaseRespServiceChrgDto commisionResponse = restTemplate
					.exchange(urlTemplate, HttpMethod.GET, getCommisionResponse, BaseRespServiceChrgDto.class)
					.getBody();
			log.warn("commision api Response =====>{}", commisionResponse);
			// int status = commisionResponse.get("statusCode").asInt();
			// String msg = commisionResponse.get("message").toString();
			// Object obj = commisionResponse.get("responseContent");
			if (commisionResponse.getStatusCode() != 200 || commisionResponse.getResponseContent() == null) {
				log.warn(
						"**************************** Commison Response Issue ********************************************************");
				response.setStatusCode(commisionResponse.getStatusCode());
				response.setMessage("Technical Issue. We can't process the transaction.");
				response.setResponseContent(null);
			} else {
				log.warn("commision api Request=====>{}", commisionResponse + " url ===> " + urlTemplate);
				List<ServiceChargesDTO> serviceChargesDTO = commisionResponse.getResponseContent();
				log.info("serviceChargesDTO ===> "+serviceChargesDTO);
				// ServiceChargesResDTO serviceChargesResDTO = null;
				List<ServiceChargesResDTO> serChargesResDTOs = new ArrayList<ServiceChargesResDTO>();

				for (ServiceChargesDTO dto : serviceChargesDTO) {

					SlabDetailsDTO slabDetailsDTO = new SlabDetailsDTO();
					
					if(dto.getCommission().getAsdrCommsionCharges() != null && Double.compare(dto.getCommission().getAsdrCommsionCharges(), 0.0) != 0 ) {
					slabDetailsDTO.setCharges(dto.getCommission().getAsdrCommsionCharges());
					}else if(dto.getCommission().getDdCommisionValue() != null &&  Double.compare(dto.getCommission().getDdCommisionValue(), 0.0) != 0)
					{
						slabDetailsDTO.setCharges(dto.getCommission().getDdCommisionValue());
					}else if(dto.getCommission().getPcdCommisionValue() != null &&  Double.compare(dto.getCommission().getPcdCommisionValue(), 0.0) != 0)
					{
						slabDetailsDTO.setCharges(dto.getCommission().getPcdCommisionValue());
					}else 
					{
						slabDetailsDTO.setCharges(dto.getCommission().getRCommisionValue());
					}

					response.setStatusCode(commisionResponse.getStatusCode());
					response.setMessage(commisionResponse.getMessage());
					response.setResponseContent(slabDetailsDTO);

				}

			}

		} catch (Exception e) {
			log.error("error in get commision for fund transfer", e);
		}
		return response;
	}
	/*
	 * private BaseDTO getCommissionForFundTransfer(String userUUID,
	 * ServiceProviderEntity serviceProviderEntity, SubCategoryEntity
	 * subCategoryEntity, Double amount) { BaseDTO response = new BaseDTO();
	 * 
	 * String stringCommisionResposne = null; try { String access_token =
	 * headerRequest.getHeader("X-Authorization");
	 * 
	 * 
	 * 
	 * HttpHeaders header = new HttpHeaders(); header.set("Content-type",
	 * "application/json"); header.set("X-Authorization", access_token);
	 * 
	 * HttpEntity<String> getCommisionResponse = new HttpEntity<>(header);
	 * 
	 * // Map<String, String> uriVariables = new HashMap<>();
	 * 
	 * 
	 * if(amount != null) { uriVariables.put("amount", String.valueOf(amount)); }
	 * if(serviceProviderEntity.getId() != null) {
	 * log.info("service provider id --- > "+serviceProviderEntity.getId());
	 * uriVariables.put("serviceProvierId",
	 * String.valueOf(serviceProviderEntity.getId())); }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * String url = commision +
	 * "/commission/v2/applicableCommissionOrOffer/"+userUUID+"/"+subCategoryEntity.
	 * getServiceCategoryId().getId();
	 * 
	 * 
	 * 
	 * 
	 * String urlTemplate; if(amount != null) {
	 * 
	 * urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
	 * .queryParam("subcategoryId", String.valueOf(subCategoryEntity.getId()))
	 * .queryParam("amount", String.valueOf(amount)) .queryParam("serviceProvierId",
	 * String.valueOf(serviceProviderEntity.getId())).encode() .toUriString(); }else
	 * { urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
	 * .queryParam("subcategoryId", String.valueOf(subCategoryEntity.getId()))
	 * .queryParam("serviceProvierId",
	 * String.valueOf(serviceProviderEntity.getId())) .encode() .toUriString(); }
	 * 
	 * 
	 * 
	 * log.warn("commision api Request=====>{}", getCommisionResponse +" url ===> "
	 * +urlTemplate); masterManagementApplication.disableSSL();
	 * BaseRespServiceChrgDto commisionResponse = restTemplate
	 * .exchange(urlTemplate, HttpMethod.GET, getCommisionResponse,
	 * BaseRespServiceChrgDto.class).getBody();
	 * log.warn("commision api Response =====>{}", commisionResponse); //int status
	 * = commisionResponse.get("statusCode").asInt(); //String msg =
	 * commisionResponse.get("message").toString(); //Object obj =
	 * commisionResponse.get("responseContent"); if
	 * (commisionResponse.getStatusCode() != 200 ||
	 * commisionResponse.getResponseContent() == null) { log.warn(
	 * "**************************** Commison Response Issue ********************************************************"
	 * ); response.setStatusCode(commisionResponse.getStatusCode());
	 * response.setMessage("Technical Issue. We can't process the transaction.");
	 * response.setResponseContent(null); } else {
	 * 
	 * MinMaxChargeDto minMaxChargeDto = new MinMaxChargeDto(); BaseDTOMinMax
	 * minMaxResponse = getFtMinMaxAmount(serviceCategoryEntity.getId());
	 * 
	 * 
	 * 
	 * if(minMaxResponse.getStatusCode() == 200) { Object obj =
	 * minMaxResponse.getResponseContents(); List<ServiceCategoryGlobalSettingDto>
	 * participantJson = mapper.convertValue(obj, new
	 * TypeReference<List<ServiceCategoryGlobalSettingDto>>() { });
	 * log.info("Min Max charge dto Response ...." + participantJson);
	 * 
	 * if (!participantJson.isEmpty()) { for (ServiceCategoryGlobalSettingDto
	 * minMaxChargeDtoRes : participantJson) {
	 * if(minMaxChargeDtoRes.getSubCategoryName().equalsIgnoreCase("IMPS")) {
	 * minMaxChargeDto.setImpssdMinimumAmount(minMaxChargeDtoRes.getRMinCharges());
	 * minMaxChargeDto.setImpssdMaximumAmount(minMaxChargeDtoRes.getRMaxCharges());
	 * minMaxChargeDto.setImpsdMinimumAmount(minMaxChargeDtoRes.getSdMinCharges());
	 * minMaxChargeDto.setImpsdMaximumAmount(minMaxChargeDtoRes.getSdMaxCharges());
	 * 
	 * minMaxChargeDto.setImpsrMinimumAmount(minMaxChargeDtoRes.getDMinCharges());
	 * minMaxChargeDto.setImpsrMaximumAmount(minMaxChargeDtoRes.getDMaxCharges());
	 * 
	 * }else if(minMaxChargeDtoRes.getSubCategoryName().equalsIgnoreCase("NEFT")) {
	 * minMaxChargeDto.setNeftsdMinimumAmount(minMaxChargeDtoRes.getRMinCharges());
	 * minMaxChargeDto.setNeftsdMaximumAmount(minMaxChargeDtoRes.getRMaxCharges());
	 * minMaxChargeDto.setNeftdMinimumAmount(minMaxChargeDtoRes.getSdMinCharges());
	 * minMaxChargeDto.setNeftdMaximumAmount(minMaxChargeDtoRes.getSdMaxCharges());
	 * minMaxChargeDto.setNeftrMinimumAmount(minMaxChargeDtoRes.getSdMinCharges());
	 * minMaxChargeDto.setNeftrMaximumAmount(minMaxChargeDtoRes.getSdMaxCharges());
	 * 
	 * }else if(minMaxChargeDtoRes.getSubCategoryName().equalsIgnoreCase("RTGS")) {
	 * 
	 * minMaxChargeDto.setRtgssdMinimumAmount(minMaxChargeDtoRes.getRMinCharges());
	 * minMaxChargeDto.setRtgssdMaximumAmount(minMaxChargeDtoRes.getRMaxCharges());
	 * 
	 * minMaxChargeDto.setRtgsdMinimumAmount(minMaxChargeDtoRes.getSdMinCharges());
	 * minMaxChargeDto.setRtgsdMaximumAmount(minMaxChargeDtoRes.getSdMaxCharges());
	 * 
	 * minMaxChargeDto.setRtgsrMinimumAmount(minMaxChargeDtoRes.getDMinCharges());
	 * minMaxChargeDto.setRtgsrMaximumAmount(minMaxChargeDtoRes.getDMaxCharges());
	 * 
	 * } } } }
	 * 
	 * Double gstAmount = getGstAmount();
	 * 
	 * 
	 * 
	 * List<ServiceChargesDTO> serviceChargesDTO =
	 * commisionResponse.getResponseContent();
	 * 
	 * 
	 * //ServiceChargesResDTO serviceChargesResDTO = null;
	 * List<ServiceChargesResDTO> serChargesResDTOs = new
	 * ArrayList<ServiceChargesResDTO>();
	 * 
	 * for(ServiceChargesDTO dto : serviceChargesDTO) {
	 * if(Boolean.FALSE.equals(CollectionUtils.isEmpty(serChargesResDTOs)) &&
	 * serChargesResDTOs.stream().anyMatch(element ->
	 * element.getConstantName().equals(dto.getCommission().
	 * getSubCategoryConstantName()))) {
	 * 
	 * for (Iterator<ServiceChargesResDTO> it = serChargesResDTOs.iterator();
	 * it.hasNext();) { ServiceChargesResDTO record = it.next();
	 * if(dto.getCommission().getSubCategoryConstantName().equals(record.
	 * getConstantName())) { SlabDetailsDTO slDto = new SlabDetailsDTO();
	 * if(dto.getCommission().getSlot() != null) { String[] slot =
	 * dto.getCommission().getSlot().split("-");
	 * slDto.setMinSlot(String.valueOf(slot[0]));
	 * slDto.setMaxSlot(String.valueOf(slot[1])); }
	 * 
	 * slDto.setChargingType(dto.getCommission().getChargingType());
	 * slDto.setCharges(dto.getCommission().getAsdrCommsionCharges());
	 * slDto.setGstType(dto.getCommission().getGstType());
	 * slDto.setServiceType(dto.getCommission().getServiceCharge());
	 * 
	 * record.getSlabDetails().add(slDto); break;
	 * 
	 * }
	 * 
	 * } }else { ServiceChargesResDTO serviceChargesResDTO = new
	 * ServiceChargesResDTO(); List<SlabDetailsDTO> slabDTO = new
	 * ArrayList<SlabDetailsDTO>(); SlabDetailsDTO slabDetailsDTO = new
	 * SlabDetailsDTO();
	 * 
	 * 
	 * if(dto.getCommission().getSubCategoryConstantName().equalsIgnoreCase("IMPS"))
	 * { serviceChargesResDTO.setConstantName(dto.getCommission().
	 * getSubCategoryConstantName()); serviceChargesResDTO.setSdMinAmount((double)
	 * 1); serviceChargesResDTO.setSdMaxAmount((double) 500000);
	 * serviceChargesResDTO.setDMinAmount((double) 1);
	 * serviceChargesResDTO.setDMaxAmount((double) 500000);
	 * serviceChargesResDTO.setRMinAmount((double) 1);
	 * serviceChargesResDTO.setRMaxAmount((double) 500000);
	 * serviceChargesResDTO.setGst((double) 18); }else
	 * if(dto.getCommission().getSubCategoryConstantName().equalsIgnoreCase("NEFT"))
	 * { serviceChargesResDTO.setConstantName(dto.getCommission().
	 * getSubCategoryConstantName()); serviceChargesResDTO.setSdMinAmount((double)
	 * 1); serviceChargesResDTO.setSdMaxAmount((double) 1000000);
	 * serviceChargesResDTO.setDMinAmount((double) 1);
	 * serviceChargesResDTO.setDMaxAmount((double) 1000000);
	 * serviceChargesResDTO.setRMinAmount((double) 1);
	 * serviceChargesResDTO.setRMaxAmount((double) 1000000);
	 * serviceChargesResDTO.setGst((double) 18); }else
	 * if(dto.getCommission().getSubCategoryConstantName().equalsIgnoreCase("RTGS"))
	 * { serviceChargesResDTO.setConstantName(dto.getCommission().
	 * getSubCategoryConstantName()); serviceChargesResDTO.setSdMinAmount((double)
	 * 1); serviceChargesResDTO.setSdMaxAmount((double) 500000);
	 * serviceChargesResDTO.setDMinAmount((double) 1);
	 * serviceChargesResDTO.setDMaxAmount((double) 500000);
	 * serviceChargesResDTO.setRMinAmount((double) 1);
	 * serviceChargesResDTO.setRMaxAmount((double) 500000);
	 * serviceChargesResDTO.setGst((double) 18); }
	 * 
	 * if(dto.getCommission().getSlot() != null) { String[] slot =
	 * dto.getCommission().getSlot().split("-");
	 * slabDetailsDTO.setMinSlot(String.valueOf(slot[0]));
	 * slabDetailsDTO.setMaxSlot(String.valueOf(slot[1])); }
	 * 
	 * slabDetailsDTO.setChargingType(dto.getCommission().getChargingType());
	 * slabDetailsDTO.setCharges(dto.getCommission().getAsdrCommsionCharges());
	 * slabDetailsDTO.setGstType(dto.getCommission().getGstType());
	 * slabDetailsDTO.setServiceType(dto.getCommission().getServiceCharge());
	 * 
	 * slabDTO.add(slabDetailsDTO); serviceChargesResDTO.setSlabDetails(slabDTO);
	 * 
	 * 
	 * serChargesResDTOs.add(serviceChargesResDTO);
	 * 
	 * response.setStatusCode(commisionResponse.getStatusCode());
	 * response.setMessage(commisionResponse.getMessage());
	 * response.setResponseContent(serChargesResDTOs);
	 * 
	 * }
	 * 
	 * 
	 * }
	 * 
	 * }
	 * 
	 * } catch (Exception e) { log.error("error in get commision for fund transfer",
	 * e); } return response; }
	 */

	private Double getGstAmount() {
		// TODO Auto-generated method stub
		Double amount = null;
		try {
			String access_token = headerRequest.getHeader("X-Authorization");

			HttpHeaders header = new HttpHeaders();
			header.set("Content-type", "application/json");
			header.set("X-Authorization", access_token);

			HttpEntity<String> getResponse = new HttpEntity<>(header);

			String url = settingApi + "/gst/getigst";

			log.warn("GST url =====>{} " + url);
			uppclUserMasterManagementApplication.disableSSL();
			BaseDTOMinMax gstResponse = restTemplate.exchange(url, HttpMethod.GET, getResponse, BaseDTOMinMax.class)
					.getBody();
			log.warn("GST api Response =====>{}", gstResponse);
			if (gstResponse.getStatusCode() == 200) {
				Object obj = gstResponse.getResponseContents();
				List<GstResponse> participantJson = mapper.convertValue(obj, new TypeReference<List<GstResponse>>() {
				});
				log.info("Min Max charge dto Response ...." + participantJson);
				if (!participantJson.isEmpty()) {
					for (GstResponse getAmount : participantJson)
						amount = getAmount.getAmount();
				}

			}

		} catch (Exception e) {
			log.error("error in get ft min max amount => ", e);

		}
		return amount;

	}

	private BaseDTOMinMax getFtMinMaxAmount(UUID id) {
		// TODO Auto-generated method stub
		BaseDTOMinMax response = new BaseDTOMinMax();
		try {
			String access_token = headerRequest.getHeader("X-Authorization");

			HttpHeaders header = new HttpHeaders();
			header.set("Content-type", "application/json");
			header.set("X-Authorization", access_token);

			HttpEntity<String> getResponse = new HttpEntity<>(header);

			String url = settingApi + "/globalchargesbyservicecategory/minmaxcharges/" + id;

			log.warn("Fund transfer =====>{} " + url);
			uppclUserMasterManagementApplication.disableSSL();
			BaseDTOMinMax minMaxResponse = restTemplate.exchange(url, HttpMethod.GET, getResponse, BaseDTOMinMax.class)
					.getBody();
			log.warn("Min and Max charge api Response =====>{}", minMaxResponse);
			if (minMaxResponse.getStatusCode() == 200) {
				response.setStatusCode(minMaxResponse.getStatusCode());
				response.setMessage(minMaxResponse.getMessage());
				response.setResponseContents(minMaxResponse.getResponseContents());

			}

		} catch (Exception e) {
			log.error("error in get ft min max amount => ", e);

		}
		return response;
	}

	@Override
	public BaseDTOMinMax getMinMaxAmountByName(String name) {
		// log.info(" <----- ServiceCategoryServiceImpl.getById() STARTED -----> ");
		BaseDTOMinMax response = new BaseDTOMinMax();

		try {
			ServiceCategoryEntity serviceCategoryEntity = serviceCategoryDao.getMinMaxAmountByName(name);

			if (serviceCategoryEntity != null) {
				MinMaxChargeDto minMaxChargeDto = new MinMaxChargeDto();
				//BaseDTOMinMax minMaxResponse = getFtMinMaxAmount(serviceCategoryEntity.getId());
				
				List<ServiceChargesResDTO> finalList = new ArrayList<ServiceChargesResDTO>();
				//ServiceCategoryGlobalSettingDto list =  new ServiceCategoryGlobalSettingDto();
				ServiceChargesResDTO list = new ServiceChargesResDTO();
				ServiceChargesResDTO list1 =  new ServiceChargesResDTO();
				ServiceChargesResDTO list2 =  new ServiceChargesResDTO();
				ServiceChargesResDTO list3 = new ServiceChargesResDTO();
				ServiceChargesResDTO list4 = new ServiceChargesResDTO();
				ServiceChargesResDTO list5 = new ServiceChargesResDTO();
				
				
				if(name.equalsIgnoreCase("Fund Transfer")) {
				  list.setConstantName("IMPS");
				  list.setRMinAmount((double) 1);
				  list.setRMaxAmount((double) 500000);
				  list.setSdMinAmount((double) 1);
				  list.setSdMaxAmount((double) 500000);
			  
				  list.setDMinAmount((double) 1);
				  list.setDMaxAmount((double) 500000);
				  
				  finalList.add(list);
			  
			 
				      list1.setConstantName("NEFT");
				      list1.setRMinAmount((double) 1);
					  list1.setRMaxAmount((double) 1000000);
					  list1.setSdMinAmount((double) 1);
					  list1.setSdMaxAmount((double) 1000000);
				  
					  list1.setDMinAmount((double) 1);
					  list1.setDMaxAmount((double) 1000000);
			  
					  finalList.add(list1);
					  
				      list2.setConstantName("RTGS");
					  list2.setRMinAmount((double) 200000);
					  list2.setRMaxAmount((double) 1000000);
					  list2.setSdMinAmount((double) 200000);
					  list2.setSdMaxAmount((double) 1000000);
				  
					  list2.setDMinAmount((double) 200000);
					  list2.setDMaxAmount((double) 1000000);
					  finalList.add(list2);
					  
				}else if(name.equalsIgnoreCase("Aadhaar Pay"))
				{
					list.setConstantName("Aadhaar pay MDR");
					  list.setRMinAmount((double) 1);
					  list.setRMaxAmount((double) 10000);
					  list.setSdMinAmount((double) 1);
					  list.setSdMaxAmount((double) 10000);
				  
					  list.setDMinAmount((double) 1);
					  list.setDMaxAmount((double) 10000);
					  
					  finalList.add(list);	
				}else if(name.equalsIgnoreCase("Payment Gateway"))
				{
					list.setConstantName("Credit Card");
					  list.setRMinAmount((double) 100);
					  list.setRMaxAmount((double) 25000);
					  list.setSdMinAmount((double) 100);
					  list.setSdMaxAmount((double) 25000);
				  
					  list.setDMinAmount((double) 100);
					  list.setDMaxAmount((double) 25000);
					  
					  finalList.add(list);
					  
					  list1.setConstantName("UPI");
				      list1.setRMinAmount((double) 100);
					  list1.setRMaxAmount((double) 100000);
					  list1.setSdMinAmount((double) 100);
					  list1.setSdMaxAmount((double) 100000);
				  
					  list1.setDMinAmount((double) 100);
					  list1.setDMaxAmount((double) 100000);
			  
					  finalList.add(list1);
					  
				      list2.setConstantName("Netbanking");
					  list2.setRMinAmount((double) 100);
					  list2.setRMaxAmount((double) 25000);
					  list2.setSdMinAmount((double) 100);
					  list2.setSdMaxAmount((double) 25000);
				  
					  list2.setDMinAmount((double) 100);
					  list2.setDMaxAmount((double) 25000);
					  finalList.add(list2);
					  
					  list3.setConstantName("Debit Card");
				      list3.setRMinAmount((double) 100);
					  list3.setRMaxAmount((double) 25000);
					  list3.setSdMinAmount((double) 100);
					  list3.setSdMaxAmount((double) 25000);
				  
					  list3.setDMinAmount((double) 100);
					  list3.setDMaxAmount((double) 25000);
			  
					  finalList.add(list3);
					  
				      list4.setConstantName("Debit Card Rupay");
					  list4.setRMinAmount((double) 100);
					  list4.setRMaxAmount((double) 25000);
					  list4.setSdMinAmount((double) 100);
					  list4.setSdMaxAmount((double) 25000);
				  
					  list4.setDMinAmount((double) 100);
					  list4.setDMaxAmount((double) 25000);
					  finalList.add(list4);
					  
					  list5.setConstantName("Wallet");
					  list5.setRMinAmount((double) 100);
					  list5.setRMaxAmount((double) 10000);
					  list5.setSdMinAmount((double) 100);
					  list5.setSdMaxAmount((double) 10000);
				  
					  list5.setDMinAmount((double) 100);
					  list5.setDMaxAmount((double) 10000);
					  finalList.add(list5);
				}
					  response.setStatusCode(200);
					  response.setMessage("Success");
					  response.setResponseContent(finalList);
			  }
			  
				/* if(minMaxResponse.getStatusCode() == 200)
				  {
						
						  Object obj = minMaxResponse.getResponseContents();
						  List<ServiceCategoryGlobalSettingDto> participantJson =
						  mapper.convertValue(obj, new
						  TypeReference<List<ServiceCategoryGlobalSettingDto>>() { });
						  log.info("Min Max charge dto Response ...." + participantJson);
						  
						  if (!participantJson.isEmpty()) { for (ServiceCategoryGlobalSettingDto
						  minMaxChargeDtoRes : participantJson) {
						  if(minMaxChargeDtoRes.getSubCategoryName().equalsIgnoreCase("IMPS")) {
							  list.setRMinCharges(minMaxChargeDtoRes.getRMinCharges());
							  list.setRMaxCharges(minMaxChargeDtoRes.getRMaxCharges());
							  list.setSdMinCharges(minMaxChargeDtoRes.getSdMinCharges());
							  list.setSdMaxCharges(minMaxChargeDtoRes.getSdMaxCharges());
						  
							  list.setDMinCharges(minMaxChargeDtoRes.getDMinCharges());
							  list.setDMaxCharges(minMaxChargeDtoRes.getDMaxCharges());
						  
						  }else if(minMaxChargeDtoRes.getSubCategoryName().equalsIgnoreCase("NEFT")) {
							  list.setRMinCharges(minMaxChargeDtoRes.getRMinCharges());
							  list.setRMaxCharges(minMaxChargeDtoRes.getRMaxCharges());
							  list.setSdMinCharges(minMaxChargeDtoRes.getSdMinCharges());
							  list.setSdMaxCharges(minMaxChargeDtoRes.getSdMaxCharges());
						  
							  list.setDMinCharges(minMaxChargeDtoRes.getDMinCharges());
							  list.setDMaxCharges(minMaxChargeDtoRes.getDMaxCharges());
						  }else if(minMaxChargeDtoRes.getSubCategoryName().equalsIgnoreCase("RTGS")) {
						  
							  list.setRMinCharges(minMaxChargeDtoRes.getRMinCharges());
							  list.setRMaxCharges(minMaxChargeDtoRes.getRMaxCharges());
							  list.setSdMinCharges(minMaxChargeDtoRes.getSdMinCharges());
							  list.setSdMaxCharges(minMaxChargeDtoRes.getSdMaxCharges());
						  
							  list.setDMinCharges(minMaxChargeDtoRes.getDMinCharges());
							  list.setDMaxCharges(minMaxChargeDtoRes.getDMaxCharges());
						  }
						  
						  
						  finalList.add(list);
						  } 
						  } 
						  
						  response.setStatusCode(200);
						  response.setMessage(minMaxResponse.getMessage());
						  response.setResponseContent(finalList);
						  }else
						  {
							  response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
							  response.setMessage("Failure");
							  response.setResponseContent(null);
						  }*/
			
				
				 
			
		} catch (Exception e) {
			log.error("<---- ServiceCategoryServiceImpl.getMinMaxAmountByName() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);

		}
		// log.info(" <----- ServiceCategoryServiceImpl.getById() END -----> ");
		return response;
	}
	public BaseDTO getAllOrderByModifiedDate() {
		
		BaseDTO response = new BaseDTO();
		try {
			response = serviceCategoryDao.getAllOrderByModifiedDate();
		} catch (Exception e) {
			log.error("<---- ServiceCategoryServiceImpl.getAllOrderByModifiedDate() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,
					Locale.US);
			response.setMessage(msg);

		}
		
		return response;
	}



/*
 * else if(dto.getCommission().getSubCategoryConstantName().
 * equalsIgnoreCase("Credit Card")) {
 * serviceChargesResDTO.setConstantName(dto.getCommission().
 * getSubCategoryConstantName());
 * 
 * if(dto.getCommission().getSlot() != null) { String[] slot =
 * dto.getCommission().getSlot().split("-"); minSlot = slot[0]; maxSlot =
 * slot[1]; } slabDetailsDTO.setMinSlot(String.valueOf(minSlot));
 * slabDetailsDTO.setMaxSlot(String.valueOf(maxSlot));
 * slabDetailsDTO.setChargingType(dto.getCommission().getChargingType());
 * slabDetailsDTO.setCharges(dto.getCommission().getRCommisionValue());
 * slabDetailsDTO.setGstType(dto.getCommission().getGstType());
 * 
 * slabDTO.add(slabDetailsDTO); serviceChargesResDTO.setSlabDetails(slabDTO); }
 * else if(dto.getCommission().getSubCategoryConstantName().
 * equalsIgnoreCase("Debit Card Rupay")) {
 * serviceChargesResDTO.setConstantName(dto.getCommission().
 * getSubCategoryConstantName());
 * 
 * if(dto.getCommission().getSlot() != null) { String[] slot =
 * dto.getCommission().getSlot().split("-"); minSlot = slot[0]; maxSlot =
 * slot[1]; } slabDetailsDTO.setMinSlot(String.valueOf(minSlot));
 * slabDetailsDTO.setMaxSlot(String.valueOf(maxSlot));
 * slabDetailsDTO.setChargingType(dto.getCommission().getChargingType());
 * slabDetailsDTO.setCharges(dto.getCommission().getRCommisionValue());
 * slabDetailsDTO.setGstType(dto.getCommission().getGstType());
 * 
 * slabDTO.add(slabDetailsDTO); serviceChargesResDTO.setSlabDetails(slabDTO); }
 * else
 * if(dto.getCommission().getSubCategoryConstantName().equalsIgnoreCase("Wallet"
 * )) { serviceChargesResDTO.setConstantName(dto.getCommission().
 * getSubCategoryConstantName());
 * 
 * if(dto.getCommission().getSlot() != null) { String[] slot =
 * dto.getCommission().getSlot().split("-"); minSlot = slot[0]; maxSlot =
 * slot[1]; } slabDetailsDTO.setMinSlot(String.valueOf(minSlot));
 * slabDetailsDTO.setMaxSlot(String.valueOf(maxSlot));
 * slabDetailsDTO.setChargingType(dto.getCommission().getChargingType());
 * slabDetailsDTO.setCharges(dto.getCommission().getRCommisionValue());
 * slabDetailsDTO.setGstType(dto.getCommission().getGstType());
 * 
 * slabDTO.add(slabDetailsDTO); serviceChargesResDTO.setSlabDetails(slabDTO); }
 * else
 * if(dto.getCommission().getSubCategoryConstantName().equalsIgnoreCase("UPI"))
 * { serviceChargesResDTO.setConstantName(dto.getCommission().
 * getSubCategoryConstantName());
 * 
 * if(dto.getCommission().getSlot() != null) { String[] slot =
 * dto.getCommission().getSlot().split("-"); minSlot = slot[0]; maxSlot =
 * slot[1]; } slabDetailsDTO.setMinSlot(String.valueOf(minSlot));
 * slabDetailsDTO.setMaxSlot(String.valueOf(maxSlot));
 * slabDetailsDTO.setChargingType(dto.getCommission().getChargingType());
 * slabDetailsDTO.setCharges(dto.getCommission().getRCommisionValue());
 * slabDetailsDTO.setGstType(dto.getCommission().getGstType());
 * 
 * slabDTO.add(slabDetailsDTO); serviceChargesResDTO.setSlabDetails(slabDTO); }
 * else if(dto.getCommission().getSubCategoryConstantName().equalsIgnoreCase(
 * "Netbanking")) { serviceChargesResDTO.setConstantName(dto.getCommission().
 * getSubCategoryConstantName());
 * 
 * if(dto.getCommission().getSlot() != null) { String[] slot =
 * dto.getCommission().getSlot().split("-"); minSlot = slot[0]; maxSlot =
 * slot[1]; } slabDetailsDTO.setMinSlot(String.valueOf(minSlot));
 * slabDetailsDTO.setMaxSlot(String.valueOf(maxSlot));
 * slabDetailsDTO.setChargingType(dto.getCommission().getChargingType());
 * slabDetailsDTO.setCharges(dto.getCommission().getRCommisionValue());
 * slabDetailsDTO.setGstType(dto.getCommission().getGstType());
 * 
 * slabDTO.add(slabDetailsDTO); serviceChargesResDTO.setSlabDetails(slabDTO); }
 */
}


