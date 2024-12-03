package com.oasys.uppcl_user__master_management.dao.impl;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.BankNameDao;
import com.oasys.uppcl_user__master_management.dto.BankNameDTO;
import com.oasys.uppcl_user__master_management.entity.BankNameMasterEntity;
import com.oasys.uppcl_user__master_management.repository.BankNameRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class BankNameDaoImpl implements BankNameDao {
	@Autowired
	BankNameRepository bankNameRepository;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	MessageSource messageSource;

	@Override
	public BaseDTO create(BankNameDTO bankNameDTO) {
		//log.info(" <----- BankNameDaoImpl.create() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			if (validateBankName(bankNameDTO.getBankName()) == true) {
				//log.info("Bank Name - {} Is Valid..", bankNameDTO.getBankName());
				BankNameMasterEntity bankName = objectMapper.convertValue(bankNameDTO, BankNameMasterEntity.class);
				bankNameRepository.save(bankName);
				//log.info("bank Name saved sucessfully - {}",bankName.getBankName());
				String msg = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(bankNameDTO.getBankName() + " " + msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Added");
			} else {
				String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,Locale.US);
				response.setMessage("This Bank " + msg  );
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				//log.warn("Bank Name is Already Exists  - {} ", bankNameDTO.getBankName());
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- BankNameDaoImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			log.warn("Exception" + bankNameDTO );
		} catch (Exception e) {
			log.error("<---- BankNameDaoImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			
		}
		//log.info(" <----- BankNameDaoImpl.create() END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO update(UUID id, BankNameDTO bankNameDTO) {
		//log.info(" <----- BankNameDaoImpl.update() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			boolean check = false;
			Optional<BankNameMasterEntity> optional = bankNameRepository.findById(id);
			if (optional.isPresent()) {
				//log.info("Bank Name id -{}",id);

				BankNameMasterEntity bankName = new BankNameMasterEntity();
				bankName = optional.get();
				List<BankNameMasterEntity> checkName = bankNameRepository.check(bankNameDTO.getBankName());
				for (BankNameMasterEntity entity : checkName) {
					if (entity.getBankName().equalsIgnoreCase(bankNameDTO.getBankName())) {
						if(id.equals(entity.getId())) {
							check = false;
						}else	
						check = true;

					} else {
						check = false;
					}
				}

				if (check) {
					String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,Locale.US);
					response.setMessage("This Bank " + msg  );
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.info("Bank Name - {} Already Exist");
				} else {
					bankName.setBankName(bankNameDTO.getBankName());
					//bankName.setBankTypeId(bankNameDTO.getBankTypeId());
					bankName.setBankId(bankNameDTO.getBankId());
					bankName.setStatus(bankNameDTO.getStatus());
					bankName.setRemarks(bankNameDTO.getRemarks());
					BankNameMasterEntity afterUpdate = bankNameRepository.save(bankName);
					response.setResponseContent(afterUpdate);
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(bankNameDTO.getBankName() + " " + msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				
				}
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- BankNameDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());

		} catch (Exception e) {
			log.error("<---- BankNameDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info(" <----- BankNameDaoImpl.update() END -----> ");
		return response;
	}

	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- BankNameDaoImpl.getById() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			Optional<BankNameMasterEntity> bankName = bankNameRepository.findById(id);
			if (bankName.isPresent()) {
				//log.info("Bank Name id -{}",id);
				response.setResponseContent(bankName);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get BankName Details ");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- BankNameDaoImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg );
		}
		//log.info(" <----- BankNameDaoImpl.getById() END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAll() {
		//log.info(" <----- BankNameDaoImpl.getAll() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<BankNameMasterEntity> bankName = bankNameRepository.findAll();

			if (bankName.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setResponseContents(bankName);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				//log.info("Bank Name Get All Details Fetched successfully");
			}
		} catch (Exception e) {
			log.error("<----- BankNameDaoImpl.getAll() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- BankNameDaoImpl.getAll() END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		//log.info(" <----- BankNameDaoImpl.getAllActive() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<BankNameMasterEntity> bankName = bankNameRepository.getAllActive();

			if (bankName.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg );
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setResponseContents(bankName);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				//log.info("Successfully Get All Active  Bank Name Details..");
			}
		} catch (Exception e) {
			log.error("<----- BankNameDaoImpl.getAllActive() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- BankNameDaoImpl.getAllActive() END -----> ");
		return response;
	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- BankNameDaoImpl.getLazyList() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> totalListOfDataReport = null;
		List<Map<String, Object>> listCount = null;
		int pageSize = 0;
		int totalRecords = 0;
		int totalPages = 0;
		String appendBankName = "";
		//String appendBankType = "";
		String createdDate = "";
		String appendOrder = " ORDER BY ";
		StringBuffer countBuffer = new StringBuffer();
		try {
			StringBuffer buffer = new StringBuffer();
			if (requestData.getSearch() != null) {
				appendBankName = " UPPER(d.bank_name) like '%" + requestData.getSearch().toUpperCase() + "%'";
				//appendBankType = " OR UPPER(s.type) like '%" + requestData.getSearch().toUpperCase() + "%'";
				createdDate = " or UPPER(d.created_date) LIKE '%" + requestData.getSearch().toUpperCase() + "%'";
			} else {
				appendBankName = " UPPER(d.bank_name) like '%" + "" + "%'";
				//appendBankType = " OR UPPER(s.type) like '%" + "" + "%'";
				createdDate = " or UPPER(d.created_date) LIKE '%" + "" + "%'";
			}
		/*	countBuffer.append(
					"select COUNT(*) as count FROM bank_name d JOIN bank_type s ON s.id=d.bank_type_id And s.status=1 WHERE"
							+ appendBankName + " " + appendBankType + " "  + createdDate + " "  );*/
			
			countBuffer.append(
					"select COUNT(*) as count FROM bank_name d where d.status=1 and " +appendBankName + createdDate + " ");
			
			listCount = jdbcTemplate.queryForList(countBuffer.toString());
			if (listCount.size() > 0 && listCount.get(0).get("count") != null) {
				totalRecords = Integer.parseInt(listCount.get(0).get("count").toString());
				//totalPages = Math.round(totalRecords / requestData.getPaginationSize());
				totalPages = (totalRecords / requestData.getPaginationSize());				
				if( (totalRecords % requestData.getPaginationSize()) != 0) {
					totalPages++;
				}				
				//log.info("totalpages -{}",totalPages);
			}
		/*	buffer.append("SELECT d.id AS id,s.type AS type,d.bank_name AS bankName,d.status, d.created_date as createdDate FROM bank_name d "
					+ " JOIN bank_type s ON d.bank_type_id =s.id And s.status =1 WHERE" + appendBankName + " "
					+ appendBankType + "" + createdDate + " ");*/
			
			
			buffer.append(""
					+ "select d.id as id,d.bank_id as bankId,d.bank_name as bankName,d.status,d.created_date as createdDate "
					+ "FROM bank_name d where d.status=1 AND" + appendBankName + createdDate + " ");
			
			
			if (requestData.getPageNo() != null && requestData.getPaginationSize() != null) {
				if (requestData.getPageNo() != 0) {
					pageSize = (requestData.getPageNo() * requestData.getPaginationSize());
				}
			}
			if (requestData.getSortField() != null) {
				if (requestData.getSortField().toUpperCase().equals("BANKNAME"))
					appendOrder = appendOrder + "d.bank_name ";

				else if (requestData.getSortField().toUpperCase().equals("CREATEDDATE"))
					appendOrder = appendOrder + "d.created_date";
				
				else if (requestData.getSortField().toUpperCase().equals("STATUS"))
					appendOrder = appendOrder + "d.status ";
			} else
				appendOrder = appendOrder + "d.bank_name ";
			if (requestData.getSortOrder() != null) {
				if (!requestData.getSortOrder().toUpperCase().startsWith("A"))
					appendOrder = appendOrder +  " DESC ";
			}
			buffer.append(appendOrder + " limit " + pageSize + "," + requestData.getPaginationSize() + "");
			//log.info("<=========== query " + buffer);
			totalListOfDataReport = jdbcTemplate.queryForList(buffer.toString());
			for (Map<String, Object> entity : totalListOfDataReport) {
				for (Map.Entry<String, Object> entry : entity.entrySet()) {
					if (entry.getKey().equals("id")) {
						entry.setValue(mapper.convertValue(entry.getValue(), UUID.class));
					}
				}
			}
			if (totalListOfDataReport != null && totalListOfDataReport.size() > 0) {
				response.setResponseContents(totalListOfDataReport);
				response.setNumberOfElements(requestData.getPaginationSize());
				response.setTotalRecords(Long.parseLong(totalRecords + ""));
				response.setTotalPages(totalPages);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Get Lazy List Details Fetched Successfully");
			} else {
				//log.warn("No Records Found");
				response.setMessage("No Records Found");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<----- BankNameDaoImpl.getLazyList() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			
		}
		//log.info(" <----- BankNameDaoImpl.getLazyList()  END -----> ");
		return response;
	}

	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- BankNameDaoImpl.delete() Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			BankNameMasterEntity bankName = bankNameRepository.getOne(id);
			if (bankName != null) {
				bankNameRepository.delete(bankName);
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- BankNameDaoImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg + " " + "to Delete BankName");
		}
		//log.info(" <-----  BankNameDaoImpl.delete() END -----> ");
		return response;
	}

	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- BankNameDaoImpl.softDelete() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			BankNameMasterEntity bankName = bankNameRepository.getOne(id);
			if (bankName != null) {
				bankName.setStatus(false);
				bankNameRepository.save(bankName);
				response.setMessage("Successfully Deleted");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- BankNameServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete BankName");
		}
		//log.info(" <----- BankNameDaoImpl.softDelete() Service END -----> ");
		return response;
	}

	/*public BaseDTO getByBanktypeId(UUID id) {
		log.info(" <----- BankNameDaoImpl.getByBanktypeId Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			BankTypeMasterEntity bankType = new BankTypeMasterEntity();
			bankType.setId(id);
			List<BankNameMasterEntity> bankName = bankNameRepository.getByBankTypeId(bankType);
			if (bankName.isEmpty()) {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				log.warn("No Record Found..");
			} else {
				response.setResponseContents(bankName);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				log.info("Successfully Fetched Bank Name Details");
			}
		} catch (Exception e) {
			log.error("<---- BankNameServiceImpl.getByBanktypeId() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		log.info(" <----- BankNameDaoImpl.getByBanktypeId Service END -----> ");
		return response;
	}

*/	private BankNameMasterEntity updatedValues(BankNameMasterEntity bankNameMaster, BankNameDTO bankNameDTO) {
		BankNameMasterEntity bankName = bankNameMaster;
		//bankName.setBankTypeId(bankNameDTO.getBankTypeId());
		bankName.setBankName(bankNameDTO.getBankName());
		bankName.setStatus(bankNameDTO.getStatus());
		return bankName;
	}

	private boolean validateBankName(String str1) {

		//BankNameMasterEntity entity = bankNameRepository.getByBankName(str1);
		BankNameMasterEntity entity = bankNameRepository.findByBankNameIgnoreCase(str1);
		if (entity == null) {
			log.info("Valid ------>");
			return true;
		} else {
			log.warn("Invalid ------>");
			return false;
		}
	}
	


}
