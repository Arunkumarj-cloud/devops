package com.oasys.uppcl_user__master_management.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.cexception.NoRecoerdFoundException;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.DistrictMasterDAO;
import com.oasys.uppcl_user__master_management.dto.DistrictMasterDTO;
import com.oasys.uppcl_user__master_management.entity.DistrictMasterEntity;
import com.oasys.uppcl_user__master_management.entity.StateMasterEntity;
import com.oasys.uppcl_user__master_management.repository.DistrictMasterRepository;
import com.oasys.uppcl_user__master_management.repository.StateMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class DistrictMasterDaoImpl implements  DistrictMasterDAO{

	
	@Autowired
	DistrictMasterRepository districtMasterRepository;

	@Autowired
	StateMasterRepository stateMasterRepository;

	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	DistrictMasterDAO districtMasterDAO;

	final String DELETE_OK = "Y";

	@Autowired
	MessageSource messageSource;

	@Override
	public BaseDTO create(DistrictMasterDTO districtMasterDTO) {
		// log.info(" <-----DistrictMasterDaoImpl.create() Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();

		if (Objects.nonNull(districtMasterDTO)) {

			DistrictMasterEntity districtMasterEntity = new DistrictMasterEntity();
			if (StringUtils.isNotBlank(districtMasterDTO.getDistrictName())) {
				// log.info(" District Name {} -Is Not Empty",
				// districtMasterDTO.getDistrictName());
				if (Objects.nonNull(districtMasterDTO.getStatus())) {
					// log.info(" Status {} -Is Not Empty", districtMasterDTO.getStatus());
					if (Objects.nonNull(districtMasterDTO.getStateId())) {
						
						if (stateMasterRepository.findById(districtMasterDTO.getStateId().getId()).isPresent()) {
							Optional<DistrictMasterEntity> optional = districtMasterRepository
									.check(districtMasterDTO.getDistrictName().trim().toUpperCase(), districtMasterDTO.getStateId().getId());
							if (optional.isPresent()) {
								baseDTO.setMessage("This District Name Already Exist.");
								baseDTO.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
								/*
								 * log.info("District Code - {}  Already Exist",
								 * districtMasterDTO.getDistrictName(), districtMasterDTO.getDistrictCode(),
								 * districtMasterDTO.getStateId().getId());
								 */
							}
							else {
								districtMasterEntity.setDistrictName(districtMasterDTO.getDistrictName().trim());
								districtMasterEntity.setDistrictCode(null);
								districtMasterEntity.setStateCode(districtMasterDTO.getStateCode());
								districtMasterEntity.setStatus(districtMasterDTO.getStatus());
								districtMasterEntity.setStateId(districtMasterDTO.getStateId());
								districtMasterEntity.setRemarks(districtMasterDTO.getRemarks());
								districtMasterRepository.save(districtMasterEntity);
								String msg = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE,
										null, Locale.US);
								baseDTO.setMessage(districtMasterDTO.getDistrictName() + " " + msg);
								baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
								/*
								 * log.info("District - {} Created Successfully",
								 * districtMasterDTO.getStateId(), districtMasterDTO.getDistrictName(),
								 * districtMasterDTO.getDistrictCode());
								 */
							}
						} else {
							baseDTO.setMessage("State Not Found");
							baseDTO.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
							// log.warn("State Not Found");
						}
					} else {
						baseDTO.setMessage("stateId Required");
						baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
						// log.warn("District Required");
					}
				} else {
					baseDTO.setMessage("Status Required");
					baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
					// log.warn("Status Required");
				}
			} else {
				baseDTO.setMessage("District Name Required");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				// log.warn("District Name Required");
			}

		} else {
			baseDTO.setMessage("No Record Found..");
			baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			// log.error("No Record Found..");
		}

		// log.info(" <-----DistrictMasterDaoImpl.create() Dao END -----> ");
		return baseDTO;
	}
	
	
	@Override
	public BaseDTO getByName(String name) {
		//log.info("<==== Started RoleMasterDaoImpl.getByName() ===> ");
		BaseDTO response = new BaseDTO();
		try {
			
			List<DistrictMasterEntity> roleTypeMaster1= districtMasterRepository.findByDistrictName(name);
			if (!roleTypeMaster1.isEmpty()) {
				response.setResponseContent(roleTypeMaster1.get(0));
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Get Details successfully");
			} else {
				//log.error("role Type id does not Exist");
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
		} catch (Exception e) {
			log.error("<------RoleMasterDaoImpl.getByName()------> Exception", e);
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<==== Ended RoleMasterDaoImpl.getByName() ===> ");
		return response;
	}

	@Override
	public List<DistrictMasterEntity> getAll() {
		//log.info(" <----- DistrictMasterDaoImpl getAll Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		List<DistrictMasterEntity> districtMasterEntity = new ArrayList<DistrictMasterEntity>();
		try {
			List<DistrictMasterEntity> districtMasterEntity1 = districtMasterRepository.findAll();
			for (DistrictMasterEntity district : districtMasterEntity1) {
				DistrictMasterEntity district1 = objectMapper.convertValue(district, DistrictMasterEntity.class);
				districtMasterEntity.add(district1);
			}
			if (districtMasterEntity.isEmpty()) {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setResponseContents(districtMasterEntity);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get District Details..");
			}
		} catch (Exception e) {
			log.error("<---- DistrictMasterDaoImpl.getAll() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- DistrictMasterDaoImpl getAll Service END -----> ");
		return districtMasterEntity;
	}

	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- DistrictMasterDaoImpl.getById() Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			Optional<DistrictMasterEntity> districtName = districtMasterRepository.findById(id);
			if (districtName.isPresent()) {
				//log.info("District id - {} ", id);
				baseDTO.setMessage("Successfully Fetched");
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				baseDTO.setResponseContent(districtName.get());
				//log.info("Successfully Fetched");
			} else {
				baseDTO.setMessage("No Record Found..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- DistrictMasterDaoImpl.getById() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			baseDTO.setMessage("Unable to Get District");
		}
		//log.info(" <-----  DistrictMasterDaoImpl.getById() Dao END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO getByIds(Set<UUID> ids) {
		BaseDTO baseDTO = new BaseDTO();
		List<DistrictMasterEntity> districts = districtMasterRepository.findByIdIn(ids);
		if (districts == null || districts.size() == 0) {
			throw new NoRecoerdFoundException();
		}
		log.warn("district response ---- " 
				+ districts);
		baseDTO.setResponseContents(districts);
		return baseDTO;
		
	}

	@Override
	public BaseDTO getAllActive() {
		//log.info(" <-----DistrictMasterDaoImpl.getAllActive() Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			List<DistrictMasterEntity> districtName = districtMasterRepository.getAllActive();

			if (districtName.isEmpty()) {
				baseDTO.setMessage("No Record Found..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.info("No Record Found..");
			} else {
				baseDTO.setResponseContents(districtName);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,
						Locale.US);
				baseDTO.setMessage(msg);
				//log.info("Successfully Get All District Details");
			}

		} catch (Exception e) {
			log.error("<----- DistrictMasterDaoImpl.getAllActive() ----->", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,
					Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <-----DistrictMasterDaoImpl.getAllActive() Dao END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- DistrictMasterDaoImpl.delete() Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			DistrictMasterEntity districtName = districtMasterRepository.getOne(id);
			if (districtName != null) {
				districtMasterRepository.delete(districtName);
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,
						Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted");
			} else {
				baseDTO.setMessage("No Record Found..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}

		} catch (Exception e) {
			log.error("<---- DistrictMasterDaoImpl.delete() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- DistrictMasterDaoImpl.delete() Dao END -----> ");
		return baseDTO;
	}

	public BaseDTO softDelete(UUID id) {
		//log.info(" <-----  DistrictMasterServiceImpl.softDelete() Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			DistrictMasterEntity districtName = districtMasterRepository.getOne(id);
			if (districtName != null) {
				districtName.setStatus(false);
				districtMasterRepository.save(districtName);
				baseDTO.setMessage("Successfully Deleted");
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted");
			} else {
				baseDTO.setMessage("No Record Found..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				///log.info("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- DistrictMasterServiceImpl.softDelete() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			baseDTO.setMessage("Unable to Delete districtName");
		}
		//log.info(" <-----  DistrictMasterServiceImpl.softDelete() Service END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- DistrictMasterDaoImpl.getLazyList() Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		List<Map<String, Object>> totalListOfDataReport = null;
		List<Map<String, Object>> listCount = null;
		int pageSize = 0;
		int totalRecords = 0;
		int totalPages = 0;
		String appendStateName = "";
		String appendDistrictName = "";
		String appendDistrictCode = "";

		String createdDate = "";
		String appendOrder = " ORDER BY ";
		StringBuffer countBuffer = new StringBuffer();

		try {
			StringBuffer buffer = new StringBuffer();
			if (requestData.getSearch() != null) {
				appendDistrictCode = " UPPER(dm.district_code) LIKE '%" + requestData.getSearch().toUpperCase() + "%'";
				appendStateName = " or UPPER(sm.state_name) LIKE '%" + requestData.getSearch().toUpperCase() + "%'";
				createdDate = " or UPPER(sm.created_date) LIKE '%" + requestData.getSearch().toUpperCase() + "%'";
				appendDistrictName = " or UPPER(dm.district_name) LIKE '%" + requestData.getSearch().toUpperCase()
						+ "%'";

			} else {
				appendDistrictCode = " UPPER(dm.district_code) LIKE '%" + "" + "%'";
				appendStateName = " or UPPER(sm.state_name) LIKE '%" + "" + "%'";
				createdDate = " or UPPER(sm.created_date) LIKE '%" + "" + "%'";

				appendDistrictName = " or UPPER(dm.district_name) LIKE '%" + "" + "%'";
			}
			countBuffer.append(
					"SELECT COUNT(*) as count from district_master dm JOIN state_master sm on dm.stateid = sm.id and sm.status =1 where"
							+ appendDistrictCode + " " + appendDistrictName + "" + appendStateName + " " + createdDate
							+ " ");
			listCount = jdbcTemplate.queryForList(countBuffer.toString());
			if (listCount.size() > 0 && listCount.get(0).get("count") != null) {
				totalRecords = Integer.parseInt(listCount.get(0).get("count").toString());
				// totalPages = Math.round(totalRecords / requestData.getPaginationSize());
				totalPages = (totalRecords / requestData.getPaginationSize());
				if ((totalRecords % requestData.getPaginationSize()) != 0) {
					totalPages++;
				}
				log.info("totalpages -{}", totalPages);
			}
			buffer.append(
					"select dm.id as id, dm.district_code as districtCode, dm.district_name as districtName,dm.status, dm.created_date as createdDate,"
							+ " sm.state_code as stateCode,sm.state_name as stateName from district_master dm JOIN state_master sm on dm.stateid = sm.id and sm.status = 1 where"
							+ appendDistrictCode + " " + appendDistrictName + "" + appendStateName + " " + createdDate
							+ " ");
			if (requestData.getPageNo() != null && requestData.getPaginationSize() != null) {
				if (requestData.getPageNo() != 0) {
					pageSize = (requestData.getPageNo() * requestData.getPaginationSize());
				}
			}
			if (requestData.getSortField() != null) {
				if (requestData.getSortField().toUpperCase().equals("DISTRICTNAME"))
					appendOrder = appendOrder + "dm.district_name ";
				else if (requestData.getSortField().toUpperCase().equals("DISTRICTCODE"))
					appendOrder = appendOrder + "dm.district_code ";

				else if (requestData.getSortField().toUpperCase().equals("CREATEDDATE"))
					appendOrder = appendOrder + "dm.created_date ";
				else if (requestData.getSortField().toUpperCase().equals("STATENAME"))
					appendOrder = appendOrder + "sm.state_name ";

				else if (requestData.getSortField().toUpperCase().equals("STATUS"))
					appendOrder = appendOrder + "dm.status ";
			} else
				appendOrder = appendOrder + "dm.district_name ";
			if (requestData.getSortOrder() != null) {
				if (!requestData.getSortOrder().toUpperCase().startsWith("A"))
					appendOrder = appendOrder + "DESC ";
			}
			buffer.append(appendOrder + " limit " + pageSize + "," + requestData.getPaginationSize() + "");
			//log.info("<=========== query " + buffer);
			totalListOfDataReport = jdbcTemplate.queryForList(buffer.toString());
			for (Map<String, Object> entity : totalListOfDataReport) {
				for (Map.Entry<String, Object> entry : entity.entrySet()) {
					if (entry.getKey().equals("id")) {
						entry.setValue(objectMapper.convertValue(entry.getValue(), UUID.class));
					}
				}
			}

			if (totalListOfDataReport != null && totalListOfDataReport.size() > 0) {
				baseDTO.setResponseContents(totalListOfDataReport);
				baseDTO.setNumberOfElements(requestData.getPaginationSize());
				baseDTO.setTotalRecords(Long.parseLong(totalRecords + ""));
				baseDTO.setTotalPages(totalPages);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("successfully get details");
			} else {
				//log.error("No Records Found");
				baseDTO.setMessage("No Records Found");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<----- DistrictMasterDaoImpl.getLazyList() ----->", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- DistrictMasterDaoImpl.getLazyList() Dao END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO getByStateId(UUID id) {
		//log.info(" <----- DistrictMasterDaoImpl.getById() Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {

			StateMasterEntity entity = new StateMasterEntity();
			entity.setId(id);
			List<DistrictMasterEntity> districtName = districtMasterRepository.findByStateId(entity);
			if (!districtName.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				baseDTO.setResponseContents(districtName);
				//log.info("Successfully Fetched");
			} else {
				baseDTO.setMessage("No Record Found..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- DistrictMasterDaoImpl.getById() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- DistrictMasterDaoImpl.getById() Dao END -----> ");
		return baseDTO;
	}

	private DistrictMasterEntity updatedValues(DistrictMasterEntity districtMaster,
			DistrictMasterDTO districtMasterDTO) {
		DistrictMasterEntity districtName = districtMaster;
		districtName.setDistrictCode(districtMasterDTO.getDistrictCode());
		districtName.setDistrictName(districtMasterDTO.getDistrictName());
		districtName.setStatus(districtMasterDTO.getStatus());
		districtName.setStateId(districtMasterDTO.getStateId());
		return districtName;
	}

	@Override
	public BaseDTO update(UUID id, DistrictMasterDTO districtMasterDTO) {
		//log.info(" <----- DistrictMasterDaoImpl.update() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			boolean check = false;
			Optional<DistrictMasterEntity> optional = districtMasterRepository.findById(id);
			if (optional.isPresent()) {
				DistrictMasterEntity districtName = new DistrictMasterEntity();
				districtName = optional.get();
				Optional<DistrictMasterEntity> districtMaster = districtMasterRepository
						.check(districtMasterDTO.getDistrictName().trim().toUpperCase(), districtMasterDTO.getStateId().getId());
				if (districtMaster.isPresent()) {
					if (id.equals(districtMaster.get().getId())) {
						check = false;
					} else {
						check = true;
					}
				}
			
				if (check) {
					String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
					response.setMessage("This District Name Already Exist.");
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.warn("District Code is already exists - {} ", districtMasterDTO.getDistrictCode());
				} else {
					districtName.setDistrictName(districtMasterDTO.getDistrictName());
					districtName.setStateCode(districtMasterDTO.getStateCode());
					districtName.setStateId(districtMasterDTO.getStateId());
					districtName.setStatus(districtMasterDTO.getStatus());
					districtName.setRemarks(districtMasterDTO.getRemarks());
					DistrictMasterEntity afterUpdate = districtMasterRepository.save(districtName);
					response.setResponseContent(afterUpdate);
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(districtMasterDTO.getDistrictName() + " " + msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					/*log.info("District - {} Updated Successfully", districtMasterDTO.getDistrictCode(),
							districtMasterDTO.getDistrictName(), districtMasterDTO.getStateId());*/
				}

			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- DistrictMasterDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());

		} catch (Exception e) {
			log.error("<---- DistrictMasterDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info(" <----- DistrictMasterDaoImpl.update() END -----> ");
		return response;
	}

	@Override
	public BaseDTO getBydistricName(String districName,UUID stateID) {
		//log.info(" <----- DistrictMasterDaoImpl.getBydistricName() Dao STARTED -----> ");
		BaseDTO baseDTO= new BaseDTO();
		DistrictMasterEntity districtName = null ;
		try {
			
			districtName = districtMasterRepository.findByDistrictAndStateId(districName,stateID);
			
			if (districtName!=null) {		
				
				baseDTO.setResponseContent(districtName);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getErrorCode());
			} else {
				baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
				
			}
		} catch (Exception e) {
			log.error("<---- DistrictMasterDaoImpl.getBydistricName() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			
		}
		//log.info(" <----- DistrictMasterDaoImpl.getById() Dao END -----> ");
		return baseDTO;
		
	}
}
