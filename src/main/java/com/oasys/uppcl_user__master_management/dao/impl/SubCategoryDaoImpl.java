package com.oasys.uppcl_user__master_management.dao.impl;




import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.clients.userservice.utils.CommonUtil;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.uppcl_user__master_management.cache.dto.SubCategoryCacheDTO;
import com.oasys.uppcl_user__master_management.cache.impl.SubCategoryCacheImpl;
import com.oasys.uppcl_user__master_management.dao.SubCategoryDao;
import com.oasys.uppcl_user__master_management.dto.SearchDTO;
import com.oasys.uppcl_user__master_management.dto.SubCategoryDTO;
import com.oasys.uppcl_user__master_management.entity.ServiceCategoryEntity;
import com.oasys.uppcl_user__master_management.entity.ServiceProviderEntity;
import com.oasys.uppcl_user__master_management.entity.SubCategoryEntity;
import com.oasys.uppcl_user__master_management.repository.ServiceCategoryRepository;
import com.oasys.uppcl_user__master_management.repository.ServiceProviderRepository;
import com.oasys.uppcl_user__master_management.repository.SubCategoryRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class SubCategoryDaoImpl implements SubCategoryDao {

	@Autowired
	SubCategoryRepository subCategoryRepository;

	@Autowired
	ServiceCategoryRepository serviceCategoryRepository;
	
	@Autowired
	ServiceProviderRepository ServiceProviderRepository;

	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	SubCategoryDao subCategoryDao;

	@Autowired
	MessageSource messageSource;
	
	@Autowired
	SubCategoryCacheImpl subCategoryCacheImpl;
	
	@Autowired
	CommonUtil commonUtil;

	@Override
	public BaseDTO create(SubCategoryDTO dto) {
		// log.info(" <-----SubCategoryDaoImpl.create() Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			if (null != dto) {

				SubCategoryEntity subCategoryEntity = new SubCategoryEntity();

				Optional<SubCategoryEntity> optional = subCategoryRepository.check(dto.getName().toUpperCase(),
						dto.getServiceCategoryId().getId());
				if (optional.isPresent()) {
					String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
					baseDTO.setMessage("This Sub-Service Category " + msg);
					baseDTO.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				}

				else {
					subCategoryEntity.setName(dto.getName());
					subCategoryEntity.setStatus(dto.getStatus());
					subCategoryEntity.setServiceCategoryId(dto.getServiceCategoryId());
					subCategoryEntity.setMinAmount(0.0);
					subCategoryEntity.setMaxAmount(0.0);
					subCategoryEntity.setSubscriberId(null);
					subCategoryEntity.setLength(null);
					subCategoryEntity.setConstantName(dto.getServiceCategoryId().getName());
					subCategoryEntity.setRemarks(dto.getRemarks());
					subCategoryEntity = subCategoryRepository.save(subCategoryEntity);
					subCategoryCacheImpl.put(subCategoryEntity.getId().toString(),commonUtil.modalMap(subCategoryEntity, SubCategoryCacheDTO.class) );
					String msg = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					baseDTO.setMessage(dto.getName() + " " + msg);
					baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				}

			} else {
				baseDTO.setMessage("No Record Found..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				// log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- SubCategoryDaoImpl.create() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		}
		// log.info(" <-----SubCategoryDaoImpl.create() Dao END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO getAllActive() {
		// log.info(" <-----SubCategoryDaoImpl.getAllActive() Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			List<SubCategoryEntity> districtName = subCategoryRepository.getAllActive();

			if (districtName.isEmpty()) {
				baseDTO.setMessage("No Record Found..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				// log.info("No Record Found..");
			} else {
				baseDTO.setResponseContents(districtName);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,
						Locale.US);
				baseDTO.setMessage(msg);

			}

		} catch (Exception e) {
			log.error("<----- SubCategoryDaoImpl.getAllActive() ----->", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,
					Locale.US);
			baseDTO.setMessage(msg);
		}
		// log.info(" <-----SubCategoryDaoImpl.getAllActive() Dao END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO getByServiceCategoryId(UUID id) {
		// log.info(" <----- SubCategoryDaoImpl.getByServiceCategoryId() STARTED ----->
		// ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			List<SubCategoryEntity> entity = subCategoryRepository.findByServiceCategoryId(id);
			if (!entity.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				baseDTO.setResponseContents(entity);
				// log.info("Successfully Fetched");
			} else {
				baseDTO.setMessage("No Record Found..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				// log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- SubCategoryDaoImpl.getByServiceCategoryId() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		}
		// log.info(" <----- SubCategoryDaoImpl.getByServiceCategoryId() END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO getById(UUID id) {
		// log.info(" <----- ServiceproviderDaoImpl.getById() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		UUID serviceProviderId = null;
		String serviceProvideName = null;
		try {
			Optional<SubCategoryEntity> entity = subCategoryRepository.findById(id);
			SubCategoryEntity serviceCategoryEntity = entity.get();
			if (entity.isPresent()) {
				List<ServiceProviderEntity> providerId = ServiceProviderRepository.findByCategoryId(serviceCategoryEntity.getServiceCategoryId().getId());
				if(providerId.size()>0) {
				for(ServiceProviderEntity getProviderId : providerId)
				{
					serviceProviderId = getProviderId.getId();
					serviceProvideName = getProviderId.getServiceProviderName();
				}
				serviceCategoryEntity.setServiceProviderId(serviceProviderId);
				serviceCategoryEntity.setServiceProviderName(serviceProvideName);
				}else
				{
					serviceCategoryEntity.setServiceProviderId(null);
					serviceCategoryEntity.setServiceProviderName(null);
				}
				response.setResponseContent(serviceCategoryEntity);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());

			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<---- ServiceproviderDaoImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		// log.info(" <----- ServiceproviderDaoImpl.getById() END -----> ");
		return response;
	}

	@Override
	public BaseDTO delete(UUID id) {
		// log.info(" <----- SubCategoryDaoImpl.delete() Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			SubCategoryEntity entity = subCategoryRepository.getOne(id);
			if (entity != null) {
				subCategoryRepository.delete(entity);
				subCategoryCacheImpl.delete(entity.getId().toString());
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,
						Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				// log.info("Successfully Deleted");
			} else {
				baseDTO.setMessage("No Record Found..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				// log.error("No Record Found..");
			}

		} catch (Exception e) {
			log.error("<---- SubCategoryDaoImpl.delete() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		}
		// log.info(" <----- SubCategoryDaoImpl.delete() Dao END -----> ");
		return baseDTO;
	}

	private SubCategoryEntity updatedValues(SubCategoryEntity districtMaster, SubCategoryDTO dto) {
		SubCategoryEntity districtName = districtMaster;
		districtName.setServiceCategoryId(dto.getServiceCategoryId());
		districtName.setName(dto.getName());
		districtName.setStatus(dto.getStatus());

		return districtName;
	}

	@Override
	public BaseDTO update(UUID id, SubCategoryDTO dto) {
		// log.info(" <----- SubCategoryDaoImpl.update() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			boolean check = false;
			Optional<SubCategoryEntity> optional = subCategoryRepository.findById(id);
			if (optional.isPresent()) {
				SubCategoryEntity districtName = new SubCategoryEntity();
				districtName = optional.get();
				List<SubCategoryEntity> checkCode = subCategoryRepository.checkCode(dto.getName());
				for (SubCategoryEntity entity : checkCode) {
					if (entity.getName().equalsIgnoreCase(dto.getName())) {
						if (id.equals(entity.getId())) {
							check = false;
						} else
							check = true;
					} else {
						check = false;
					}
				}

				if (check) {
					String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
					response.setMessage("This Sub-Service Category " + msg);
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				} else {
					districtName.setName(dto.getName());
					districtName.setServiceCategoryId(dto.getServiceCategoryId());
					districtName.setStatus(dto.getStatus());
					districtName.setMinAmount(optional.get().getMinAmount());
					districtName.setMaxAmount(optional.get().getMaxAmount());
					districtName.setSubscriberId(optional.get().getSubscriberId());
					districtName.setLength(optional.get().getLength());
					districtName.setConstantName(optional.get().getConstantName());
					districtName.setRemarks(dto.getRemarks());
					SubCategoryEntity afterUpdate = subCategoryRepository.save(districtName);
					subCategoryCacheImpl.put(afterUpdate.getId().toString(),commonUtil.modalMap(afterUpdate, SubCategoryCacheDTO.class));
					response.setResponseContent(afterUpdate);
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(dto.getName() + " " + msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());

				}

			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				// log.warn("No Record Found..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- SubCategoryDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());

		} catch (Exception e) {
			log.error("<---- SubCategoryDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		// log.info(" <----- SubCategoryDaoImpl.update() END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAllActiveWithSearch(SearchDTO searchDTO) {
		// log.info(" <-----SubCategoryDaoImpl.getAllActive() Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			List<SubCategoryEntity> districtName = null;

			if (searchDTO.getSearch() == null) {
				districtName = subCategoryRepository.getAllActive();
			} else {
				districtName = subCategoryRepository.getAllActiveWithSearch(searchDTO.getSearch());
			}

			if (districtName.isEmpty()) {
				baseDTO.setMessage("No Record Found..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				// log.info("No Record Found..");
			} else {
				baseDTO.setResponseContents(districtName);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,
						Locale.US);
				baseDTO.setMessage(msg);

			}

		} catch (Exception e) {
			log.error("<----- SubCategoryDaoImpl.getAllActive() ----->", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,
					Locale.US);
			baseDTO.setMessage(msg);
		}
		// log.info(" <-----SubCategoryDaoImpl.getAllActive() Dao END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO getByServiceCategoryByName(String name) {
		// log.info(" <----- SubCategoryDaoImpl.getByServiceCategoryId() STARTED ----->
		// ");
		BaseDTO baseDTO = new BaseDTO();
		UUID serviceProviderId = null;
		String serviceProvideName = null;
		try {
			List<SubCategoryEntity> entity = subCategoryRepository.findByConstantNameIgnoreCase(name);
			List<SubCategoryEntity> subCategoryEntity = new ArrayList<SubCategoryEntity>();
			if (!entity.isEmpty()) {
				for(SubCategoryEntity getEntity : entity)
				{
					List<ServiceProviderEntity> providerId = ServiceProviderRepository.findByCategoryId(getEntity.getServiceCategoryId().getId());
					log.info("provider ==> "+ providerId);
					
					if(providerId.size()>0) {
						for(ServiceProviderEntity getProviderId : providerId)
						{
							serviceProviderId = getProviderId.getId();
							serviceProvideName = getProviderId.getServiceProviderName();
						}
						getEntity.setServiceProviderId(serviceProviderId);
						getEntity.setServiceProviderName(serviceProvideName);
						}else
						{
							getEntity.setServiceProviderId(null);
							getEntity.setServiceProviderName(null);
						}
					subCategoryEntity.add(getEntity);
				}
				
				
				
				baseDTO.setResponseContents(subCategoryEntity);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());

			} else {
				baseDTO.setMessage("No Record Found..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}	
			
		} catch (Exception e) {
			log.error("<---- SubCategoryDaoImpl.getByServiceCategoryByName() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		}
		// log.info(" <----- SubCategoryDaoImpl.getByServiceCategoryId() END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO getByName(String name) {
		// log.info(" <----- SubCategoryDaoImpl.getByServiceCategoryId() STARTED ----->
				// ");
				BaseDTO baseDTO = new BaseDTO();
				ServiceCategoryEntity serviceCategoryEntity = new ServiceCategoryEntity();
				ServiceProviderEntity serviceProviderEntity = new ServiceProviderEntity();
				List<SubCategoryEntity> entity = new ArrayList<SubCategoryEntity>();
				try {
					if(name.equalsIgnoreCase("MOBILE RECHARGE"))
					{
					 serviceCategoryEntity = serviceCategoryRepository.getByName("Mobile Recharge");
					}else if(name.equalsIgnoreCase("DTH RECHARGE"))
					{
					 serviceCategoryEntity = serviceCategoryRepository.getByName("DTH Recharge");	
					}else if(name.equalsIgnoreCase("PayU"))
					{
						serviceCategoryEntity = serviceCategoryRepository.getByName("Payment Gateway");	
					}
					else if(name.equalsIgnoreCase("Razorpay"))
					{
						serviceCategoryEntity = serviceCategoryRepository.getByName("Payment Gateway");	
					}
					if(serviceCategoryEntity != null)
					{
					 entity = subCategoryRepository.findByServiceCategoryId(serviceCategoryEntity.getId());
					}
					
					if (!entity.isEmpty()) {
						String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
						baseDTO.setMessage(msg);
						baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
						baseDTO.setResponseContents(entity);
						// log.info("Successfully Fetched");
					} else {
						baseDTO.setMessage("No Record Found..");
						baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
						// log.error("No Record Found..");
					}
				} catch (Exception e) {
					log.error("<---- SubCategoryDaoImpl.getByServiceCategoryByName() ----> EXCEPTION", e);
					baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
					baseDTO.setMessage(msg);
				}
				// log.info(" <----- SubCategoryDaoImpl.getByServiceCategoryId() END -----> ");
				return baseDTO;
	}

	@Override
	public BaseDTO getByServiceAndSubcategory(String serviceName, String categoryName) {
		// TODO Auto-generated method stub
		BaseDTO baseDTO = new BaseDTO();
		ServiceCategoryEntity serviceCategoryEntity = new ServiceCategoryEntity();
		SubCategoryEntity entity = null;
		UUID serviceProviderId = null;
		try {
			
			 serviceCategoryEntity = serviceCategoryRepository.getByName(serviceName);
			
			if(serviceCategoryEntity != null)
			{
			 entity = subCategoryRepository.findByServiceAndCategory(serviceCategoryEntity.getId(),categoryName);
			}
			
			if (entity != null) {
				//ServiceProviderEntity providerId = ServiceProviderRepository.findByServiceCategoryId(entity.getServiceCategoryId().getId());
				//log.info("provider ==> "+ providerId);
				
//				if(providerId != null) {
//					
//					entity.setServiceProviderId(providerId.getId());
//					
//					}else
//					{
//					entity.setServiceProviderId(null);
//						
//					}
				
				
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				baseDTO.setResponseContent(entity);
				// log.info("Successfully Fetched");
			} else {
				baseDTO.setMessage("No Record Found..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				// log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- SubCategoryDaoImpl.getByServiceAndCategory() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		}
		// log.info(" <----- SubCategoryDaoImpl.getByServiceCategoryId() END -----> ");
		return baseDTO;
	}
	public BaseDTO getAllOrderByModifiedDate() {
		BaseDTO response = new BaseDTO();
		try {
			List<SubCategoryEntity> entity = subCategoryRepository.getAllOrderByModifiedDate();

			if (entity.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg );
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				
			} else {
				response.setResponseContents(entity);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
			
			}
		} catch (Exception e) {
			log.error("<----- SubCategoryDaoImpl.getAllOrderByModifiedDate() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		
		return response;
	}

}

