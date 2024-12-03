package com.oasys.uppcl_user__master_management.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.oasys.config.BaseDTO;
import com.oasys.config.Constants;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.RoleMasterDao;
import com.oasys.uppcl_user__master_management.dto.RoleMasterDTO;
import com.oasys.uppcl_user__master_management.dto.RoleResponse;
import com.oasys.uppcl_user__master_management.dto.UserTypeRspDTO;
import com.oasys.uppcl_user__master_management.entity.DistributorModelEntity;
import com.oasys.uppcl_user__master_management.entity.MerchantTypeMasterEntity;
import com.oasys.uppcl_user__master_management.entity.RoleMasterEntity;
import com.oasys.uppcl_user__master_management.repository.MerchantTypeMasterRepository;
import com.oasys.uppcl_user__master_management.repository.ModelRepository;
import com.oasys.uppcl_user__master_management.repository.ReportQueriesRepository;
import com.oasys.uppcl_user__master_management.repository.RoleMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.response.ResponseMessageConstant;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class RoleMasterDaoImpl implements RoleMasterDao {

	@Autowired
	RoleMasterRepository roleMasterRepository;

	@Autowired
	ReportQueriesRepository reportQueriesRepository;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	ObjectMapper mapper;

	HttpServletResponse request;

//	CustomRemoteTokenService tokencheck;

	@Autowired
	MerchantTypeMasterRepository merchantTypeRepository;

	@Autowired
	MessageSource messageSource;

	@Autowired
	ModelRepository modelRepo;
	
	
	public BaseDTO getAll() {
		BaseDTO response = new BaseDTO();
		// log.info("<==== RoleMasterDaoImpl.getAll() Started ====>");
		List<RoleMasterEntity> roletypeList = null;
		try {

			roletypeList = roleMasterRepository.findAll();
			if (roletypeList.size() != 0) {
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setResponseContents(roletypeList);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				// log.info("Get All Role details Fetched Successfully");
			} else {
				response.setMessage("Role Type List Empty");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<== Exception RoleMasterDaoImpl.getAll() ==>", e);
		}
		// log.info("<==== RoleMasterDaoImpl.getAll() Ended ====>");
		return response;
	}

	@Override
	public BaseDTO getAllRole() {
		BaseDTO response = new BaseDTO();
		List<RoleMasterEntity> list = null;
		try {
			list = roleMasterRepository.findList();
			if (list.size() != 0) {
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setResponseContents(list);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			} else {
				response.setMessage("Role Type List Empty");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<== Exception RoleMasterDaoImpl.getAllRole() ==>", e);
		}
		return response;
	}

	@Override
	public BaseDTO create(RoleMasterDTO roleMasterDTO) {
		// log.info("<== Started RoleMasterDaoImpl.create() ==>");
		BaseDTO response = new BaseDTO();
		RoleMasterEntity roleMasterEntity = new RoleMasterEntity();
		BaseDTO validationResponse = this.validateRequestDTO(roleMasterDTO);
		if (!ErrorDescription.SUCCESS_RESPONSE.getCode().equals(validationResponse.getStatusCode())) {
			return validationResponse;
		}
		roleMasterDTO.setRoleName(roleMasterDTO.getRoleName().trim());
		roleMasterDTO.setDescription(roleMasterDTO.getDescription().trim());
		
		try {
			Optional<RoleMasterEntity> optional = roleMasterRepository
					.findByRoleNameIgnoreCase(roleMasterDTO.getRoleName().toUpperCase());
			if (optional.isPresent()) {
				response.setMessage("This Role Name Already Exist");
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				return response;
			}
			optional = roleMasterRepository
					.findByUserRoleNameIgnoreCase(roleMasterDTO.getRoleName().toUpperCase());
			if (optional.isPresent()) {
				response.setMessage("This Role Name Already Exist");
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				return response;
			}
			roleMasterEntity.setRoleName(roleMasterDTO.getRoleName());
			roleMasterEntity.setDescription(roleMasterDTO.getDescription());
			roleMasterEntity.setStatus(roleMasterDTO.getStatus());
			roleMasterEntity.setUserRoleName(roleMasterDTO.getRoleName());
			roleMasterRepository.save(roleMasterEntity);
			response.setMessage("Role Created Successfully");
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			return response;

		} catch (DataIntegrityViolationException e) {
			log.error("<-------- RoleMasterDaoImpl.create() exception-------->{}", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		} catch (Exception e) {
			log.error("<-------- RoleMasterDaoImpl.create() exception-------->{}", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		// log.info("<== Ended RoleMasterDaoImpl.create() ==>");
		return response;
	}

	@Override
	public BaseDTO update(UUID id, RoleMasterDTO roleMasterDTO) {
		BaseDTO response = new BaseDTO();
		response.setMessage("Role is not allowed to edit");
		response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		return response;
	}
	public BaseDTO updateOld(UUID id, RoleMasterDTO roleMasterDTO) {
		// log.info(" <----- RoleMasterDaoImpl.update() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			boolean check = false;
			Optional<RoleMasterEntity> optional = roleMasterRepository.findById(id);
			if (optional.isPresent()) {
				RoleMasterEntity roleName = new RoleMasterEntity();
				roleName = optional.get();
				List<RoleMasterEntity> checkName = roleMasterRepository.findByName(roleMasterDTO.getRoleName().toUpperCase());
				for (RoleMasterEntity entity : checkName) {
					if (entity.getRoleName().equals(roleMasterDTO.getRoleName())) {
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
					response.setMessage("This Role Name " + msg);
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					// log.warn("Role Name - {} already exists", roleMasterDTO.getRoleName());
				} else {

					Gson gson = new Gson();
					String usertype = gson.toJson(roleMasterDTO.getUserType());
					roleName.setRoleName(roleMasterDTO.getRoleName());
					roleName.setDescription(roleMasterDTO.getDescription());
					roleName.setStatus(roleMasterDTO.getStatus());
					roleName.setUserType(usertype);
					roleName.setUserRoleName(roleMasterDTO.getRoleName());
					RoleMasterEntity afterUpdate = roleMasterRepository.save(roleName);
					response.setResponseContent(afterUpdate);
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(roleMasterDTO.getRoleName() + " " + msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					// log.info("Role - {} Updated Successfully", roleMasterDTO.getRoleName());
				}

			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				// log.warn("No Record Found..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- RoleMasterDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());

		} catch (Exception e) {
			log.error("<---- RoleMasterDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		// log.info(" <----- RoleMasterDaoImpl.update() END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAllRoleList() {
		BaseDTO response = new BaseDTO();
		List<RoleMasterEntity> list = null;
		try {
			list = roleMasterRepository.findRoleList();
			if (list.size() != 0) {
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setResponseContents(list);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			} else {
				response.setMessage("Role Type List Empty");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<== Exception RoleMasterDaoImpl.getAllRoleList() ==>", e);
		}
		return response;
	}

	@Override
	public BaseDTO getRoleList(UUID id) {
		BaseDTO response = new BaseDTO();
		List<RoleMasterEntity> list = null;

		try {
			Optional<RoleMasterEntity> roleMaster = roleMasterRepository.findById(id);
			if (roleMaster.isPresent()) {
				if (roleMaster.get().getRoleName().equals("districtDistributors")
						|| roleMaster.get().getRoleName().equals("SuperDistrictDistributor")) {
					list = roleMasterRepository.findlist1();
				}
				if (roleMaster.get().getRoleName().equals("pincodeDistributors")
						|| roleMaster.get().getRoleName().equals("SuperPincodeDistributor")) {
					list = roleMasterRepository.findlist2();
				}

				if (roleMaster.get().getRoleName().equals("Sales Officer")) {

					list = roleMasterRepository.findList();
				}
				if (roleMaster.get().getRoleName().equals("Admin")) {

					list = roleMasterRepository.getAllactive();
				}
				if(roleMaster.get().getRoleName().equals("Finance Officer")) {
					list = roleMasterRepository.findlist();
				}

				if (list.size() != 0) {
					String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(msg);
					response.setResponseContents(list);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				} else {
					response.setMessage("Role Type List Empty");
					response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				}
			} else {

				response.setMessage("Role Type List Empty");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}

		} catch (Exception e) {
			log.error("<== Exception RoleMasterDaoImpl.getRoleList() ==>", e);
		}
		return response;
	}

	public BaseDTO delete(UUID id) {
		// log.info("<==== Started RoleMasterDaoImpl.delete() ===> ");
		BaseDTO response = new BaseDTO();
		RoleMasterEntity master = new RoleMasterEntity();
		try {
			master = roleMasterRepository.getOne(id);
			if (master.getId() != null) {
				roleMasterRepository.delete(master);
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,
						Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				// log.info("successfully Deleted");
			} else {
				// log.error("role ID does not exist");
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
		} catch (Exception e) {
			log.error("<---- RoleMasterDaoImpl.delete()  exception----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		// log.info("<====Ended RoleMasterDaoImpl.delete() ===> ");
		return response;
	}

	public BaseDTO getById(UUID id) {
		// log.info("<==== Started RoleMasterDaoImpl.getById() ===> ");
		BaseDTO response = new BaseDTO();
		RoleResponse roleResponse = new RoleResponse();
		Optional<RoleMasterEntity> roleTypeMaster = null;
		try {
			roleTypeMaster = roleMasterRepository.findById(id);
			if (roleTypeMaster.isPresent()) {
				// log.info("Role Id {}", id);
				RoleMasterEntity roleMaster = roleTypeMaster.get();
//				roleResponse.setId(roleMaster.getId());
//				String userType = roleMaster.getUserType();
//
//				ObjectMapper mapper = new ObjectMapper();
//				UserTypeRspDTO[] user = mapper.readValue(userType, UserTypeRspDTO[].class);
//
//				List<UserTypeRspDTO> userTypes = getAgentType(user);
//				roleResponse.setUserType(userTypes);

				roleResponse.setRoleName(roleMaster.getRoleName());
				roleResponse.setStatus(roleMaster.getStatus());
				roleResponse.setDescription(roleMaster.getDescription());
				roleResponse.setCreatedDate(roleMaster.getCreatedDate());
				roleResponse.setCreatedBy(roleMaster.getCreatedBy());
				roleResponse.setModifiedDate(roleMaster.getModifiedDate());
				roleResponse.setModifiedBy(roleMaster.getModifiedBy());
				roleResponse.setUserRoleName(roleMaster.getUserRoleName());
				roleResponse.setId(roleMaster.getId());
				response.setResponseContent(roleResponse);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				// log.info("successfully Get Details");
			} else {
				// log.error("role Type id does not Exist");
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
		} catch (Exception e) {
			log.error("<------RoleMasterDaoImpl.getById()------> Exception", e);
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		// log.info("<==== Ended RoleMasterDaoImpl.getById() ===> ");
		return response;
	}

	private List<UserTypeRspDTO> getAgentType(UserTypeRspDTO[] user) {

		List<UserTypeRspDTO> agentType = new ArrayList<UserTypeRspDTO>();

		for (UserTypeRspDTO single : user) {
			single.setId(single.getId());
			UUID agentTypeId = UUID.fromString(single.getId());
			MerchantTypeMasterEntity merchantTypeMaster = merchantTypeRepository.getOne(agentTypeId);

			single.setName(merchantTypeMaster.getAgentType());
			agentType.add(single);
		}
		return agentType;
	}

	public BaseDTO getAllActive() {
		// log.info("<------RoleMasterDaoImpl.getAllActive()------> Started");
		BaseDTO response = new BaseDTO();
		List<RoleMasterEntity> roleTypeMasterList = new ArrayList<RoleMasterEntity>();
		List<RoleMasterEntity> roleTypeMaster = null;
		try {
			roleTypeMaster = roleMasterRepository.getAllactive();
			if (roleTypeMaster != null) {
				DistributorModelEntity res = modelRepo.getData();
				if (res.getType() == 1) {
					for (RoleMasterEntity roleMaster : roleTypeMaster) {
						RoleMasterEntity roleMasterEntity = new RoleMasterEntity();
						if (roleMaster.getRoleName().equalsIgnoreCase("SuperPincodeDistributor")
								|| roleMaster.getRoleName().equalsIgnoreCase("SuperDistrictDistributor")) {

						} else {
							roleMasterEntity.setId(roleMaster.getId());
							roleMasterEntity.setRoleName(roleMaster.getRoleName());
							roleMasterEntity.setStatus(roleMaster.getStatus());
							roleMasterEntity.setUserRoleName(roleMaster.getUserRoleName());
							roleMasterEntity.setDescription(roleMaster.getDescription());
							roleTypeMasterList.add(roleMasterEntity);
						}
					}
				} else {
					for (RoleMasterEntity roleMaster : roleTypeMaster) {
						RoleMasterEntity roleMasterEntity = new RoleMasterEntity();
						if (roleMaster.getRoleName().equalsIgnoreCase("districtDistributors")
								|| roleMaster.getRoleName().equalsIgnoreCase("pincodeDistributors")) {

						} else {
							roleMasterEntity.setId(roleMaster.getId());
							roleMasterEntity.setRoleName(roleMaster.getRoleName());
							roleMasterEntity.setStatus(roleMaster.getStatus());
							roleMasterEntity.setUserRoleName(roleMaster.getUserRoleName());
							roleMasterEntity.setDescription(roleMaster.getDescription());
							roleTypeMasterList.add(roleMasterEntity);
						}
					}
				}

				response.setResponseContents(roleTypeMasterList);
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,
						Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				// log.info("successfully Get all active details");
			} else {
				// log.warn("No role is Active");
				response.setMessage("No role is Active");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<------RoleMasterDaoImpl.getAllActive()------> exception", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,
					Locale.US);
			response.setMessage(msg);
		}
		// log.info("<------RoleMasterDaoImpl.getAllActive()------> Ended");
		return response;
	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO pageData) {
		// log.info("<== RoleMasterDaoImpl.getAllLazy() Started ==>");
		BaseDTO response = new BaseDTO();
		Page<RoleMasterEntity> roleMasterEntity = null;
		List<RoleMasterEntity> contentList = new ArrayList<RoleMasterEntity>();
		Pageable pageRequest;
		try {
			if (pageData.getSearch() != null) {
				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.ASC, pageData.getSortField()));
					roleMasterEntity = roleMasterRepository.search(pageRequest, pageData.getSearch());
				} else {
					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.DESC, pageData.getSortField()));
					roleMasterEntity = roleMasterRepository.search(pageRequest, pageData.getSearch());
				}
			} else {

				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					roleMasterEntity = roleMasterRepository.findAll(PageRequest.of(pageData.getPageNo(),
							pageData.getPaginationSize(), Sort.by(Direction.ASC, pageData.getSortField())));
				} else {
					roleMasterEntity = roleMasterRepository.findAll(PageRequest.of(pageData.getPageNo(),
							pageData.getPaginationSize(), Sort.by(Direction.DESC, pageData.getSortField())));
				}
			}
			if (roleMasterEntity.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				// log.error("No Record Found..");
			} else {
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				response.setNumberOfElements(roleMasterEntity.getNumberOfElements());
				response.setTotalRecords(roleMasterEntity.getTotalElements());
				response.setTotalPages(roleMasterEntity.getTotalPages());
				for (RoleMasterEntity roleMaster : roleMasterEntity) {
					contentList.add(roleMaster);
				}
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setResponseContents(contentList);
				// log.info("successfully Get Details");
			}
		} catch (Exception e) {
			log.error("<<<< ------- RoleMasterDaoImpl.getAllLazy() --------- Exception>>>>", e);
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		// log.info("<== RoleMasterDaoImpl.getAllLazy() Ended ==>");
		return response;
	}

	@Override
	public BaseDTO getByName(String name) {
		// log.info("<==== Started RoleMasterDaoImpl.getByName() ===> ");
		BaseDTO response = new BaseDTO();
		RoleMasterEntity roleTypeMaster = null;
		try {

			roleTypeMaster = roleMasterRepository.findByRoleName(name).get();
			if (roleTypeMaster.getId() != null) {
				response.setResponseContent(roleTypeMaster);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				// log.info("Get Details successfully");
			} else {
				// log.error("role Type id does not Exist");
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
		// log.info("<==== Ended RoleMasterDaoImpl.getByName() ===> ");
		return response;
	}
	/*
	 * @Override public BaseDTO getByUserTypeId(UUID id) {
	 * log.info(" <----- RoleMasterDaoImpl.getByUserTypeId() Dao STARTED -----> ");
	 * BaseDTO baseDTO = new BaseDTO(); try {
	 * 
	 * MerchantTypeMasterEntity entity = new MerchantTypeMasterEntity();
	 * entity.setId(id); List<RoleMasterEntity> roleName =
	 * roleMasterRepository.getByUserTypeId(entity); if (!roleName.isEmpty()) {
	 * String msg =
	 * messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,
	 * Locale.US); baseDTO.setMessage(msg);
	 * baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
	 * baseDTO.setResponseContents(roleName); log.info("Successfully Fetched"); }
	 * else { baseDTO.setMessage("No Record Found..");
	 * baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
	 * log.error("No Record Found.."); } } catch (Exception e) {
	 * log.error("<---- RoleMasterDaoImpl.getByUserTypeId() ----> EXCEPTION", e);
	 * baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode()); String
	 * msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE,
	 * null, Locale.US); baseDTO.setMessage(msg); }
	 * log.info(" <----- RoleMasterDaoImpl.getByUserTypeId() Dao END -----> ");
	 * return baseDTO; }
	 */

	@Override
	public BaseDTO getByUserTypeId(UUID id) {
		// log.info("<------RoleMasterDaoImpl.getAllActive()------> Started");
		BaseDTO response = new BaseDTO();
		List<RoleMasterEntity> roleTypeMaster = null;
		List<RoleMasterEntity> roleTypeMasterfil = null;
		try {
			roleTypeMaster = roleMasterRepository.getAllactive();
			if (roleTypeMaster != null) {

				String agentTypeId;
				StringBuilder rollId = new StringBuilder();
				for (RoleMasterEntity single1 : roleTypeMaster) {
					String agentType = single1.getUserType().trim();
					if (!agentType.isEmpty()) {
						// log.info("agentType" + agentType);
						ObjectMapper mapper = new ObjectMapper();
						// 1. convert JSON array to Array objects
						UserTypeRspDTO[] pp1 = mapper.readValue(agentType, UserTypeRspDTO[].class);

						// List<UserTypeRspDTO> = Arrays.asList(pp1);
						// log.info("agentType" + pp1);
						String agentId = getfilter(pp1, id);
						if (!agentId.equals("")) {
							rollId.append(single1.getId().toString().trim() + ",");
						}
					}

				}
				agentTypeId = rollId.toString().replaceAll(",$", "");
				// log.info("agentTypeId" + agentTypeId);

				if (agentTypeId != "") {

					UUID UUID = null;
					String[] values = agentTypeId.split(",");
					List<UUID> listdata = new ArrayList<UUID>();
					for (int i = 0; i < values.length; i++) {
						UUID entity = UUID.fromString(values[i]);
						listdata.add(entity);
					}
					roleTypeMasterfil = roleMasterRepository.findByIdIn(listdata);
					// log.info("list" + listdata);
					response.setResponseContents(roleTypeMasterfil);
					String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE,
							null, Locale.US);
					response.setMessage(msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					// log.info("Successfully Get all active details");

				} else {
					// log.warn("No role is Active");
					response.setMessage("No role is Active");
					response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());

				}
			} else {
				// log.warn("No role is Active");
				response.setMessage("No role is Active");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<------RoleMasterDaoImpl.getByUserTypeId()------> exception", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,
					Locale.US);
			response.setMessage(msg);
		}
		// log.info("<------RoleMasterDaoImpl.getAllActive()------> Ended");
		return response;
	}

	private String getfilter(UserTypeRspDTO[] pp1, UUID id) {

		String uuid = id.toString();
		// log.info("pp1----" + pp1);
		// log.info("uuid : VALUE" + uuid);
		StringBuilder data = new StringBuilder();
		String agentId;
		for (UserTypeRspDTO single : pp1) {
			// log.info("single" + single);
			if (single.getId().equals(uuid)) {
				data.append(single.getId());
			}

		}
		agentId = data.toString();
		return agentId;
	}

	@Override
	public BaseDTO getRoleCreator() {
		BaseDTO response = new BaseDTO();
		List<RoleMasterEntity> roleTypeMaster = null;
		try {

			DistributorModelEntity res = modelRepo.getData();
			if (res.getType() == 1) {
				roleTypeMaster = roleMasterRepository.getActiveRoleByModel1();
			} else {
				roleTypeMaster = roleMasterRepository.getActiveRoleByModel2();
			}

			if (roleTypeMaster != null) {
				response.setResponseContents(roleTypeMaster);
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,
						Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				// log.info("successfully Get all active details");
			} else {

				response.setMessage("No role is Active");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<------RoleMasterDaoImpl.getAllActive()------> exception", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,
					Locale.US);
			response.setMessage(msg);
		}

		return response;
	}

	@Override
	public BaseDTO getRoleCreation(UUID id) {
		BaseDTO response = new BaseDTO();
		List<RoleMasterEntity> roleTypeMaster = null;
		try {
			Optional<RoleMasterEntity> master = roleMasterRepository.findById(id);
			if (master.isPresent()) {
				DistributorModelEntity res = modelRepo.getData();
				if (res.getType() == 1) {

					if (master.get().getRoleName().equalsIgnoreCase("Admin")
							|| master.get().getRoleName().equalsIgnoreCase("Sales Officer")) {

						roleTypeMaster = roleMasterRepository.getActiveRoleByAdminModel1();
					}
					if (master.get().getRoleName().equalsIgnoreCase("districtDistributors")) {

						roleTypeMaster = roleMasterRepository.getActiveRoleByDistModel1();
					}
					if (master.get().getRoleName().equalsIgnoreCase("pincodeDistributors")) {

						roleTypeMaster = roleMasterRepository.getActiveRoleByPin();
					}

				} else {
					if (master.get().getRoleName().equalsIgnoreCase("Admin")
							|| master.get().getRoleName().equalsIgnoreCase("Sales Officer")) {

						roleTypeMaster = roleMasterRepository.getActiveRoleByAdminModel2();
					}
					if (master.get().getRoleName().equalsIgnoreCase("SuperDistrictDistributor")) {

						roleTypeMaster = roleMasterRepository.getActiveRoleByDistModel2();
					}
					if (master.get().getRoleName().equalsIgnoreCase("SuperPincodeDistributor")) {

						roleTypeMaster = roleMasterRepository.getActiveRoleByPin();
					}
				}
			}
			if (roleTypeMaster != null) {
				response.setResponseContents(roleTypeMaster);
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,
						Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				// log.info("successfully Get all active details");
			} else {

				response.setMessage("No role is Active");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<------RoleMasterDaoImpl.getAllActive()------> exception", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,
					Locale.US);
			response.setMessage(msg);
		}

		return response;
	}

	@Override
	public BaseDTO getActiveUserListForKYCAdmin() {
		BaseDTO response = new BaseDTO();
		DistributorModelEntity distributorModelEntity = modelRepo.getData();
		List<RoleMasterEntity> userList = null;
		if (Objects.nonNull(distributorModelEntity) && distributorModelEntity.getType() == 1) {
			userList = roleMasterRepository.getActiveRolesForKYCAdminUserListModel1();
		} else {
			userList = roleMasterRepository.getActiveRolesForKYCAdminUserListModel2();
		}
		if (CollectionUtils.isNotEmpty(userList)) {
			response.setResponseContents(userList);
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,
					Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
		} else {
			log.info("Inside RoleMasterDaoImpl.getKYCAdminActive()=> NO active data found for KYC Admin User List ");
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,
					Locale.US);
			response.setMessage(msg);
		}
		return response;
	}
	
	public BaseDTO validateRequestDTO(RoleMasterDTO roleMasterDTO) {
		BaseDTO response = new BaseDTO();
		if (StringUtils.isEmpty(roleMasterDTO.getRoleName())) {
			response.setMessage("Role Name is Required");
			response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			return response;
		}
		if (StringUtils.isEmpty(roleMasterDTO.getDescription())) {
			response.setMessage("Role Description Required");
			response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			return response;
		}
		if (Objects.isNull(roleMasterDTO.getStatus())) {
			response.setMessage("Status Required");
			response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			return response;
		}
		roleMasterDTO.setRoleName(roleMasterDTO.getRoleName().trim());
		roleMasterDTO.setDescription(roleMasterDTO.getDescription().trim());
		if (roleMasterDTO.getRoleName().length() < Constants.NAME_FIELD_MIN_LENTH) {
			response.setStatusCode(ResponseMessageConstant.MINIMUM_FIELD_LENGTH_VALIDATION_FAILURE.getErrorCode());
			response.setMessage(ResponseMessageConstant.MINIMUM_FIELD_LENGTH_VALIDATION_FAILURE
					.getMessage(new Object[] { Constants.NAME_FIELD_MIN_LENTH, Constants.ROLE_NAME }));
			return response;
		}
		if (roleMasterDTO.getRoleName().length() > Constants.NAME_FIELD_MAX_LENTH) {
			response.setStatusCode(ResponseMessageConstant.FIELD_LENGTH_EXCEEDED.getErrorCode());
			response.setMessage(ResponseMessageConstant.FIELD_LENGTH_EXCEEDED
					.getMessage(new Object[] { Constants.ROLE_NAME, Constants.NAME_FIELD_MAX_LENTH }));
			return response;
		}
		if (roleMasterDTO.getDescription().length() < Constants.NAME_FIELD_MIN_LENTH) {
			response.setStatusCode(ResponseMessageConstant.MINIMUM_FIELD_LENGTH_VALIDATION_FAILURE.getErrorCode());
			response.setMessage(ResponseMessageConstant.MINIMUM_FIELD_LENGTH_VALIDATION_FAILURE
					.getMessage(new Object[] { Constants.NAME_FIELD_MIN_LENTH, Constants.DESCRIPTION }));
			return response;
		}
		if (roleMasterDTO.getDescription().length() > Constants.REMARKS_FIELD_MAX_LENTH) {
			response.setStatusCode(ResponseMessageConstant.FIELD_LENGTH_EXCEEDED.getErrorCode());
			response.setMessage(ResponseMessageConstant.FIELD_LENGTH_EXCEEDED
					.getMessage(new Object[] { Constants.DESCRIPTION, Constants.NAME_FIELD_MAX_LENTH }));
			return response;
		}
		response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
		return response;
	}

	
}
