package com.oasys.uppcl_user__master_management.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.ComplaintReasonDao;
import com.oasys.uppcl_user__master_management.dto.ComplaintReasonDTO;
import com.oasys.uppcl_user__master_management.entity.ComplaintReasonEntity;
import com.oasys.uppcl_user__master_management.repository.ComplaintReasonRepository;
import com.oasys.uppcl_user__master_management.repository.ReasonTypeRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.ComplaintReasonService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ComplaintReasonServiceImpl implements ComplaintReasonService {

	@Autowired
	ComplaintReasonDao complaintReasonDao;

	@Autowired
	ComplaintReasonRepository complaintReasonRepository;

	@Autowired
	ReasonTypeRepository reasonTypeRepository;

	@Autowired
	ObjectMapper mapper;

	@Autowired
	MessageSource messageSource;

	@Override
	public BaseDTO create(ComplaintReasonDTO complaintReasonDTO) {
		//log.info("<===Started ComplaintReasonServiceImpl.create() ===>");
		BaseDTO response = new BaseDTO();
		ComplaintReasonEntity entity = new ComplaintReasonEntity();
		try {
			if (complaintReasonDTO != null) {
				List<ComplaintReasonEntity> list = complaintReasonRepository
						.checkCode(complaintReasonDTO.getReasonCode());
				if (list.isEmpty()) {
					if (complaintReasonDTO.getReasonCode() != null) {
						//log.info("Reason code - Is Not Null..",complaintReasonDTO.getReasonCode());
						if (complaintReasonDTO.getReasonCode() != "") {
							//log.info("Reason Code - Is Not empty..",complaintReasonDTO.getReasonCode());
							//if (validateReasonCode(complaintReasonDTO.getReasonCode())) {
								//log.info("Reason code  - Is Validate..",complaintReasonDTO.getReasonCode());
								if (complaintReasonDTO.getReasonName() != null) {
									//log.info("Reason name  - Is Not Null..",complaintReasonDTO.getReasonName());
									if (complaintReasonDTO.getReasonName() != "") {
										//log.info("Reason name  - Is Not Empty..",complaintReasonDTO.getReasonName());
										if (complaintReasonDTO.getStatus() != null) {
											//log.info("status  - Is Not Null..",complaintReasonDTO.getStatus());
											if (reasonTypeRepository.findById(complaintReasonDTO.getReasonTypeId().getId()).isPresent()) {			
												ComplaintReasonEntity complaintReasonEntity = mapper
														.convertValue(complaintReasonDTO, ComplaintReasonEntity.class);
												
												entity.setReasonCode(complaintReasonDTO.getReasonCode());
												entity.setReasonName(complaintReasonDTO.getReasonName());
												entity.setReasonTypeId(complaintReasonDTO.getReasonTypeId());
												entity.setStatus(complaintReasonDTO.getStatus());
												complaintReasonRepository.save(entity);
												response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
												String msg = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
												response.setMessage(msg);
												
											} else {
												response.setMessage("Reason Type Does Not Exist");
												response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
												//log.warn("Reason Type Does Not Exist");
											}
										} else {
											response.setMessage("Status Required");
											response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
											//log.warn("Status Required");
										}
									} else {
										response.setMessage("Reason Name is Empty");
										response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
										//log.warn("Reason Name is Empty");
									}
								} else {
									response.setMessage("Reason Name Required");
									response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
									//log.warn("Reason Name Required");
								}
							/*} else {
								response.setMessage("Complaint Reason Code is Not Valid");
								response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
								//log.warn("Complaint Reason Code is Not Valid");
							}*/
						} else {
							response.setMessage("Reason Code is empty");
							response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
							//log.warn("Reason Code is empty");
						}
					} else {
						response.setMessage("Reason Code Required");
						response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
						//log.warn("Reason Code Required");
					}
				} else {
					String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS,null, Locale.US);
					response.setMessage( complaintReasonDTO.getReasonCode() + " " + msg);
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.warn("Reason Code - {} Already Exist",complaintReasonDTO.getReasonCode());
				}
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			log.error("Excetion in ComplaintReasonServiceImpl.create():" + e);
		}
		//log.info("<=== Ended ComplaintReasonServiceImpl.create() ===>");
		return response;
	}

	private boolean validateReasonCode(String reasonCode) {
		//log.info(" <----- ComplaintReasonServiceImpl.validateReasonCode() STARTED -----> ");
		boolean flag = false;
		try {
			if (reasonCode.matches("[A-Za-z0-9]{1,5}+")) {
				flag = true;
				//log.info(" <-- ComplaintReasonServiceImpl.validateReasonCode() reasonCode is valid --> ");
			} else {
				flag = false;
				//log.warn(" <-- ComplaintReasonServiceImpl.validateReasonCode() reasonCode is not valid --> ");
			}
		} catch (Exception e) {
			log.error("<---- ComplaintReasonServiceImpl.validateReasonCode() ----> EXCEPTION", e);
		}
		//log.info(" <----- ComplaintReasonServiceImpl.validateReasonCode() ENDED -----> ");
		return flag;
	}

	@Override
	public BaseDTO getAll() {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started ComplaintReasonServiceImpl.getAll()===>");
		List<ComplaintReasonDTO> complaintMasterDTO = new ArrayList<ComplaintReasonDTO>();
		try {
			List<ComplaintReasonEntity> complaintReasonEntity = complaintReasonDao.getAll();
			for (ComplaintReasonEntity complaint : complaintReasonEntity) {
				ComplaintReasonDTO complaintMaster = mapper.convertValue(complaint, ComplaintReasonDTO.class);
				complaintMasterDTO.add(complaintMaster);
			}
			if (complaintReasonEntity.isEmpty()) {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setResponseContent(complaintReasonEntity);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE,null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get complaint Details..");
			}
		} catch (Exception e) {
			log.error("<---- SubscriptionMasterServiceImpl.getAll() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE,null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- ComplaintReasonServiceImpl.getAll() ENDED -----> ");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started ComplaintReasonServiceImpl.getAllActive()===>");
		List<ComplaintReasonDTO> complaintMasterDTO = new ArrayList<ComplaintReasonDTO>();
		try {
			List<ComplaintReasonEntity> complaintReasonEntity = complaintReasonDao.getAllActive();
			for (ComplaintReasonEntity complaint : complaintReasonEntity) {
				ComplaintReasonDTO complaintMaster = mapper.convertValue(complaint, ComplaintReasonDTO.class);
				complaintMasterDTO.add(complaintMaster);
			}
			if (complaintReasonEntity.isEmpty()) {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setResponseContent(complaintReasonEntity);
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE,null, Locale.US);
				response.setMessage( msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get  All Active Complaint Details..");
			}
		} catch (Exception e) {
			log.error("<---- ComplaintReasonServiceImpl.getAllActive() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE,null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- ComplaintReasonServiceImpl getAllActive ENDED -----> ");
		return response;
	}


	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started ComplaintReasonServiceImpl.getAllLazy()===>");
		List<ComplaintReasonDTO> ComplaintReasonDTO = new ArrayList<ComplaintReasonDTO>();
		try {
			Page<ComplaintReasonEntity> complaintReasonEntity = complaintReasonDao.getLazyList(requestData);
			if (complaintReasonEntity.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND,null, Locale.US);
				response.setMessage( msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				response.setNumberOfElements(complaintReasonEntity.getNumberOfElements());
				response.setTotalRecords(complaintReasonEntity.getTotalElements());
				response.setTotalPages(complaintReasonEntity.getTotalPages());
				for (ComplaintReasonEntity complaint : complaintReasonEntity) {
					ComplaintReasonDTO complaintResponse = mapper.convertValue(complaint, ComplaintReasonDTO.class);
					ComplaintReasonDTO.add(complaintResponse);
				}
				response.setResponseContents(ComplaintReasonDTO);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				//log.info("Successfully Get Lazy List Details");
			}
		} catch (Exception e) {
			log.error(" Exception ComplaintReasonServiceImpl.getAllLazy() " + e);
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended ComplaintReasonServiceImpl.getAllLazy() ===>");
		return response;
	}

	@Override
	public BaseDTO getById(UUID id) {
		//log.info("<=== Started ComplaintReasonServiceImpl.getById()===>");
		BaseDTO response = new BaseDTO();
		try {
			ComplaintReasonEntity complaintReasonEntity = complaintReasonDao.getById(id);
			if (complaintReasonEntity.getId() == null) {
				//log.info("Complaint Reason Id {}",id);
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setResponseContent(complaintReasonEntity);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Retreived Complaint Details..");
			}
		} catch (Exception e) {
			log.error("<---- ComplaintReasonServiceImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- ComplaintReasonServiceImpl.getById ENDED -----> ");
		return response;
	}

	@Override
	public BaseDTO delete(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started ComplaintReasonServiceImpl.delete() ===>");
		try {
			ComplaintReasonEntity complaintReasonEntity = complaintReasonDao.getById(id);
			if (complaintReasonEntity != null) {
				//log.info("Complaint reason Id {}",id);
				complaintReasonEntity = complaintReasonDao.delete(id);
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted");
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("Exception ComplaintReasonServiceImpl.delete()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<=== Ended ComplaintReasonServiceImpl.delete() ===>");
		return response;
	}

	@Override
	public BaseDTO softDelete(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started ComplaintReasonServiceImpl.softDelete()===>");
		try {
			ComplaintReasonEntity complaintReasonEntity = complaintReasonDao.getById(id);
			if (complaintReasonEntity != null) {
				complaintReasonEntity.setStatus(false);
				complaintReasonEntity = complaintReasonDao.save(complaintReasonEntity);
				if (complaintReasonEntity.getStatus() == false) {
					response.setMessage("Successfully Deleted");
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Deleted");
				} else {
					response.setMessage("Unable to Delete Complaint Details..");
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					//log.warn("Unable to Delete Complaint Details..");
				}
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- SubscriptionMasterServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Complaint Details");
		}
		//log.info("<=== Ended ComplaintReasonServiceImpl.softDelete() ===>");
		return response;
	}

	@Override
	public BaseDTO update(UUID id, ComplaintReasonDTO complaintReasonDTO) {
		//log.info(" <----- BlockDaoImpl.update() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			boolean check = false;
			Optional<ComplaintReasonEntity> optional = complaintReasonRepository.findById(id);
			if (optional.isPresent()) {
				ComplaintReasonEntity comaplaintReason = new ComplaintReasonEntity();
				comaplaintReason = optional.get();
				List<ComplaintReasonEntity> checkCode = complaintReasonRepository.checkCode(complaintReasonDTO.getReasonCode());
				for (ComplaintReasonEntity entity : checkCode) {
					if (entity.getReasonCode().equals(complaintReasonDTO.getReasonCode())) {
						if(id.equals(entity.getId())) {
							check = false;
						}else
						check = true;
						break;
					} else {
						check = false;
					}
				}

				if (check) {
					String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
					response.setMessage(complaintReasonDTO.getReasonCode() + " " + msg);
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.warn("Complaint Reason Code is Already Exists - {} ", complaintReasonDTO.getReasonCode());
				} else {
					comaplaintReason.setReasonCode(complaintReasonDTO.getReasonCode());
					comaplaintReason.setReasonName(complaintReasonDTO.getReasonName());
					comaplaintReason.setReasonTypeId(complaintReasonDTO.getReasonTypeId());
					comaplaintReason.setStatus(complaintReasonDTO.getStatus());
					ComplaintReasonEntity afterUpdate = complaintReasonRepository.save(comaplaintReason);
					response.setResponseContent(afterUpdate);
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(complaintReasonDTO.getReasonName() + " " + msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					/*log.info("Compalint Reason - {} Updated Successfully",  complaintReasonDTO.getReasonCode()
							,complaintReasonDTO.getReasonCode(), complaintReasonDTO.getReasonTypeId());*/
				}

			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- ComplaintReasonServiceImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());

		} catch (Exception e) {
			log.error("<---- ComplaintReasonServiceImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info(" <----- ComplaintReasonServiceImpl.update() END -----> ");
		return response;
	}

}
