package com.oasys.uppcl_user__master_management.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.oasys.clients.userservice.utils.POIUtils;
import com.oasys.config.BaseDTO;
import com.oasys.config.Constants;
import com.oasys.config.ErrorDescription;
import com.oasys.uppcl_user__master_management.dto.errorCodeDTO;
import com.oasys.uppcl_user__master_management.entity.ErrorCodeEntity;
import com.oasys.uppcl_user__master_management.repository.ErrorCodeMasterRepository;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Service
public class ErrorCodeMasterServiceImp {
	
	@Autowired
	ErrorCodeMasterRepository errorCodeMasterRepository;
	public BaseDTO addmultiplebank(List<errorCodeDTO> yesBankRequestErrorCodeDTO) {
		BaseDTO response = new BaseDTO();

		try {
			log.info("<==== ErrorCodeMasterservice started ===> ");

			for (errorCodeDTO yesBankErrorCodeDTO : yesBankRequestErrorCodeDTO) {
				String responseCode = yesBankErrorCodeDTO.getResponse();

				List<ErrorCodeEntity> existingCodes = errorCodeMasterRepository
						.findByResponse(responseCode);
				if (!existingCodes.isEmpty()) {
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					response.setMessage("Response code already exists");
					return response;
				}

				ErrorCodeEntity errorEntity = new ErrorCodeEntity();
				errorEntity.setResponseCode(responseCode);
				errorEntity.setDescription(yesBankErrorCodeDTO.getDescription());
				errorEntity.setBhimAadhaarPay(yesBankErrorCodeDTO.getBhim());
				errorEntity.setReceiptRequired(yesBankErrorCodeDTO.getReceipt());
				errorEntity.setStatus(yesBankErrorCodeDTO.getStatus());

				errorCodeMasterRepository.save(errorEntity);

			}

			response.setMessage("YesBank's added successfully");
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());

		} catch (DataIntegrityViolationException ee) {
			log.info("<==== ExceptionDataIntegrityViolationException ===> ", ee.getRootCause());
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		} catch (Exception e) {
			log.info("<==== Exception ===> ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}

		return response;
	}

	public BaseDTO addBank(errorCodeDTO bankDto) {

		BaseDTO response = new BaseDTO();
		ErrorCodeEntity yesBankErrorEntity = new ErrorCodeEntity();
		try {
			List<ErrorCodeEntity> yesBankError = errorCodeMasterRepository
					.findByResponse(bankDto.getResponse());
			if (!yesBankError.isEmpty()) {
				for (ErrorCodeEntity yesBankEntity : yesBankError) {
					if (yesBankEntity.getResponseCode().equals(bankDto.getResponse())) {
						response.setMessage("Already exist");
						response.setErrorCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					}
				}
			} else {

				yesBankErrorEntity.setResponseCode(bankDto.getResponse());
				yesBankErrorEntity.setDescription(bankDto.getDescription());
				yesBankErrorEntity.setBhimAadhaarPay(bankDto.getBhim());
				yesBankErrorEntity.setReceiptRequired(bankDto.getReceipt());
				yesBankErrorEntity.setStatus(bankDto.getStatus());

				errorCodeMasterRepository.save(yesBankErrorEntity);
				response.setMessage("YesBank added successfully");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			}
		} catch (Exception e) {
			log.info("<====Bank Exception=====>", e);
			response.setMessage("Failed to add the details");
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		return response;
	}

	public BaseDTO getAll() {
		BaseDTO response = new BaseDTO();
		try {
			List<ErrorCodeEntity> errorEntity = errorCodeMasterRepository
					.findAll();
			if (errorEntity.isEmpty()) {
				response.setMessage("Details Not Found");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			} else {
				response.setMessage("Details Fetched successfully");
				response.setResponseContent(errorEntity);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());

			}
		} catch (Exception e) {
			log.info("<=========Failed To Fetch The Details===========>", e);
			response.setMessage("Failed to fetch the details");
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		return response;
	}

	public BaseDTO getById(UUID id) {
		BaseDTO response = new BaseDTO();
		try {
			Optional<ErrorCodeEntity> errorEntity = errorCodeMasterRepository
					.findById(id);
			if (errorEntity.isPresent()) {
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				response.setResponseContent(errorEntity);
				response.setMessage("Details Fetched successfully");
			} else {
				response.setMessage("Details Not Found");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.info("<=========Failed To Fetch The Details===========>", e);
			response.setMessage("Failed to fetch the details");
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		return response;
	}

	public BaseDTO updateById(UUID id, errorCodeDTO bankDto) {
		BaseDTO response = new BaseDTO();

		try {
			Optional<ErrorCodeEntity> optional = errorCodeMasterRepository.findById(id);
			if (optional.isPresent()) {
				ErrorCodeEntity errorEntity = optional.get();
				if(bankDto.getBhim()!=null) {
					errorEntity.setBhimAadhaarPay(bankDto.getBhim());
				}
				if(bankDto.getStatus()!=null) {
					errorEntity.setStatus(bankDto.getStatus());
				}
				if(bankDto.getResponse()!=null) {
					errorEntity.setResponseCode(bankDto.getResponse());
				}
				if(bankDto.getReceipt()!=null) {
					errorEntity.setReceiptRequired(bankDto.getReceipt());
				}
				if(bankDto.getDescription()!=null) {
					errorEntity.setDescription(bankDto.getDescription());
				}
				ErrorCodeEntity afterUpdate = errorCodeMasterRepository
						.save(errorEntity);
				response.setResponseContent(afterUpdate);
				response.setMessage("Sucessfully Updated");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());

			}else {
				response.setMessage("id is not available");
				response.setStatusCode(201);
			}

		} catch (Exception e) {
			log.info("<============Update By Id==============>", e);
			response.setStatusCode(409);
			response.setMessage("Server Error");

		}
		return response;
	}

	public BaseDTO softDelete(UUID id) {
		BaseDTO response = new BaseDTO();
		try {
			ErrorCodeEntity errorEntity = errorCodeMasterRepository.getOne(id);
			if (errorEntity != null) {
				if (errorEntity.getStatus() == true) {
					errorEntity.setStatus(false);
					errorEntity = errorCodeMasterRepository.save(errorEntity);
					response.setMessage("Successfully Deleted");
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				} else {
					response.setMessage("Unable to Delete ");
				}
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}

		} catch (Exception e) {
			log.info("<==========Soft Delete Exception===========> ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());

		}
		return response;
	}

	public BaseDTO importExcelFile(MultipartFile file) {

		int newResponseCount = 0;
		int duplicateResponseCount = 0;

		BaseDTO responseDTO = new BaseDTO();
		Map<Integer, String> unprocessedData = new HashMap<>();
		Workbook workbook = null;
		try {
			workbook = WorkbookFactory.create(file.getInputStream());
			Sheet worksheet = workbook.getSheetAt(0);
			for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
				if (index > 0) {

					// this.processData(workbook, worksheet, index, unprocessedData);
					String response = POIUtils.getByColumnName(workbook, worksheet, Constants.RESPONSE_CODE, index);
					String description = POIUtils.getByColumnName(workbook, worksheet, Constants.DESCRIPTION, index);
					String bhim = POIUtils.getByColumnName(workbook, worksheet, Constants.BHIM_AADHAR_PAY, index);
					String receipt = POIUtils.getByColumnName(workbook, worksheet, Constants.RECEIPT_REQUIRED, index);
					String status = POIUtils.getByColumnName(workbook, worksheet, Constants.STATUS_, index);

					log.info("description is missing : {} ", unprocessedData.size());

					if (StringUtils.isBlank(response)) {
						unprocessedData.put(index, Constants.RESPONSE_CODE + " is missing." + response);
						log.info("response is missing : {} ", response);
						return responseDTO;
					}

					if (StringUtils.isBlank(description)) {
						unprocessedData.put(index, Constants.DESCRIPTION + " is missing." + description);
						log.info("description is missing : {} ", description);
						return responseDTO;
					}

					if (StringUtils.isBlank(status)) {
						unprocessedData.put(index, Constants.BHIM_AADHAR_PAY + " is missing." + status);
						log.info("status is missing : {} ", status);
						return responseDTO;
					}

					List<ErrorCodeEntity> errorResponse = errorCodeMasterRepository
							.findByResponse(response);

					ErrorCodeEntity errorEntity = new ErrorCodeEntity();

					if (!errorResponse.isEmpty()) {
						for (ErrorCodeEntity error : errorResponse) {
							if (error.getResponseCode().equals(response)) {
								duplicateResponseCount++;
								unprocessedData.put(index, Constants.RESPONSE_CODE + " is a duplicate " + response);
								log.info("duplicate value passed : {} ", response);
								log.info("<====================Duplicate Response Count====================>",
										duplicateResponseCount);

							}
						}
					} else {
						errorEntity.setResponseCode(response);
						errorEntity.setDescription(description);
						errorEntity.setBhimAadhaarPay(bhim);
						errorEntity.setStatus(Boolean.parseBoolean(status));
						errorEntity.setReceiptRequired(receipt);
						errorCodeMasterRepository.save(errorEntity);
						newResponseCount++;
						log.info("<====================New Response Count====================>", newResponseCount);

					}

				}
				responseDTO.setErrorCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				responseDTO.setDuplicateResponseCodeCount(duplicateResponseCount);
				responseDTO.setNewResponseCodeCount(newResponseCount);
				responseDTO.setUserDisplayMesg("File uploaded successfully!");

			}

		} catch (NumberFormatException e) {
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

		return responseDTO;

	}

}
