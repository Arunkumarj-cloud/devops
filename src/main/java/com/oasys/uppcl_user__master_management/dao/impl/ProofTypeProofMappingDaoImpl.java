package com.oasys.uppcl_user__master_management.dao.impl;

import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import com.oasys.uppcl_user__master_management.dao.ProofTypeProofMappingDao;
import com.oasys.uppcl_user__master_management.dto.ProofTypeProofMappingDTO;
import com.oasys.uppcl_user__master_management.entity.ProofTypeMasterEntity;
import com.oasys.uppcl_user__master_management.entity.ProofTypeProofMappingEntity;
import com.oasys.uppcl_user__master_management.entity.ReportQueriesEntity;
import com.oasys.uppcl_user__master_management.repository.ProofTypeProofMappingRepository;
import com.oasys.uppcl_user__master_management.repository.ReportQueriesRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class ProofTypeProofMappingDaoImpl  implements ProofTypeProofMappingDao{
	@Autowired
	ProofTypeProofMappingRepository proofTypeProofMappingRepository;

	@Autowired
	ReportQueriesRepository reportQueriesRepository;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	MessageSource messageSource;
	
	
	public BaseDTO create(ProofTypeProofMappingDTO proofTypeProofMappingDTO) {
		//log.info(" <----- ProofTypeProofMapping create Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			ProofTypeProofMappingEntity proofTypeProofMapping = objectMapper.convertValue(proofTypeProofMappingDTO,
					ProofTypeProofMappingEntity.class);
			if (proofTypeProofMapping.getProofId() != null) {
				if (proofTypeProofMapping.getProofTypeId() != null) {
					if (validate(proofTypeProofMappingDTO) == false) {
						if (proofTypeProofMapping.getStatus()) {
							proofTypeProofMappingRepository.save(proofTypeProofMapping);
							String msg = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
							baseDTO.setMessage(msg);
							baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
							//log.info("Successfully Added");
						} else {
							baseDTO.setMessage("Status is Empty..");
							baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
							//log.warn("Status is Empty..");
						}
					} else {
						baseDTO.setMessage("This Proof name is already Mapped..");
						baseDTO.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
						//log.warn("Proof Name is already Mapped..");
					}
				} else {
					baseDTO.setMessage("Proof type Name is Empty..");
					baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
					//log.warn("Proof type Name is Empty..");
				}
			} else {
				baseDTO.setMessage("Proof Name is Empty..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("Proof type Name is Empty..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- ProofTypeProofMappingDaoImpl.create() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		} catch (Exception e) {
			log.error("<---- ProofTypeProofMappingDaoImpl.create() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- ProofTypeProofMapping create Dao END -----> ");
		return baseDTO;
	}

	private Boolean validate(ProofTypeProofMappingDTO proofTypeProofMappingDTO) {
		boolean check = false;
		ProofTypeMasterEntity proofType = new ProofTypeMasterEntity();
		proofType.setId(proofTypeProofMappingDTO.getProofTypeId().getId());
		List<ProofTypeProofMappingEntity> list = proofTypeProofMappingRepository.findByProofTypeId(proofType);
		for (ProofTypeProofMappingEntity listDTO : list) {
			if (listDTO.getProofId().getId().toString()
					.contentEquals(proofTypeProofMappingDTO.getProofId().getId().toString())) {
				check = true;
				break;
			} else {
				check = false;
			}
		}
		return check;
	}
	
	public BaseDTO update(UUID id, ProofTypeProofMappingDTO proofTypeProofMappingDTO) {
		//log.info(" <----- proofTypeProofMapping updateproofTypeProofMapping Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			ProofTypeProofMappingEntity proofTypeProofMapping = proofTypeProofMappingRepository.getOne(id);
			if (proofTypeProofMapping != null) {
				ProofTypeProofMappingEntity beforeUpdate = updatedValues(proofTypeProofMapping,
						proofTypeProofMappingDTO);
				if (beforeUpdate.getProofTypeId() != null) {
					if (beforeUpdate.getProofId() != null) {
						if (validate(proofTypeProofMappingDTO) == false) {
							if (beforeUpdate.getStatus()) {
								ProofTypeProofMappingEntity afterUpdate = proofTypeProofMappingRepository
										.save(beforeUpdate);
								response.setResponseContent(afterUpdate);
								String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
								response.setMessage(msg);
								response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
								//log.info("Successfully Updated");
							} else {
								response.setMessage("Status is Empty..");
								response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
								//log.warn("Status is Empty..");
							}
						} else {
							response.setMessage("This Proof Name is already Mapped..");
							response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
							//log.warn("Proof Name is already Mapped..");
						}
					} else {
						response.setMessage("Proof Name is Empty..");
						response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
						//log.warn("Proof Name is Empty..");
					}
				} else {
					response.setMessage("Proof Type Name is Empty..");
					response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
					//log.warn("Proof Type Name is Empty..");
				}
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("No Record Found..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- ProofTypeProofMappingDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		} catch (Exception e) {
			log.error("<---- ProofTypeProofMappingDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info(" <----- proofTypeProofMapping updateproofTypeProofMapping Dao END -----> ");
		return response;
	}
	

	private ProofTypeProofMappingEntity updatedValues(ProofTypeProofMappingEntity proofTypeProofMappingEntity,
			ProofTypeProofMappingDTO proofTypeProofMappingDTO) {
		ProofTypeProofMappingEntity proofTypeProofMapping = proofTypeProofMappingEntity;
		proofTypeProofMapping.setProofId(proofTypeProofMappingDTO.getProofId());
		proofTypeProofMapping.setProofTypeId(proofTypeProofMappingDTO.getProofTypeId());
		proofTypeProofMapping.setStatus(proofTypeProofMappingDTO.getStatus());
		return proofTypeProofMapping;
	}
	
	public BaseDTO getById(UUID id) {
		//log.info(" <----- ProofTypeProofMapping getById Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			ProofTypeProofMappingEntity proofTypeProofMapping = proofTypeProofMappingRepository.getOne(id);
			if (proofTypeProofMapping != null) {
				baseDTO.setResponseContent(proofTypeProofMapping);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get ProofTypeProofMapping");
			} else {
				baseDTO.setMessage("No Record Found..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- ProofTypeProofMappingDaoImpl.getById() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- ProofTypeProofMapping getById Dao END -----> ");
		return baseDTO;
	}
	
	public BaseDTO getAll() {
		//log.info(" <----- ProofTypeProofMapping getAll Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			List<ProofTypeProofMappingEntity> proofTypeProofMapping = proofTypeProofMappingRepository.findAll();
			if (proofTypeProofMapping.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				baseDTO.setResponseContents(proofTypeProofMapping);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				baseDTO.setMessage(msg);
				//log.info("Successfully get all details");
			}
		} catch (Exception e) {
			log.error("<----- ProofTypeProofMappingDaoImpl.getAll() ----->", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- ProofTypeProofMapping getAll Dao END -----> ");
		return baseDTO;
	}
	
	public BaseDTO getAllActive() {
		//log.info(" <----- ProofTypeProofMapping getAllActive Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			List<ProofTypeProofMappingEntity> proofTypeProofMapping = proofTypeProofMappingRepository.getAllActive();
			if (proofTypeProofMapping.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				baseDTO.setResponseContents(proofTypeProofMapping);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				baseDTO.setMessage(msg);
				//log.info("Successfully Get all active details");
			}
		} catch (Exception e) {
			log.error("<----- ProofTypeProofMappingDaoImpl.getAll() ----->", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- ProofTypeProofMapping getAllActive Dao END -----> ");
		return baseDTO;
	}

	
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- ProofTypeProofMapping getLazyList Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		int pageSize = 0;
		int totalRecords = 0;
		int totalPages = 0;
		List<Map<String, Object>> totalListOfDataReport = null;
		List<Map<String, Object>> listCount = null;
		try {
			ReportQueriesEntity reportQuery = reportQueriesRepository
					.getReportbyName("ProofType_ProofMapping_search_pagination");
			if (reportQuery == null) {
				//log.error("ProofType_ProofMapping_search_pagination  Not Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
			ReportQueriesEntity reportQueryCount = reportQueriesRepository
					.getReportbyName("ProofType_ProofMapping_search_pagination_count");
			if (reportQueryCount == null) {
				//log.warn("ProofType_ProofMapping_search_pagination_count  Not Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
			String query = reportQuery.getDataQuery();
			String queryCount = reportQueryCount.getDataQuery();
			if (requestData.getSearch() != null) {
				query = query.replace(":search", requestData.getSearch());
				queryCount = queryCount.replace(":search", requestData.getSearch());
			} else {
				query = query.replace(":search", "");
				queryCount = queryCount.replace(":search", "");
			}
			listCount = jdbcTemplate.queryForList(queryCount);
			if (listCount.size() > 0 && listCount.get(0).get("count") != null) {
				totalRecords = Integer.parseInt(listCount.get(0).get("count").toString());
				totalPages = Math.round(totalRecords / requestData.getPaginationSize());
			}
			if (requestData.getPageNo() != null && requestData.getPaginationSize() != null) {
				if (requestData.getPageNo() != 0) {
					pageSize = (requestData.getPageNo() * requestData.getPaginationSize());
				}
			}
			if (requestData.getSortField() != null) {
				if (requestData.getSortField().toUpperCase().equals("PROOFTYPE"))
					query = query.replace(":shortField", "proof_type_name");
				else if (requestData.getSortField().toUpperCase().equals("PROOF"))
					query = query.replace(":shortField", "proof");
				else if (requestData.getSortField().toUpperCase().equals("STATUS"))
					query = query.replace(":shortField", "status");
			} else
				query = query.replace(":shortField", "proof_type_name");

			if (requestData.getSortOrder() != null) {
				if (requestData.getSortOrder().toUpperCase().startsWith("A"))
					query = query.replace(":shortOrder", "ASC");
				else
					query = query.replace(":shortOrder", "DESC");

			}

			query = query.replace(":limit", +pageSize + "," + requestData.getPaginationSize());
			//log.info("<=========== query " + query);
			totalListOfDataReport = jdbcTemplate.queryForList(query);
			for (Map<String, Object> entity : totalListOfDataReport) {
				for (Map.Entry<String, Object> entry : entity.entrySet()) {
					if (entry.getKey().equals("id")) {
						entry.setValue(objectMapper.convertValue(entry.getValue(), UUID.class));
					}
				}
			}

			if (totalListOfDataReport != null && totalListOfDataReport.size() > 0) {
				response.setResponseContents(totalListOfDataReport);
				response.setNumberOfElements(requestData.getPaginationSize());
				response.setTotalRecords(Long.parseLong(totalRecords + ""));
				response.setTotalPages(totalPages);
				//response.setMessage("Successfully Get All ProofType Proof Details..");
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully get details");
			} else {
				//log.warn("No Records Found");
				response.setMessage("No Records Found");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}

		} catch (Exception e) {
			log.error("<----- ProofTypeProofMappingDaoImpl.getLazyList() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Get LazyList for ProofTypeProofMapping");
		}
		//log.info(" <----- ProofTypeProofMapping getLazyList Dao END -----> ");
		return response;
	}

	
	public BaseDTO delete(UUID id) {
		//log.info(" <----- ProofTypeProofMapping delete Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			ProofTypeProofMappingEntity proofTypeProofMapping = proofTypeProofMappingRepository.getOne(id);
			if (proofTypeProofMapping != null) {
				proofTypeProofMappingRepository.delete(proofTypeProofMapping);
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted");
			} else {
				baseDTO.setMessage("No Record Found..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- ProofTypeProofMappingDaoImpl.delete() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- ProofTypeProofMapping delete Dao END -----> ");
		return baseDTO;
	}

	public BaseDTO softDelete(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public BaseDTO getByProofTypeId(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}
}
