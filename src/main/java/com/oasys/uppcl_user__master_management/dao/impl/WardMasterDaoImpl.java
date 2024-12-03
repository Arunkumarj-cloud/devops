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

import com.oasys.uppcl_user__master_management.dao.WardMasterDao;
import com.oasys.uppcl_user__master_management.entity.ReportQueriesEntity;
import com.oasys.uppcl_user__master_management.entity.WardMasterEntity;
import com.oasys.uppcl_user__master_management.repository.ReportQueriesRepository;
import com.oasys.uppcl_user__master_management.repository.WardMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class WardMasterDaoImpl implements WardMasterDao {

	@Autowired
	WardMasterRepository wardMasterRepository;

	@Autowired
	ReportQueriesRepository reportQueriesRepository;

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	MessageSource messageSource;

	@Override
	public WardMasterEntity save(WardMasterEntity wardMasterEntity) {
		log.info(" WardMasterDaoImpl.create() Started - {} ",wardMasterEntity);
		WardMasterEntity wardMaster = wardMasterRepository.save(wardMasterEntity);
		log.info(" WardMasterDaoImpl.create() Ended - {} ",wardMaster);
		return wardMaster;
	}
	@Override
	public WardMasterEntity findByWardName(String wardName) {
		log.info(" WardMasterDaoImpl.findByWardName() Started - {} ",wardName);
		WardMasterEntity wardMaster = wardMasterRepository.findByWardName(wardName);
		log.info(" WardMasterDaoImpl.findByWardName() Ended - {} ",wardMaster);
		return wardMaster;
	}
	@Override
	public Optional<WardMasterEntity> findById(UUID id) {
		log.info(" WardMasterDaoImpl.findById() Started - {} ",id);
		Optional<WardMasterEntity> wardMaster = wardMasterRepository.findById(id);
		log.info(" WardMasterDaoImpl.findById() Ended - {} ",wardMaster);
		return wardMaster;
	}
	@Override
	public List<WardMasterEntity> findByIdNotIn(UUID id) {
		log.info(" WardMasterDaoImpl.findById() Started - {} ",id);
		List<WardMasterEntity> wardMaster = wardMasterRepository.findByIdNotIn(id);
		log.info(" WardMasterDaoImpl.findById() Ended - {} ",wardMaster);
		return wardMaster;
	}

	@Override
	public BaseDTO delete(UUID id) {
		log.info("<==== WardMasterDaoImpl.delete() Started ====>");
		BaseDTO response = new BaseDTO();
		WardMasterEntity master = new WardMasterEntity();
		try {
			master = wardMasterRepository.getOne(id);
			if (master.getId() != null) {
				wardMasterRepository.delete(master);
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				log.info("Successfully Deleted");
			} else {
				log.error("ward ID does not exist");
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
		} catch (Exception e) {
			log.error("<---- WardMasterDaoImpl.delete()  exception---- {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		log.info("<====Ended WardMasterDaoImpl.delete() ===> ");
		return response;
	}

	@Override
	public BaseDTO getAll() {
		BaseDTO response = new BaseDTO();
		//log.info("<==== WardMasterDaoImpl.getAll() Started ====>");
		List<WardMasterEntity> wardtypeList = null;
		try {

			wardtypeList = wardMasterRepository.findAll();
			if (wardtypeList.size() != 0) {
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setResponseContents(wardtypeList);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info(" Ward Master Get all Details Fetched successfully");
			} else {
				response.setMessage("ward Type List Empty");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<== Exception WardMasterDaoImpl.getAll() - {} ", e);
		}
		//log.info("<==== WardMasterDaoImpl.getAll() Ended ====>");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		BaseDTO response = new BaseDTO();
		//log.info("<==== WardMasterDaoImpl.getAllActive() Started ====>");
		List<WardMasterEntity> wardtypeList = null;
		try {
			wardtypeList = wardMasterRepository.getAllactive();
			if (wardtypeList != null) {
				response.setResponseContents(wardtypeList);
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info(" Ward Master Get All  Active Details Fetched successfully");
			} else {
				response.setMessage("No ward Type is Active");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<------WardMasterDaoImpl.getAllActive()------> exception - {} ", e);
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<------WardMasterDaoImpl.getAllActive()------> Ended");
		return response;
	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info("<== WardMasterDaoImpl.getLazyList() Started ==>");
		BaseDTO response = new BaseDTO();
		int pageSize = 0;
		int totalRecords = 0;
		int totalPages = 0;
		List<Map<String, Object>> totalListOfDataReport = null;
		List<Map<String, Object>> listCount = null;
		try {
			ReportQueriesEntity reportQuery = reportQueriesRepository.getReportbyName("Ward_search_pagination");
			if (reportQuery == null) {
				//log.info("Ward_search_pagination  Not Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
			ReportQueriesEntity reportQueryCount = reportQueriesRepository
					.getReportbyName("Ward_search_pagination_count");
			if (reportQueryCount == null) {
				//log.info("Ward_search_pagination_count  Not Found..");
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
				if( (totalRecords % requestData.getPaginationSize()) != 0) {
					totalPages++;
				}				
				//log.info(totalPages);
			}
			if (requestData.getPageNo() != null && requestData.getPaginationSize() != null) {
				if (requestData.getPageNo() != 0) {
					pageSize = (requestData.getPageNo() * requestData.getPaginationSize());
				}
			}
			if (requestData.getSortField() != null) {
				if (requestData.getSortField().toUpperCase().equals("MUNICIPALITYNAME"))
					query = query.replace(":shortField", "municipality_name");
				else if (requestData.getSortField().toUpperCase().equals("WARDNAME"))
					query = query.replace(":shortField", "ward_name");
				else if (requestData.getSortField().toUpperCase().equals("DISTRICTNAME"))
					query = query.replace(":shortField", "district_name");
				else if (requestData.getSortField().toUpperCase().equals("STATENAME"))
					query = query.replace(":shortField", "state_name");

				else if (requestData.getSortField().toUpperCase().equals("STATUS"))
					query = query.replace(":shortField", "status");
			} else
				query = query.replace(":shortField", "ward_name");

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
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get Lazy List Details");
			} else {
				//log.error("No Records Found");
				response.setMessage("No Records Found");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error(" WardMasterDaoImpl.getLazyList() -- Exception - {} ", e);
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<== WardMasterDaoImpl.getLazyList() Ended ==>");
		return response;
	}

}
