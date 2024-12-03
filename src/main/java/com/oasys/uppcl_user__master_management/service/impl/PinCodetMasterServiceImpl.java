package com.oasys.uppcl_user__master_management.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.oasys.cexception.DaoLayerException;
import com.oasys.cexception.NoRecoerdFoundException;
import com.oasys.clients.userservice.utils.CommonUtil;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.PinCodeMasterDao;
import com.oasys.uppcl_user__master_management.dto.DistrictMasterDTO;
import com.oasys.uppcl_user__master_management.dto.PinCodeMasterDTO;
import com.oasys.uppcl_user__master_management.entity.DistrictMasterEntity;
import com.oasys.uppcl_user__master_management.entity.PinCodeMasterEntity;
import com.oasys.uppcl_user__master_management.entity.StateMasterEntity;
import com.oasys.uppcl_user__master_management.repository.DistrictMasterRepository;
import com.oasys.uppcl_user__master_management.repository.PinCodeMasterRepository;
import com.oasys.uppcl_user__master_management.repository.StateMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.response.ResponseMessageConstant;
import com.oasys.uppcl_user__master_management.service.PinCodeMasterService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class PinCodetMasterServiceImpl implements PinCodeMasterService {

	@Autowired
	DistrictMasterRepository districtMasterRepository;

	@Autowired
	PinCodeMasterRepository pinCodeMasterRepository;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	PinCodeMasterDao pinCodeMasterDao;

	@Autowired
	StateMasterRepository stateMasterRepository;

	final String DELETE_OK = "Y";

	@Autowired
	MessageSource messageSource;

	@Override
	public BaseDTO create(PinCodeMasterDTO pinCodeMasterDTO) {
		BaseDTO baseDTO = new BaseDTO(ResponseMessageConstant.CREATE_SUCCESS_RESPONSE);
		Optional<DistrictMasterEntity> districtMasterEntity = districtMasterRepository
				.findById(pinCodeMasterDTO.getDistrictUUId());
		if (!districtMasterEntity.isPresent()) {
			throw new DaoLayerException("No district found for id:"+
					pinCodeMasterDTO.getDistrictUUId().toString());
		}
		if (pinCodeMasterDTO.getStateId() != null) {
			Optional<StateMasterEntity> state =stateMasterRepository.findById(pinCodeMasterDTO.getStateId());
			if (!state.isPresent()) {
				
			}
		} else {
			baseDTO.setMessage("State Not Found");
			baseDTO.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			//log.warn("State Not Found");
		}
		
		if (validatePinCode(pinCodeMasterDTO.getPinCode()) == true) {
		//PinCodeMasterEntity entity = pinCodeMasterRepository.findByPinCode2(pinCodeMasterDTO.getPinCode());
		PinCodeMasterEntity pinCodeMasterEntity = commonUtil.modalMap(pinCodeMasterDTO, PinCodeMasterEntity.class);
		pinCodeMasterEntity.setRemarks(pinCodeMasterDTO.getRemarks());
		pinCodeMasterEntity.setDistrictId(districtMasterEntity.get());
		pinCodeMasterRepository.save(pinCodeMasterEntity);
		return baseDTO;
		
		} else {
			String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,Locale.US);
			baseDTO.setMessage(pinCodeMasterDTO.getPinCode() + " " + msg  );
			baseDTO.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
			
		}
		return baseDTO;

	}
		
		private boolean validatePinCode(String str1) {

			PinCodeMasterEntity entity = pinCodeMasterRepository.findByPinCode2(str1);
			if (entity == null) {
				log.info("Valid ------>");
				return true;
			} else {
				log.warn("Invalid ------>");
				return false;
			}
		}
	
	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		
		BaseDTO response = new BaseDTO();
		try {
			response = pinCodeMasterDao.getLazyList(requestData);
		} catch (Exception e) {
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		
		}
		
		return response;
	}
	@Override
	public BaseDTO getAll() {
		BaseDTO response = new BaseDTO();

		List<PinCodeMasterEntity> pinCodeMasterEntities = pinCodeMasterRepository.findAll();
		if (pinCodeMasterEntities == null || pinCodeMasterEntities.size() == 0) {
			throw new NoRecoerdFoundException();
		}
		response.setResponseContents(pinCodeMasterEntities);
		return response;
	}

	@Override
	public BaseDTO getById(UUID id) {

		//BaseDTO baseDTO = new BaseDTO();
		Optional<PinCodeMasterEntity> districtName = pinCodeMasterRepository.findById(id);
		if (!districtName.isPresent()) {
			throw new NoRecoerdFoundException();
		}
		BaseDTO baseDTO = new BaseDTO(ResponseMessageConstant.SUCCESS_RESPONSE);
		baseDTO.setResponseContent(districtName.get());
		return baseDTO;
	}

	@Override
	public BaseDTO getByIds(Set<UUID> ids) {
		BaseDTO baseDTO = new BaseDTO();
		List<PinCodeMasterEntity> pinCodeMasterEntities = pinCodeMasterRepository.findByIdIn(ids);
		if (pinCodeMasterEntities == null || pinCodeMasterEntities.size() == 0) {
			throw new NoRecoerdFoundException();
		}

		baseDTO.setResponseContents(pinCodeMasterEntities);
		return baseDTO;
	}

	@Override
	public BaseDTO getAllActive() {
		List<PinCodeMasterEntity> pinCodeMasterEntities = pinCodeMasterRepository.getAllActive();
		if (pinCodeMasterEntities == null || pinCodeMasterEntities.isEmpty()) {
			throw new NoRecoerdFoundException();
		}
		BaseDTO baseDTO = new BaseDTO(ResponseMessageConstant.SUCCESS_RESPONSE);
		baseDTO.setResponseContents(pinCodeMasterEntities);
		return baseDTO;
	}

	@Override
	public BaseDTO delete(UUID id) {
		BaseDTO baseDTO = new BaseDTO(ResponseMessageConstant.DELET_RESPONSE);
		Optional<PinCodeMasterEntity> pinCodeMasterEntity = pinCodeMasterRepository.findById(id);
		if (!pinCodeMasterEntity.isPresent()) {
			throw new NoRecoerdFoundException();
		}
		pinCodeMasterRepository.delete(pinCodeMasterEntity.get());
		return baseDTO;

	}

	public BaseDTO softDelete(UUID id) {
		BaseDTO baseDTO = new BaseDTO(ResponseMessageConstant.DELET_RESPONSE);
		Optional<PinCodeMasterEntity> pinOptional = pinCodeMasterRepository.findById(id);
		if (!pinOptional.isPresent()) {
			throw new NoRecoerdFoundException();
		}
		PinCodeMasterEntity pinCodeMasterEntity = pinOptional.get();
		pinCodeMasterEntity.setStatus(false);
		pinCodeMasterRepository.save(pinCodeMasterEntity);
		return baseDTO;

	}

	@Override
	public BaseDTO getByDistrictId(UUID id) {
		//BaseDTO baseDTO = new BaseDTO();
		DistrictMasterEntity entity = new DistrictMasterEntity();
		entity.setId(id);
		
		BaseDTO baseDTO = new BaseDTO(ResponseMessageConstant.SUCCESS_RESPONSE);
		List<PinCodeMasterEntity> pinCodeMasterEntities = pinCodeMasterRepository.findByDistrictId(entity);
		if (pinCodeMasterEntities == null || pinCodeMasterEntities.isEmpty()) {
			throw new NoRecoerdFoundException();
		}
		baseDTO.setResponseContents(pinCodeMasterEntities);
		return baseDTO;
	}

	/*@Override
	public BaseDTO update(UUID id, PinCodeMasterDTO pinCodeMasterDTO) {
		BaseDTO response = new BaseDTO(ResponseMessageConstant.UPDAE_RESPONSE);
		Optional<PinCodeMasterEntity> optional = pinCodeMasterRepository.findById(id);
		if (!optional.isPresent()) {
			throw new NoRecoerdFoundException();
		}
		PinCodeMasterEntity pinCodeMasterEntity = optional.get();
		commonUtil.modalMapCopy(pinCodeMasterDTO, pinCodeMasterEntity);

		Optional<DistrictMasterEntity> districtMasterEntity = districtMasterRepository
				.findById(pinCodeMasterDTO.getDistrictUUId());
		if (!districtMasterEntity.isPresent()) {
			throw new InvalidRequestException(AttributeConstant.DISTRICT_ID,
					pinCodeMasterDTO.getDistrictUUId().toString());
		}
		pinCodeMasterEntity.setDistrictId(districtMasterEntity.get());
		PinCodeMasterEntity afterUpdate = pinCodeMasterRepository.save(pinCodeMasterEntity);
		response.setResponseContent(afterUpdate);
		return response;

	}*/

	

	@Override
	public BaseDTO update(UUID id, PinCodeMasterDTO pinCodeMasterDTO) {
		
		BaseDTO response = new BaseDTO();
		try {
			boolean check = false;
			
			Optional<PinCodeMasterEntity> optional = pinCodeMasterRepository.findById(id);
			if (optional.isPresent()) {
				PinCodeMasterEntity pincodeentity = new PinCodeMasterEntity();
				pincodeentity = optional.get();
				if (pinCodeMasterDTO.getStateId() != null) {
					Optional<StateMasterEntity> state =stateMasterRepository.findById(pinCodeMasterDTO.getStateId());
					if (!state.isPresent()) {
						
					}
				} else {
					response.setMessage("State Not Found");
					response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
					return response;
				}
				Optional<DistrictMasterEntity> districtMasterEntity = districtMasterRepository
						.findById(pinCodeMasterDTO.getDistrictUUId());
				if (!districtMasterEntity.isPresent()) {
					throw new DaoLayerException("No district found for id:"+
							pinCodeMasterDTO.getDistrictUUId().toString());
				}
				List<PinCodeMasterEntity> checkCode = pinCodeMasterRepository
						.findByPinCode3(pinCodeMasterDTO.getPinCode());
				for (PinCodeMasterEntity entity : checkCode) {
					if (entity.getPinCode().equals(pinCodeMasterDTO.getPinCode())) {
						if (id.equals(entity.getId())) {
							check = false;
						} else
							check = true;
					} else {
						check = false;
					}
				}

				if (check) {
					String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
					response.setMessage(pinCodeMasterDTO.getPinCode() + " " + msg);
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					
				} else {
					
					pincodeentity.setDivisionName(pinCodeMasterDTO.getDivisionName());
					pincodeentity.setPinCode(pinCodeMasterDTO.getPinCode());
					pincodeentity.setDistrictId(districtMasterEntity.get());
					pincodeentity.setStatus(pinCodeMasterDTO.getStatus());
					pincodeentity.setRemarks(pinCodeMasterDTO.getRemarks());
					PinCodeMasterEntity afterUpdate = pinCodeMasterRepository.save(pincodeentity);
					response.setResponseContent(afterUpdate);
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(pinCodeMasterDTO.getPinCode() + " " + msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					
				}
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				
			}
		} catch (DataIntegrityViolationException e) {
			
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());

		} catch (Exception e) {
			
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		
		return response;
	}


	@Override
	public BaseDTO findByPinCode(String pincode) {
		Optional<PinCodeMasterEntity> optional = pinCodeMasterRepository.findByPinCode(pincode);
		BaseDTO response = new BaseDTO();
		if (!optional.isPresent()) {
//			throw new NoRecoerdFoundException();
			response.setMessage("No Record Found");
			response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
		}
		else {
		response.setMessage(ResponseMessageConstant.SUCCESS_RESPONSE.getMessage());
		response.setStatusCode(ResponseMessageConstant.SUCCESS_RESPONSE.getErrorCode());
		response.setResponseContent(optional.get());
		}
		return response;
	

	}

	@Override
	public BaseDTO uploadExcelFile(MultipartFile file) {
		BaseDTO response = new BaseDTO();
		String message = null;
		int falseCount = 0, trueCount = 0;

		List<PinCodeMasterEntity> pincodedto = new ArrayList<PinCodeMasterEntity>();
		String stateName = "State Name";
		String districtName = "District Name";
		String pinCode = "Pin Code";
		String divisionName = "Division Name";
		String districName = "District Name";

		try {
			if (file == null) {
				//log.info("File is empty");
				response.setMessage("FILE PARAMETER IS NULL");
				response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
				return response;
			}
			String fileDirectory = System.getProperty("user.dir") + File.separator + "tmp" + File.separator
					+ file.getOriginalFilename();
			File tempFile = new File(fileDirectory);
			if (tempFile.getParentFile().exists() == false) {
				//log.info("Directory not present, now creating " + tempFile.getAbsolutePath());
				tempFile.getParentFile().mkdirs();
			}
			BufferedOutputStream fileOutputStream = new BufferedOutputStream(new FileOutputStream(fileDirectory));
			fileOutputStream.write(file.getBytes());
			fileOutputStream.close();

			List<Map<String, Object>> excelList = getExcelFileAsJson(tempFile);
			for (Map<String, Object> entity : excelList) {

				if (entity.containsKey("Division Name")) {
					if (entity.get(divisionName).toString().trim().isEmpty()) {
						message = divisionName + " cell is empty";
						response.setMessage(message);
						//log.info(message);
						response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
						return response;
					}
				} else {
					message = divisionName + " Column is missing";
					response.setMessage(message);
					//log.info(message);
					response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
					return response;
				}

				if (entity.containsKey("Pin Code")) {
					if (entity.get(pinCode).toString().trim().isEmpty()) {
						message = pinCode + " cell is empty";
						response.setMessage(message);
						log.warn(message);
						response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
						return response;
					}
					if (!entity.get(pinCode).toString().matches("[0-9]+")) {
						message = pinCode + " should be Numeric - " + entity.get(pinCode).toString();
						response.setMessage(message);
						log.warn(message);
						response.setStatusCode(ErrorDescription.AUTHENTICATION_FAILED.getCode());
						return response;
					}
				} else {
					message = pinCode + " Column is missing";
					response.setMessage(message);
					log.warn(message);
					response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
					return response;
				}

				if (entity.containsKey("District Name")) {
					if (entity.get(districtName).toString().trim().isEmpty()) {
						message = districtName + " cell is empty";
						response.setMessage(message);
						//log.info(message);
						response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
						return response;
					}
				} else {
					message = districtName + " Column is missing";
					response.setMessage(message);
					//log.info(message);
					response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
					return response;
				}

				if (entity.containsKey("State Name")) {
					if (entity.get(stateName).toString().trim().isEmpty()) {
						log.warn("stateName", stateName);

						message = stateName + " cell is empty";
						response.setMessage(message);
						//log.info(message);
						response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
						return response;
					}
				} else {
					message = stateName + " Column is missing";
					response.setMessage(message);
					//log.info(message);
					response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
					return response;
				}

				long PinCodeCount = 0;
				/*
				 * for (Map<String, Object> entity2 : excelList) { if
				 * (entity.get(pinCode).toString().trim()
				 * .equalsIgnoreCase(entity2.get(pinCode).toString().trim())) { PinCodeCount++;
				 * if (PinCodeCount > 1) { message = pinCode + " has been repeated in row num "
				 * + (excelList.indexOf(entity) + 2) + " and " + (excelList.indexOf(entity2) +
				 * 2); log.info(message + " - " + entity.get(pinCode).toString());
				 * response.setMessage(message);
				 * response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode()); return
				 * response; } } }
				 * 
				 * if (!validatePin(entity.get(pinCode).toString().trim())) { message =
				 * entity.get(pinCode).toString() + " is Already Exist";
				 * response.setMessage(message); log.info(message);
				 * response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode()); return
				 * response; }
				 */
				if (validatePin(entity.get(pinCode).toString().trim())) {
					PinCodeMasterEntity userEntity = new PinCodeMasterEntity();

					PinCodeMasterDTO dto = new PinCodeMasterDTO();
					DistrictMasterDTO districtdto = new DistrictMasterDTO();
					StateMasterEntity stateMaster = stateMasterRepository.getstateName((String) entity.get(stateName));
					log.warn("stateMaster", stateMaster);
					log.warn("req" + entity.get(districtName));
					String district = "";
					if (entity.containsKey("District Name")) {
						district = entity.get(districtName).toString();
					}
					log.warn("ditrictMaster ------" + district);
					log.warn("stateId" + stateMaster.getId());
					DistrictMasterEntity districtMaster = districtMasterRepository.findByDistrictAndStateId(district,
							stateMaster.getId());
					log.info("districtMaster"+ districtMaster);
					
					if(districtMaster!=null) {

					userEntity.setDivisionName(entity.get(divisionName).toString());
					userEntity.setPinCode(entity.get(pinCode).toString());
					userEntity.setDistrictId(districtMaster);
					userEntity.setStatus(true);
					pincodedto.add(userEntity);
					pinCodeMasterRepository.save(userEntity);
					}

				}

			}

			
			if (trueCount == excelList.size()) {
				response.setMessage("Successfully Uploaded");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			} else if (falseCount == excelList.size()) {
				response.setMessage("Uploaded Failed");
				response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			} else {
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			}

		} catch (DataIntegrityViolationException e) {
			log.error("Exception: {} ", e.getMessage());
			message = "Failure to Upload";
			response.setMessage(message);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		} catch (Exception ex) {
			log.error("Exception: {} ", ex.getMessage());
			message = "Failure to Upload";
			response.setMessage(message);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		return response;

	}

	public List<Map<String, Object>> getExcelFileAsJson(File file) throws IOException, InvalidFormatException {
		List<String> keyValue = new ArrayList<>();
		List<Map<String, Object>> list = new ArrayList<>();
		try {
			Workbook workbook = WorkbookFactory.create(file);
			log.info("workbooksheets" + workbook.getNumberOfSheets());
			Sheet Initialsheet = workbook.getSheetAt(0);
			DataFormatter dataFormatter = new DataFormatter();
			Row header = Initialsheet.getRow(0);
			int rowCount = Initialsheet.getPhysicalNumberOfRows();
			int columnCount = header.getLastCellNum();

			for (int i = 0; i < columnCount; i++) {
				String headerName = dataFormatter.formatCellValue(header.getCell(i));
				keyValue.add(headerName);
			}
			for (int i = 1; i < rowCount; i++) {
				Map<String, Object> json = new HashMap<>();
				for (int j = 0; j < columnCount; j++) {
					String value = dataFormatter.formatCellValue(Initialsheet.getRow(i).getCell(j));
					json.put(keyValue.get(j), value);
				}
				list.add(json);
			}

			//log.info(" row count list", list);
		} catch (Exception e) {
			log.error(" row count list" + e);
		}
		return list;
	}

	private boolean validatePin(String pinCode) {
		PinCodeMasterEntity entity = pinCodeMasterDao.findByPinCode(pinCode);
		if (entity == null) {
			return true;
		} else {
			return false;
		}
	}

}
