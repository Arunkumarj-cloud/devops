package com.oasys.uppcl_user__master_management.service.impl;




import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.DateUtill;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.TalukMasterDao;
import com.oasys.uppcl_user__master_management.dto.TalukMasterDTO;
import com.oasys.uppcl_user__master_management.entity.TalukMasterEntity;
import com.oasys.uppcl_user__master_management.repository.TalukMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.TalukMasterService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2 
public class TalukMasterServiceImpl implements TalukMasterService {
	
	@Autowired
	TalukMasterDao talukMasterDao;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	TalukMasterRepository talukMasterRepository;
	
	@Autowired
	DateUtill date;
	
	@Override
	public BaseDTO create(TalukMasterDTO talukMasterDTO) {
		//log.info(" <----- TalukMasterServiceImpl.create() Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = talukMasterDao.create(talukMasterDTO);
		}catch (Exception e) {
			log.error("<---- TalukMasterServiceImpl.create() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			baseDTO.setMessage("Unable to Add ");
		}
		//log.info(" <----- TalukMasterServiceImpl.create() Service END -----> ");
		return baseDTO;
	}
	
	public BaseDTO getAll() {
		//log.info(" <-----TalukMasterServiceImpl.getAll() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = talukMasterDao.getAll();
		} catch (Exception e) {
			log.error("<---- TalukMasterServiceImpl.getAll() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		
		}
		//log.info(" <-----TalukMasterServiceImpl.getAll() Service END -----> ");
		return response;
	}
	
	public BaseDTO update(UUID id,TalukMasterDTO talukMasterDTO) {
		//log.info(" <-----TalukMasterServiceImpl.update() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = talukMasterDao.update(id,talukMasterDTO);
		}catch (Exception e) {
			log.error("<---- TalukMasterServiceImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <-----TalukMasterServiceImpl.update() Service END -----> ");
		return response;
	}
	
	public BaseDTO getById(UUID id) {
		//log.info(" <----- TalukMasterServiceImpl.getById() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = talukMasterDao.getById(id);
		} catch (Exception e) {
			log.error("<---- TalukMasterServiceImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		
		}
		//log.info(" <----- TalukMasterServiceImpl.getById() Service END -----> ");
		return response;
	}
	
