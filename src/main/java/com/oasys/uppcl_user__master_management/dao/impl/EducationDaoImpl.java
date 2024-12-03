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
import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.EducationDao;
import com.oasys.uppcl_user__master_management.dto.EducationDTO;
import com.oasys.uppcl_user__master_management.entity.EducationEntity;
import com.oasys.uppcl_user__master_management.repository.EducationRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;
@Service
@Log4j2
public class EducationDaoImpl implements EducationDao {
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	EducationRepository educationRepository;
	
	
	public BaseDTO create(EducationDTO educationDTO) {
		//log.info(" <----- EducationDaoImpl.create() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		
		EducationEntity  educationEntity = null;
		try {
			
			//Optional<EducationEntity> optional = educationRepository.findByEducationName(educationDTO.getName());
		     educationEntity = educationRepository.findByNameIgnoreCase(educationDTO.getName());
			if(educationEntity != null) {
				String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,Locale.US);
				response.setMessage("This Education Name is Already Exist.");
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				//log.info("Education Name is Already Exist",educationDTO.getName() );
				
			}else {
			educationEntity = new EducationEntity();
			educationEntity.setName(educationDTO.getName());
			educationEntity.setStatus(educationDTO.getStatus());
			educationEntity.setRemarks(educationDTO.getRemarks());
			educationRepository.save(educationEntity);
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			//log.info("Successfully Added..");
			}
		} catch (Exception e) {
			log.error("<---- EducationDaoImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- EducationDaoImpl.create() END -----> ");
		return response;
	}

	

	@Override
		public BaseDTO getAllActive() {
			//log.info(" <----- EducationDaoImpl.getAllActive() STARTED -----> ");
			BaseDTO response = new BaseDTO();
			try {
				List<EducationEntity> educationEntity = educationRepository.getAllActive();

				if (educationEntity.isEmpty()) {
					String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
					response.setMessage(msg );
					response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
					//log.warn("No Record Found..");
				} else {
					response.setResponseContents(educationEntity);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage(msg);
					//log.info(" Get All Active Education Details Fetched Successfully..");
				}
			} catch (Exception e) {
				log.error("<----- EducationDaoImpl.getAllActive() ----->", e);
				response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
			}
			//log.info(" <----- EducationDaoImpl.getAllActive() END -----> ");
			return response;
		}
		
		
	@Override
	

	public BaseDTO getLazyList(PaginationRequestDTO pageData) {
		//log.info("<== EducationDaoImpl.getAllLazy() Started ==>");
		BaseDTO response = new BaseDTO();
		Page<EducationEntity> educationEntity = null;
		List<EducationEntity> contentList = new ArrayList<EducationEntity>();
		Pageable pageRequest;
		try {
			//log.info("<<<< ------- EducationDaoImpl.getAllLazy() ---------- >>>>>>>");

			if (pageData.getSearch() != null) {
				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.ASC, pageData.getSortField()));
					educationEntity = educationRepository.search(pageRequest, pageData.getSearch().toUpperCase());
				} else {
					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.DESC, pageData.getSortField()));
					educationEntity = educationRepository.search(pageRequest, pageData.getSearch().toUpperCase());
				}
			} else {

				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					educationEntity = educationRepository.findAll(PageRequest.of(pageData.getPageNo(),
							pageData.getPaginationSize(), Sort.by(Direction.ASC, pageData.getSortField())));
				} else {
					educationEntity = educationRepository.findAll(PageRequest.of(pageData.getPageNo(),
							pageData.getPaginationSize(), Sort.by(Direction.DESC, pageData.getSortField())));
				}
			}
			if (educationEntity != null) {
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				response.setNumberOfElements(educationEntity.getNumberOfElements());
				response.setTotalRecords(educationEntity.getTotalElements());
				response.setTotalPages(educationEntity.getTotalPages());
				for (EducationEntity educationList : educationEntity) {
					contentList.add(educationList);
				}
				response.setResponseContents(contentList);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info(" Get Details Fetched Successfully");
			}
		} catch (Exception e) {
			log.error("<<<< ------- EducationDaoImpl.getAllLazy() ---------- Exception>>>>", e);
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<== EducationDaoImpl.getAllLazy() Ended ==>");
		return response;
	}
	@Override
	public BaseDTO update(UUID id, EducationDTO educationDTO) {
		//log.info(" <----- EducationDaoImpl.update() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			boolean check = false;
			
			Optional<EducationEntity> optional = educationRepository.findById(id);
			if (optional.isPresent()) {
				//log.info("Education Name id -{}",id);
				EducationEntity educationEntity = new EducationEntity();
				educationEntity = optional.get();
				List<EducationEntity> checkCode = educationRepository.findByName(educationDTO.getName());
				for (EducationEntity entity : checkCode) {
					if (entity.getName().equalsIgnoreCase(educationDTO.getName())) {
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
					response.setMessage("This education name is already exist.");
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.info(" Education Name is -{} Already Exists",educationDTO.getName());
				} else {
					educationEntity.setName(educationDTO.getName());
					educationEntity.setStatus(educationDTO.getStatus());
					educationEntity.setRemarks(educationDTO.getRemarks());
					EducationEntity afterUpdate = educationRepository.save(educationEntity);
					response.setResponseContent(afterUpdate);
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(educationDTO.getName()+ " " + msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Education - {} Updated Successfully", educationDTO.getName());
				}
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				log.warn("No Record Found..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- EducationDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());

		} catch (Exception e) {
			log.error("<---- EducationDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info(" <----- EducationDaoImpl.update() END -----> ");
		return response;
	}
	
	

	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- EducationDaoImpl.getById() STARTED -----> ");
				BaseDTO response = new BaseDTO();
				try {
					Optional<EducationEntity> educationEntity = educationRepository.findById(id);
					if (educationEntity.isPresent()) {
						//log.info("Education Name id -{}",id);
						response.setResponseContent(educationEntity);
						String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
						response.setMessage(msg);
						response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
						//log.info("Successfully Get Education Details ");
					} else {
						response.setMessage("No Record Found..");
						response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
						//log.warn("No Record Found..");
					}
				} catch (Exception e) {
					log.error("<---- EducationDaoImpl.getById() ----> EXCEPTION", e);
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage(msg );
				}
				//log.info(" <----- EducationDaoImpl.getById() END -----> ");
				return response;
			}
			

	@Override
	public BaseDTO getAll() {
		//log.info(" <----- EducationDaoImpl.getAll() STARTED -----> ");
				BaseDTO response = new BaseDTO();
				try {
					List<EducationEntity> education = educationRepository.findAll();

					if (education.isEmpty()) {
						String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
						response.setMessage(msg);
						response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
						//log.warn("No Record Found..");
					} else {
						response.setResponseContents(education);
						response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
						String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
						response.setMessage(msg);
						//log.info("Get All Details Fetched Successfully");
					}
				} catch (Exception e) {
					log.error("<----- EducationDaoImpl.getAll() ----->", e);
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage(msg);
				}
				//log.info(" <----- EducationDaoImpl.getAll() END -----> ");
				return response;
			}
	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- EducationDaoImpl softDelete  STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			EducationEntity master = educationRepository.getOne(id);
			if (master != null) {
				master.setStatus(false);
				educationRepository.save(master);
				response.setMessage("Successfully Deleted");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- EducationDaoImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete ");
		}
		//log.info(" <----- EducationDaoImpl softDelete  END -----> ");
		return response;
	}

}
