package com.oasys.uppcl_user__master_management.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.oasys.clients.userservice.utils.CommonUtil;
import com.oasys.clients.userservice.utils.POIUtils;
import com.oasys.config.BaseDTO;
import com.oasys.config.Constants;
import com.oasys.config.ErrorDescription;
import com.oasys.config.FileUploadResponseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.security.Oauth2UserDetails;
import com.oasys.uppcl_user__master_management.dto.BlacklistedPhoneDTO;
import com.oasys.uppcl_user__master_management.entity.BlacklistedPhoneNumberEntity;
import com.oasys.uppcl_user__master_management.repository.BlacklistedPhoneNumberRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.BlacklistedPhoneNumberService;

import lombok.extern.log4j.Log4j2;



@Service
@Log4j2
public class BlacklistedPhoneNumberServiceImpl implements BlacklistedPhoneNumberService{

	@Autowired
	private BlacklistedPhoneNumberRepository blacklistedPhoneNumberRepository;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	MessageSource messageSource;
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	Oauth2UserDetails oauth2UserDetails;

	@Override
	public FileUploadResponseDTO upload(MultipartFile file) {
		FileUploadResponseDTO responseDTO = new FileUploadResponseDTO();
		Map<Integer, String> unprocessedData = new HashMap<>();
		Workbook workbook = null;
		try {
			workbook = WorkbookFactory.create(file.getInputStream());
			Sheet worksheet = workbook.getSheetAt(0);
			for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
				if (index > 0) {
					this.processData(workbook, worksheet, index, unprocessedData);
				}
			}

		} catch (ParseException e) {
			log.error("error occurred while parsing the data : {}", e);
			responseDTO.setErrorCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			responseDTO.setUserDisplayMesg("Invalid Request parameter passed");
			return responseDTO;

		} catch (Exception e) {
			log.error("error occurred while saving the data : {}", e);
			responseDTO.setErrorCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			responseDTO.setUserDisplayMesg("Failed to upload");
			return responseDTO;

		}
		responseDTO.setUnProcesseData(unprocessedData);
		responseDTO.setErrorCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
		responseDTO.setStatus(ErrorDescription.SUCCESS_RESPONSE.getCode().toString());
		responseDTO.setUserDisplayMesg("File uploaded successfully!");
		return responseDTO;
	}

	public void processData(Workbook workbook, Sheet worksheet, int index, Map<Integer, String> unprocessedData)
			throws InvalidFormatException, EncryptedDocumentException, IOException, ParseException {

		String phoneNumber = POIUtils.getByColumnName(workbook, worksheet, Constants.PHONE_NUMBER, index);

		if (StringUtils.isBlank(phoneNumber)) {
			unprocessedData.put(index, Constants.PHONE_NUMBER + " is missing.");
			log.info("Phone Number is missing : {} ", phoneNumber);
			return;
		}
		phoneNumber=phoneNumber.trim();
		if (Boolean.FALSE.equals(commonUtil.isValidPhoneNumber(phoneNumber))) {
			unprocessedData.put(index, Constants.PHONE_NUMBER + " is invalid.");
			log.info("Phone Number is invalid : {} ", phoneNumber);
			return;
		}

		Optional<BlacklistedPhoneNumberEntity> entityOptional = blacklistedPhoneNumberRepository.findByPhoneNumber(phoneNumber);
		if (entityOptional.isPresent()) {
			unprocessedData.put(index, Constants.PHONE_NUMBER + " already exist");
			log.info("phone Number already exist : {} ", phoneNumber);
			return;
		}
		BlacklistedPhoneNumberEntity entity = new BlacklistedPhoneNumberEntity();
		entity.setPhoneNumber(phoneNumber);
		blacklistedPhoneNumberRepository.save(entity);
	}
	
	@Override
	public BaseDTO validate(String phoneNumber) {
		BaseDTO responseDTO = new BaseDTO();
		if (Boolean.FALSE.equals(commonUtil.isValidPhoneNumber(phoneNumber))) {
			responseDTO.setMessage("Please Enter the valid Phone Number");
			responseDTO.setStatusCode(ErrorDescription.BAD_REQUEST.getCode());
			return responseDTO;
		}
		Optional<BlacklistedPhoneNumberEntity> entityOptional = blacklistedPhoneNumberRepository.findByPhoneNumber(phoneNumber);
		
		if (entityOptional.isPresent()) {
			responseDTO.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
			responseDTO.setMessage("Phone Number is Blacklisted");
			return responseDTO;
		}
		responseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
		responseDTO.setMessage("Phone Number is not Blacklisted");
		return responseDTO;
	}
	
	@Override
	public BaseDTO create(BlacklistedPhoneDTO blacklistedPhoneDTO) {

		BaseDTO responseDTO = new BaseDTO();
		String message = "";
		
		if (Boolean.FALSE.equals(commonUtil.isValidPhoneNumber(blacklistedPhoneDTO.getPhoneNumber()))) {
			responseDTO.setMessage("Please Enter the valid Phone Number");
			responseDTO.setStatusCode(ErrorDescription.BAD_REQUEST.getCode());
			return responseDTO;
		}

		Optional<BlacklistedPhoneNumberEntity> entityOptional = blacklistedPhoneNumberRepository
				.findByPhoneNumber(blacklistedPhoneDTO.getPhoneNumber().trim());

		if (entityOptional.isPresent()) {
			message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
			responseDTO.setMessage("This Phone Number " + message);
			responseDTO.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
			return responseDTO;
		}
		BlacklistedPhoneNumberEntity blacklistedPhoneNumberEntity = new BlacklistedPhoneNumberEntity();
		blacklistedPhoneNumberEntity.setPhoneNumber(blacklistedPhoneDTO.getPhoneNumber());
		blacklistedPhoneNumberEntity.setCreatedBy(oauth2UserDetails.getId());
		blacklistedPhoneNumberRepository.save(blacklistedPhoneNumberEntity);

		message = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
		responseDTO.setMessage(blacklistedPhoneDTO.getPhoneNumber() + " " + message);
		responseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
		return responseDTO;
	}

	@Override
	public BaseDTO update(UUID id, String phoneNumber) {

		BaseDTO responseDTO = new BaseDTO();
		String message = "";
		if (Boolean.FALSE.equals(commonUtil.isValidPhoneNumber(phoneNumber))) {
			responseDTO.setMessage("Please Enter the valid Phone Number");
			responseDTO.setStatusCode(ErrorDescription.BAD_REQUEST.getCode());
			return responseDTO;
		}
		Optional<BlacklistedPhoneNumberEntity> entityOptional = blacklistedPhoneNumberRepository.findById(id);

		if (entityOptional.isPresent()) {
			if (entityOptional.get().getPhoneNumber().equals(phoneNumber)) {
				message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
				responseDTO.setMessage("This Phone Number " + message);
				responseDTO.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());

			} else {
				Optional<BlacklistedPhoneNumberEntity> entityOptional1 = blacklistedPhoneNumberRepository.findByPhoneNumber(phoneNumber);
				if(entityOptional1.isPresent()) {
					if(entityOptional1.get().getPhoneNumber().equals(phoneNumber)) {
						message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
						responseDTO.setMessage("This Phone Number " + message + " for other Id");
						responseDTO.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
						return responseDTO;
					}
				}
				entityOptional.get().setPhoneNumber(phoneNumber);
				entityOptional.get().setModifiedBy(oauth2UserDetails.getId());
				blacklistedPhoneNumberRepository.save(entityOptional.get());
				message = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				responseDTO.setMessage("This Phone Number " + message);
				responseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			}
		} else {
			message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
			responseDTO.setMessage(message);
			responseDTO.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
		}

		return responseDTO;
	}

	@Override
	public BaseDTO getById(UUID id) {

		BaseDTO responseDTO = new BaseDTO();
		String message = "";

		BlacklistedPhoneDTO  blacklistedPhoneDTO = new BlacklistedPhoneDTO();
		try {
			Optional<BlacklistedPhoneNumberEntity> entityOptional = blacklistedPhoneNumberRepository.findById(id);

			if (entityOptional.isPresent()) {

				blacklistedPhoneDTO = objectMapper.convertValue(entityOptional.get(), BlacklistedPhoneDTO.class);
				responseDTO.setResponseContent(blacklistedPhoneDTO);
				responseDTO.setMessage("Successfully Get Blacklisted Phone Number");
				responseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				
			} else {
				responseDTO.setMessage("No Record Found..");
				responseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				
			}
		} catch (Exception e) {
			responseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			responseDTO.setMessage(msg);
		}
		
		return responseDTO;

	}

	@Override
	public BaseDTO getAll() {

		BaseDTO response = new BaseDTO();
		try {
			List<BlacklistedPhoneNumberEntity> blacklistedPhoneNumberEntities = blacklistedPhoneNumberRepository.findAll();
			if (blacklistedPhoneNumberEntities.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());

			} else {
				response.setResponseContents(blacklistedPhoneNumberEntities);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);

			}
		} catch (Exception e) {

			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}

		return response;
	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		BaseDTO response = new BaseDTO();
		List<BlacklistedPhoneNumberEntity> contentList = new ArrayList<BlacklistedPhoneNumberEntity>();
		Page<BlacklistedPhoneNumberEntity> blackListPan = null;
		Pageable pageable = null;
		try {

			if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
				pageable = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
						Sort.by(Direction.ASC, requestData.getSortField()));
			} else {
				pageable = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
						Sort.by(Direction.DESC, requestData.getSortField()));
			}

			if (Objects.isNull(blackListPan)) {
				if (StringUtils.isBlank(requestData.getSearch())) {
					if (StringUtils.isNotBlank(requestData.getFromDate())
							&& StringUtils.isNotBlank(requestData.getToDate())) {

						blackListPan = blacklistedPhoneNumberRepository.getReportwithDate(pageable,
								new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
										.parse(requestData.getFromDate() + " " + "00:00:00"),
								new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
										.parse(requestData.getToDate() + " " + "23:59:59"));
					} else {
						blackListPan = blacklistedPhoneNumberRepository.getReport(pageable);

					}
				} else {
					if (StringUtils.isNotBlank(requestData.getFromDate())
							&& StringUtils.isNotBlank(requestData.getToDate())) {

						blackListPan = blacklistedPhoneNumberRepository.getReportSearchwithDate(
								requestData.getSearch().toUpperCase(), pageable,
								new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
										.parse(requestData.getFromDate() + " " + "00:00:00"),
								new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
										.parse(requestData.getToDate() + " " + "23:59:59"));
					} else {
						blackListPan = blacklistedPhoneNumberRepository
								.getReportSearch(requestData.getSearch().toUpperCase(), pageable);

					}
				}
			}
			if (blackListPan.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				
			} else {
				response.setNumberOfElements(blackListPan.getNumberOfElements());
				response.setTotalRecords(blackListPan.getTotalElements());
				response.setTotalPages(blackListPan.getTotalPages());
				for (BlacklistedPhoneNumberEntity blacklistedPhoneNumberEntity : blackListPan) {
					contentList.add(blacklistedPhoneNumberEntity);
				}
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setResponseContents(contentList);
				
			}

		} catch (Exception e) {
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
	
		return response;
	}

}
