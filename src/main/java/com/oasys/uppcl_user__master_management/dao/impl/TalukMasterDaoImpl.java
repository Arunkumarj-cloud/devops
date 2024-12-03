package com.oasys.uppcl_user__master_management.dao.impl;


import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.uppcl_user__master_management.dao.TalukMasterDao;
import com.oasys.uppcl_user__master_management.dto.TalukMasterDTO;
import com.oasys.uppcl_user__master_management.entity.TalukMasterEntity;
import com.oasys.uppcl_user__master_management.repository.DistrictMasterRepository;
import com.oasys.uppcl_user__master_management.repository.TalukMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class TalukMasterDaoImpl implements TalukMasterDao {
	
	@Autowired
	TalukMasterRepository talukMasterRepository;

	@Autowired
	MessageSource messageSource;

	@Autowired
	DistrictMasterRepository districtMasterRepository;

	@Override
	public BaseDTO create(TalukMasterDTO talukMasterDTO) {
		//log.info("<== Started TalukMasterDaoImpl.create() ==>");
		BaseDTO response = new BaseDTO();
		TalukMasterEntity talukMasterEntity = new TalukMasterEntity();
		boolean talukName = false, talukCode = false;
		try {

			if (districtMasterRepository.findById(talukMasterDTO.getDistrictId().getId()).isPresent()) {

				if (talukMasterDTO.getTalukCode() == null || talukMasterDTO.getTalukCode() == "") {

					Optional<TalukMasterEntity> optional3 = talukMasterRepository.checkDistrictIdandTalukName(
							talukMasterDTO.getDistrictId().getId(), talukMasterDTO.getTalukName());
					if (optional3.isPresent()) {
						String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
						response.setMessage("Taluk Name Is Already Exist:");
						response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
						//log.info("Taluk Name Is Already Exist", talukMasterDTO.getTalukName());

					} else {
						talukName = true;
					}

				} else {

					Optional<TalukMasterEntity> optional1 = talukMasterRepository.checkDistrictIdandTalukName(
							talukMasterDTO.getDistrictId().getId(), talukMasterDTO.getTalukName());
					if (optional1.isPresent()) {
						String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
						response.setMessage("Taluk Name Is Already Exist:");
						response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
						//log.info("Taluk Name Is Already Exist", talukMasterDTO.getTalukName());
						// talukName = true;
					} else {
						talukName = true;
					}

					Optional<TalukMasterEntity> optional2 = talukMasterRepository
							.checkCode(talukMasterDTO.getTalukCode());
					if (optional2.isPresent()) {
						String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
						response.setMessage("Taluk Code Is Already Exist:");
						response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
						//log.info("Taluk Code Is Already Exist", talukMasterDTO.getTalukCode());
						// talukCode = true;
					} else {
						talukCode = true;
					}

				}

				if (talukName && talukCode) {
					talukMasterEntity.setTalukName(talukMasterDTO.getTalukName());
					talukMasterEntity.setTalukCode(talukMasterDTO.getTalukCode());
					talukMasterEntity.setStatus(talukMasterDTO.getStatus());
					talukMasterEntity.setDistrictId(talukMasterDTO.getDistrictId());
					talukMasterRepository.save(talukMasterEntity);
					response.setMessage("Taluk Created Successfully");
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Created");
				} else if (talukName) {
					talukMasterEntity.setTalukName(talukMasterDTO.getTalukName());
					talukMasterEntity.setStatus(talukMasterDTO.getStatus());
					talukMasterEntity.setDistrictId(talukMasterDTO.getDistrictId());
					talukMasterRepository.save(talukMasterEntity);
					response.setMessage("Taluk Created Successfully");
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Created");
				}

			} else {
				response.setMessage("District Is Not Found");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("District Is Not Found");
			}

		} catch (Exception e) {
			log.error("<---- TalukMasterDaoImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <-----TalukMasterDaoImpl.create() Dao END -----> ");
		return response;

	}
	
	
	public BaseDTO getAll() {
		//log.info(" <----- TalukMasterDaoImpl.getAll() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<TalukMasterEntity> talukMasterEntity = talukMasterRepository.findAll();

			if (talukMasterEntity.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setResponseContents(talukMasterEntity);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				//log.info(" Get All Details Fetched Successfully");
			}
		} catch (Exception e) {
			log.error("<----- TalukMasterDaoImpl.getAll() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- TalukMasterDaoImpl.getAll() END -----> ");
		return response;
	}
	
	public BaseDTO update(UUID id, TalukMasterDTO talukMasterDTO) {
		//log.info(" <----- TalukMasterDaoImpl update Service STARTED -----> {} and {} ", id, talukMasterDTO);
		BaseDTO response = new BaseDTO();
		String message = "";
		TalukMasterEntity talukMasterEntity = new TalukMasterEntity();
		try {
			boolean talukName = false;
			boolean talukCode = false;
			Optional<TalukMasterEntity> talukMasterEntity1 = talukMasterRepository.findById(id);
			if (!talukMasterEntity1.isPresent()) {
				message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("No Record Found.. {} ", id);
				return response;
			}
			if (districtMasterRepository.findByDistrictId(talukMasterDTO.getDistrictId().getId()).isEmpty()) {
				message = messageSource.getMessage(ResponseConstant.EMPTY_DATA, null, Locale.US);
				response.setMessage("District is Not Found");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("District is Not Found- {} ", talukMasterDTO.getDistrictId().getId());
				return response;
			}

			if (talukMasterDTO.getTalukCode() == null || talukMasterDTO.getTalukCode() == "") {
				Optional<TalukMasterEntity> optional3 = talukMasterRepository.checkDistrictIdandTalukNameForUpdate(
						talukMasterDTO.getDistrictId().getId(),talukMasterDTO.getTalukName(), id);
				if (optional3.isPresent()) {
					response.setMessage("Taluk Name Is Already Exist:");
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.info("Taluk Name Is Already Exist", talukMasterDTO.getTalukCode());

				} else {
					talukName = true;
				}

			} else {

				Optional<TalukMasterEntity> optional1 = talukMasterRepository.checkDistrictIdandTalukNameForUpdate(
						talukMasterDTO.getDistrictId().getId(),talukMasterDTO.getTalukName(), id );
				if (optional1.isPresent()) {
					String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
					response.setMessage("Taluk Name Is Already Exist:");
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.info("Taluk Name Is Already Exist", talukMasterDTO.getTalukName());
				} else {
					talukName = true;
				}
				List<TalukMasterEntity> optional2 = talukMasterRepository.checkCodeUpdate(talukMasterDTO.getTalukCode(),
						id);
				if (!optional2.isEmpty()) {
					String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
					response.setMessage("Taluk Code Is Already Exist:");
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.info("Taluk Code Is Already Exist", talukMasterDTO.getTalukCode());
				} else {
					talukCode = true;
				}

			}
			if (response.getMessage() == null) {
				if (talukName && talukCode) {
					talukMasterEntity = talukMasterEntity1.get();
					talukMasterEntity.setTalukName(talukMasterDTO.getTalukName());
					talukMasterEntity.setTalukCode(talukMasterDTO.getTalukCode());
					talukMasterEntity.setStatus(talukMasterDTO.getStatus());
					talukMasterEntity.setDistrictId(talukMasterDTO.getDistrictId());
					talukMasterEntity = talukMasterRepository.save(talukMasterEntity);
					response.setMessage("Taluk Updated Successfully");
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info(" Updated Successfully");

				} else if (talukName) {
					talukMasterEntity = talukMasterEntity1.get();
					talukMasterEntity.setTalukName(talukMasterDTO.getTalukName());
					talukMasterEntity.setTalukCode(null);
					talukMasterEntity.setStatus(talukMasterDTO.getStatus());
					talukMasterEntity.setDistrictId(talukMasterDTO.getDistrictId());
					talukMasterRepository.save(talukMasterEntity);
					response.setMessage("Taluk Updated Successfully");
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Updated");
				}
			}
			
		} catch (DataIntegrityViolationException e) {
			log.error("<---- TalukMasterDaoImpl.update() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		} catch (Exception e) {
			log.error("<---- TalukMasterDaoImpl.update() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}
		//log.info(" <----- TalukMasterDaoImpl update Service END -----> ");
		return response;
	}
	
	public BaseDTO getById(UUID id) {
		//log.info(" <----- TalukMasterDaoImpl.getById() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			Optional<TalukMasterEntity> taluk = talukMasterRepository.findById(id);
			if (taluk.isPresent()) {
				//log.info(" Taluk id -{}", id);
				response.setResponseContent(taluk);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get  Details ");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- TalukMasterDaoImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- TalukMasterDaoImpl.getById() END -----> ");
		return response;
	}
	
	public BaseDTO getAllActive() {
		//log.info(" <----- TalukMasterDaoImpl.getAllActive() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<TalukMasterEntity> talukMasterEntity = talukMasterRepository.getAllActive();

			if (talukMasterEntity.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setResponseContents(talukMasterEntity);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,
						Locale.US);
				response.setMessage(msg);
				//log.info("Successfully Get All Active Details..");
			}
		} catch (Exception e) {
			log.error("<----- TalukMasterDaoImpl.getAllActive() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,
					Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- TalukMasterDaoImpl.getAllActive() END -----> ");
		return response;
	}
	
	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- TalukMasterDaoImpl.softDelete() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			TalukMasterEntity talukMasterEntity = talukMasterRepository.getOne(id);
			if (talukMasterEntity != null) {
				talukMasterEntity.setStatus(false);
				talukMasterRepository.save(talukMasterEntity);
				response.setMessage("Successfully Deleted");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- TalukMasterDaoImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Taluk");
		}
		//log.info(" <----- TalukMasterDaoImpl.softDelete() Service END -----> ");
		return response;
	}
	
	public BaseDTO getByDistrictId(UUID id) {
		//log.info(" <----- TalukMasterDaoImpl.getByDistrictId() Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			List<TalukMasterEntity> talukMasterEntity = talukMasterRepository.findByDistrictId(id);
			if (!talukMasterEntity.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				baseDTO.setResponseContents(talukMasterEntity);
				//log.info("Successfully Fetched");
			} else {
				baseDTO.setMessage("No Record Found..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- TalukMasterDaoImpl.getByDistrictId() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- TalukMasterDaoImpl.getByDistrictId() Dao END -----> ");
		return baseDTO;
	}
	
	public Page<TalukMasterEntity> getAllWithPage(Pageable pageable) {
		Page<TalukMasterEntity> page = talukMasterRepository.getAllWithPage(pageable);
		return page;
	}

	
	public Page<TalukMasterEntity> getAllWithPageAndSearch(Pageable pageable, String search) {
		Page<TalukMasterEntity> page = talukMasterRepository.getAllWithPageAndSearch(pageable, search.toUpperCase());
		return page;
	}

	
	public Page<TalukMasterEntity> getAllWithPageAndTime(Pageable pageable, Date fromDate, Date toDate) {
		Page<TalukMasterEntity> page = talukMasterRepository.getAllWithPageAndTime(pageable, fromDate, toDate);
		return page;
	}

	
	public Page<TalukMasterEntity> getAllWithPageAndSearchAndTime(Pageable pageable, String search, Date fromDate,
			Date toDate) {
		Page<TalukMasterEntity> page = talukMasterRepository.getAllWithPageAndSearchAndTime(pageable,
				search.toUpperCase(), fromDate, toDate);
		return page;
	}

	@Override
	public Integer getCount() {
		long count = talukMasterRepository.count();
		return (int) count;
	}

	

}
