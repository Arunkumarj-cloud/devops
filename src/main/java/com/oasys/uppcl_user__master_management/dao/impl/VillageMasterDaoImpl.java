package com.oasys.uppcl_user__master_management.dao.impl;


import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.VillageMasterDAO;
import com.oasys.uppcl_user__master_management.entity.ReportQueriesEntity;
import com.oasys.uppcl_user__master_management.entity.VillageMasterEntity;
import com.oasys.uppcl_user__master_management.repository.GramPanchayatRepository;
import com.oasys.uppcl_user__master_management.repository.ReportQueriesRepository;
import com.oasys.uppcl_user__master_management.repository.VillageMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class VillageMasterDaoImpl implements VillageMasterDAO {

	@Autowired
	VillageMasterRepository villageMasterRepository;

	@Autowired
	ReportQueriesRepository reportQueriesRepository;

	@Autowired
	GramPanchayatRepository gramPanchayatRepository;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	MessageSource messageSource;

	@Override
	public VillageMasterEntity save(VillageMasterEntity villageMasterEntity) {
		//log.info(" <----- Village save Dao STARTED -----> {} ",villageMasterEntity);
		VillageMasterEntity villageMaster = villageMasterRepository.save(villageMasterEntity);
		//log.info(" <----- Village save Dao END -----> ");
		return villageMaster;
	}
	@Override
	public VillageMasterEntity check(String villageName, String code, UUID id) {
		//log.info(" <----- Village findByVillageName Dao STARTED -----> {} ",villageName);
		VillageMasterEntity villageMaster = villageMasterRepository.findByTalukIdandVillagenameandCode(id,villageName,code);
		//log.info(" <----- Village findByVillageName Dao END -----> ");
		return villageMaster;
	}

	@Override
	public BaseDTO getAll() {
		//log.info(" <----- Village getAll Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			List<VillageMasterEntity> villageName = villageMasterRepository.findAll();
			if (villageName != null) {
				baseDTO.setResponseContents(villageName);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				baseDTO.setMessage(msg);
				//log.info("Village Master Get All details Fetched Successfully");
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<----- VillageMasterDAOImpl.getAll() ----->", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- villageMaster getAll Dao END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO getAllActive() {
		//log.info(" <----- Village Master getAllActive Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			List<VillageMasterEntity> villageMaster = villageMasterRepository.getAllActive();

			if (villageMaster.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				baseDTO.setResponseContents(villageMaster);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				baseDTO.setMessage(msg);
				//log.info("Village Master Get All Active details Fetched Successfully");
			}

		} catch (Exception e) {
			log.error("<----- VillageMasterDaoImpl.getAllActive() ----->", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}
		///log.info(" <----- Village getAllActive Dao END -----> ");
		return baseDTO;
	}

	@Override
	public Optional<VillageMasterEntity> getById(UUID id) {
		//log.info(" <----- Village getById Dao STARTED -----> {} ",id);
		Optional<VillageMasterEntity> villageMaster= villageMasterRepository.findById(id);
		//log.info(" <----- Village getById Dao END -----> ");
		return villageMaster;
	}

	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- Village delete Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			VillageMasterEntity villageName = villageMasterRepository.getOne(id);
			if (villageName != null) {
				villageMasterRepository.delete(villageName);
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
			log.error("<---- VillageMasterDaoImpl.delete() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- villageName delete Dao END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- villageName getLazyList Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		int pageSize = 0;
		int totalRecords = 0;
		int totalPages = 0;
		List<Map<String, Object>> totalListOfDataReport = null;
		List<Map<String, Object>> listCount = null;
		try {
			ReportQueriesEntity reportQuery = reportQueriesRepository.getReportbyName("Village_search_pagination");
			if (reportQuery == null) {
				//log.error("Village_search_pagination  Not Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
			ReportQueriesEntity reportQueryCount = reportQueriesRepository
					.getReportbyName("Village_search_pagination_count");
			if (reportQueryCount == null) {
				//log.warn("Village_search_pagination_count  Not Found..");
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
				//totalPages = Math.round(totalRecords / requestData.getPaginationSize());
				totalPages = (totalRecords / requestData.getPaginationSize());				
								if( (totalRecords % requestData.getPaginationSize()) != 0) {
									totalPages++;
								}				
				             log.info("totalpages -{}",totalPages);
			}
			if (requestData.getPageNo() != null && requestData.getPaginationSize() != null) {
				if (requestData.getPageNo() != 0) {
					pageSize = (requestData.getPageNo() * requestData.getPaginationSize());
				}
			}
			if (requestData.getSortField() != null) {
				if (requestData.getSortField().toUpperCase().equals("VILLAGENAME"))
					query = query.replace(":shortField", "village_name");
				else if (requestData.getSortField().toUpperCase().equals("CODE"))
					query = query.replace(":shortField", "code");
				else if (requestData.getSortField().toUpperCase().equals("CREATEDDATE"))
					query = query.replace(":shortField", "block_name");
				else if (requestData.getSortField().toUpperCase().equals("DISTRICTNAME"))
					query = query.replace(":shortField", "district_name");
				else if (requestData.getSortField().toUpperCase().equals("STATENAME"))
					query = query.replace(":shortField", "state_name");

				
				else if (requestData.getSortField().toUpperCase().equals("TALUKNAME"))
					query = query.replace(":shortField", "taluk_name");
				else if (requestData.getSortField().toUpperCase().equals("STATUS"))
					query = query.replace(":shortField", "status");
			} else
				query = query.replace(":shortField", "village_name");

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
					if(entry.getKey().equals("talukId")) {
						entry.setValue(objectMapper.convertValue(entry.getValue(), UUID.class));
					}
					if(entry.getKey().equals("districtId")) {
						entry.setValue(objectMapper.convertValue(entry.getValue(), UUID.class));
					}
					if(entry.getKey().equals("stateId")) {
						entry.setValue(objectMapper.convertValue(entry.getValue(), UUID.class));
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
				//log.info("successfully Get Lazy List details");
			} else {
				//log.error("No Records Found");
				response.setMessage("No Records Found");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}

		} catch (Exception e) {
			log.error("<----- VillageMasterDaoImpl.getLazyList() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- villageName getLazyList Dao END -----> ");
		return response;
	}

	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- villageName softDelete Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			VillageMasterEntity villageName = villageMasterRepository.getOne(id);
			if (villageName != null) {
				villageName.setStatus(false);
				villageMasterRepository.save(villageName);
				baseDTO.setMessage("Successfully Deleted");
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted");
			} else {
				baseDTO.setMessage("No Record Found..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- VillageMasterServiceImpl.softDelete() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			baseDTO.setMessage("Unable to Delete villageName");
		}
		////log.info(" <----- villageName softDelete Service END -----> ");
		return baseDTO;
	}

	
	@Override
	public BaseDTO getByTalukId(UUID id) {
		//log.info(" <----- VillageMasterDaoImpl.getByTalukId() Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			List<VillageMasterEntity> villageMasterEntity = villageMasterRepository.findByTalukId(id);
			if (!villageMasterEntity.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				baseDTO.setResponseContents(villageMasterEntity);
				//log.info("Successfully Fetched");
			} else {
				baseDTO.setMessage("No Record Found..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- VillageMasterDaoImpl.getByTalukId() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- VillageMasterDaoImpl.getByTalukId() Dao END -----> ");
		return baseDTO;
	}
	@Override
	public List<VillageMasterEntity> checkCodeUpdate(String code, UUID id) {
		// TODO Auto-generated method stub
		return null;
	}
	

 }

