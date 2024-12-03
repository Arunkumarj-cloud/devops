package com.oasys.uppcl_user__master_management.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.uppcl_user__master_management.dao.CityMasterDao;
import com.oasys.uppcl_user__master_management.dto.CityMasterDTO;
import com.oasys.uppcl_user__master_management.entity.CityMasterEntity;
import com.oasys.uppcl_user__master_management.repository.CityMasterRepository;
import com.oasys.uppcl_user__master_management.repository.DistrictMasterRepository;
import com.oasys.uppcl_user__master_management.repository.StateMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CityMasterDaoImpl implements CityMasterDao {
	@Autowired
	CityMasterRepository cityMasterRepository;

	@Autowired
	DistrictMasterRepository districtMasterRepository;

	@Autowired
	StateMasterRepository stateMasterRepository;

	@Autowired
	CityMasterDao cityMasterDao;

	@Autowired
	ObjectMapper objectMapper;

	
	@Autowired
	MessageSource messageSource;

	@Override
	public BaseDTO create(CityMasterDTO cityMasterDTO) {
		
		//log.info(" <-----CityDaoImpl.create() Dao STARTED -----> ");
		BaseDTO baseDTO=new BaseDTO();
		try {
			
			if(null != cityMasterDTO) {
				 CityMasterEntity cityMasterEntity = new CityMasterEntity();
			
				 if (cityMasterDTO.getStateId() != null) {
					 
					 if (stateMasterRepository.findById(cityMasterDTO.getStateId().getId()).isPresent()) {
						 if (cityMasterDTO.getDistrictId() != null) {
							 
							 if (districtMasterRepository.findById(cityMasterDTO.getDistrictId().getId()).isPresent()) {
								 
								 Optional<CityMasterEntity> optional =cityMasterRepository.check(cityMasterDTO.getCityName(),cityMasterDTO.getStateId().getId(),cityMasterDTO.getDistrictId().getId());
								 
								  if (optional.isPresent()) {
					                    //String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,Locale.US);
							            baseDTO.setMessage("This City Name Already Exist.");
										baseDTO.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
										//log.info("City Name - {}  Already Exist", cityMasterDTO.getCityName(),cityMasterDTO.getStateId().getId(),cityMasterDTO.getDistrictId().getId());
									}else {
										cityMasterEntity.setCityName(cityMasterDTO.getCityName());
										cityMasterEntity.setStatus(cityMasterDTO.getStatus());
										cityMasterEntity.setStateId(cityMasterDTO.getStateId());
										cityMasterEntity.setDistrictId(cityMasterDTO.getDistrictId());
								
										cityMasterRepository.save(cityMasterEntity);
										
										String msg = messageSource.getMessage(
												ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
										baseDTO.setMessage(cityMasterDTO.getCityName() + " " + msg);
										baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());	
									}
							 }else {
									baseDTO.setMessage("District not found");
									baseDTO.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
									//log.warn("District Not Found");
								 
							 }
							 }
						}else {
							baseDTO.setMessage("state not found");
							baseDTO.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
							//log.warn("state Not Found");
						}
						 
					 }
					 
				 }else {
						baseDTO.setMessage("city Not Found");
						baseDTO.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
						//log.warn("city Not Found");
				 }
			
		}
							 
			catch(Exception e) {
				log.error("<----CityMasterDaoImpl.create() ----> EXCEPTION", e);
				baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
				baseDTO.setMessage(msg);
				
			}
			return baseDTO;
		
	}
	
	
	@Override
	public List<CityMasterEntity>  getAll(){
		//log.info(" <----- CityMasterDaoImpl getAll Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		List<CityMasterEntity> cityMasterEntity = new ArrayList<CityMasterEntity>();
		try {
			List<CityMasterEntity> cityMasterEntity1=cityMasterRepository.findAll();
		
			for(CityMasterEntity city : cityMasterEntity1) {
				CityMasterEntity city1=objectMapper.convertValue(city , CityMasterEntity.class);
				cityMasterEntity.add(city1);
			}
			if(cityMasterEntity.isEmpty()) {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("No Record Found..");
				
			}else {
				response.setResponseContent(cityMasterEntity);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get City Details..");
			}
			
		}
		catch(Exception e) {
			log.error("<---- CityMasterDaoImpl.getAll() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
			
		}
		//log.info(" <----- CityDaoImpl getAll Service END -----> ");
		return cityMasterEntity;
		
		
	}
	
	@Override
	 public BaseDTO update( UUID id,CityMasterDTO cityMasterDTO) {
			//log.info(" <----- CityMasterDaoImpl update Service STARTED -----> ");
			BaseDTO response=new BaseDTO();
			try
			{
				boolean check = false;
				Optional<CityMasterEntity> optional=cityMasterRepository.findById(id);
				
			   if(optional.isPresent()) {
					CityMasterEntity city=new CityMasterEntity();
					
					city = optional.get();
					List<CityMasterEntity> checkName = cityMasterRepository.checkCityName(cityMasterDTO.getCityName());
					
					for(CityMasterEntity entity : checkName) {
						if(entity.getCityName().equals(cityMasterDTO.getCityName())) {
							
							if(id.equals(entity.getId())) {
								check = false;
							}else
						check = true;
						}else {
							check=false;
						}
						
					}
					if(check) {
						String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
						response.setMessage("This City Name Already Exist.");
						response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
						//log.warn("city name is already exists - {} ", cityMasterDTO.getCityName());
					}
					else {
						city.setCityName(cityMasterDTO.getCityName());
						city.setStatus(cityMasterDTO.getStatus());
						city.setStateId(cityMasterDTO.getStateId());
						city.setDistrictId(cityMasterDTO.getDistrictId());
						
						CityMasterEntity afterUpdate = cityMasterRepository.save(city);
						response.setResponseContent(afterUpdate);
						String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
								Locale.US);
						response.setMessage(cityMasterDTO.getCityName() + " " + msg);
						response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
						//log.info("District - {} Updated Successfully",cityMasterDTO.getCityName(),cityMasterDTO.getStateId(),cityMasterDTO.getDistrictId());
					}
				}else {
					String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
					response.setMessage(msg);
					response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
					//log.warn("No Record Found..");
				}
				
			}
			catch(DataIntegrityViolationException e) {
				log.error("<----CityMasterDaoImpl.update() ----> EXCEPTION", e);
				response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());

			}
			catch(Exception e) {
				log.error("<---- CityMasterDaoImpl.update() ----> EXCEPTION", e);
				response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			}
			return response;
		 
	 }


}
