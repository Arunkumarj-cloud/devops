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
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.CorporationDao;
import com.oasys.uppcl_user__master_management.dto.CorporationDTO;
import com.oasys.uppcl_user__master_management.entity.CorporationMasterEntity;
import com.oasys.uppcl_user__master_management.entity.DistrictMasterEntity;
import com.oasys.uppcl_user__master_management.entity.ReportQueriesEntity;
import com.oasys.uppcl_user__master_management.repository.CorporationRepository;
import com.oasys.uppcl_user__master_management.repository.DistrictMasterRepository;
import com.oasys.uppcl_user__master_management.repository.ReportQueriesRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CorporationDaoImpl implements CorporationDao {

	@Autowired
	CorporationRepository corporationRepository;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	ReportQueriesRepository reportQueriesRepository;

	@Autowired
	DistrictMasterRepository districtMasterRepository;

	@Autowired
	JdbcTemplate jdbcTemplate;

	final String DELETE_OK = "Y";
	
	@Autowired
	MessageSource messageSource;

	@Override
	public BaseDTO create(CorporationDTO corporationDTO) {
		//log.info(" <----- CorporationDaoImpl.create()  STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			if (null != corporationDTO) {
				List<CorporationMasterEntity> list = corporationRepository
						.checkCode(corporationDTO.getCorporationCode());
				if (list.isEmpty()) {
					CorporationMasterEntity corporationMasterEntity = new CorporationMasterEntity();
					if (corporationDTO.getCorporationCode() != "") {
						//log.info(" carporation Code {}  -Is Not Empty", corporationDTO.getCorporationCode());
						if (corporationDTO.getCorporationName() != "") {
							//log.info("carporation Name {}  -Is Not Empty", corporationDTO.getCorporationName());
							if (corporationDTO.getStatus() != null) {
								//log.info(" status {}  -Is Not Null", corporationDTO.getStatus());
								if (corporationDTO.getDistrictId() != null) {
									if (districtMasterRepository.findById(corporationDTO.getDistrictId().getId())
											.isPresent()) {
										corporationMasterEntity.setCorporationName(corporationDTO.getCorporationName());
										corporationMasterEntity.setCorporationCode(corporationDTO.getCorporationCode());
										corporationMasterEntity.setStatus(corporationDTO.getStatus());
										corporationMasterEntity.setDistrictId(corporationDTO.getDistrictId());
										corporationRepository.save(corporationMasterEntity);
										String msg = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
										response.setMessage(corporationDTO.getCorporationName() + " " + msg);
										response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
										//log.info("corporation - {} Created Successfully", corporationDTO.getCorporationCode(),corporationDTO.getCorporationName(),corporationDTO.getDistrictId().getId());
									} else {
										response.setMessage("District Not Found");
										response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
										//log.error("District Not Found");
									}
								} else {
									response.setMessage("District Required");
									response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
									//log.warn("District Required");
								}
							} else {
								response.setMessage("Status Required");
								response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
								//log.warn("Status Required");
							}
						} else {
							response.setMessage("Corporation Name Required");
							response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
							//log.warn("Corporation Name Required");
						}
					} else {
						response.setMessage("Corporation Code Required");
						response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
						//log.warn("Corporation Code Required");
					}
				} else {
					String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,Locale.US);
					response.setMessage(corporationDTO.getCorporationCode() + " " +  msg);
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.warn("Corporation Code - {}  Already Exist",corporationDTO.getCorporationCode());
				}
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}

		} catch (DataIntegrityViolationException e) {

			log.error("<---- CorporationDaoImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		} catch (Exception e) {
			log.error("<---- CorporationDaoImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- CorporationDaoImpl.create()  END -----> ");
		return response;
	}

	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- CorporationDaoImpl.getById()  STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			Optional<CorporationMasterEntity> optional = corporationRepository.findById(id);
			if (optional.isPresent()) {
				//log.info("Corporation Id - {}",id);
				response.setResponseContent(optional.get());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Fetched");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- CorporationDaoImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- CorporationDaoImpl.getById()  END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAll() {
		//log.info(" <----- CorporationDaoImpl.getAll()  STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<CorporationMasterEntity> list = corporationRepository.findAll();
			if (list.isEmpty()) {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			} else {
				response.setResponseContents(list);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				//log.info("Successfully Get  All Corporation Details");
			}
		} catch (Exception e) {
			log.error("<----- CorporationDaoImpl.getAll() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- CorporationDaoImpl.getAll()  END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		//log.info(" <----- CorporationDaoImpl.getAllActive()  STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<CorporationMasterEntity> list = corporationRepository.getAllActive();

			if (list.isEmpty()) {
				response.setMessage("No record Found");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.info("No Record Found..");
			} else {
				response.setResponseContents(list);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				//log.info("Successfully Get  All  Active Corporation Details");
			}

		} catch (Exception e) {
			log.error("<----- CorporationDaoImpl.getAllActive() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- CorporationDaoImpl.getAllActive()  END -----> ");
		return response;
	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <-----CorporationDaoImpl.getLazyList()  STARTED -----> ");
		BaseDTO response = new BaseDTO();
		int pageSize = 0;
		int totalRecords = 0;
		int totalPages = 0;
		List<Map<String, Object>> totalListOfDataReport = null;
		List<Map<String, Object>> listCount = null;
		try {
			ReportQueriesEntity reportQuery = reportQueriesRepository.getReportbyName("Corporation_search_pagination");
			if (reportQuery == null) {
				//log.error("Corporation_search_pagination  Not Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
			ReportQueriesEntity reportQueryCount = reportQueriesRepository
					.getReportbyName("Corporation_search_pagination_count");
			if (reportQueryCount == null) {
				//log.error("Corporation_search_pagination_count  Not Found..");
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
				//log.info("totalpages -{}",totalPages);
			}
			if (requestData.getPageNo() != null && requestData.getPaginationSize() != null) {
				if (requestData.getPageNo() != 0) {
					pageSize = (requestData.getPageNo() * requestData.getPaginationSize());
				}
			}
			if (requestData.getSortField() != null) {
				if (requestData.getSortField().toUpperCase().equals("CORPORATIONNAME"))
					query = query.replace(":shortField", "corporation_name");
				else if (requestData.getSortField().toUpperCase().equals("DISTRICTNAME"))
					query = query.replace(":shortField", "district_name");
				else if (requestData.getSortField().toUpperCase().equals("STATENAME"))
					query = query.replace(":shortField", "state_name");

				else if (requestData.getSortField().toUpperCase().equals("STATUS"))
					query = query.replace(":shortField", "status");
			} else
				query = query.replace(":shortField", "corporation_name");

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
				//log.info("Successfully Get All  Corporation Details");
			} else {
				//log.error("No Records Found");
				response.setMessage("No Records Found");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}

		} catch (Exception e) {
			log.error("<----- CorporationDaoImpl.getLazyList() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <-----CorporationDaoImpl.getLazyList()  END -----> ");
		return response;
	}

	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- CorporationDaoImpl.delete()  STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			CorporationMasterEntity corporationMasterEntity = corporationRepository.getOne(id);
			if (corporationMasterEntity != null) {
				corporationRepository.delete(corporationMasterEntity);
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
			log.error("<---- CorporationDaoImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- CorporationDaoImpl.delete()  END -----> ");
		return response;
	}

	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- CorporationDaoImpl.softDelete() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			CorporationMasterEntity corporationMasterEntity = corporationRepository.getOne(id);
			if (corporationMasterEntity != null) {
				corporationMasterEntity.setStatus(false);
				corporationRepository.save(corporationMasterEntity);
				response.setMessage("Successfully Deleted");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- CorporationDaoImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Corporation");
		}
		//log.info(" <----- CorporationDaoImpl.softDelete() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO update(UUID id, CorporationDTO corporationDTO) {
		//log.info(" <----- Corporation updateCorporation Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			boolean check = false;
			Optional<CorporationMasterEntity> optional = corporationRepository.findById(id);
			if (optional.isPresent()) {
				CorporationMasterEntity corporationCode = new CorporationMasterEntity();
				corporationCode = optional.get();
				List<CorporationMasterEntity> checkCode = corporationRepository.checkCode(corporationDTO.getCorporationCode());
				for (CorporationMasterEntity entity : checkCode) {
					if (entity.getCorporationCode().equals(corporationDTO.getCorporationCode())) {
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
					response.setMessage(corporationDTO.getCorporationCode() + " " + msg);
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.info(" corporation code - {} Already Exists",corporationDTO.getCorporationCode());
				} else {
					corporationCode.setCorporationCode(corporationDTO.getCorporationCode());
					corporationCode.setCorporationName(corporationDTO.getCorporationName());
					corporationCode.setDistrictId(corporationDTO.getDistrictId());
					corporationCode.setStatus(corporationDTO.getStatus());
					CorporationMasterEntity afterUpdate = corporationRepository.save(corporationCode);
					response.setResponseContent(afterUpdate);
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(corporationDTO.getCorporationCode() + " " + msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					/*log.info(" Corporation - {} Updated Successfully", corporationDTO.getCorporationCode()
							,corporationDTO.getCorporationName(), corporationDTO.getDistrictId());*/
				}
			} else {
				//log.error("No Record found");
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- CoporationDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}

		catch (Exception e) {
			log.error("<---- CoporationDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info(" <----- Corporation updateCorporation Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO getbyDistrictId(UUID id) {
		//log.info(" <----- CorporationDaoImpl.getbyDistrictId() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			DistrictMasterEntity entity = new DistrictMasterEntity(id);
			List<CorporationMasterEntity> list = corporationRepository.findByDistrictId(entity);
			if (!list.isEmpty()) {
				response.setResponseContents(list);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Fetched");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.info("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- CorporationDaoImpl.getbyDistrictId() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- CorporationDaoImpl.getbyDistrictId() END -----> ");
		return response;
	}

	private CorporationMasterEntity updatedValues(CorporationMasterEntity corporationMasterEntity,
			CorporationDTO corporationDTO) {
		CorporationMasterEntity corporationMasterEntity2 = corporationMasterEntity;
		corporationMasterEntity2.setCorporationCode(corporationDTO.getCorporationCode());
		corporationMasterEntity2.setCorporationName(corporationDTO.getCorporationName());
		corporationMasterEntity2.setStatus(corporationDTO.getStatus());
		corporationMasterEntity2.setDistrictId(corporationDTO.getDistrictId());
		return corporationMasterEntity2;
	}
}
