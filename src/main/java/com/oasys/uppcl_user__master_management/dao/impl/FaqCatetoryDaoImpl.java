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
import com.oasys.uppcl_user__master_management.dao.FaqCategoryDao;
import com.oasys.uppcl_user__master_management.dto.FaqCategoryDTO;
import com.oasys.uppcl_user__master_management.entity.FaqCategoryEntity;
import com.oasys.uppcl_user__master_management.repository.FaqCategoryRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class FaqCatetoryDaoImpl implements FaqCategoryDao {

	@Autowired
	FaqCategoryRepository faqCategoryRepository;
	
	@Autowired
	MessageSource messageSource;

	public BaseDTO create(FaqCategoryDTO faqCategoryDTO) {
		//log.info(" <----- Faq Category Create Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		FaqCategoryEntity entity = null;
		try {
			if (faqCategoryDTO.getCategoryName() != "") {
				//log.info(" category Name {}  -Is Not Empty", faqCategoryDTO.getCategoryName());
				if (faqCategoryDTO.getCategoryName() != null) {
					//log.info(" category Name {}  -Is Not Null", faqCategoryDTO.getCategoryName());
					
						//log.info(" category Discription {}  -Is Not Empty", faqCategoryDTO.getCategoryDescription());
						//if (faqCategoryDTO.getCategoryName() != null) {
							//log.info(" category Discription {}  -Is Not Null", faqCategoryDTO.getCategoryDescription());
						/*
						 * Optional<FaqCategoryEntity> optional = faqCategoryRepository
						 * .getByCategoryName(faqCategoryDTO.getCategoryName());
						 */
					
					 entity = faqCategoryRepository
							.findByCategoryNameIgnoreCase(faqCategoryDTO.getCategoryName());
							if (entity == null) {
								entity = new FaqCategoryEntity();
								entity.setCategoryName(faqCategoryDTO.getCategoryName());
								//entity.setCategoryDescription(faqCategoryDTO.getCategoryDescription());
								entity.setRemarks(faqCategoryDTO.getRemarks());
								if (entity.getStatus() == null) {
									//log.info(" status {}  -Is Not Null", faqCategoryDTO.getStatus());
									entity.setStatus(true);
								} else {
									entity.setStatus(faqCategoryDTO.getStatus());
								}
								faqCategoryRepository.save(entity);
								String msg = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
								response.setMessage("FAQ Category " + msg);
								response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
								//log.info("Successfully Added",faqCategoryDTO.getCategoryDescription() ,faqCategoryDTO.getCategoryName(),faqCategoryDTO.getCategoryName());
							} else {
								//log.warn("<----- Category Name Already Present ----->");
								response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
								String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,Locale.US);
								response.setMessage("This FAQ Category " + msg);
							}
						
							/*
							 * } else { //log.warn("<----- Category Description Must Not Empty ----->");
							 * response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
							 * response.setMessage("Category Description Must Not Empty"); }
							 */
				} else {
					//log.warn("<----- Category Name Must Not Null ----->");
					response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
					response.setMessage("Category Name Must Not Null");
				}
			} else {
				//log.warn("<----- Category Name Must Not Empty ----->");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				response.setMessage("Category Name Must Not Empty");
			}

		} catch (DataIntegrityViolationException e) {
			log.error("<----- FaqCatetoryDaoImpl.create() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		} catch (Exception e) {
			log.error("<----- FaqCatetoryDaoImpl.create() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Faq Category Create Dao ENDED -----> ");
		return response;
	}


	@Override
	public BaseDTO update(UUID id, FaqCategoryDTO faqCategoryDTO) {
		// log.info(" <----- Faq Category Update Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		FaqCategoryEntity entity1 = new FaqCategoryEntity();
		try {
			if (faqCategoryDTO.getCategoryName() != "") {
				// log.info(" category name {} -Is Not Empty",
				// faqCategoryDTO.getCategoryName());
				if (faqCategoryDTO.getCategoryName() != null) {
					// log.info(" category name {} -Is Not Null", faqCategoryDTO.getCategoryName());

					// log.info(" status {} -Is Not Null", faqCategoryDTO.getCategoryDescription());
					Optional<FaqCategoryEntity> optional = faqCategoryRepository.findById(id);
					 
					if (optional.isPresent()) {
						
						FaqCategoryEntity entity = new FaqCategoryEntity();
						entity = optional.get();
						
						entity1 = faqCategoryRepository
								.findByCategoryNameIgnoreCase(faqCategoryDTO.getCategoryName());
						
						if(entity1!=null)
						{
							if(entity1.getId().equals(entity.getId()))
							{
								entity.setCategoryName(faqCategoryDTO.getCategoryName());
								// entity.setCategoryDescription(faqCategoryDTO.getCategoryDescription());
								entity.setRemarks(faqCategoryDTO.getRemarks());
								if (entity.getStatus() == null) {
									// log.info(" status {} -Is Not Null", faqCategoryDTO.getStatus());
									entity.setStatus(true);
								} else {
									entity.setStatus(faqCategoryDTO.getStatus());
								}
								faqCategoryRepository.save(entity);
								String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
										Locale.US);
								response.setMessage(faqCategoryDTO.getCategoryName() + " " + msg);
								response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
								// log.info("Successfully Updated");
							}else
							{
								
								response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
								response.setMessage("This Faq Category Already Exist.");	
							}
						}else
						{
							entity.setCategoryName(faqCategoryDTO.getCategoryName());
							// entity.setCategoryDescription(faqCategoryDTO.getCategoryDescription());
							entity.setRemarks(faqCategoryDTO.getRemarks());
							if (entity.getStatus() == null) {
								// log.info(" status {} -Is Not Null", faqCategoryDTO.getStatus());
								entity.setStatus(true);
							} else {
								entity.setStatus(faqCategoryDTO.getStatus());
							}
							faqCategoryRepository.save(entity);
							String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
									Locale.US);
							response.setMessage(faqCategoryDTO.getCategoryName() + " " + msg);
							response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
							// log.info("Successfully Updated");	
						}
						
						
						
					} else {
						// log.error("<----- Faq Category Type Not Persent ----->");
						response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
						response.setMessage("This Faq Category Type Not Persent");
					}

				} else {
					// log.warn("<----- Category Name Must Not Null ----->");
					response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
					response.setMessage("Category Name Must Not Null");
				}
			} else {
				// log.warn("<----- Category Name Must Not Empty ----->");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				response.setMessage("Category Name Must Not Empty");
			}

		} catch (DataIntegrityViolationException e) {
			log.error("<----- FaqCatetoryDaoImpl.update() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		} catch (Exception e) {
			log.error("<----- FaqCatetoryDaoImpl.update() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Update Faq");
		}
		// log.info(" <----- Faq Category Update Dao ENDED -----> ");
		return response;
	}

	@Override
	public BaseDTO getByID(UUID id) {
		//log.info(" <----- FaqCatetoryDaoImpl.getByID() Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			Optional<FaqCategoryEntity> list = faqCategoryRepository.findById(id);
			if (!list.isPresent()) {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			} else {
				response.setResponseContent(list);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage( msg);
				//log.info("Successfully get Details");
			}
		} catch (Exception e) {
			log.error("<----- FaqCatetoryDaoImpl.getByID() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( msg);
		}
		//log.info(" <----- FaqCatetoryDaoImpl.getByID() Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAll() {
		//log.info(" <----- Faq Category getAll Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<FaqCategoryEntity> list = faqCategoryRepository.findAll();

			if (list.isEmpty()) {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			} else {
				response.setResponseContents(list);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				//log.info("Successfully Get all details ");
			}
		} catch (Exception e) {
			log.error("<----- FaqCatetoryDaoImpl.getAll() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Faq Category getAll Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- Faq Category delete Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			Optional<FaqCategoryEntity> list = faqCategoryRepository.findById(id);
			if (!list.isPresent()) {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			} else {
				FaqCategoryEntity entity = new FaqCategoryEntity();
				entity = list.get();
				entity.setStatus(false);
				faqCategoryRepository.save(entity);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				//log.info("Successfully Delete");
			}
		} catch (Exception e) {
			log.error("<----- FaqCatetoryDaoImpl.delete() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Faq Category delete Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		//log.info(" <----- Faq Category getAllActive Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<FaqCategoryEntity> list = faqCategoryRepository.getAllActive();

			if (list.isEmpty()) {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				log.error("No Record Found..");
			} else {
				response.setResponseContents(list);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				//log.info("faq category Get all active details Fetched successfully");
			}
		} catch (Exception e) {
			log.error("<----- FaqCatetoryDaoImpl.getAllActive() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Faq Category getAllActive Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO lazylist(PaginationRequestDTO pageData) {
		BaseDTO response = new BaseDTO();
		//log.info("<----- Faq Category lazylist Dao STARTED ----->");
		Page<FaqCategoryEntity> page = null;
		List<FaqCategoryEntity> contentsList = new ArrayList<FaqCategoryEntity>();
		Pageable pageRequest;
		try {

			if (pageData.getSearch() != null) {
				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.ASC, pageData.getSortField()));
					page = faqCategoryRepository.search(pageRequest, pageData.getSearch().toUpperCase());
				} else {
					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.DESC, pageData.getSortField()));
					page = faqCategoryRepository.search(pageRequest, pageData.getSearch().toUpperCase());
				}
			} else {

				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					page = faqCategoryRepository.findAll(PageRequest.of(pageData.getPageNo(),
							pageData.getPaginationSize(), Sort.by(Direction.ASC, pageData.getSortField())));
				} else {
					page = faqCategoryRepository.findAll(PageRequest.of(pageData.getPageNo(),
							pageData.getPaginationSize(), Sort.by(Direction.DESC, pageData.getSortField())));
				}
			}
			if (page.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("No Record Found..");

			} else {
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				for (FaqCategoryEntity entity : page) {
					contentsList.add(entity);
				}
				response.setResponseContents(contentsList);
				response.setNumberOfElements(page.getNumberOfElements());
				response.setTotalRecords(page.getTotalElements());
				response.setTotalPages(page.getTotalPages());
				//log.info("Faq Category Data List Retrived");
			}

		} catch (Exception e) {
			log.error("<----- FaqCatetoryDaoImpl.lazylist() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( msg);
		}
		//log.info("<----- Faq Category lazylist Dao END ----->");
		return response;
	}

}
