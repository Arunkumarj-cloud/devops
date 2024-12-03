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
import com.oasys.uppcl_user__master_management.dao.FaqDao;
import com.oasys.uppcl_user__master_management.dto.FaqDTO;
import com.oasys.uppcl_user__master_management.dto.FaqRequestDTO;
import com.oasys.uppcl_user__master_management.entity.FaqCategoryEntity;
import com.oasys.uppcl_user__master_management.entity.FaqEntity;
import com.oasys.uppcl_user__master_management.repository.FaqCategoryRepository;
import com.oasys.uppcl_user__master_management.repository.FaqRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class FaqDaoImpl implements FaqDao {

	@Autowired
	FaqRepository faqRepository;

	@Autowired
	FaqCategoryRepository faqCategoryRepository;

	@Autowired
	MessageSource messageSource;
	
	@Autowired
	ObjectMapper objectMapper;

	@Override
	public BaseDTO create(FaqDTO faqDTO) {
		//log.info(" <----- Faq create Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		List<FaqEntity> faqEntity = null;
		try {
			if (faqDTO.getQuestion() != "") {
				//log.info(" Question  {}  -Is Not Empty", faqDTO.getQuestion());
				if (faqDTO.getAnswer() != "") {
					//log.info("Answer {}  -Is Not Empty", faqDTO.getAnswer());
					Optional<FaqCategoryEntity> optional = faqCategoryRepository
							.findById(faqDTO.getCategoryId().getId());
					if (optional.isPresent()) {
						 faqEntity = faqRepository.getByCategoryId(faqDTO.getCategoryId().getId());
						if(faqEntity != null)
						{
							response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
							response.setMessage("This Faq upload already exist");
						}else
						{
							faqEntity = new ArrayList<FaqEntity>();
							for(FaqEntity entity:faqEntity)
							{
								entity.setQuestion(faqDTO.getQuestion());
								entity.setAnswer(faqDTO.getAnswer());
								entity.setRemarks(faqDTO.getRemarks());
								entity.setCategoryId(faqDTO.getCategoryId());
								if (entity.getStatus() == null) {
									//log.info(" status  {}  -Is Not Null", entity.getStatus());
									entity.setStatus(true);
								} else {
									entity.setStatus(faqDTO.getStatus());
								}
								if(faqDTO.getImage()!=null) {
								entity.setImage(faqDTO.getImage());
								}	
								
								faqRepository.save(entity);
							}
							String msg = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,
									Locale.US);
							response.setMessage(msg);
							response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
							
						}
						//FaqEntity entity = new FaqEntity();
						
						
						
						//log.info("Successfully Added");
					} else {
						//log.error("<----- Category Id Not Empty ----->");
						response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
						response.setMessage("Category Id Is Empty");
					}
				} else {
					//log.warn("<----- Answer Not Empty ----->");
					response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
					response.setMessage("Answer Is Empty");
				}
			} else {
				//log.warn("<----- Question Not Empty ----->");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				response.setMessage("Question Is Empty");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<----- FaqDaoImpl.Create() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to create Faq");
		} catch (Exception e) {
			log.error("<----- FaqDaoImpl.Create() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Faq Create Dao Ended -----> ");
		return response;
	}

	@Override
	public BaseDTO createList(List<FaqDTO> faqDTO) {
		//log.info(" <----- Faq Create Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		FaqEntity faqEntity = null;
		try {
			for (FaqDTO faqDTO2 : faqDTO) {
//				if (faqDTO2.getQuestion() != null || faqDTO2.getQuestion() != "") {
					//log.info("  Question {} - Is Not Null  -Is Not Empty", faqDTO2.getQuestion());
					if (faqDTO2.getLanguage() != null || faqDTO2.getLanguage() != "") {
						//log.info(" Answer  {} - Is Not Null {} -Is Not Empty", faqDTO2.getAnswer());
						if (faqDTO2.getCategoryId() != null) {
							
							
							Optional<FaqCategoryEntity> optional = faqCategoryRepository
									.findById(faqDTO2.getCategoryId().getId());
							if (optional.isPresent()) {
								 faqEntity = faqRepository.getByCategoryIdAndLanguage(faqDTO2.getCategoryId().getId(),faqDTO2.getLanguage());
								if(faqEntity != null)
								{
									String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,
											Locale.US);
									response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
									response.setMessage(faqDTO2.getLanguage() +" language FAQ for "+faqEntity.getCategoryId().getCategoryName() + " "+msg);
								}else
								{
									faqEntity = new FaqEntity();
									faqEntity.setQuestion(faqDTO2.getQuestion());
									faqEntity.setAnswer(faqDTO2.getAnswer());
									faqEntity.setRemarks(faqDTO2.getRemarks());
									faqEntity.setCategoryId(faqDTO2.getCategoryId());
									faqEntity.setHtmlcontent(faqDTO2.getHtmlcontent());
									faqEntity.setLanguage(faqDTO2.getLanguage());
										if (faqEntity.getStatus() == null) {
											//log.info(" status  {}  -Is Not Null", entity.getStatus());
											faqEntity.setStatus(true);
										} else {
											faqEntity.setStatus(faqDTO2.getStatus());
										}
										if(faqDTO2.getImage()!=null) {
											faqEntity.setImage(faqDTO2.getImage());
										}	
										
										faqRepository.save(faqEntity);
										
										String msg = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,
												Locale.US);
										response.setMessage(msg);
										response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
									}
									
									
								}
								//FaqEntity entity = new FaqEntity();
								
								
								
								//log.info("Successfully Added");
							} else {
								//log.error("<----- Category Id Not Empty ----->");
								response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
								response.setMessage("Category Id Is Empty");
							}
							
					} else {
						//log.error("<----- Answer Not Empty ----->");
						response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
						response.setMessage("Language Is Empty");
					}
//				} else {
//					//log.error("<----- Question Not Empty ----->");
//					response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
//					response.setMessage("Question Is Empty");
//				}

			}

		} catch (DataIntegrityViolationException e) {
			log.error("<----- FaqDaoImpl.createList() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		} catch (Exception e) {
			log.error("<----- FaqDaoImpl.createList() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Faq Create Dao Ended -----> ");
		return response;
	}
	
	@Override
	public BaseDTO FAQGetAll() {
		BaseDTO response = new BaseDTO();
		List<FaqDTO> list = new ArrayList<FaqDTO>();
		try {
			List<FaqEntity> faqEntity = faqRepository.findAll();
			if (faqEntity.isEmpty()) {
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				response.setMessage(" Empty data");
			} else {
				for (FaqEntity obj : faqEntity) {
					FaqDTO listresp = objectMapper.convertValue(obj, FaqDTO.class);

					FaqCategoryEntity faq = getById1(obj.getCategoryId().getId());
					listresp.setId(obj.getId());
					listresp.setCategoryId(obj.getCategoryId());
					listresp.setCategoryName(faq.getCategoryName());
					listresp.setQuestion(obj.getQuestion());
					listresp.setAnswer(obj.getAnswer());
					listresp.setImage(obj.getImage());
					list.add(listresp);
				}
				response.setResponseContents(list);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			}
		} catch (Exception e) {
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Exception");
		}
		return response;

	}

	public FaqCategoryEntity getById1(UUID id) {
		BaseDTO response = new BaseDTO();
		FaqCategoryEntity faqCategoryEntity = new FaqCategoryEntity();
		try {
			faqCategoryEntity = faqCategoryRepository.getOne(id);

		} catch (Exception e) {
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		return faqCategoryEntity;

	}

	@Override
	public BaseDTO updateList(List<FaqDTO> faqDTO) {
		//log.info(" <----- Faq updateList Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			for (FaqDTO faqDTO2 : faqDTO) {
				if (faqDTO2.getQuestion() != "") {
					//log.info("  Question {} -Is Not Empty", faqDTO2.getQuestion());
					if (faqDTO2.getAnswer() != "") {
						//log.info("  Question {} -Is Not Empty", faqDTO2.getAnswer());
						if (faqDTO2.getCategoryId() != null) {
							Optional<FaqEntity> before = faqRepository.findById(faqDTO2.getId());
							FaqEntity after = new FaqEntity();
							if (before.isPresent()) {
								after = before.get();
								after.setQuestion(faqDTO2.getQuestion());
								after.setAnswer(faqDTO2.getAnswer());
								after.setCategoryId(faqDTO2.getCategoryId());
								if (after.getStatus() == null) {
									//log.info("status {} - Is Not Null");
									after.setStatus(true);
								} else {
									after.setStatus(faqDTO2.getStatus());
								}
								if(faqDTO2.getImage()!=null) {
									after.setImage(faqDTO2.getImage());
								}
								
								faqRepository.save(after);
								String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE,
										null, Locale.US);
								response.setMessage(msg);
								response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
								//log.info("Successfully Updated");
							}
						} else {
							//log.error("<----- Faq Type Not Empty ----->");
							response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
							response.setMessage("Faq Type Is Empty");
							return response;
						}
					} else {
						//log.error("<----- Answer Not Empty ----->");
						response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
						response.setMessage("Answer Is Empty");
						return response;
					}
				} else {
					//log.error("<----- Question Not Empty ----->");
					response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
					response.setMessage("Question Is Empty");
					return response;
				}

			}

		} catch (DataIntegrityViolationException e) {
			log.error("<----- FaqDaoImpl.updateList() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		} catch (Exception e) {
			log.error("<----- FaqDaoImpl.updateList() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Faq updateList Dao Ended -----> ");
		return response;
	}

	@Override
	public BaseDTO getAll() {
		//log.info(" <----- Faq getAll Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<FaqEntity> list = faqRepository.getAll();

			if (list.isEmpty()) {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.info("No Record Found..");
			} else {
				response.setResponseContents(list);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				//log.info("Successfully Get All Faq Details");
			}
		} catch (Exception e) {
			log.error("<----- FaqDaoImpl.getAll() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Faq getAll Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO LazyList(PaginationRequestDTO paginationRequestDTO) {
		BaseDTO response = new BaseDTO();
		//log.info("Entered in FaqDaoImpl.LazyList(FaqDTO faqDTO)");
		Page<FaqEntity> page = null;
		List<FaqEntity> contentsList = new ArrayList<FaqEntity>();
		Pageable pageRequest;
		try {
			if (paginationRequestDTO.getSearch() != null) {
				//log.info("aaaaaaaaaaaaaaaaaaaa" + paginationRequestDTO.getSearch());
				if (paginationRequestDTO.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(paginationRequestDTO.getPageNo(),
							paginationRequestDTO.getPaginationSize(),
							Sort.by(Direction.ASC, paginationRequestDTO.getSortField()));
					page = faqRepository.search(pageRequest, paginationRequestDTO.getSearch().toUpperCase());
				} else {
					pageRequest = PageRequest.of(paginationRequestDTO.getPageNo(),
							paginationRequestDTO.getPaginationSize(),
							Sort.by(Direction.DESC, paginationRequestDTO.getSortField()));
					page = faqRepository.search(pageRequest, paginationRequestDTO.getSearch().toUpperCase());
				}
			} else {

				if (paginationRequestDTO.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(paginationRequestDTO.getPageNo(),
							paginationRequestDTO.getPaginationSize(),
							Sort.by(Direction.ASC, paginationRequestDTO.getSortField()));
					page = faqRepository.withoutSearch(pageRequest);
				} else {
					pageRequest = PageRequest.of(paginationRequestDTO.getPageNo(),
							paginationRequestDTO.getPaginationSize(),
							Sort.by(Direction.DESC, paginationRequestDTO.getSortField()));
					page = faqRepository.withoutSearch(pageRequest);
				}
			}
			if (page.isEmpty()) {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.info("No Record Found..");

			} else {

				response.setMessage("Faq Data List Retrieved");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				for (FaqEntity faqEntity : page) {
					contentsList.add(faqEntity);
				}
				response.setResponseContents(contentsList);
				response.setNumberOfElements(page.getNumberOfElements());
				response.setTotalRecords(page.getTotalElements());
				response.setTotalPages(page.getTotalPages());
				//log.info("Faq Data List Retrived");
			}
		} catch (Exception e) {
			response.setMessage("Problem Occured in Retrieving Faq Data List");
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			log.error("Exception Occured :" + e.getLocalizedMessage());
		}
		//log.info("Finished Execution of FaqDaoImpl.LazyList");
		return response;
	}

	@Override
	public BaseDTO getByCategoryId(UUID id) {
		//log.info(" <----- Faq getByCategoryId Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<FaqEntity> list = faqRepository.getByCategoryId(id);
			if (list.isEmpty()) {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			} else {
				response.setResponseContents(list);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
			}
		} catch (Exception e) {
			log.error("<----- FaqDaoImpl.getByCategoryId() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Faq getByCategoryId Dao END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getByCategoryIdLang(UUID id, String language) {
		//log.info(" <----- Faq getByCategoryId Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<FaqEntity> list = faqRepository.getByCategoryIdLang(id, language.toUpperCase());
			if (list.isEmpty()) {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			} else {
				response.setResponseContents(list);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
			}
		} catch (Exception e) {
			log.error("<----- FaqDaoImpl.getByCategoryIdLang() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Faq getByCategoryId Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		//log.info(" <----- Faq getAllActive Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<FaqEntity> list = faqRepository.getAllActive();

			if (list.isEmpty()) {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			} else {
				response.setResponseContents(list);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,
						Locale.US);
				response.setMessage(msg);
				//log.info("Faq Get all Active Details Fetched Successfully");
			}
		} catch (Exception e) {
			log.error("<----- FaqDaoImpl.getAllActive() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,
					Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Faq getAllActive Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO deleteById(UUID id) {
		//log.info(" <----- Faq delete Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			Optional<FaqEntity> faqEntity = faqRepository.findById(id);
			if (faqEntity.isPresent()) {
				//log.info("faq Id", id);
				faqRepository.deleteById(id);
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,
						Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted", id);
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- FaqDaoImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Faq delete Dao END -----> ");
		return response;
	}

	@Override
	public FaqEntity save(FaqEntity faqEntity) {
		//log.info(" <----- Faq save Dao STARTED -----> ");
		try {
			faqEntity = faqRepository.save(faqEntity);

		} catch (DataIntegrityViolationException e) {
			log.error("<---- FaqDaoImpl.save() ----> EXCEPTION", e);
		} catch (Exception e) {
			log.error("<---- FaqDaoImpl.save() ----> EXCEPTION", e);
		}
		//log.info(" <----- Faq save Dao END -----> ");
		return faqEntity;
	}

	@Override
	public FaqEntity getById(UUID id) {
		//log.info(" <----- FaqDaoImpl getById Dao STARTED -----> ");
		FaqEntity faqEntity = new FaqEntity();
		try {
			faqEntity = faqRepository.getOne(id);

		} catch (Exception e) {
			log.error("<---- FaqDaoImpl.getById() ----> EXCEPTION", e);
		}
		//log.info(" <----- FaqDaoImpl getById Dao END -----> ");
		return faqEntity;
	}

	public BaseDTO softDeleteAll(FaqRequestDTO listOfIds) {
		//log.info(" <----- FaqDaoImpl softDelete Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		boolean flag = false;
		Optional<FaqEntity> optional = null;
		try {
			if (listOfIds != null) {
				for (UUID id : listOfIds.getListOfIds()) {
					if (id != null) {
						optional = faqRepository.findById(id);
						if (optional.isPresent()) {
							FaqEntity f = optional.get();
							f.setStatus(false);
							faqRepository.save(f);
							flag = true;
						}
					}

				}
				if (flag == true) {
					baseDTO.setMessage("Successfully Deleted");
					baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Deleted");

				} else {
					baseDTO.setMessage("No Record Found..");
					baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
					//log.error("No Record Found..");
				}

			} else {
				baseDTO.setMessage("No ID To Retrive Data..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No ID Found To Delete The Records..");
			}

		} catch (Exception e) {
			log.error("<---- FaqDaoImpl.softDelete() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			baseDTO.setMessage("Unable to Delete Faq" + e);
		}
		//log.info(" <----- FaqDaoImpl softDelete Service END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO getDetailsById(UUID id) {
		//log.info(" <----- Faq getDetailsById Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		Optional<FaqEntity> faqEntity = null;
		try {

			faqEntity = faqRepository.findById(id);
			if (faqEntity.isPresent()) {
				response.setResponseContent(faqEntity);
				response.setMessage("Data Retrived Sucessfully....");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());

			} else {
				response.setMessage("Empty data by Id...");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<----- FaqDaoImpl.getDetailsById() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Faq getDetailsById Dao END -----> ");
		return response;
	}

}
