package com.oasys.uppcl_user__master_management.dao.impl;



import java.util.ArrayList;
import java.util.Date;
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
import com.oasys.clients.userservice.utils.CommonUtil;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.ServiceDao;
import com.oasys.uppcl_user__master_management.dto.ListDto;
import com.oasys.uppcl_user__master_management.dto.SearchDTO;
import com.oasys.uppcl_user__master_management.dto.SearchFiledsDto;
import com.oasys.uppcl_user__master_management.dto.ServiceDTO;
import com.oasys.uppcl_user__master_management.entity.ReportQueriesEntity;
import com.oasys.uppcl_user__master_management.entity.SearchFieldsEntity;
import com.oasys.uppcl_user__master_management.entity.ServiceMasterEntity;
import com.oasys.uppcl_user__master_management.repository.ReportQueriesRepository;
import com.oasys.uppcl_user__master_management.repository.SearchFieldsRepository;
import com.oasys.uppcl_user__master_management.repository.ServiceRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class ServiceDaoImpl implements ServiceDao {

	@Autowired
	ServiceRepository serviceRepository;

	@Autowired
	ReportQueriesRepository reportQueriesRepository;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	MessageSource messageSource;
	
	@Autowired
	SearchFieldsRepository searchFieldsRepository;
	
	

	@Autowired
	CommonUtil commonUtil;
	
	@Override
	public BaseDTO create(ServiceDTO serviceDTO) {
		//log.info(" <----- Service create Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		String message = "";
		try {
			ServiceMasterEntity name = serviceRepository.getServiceName(serviceDTO.getServiceName().toUpperCase());
			if (name == null) {
				// if (validateServiceNumber(serviceDTO.getServiceNumber())) {
				//log.info("service Number - {} Is Valid..", serviceDTO.getServiceNumber());
				ServiceMasterEntity number = serviceRepository.getServiceNumber(serviceDTO.getServiceNumber());
				if (number == null) {
					ServiceMasterEntity service = objectMapper.convertValue(serviceDTO, ServiceMasterEntity.class);				
					service=serviceRepository.save(service);
					message = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(message);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Added");
				} else {

					message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
					response.setMessage("This Service Number " + message);
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.warn("Service Number is already exists..");
				}
				// } else {
				// response.setMessage("Service Number is Incorrect..");
				// response.setStatusCode(ErrorDescription.NOT_ACCEPTABLE.getCode());
				// log.warn("Service Number is Incorrect..");
				// }
			} else {
				message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
				response.setMessage("Service Name " + message);
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				//log.warn("Service Name is already exists..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- ServiceDaoImpl.create() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		} catch (Exception e) {
			log.error("<---- ServiceDaoImpl.create() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}
		//log.info(" <----- Service create Dao END -----> ");
		return response;
	}

	public BaseDTO update(UUID id, ServiceDTO serviceDTO) {
		//log.info(" <----- Service updateService Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		String message = "";
		try {
			boolean validateServiceName = false, validateServiceNumber = false;
			Optional<ServiceMasterEntity> service = serviceRepository.findById(id);
			if (!service.isPresent()) {
				message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("No Record Found..");
				return response;
			}

			//log.info("service Id {} ", id);
			ServiceMasterEntity beforeUpdate = updatedValues(service.get(), serviceDTO);
			List<ServiceMasterEntity> except = serviceRepository.getByExceptId(id);
			for (ServiceMasterEntity serviceMaster : except) {
				if (serviceMaster.getId() != beforeUpdate.getId()) {
					if (serviceMaster.getServiceName().equalsIgnoreCase(beforeUpdate.getServiceName())) {
						validateServiceName = true;
						break;
					} else if (serviceMaster.getServiceNumber().equalsIgnoreCase(beforeUpdate.getServiceNumber())) {
						validateServiceNumber = true;
						break;
					} else {
						validateServiceName = false;
						validateServiceNumber = false;
					}
				} else {
					validateServiceName = false;
					validateServiceNumber = false;
				}
			}
			if (validateServiceName == false) {
				if (!validateServiceNumber) {
					beforeUpdate = serviceRepository.save(beforeUpdate);
					message = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage("Service Updated Successfully");
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					log.info("Successfully Updated");
				}
				else {
					message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
					response.setMessage("This Service Number Already Exists");
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.warn("Service Number - {}  is Already Exists..");
				}
			} else {
				message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
				response.setMessage("Service Name Already Exists");
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				//log.warn("Service Name - {}  is Already Exists..");

			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- ServiceDaoImpl.update() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		} catch (Exception e) {
			log.error("<---- ServiceDaoImpl.update() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}
		//log.info(" <----- Service updateService Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- Service getById Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			Optional<ServiceMasterEntity> service = serviceRepository.findById(id);
			if (service.isPresent()) {
				//log.info("service Id {} ", id);
				response.setResponseContent(service);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get Service Details..");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- ServiceDaoImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Service getById Dao END -----> ");
		return response;
	}

	
	public BaseDTO getAll() {
		//log.info(" <----- Service getAll Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<ServiceMasterEntity> service = serviceRepository.findAll();

			if (service.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setResponseContents(service);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				//log.info("service Get all Details fetched successfully");
			}
		} catch (Exception e) {
			log.error("<----- ServiceDaoImpl.getAll() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Service getAll Dao END -----> ");
		return response;
	}

	public BaseDTO getAllActive() {
		//log.info(" <----- Service getAllActive Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		List<ServiceMasterEntity> contentList = new ArrayList<ServiceMasterEntity>();
		try {

			contentList = serviceRepository.getAllActive();
			if (contentList.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,
						Locale.US);
				response.setMessage(msg);
				response.setResponseContents(contentList);
				//log.info("service Get all  active Details fetched successfully");
			}
		} catch (Exception e) {
			log.error("<----- ServiceDaoImpl.getAllActive() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,
					Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Service getAllActive Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- Service getLazyList Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		int pageSize = 0;
		int totalRecords = 0;
		int totalPages = 0;
		List<Map<String, Object>> totalListOfDataReport = null;
		List<Map<String, Object>> listCount = null;
		try {
			ReportQueriesEntity reportQuery = reportQueriesRepository.getReportbyName("Service_search_pagination");
			if (reportQuery == null) {
				//log.error("Service_search_pagination  Not Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
			ReportQueriesEntity reportQueryCount = reportQueriesRepository
					.getReportbyName("Service_search_pagination_count");
			if (reportQueryCount == null) {
				//log.error("Service_search_pagination_count  Not Found..");
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
				totalPages = (totalRecords / requestData.getPaginationSize());
				if ((totalRecords % requestData.getPaginationSize()) != 0) {
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
				if (requestData.getSortField().toUpperCase().equals("SERVICENAME"))
					query = query.replace(":shortField", "service_name");
				else if (requestData.getSortField().toUpperCase().equals("SERVICETYPE"))
					query = query.replace(":shortField", "service_type");
				else if (requestData.getSortField().toUpperCase().equals("BASEURL"))
					query = query.replace(":shortField", "base_url");
				else if (requestData.getSortField().toUpperCase().equals("SERVICENUMBER"))
					query = query.replace(":shortField", "service_number");
				else if (requestData.getSortField().toUpperCase().equals("CREATEDDATE"))
					query = query.replace(":shortField", "ss.created_date");

				else if (requestData.getSortField().toUpperCase().equals("STATUS"))
					query = query.replace(":shortField", "status");
			} else
				query = query.replace(":shortField", "service_name");

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
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("successfully Get Details");
			} else {
				//log.warn("No Records Found");
				response.setMessage("No Records Found");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}

		} catch (Exception e) {
			log.error("<----- ServiceDaoImpl.getLazyList() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Service getLazyList Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- Service delete Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			ServiceMasterEntity service = serviceRepository.getOne(id);
			if (service != null) {
				serviceRepository.delete(service);
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,
						Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- ServiceDaoImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Service delete Dao END -----> ");
		return response;
	}

	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- Service softDelete Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			ServiceMasterEntity service = serviceRepository.getOne(id);
			if (service != null) {
				service.setStatus(false);
				serviceRepository.save(service);
				response.setMessage("Successfully Deleted");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- ServiceServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Service");
		}
		//log.info(" <----- Service softDelete Service END -----> ");
		return response;
	}

	private ServiceMasterEntity updatedValues(ServiceMasterEntity serviceMaster, ServiceDTO serviceDTO) {
		ServiceMasterEntity service = serviceMaster;
		service.setServiceName(serviceDTO.getServiceName());
		service.setServiceNumber(serviceDTO.getServiceNumber());
		service.setServiceTypeId(serviceDTO.getServiceTypeId());
		service.setBaseURL(serviceDTO.getBaseURL());
		service.setStatus(serviceDTO.getStatus());
		service.setServiceMode(serviceDTO.getServiceMode());
		return service;
	}

	// private static boolean validateServiceNumber(String str) {
	// Pattern pattern = Pattern.compile("^[A-Z]{2}[0-9]{5}$");
	// Matcher matcher = pattern.matcher(str);
	// if (matcher.matches()) {
	// log.info(" Working Fine -------> ");
	// return true;
	// } else {
	// log.error(" Not Working --------> ");
	// return false;
	// }
	// }

	@Override
	public BaseDTO getList(ListDto ids) {
		//log.info(" <----- Service softDelete Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<ServiceDTO> list = new ArrayList<ServiceDTO>();
			for (UUID uuid : ids.getIds()) {
				ServiceMasterEntity s = serviceRepository.findById(uuid).get();
				ServiceDTO sdto = new ServiceDTO();
				sdto.setId(s.getId());
				sdto.setServiceName(s.getServiceName());
				sdto.setBaseURL(s.getBaseURL());
				list.add(sdto);
			}
			if (list.isEmpty()) {
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				response.setMessage("No Record Found");
				response.setResponseContents(list);
			} else {
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setResponseContents(list);
				//log.info("Successfully get Details");
			}
		} catch (Exception e) {
			log.error("<---- ServiceServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Service");
		}
		//log.info(" <----- Service softDelete Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAllActiveWithSearch(SearchDTO searchDTO) {
		// log.info(" <----- Service getAllActive Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		List<ServiceMasterEntity> contentList = new ArrayList<ServiceMasterEntity>();
		try {

			if (searchDTO.getSearch() == null) {
				contentList = serviceRepository.getAllActive();
			} else {
				contentList = serviceRepository.getAllActiveWithSearch(searchDTO.getSearch());
			}

			if (contentList.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				// log.warn("No Record Found..");
			} else {
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,
						Locale.US);
				response.setMessage(msg);
				response.setResponseContents(contentList);
				// log.info("service Get all active Details fetched successfully");
			}
		} catch (Exception e) {
			log.error("<----- ServiceDaoImpl.getAllActive() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,
					Locale.US);
			response.setMessage(msg);
		}
		// log.info(" <----- Service getAllActive Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO createSearchFields(SearchFiledsDto searchFiledsDto) {
		// log.info(" <----- Service create Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		String message = "";
		try {
			SearchFieldsEntity searchFieldsEntity = new SearchFieldsEntity();
			searchFieldsEntity.setName(searchFiledsDto.getName());
			searchFieldsEntity.setServiceId(searchFiledsDto.getServiceId());
			searchFieldsEntity.setShowOrder(searchFiledsDto.getShowOrder());
			searchFieldsEntity.setCreatedDate(new Date());
			searchFieldsEntity.setModifiedDate(new Date());
			searchFieldsRepository.save(searchFieldsEntity);
			
			response.setMessage("Success");
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			
		} catch (DataIntegrityViolationException e) {
			log.error("<---- ServiceDaoImpl.create() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		} catch (Exception e) {
			log.error("<---- ServiceDaoImpl.create() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}
		// log.info(" <----- Service create Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO getSearchFields(UUID id) {
		BaseDTO response = new BaseDTO();
		try {
			List<SearchFieldsEntity> searchFieldsEntity = searchFieldsRepository.getByServiceId(id);
			if (!searchFieldsEntity.isEmpty()) {
				response.setMessage("Sucessfully get the list");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				response.setResponseContents(searchFieldsEntity);

			} else {
				response.setMessage("Empty data..");
				response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());

			}

		} catch (Exception e) {
			log.error("<---- ServiceDaoImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		return response;
	}

	@Override
	public BaseDTO getServiceList() {
		// log.info(" <----- Service getAllActive Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		List<ServiceMasterEntity> contentList = new ArrayList<ServiceMasterEntity>();
		List<ServiceDTO> serviceDTOList = new ArrayList<>();
		try {

			contentList = serviceRepository.getAllActive();
			if (contentList.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				// log.warn("No Record Found..");
			} else {
				for (ServiceMasterEntity serviceMasterEntity : contentList) {
					ServiceDTO serviceDTO = new ServiceDTO();
					serviceDTO.setId(serviceMasterEntity.getId());
					serviceDTO.setServiceName(serviceMasterEntity.getServiceName());
					serviceDTOList.add(serviceDTO);
				}

				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,
						Locale.US);
				response.setMessage(msg);
				response.setResponseContents(serviceDTOList);
				// log.info("service Get all active Details fetched successfully");
			}
		} catch (Exception e) {
			log.error("<----- ServiceDaoImpl.getAllActive() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,
					Locale.US);
			response.setMessage(msg);
		}
		// log.info(" <----- Service getAllActive Dao END -----> ");
		return response;
	}

}
