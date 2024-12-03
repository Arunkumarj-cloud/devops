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
import com.oasys.uppcl_user__master_management.dto.BlacklistedPanDTO;
import com.oasys.uppcl_user__master_management.entity.BlacklistedPANNumberEntity;
import com.oasys.uppcl_user__master_management.repository.BlacklistedPANNumberRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.BlacklistedPANNumberService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BlacklistedPANSNumbererviceImpl implements BlacklistedPANNumberService {

	@Autowired
	private BlacklistedPANNumberRepository blacklistedPANNumberRepository;

	@Autowired
	MessageSource messageSource;
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	private CommonUtil commonUtil;
	
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

		String panNumber = POIUtils.getByColumnName(workbook, worksheet, Constants.PAN_NUMBER, index);

		if (StringUtils.isBlank(panNumber)) {
			unprocessedData.put(index, Constants.PAN_NUMBER + " is missing.");
			log.info("PAN Number is missing : {} ", panNumber);
			return;
		}
		panNumber = panNumber.trim();
		if (Boolean.FALSE.equals(commonUtil.isValidatePanNumber(panNumber))) {
			unprocessedData.put(index, Constants.PAN_NUMBER + " is invalid.");
			log.info("PAN Number is invalid : {} ", panNumber);
			return;
		}
		Optional<BlacklistedPANNumberEntity> entityOptional = blacklistedPANNumberRepository
				.findByPanNumberIgnoreCase(panNumber);
		if (entityOptional.isPresent()) {
			unprocessedData.put(index, Constants.PAN_NUMBER + " already exist");
			log.info("PAN Number already exist : {} ", panNumber);
			return;
		}
		BlacklistedPANNumberEntity entity = new BlacklistedPANNumberEntity();
		entity.setPanNumber(panNumber);
		blacklistedPANNumberRepository.save(entity);
	}

	@Override
	public BaseDTO validate(String panNumber) {
		BaseDTO responseDTO = new BaseDTO();
		if (Boolean.FALSE.equals(commonUtil.isValidatePanNumber(panNumber))) {
			responseDTO.setMessage("Please Enter the valid Pan Number");
			responseDTO.setStatusCode(ErrorDescription.BAD_REQUEST.getCode());
			return responseDTO;
		}
		Optional<BlacklistedPANNumberEntity> entityOptional = blacklistedPANNumberRepository
				.findByPanNumberIgnoreCase(panNumber.trim());

		if (entityOptional.isPresent()) {
			responseDTO.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
			responseDTO.setMessage("PAN Number is Blacklisted");
			return responseDTO;
		}
		responseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
		responseDTO.setMessage("PAN Number is not Blacklisted");
		return responseDTO;
	}

	@Override
	public BaseDTO create(BlacklistedPanDTO blacklistedPanDTO) {

		BaseDTO responseDTO = new BaseDTO();
		String message = "";
		if (Boolean.FALSE.equals(commonUtil.isValidatePanNumber(blacklistedPanDTO.getPanNumber()))) {
			responseDTO.setMessage("Please Enter the valid Pan Number");
			responseDTO.setStatusCode(ErrorDescription.BAD_REQUEST.getCode());
			return responseDTO;
		}

		Optional<BlacklistedPANNumberEntity> entityOptional = blacklistedPANNumberRepository
				.findByPanNumberIgnoreCase(blacklistedPanDTO.getPanNumber().trim());

		if (entityOptional.isPresent()) {
			message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
			responseDTO.setMessage("This Pan Number " + message);
			responseDTO.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
			return responseDTO;
		}
		BlacklistedPANNumberEntity blacklistedPANNumberEntity = new BlacklistedPANNumberEntity();
		blacklistedPANNumberEntity.setPanNumber(blacklistedPanDTO.getPanNumber());
		blacklistedPANNumberEntity.setCreatedBy(oauth2UserDetails.getId());
		blacklistedPANNumberRepository.save(blacklistedPANNumberEntity);

		message = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
		responseDTO.setMessage(blacklistedPanDTO.getPanNumber() + " " + message);
		responseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
		return responseDTO;
	}

	@Override
	public BaseDTO update(UUID id, String panNumber) {

		BaseDTO responseDTO = new BaseDTO();
		String message = "";
		
		if (Boolean.FALSE.equals(commonUtil.isValidatePanNumber(panNumber))) {
			responseDTO.setMessage("Please Enter the valid Pan Number");
			responseDTO.setStatusCode(ErrorDescription.BAD_REQUEST.getCode());
			return responseDTO;
		}

		Optional<BlacklistedPANNumberEntity> entityOptional = blacklistedPANNumberRepository.findById(id);

		if (entityOptional.isPresent()) {
			if (entityOptional.get().getPanNumber().equals(panNumber)) {
				message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
				responseDTO.setMessage("This Pan Number " + message);
				responseDTO.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());

			} else {
				Optional<BlacklistedPANNumberEntity> entityOptional1 = blacklistedPANNumberRepository.findByPanNumberIgnoreCase(panNumber);
				if(entityOptional1.isPresent()) {
				if(entityOptional1.get().getPanNumber().equals(panNumber)) {
					message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
					responseDTO.setMessage("This Pan Number " + message + " for other id");
					responseDTO.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					return responseDTO;
				}}
				entityOptional.get().setPanNumber(panNumber);
				entityOptional.get().setModifiedBy(oauth2UserDetails.getId());
				blacklistedPANNumberRepository.save(entityOptional.get());
				message = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				responseDTO.setMessage("This Pan Number " + message);
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
        BlacklistedPanDTO panNumber = new BlacklistedPanDTO();
		try {
			Optional<BlacklistedPANNumberEntity> entityOptional = blacklistedPANNumberRepository.findById(id);

			if (entityOptional.isPresent()) {
				panNumber = objectMapper.convertValue(entityOptional.get(), BlacklistedPanDTO.class);
				responseDTO.setResponseContent(panNumber);
				responseDTO.setMessage("Successfully Get Blacklisted Pan Number");
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
			List<BlacklistedPANNumberEntity> blacklistedPANNumberEntities = blacklistedPANNumberRepository.findAll();
			if (blacklistedPANNumberEntities.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());

			} else {
				response.setResponseContents(blacklistedPANNumberEntities);
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
		List<BlacklistedPANNumberEntity> contentList = new ArrayList<BlacklistedPANNumberEntity>();
		Page<BlacklistedPANNumberEntity> blackListPan = null;
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

						blackListPan = blacklistedPANNumberRepository.getReportwithDate(pageable,
								new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
										.parse(requestData.getFromDate() + " " + "00:00:00"),
								new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
										.parse(requestData.getToDate() + " " + "23:59:59"));
					} else {
						blackListPan = blacklistedPANNumberRepository.getReport(pageable);

					}
				} else {
					if (StringUtils.isNotBlank(requestData.getFromDate())
							&& StringUtils.isNotBlank(requestData.getToDate())) {

						blackListPan = blacklistedPANNumberRepository.getReportSearchwithDate(
								requestData.getSearch().toUpperCase(), pageable,
								new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
										.parse(requestData.getFromDate() + " " + "00:00:00"),
								new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
										.parse(requestData.getToDate() + " " + "23:59:59"));
					} else {
						blackListPan = blacklistedPANNumberRepository
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
				for (BlacklistedPANNumberEntity blacklistedPANNumberEntity : blackListPan) {
					contentList.add(blacklistedPANNumberEntity);
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
