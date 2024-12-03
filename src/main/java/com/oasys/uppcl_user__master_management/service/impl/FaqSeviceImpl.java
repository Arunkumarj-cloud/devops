package com.oasys.uppcl_user__master_management.service.impl;

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
import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.FaqDao;
import com.oasys.uppcl_user__master_management.dao.FaqListViewDao;
import com.oasys.uppcl_user__master_management.dto.FaqDTO;
import com.oasys.uppcl_user__master_management.dto.FaqListViewDTO;
import com.oasys.uppcl_user__master_management.dto.FaqRequestDTO;
import com.oasys.uppcl_user__master_management.entity.FaqEntity;
import com.oasys.uppcl_user__master_management.entity.FaqListViewEntity;
import com.oasys.uppcl_user__master_management.repository.FaqRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.FaqService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class FaqSeviceImpl implements FaqService {

	@Autowired(required = false)
	FaqDao faqDao;

	@Autowired(required = false)	
	FaqListViewDao faqListViewDao;
	
	@Autowired(required = false)
	MessageSource messageSource;
	
	
	@Autowired(required = false)
	FaqRepository faqRepository;

	@Override
	public BaseDTO create(FaqDTO faqDTO) {
		//log.info(" <----- Faq create Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = faqDao.create(faqDTO);
		} catch (DataIntegrityViolationException e) {
			log.error("<---- FaqSeviceImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		} catch (Exception e) {
			log.error("<---- FaqSeviceImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Faq create Service END -----> ");
		return response;
	}
	@Override
	public BaseDTO updateList(List<FaqDTO> faqDTO) {
		//log.info(" <----- Faq updateList Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = faqDao.updateList(faqDTO);
		} catch (DataIntegrityViolationException e) {
			log.error("<---- FaqSeviceImpl.updateList() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		} catch (Exception e) {
			log.error("<---- FaqSeviceImpl.updateList() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Faq updateList Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO createList(List<FaqDTO> faqDTO) {
		//log.info(" <----- Faq createList Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = faqDao.createList(faqDTO);
		} catch (DataIntegrityViolationException e) {
			log.error("<---- FaqSeviceImpl.createList() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		} catch (Exception e) {
			log.error("<---- FaqSeviceImpl.createList() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Faq createList Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO FAQGetAll() {
		BaseDTO response = new BaseDTO();
		try {
			response = faqDao.FAQGetAll();
		} catch (Exception e) {
			log.error(" Exception EducationServiceImpl.FAQGetAll()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		return response;

	}

	@Override
	public BaseDTO getAll() {
		//log.info(" <----- Faq getAll Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = faqDao.getAll();
		} catch (Exception e) {
			log.error("<---- FaqSeviceImpl.getAll() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Faq getAll Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getByCategoryId(UUID id) {
		//log.info(" <----- Faq getAll By Faq ByCategoryId Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = faqDao.getByCategoryId(id);
		} catch (Exception e) {
			log.error("<---- FaqSeviceImpl.getByFaqType() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Faq getAll By Faq Type Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getByCategoryIdLang(UUID id, String language) {
		//log.info(" <----- Faq getAll By Faq ByCategoryId Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = faqDao.getByCategoryIdLang(id, language);
		} catch (Exception e) {
			log.error("<---- FaqSeviceImpl.getByFaqType() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Faq getAll By Faq Type Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO LazyList(PaginationRequestDTO paginationRequestDTO) {
		//log.info(" <----- Faq LazyList Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = faqDao.LazyList(paginationRequestDTO);
		} catch (Exception e) {
			log.error("<---- FaqSeviceImpl.LazyList(FaqDTO faqDTO) ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Faq LazyList Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		//log.info(" <----- Faq getAllActive Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = faqDao.getAllActive();
		} catch (Exception e) {
			log.error("<---- FaqSeviceImpl.getAllActive() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Faq getAllActive Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO deleteById(UUID id) {
		//log.info(" <----- Faq deleteById Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = faqDao.deleteById(id);

		} catch (Exception e) {
			log.error("<---- FaqSeviceImpl.deleteById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Faq deleteById Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO CategoryLazyList(PaginationRequestDTO pageData) {
		//log.info(" <----- CategoryLazyList (PaginationRequestDTO pageData) Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		Page<FaqListViewEntity> page = null;
		List<FaqListViewDTO> contentsList = new ArrayList<FaqListViewDTO>();
		Pageable pageRequest;
		try {
			
				if (pageData.getSearch() != null) {
					if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
						pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
								Sort.by(Direction.ASC, pageData.getSortField()));
						page = faqListViewDao.search(pageRequest, pageData.getSearch().toUpperCase());
								
					} else {
						pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
								Sort.by(Direction.DESC, pageData.getSortField()));
						page = faqListViewDao.search(pageRequest, pageData.getSearch().toUpperCase());
					}
				} else {
					if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
						pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
								Sort.by(Direction.ASC, pageData.getSortField()));
						page = faqListViewDao.findall1(pageRequest);
					} else {
						pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
								Sort.by(Direction.DESC, pageData.getSortField()));
						page = faqListViewDao.findall1(pageRequest);
					}
				}
				if (page.isEmpty()) {
					response.setMessage("No Record Found..");
					response.setStatusCode(ErrorDescription.EMPTY_DATA.getErrorCode());
					//log.warn("No Record Found..");

				} else {
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getErrorCode());
					for (FaqListViewEntity faqListViewEntity : page) {

						FaqListViewDTO list = new FaqListViewDTO();
						
						list.setId(faqListViewEntity.getCategoryId());
						list.setCategoryId(faqListViewEntity.getCategoryId());
						list.setCategoryName(faqListViewEntity.getCategoryName());
						list.setStatus(faqListViewEntity.getStatus());
						contentsList.add(list);
					}
					response.setResponseContents(contentsList);
					response.setTotalRecords(page.getTotalElements());
					String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage(msg);
					//log.info("successfully Get Lazy list details");
				}
			} catch (Exception e) {
				String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getErrorCode());
				log.error("Exception --->" + e);
			}
			//log.info("Category lazyList End");
			return response;
		}

	@Override
	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- Faq softDelete Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			FaqEntity faqEntity = faqDao.getById(id);
			if (faqEntity != null) {
				faqEntity.setStatus(false);
				faqEntity = faqDao.save(faqEntity);

				if (faqEntity.getStatus() == false) {
					response.setMessage("Successfully Deleted");
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Deleted");
				} else {
					response.setMessage("Unable to Delete Faq..");
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					//log.error("Unable to Delete Faq..");
				}
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- FaqSeviceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Faq");
		}
		//log.info(" <----- Faq softDelete Service END -----> ");
		return response;
	}


	@Override
	public BaseDTO softDeleteAll(FaqRequestDTO listOfIds) {
		//log.info(" <----- Faq softDeleteAll Service Started -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = faqDao.softDeleteAll(listOfIds);
		} catch (Exception e) {
			log.error("<---- FaqSeviceImpl.deleteById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Faq softDeleteAll Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getDetailsById(UUID id) {
		//log.info(" <----- FaqSeviceImpl.getDetailsById() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = faqDao.getDetailsById(id);
		} catch (Exception e) {
			log.error("<---- FaqSeviceImpl.getDetailsById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- FFaqSeviceImpl.getDetailsById() END -----> ");
		return response;
	}



	@Override
	public BaseDTO updateFaq(UUID id, FaqDTO faqDTO) {
		//log.info(" <----- Faq create Dao STARTED -----> ");
				BaseDTO response = new BaseDTO();
				FaqEntity entity =null;
				try {
//					if (faqDTO.getQuestion() != "") {
						//log.info(" Question  {}  -Is Not Empty", faqDTO.getQuestion());
						if (faqDTO.getLanguage() != "") {
							//log.info("Answer {}  -Is Not Empty", faqDTO.getAnswer());
							entity = faqRepository.getOne(id);
							if (entity!=null) {
								/*
								 * FaqEntity faqEntity =
								 * faqRepository.getByCategoryIdAndLanguage(faqDTO.getCategoryId().getId(),
								 * faqDTO.getLanguage()); if(faqEntity != null) { String msg =
								 * messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
								 * response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
								 * response.setMessage(faqDTO.getLanguage()
								 * +" language FAQ for "+faqEntity.getCategoryId().getCategoryName() + " "+msg);
								 * }else {
								 */
								//entity	= new FaqEntity(); 
								entity.setQuestion(faqDTO.getQuestion());
								entity.setAnswer(faqDTO.getAnswer());
								entity.setCategoryId(faqDTO.getCategoryId());
								entity.setRemarks(faqDTO.getRemarks());
								entity.setHtmlcontent(faqDTO.getHtmlcontent());
								entity.setLanguage(faqDTO.getLanguage());
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
								
								response.setMessage("FAQ updated Successfully");
								response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
								//}
								//log.info("Successfully Added");
							} else {
								//log.error("<----- Category Id Not Empty ----->");
								response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
								response.setMessage("Category Id Is Invalid");
							}
						} else {
							//log.warn("<----- Answer Not Empty ----->");
							response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
							response.setMessage("Language Is Empty");
						}
//					} else {
//						//log.warn("<----- Question Not Empty ----->");
//						response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
//						response.setMessage("Language Is Empty");
//					}
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

}