	public BaseDTO getAllActive() {
		//log.info(" <-----TalukMasterServiceImpl.getAllActive() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = talukMasterDao.getAllActive();
		} catch (Exception e) {
			log.error("<---- TalukMasterServiceImpl.getAllActive() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		
		}
		//log.info(" <-----TalukMasterServiceImpl.getAllActive() Service END -----> ");
		return response;
	}
	
	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- TalukMasterServiceImpl.softDelete() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = talukMasterDao.softDelete(id);
		} catch (Exception e) {
			log.error("<---- TalukMasterServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Taluk");
		}
		//log.info(" <----- TalukMasterServiceImpl.softDelete() Service END -----> ");
		return response;
	}
	
	public BaseDTO getByDistrictId(UUID id) {
		//log.info(" <----- TalukMasterServiceImpl.getById() Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = talukMasterDao.getByDistrictId(id);
		} catch (Exception e) {
			log.error("<---- TalukMasterServiceImpl.getById() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- TalukMasterServiceImpl.getById() Service END -----> ");
		return baseDTO;
	}
	
	public BaseDTO lazylist(PaginationRequestDTO pageData) {
		BaseDTO response = new BaseDTO();
		Page<TalukMasterEntity> page = null;
		List<TalukMasterDTO> contentsList = new ArrayList<TalukMasterDTO>();
		Pageable pageable = null;
		try {
			//log.info("lazylist Process Start");
			if(talukMasterDao.getCount() > 0) {
				if(pageData.getFromDate() == null && pageData.getToDate() == null) {
				//	log.info("FromDate And ToDate Is Null");
					if(pageData.getSearch() == null) {
						//log.info("Search Is Null");
						if(pageData.getSortOrder() == null && pageData.getSortField() == null) {
							//log.info("Sort Order and Sort Field Null");
							pageable = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize());
						}
						else {
							//log.info("Sort Order => {} and Sort Field => {}",pageData.getSortOrder(),pageData.getSortField());
							if(pageData.getSortOrder().equalsIgnoreCase("ASC")) {
								pageable = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
										Sort.by(Direction.ASC, pageData.getSortField()));
							}
							else if(pageData.getSortOrder().equalsIgnoreCase("DESC")){
								pageable = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
										Sort.by(Direction.DESC, pageData.getSortField()));
							}
							else {
								log.info("Give Valid Sort Order");
							}
						}
						if(pageable != null) {
							page = talukMasterDao.getAllWithPage(pageable);
						}
						else{
							log.info("Some Issue In Pageable");
						}
					}
					else {
						//log.info("Search => {}",pageData.getSearch());
						if(pageData.getSortOrder() == null && pageData.getSortField() == null) {
							//log.info("Sort Order and Sort Field Null");
							pageable = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize());
						}
						else {
							//log.info("Sort Order => {} and Sort Field => {}",pageData.getSortOrder(),pageData.getSortField());
							if(pageData.getSortOrder().equalsIgnoreCase("ASC")) {
								pageable = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
										Sort.by(Direction.ASC, pageData.getSortField()));
							}
							else if(pageData.getSortOrder().equalsIgnoreCase("DESC")){
								pageable = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
										Sort.by(Direction.DESC, pageData.getSortField()));
							}
							else {
								log.info("Give Valid Sort Order");
							}
						}
						if(pageable != null) {
							page = talukMasterDao.getAllWithPageAndSearch(pageable,pageData.getSearch());
						}
						else{
							log.info("Some Issue In Pageable");
						}
					}
				}
				else {
					//log.info("FromDate => {} And ToDate => {} ",date.fromDateFormat(pageData.getFromDate()),date.fromDateFormat(pageData.getToDate()));
					if(pageData.getSearch() == null) {
						//log.info("Search Is Null");
						if(pageData.getSortOrder() == null && pageData.getSortField() == null) {
							//log.info("Sort Order and Sort Field Null");
							pageable = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize());
						}
						else {
							//log.info("Sort Order => {} and Sort Field => {}",pageData.getSortOrder(),pageData.getSortField());
							if(pageData.getSortOrder().equalsIgnoreCase("ASC")) {
								pageable = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
										Sort.by(Direction.ASC, pageData.getSortField()));
							}
							else if(pageData.getSortOrder().equalsIgnoreCase("DESC")){
								pageable = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
										Sort.by(Direction.DESC, pageData.getSortField()));
							}
							else {
								log.info("Give Valid Sort Order");
							}
						}
						if(pageable != null) {
							page = talukMasterDao.getAllWithPageAndTime(pageable,date.fromDateFormat(pageData.getFromDate()),date.toDateFormat(pageData.getToDate()));
						}
						else{
							log.info("Some Issue In Pageable");
						}
					}
					else {
						//log.info("Search => {}",pageData.getSearch());
						if(pageData.getSortOrder() == null && pageData.getSortField() == null) {
							//log.info("Sort Order and Sort Field Null");
							pageable = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize());
						}
						else {
							//log.info("Sort Order => {} and Sort Field => {}",pageData.getSortOrder(),pageData.getSortField());
							if(pageData.getSortOrder().equalsIgnoreCase("ASC")) {
								pageable = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
										Sort.by(Direction.ASC, pageData.getSortField()));
							}
							else if(pageData.getSortOrder().equalsIgnoreCase("DESC")){
								pageable = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
										Sort.by(Direction.DESC, pageData.getSortField()));
							}
							else {
								log.info("Give Valid Sort Order");
							}
						}
						if(pageable != null) {
							page = talukMasterDao.getAllWithPageAndSearchAndTime(pageable,pageData.getSearch(),date.fromDateFormat(pageData.getFromDate()),date.fromDateFormat(pageData.getToDate()));
						}
						else{
							log.info("Some Issue In Pageable");
						}
					}
				}
			}
			if (page == null || page.isEmpty()) {
				//log.info("No Record Found..");
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			} else {
				for (TalukMasterEntity entity : page) {
					TalukMasterDTO responseDTO = new TalukMasterDTO();
					responseDTO.setId(entity.getId());
					responseDTO.setTalukName(entity.getTalukName());
					responseDTO.setTalukCode(entity.getTalukCode());
					responseDTO.setDistrictId(entity.getDistrictId());
					responseDTO.setStatus(entity.getStatus());
					contentsList.add(responseDTO);
				}
				//log.info("Data Retrived Successfully");
				response.setMessage("Data Retrived Successfully");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				response.setResponseContents(contentsList);
				response.setTotalRecords(page.getTotalElements());
				response.setNumberOfElements(pageData.getPaginationSize());
				
				response.setTotalPages(page.getTotalPages());
				
			}
			//log.info("lazylist Process End");
		}
		catch (Exception e) {
			log.error("Exception => {}",e);
			response.setMessage("Exception  => "+e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		return response;
	}

	

}
