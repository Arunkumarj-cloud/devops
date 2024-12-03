package com.oasys.uppcl_user__master_management.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
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

import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.GenderMasterDao;
import com.oasys.uppcl_user__master_management.dto.GenderMasterDTO;
import com.oasys.uppcl_user__master_management.entity.EducationEntity;
import com.oasys.uppcl_user__master_management.entity.GenderMasterEntity;
import com.oasys.uppcl_user__master_management.entity.GenderResponse;
import com.oasys.uppcl_user__master_management.repository.GenderMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;
@Repository
@Log4j2
public class GenderMasterDaoImpl implements GenderMasterDao{
	
	@Autowired
	GenderMasterRepository genderMasterRepository;
	
	@Autowired
	MessageSource messageSource;

	@Override
	public BaseDTO create(GenderMasterDTO genderMasterDTO) {
		//log.info("<== Started GenderMasterDaoImpl.create() ==>");
		BaseDTO response = new BaseDTO();
		GenderMasterEntity genderMasterEntity= null;
		
		try {
	
			            genderMasterEntity = genderMasterRepository.findByNameIgnoreCase(genderMasterDTO.getName().toUpperCase());
						if(genderMasterEntity != null) {
							String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,Locale.US);
							response.setMessage("This Gender " +msg);
							response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
							//log.info("Gender is Already Exist",genderMasterDTO.getName() );
							
						}else {
							genderMasterEntity = new  GenderMasterEntity();
							genderMasterEntity.setName(genderMasterDTO.getName());
							genderMasterEntity.setStatus(genderMasterDTO.getStatus());
							genderMasterEntity.setRemarks(genderMasterDTO.getRemarks());
							genderMasterRepository.save(genderMasterEntity);
							String msg = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
							response.setMessage( genderMasterDTO.getName() + " " + msg);
							response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
							//log.info("Successfully Added",genderMasterDTO.getName());
							}
			
		}  catch(DataIntegrityViolationException e) {
			log.error("<-------- GenderMasterDaoImpl.create() exception-------->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}catch (Exception e) {
			log.error("<-------- GenderMasterDaoImpl.create() exception-------->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<== Ended GenderMasterDaoImpl.create() ==>");
		return response;
	}

			
   @Override
	public BaseDTO getById(UUID id) {
		//log.info("<==== Started GenderMasterDaoImpl.getById() ===> ");
		BaseDTO response = new BaseDTO();
		GenderMasterEntity master = new GenderMasterEntity();
		try {
			master = genderMasterRepository.getOne(id);
			if (master.getId() != null) {
				response.setResponseContent(master);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info(" Get Details Fetched Successfully");
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("No Record Found");
			}
		} catch (Exception e) {
			log.error("<------GenderMasterDaoImpl.getById()------> Exception", e);
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<==== Ended GenderMasterDaoImpl.getById() ===> ");
		return response;
	}

	@Override
	public BaseDTO update(UUID id, GenderMasterDTO genderMasterDTO) {
		//log.info(" <----- GenderMasterDaoImpl.update() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			boolean check = false;
			
			Optional<GenderMasterEntity> optional = genderMasterRepository.findById(id);
			if (optional.isPresent()) {
				//log.info("Gender Name id -{}",id);
				GenderMasterEntity genderMasterEntity = new GenderMasterEntity();
				genderMasterEntity = optional.get();
				List<GenderMasterEntity> checkCode = genderMasterRepository.findByName(genderMasterDTO.getName());
				for (GenderMasterEntity entity : checkCode) {
					if (entity.getName().equalsIgnoreCase(genderMasterDTO.getName())) {
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
					response.setMessage("This Gender " + msg);
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.info("Gender -{} Already Exists",genderMasterDTO.getName());
				} else {

					genderMasterEntity.setName(genderMasterDTO.getName());
					genderMasterEntity.setStatus(genderMasterDTO.getStatus());
					genderMasterEntity.setRemarks(genderMasterDTO.getRemarks());
					GenderMasterEntity afterUpdate = genderMasterRepository.save(genderMasterEntity);
					response.setResponseContent(afterUpdate);
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(genderMasterDTO.getName()+ " " + msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					/*log.info("Gender - {} Updated Successfully", genderMasterDTO.getName()
							);*/
				}
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- GenderMasterDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());

		} catch (Exception e) {
			log.error("<---- GenderMasterDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info(" <----- GenderMasterDaoImpl.update() END -----> ");
		return response;
	}
		
	
   @Override
	public BaseDTO getAll() {
		//log.info("<------GenderMasterDaoImpl.getAll()------> Started");
		BaseDTO response = new BaseDTO();
		List<GenderMasterEntity> genderTypeMaster = null;
		try {
			genderTypeMaster = genderMasterRepository.findAll();
			if (genderTypeMaster != null) {
				response.setResponseContents(genderTypeMaster);
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Get All Details Fetched Successfully");
			} else {
				//log.error("No Record Found");
				response.setMessage("No record Found");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<------GenderMasterDaoImpl.getAll()------> exception", e);
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<------GenderMasterDaoImpl.getAll()------> Ended");
		return response;
	}

	public BaseDTO getLazyList(PaginationRequestDTO pageData) {
		//log.info("<== GenderMasterDaoImpl.getAllLazy() Started ==>");
		BaseDTO response = new BaseDTO();
		Page<GenderMasterEntity> genderMasterEntity = null;
		List<GenderMasterEntity> contentList = new ArrayList<GenderMasterEntity>();
		Pageable pageRequest;
		try {
			//log.info("<<<< ------- GenderMasterDaoImpl.getAllLazy() ---------- >>>>>>>");

			if (pageData.getSearch() != null) {
				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.ASC, pageData.getSortField()));
					genderMasterEntity = genderMasterRepository.search(pageRequest, pageData.getSearch().toUpperCase());
				} else {
					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.DESC, pageData.getSortField()));
					genderMasterEntity = genderMasterRepository.search(pageRequest, pageData.getSearch().toUpperCase());
				}
			} else {

				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					genderMasterEntity = genderMasterRepository.findAll(PageRequest.of(pageData.getPageNo(),
							pageData.getPaginationSize(), Sort.by(Direction.ASC, pageData.getSortField())));
				} else {
					genderMasterEntity = genderMasterRepository.findAll(PageRequest.of(pageData.getPageNo(),
							pageData.getPaginationSize(), Sort.by(Direction.DESC, pageData.getSortField())));
				}
			}
			if (genderMasterEntity != null) {
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				response.setNumberOfElements(genderMasterEntity.getNumberOfElements());
				response.setTotalRecords(genderMasterEntity.getTotalElements());
				response.setTotalPages(genderMasterEntity.getTotalPages());
				for (GenderMasterEntity genderList : genderMasterEntity) {
					contentList.add(genderList);
				}
				response.setResponseContents(contentList);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info(" Get Details Fetched Successfully");
			}
		} catch (Exception e) {
			log.error("<<<< ------- GenderMasterDaoImpl.getAllLazy() ---------- Exception>>>>", e);
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<== GenderMasterDaoImpl.getAllLazy() Ended ==>");
		return response;
	}


	@Override
	public BaseDTO getAllActive() {
		//log.info(" <----- GenderMasterDaoImpl.getAllActive() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		List<GenderResponse> genderResponse11 = new ArrayList<>();
		try {
			List<GenderMasterEntity> genderMasterEntity = genderMasterRepository.getAllActive();
			int index = 1;
			for(GenderMasterEntity genderMasterEntity1:genderMasterEntity) {
				GenderResponse genderResponse = new GenderResponse();
				genderResponse.setId(index);
				genderResponse.setName(genderMasterEntity1.getName());
				genderResponse.setStatus(genderMasterEntity1.getStatus());
				index++;
				genderResponse11.add(genderResponse);
			}
			

			if (genderMasterEntity.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg );
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setResponseContents(genderResponse11);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				log.info(" Get All Active Details Fetched Successfully..");
			}
		} catch (Exception e) {
			log.error("<----- GenderMasterDaoImpl.getAllActive() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- GenderMasterDaoImpl.getAllActive() END -----> ");
		return response;
	}


}
