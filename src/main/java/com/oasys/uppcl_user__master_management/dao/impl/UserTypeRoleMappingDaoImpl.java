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
import com.oasys.uppcl_user__master_management.dao.UserTypeRoleMappingDao;
import com.oasys.uppcl_user__master_management.dto.UserTypeRoleMappingDTO;
import com.oasys.uppcl_user__master_management.entity.ReportQueriesEntity;
import com.oasys.uppcl_user__master_management.entity.UserTypeMasterEntity;
import com.oasys.uppcl_user__master_management.entity.UserTypeRoleMappingMaster;
import com.oasys.uppcl_user__master_management.repository.ReportQueriesRepository;
import com.oasys.uppcl_user__master_management.repository.UserTypeRoleMappingRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;
@Repository
@Log4j2
public class UserTypeRoleMappingDaoImpl implements UserTypeRoleMappingDao {

	@Autowired
	UserTypeRoleMappingRepository userTypeRoleMappingRepository;

	@Autowired
	ReportQueriesRepository reportQueriesRepository;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	MessageSource messageSource;
	
	@Override
	public BaseDTO create(UserTypeRoleMappingDTO userTypeRoleMappingDTO) {
		//log.info(" <----- UserTypeRoleMapping create Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			UserTypeRoleMappingMaster userTypeRoleMapping = objectMapper.convertValue(userTypeRoleMappingDTO,
					UserTypeRoleMappingMaster.class);
			if (validate(userTypeRoleMappingDTO) == false) {
				userTypeRoleMappingRepository.save(userTypeRoleMapping);
				String msg = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage("User Type Role Successfully Created");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info(" User Type Role Added Successfully");
			} else {
				response.setMessage("This User Type Already Mapped with Role Name");
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				//log.error("Role Name is Already Mapped..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- UserTypeRoleMappingDaoImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		} catch (Exception e) {
			log.error("<---- UserTypeRoleMappingDaoImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- UserTypeRoleMapping create Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO update(UUID id, UserTypeRoleMappingDTO userTypeRoleMappingDTO) {
		//log.info(" <----- UserTypeRoleMapping updateUserTypeRoleMapping Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			Optional<UserTypeRoleMappingMaster> userTypeRoleMapping = userTypeRoleMappingRepository.findById(id);
			if (userTypeRoleMapping.isPresent()) {
				UserTypeRoleMappingMaster beforeUpdate = updatedValues(userTypeRoleMapping.get(), userTypeRoleMappingDTO);
				if (validate(userTypeRoleMappingDTO) == false) {
					UserTypeRoleMappingMaster afterUpdate = userTypeRoleMappingRepository.save(beforeUpdate);
					//response.setResponseContent(afterUpdate);
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage("User Type Role Updated Successfully");
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info(" User Type Role Successfully Updated" );
				} else {
					response.setMessage("This User Type Already Mapped with Role Name");
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.error(" Role Name Already Mapped..");
				}
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("No Record Found..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- UserTypeRoleMappingDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		} catch (Exception e) {
			log.error("<---- UserTypeRoleMappingDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info(" <----- UserTypeRoleMapping updateUserTypeRoleMapping Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- UserTypeRoleMapping getById Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			UserTypeRoleMappingMaster userTypeRoleMapping = userTypeRoleMappingRepository.getOne(id);
			if (userTypeRoleMapping != null) {
				response.setResponseContent(userTypeRoleMapping);
				response.setMessage("Successfully Get UserTypeRoleMapping");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get UserTypeRoleMapping");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- UserTypeRoleMappingDaoImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Get UserTypeRoleMapping");
		}
		//log.info(" <----- UserTypeRoleMapping getById Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAll() {
		//log.info(" <----- UserTypeRoleMapping getAll Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<UserTypeRoleMappingMaster> userTypeRoleMapping = userTypeRoleMappingRepository.findAll();
			if (userTypeRoleMapping.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setResponseContents(userTypeRoleMapping);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				//log.info("User Type Role Mapping Get All Details Fetched Successfully");
			}
		} catch (Exception e) {
			log.error("<----- UserTypeRoleMappingDaoImpl.getAll() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- UserTypeRoleMapping getAll Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		//log.info(" <----- UserTypeRoleMapping getAllActive Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<UserTypeRoleMappingMaster> userTypeRoleMapping = userTypeRoleMappingRepository.getAllActive();
			if (userTypeRoleMapping.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setResponseContents(userTypeRoleMapping);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				//log.info("User Type Role Mapping Get All Active  Details Fetched Successfully");
			}
		} catch (Exception e) {
			log.error("<----- UserTypeRoleMappingDaoImpl.getAll() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- UserTypeRoleMapping getAllActive Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- UserTypeRoleMapping getLazyList Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		int pageSize = 0;
		int totalRecords = 0;
		int totalPages = 0;
		List<Map<String, Object>> totalListOfDataReport = null;
		List<Map<String, Object>> listCount = null;
		try {
			ReportQueriesEntity reportQuery = reportQueriesRepository
					.getReportbyName("UserType_RoleMapping_search_pagination");
			if (reportQuery == null) {
				//log.error("UserType_RoleMapping_search_pagination  Not Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
			ReportQueriesEntity reportQueryCount = reportQueriesRepository
					.getReportbyName("UserType_RoleMapping_search_pagination_count");
			if (reportQueryCount == null) {
				//log.warn("UserType_RoleMapping_search_pagination_count  Not Found..");
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
             //log.info("totalpages -{}",totalPages);
				
				
			}
			if (requestData.getPageNo() != null && requestData.getPaginationSize() != null) {
				if (requestData.getPageNo() != 0) {
					pageSize = (requestData.getPageNo() * requestData.getPaginationSize());
				}
			}
			if (requestData.getSortField() != null) {
				if (requestData.getSortField().toUpperCase().equals("USERTYPE"))
					query = query.replace(":shortField", "ut.user_type");
				else if (requestData.getSortField().toUpperCase().equals("ROLENAME"))
					query = query.replace(":shortField", "rm.role_name");
				else if (requestData.getSortField().toUpperCase().equals("STATUS"))
					query = query.replace(":shortField", "ut.status");
				else if (requestData.getSortField().toUpperCase().equals("CREATEDDATE"))
					query = query.replace(":shortField", "urm.created_date");
				
			} else
				query = query.replace(":shortField", "ut.user_type");

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
						entry.setValue(entry.getValue());
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
				//log.info(" Successfully Get  User Type Role Mapping Details");
			} else {
				//log.error("No Records Found");
				response.setMessage("No Records Found");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}

		} catch (Exception e) {
			log.error("<----- UserTypeRoleMappingDaoImpl.getLazyList() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- UserTypeRoleMapping getLazyList Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- UserTypeRoleMapping delete Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			UserTypeRoleMappingMaster userTypeRoleMapping = userTypeRoleMappingRepository.getOne(id);
			if (userTypeRoleMapping != null) {
				//log.info("User type Role Mapping Id {}",id);
				userTypeRoleMappingRepository.delete(userTypeRoleMapping);
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- UserTypeRoleMappingDaoImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- UserTypeRoleMapping delete Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- UserTypeRoleMapping softDelete Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			UserTypeRoleMappingMaster userTypeRoleMapping = userTypeRoleMappingRepository.getOne(id);
			if (userTypeRoleMapping != null) {
				userTypeRoleMapping.setStatus(false);
				userTypeRoleMappingRepository.save(userTypeRoleMapping);
				response.setMessage("Successfully Deleted");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- UserTypeRoleMappingDaoImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete UserTypeRoleMapping");
		}
		//log.info(" <----- UserTypeRoleMapping softDelete Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO getByUserTypeId(UUID id) {
		//log.info(" <----- UserTypeRoleMapping getByUserTypeId Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			UserTypeMasterEntity userType = new UserTypeMasterEntity();
			userType.setId(id);
			//log.info("User type Id {}",id);
			List<UserTypeRoleMappingMaster> list = userTypeRoleMappingRepository.findByUserTypeId(userType);
			if (!list.isEmpty()) {
				response.setResponseContents(list);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get UserTypeRoleMapping Details..");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- UserTypeRoleMappingDaoImpl.getByUserTypeId() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Get UserTypeRoleMapping");	
		}
		//log.info(" <----- UserTypeRoleMapping getByUserTypeId Dao END -----> ");
		return response;
	}

	private UserTypeRoleMappingMaster updatedValues(UserTypeRoleMappingMaster userTypeRoleMappingMaster,
			UserTypeRoleMappingDTO userTypeRoleMappingDTO) {
		UserTypeRoleMappingMaster userTypeRoleMapping = userTypeRoleMappingMaster;
		userTypeRoleMapping.setRoleId(userTypeRoleMappingDTO.getRoleId());
		userTypeRoleMapping.setUserTypeId(userTypeRoleMappingDTO.getUserTypeId());
		userTypeRoleMapping.setStatus(userTypeRoleMappingDTO.getStatus());
		userTypeRoleMapping.setRemarks(userTypeRoleMapping.getRemarks());
		return userTypeRoleMapping;
	}

	private Boolean validate(UserTypeRoleMappingDTO userTypeRoleMappingDTO) {
		boolean check = false;
//		UserTypeMasterEntity userType = new UserTypeMasterEntity();
//		userType.setId(userTypeRoleMappingDTO.getUserTypeId().getId());
//		List<UserTypeRoleMappingMaster> list = userTypeRoleMappingRepository.findUserTypeId(userType);
//		for (UserTypeRoleMappingMaster listDTO : list) {
//			if (listDTO.getRoleId().getId().toString()
//					.contentEquals(userTypeRoleMappingDTO.getRoleId().getId().toString())) {
//				check = true;
//				break;
//			} else {
//				check = false;
//			}
//		}
		Optional<UserTypeRoleMappingMaster> optional = userTypeRoleMappingRepository.check(userTypeRoleMappingDTO.getUserTypeId().getId(),userTypeRoleMappingDTO.getRoleId().getId());
		if(optional.isPresent()) {
			check = true;
		}
		return check;
	
	}
}
