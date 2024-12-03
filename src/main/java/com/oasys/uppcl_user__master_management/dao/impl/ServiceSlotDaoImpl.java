package com.oasys.uppcl_user__master_management.dao.impl;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.clients.userservice.utils.CommonUtil;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.uppcl_user__master_management.cache.dto.ServiceSlotCacheDTO;
import com.oasys.uppcl_user__master_management.cache.impl.SlotCacheImpl;
import com.oasys.uppcl_user__master_management.dao.ServiceSlotDao;
import com.oasys.uppcl_user__master_management.dto.ServiceSlotDTO;
import com.oasys.uppcl_user__master_management.entity.ServiceSlotEntity;
import com.oasys.uppcl_user__master_management.repository.ServiceCategoryRepository;
import com.oasys.uppcl_user__master_management.repository.ServiceSlotRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ServiceSlotDaoImpl implements ServiceSlotDao {

	@Autowired
	ServiceSlotRepository serviceSlotRepository;

	@Autowired
	ServiceCategoryRepository serviceCategoryRepository;

	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	ServiceSlotDao serviceSlotDao;

	@Autowired
	MessageSource messageSource;

	@Value("${commision}")
	private String commisionBaseURL;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private HttpServletRequest headerRequest;


	@Autowired
	SlotCacheImpl slotCacheImpl;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Override
	public BaseDTO create(ServiceSlotDTO dto) {
		// log.info(" <-----ServiceSlotDaoImpl.create() Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			if (null != dto) {

				ServiceSlotEntity serviceSlotEntity = new ServiceSlotEntity();

				if (Long.parseLong(dto.getSlot().split("-")[0]) > Long.parseLong(dto.getSlot().split("-")[1])) {
					String msg = messageSource.getMessage(ResponseConstant.START_RANGE_NOT_GRETER, null, Locale.US);
					baseDTO.setMessage("For Slab, " + msg);
					baseDTO.setStatusCode(ErrorDescription.START_RANGE_NOT_GRETER.getCode());
					return baseDTO;
				}
				List<ServiceSlotEntity> serviceSlotEntities = serviceSlotRepository.isSlotRangePresnt(
						Long.parseLong(dto.getSlot().split("-")[0]), Long.parseLong(dto.getSlot().split("-")[1]),
						dto.getSubCategoryId().getId());
				if (!serviceSlotEntities.isEmpty()) {
					String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
					baseDTO.setMessage("This Slab " + msg);
					baseDTO.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				} else {
					serviceSlotEntity.setStartRange(Long.parseLong(dto.getSlot().split("-")[0]));
					serviceSlotEntity.setEndRange(Long.parseLong(dto.getSlot().split("-")[1]));

					serviceSlotEntity.setStatus(dto.getStatus());
					serviceSlotEntity.setServiceCategoryId(dto.getServiceCategoryId());
					serviceSlotEntity.setPosition(dto.getPosition());
					serviceSlotEntity.setRemarks(dto.getRemarks());
					serviceSlotEntity.setSubCategoryId(dto.getSubCategoryId());
					serviceSlotRepository.save(serviceSlotEntity);
					// put service slot in cache
					slotCacheImpl.put(serviceSlotEntity.getId().toString(), commonUtil.modalMap(serviceSlotEntity,ServiceSlotCacheDTO.class));
					if (Boolean.FALSE.equals(serviceSlotEntity.getStatus())) {
						baseDTO = deleteCommissionServiceSlot(serviceSlotEntity.getId());
						if (Objects.nonNull(baseDTO)
								&& !ErrorDescription.SUCCESS_RESPONSE.getCode().equals(baseDTO.getStatusCode())) {
							return baseDTO;
						}
					}
					String msg = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					baseDTO.setMessage(dto.getSlot() + " " + msg);
					baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				}

			} else {
				baseDTO.setMessage("No Record Found..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				// log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- ServiceSlotDaoImpl.create() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		}
		// log.info(" <-----ServiceSlotDaoImpl.create() Dao END -----> ");
		return baseDTO;
	}

	
	


	@Override
	public BaseDTO getById(UUID id) {
		// log.info(" <----- ServiceSlotDaoImpl.getById() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {

			Optional<ServiceSlotEntity> entity = serviceSlotRepository.findById(id);
			if (entity.isPresent()) {
				response.setResponseContent(entity);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());

			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<---- ServiceSlotDaoImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		// log.info(" <----- ServiceSlotDaoImpl.getById() END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		// log.info(" <-----ServiceSlotDaoImpl.getAllActive() Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			List<ServiceSlotEntity> entity = serviceSlotRepository.getAllActive();

			if (entity.isEmpty()) {
				baseDTO.setMessage("No Record Found..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				// log.info("No Record Found..");
			} else {
				baseDTO.setResponseContents(entity);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,
						Locale.US);
				baseDTO.setMessage(msg);
			}

		} catch (Exception e) {
			log.error("<----- ServiceSlotDaoImpl.getAllActive() ----->", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,
					Locale.US);
			baseDTO.setMessage(msg);
		}
		// log.info(" <-----ServiceSlotDaoImpl.getAllActive() Dao END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO getByServiceCategoryId(UUID id) {
		// log.info(" <----- ServiceSlotDaoImpl.getByServiceCategoryId() STARTED ----->
		// ");
		BaseDTO baseDTO = new BaseDTO();
		try {

			List<ServiceSlotEntity> name = serviceSlotRepository.findByCategoryId(id);

			if (!name.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				baseDTO.setResponseContents(name);
				// log.info("Successfully Fetched");
			} else {
				baseDTO.setMessage("No Record Found..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				// log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- ServiceSlotDaoImpl.getByServiceCategoryId() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		}
		// log.info(" <----- ServiceSlotDaoImpl.getByServiceCategoryId() END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO delete(UUID id) {
		// log.info(" <----- ServiceSlotDaoImpl.delete() Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			ServiceSlotEntity entity = serviceSlotRepository.getOne(id);
			if (entity.getStatus() == true) {
//						serviceSlotRepository.delete(entity);
				entity.setStatus(false);
				serviceSlotRepository.save(entity);
				slotCacheImpl.delete(entity.getId().toString());
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,
						Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				// log.info("Successfully Deleted");
			} else {
				baseDTO.setMessage("Record has been deleted already..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				// log.error("No Record Found..");
			}

		} catch (Exception e) {
			log.error("<---- ServiceSlotDaoImpl.delete() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		}
		// log.info(" <----- ServiceSlotDaoImpl.delete() Dao END -----> ");
		return baseDTO;
	}

	private ServiceSlotEntity updatedValues(ServiceSlotEntity master, ServiceSlotDTO dto) {
		ServiceSlotEntity entity = master;
		entity.setServiceCategoryId(dto.getServiceCategoryId());
		entity.setStartRange(Long.parseLong(dto.getSlot().split("-")[0]));
		entity.setEndRange(Long.parseLong(dto.getSlot().split("-")[1]));
		entity.setStatus(dto.getStatus());
		entity.setPosition(dto.getPosition());
		return entity;
	}

	
	
	@Override
	public BaseDTO update(UUID id, ServiceSlotDTO dto) {
		//log.info(" <----- ServiceSlotDaoImpl.update() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		if(Long.parseLong(dto.getSlot().split("-")[0]) > Long.parseLong(dto.getSlot().split("-")[1]))
		{
			String msg = messageSource.getMessage(ResponseConstant.START_RANGE_NOT_GRETER, null,
					Locale.US);
			response.setMessage("For Slab, " + msg);
			response.setStatusCode(ErrorDescription.START_RANGE_NOT_GRETER.getCode());
			return response;
		}
		try {
			boolean check = false;
			Optional<ServiceSlotEntity> optional = serviceSlotRepository.findById(id);
			if (optional.isPresent()) {
				ServiceSlotEntity entity2 = new ServiceSlotEntity();
				
				entity2 = optional.get();
				
				List<ServiceSlotEntity> serviceSlotEntities = serviceSlotRepository.isSlotRangePresnt(
						Long.parseLong(dto.getSlot().split("-")[0]),Long.parseLong(dto.getSlot().split("-")[1]),
						dto.getSubCategoryId().getId());
				
				
				Optional<ServiceSlotEntity> servOptional = serviceSlotEntities.stream().
						filter(obj->!obj.getId().equals(optional.get().getId())).findAny();						
				if(servOptional.isPresent()) {
					String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,
							Locale.US);
					response.setMessage("This Slab " + msg);
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					return response;
				}
				
				List<ServiceSlotEntity> checkCode = serviceSlotRepository
						.checkCode(Long.parseLong(dto.getSlot().split("-")[0]),Long.parseLong(dto.getSlot().split("-")[1]));
				for (ServiceSlotEntity entity : checkCode) {
					if ((entity.getStartRange()+"-"+entity.getEndRange()).equals(dto.getSlot())) {
						if (id.equals(entity.getId())) {
							check = false;
						} else
							check = true;
					} else {
						check = false;
					}
				}

		if(dto.getId() != null) {
					entity2.setStartRange(Long.parseLong(dto.getSlot().split("-")[0]));
					entity2.setEndRange(Long.parseLong(dto.getSlot().split("-")[1]));				
					entity2.setServiceCategoryId(dto.getServiceCategoryId());
					entity2.setStatus(dto.getStatus());
					entity2.setPosition(dto.getPosition());
					entity2.setSubCategoryId(dto.getSubCategoryId());
					entity2.setRemarks(dto.getRemarks());
					ServiceSlotEntity afterUpdate = serviceSlotRepository.save(entity2);
					slotCacheImpl.put(entity2.getId().toString(),commonUtil.modalMap(entity2,ServiceSlotCacheDTO.class));
					response.setResponseContent(afterUpdate);
					if(Boolean.FALSE.equals(entity2.getStatus())) {
						response = deleteCommissionServiceSlot(entity2.getId());
						if (Objects.nonNull(response) 
								&& !ErrorDescription.SUCCESS_RESPONSE.getCode().equals(response.getStatusCode())) {
							return response;
						}
					}
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(dto.getSlot() + " " + msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					
		}

			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- ServiceSlotDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());

		} catch (Exception e) {
			log.error("<---- ServiceSlotDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info(" <----- ServiceSlotDaoImpl.update() END -----> ");
		return response;
	}

	public BaseDTO deleteCommissionServiceSlot(UUID slotId) {
		BaseDTO response = new BaseDTO();
		ResponseEntity<BaseDTO> responseDTO = null;
		final String url = commisionBaseURL + "/inactive/" + slotId;
		String access_token = headerRequest.getHeader("X-Authorization");
		HttpHeaders header = new HttpHeaders();
		header.set("Content-type", "application/json");
		header.set("X-Authorization", access_token);
		HttpEntity<BaseDTO> entity = new HttpEntity<BaseDTO>(header);
		log.info("calling commission api to delete inactive commission:: url :{}", url);
		try {
			responseDTO = restTemplate.exchange(url, HttpMethod.DELETE, entity, BaseDTO.class);
		} catch (Exception e) {
			log.info("Error occurred while deleting commission service slot :: url :{}, exception :{}", url, e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage(messageSource
					.getMessage(ResponseConstant.FAILED_TO_DELETE_COMMISSION_RECORD_RESPONSE_MESSAGE, null, Locale.US));
			return response;
		}
		log.info("response :: {}", responseDTO);
		if (Objects.nonNull(responseDTO) && Objects.nonNull(responseDTO.getBody())
				&& ErrorDescription.SUCCESS_RESPONSE.getCode().equals(responseDTO.getBody().getStatusCode())) {
			return responseDTO.getBody();
		} else {
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage(messageSource
					.getMessage(ResponseConstant.FAILED_TO_DELETE_COMMISSION_RECORD_RESPONSE_MESSAGE, null, Locale.US));
			return response;
		}
	}

}
