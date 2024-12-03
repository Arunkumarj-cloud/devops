package com.oasys.uppcl_user__master_management.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.UserTypeDao;
import com.oasys.uppcl_user__master_management.dto.UserTypeDTO;
import com.oasys.uppcl_user__master_management.entity.UserTypeMasterEntity;
import com.oasys.uppcl_user__master_management.repository.UserTypeRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class UserTypeDaoImpl implements UserTypeDao {

	@Autowired
	UserTypeRepository userTypeRepository;

	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	MessageSource messageSource;

	@Override
	public BaseDTO create(UserTypeDTO userTypeDTO) {
		//log.info(" <----- UserType create Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {

			UserTypeMasterEntity userType = objectMapper.convertValue(userTypeDTO, UserTypeMasterEntity.class);
			UserTypeMasterEntity type = userTypeRepository.getByUserTypeIgnoreCase(userType.getUserType().toUpperCase());
			if (type == null) {
				UserTypeMasterEntity u = userTypeRepository.save(userType);
				UserTypeDTO re = new UserTypeDTO();
				re.setId(u.getId());
				re.setUserType(u.getUserType());
				re.setDescription(u.getDescription());
				re.setStatus(u.getStatus());
				String msg = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setResponseContent(re);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Added");
			} else {
				response.setMessage("This User Type is already exists..");
				response.setResponseContent(type);
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("User Type is already exists..");
			}
		}catch(DataIntegrityViolationException e) {
			log.error("<---- UserTypeDaoImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}catch (Exception e) {
			log.error("<---- UserTypeDaoImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- UserType create Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO update(UUID id, UserTypeDTO userTypeDTO) {
		//log.info(" <----- UserType updateUserType Dao STARTED -----> {} and {} ",id,userTypeDTO);
		BaseDTO response = new BaseDTO();
		try {
			boolean validateUserType = false;
			UserTypeMasterEntity userType = userTypeRepository.getOne(id);
			if (userType != null) {
				UserTypeMasterEntity beforeUpdate = updatedValues(userType, userTypeDTO);
				List<UserTypeMasterEntity> except = userTypeRepository.getByExceptId(id);
				for (UserTypeMasterEntity serTypeMaster : except) {
					if (serTypeMaster.getId() != beforeUpdate.getId()) {
						if (serTypeMaster.getUserType().equalsIgnoreCase(beforeUpdate.getUserType())) {
							validateUserType = true;
							break;
						} else {
							validateUserType = false;
						}
					} else {
						validateUserType = false;
					}
				}
				if (!validateUserType) {
					UserTypeMasterEntity afterUpdate = userTypeRepository.save(beforeUpdate);
					response.setResponseContent(afterUpdate);
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage(msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Updated");
				} else {
					response.setMessage("This User Type is already exists..");
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.error("User Type is already exists..");
				}
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("No Record Found..");
			}
		}catch(DataIntegrityViolationException e) {
			log.error("<---- UserTypeDaoImpl.update() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}catch (Exception e) {
			log.error("<---- UserTypeDaoImpl.update() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info(" <----- UserType updateUserType Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- UserType getById Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			UserTypeMasterEntity userType = userTypeRepository.getOne(id);
			if (userType != null) {
				response.setResponseContent(userType);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get UserType");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- UserTypeDaoImpl.getById() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- UserType getById Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAll() {
		//log.info(" <----- UserType getAll Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<UserTypeMasterEntity> userType = userTypeRepository.findAll();
			if (userType.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.info("No Record Found..");
			} else {
				response.setResponseContents(userType);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				//log.info(" Successfully Get All User Type Details");
			}
		} catch (Exception e) {
			log.error("<----- UserTypeDaoImpl.getAll() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- UserType getAll Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		//log.info(" <----- UserType getAllActive Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<UserTypeMasterEntity> userType = userTypeRepository.getAllActive();
			if (userType.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setResponseContents(userType);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				//log.info(" Successfully Get All Active  User Type Details");
			}
		} catch (Exception e) {
			log.error("<----- UserTypeDaoImpl.getAll() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- UserType getAllActive Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- UserType getLazyList Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		List<UserTypeMasterEntity> contentList = new ArrayList<UserTypeMasterEntity>();
		Page<UserTypeMasterEntity> userTypeList = null;
		Pageable pageRequest;
		try {
			if (requestData.getSearch() != null) {
				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.ASC, requestData.getSortField()));
					userTypeList = userTypeRepository.search(pageRequest, requestData.getSearch().toUpperCase());
				} else {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.DESC, requestData.getSortField()));
					userTypeList = userTypeRepository.search(pageRequest, requestData.getSearch().toUpperCase());
				}
			} else {

				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					userTypeList = userTypeRepository.findAll(PageRequest.of(requestData.getPageNo(),
							requestData.getPaginationSize(), Sort.by(Direction.ASC, requestData.getSortField())));
				} else {
					userTypeList = userTypeRepository.findAll(PageRequest.of(requestData.getPageNo(),
							requestData.getPaginationSize(), Sort.by(Direction.DESC, requestData.getSortField())));
				}
			}
			if (userTypeList.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setNumberOfElements(userTypeList.getNumberOfElements());
				response.setTotalRecords(userTypeList.getTotalElements());
				response.setTotalPages(userTypeList.getTotalPages());
				for (UserTypeMasterEntity userType : userTypeList) {
					contentList.add(userType);
				}
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setResponseContents(contentList);
				//log.info(" Successfully Get User Type Details");
			}

		} catch (Exception e) {
			log.error("<----- UserTypeDaoImpl.getLazyList() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- UserType getLazyList Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- UserType delete Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			UserTypeMasterEntity userType = userTypeRepository.getOne(id);
			if (userType != null) {
				userTypeRepository.delete(userType);
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
			log.error("<---- UserTypeDaoImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- UserType delete Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- UserType softDelete Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			UserTypeMasterEntity userType = userTypeRepository.getOne(id);
			if (userType != null) {
				userType.setStatus(false);
				userTypeRepository.save(userType);
				response.setMessage("Successfully Deleted");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- UserTypeDaoImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete UserType");
		}
		//log.info(" <----- UserType softDelete Dao END -----> ");
		return response;
	}

	private UserTypeMasterEntity updatedValues(UserTypeMasterEntity userTypeMaster, UserTypeDTO userTypeDTO) {
		UserTypeMasterEntity userType = userTypeMaster;
		userType.setDescription(userTypeDTO.getDescription());
		userType.setStatus(userTypeDTO.getStatus());
		userType.setUserType(userTypeDTO.getUserType());
		return userType;
	}

	@Override
	public UserTypeMasterEntity getByName(String name) {
		//log.info(" <----- UserType userType Dao STARTED -----> ");
			UserTypeMasterEntity userType = userTypeRepository.getByUserType(name);		
		//log.info(" <----- UserType userType Dao END -----> ");
		return userType;
	}
}
