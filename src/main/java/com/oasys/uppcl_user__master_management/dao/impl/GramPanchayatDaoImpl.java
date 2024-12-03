package com.oasys.uppcl_user__master_management.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.GramPanchayatDao;
import com.oasys.uppcl_user__master_management.dto.GramPanchayatDTO;
import com.oasys.uppcl_user__master_management.entity.BlockMasterEntity;
import com.oasys.uppcl_user__master_management.entity.GramPanchayat;
import com.oasys.uppcl_user__master_management.entity.ReportQueriesEntity;
import com.oasys.uppcl_user__master_management.repository.BlockRepository;
import com.oasys.uppcl_user__master_management.repository.GramPanchayatRepository;
import com.oasys.uppcl_user__master_management.repository.ReportQueriesRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;
@Service
@Log4j2
public class GramPanchayatDaoImpl implements GramPanchayatDao {

	@Autowired
	GramPanchayatRepository gramPanchayatRepository;
	
	@Autowired
	ReportQueriesRepository reportQueriesRepository;

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	BlockRepository  blockRepository;
	
	@Autowired
	MessageSource messageSource;
	
	@Override
	public BaseDTO createGramPanchayat(GramPanchayatDTO gramPanchayatDTO) {
		BaseDTO response=new BaseDTO();
		//log.info("<===Started GramPanchayatDaoImpl.createGramPanchayat===>");
	GramPanchayat  gramPanchayat=new GramPanchayat();
		try {
			
			if(gramPanchayatDTO.getPanchayatName()!=null && !gramPanchayatDTO.getPanchayatName().isEmpty()) {	
			if(gramPanchayatDTO.getPanchayatCode()!=null && !gramPanchayatDTO.getPanchayatCode().isEmpty()){
			if(gramPanchayatDTO.getStatus()!=null) {
			if(blockRepository.findById(gramPanchayatDTO.getBlockId().getId()).isPresent()) {
				 Optional<GramPanchayat> optional = gramPanchayatRepository.findByBlockIdAndPanchayatNameAndPanchayatCode(gramPanchayatDTO.getBlockId().getId(),
						 gramPanchayatDTO.getPanchayatName(),gramPanchayatDTO.getPanchayatCode());
					if(optional.isPresent()) {
						String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,Locale.US);
						response.setMessage(gramPanchayatDTO.getPanchayatName() + " " +msg);
						response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
						//log.info("Panchayat Name Already Exist",gramPanchayatDTO.getBlockId().getId(),gramPanchayatDTO.getPanchayatName(),gramPanchayatDTO.getPanchayatCode());
					}
					else {
				gramPanchayat.setBlockId(gramPanchayatDTO.getBlockId());
				gramPanchayat.setPanchayatName(gramPanchayatDTO.getPanchayatName());
				gramPanchayat.setPanchayatCode(gramPanchayatDTO.getPanchayatCode());
				gramPanchayat.setStatus(gramPanchayatDTO.getStatus());
				gramPanchayat = gramPanchayatRepository.save(gramPanchayat);
				String msg = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage( gramPanchayatDTO.getPanchayatName() + " " + msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					}
			}else {
				//log.error("Block id is required");
				response.setMessage("Block id is required");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
			
			}else {
				//log.warn("status id is required");
				response.setMessage("Status is required");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		
			}else {
				//log.warn("panchayt code is required");
				response.setMessage("panchayt code is required");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
			
			}else {
				//log.warn("Panchayat name is required");
				response.setMessage("Panchayat name is required");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
			
		}catch(DataIntegrityViolationException e) {
			log.error("Exception GramPanchayatDaoImpl.createGramPanchayat : " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}catch(Exception e) {
			log.error("Exception GramPanchayatDaoImpl.createGramPanchayat : " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		
		//log.info("<===Ended GramPanchayatDaoImpl.createGramPanchayat===>");
		return response;
		
	}
	
	public BaseDTO getGrampanchayat() {
		BaseDTO response=new BaseDTO();
		//log.info("<===Started GramPanchayatDaoImpl.getGrampanchayat===>");
		List<GramPanchayat> gramPanchayat=new ArrayList<>();
		try {
			
			gramPanchayat  = gramPanchayatRepository.findAll();
			if(!gramPanchayat.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setResponseContent(gramPanchayat);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			}else {
				log.error("Data doesn't exist");
				String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			}
			
		}catch(Exception e) {
			log.error("Exception GramPanchayatDaoImpl.getGrampanchayat : " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<===Started GramPanchayatDaoImpl.getGrampanchayat===>");
		return response;
		
	}
	
	public Optional<GramPanchayat> getGramPanchayatById(UUID id) {
		//log.info("<===Started GramPanchayatDaoImpl.getGramPanchayatById===>");
		Optional<GramPanchayat> gramPanchayat = gramPanchayatRepository.findById(id);
		//log.info("<===Started GramPanchayatDaoImpl.getGramPanchayatById===>");
		return gramPanchayat;
		
	}

public BaseDTO updateGramPanchayat(UUID id,GramPanchayatDTO gramPanchayatDTO) {
		BaseDTO response=new BaseDTO();
		//log.info("<===Started GramPanchayatDaoImpl.updateGramPanchayat===>");
		
		try {
			boolean check = false;
			Optional<GramPanchayat> optional =  gramPanchayatRepository.findById(id);
			if(optional.isPresent()) {
				GramPanchayat panchayatCode = new GramPanchayat();
				panchayatCode = optional.get();
				List<GramPanchayat> checkCode = gramPanchayatRepository.checkPanchayatCode(gramPanchayatDTO.getPanchayatCode());
				for (GramPanchayat entity : checkCode) {
					if (entity.getPanchayatCode().equals(gramPanchayatDTO.getPanchayatCode())) {
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
				response.setMessage( gramPanchayatDTO.getPanchayatCode() + " " + msg);
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				//log.info("Gram Panchayat  code - {} Already Exists ",gramPanchayatDTO.getPanchayatCode());
				} else {
					panchayatCode.setPanchayatCode(gramPanchayatDTO.getPanchayatCode());
					panchayatCode.setPanchayatName(gramPanchayatDTO.getPanchayatName());
					panchayatCode.setBlockId(gramPanchayatDTO.getBlockId());
					panchayatCode.setStatus(gramPanchayatDTO.getStatus());
					GramPanchayat afterUpdate = gramPanchayatRepository.save(panchayatCode);
					response.setResponseContent(afterUpdate);
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(gramPanchayatDTO.getPanchayatName() + " " + msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					/*log.info(" Gram Panchayat - {} Updated Successfully",  gramPanchayatDTO.getPanchayatName()
							,gramPanchayatDTO.getPanchayatCode(), gramPanchayatDTO.getBlockId());*/
				}

			}else {
				//log.error("no data found");
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
			
		}catch(DataIntegrityViolationException e) {
			log.error("Exception GramPanchayatDaoImpl.updateGramPanchayat : " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		
		catch(Exception e) {
			log.error("Exception GramPanchayatDaoImpl.updateGramPanchayat : " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		
		//log.info("<===Ended GramPanchayatDaoImpl.updateGramPanchayat===>");
		return response;
	}

	public BaseDTO deleteGramPanchayat(UUID id) {
		BaseDTO response=new BaseDTO();
		//log.info("<===Started GramPanchayatDaoImpl.deleteGramPanchayat===>");
	GramPanchayat gramPanchayat= new GramPanchayat();
	
		try {
			
			gramPanchayat = gramPanchayatRepository.getOne(id);
			if(gramPanchayat.getId()!=null) {
				gramPanchayat.setStatus(false);
				gramPanchayatRepository.save(gramPanchayat);
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			//log.info("successfully Deleted");
			}else {
				//log.error("Id doesn't exist");
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
			
		}catch(Exception e) {
			log.error("Exception GramPanchayatDaoImpl.deleteGramPanchayat : " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		
		//log.info("<===Ended GramPanchayatDaoImpl.deleteGramPanchayat===>");
		return response;
		
	}
	
	public BaseDTO getAllPanchayatlazy(PaginationRequestDTO requestData) {
		//log.info("<== Started panchayatservice.getAllPanchayatlazy() ==>");
		BaseDTO response = new BaseDTO();
		int pageSize = 0;
		int totalRecords = 0;
		int totalPages = 0;
		List<Map<String, Object>> totalListOfDataReport = null;
		List<Map<String, Object>> listCount = null;

		try {
			ReportQueriesEntity reportQuery = reportQueriesRepository.getReportbyName("Gram_Panchayat_search_pagination");
			if (reportQuery == null) {
				//log.error("Gram_Panchayat_search_pagination  Not Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
			ReportQueriesEntity reportQueryCount = reportQueriesRepository.getReportbyName("Gram_Panchayat_search_pagination_count");
			if (reportQueryCount == null) {
				//log.error("Gram_Panchayat_search_pagination_count  Not Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
			String query = reportQuery.getDataQuery();
			String queryCount = reportQueryCount.getDataQuery();
			if (requestData.getSearch() != null) {
				query = query.replace(":search", requestData.getSearch());
				queryCount=queryCount.replace(":search", requestData.getSearch());
				}
			else {
				query = query.replace(":search", "");
				queryCount=queryCount.replace(":search", "");
			}
			listCount = jdbcTemplate.queryForList(queryCount);
			if (listCount.size() > 0 && listCount.get(0).get("count") != null) {
				totalRecords = Integer.parseInt(listCount.get(0).get("count").toString());
				totalPages = Math.round(totalRecords / requestData.getPaginationSize());
				totalPages = (totalRecords / requestData.getPaginationSize());				
								if( (totalRecords % requestData.getPaginationSize()) != 0) {
									totalPages++;
								}				
								//log.info("totalpages -{}",totalPages);
			}
			if (requestData.getPageNo() != null && requestData.getPaginationSize() != null) {
				if (requestData.getPageNo() != 0) {
					pageSize = (requestData.getPageNo() * requestData.getPaginationSize());
				}
			}
			if (requestData.getSortField() != null) {
				if (requestData.getSortField().toUpperCase().equals("PANCHAYATNAME"))
					query = query.replace(":shortField", "panchayat_name");
				else if (requestData.getSortField().toUpperCase().equals("PANCHAYATCODE"))
					query = query.replace(":shortField", "panchayat_code");
				else if (requestData.getSortField().toUpperCase().equals("BLOCKNAME"))
					query = query.replace(":shortField", "block_name");
				else if (requestData.getSortField().toUpperCase().equals("DISTRICTNAME"))
					query = query.replace(":shortField", "district_name");
				else if (requestData.getSortField().toUpperCase().equals("STATENAME"))
					query = query.replace(":shortField", "state_name");

				else if (requestData.getSortField().toUpperCase().equals("STATUS"))
					query = query.replace(":shortField", "status");
			} else
				query = query.replace(":shortField", "panchayat_name");
			
			if (requestData.getSortOrder() != null) {
				if (requestData.getSortOrder().toUpperCase().startsWith("A"))
					query = query.replace(":shortOrder", "ASC");
				else
					query = query.replace(":shortOrder", "DESC");
					
			}
			
			query = query.replace(":limit", + pageSize + "," + requestData.getPaginationSize());
			//log.info("<=========== query " + query);
			totalListOfDataReport = jdbcTemplate.queryForList(query);
			for (Map<String, Object> entity : totalListOfDataReport) {
				for (Map.Entry<String, Object> entry : entity.entrySet()) {
					if (entry.getKey().equals("id")) {
						entry.setValue(objectMapper.convertValue(entry.getValue(), UUID.class));
					}
				}
			}
			
			if (totalListOfDataReport != null && totalListOfDataReport.size() > 0) {
				response.setResponseContents(totalListOfDataReport);
				response.setNumberOfElements(requestData.getPaginationSize());
				response.setTotalRecords(Long.parseLong(totalRecords + ""));
				response.setTotalPages(totalPages);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Get details successfully");
			} else {
				//log.error("No Records Found");
				response.setMessage("No Records Found");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
	
		} catch (Exception e) {
			log.error("<== Exception panchayatservice.getAllPanchayatlazy() ==>" + e);
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<== Ended panchayatservice.getAllPanchayatlazy() ==>");
		return response;
	}

	@Override
	public BaseDTO getByBlockId(UUID id) {
		BaseDTO response=new BaseDTO();
		
		//log.info("<===Started GramPanchayatDaoImpl.getByBlockId===>");
		List<GramPanchayat> gramPanchayat = null;
		try {
		//	gramPanchayat = gramPanchayatRepository.findById(id);
			BlockMasterEntity entity = new BlockMasterEntity(id);
			gramPanchayat = gramPanchayatRepository.getByBlockId(entity);
			if(gramPanchayat !=null) {
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setResponseContents(gramPanchayat);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get Details");
			}else {
				//log.error("No data found");
				response.setMessage("No data found");
				response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			}
			
		}catch(Exception e) {
			log.error("Exception GramPanchayatDaoImpl.getByBlockId : " + e);
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<===Started GramPanchayatDaoImpl.getByBlockId===>");
		return response;
	}
	
	
	private GramPanchayat updatedValues(GramPanchayat gramPanchayat, GramPanchayatDTO gramPanchayatDTO) {		
		GramPanchayat gramPanchayatUpdate = gramPanchayat;
		gramPanchayatUpdate.setPanchayatCode(gramPanchayatDTO.getPanchayatCode());
		gramPanchayatUpdate.setPanchayatName(gramPanchayatDTO.getPanchayatName());
		gramPanchayatUpdate.setStatus(gramPanchayatDTO.getStatus());
		gramPanchayatUpdate.setBlockId(gramPanchayatDTO.getBlockId());
		return gramPanchayatUpdate;
	}


}
