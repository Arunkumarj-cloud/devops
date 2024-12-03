package com.oasys.uppcl_user__master_management.service.impl;


import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.TalukMasterDao;
import com.oasys.uppcl_user__master_management.dao.VillageMasterDAO;
import com.oasys.uppcl_user__master_management.dto.VillageMasterDTO;
import com.oasys.uppcl_user__master_management.entity.TalukMasterEntity;
import com.oasys.uppcl_user__master_management.entity.VillageMasterEntity;
import com.oasys.uppcl_user__master_management.repository.TalukMasterRepository;
import com.oasys.uppcl_user__master_management.repository.VillageMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.VillageMasterService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class VillageMasterServiceImpl implements VillageMasterService {

	@Autowired
	VillageMasterDAO villageDAO;

	@Autowired
	TalukMasterRepository talukMasterRepository;

	@Autowired
	TalukMasterDao talukMasterDao;

	@Autowired
	MessageSource messageSource;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	VillageMasterRepository villageMasterRepository;

	@Override
	public BaseDTO create(VillageMasterDTO villageDTO) {
		//log.info(" <----- VillageMaster create Service STARTED -----> {} ", villageDTO);
		BaseDTO response = new BaseDTO();
		String message = "";
		try {
			VillageMasterEntity entity = new VillageMasterEntity();

			boolean villageName = false, code = false;
			Optional<TalukMasterEntity> optional = talukMasterRepository.findById(villageDTO.getTalukId().getId());
			if (!optional.isPresent()) {
				//log.info(" Taluk Id Not found");
				message = messageSource.getMessage(ResponseConstant.EMPTY_DATA, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				return response;
			}

			if (villageDTO.getCode() == null || villageDTO.getCode() == "") {

				Optional<VillageMasterEntity> optional1 = villageMasterRepository
						.findByTalukIdandVillageName(villageDTO.getTalukId().getId(), villageDTO.getVillageName());
				if (optional1.isPresent()) {
					//log.error(" Village Name Already Exist");
					response.setMessage("Village Name Already Exist");
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				} else {
					villageName = true;
				}

			} else {
				Optional<VillageMasterEntity> optional2 = villageMasterRepository
						.findByTalukIdandVillageName(villageDTO.getTalukId().getId(), villageDTO.getVillageName());
				if (optional2.isPresent()) {
					//log.error(" Village Name Already Exist");
					response.setMessage("Village Name Already Exist");
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());

				} else {
					villageName = true;
				}

				Optional<VillageMasterEntity> optional3 = villageMasterRepository
						.findByVillageCode(villageDTO.getCode());
				if (optional3.isPresent()) {
					String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
					response.setMessage("village Code Is Already Exist:");
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.info("Village Code Is Already Exist", villageDTO.getCode());
				} else {
					code = true;
				}

			}

			if (villageName && code) {
				entity.setTalukId(villageDTO.getTalukId());
				entity.setStatus(villageDTO.getStatus());
				entity.setCode(villageDTO.getCode());
				entity.setVillageName(villageDTO.getVillageName());
				villageMasterRepository.save(entity);
				response.setMessage("Village Created Successfully");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Created");

			}
			if (villageName) {
				entity.setTalukId(villageDTO.getTalukId());
				entity.setStatus(villageDTO.getStatus());
				entity.setVillageName(villageDTO.getVillageName());
				villageMasterRepository.save(entity);
				response.setMessage("Village Created Successfully");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Created");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- VillageMasterServiceImpl.create() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		} catch (Exception e) {
			log.error("<---- VillageMasterServiceImpl.create() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}
		//log.info(" <----- Village create Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAll() {
		//log.info(" <----- village Master getAll Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = villageDAO.getAll();
		} catch (Exception e) {
			log.error("<---- VillageMasterServiceImpl.getAll() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);

		}
		//log.info(" <----- Village getAll Service END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO getAllActive() {
		//log.info(" <----- Village getAllActive Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = villageDAO.getAllActive();
		} catch (Exception e) {
			log.error("<---- VillageMasterServiceImpl.getAllActive() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,
					Locale.US);
			baseDTO.setMessage(msg);

		}
		//log.info(" <----- VillageMaster getAllActive Service END -----> ");
		return baseDTO;
	}

	@SuppressWarnings("unused")
	@Override
	public BaseDTO update(UUID id, VillageMasterDTO villageDTO) {
		//log.info(" <----- VillageMaster update Service STARTED -----> {} and {} ", id, villageDTO);
		BaseDTO response = new BaseDTO();
		String message = "";
		try {
			VillageMasterEntity villageMasterEntity = new VillageMasterEntity();
			boolean villageName = false;
			boolean code = false;
			Optional<VillageMasterEntity> villageMaster = villageDAO.getById(id);
			if (!villageMaster.isPresent()) {
				message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("No Record Found.. {} ", id);
				return response;
			} 
			if (talukMasterRepository.findByTalukId(villageDTO.getTalukId().getId()).isEmpty()) {
				message = messageSource.getMessage(ResponseConstant.EMPTY_DATA, null, Locale.US);
				response.setMessage("Taluk Id Not Found");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("Taluk Id Not Found- {} ", villageDTO.getTalukId().getId());
				return response;
			}

			if (villageDTO.getCode() == null) {

				Optional<VillageMasterEntity> optional3 = villageMasterRepository.checkTalukIdandVillageNameForUpdate(villageDTO.getTalukId().getId(),
						villageDTO.getVillageName(), id);
				if (optional3.isPresent()) {
					response.setMessage("Village Name Is Already Exist:");
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.info("Village Name Is Already Exist", villageDTO.getCode());
				} else {
					villageName = true;
				}

			} else {

				Optional<VillageMasterEntity> optional1 = villageMasterRepository.checkTalukIdandVillageNameForUpdate(
						villageDTO.getTalukId().getId(), villageDTO.getVillageName(),id);
				if (optional1.isPresent()) {
					String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
					response.setMessage("Village Name Is Already Exist:");
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.info("Village Name Is Already Exist", villageDTO.getVillageName());

				} else {
					villageName = true;
				}

				List<VillageMasterEntity> optional2 = villageMasterRepository.checkCodeUpdate(villageDTO.getCode(), id);
				if (!optional2.isEmpty()) {
					String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
					response.setMessage("Village Code Is Already Exist:");
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.info("Village Code Is Already Exist", villageDTO.getCode());
				} else {
					code = true;
				}

			}

			if(response.getMessage() == null) {
				if (villageName && code) {
					villageMasterEntity = villageMaster.get();
					villageMasterEntity.setVillageName(villageDTO.getVillageName());
					villageMasterEntity.setStatus(villageDTO.getStatus());
					villageMasterEntity.setTalukId(villageDTO.getTalukId());
					villageMasterEntity.setCode(villageDTO.getCode());
					villageMasterRepository.save(villageMasterEntity);
					message = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
					response.setMessage(message);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Updated successfully");

				} else if (villageName) {
					villageMasterEntity = villageMaster.get();
					villageMasterEntity.setVillageName(villageDTO.getVillageName());
					villageMasterEntity.setCode(null);
					villageMasterEntity.setStatus(villageDTO.getStatus());
					villageMasterEntity.setTalukId(villageDTO.getTalukId());
					villageMasterRepository.save(villageMasterEntity);
					message = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
					response.setMessage(message);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Updated successfully");

				}
			}

		} catch (DataIntegrityViolationException e) {
			log.error("<---- VillageMasterServiceImpl.update() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		} catch (Exception e) {
			log.error("<---- VillageMasterServiceImpl.update() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}
		//log.info(" <----- VillageMaster update Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- VillageMaster getById Service STARTED -----> {} ", id);
		BaseDTO baseDTO = new BaseDTO();
		String message = "";
		try {
			Optional<VillageMasterEntity> villageMaster = villageDAO.getById(id);
			if (villageMaster.isPresent()) {
				//log.info("Village Master Id {}", id);
				VillageMasterDTO villageDTO = new VillageMasterDTO();
				villageDTO = objectMapper.convertValue(villageMaster.get(), VillageMasterDTO.class);
				baseDTO.setResponseContent(villageDTO);
				message = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				baseDTO.setMessage(message);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Fetched");
			} else {
				message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				baseDTO.setMessage(message);
				baseDTO.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- VillageMasterServiceImpl.getById() ----> {} ", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(message);
		}
		//log.info(" <----- VillageMaster getById Service END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- VillageMaster delete Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = villageDAO.delete(id);
		} catch (Exception e) {
			log.error("<---- VillageMasterServiceImpl.delete() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);

		}
		//log.info(" <----- VillageMaster delete Service END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- VillageMaster getLazyList Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = villageDAO.getLazyList(requestData);
		} catch (Exception e) {
			log.error("<---- VillageMasterServiceImpl.getLazyList() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);

		}
		//log.info(" <----- VillageMaster getLazyList Service END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- VillageMaster delete Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = villageDAO.softDelete(id);
		} catch (Exception e) {
			log.error("<---- VillageMasterServiceImpl.delete() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			baseDTO.setMessage("Unable to Delete VillageMaster");
		}
		//log.info(" <----- VillageMaster delete Service END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO getByTalukId(UUID id) {
		//log.info(" <----- TalukMasterServiceImpl.getByTalukId() Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = villageDAO.getByTalukId(id);
		} catch (Exception e) {
			log.error("<---- TalukMasterServiceImpl.getByTalukId() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- TalukMasterServiceImpl.getByTalukId() Service END -----> ");
		return baseDTO;
	}

}
