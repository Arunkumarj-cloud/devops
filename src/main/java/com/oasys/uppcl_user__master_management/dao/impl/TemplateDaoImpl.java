package com.oasys.uppcl_user__master_management.dao.impl;






import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.TemplateDao;
import com.oasys.uppcl_user__master_management.dto.TemplateDTO;
import com.oasys.uppcl_user__master_management.entity.ProjectTypeEntity;
import com.oasys.uppcl_user__master_management.entity.TemplateEntity;
import com.oasys.uppcl_user__master_management.entity.TemplateViewEntity;
import com.oasys.uppcl_user__master_management.repository.ProjectTypeRepository;
import com.oasys.uppcl_user__master_management.repository.TemlateRepo;
import com.oasys.uppcl_user__master_management.repository.TemplateViewRepo;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TemplateDaoImpl implements TemplateDao {

	@Autowired
	TemlateRepo templateRepo;

	@Autowired
	ObjectMapper mapper;

	@Autowired
	TemplateViewRepo templateViewRepo;

	@Autowired
	MessageSource messageSource;

	@Autowired
	HttpServletRequest headerRequest;

	@Autowired
	RestTemplate restTemplate;


	@Autowired
	private ProjectTypeRepository projectTypeRepository;
	
	
	@Override
	public BaseDTO createTemplate(TemplateDTO templateDTO) {
		// log.info("<--------Started TemplateDaoImpl.createTemplate()-----> ");
		BaseDTO response = new BaseDTO();
		TemplateEntity entity = null;
		try {

			entity = templateRepo.findByNameIgnoreCase(templateDTO.getName());
			if (entity != null) {
				String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
				response.setMessage("This Notification Template is Already Exist");
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				// log.info("Template Name is Already Exist",templateDTO.getName());
				return response;

			} else {
				entity = new TemplateEntity();
				entity.setName(templateDTO.getName());
				entity.setType(templateDTO.getType());
			
				entity.setContent(templateDTO.getTemplateContent());
				entity.setAppId(templateDTO.getAppId());
				entity.setNotificationNetwork(templateDTO.getNotificationNetwork());
				entity.setStatus(templateDTO.getStatus());
		
				entity.setTemp_type(templateDTO.getTemp_type());
				entity.setRemarks(templateDTO.getRemarks());
				entity.setServiceCategoryId(templateDTO.getServiceId());
				entity.setSubCategoryId(templateDTO.getSubServiceId());
                entity.setTemplate_id(templateDTO.getTemplate_id());
                entity.setCreatedDate(new Date());
				templateRepo.save(entity);
				response.setMessage("Created Successfully ");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			}
		} catch (Exception e) {
			log.error(" <--------Exception at  TemplateDaoImpl.createTemplate()----->");
		}
		// log.info("<--------Ended TemplateDaoImpl.createTemplate()-----> ");
		return response;
		
	}

	/*
	 * private String gettempId() { String transactionId = "0000"; try {
	 * SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS"); Date
	 * now = new Date(); String strDate = sdfDate.format(now);
	 * 
	 * transactionId = strDate;
	 * 
	 * } catch (Exception e) {
	 * 
	 * } return transactionId; }
	 */
	@Override
	public BaseDTO getById(UUID id) {
		// log.info(" <----- TemplateDaoImpl.getById() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			TemplateEntity template=new TemplateEntity();
			template=templateRepo.findById1(id);
			//Optional<TemplateEntity> template = templateRepo.findById(id);
			log.info("id -{}",template);
			if (template!=null) {
				 log.info("id -{}",id);
				response.setResponseContent(template);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				// log.info("Successfully Get template Details ");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				// log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- TemplateDaoImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		// log.info(" <----- TemplateDaoImpl.getById() END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAll() {
		// log.info("<--------Started TemplateDaoImpl.getAll()-----> ");
		BaseDTO response = new BaseDTO();
		List<TemplateEntity> list = new ArrayList<TemplateEntity>();
		List<TemplateDTO> totallist = new ArrayList<TemplateDTO>();
		try {
			List<TemplateEntity> templateEntity=templateRepo.findAll();
			
			for(TemplateEntity obj : templateEntity ) {
				TemplateDTO resp=mapper.convertValue(obj, TemplateDTO.class);
				resp.setId(obj.getId());
				resp.setName(obj.getName());
				resp.setNotificationChannel(obj.getType());
				resp.setTemplateContent(obj.getContent());
				resp.setNotificationNetwork(obj.getNotificationNetwork());
				resp.setStatus(obj.getStatus());
				resp.setApplicationId(obj.getAppId());
		
				resp.setCreatedDate(obj.getCreatedDate());
				resp.setModifiedDate(obj.getModifiedDate());
				resp.setApplicationName(getappName(obj.getAppId()));
				
				resp.setRemarks(obj.getRemarks());
				resp.setTemplateType(obj.getTemp_type());
				resp.setServiceId(obj.getServiceCategoryId());
				resp.setSubServiceId(obj.getSubCategoryId());
				resp.setTemplateId(obj.getTemplate_id());
				totallist.add(resp);
				log.info("list" + totallist);
			}
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			response.setResponseContent(totallist);
		}catch (Exception e) {
			log.error(" <--------Exception at  TemplateDaoImpl.getAll()----->");
		}return response;
		

	}
	
	@Override
	public BaseDTO get() {
		BaseDTO response = new BaseDTO();
		List<TemplateEntity> list = new ArrayList<TemplateEntity>();
		List<TemplateDTO> totallist = new ArrayList<TemplateDTO>();
		try {
			List<TemplateEntity> templateEntity=templateRepo.findAll();
			
			for(TemplateEntity obj : templateEntity ) {
				TemplateDTO resp=mapper.convertValue(obj, TemplateDTO.class);
				resp.setId(obj.getId());
				resp.setName(obj.getName());
				resp.setNotificationChannel(obj.getType());
				resp.setTemplateContent(obj.getContent());
				resp.setNotificationNetwork(obj.getNotificationNetwork());
				resp.setStatus(obj.getStatus());
				resp.setApplicationId(obj.getAppId());
		
				resp.setCreatedDate(obj.getCreatedDate());
				resp.setModifiedDate(obj.getModifiedDate());
				resp.setApplicationName(getappName(obj.getAppId()));
				
				resp.setRemarks(obj.getRemarks());
				resp.setTemplateType(obj.getTemp_type());
				resp.setServiceId(obj.getServiceCategoryId());
				resp.setSubServiceId(obj.getSubCategoryId());
				resp.setTemplateId(obj.getTemplate_id());
				totallist.add(resp);
				log.info("list" + totallist);
			}
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			response.setResponseContent(totallist);
		}catch (Exception e) {
			log.error(" <--------Exception at  TemplateDaoImpl.getAll()----->");
		}return response;
		
	}
	

	@Override
	public BaseDTO getByName(String name) {
		// log.info("<--------Started TemplateDaoImpl.getByName()-----> ");
		BaseDTO response = new BaseDTO();
		try {
			Optional<TemplateEntity> templateEntity = templateRepo.findByName(name);
			if (templateEntity.isPresent()) {
				response.setResponseContent(templateEntity);
				response.setStatusCode(200);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				// log.info(" API Details Name is = " + name);
			} else {
				// log.info("API Details Not Found for Name = " + name);
				response.setStatusCode(204);
				response.setMessage("No Record Found");
			}

		} catch (Exception e) {
			log.error("<--------Exception at  TemplateDaoImpl.getByName()-----> ");
			log.error("  Exception Name  : " + e.getLocalizedMessage());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		// log.info(" <--------Ended TemplateDaoImpl.getByName() ----> ");
		return response;
	}

	@Override
	public BaseDTO lazyList(PaginationRequestDTO pageData) {
		// log.info("<--------Started TemplateDaoImpl.lazyList()-----> ");
		BaseDTO response = new BaseDTO();
		Page<TemplateViewEntity> templateViewEntity = null;
		List<TemplateViewEntity> contentList = new ArrayList<TemplateViewEntity>();
		Pageable pageRequest;
		try {
			// log.info(" <--------1----- TemplateDaoImpl.lazyList() ----> ");
			if (pageData.getSearch() != null && !pageData.getSearch().isEmpty()) {
				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.ASC, pageData.getSortOrder()));
					templateViewEntity = templateViewRepo.search(pageRequest, pageData.getSearch());

				} else {
					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.DESC, pageData.getSortField()));
					templateViewEntity = templateViewRepo.search(pageRequest, pageData.getSearch());
				}
			} else {
				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					templateViewEntity = templateViewRepo.findAll(PageRequest.of(pageData.getPageNo(),
							pageData.getPaginationSize(), Sort.by(Direction.ASC, pageData.getSortField())));
				} else {
					templateViewEntity = templateViewRepo.findAll(PageRequest.of(pageData.getPageNo(),
							pageData.getPaginationSize(), Sort.by(Direction.DESC, pageData.getSortField())));
				}

			}
			if (templateViewEntity != null) {
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				response.setNumberOfElements(templateViewEntity.getNumberOfElements());
				response.setTotalRecords(templateViewEntity.getTotalElements());
				response.setTotalPages(templateViewEntity.getTotalPages());
				for (TemplateViewEntity templateList : templateViewEntity) {
					contentList.add(templateList);
				}
				response.setResponseContents(contentList);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				// log.info("Template Details Fetched Successfully");

			}
		} catch (Exception e) {
			log.error("<------- Exception TemplateDaoImpl.lazyList() ------>", e);
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		// log.info("<-----------Ended TemplateDaoImpl.lazyList() Ended ------------>");
		return response;
	}

	@Override
	public BaseDTO lazyList1(PaginationRequestDTO pageData) {
		if(pageData.getSortField().equalsIgnoreCase("notificationChannel")) {
			pageData.setSortField("type");
		}if(pageData.getSortField().equalsIgnoreCase("applicationName")) {
			pageData.setSortField("createdDate");
		}
			
		BaseDTO response = new BaseDTO();
		List<TemplateEntity> list = new ArrayList<TemplateEntity>();
		List<TemplateDTO> totallist = new ArrayList<TemplateDTO>();
		Pageable pageable = null;
		Page<TemplateEntity> lazylist = null;
		try {
			if (pageData.getSearch() != null && !pageData.getSearch().isEmpty()) {
				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageable = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.ASC, pageData.getSortOrder()));
					lazylist = templateRepo.search(pageable, pageData.getSearch());

				} else {
					pageable = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.DESC, pageData.getSortField()));
					lazylist = templateRepo.search(pageable, pageData.getSearch());
				}
			} else {
				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					lazylist = templateRepo.findAll(PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.ASC, pageData.getSortField())));
				} else {
					lazylist = templateRepo.findAll(PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.DESC, pageData.getSortField())));
				}

			}
			if (lazylist != null) {
				for (TemplateEntity obj : lazylist) {
					TemplateDTO resp = mapper.convertValue(obj, TemplateDTO.class);
					resp.setId(obj.getId());
					resp.setName(obj.getName());
					resp.setNotificationChannel(obj.getType());
					resp.setTemplateContent(obj.getContent());
					resp.setNotificationNetwork(obj.getNotificationNetwork());
					resp.setStatus(obj.getStatus());
					resp.setApplicationId(obj.getAppId());
					resp.setCreatedDate(obj.getCreatedDate());
					resp.setApplicationName(getappName(obj.getAppId()));
					totallist.add(resp);
					log.info("list" + totallist);

				}
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				response.setNumberOfElements(lazylist.getNumberOfElements());
				response.setTotalRecords(lazylist.getTotalElements());
				response.setTotalPages(lazylist.getTotalPages());
				response.setResponseContents(totallist);
			}
		} catch (Exception e) {
			log.error("<------- Exception TemplateDaoImpl.lazyList() ------>", e);
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		return response;
	}

	@Override
	public BaseDTO update(UUID id, TemplateDTO templateDTO) {
		// log.info("<--------Started TemplateDaoImpl.updateTemplate()-----> ");
		BaseDTO response = new BaseDTO();
		TemplateEntity templateEntity = new TemplateEntity();
		try {

			boolean name = false;
			Optional<TemplateEntity> optional = templateRepo.findById(id);

			if (templateDTO.getName() == null || templateDTO.getName().trim().length() == 0) {
				response.setMessage("Name  Required");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				return response;
			}

			if (optional.isPresent()) {
				// log.info("template Name id -{}",id);

				templateEntity = optional.get();
				List<TemplateEntity> checkCode = templateRepo.check1(templateDTO.getName());
				for (TemplateEntity entity : checkCode) {
					if (entity.getName().equalsIgnoreCase(templateDTO.getName())) {
						if (id.equals(entity.getId())) {
							name = false;
						} else
							name = true;
					} else {
						name = false;
					}
				}

				if (templateDTO.getName() =="") {
					String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
					response.setMessage("This Notification Template " + msg);
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					// log.info(" Template Name is -{} Already Exists",templateDTO.getName());
				} else {

					templateEntity.setName(templateDTO.getName());
					templateEntity.setType(templateDTO.getType());
					templateEntity.setContent(templateDTO.getTemplateContent().toString());
					templateEntity.setAppId(templateDTO.getAppId());
					templateEntity.setNotificationNetwork(templateDTO.getNotificationNetwork());
					templateEntity.setStatus(templateDTO.getStatus());
					templateEntity.setTemp_type(templateDTO.getTemp_type());
					templateEntity.setRemarks(templateDTO.getRemarks());
					templateEntity.setServiceCategoryId(templateDTO.getServiceId());
					templateEntity.setSubCategoryId(templateDTO.getSubServiceId());
					templateEntity.setTemplate_id(templateDTO.getTemplate_id());
					templateEntity.setModifiedDate(new Date());
					templateRepo.save(templateEntity);
					response.setMessage("Update Successfully ");
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				}
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				// log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error(" <--------Exception at  TemplateDaoImpl.updateTemplate()----->");
		}
		// log.info("<--------Ended TemplateDaoImpl.updateTemplate()-----> ");
		return response;

	}

	@Override
	public BaseDTO getAllActive() {
		// log.info(" <----- TemplateDaoImpl.getAllActive() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<TemplateEntity> fieldName = templateRepo.getAllactive();

			if (fieldName.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				// log.warn("No Record Found..");
			} else {
				response.setResponseContents(fieldName);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				// log.info("Template Get All Active Details Fetched Successfully");
			}
		} catch (Exception e) {
			log.error("<----- TemplateDaoImpl.getAllActive() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		// log.info(" <----- TemplateDaoImpl.getAllActive() END -----> ");
		return response;
	}

	@Override
	public BaseDTO softDelete(UUID id) {
		// log.info(" <----- TemplateDaoImpl softDelete() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			TemplateEntity master = templateRepo.getOne(id);
			if (master != null) {
				master.setStatus(false);
				templateRepo.save(master);
				response.setMessage("Successfully Deleted");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				// log.info("Successfully Deleted");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				// log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- TemplateDaoImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete ");
		}
		// log.info(" <----- TemplateDaoImpl softDelete() END -----> ");
		return response;
	}

	public String getappName(UUID appId) {
		String ApplicationName = null;
		Optional<ProjectTypeEntity> projectTypeEntity = projectTypeRepository.findById(appId);
		if(projectTypeEntity.isPresent()) {
			ApplicationName = projectTypeEntity.get().getName();
		}
		return ApplicationName;
	}

}
