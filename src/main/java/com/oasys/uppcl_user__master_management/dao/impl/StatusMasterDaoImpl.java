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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.StatusMasterDao;
import com.oasys.uppcl_user__master_management.dto.StatusMasterDTO;
import com.oasys.uppcl_user__master_management.entity.StatusMasterEntity;
import com.oasys.uppcl_user__master_management.repository.StatusMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Repository
public class StatusMasterDaoImpl implements StatusMasterDao{
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	StatusMasterRepository statusMasterRepository;
	
	@Autowired
	MessageSource messageSource;
	
	@Override
	public BaseDTO create(StatusMasterDTO statusMasterDTO) {
		//log.info(" <----- StatusMasterDaoImpl.create() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
	
			StatusMasterEntity statusMasterEntity = new StatusMasterEntity();
			if(statusMasterDTO.getStatusName() == null || statusMasterDTO.getStatusName().trim().isEmpty()) {
				//log.info("Status Name {} - Is Not Null - {} -Is Not Empty", statusMasterDTO.getStatusName());
				response.setMessage("Status Name is Required");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				return response;	
			}
			
			if(statusMasterDTO.getStatus()==null) {
				//log.info(" Status {} - Is Not Null", statusMasterDTO.getStatus());
				response.setMessage("Status is Required");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				return response;	
			}
			
				Optional<StatusMasterEntity> optional = statusMasterRepository.check(statusMasterDTO.getStatusName() );
						if(optional.isPresent()) {
							String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,Locale.US);
							response.setMessage(" Status Name is Already Exist");
							response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
							//log.info("Status Name is Already Exist",statusMasterDTO.getStatusName() );
						
						}else {
							statusMasterEntity.setStatusName(statusMasterDTO.getStatusName());
					
							statusMasterEntity.setStatus(statusMasterDTO.getStatus());
							statusMasterRepository.save(statusMasterEntity);
						String msg = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
						log.info("String Message"+msg);
						response.setMessage( statusMasterDTO.getStatusName() + " " + msg);
						response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
						log.info("Successfully Added",statusMasterDTO.getStatusName());
						}
			
		} catch (DataIntegrityViolationException e) {
			log.error("<---- StatusMasterDaoImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		 } catch (Exception e) {
			log.error("<---- StatusMasterDaoImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- StatusMasterDaoImpl.create() END -----> ");
		return response;
	}

	
	public BaseDTO getById(UUID id) {
		BaseDTO response = new BaseDTO();
		try {
			Optional<StatusMasterEntity> statusName = statusMasterRepository.findById(id);
			if (statusName.isPresent()) {
				//log.info("Status Name id -{}",id);
				response.setResponseContent(statusName);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get Status Name Details ");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- StatusMasterDaoImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg );
		}
		//log.info(" <----- StatusMasterDaoImpl.getById() END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAll() {
		BaseDTO response = new BaseDTO();
		try {
			List<StatusMasterEntity> statusName = statusMasterRepository.findAll();

			if (statusName.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setResponseContents(statusName);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				//log.info("Status Name Get All Details Fetched Successfully");
			}
		} catch (Exception e) {
			log.error("<----- StatusMasterDaoImpl.getAll() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- StatusMasterDaoImpl.getAll() END -----> ");
		return response;
	}

	
	public BaseDTO getLazyList(PaginationRequestDTO pageData) {
		//log.info("<== StatusMasterDaoImpl.getAllLazy() Started ==>");
				BaseDTO response = new BaseDTO();
				Page<StatusMasterEntity> statusMasterList = null;
				List<StatusMasterEntity> contentList = new ArrayList<StatusMasterEntity>();
				Pageable pageRequest;
				try {
					//log.info("<<<< ------- StatusMasterDaoImpl.getAllLazy() ---------- >>>>>>>");

					if (pageData.getSearch() != null) {
						if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
							pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
									Sort.by(Direction.ASC, pageData.getSortField()));
							statusMasterList = statusMasterRepository.search(pageRequest, pageData.getSearch());
						} else {
							pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
									Sort.by(Direction.DESC, pageData.getSortField()));
							statusMasterList = statusMasterRepository.search(pageRequest, pageData.getSearch());
						}
					} else {

						if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
							statusMasterList = statusMasterRepository.findAll(PageRequest.of(pageData.getPageNo(),
									pageData.getPaginationSize(), Sort.by(Direction.ASC, pageData.getSortField())));
						} else {
							statusMasterList = statusMasterRepository.findAll(PageRequest.of(pageData.getPageNo(),
									pageData.getPaginationSize(), Sort.by(Direction.DESC, pageData.getSortField())));
						}
					}
					if (statusMasterList != null) {
						response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
						response.setNumberOfElements(statusMasterList.getNumberOfElements());
						response.setTotalRecords(statusMasterList.getTotalElements());
						response.setTotalPages(statusMasterList.getTotalPages());
						for (StatusMasterEntity statusMaster : statusMasterList) {
							contentList.add(statusMaster);
						}
						response.setResponseContents(contentList);
						String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
						response.setMessage(msg);
						response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
						//log.info("Successfully Get Details Successfully");
					}
				} catch (Exception e) {
					log.error("<<<< ------- StatusMasterDaoImpl.getAllLazy() ---------- Exception>>>>", e);
					String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage(msg);
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
				}
				//log.info("<== StatusMasterDaoImpl.getAllLazy() Ended ==>");
				return response;
	}

	@Override
	public BaseDTO update(UUID id, StatusMasterDTO statusMasterDTO) {
		//log.info(" <----- StatusMasterDaoImpl.update() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			boolean check = false;
			
			Optional<StatusMasterEntity> optional = statusMasterRepository.findById(id);
			if (optional.isPresent()) {
				//log.info("Status Name id -{}",id);
				StatusMasterEntity statusMasterEntity = new StatusMasterEntity();
				statusMasterEntity = optional.get();
				List<StatusMasterEntity> checkCode = statusMasterRepository.findByName(statusMasterDTO.getStatusName());
				for (StatusMasterEntity entity : checkCode) {
					if (entity.getStatusName().equals(statusMasterDTO.getStatusName())) {
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
					response.setMessage(statusMasterDTO.getStatusName() + " " + msg  );
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.info("Status Name -{} Already Exists",statusMasterDTO.getStatusName());
				} else {
					statusMasterEntity.setStatusName(statusMasterDTO.getStatusName());
					statusMasterEntity.setStatus(statusMasterDTO.getStatus());
					StatusMasterEntity afterUpdate = statusMasterRepository.save(statusMasterEntity);
					response.setResponseContent(afterUpdate);
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(statusMasterDTO.getStatusName()+ " " + msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Status - {} Updated Successfully", statusMasterDTO.getStatusName());
				}
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- StatusMasterDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());

		} catch (Exception e) {
			log.error("<---- StatusMasterDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info(" <----- StatusMasterDaoImpl.update() END -----> ");
		return response;
	}

	
	public BaseDTO getAllActive() {
		//log.info("<------StatusMasterDaoImpl.getAllActive()------> Started");
				BaseDTO response = new BaseDTO();
				List<StatusMasterEntity> statusMasterEntity = null;
				try {
					statusMasterEntity = statusMasterRepository.getAllactive();
					if (statusMasterEntity != null) {
						response.setResponseContents(statusMasterEntity);
						String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
						response.setMessage(msg);
						response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
						//log.info(" Get All Active Details Fetched Successfully");
					} else {
						//log.error("No Records Found");
						response.setMessage("No Record Found");
						response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
					}
				} catch (Exception e) {
					log.error("<------StatusMasterDaoImpl.getAllActive()------> exception", e);
					String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage(msg);
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
				}
				//log.info("<------StatusMasterDaoImpl.getAllActive()------> Ended");
				return response;
	}
			
			

//	public BaseDTO getById(UUID id) {
		//log.info(" <----- StatusMasterDaoImpl.getById() STARTED -----> ");
//		BaseDTO response = new BaseDTO();
//		try {
//			Optional<StatusMasterEntity> statusName = statusMasterRepository.findById(id);
//			if (statusName.isPresent()) {
//				//log.info("Status Name id -{}",id);
//				response.setResponseContent(statusName);
//				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
//				response.setMessage(msg);
//				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
//				//log.info("Successfully Get Status Name Details ");
//			} else {
//				response.setMessage("No Record Found..");
//				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
//				//log.warn("No Record Found..");
//			}
//		} catch (Exception e) {
//			log.error("<---- StatusMasterDaoImpl.getById() ----> EXCEPTION", e);
//			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
//			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
//			response.setMessage(msg );
//		}
//		//log.info(" <----- StatusMasterDaoImpl.getById() END -----> ");
//		return response;
//	}
//
//	@Override
//	public BaseDTO getAll() {
//		//log.info(" <----- StatusMasterDaoImpl.getAll() STARTED -----> ");
//		BaseDTO response = new BaseDTO();
//		try {
//			List<StatusMasterEntity> statusName = statusMasterRepository.findAll();
//
//			if (statusName.isEmpty()) {
//				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
//				response.setMessage(msg);
//				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
//				//log.warn("No Record Found..");
//			} else {
//				response.setResponseContents(statusName);
//				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
//				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
//				response.setMessage(msg);
//				//log.info("Status Name Get All Details Fetched Successfully");
//			}
//		} catch (Exception e) {
//			log.error("<----- StatusMasterDaoImpl.getAll() ----->", e);
//			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
//			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
//			response.setMessage(msg);
//		}
//		//log.info(" <----- StatusMasterDaoImpl.getAll() END -----> ");
//		return response;
//	}
//
//	
//	public BaseDTO getLazyList(PaginationRequestDTO pageData) {
//		//log.info("<== StatusMasterDaoImpl.getAllLazy() Started ==>");
//		BaseDTO response = new BaseDTO();
//		Page<StatusMasterEntity> statusMasterList = null;
//		List<StatusMasterEntity> contentList = new ArrayList<StatusMasterEntity>();
//		Pageable pageRequest;
//		try {
//			//log.info("<<<< ------- StatusMasterDaoImpl.getAllLazy() ---------- >>>>>>>");
//
//			if (pageData.getSearch() != null) {
//				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
//					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
//							Sort.by(Direction.ASC, pageData.getSortField()));
//					statusMasterList = statusMasterRepository.search(pageRequest, pageData.getSearch());
//				} else {
//					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
//							Sort.by(Direction.DESC, pageData.getSortField()));
//					statusMasterList = statusMasterRepository.search(pageRequest, pageData.getSearch());
//				}
//			} else {
//
//				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
//					statusMasterList = statusMasterRepository.findAll(PageRequest.of(pageData.getPageNo(),
//							pageData.getPaginationSize(), Sort.by(Direction.ASC, pageData.getSortField())));
//				} else {
//					statusMasterList = statusMasterRepository.findAll(PageRequest.of(pageData.getPageNo(),
//							pageData.getPaginationSize(), Sort.by(Direction.DESC, pageData.getSortField())));
//				}
//			}
//			if (statusMasterList != null) {
//				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
//				response.setNumberOfElements(statusMasterList.getNumberOfElements());
//				response.setTotalRecords(statusMasterList.getTotalElements());
//				response.setTotalPages(statusMasterList.getTotalPages());
//				for (StatusMasterEntity statusMaster : statusMasterList) {
//					contentList.add(statusMaster);
//				}
//				response.setResponseContents(contentList);
//				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
//				response.setMessage(msg);
//				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
//				//log.info("Successfully Get Details Successfully");
//			}
//		} catch (Exception e) {
//			log.error("<<<< ------- StatusMasterDaoImpl.getAllLazy() ---------- Exception>>>>", e);
//			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
//			response.setMessage(msg);
//			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
//		}
//		//log.info("<== StatusMasterDaoImpl.getAllLazy() Ended ==>");
//		return response;
//	}
//
//	public BaseDTO getAllActive() {
//		//log.info("<------StatusMasterDaoImpl.getAllActive()------> Started");
//		BaseDTO response = new BaseDTO();
//		List<StatusMasterEntity> statusMasterEntity = null;
//		try {
//			statusMasterEntity = statusMasterRepository.getAllactive();
//			if (statusMasterEntity != null) {
//				response.setResponseContents(statusMasterEntity);
//				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
//				response.setMessage(msg);
//				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
//				//log.info(" Get All Active Details Fetched Successfully");
//			} else {
//				//log.error("No Records Found");
//				response.setMessage("No Record Found");
//				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
//			}
//		} catch (Exception e) {
//			log.error("<------StatusMasterDaoImpl.getAllActive()------> exception", e);
//			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
//			response.setMessage(msg);
//			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
//		}
//		//log.info("<------StatusMasterDaoImpl.getAllActive()------> Ended");
//		return response;
//	}
//	
//
//	public BaseDTO softDelete(UUID id) {
//		//log.info(" <----- StatusMasterDaoImpl softDelete  STARTED -----> ");
//		BaseDTO response = new BaseDTO();
//		try {
//			StatusMasterEntity master = statusMasterRepository.getOne(id);
//			if (master != null) {
//				master.setStatus(false);
//				statusMasterRepository.save(master);
//				response.setMessage("Successfully Deleted");
//				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
//				//log.info("Successfully Deleted");
//			} else {
//				response.setMessage("No Record Found..");
//				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
//				//log.warn("No Record Found..");
//			}
//		} catch (Exception e) {
//			log.error("<---- StatusMasterDaoImpl.softDelete() ----> EXCEPTION", e);
//			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
//			response.setMessage("Unable to Delete ");
//		}
//		//log.info(" <----- StatusMasterDaoImpl softDelete  END -----> ");
//		return response;
//	}
//
//	@Override
//	public BaseDTO update(UUID id, StatusMasterDTO statusMasterDTO) {
//		//log.info(" <----- StatusMasterDaoImpl.update() STARTED -----> ");
//		BaseDTO response = new BaseDTO();
//		try {
//			boolean check = false;
//			
//			Optional<StatusMasterEntity> optional = statusMasterRepository.findById(id);
//			if (optional.isPresent()) {
//				//log.info("Status Name id -{}",id);
//				StatusMasterEntity statusMasterEntity = new StatusMasterEntity();
//				statusMasterEntity = optional.get();
//				List<StatusMasterEntity> checkCode = statusMasterRepository.findByName(statusMasterDTO.getStatusName());
//				for (StatusMasterEntity entity : checkCode) {
//					if (entity.getStatusName().equals(statusMasterDTO.getStatusName())) {
//							if(id.equals(entity.getId())) {
//								check = false;
//							}else 
//						check = true;
//					} else {
//						check = false;
//					}
//				}
//
//				if (check) {
//					String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,Locale.US);
//					response.setMessage(statusMasterDTO.getStatusName() + " " + msg  );
//					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
//					//log.info("Status Name -{} Already Exists",statusMasterDTO.getStatusName());
//				} else {
//					statusMasterEntity.setStatusName(statusMasterDTO.getStatusName());
//					statusMasterEntity.setStatus(statusMasterDTO.getStatus());
//					StatusMasterEntity afterUpdate = statusMasterRepository.save(statusMasterEntity);
//					response.setResponseContent(afterUpdate);
//					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
//							Locale.US);
//					response.setMessage(statusMasterDTO.getStatusName()+ " " + msg);
//					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
//					//log.info("Status - {} Updated Successfully", statusMasterDTO.getStatusName());
//				}
//			} else {
//				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
//				response.setMessage(msg);
//				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
//				//log.warn("No Record Found..");
//			}
//		} catch (DataIntegrityViolationException e) {
//			log.error("<---- StatusMasterDaoImpl.update() ----> EXCEPTION", e);
//			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
//
//		} catch (Exception e) {
//			log.error("<---- StatusMasterDaoImpl.update() ----> EXCEPTION", e);
//			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
//		}
//		//log.info(" <----- StatusMasterDaoImpl.update() END -----> ");
//		return response;
//	}
//	

}
