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
import com.oasys.uppcl_user__master_management.dao.MuncipalityDao;
import com.oasys.uppcl_user__master_management.dto.MuncipalityDTO;
import com.oasys.uppcl_user__master_management.entity.DistrictMasterEntity;
import com.oasys.uppcl_user__master_management.entity.Muncipality;
import com.oasys.uppcl_user__master_management.entity.ReportQueriesEntity;
import com.oasys.uppcl_user__master_management.repository.DistrictMasterRepository;
import com.oasys.uppcl_user__master_management.repository.MuncipalityRepository;
import com.oasys.uppcl_user__master_management.repository.ReportQueriesRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;
@Service
@Log4j2
public class MuncipalityDaoImpl implements MuncipalityDao {

	@Autowired
	MuncipalityRepository muncipalityRepository;

	@Autowired
	ReportQueriesRepository reportQueriesRepository;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	DistrictMasterRepository districtMasterRepository;

	@Autowired
	MessageSource messageSource;



	@Override
	public BaseDTO createMuncipality(MuncipalityDTO muncipalityDTO) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started MuncipalityDaoImpl.createMuncipality===>");
		Muncipality muncipality = new Muncipality();
		try {

			if (muncipalityDTO.getMunicipalityName() != null && !muncipalityDTO.getMunicipalityName().isEmpty()) {
				//log.info(" Municipality Name  {}  -Is Not Empty - Is Not Null", muncipalityDTO.getMunicipalityName());
				if (muncipalityDTO.getStatus() != null) {
					//log.info(" status  {}  Is Not Null", muncipalityDTO.getStatus());
					
					if (districtMasterRepository.findById(muncipalityDTO.getDistrictId().getId()).isPresent()) {
						UUID id = muncipalityDTO.getDistrictId().getId();
					
					 Optional<Muncipality> optional = muncipalityRepository.findByMunicipalityName(muncipalityDTO.getMunicipalityName(),muncipalityDTO.getDistrictId().getId());
						if(optional.isPresent()) {
							String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,Locale.US);
							response.setMessage(muncipalityDTO.getMunicipalityName() + " " +msg);
							response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
							//log.info("Municipality  Name - {} Already Exist",muncipalityDTO.getMunicipalityName(),muncipalityDTO.getDistrictId().getId(),muncipalityDTO.getMunicipalityCode());
						}
						else {
							muncipality.setDistrictId(muncipalityDTO.getDistrictId());
						muncipality.setMunicipalityName(muncipalityDTO.getMunicipalityName());
						muncipality.setMunicipalityCode(muncipalityDTO.getMunicipalityCode());
						muncipality.setStatus(muncipalityDTO.getStatus());
						muncipalityRepository.save(muncipality);
						String msg = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
						response.setMessage(muncipalityDTO.getMunicipalityName() + " " + msg);
						response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
						//log.info("Municipality - {} created successfully",muncipalityDTO.getMunicipalityName());
						}
					} else {
						//log.warn("District id is required");
						response.setMessage("District id is required");
						response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
					}

				} else {
					//log.warn("Status is required");
					response.setMessage("Status is required");
					response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				}
			} else {
				//log.warn("Muncipality name is required");
				response.setMessage("Muncipality name is required");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}

		} catch (DataIntegrityViolationException e) {
			log.error("Exception MuncipalityDaoImpl.createMuncipality : " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		} catch (Exception e) {
			log.error("Exception MuncipalityDaoImpl.createMuncipality : " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}

		//log.info("<===Ended MuncipalityDaoImpl.createMuncipality===>");
		return response;

	}

	public BaseDTO getMuncipality() {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started MuncipalityDaoImpl.getMuncipality===>");
		List<Muncipality> muncipality = new ArrayList<>();
		try {

			muncipality = muncipalityRepository.findAll();
			if (!muncipality.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setResponseContent(muncipality);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Get Details successfully");
			} else {
				log.error("Data doesn't exist");
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}

		} catch (Exception e) {
			log.error("Exception MuncipalityDaoImpl.getMuncipality : " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<===Started MuncipalityDaoImpl.getMuncipality===>");
		return response;

	}

	public Optional<Muncipality> getMuncipalityById(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started MuncipalityDaoImpl.getMuncipalityById===>");
		Optional<Muncipality> muncipality = null;
		
		try {
			muncipality = muncipalityRepository.findById(id);
			if (muncipality.isPresent()) {
				//log.info("Municipality Id {}", muncipality.get().getId());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setResponseContent(muncipality);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Get Details Successfully");
			} else {
				//log.error("No data found");
				response.setMessage("No data found");
				response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			}

		} catch (Exception e) {
			log.error("Exception MuncipalityDaoImpl.getMuncipalityById : " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<===Started MuncipalityDaoImpl.getMuncipalityById===>");
		return muncipality;

	}

	public BaseDTO updateMuncipality(UUID id, MuncipalityDTO muncipalityDTO) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started MuncipalityDaoImpl.updateMuncipality===>");
		try {
            boolean check = false;
			Optional<Muncipality> optional = muncipalityRepository.findById(id);
			if (optional.isPresent()) {
				Muncipality municipality = new Muncipality();
				municipality = optional.get();
				List<Muncipality> checkCode = muncipalityRepository.findByName(muncipalityDTO.getMunicipalityName());
				for (Muncipality entity : checkCode) {
					if (entity.getMunicipalityName().equals(muncipalityDTO.getMunicipalityName())) {
						if(id.equals(entity.getId())) {
							check = false;
						}else
						check = true;
					} else {
						check = false;
					}
				}

				if (check) {
					String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,
							Locale.US);
					response.setMessage(muncipalityDTO.getMunicipalityName() + " " + msg);
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.info("Municiplity name - {} already exists",muncipalityDTO.getMunicipalityName());
				} 
				else {	
					municipality.setMunicipalityName(muncipalityDTO.getMunicipalityName());
					municipality.setMunicipalityCode(muncipalityDTO.getMunicipalityCode());
					municipality.setDistrictId(muncipalityDTO.getDistrictId());
					municipality.setStatus(muncipalityDTO.getStatus());
					Muncipality afterUpdate = muncipalityRepository.save(municipality);
					response.setResponseContent(afterUpdate);
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(muncipalityDTO.getMunicipalityName() + " " + msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					/*log.info("Municipality - {} Updated Successfully", muncipalityDTO.getMunicipalityName()
							,muncipalityDTO.getMunicipalityCode(), muncipalityDTO.getDistrictId());*/
				}

			} else {
				//log.error("No Record  found");
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}

		} catch (DataIntegrityViolationException e) {
			log.error("Exception MuncipalityDaoImpl.updateMuncipality : " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		} catch (Exception e) {
			log.error("Exception MuncipalityDaoImpl.updateMuncipality : " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}

		//log.info("<===Ended MuncipalityDaoImpl.updateMuncipality===>");
		return response;

	}

	public BaseDTO softDelete(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started MuncipalityDaoImpl.softDelete===>");
		Muncipality muncipality = new Muncipality();

		try {

			muncipality = muncipalityRepository.getOne(id);
			if (muncipality.getId() != null) {
				muncipality.setStatus(false);
				muncipalityRepository.save(muncipality);
				response.setMessage("Data Deleted Sucessfully");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			} else {
				//log.error("no data found");
				response.setMessage("no data found");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}

		} catch (Exception e) {
			log.error("Exception MuncipalityDaoImpl.softDelete : " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}

		//log.info("<===Ended MuncipalityDaoImpl.softDelete===>");
		return response;

	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- Muncipality getLazyList Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		int pageSize = 0;
		int totalRecords = 0;
		int totalPages = 0;
		List<Map<String, Object>> totalListOfDataReport = null;
		List<Map<String, Object>> listCount = null;
		try {

			ReportQueriesEntity reportQuery = reportQueriesRepository.getReportbyName("Municipality_search_pagination");
			if (reportQuery == null) {
				//log.error("Municipality_search_pagination  Not Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
			ReportQueriesEntity reportQueryCount = reportQueriesRepository
					.getReportbyName("Municapality_search_pagination_count");
			if (reportQueryCount == null) {
				//log.error("Municapality_search_pagination_count  Not Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
			String query = reportQuery.getDataQuery();
			String queryCount = reportQueryCount.getDataQuery();
			if (requestData.getSearch() != null) {
				query = query.replace(":search", requestData.getSearch());
				queryCount = queryCount.replace(":search", requestData.getSearch());
			} else {
				query = query.replace(":search", "");
				queryCount = queryCount.replace(":search", "");
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
				if (requestData.getSortField().toUpperCase().equals("MUNICIPALITYNAME"))
					query = query.replace(":shortField", "municipality_name");
				else if (requestData.getSortField().toUpperCase().equals("DISTRICTNAME"))
					query = query.replace(":shortField", "district_name");
				else if (requestData.getSortField().toUpperCase().equals("STATENAME"))
					query = query.replace(":shortField", "state_name");

				else if (requestData.getSortField().toUpperCase().equals("STATUS"))
					query = query.replace(":shortField", "status");
			} else
				query = query.replace(":shortField", "muncipality_name");

			if (requestData.getSortOrder() != null) {
				if (requestData.getSortOrder().toUpperCase().startsWith("A"))
					query = query.replace(":shortOrder", "ASC");
				else
					query = query.replace(":shortOrder", "DESC");

			}

			query = query.replace(":limit", +pageSize + "," + requestData.getPaginationSize());
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
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successgully Get Details");
			} else {
				//log.warn("No Records Found");
				response.setMessage("No Records Found");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<----- MuncipalityDaoImpl.getLazyList() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Municipality getLazyList Dao END -----> ");
		return response;
	}

	public BaseDTO getDistrictById(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started MuncipalityDaoImpl.getDistrictById===>");
		List<Muncipality> muncipality = null;
		try {
			DistrictMasterEntity entity = new DistrictMasterEntity(id);
			muncipality = muncipalityRepository.findByDistrictId(entity);
			if (!muncipality.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setResponseContent(muncipality);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			} else {
				//log.error("No data found");
				response.setMessage("No data found");
				response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			}

		} catch (Exception e) {
			log.error("Exception MuncipalityDaoImpl.getDistrictById : " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<===Started MuncipalityDaoImpl.getDistrictById===>");
		return response;

	}

	private Muncipality updatedValues(Muncipality muncipality, MuncipalityDTO muncipalityDTO) {
		Muncipality muncipalityUpdate = muncipality;
		muncipalityUpdate.setMunicipalityName(muncipalityDTO.getMunicipalityName());
		muncipalityUpdate.setMunicipalityCode(muncipalityDTO.getMunicipalityCode());
		muncipality.setStatus(muncipalityDTO.getStatus());
		muncipality.setDistrictId(muncipalityDTO.getDistrictId());
		return muncipalityUpdate;
	}

	@Override
	public BaseDTO deleteMunicipality(UUID id) {
		//log.info(" <----- MuncipalitydaoImpl delete Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			Optional<Muncipality> muncipality = muncipalityRepository.findById(id);
			if (muncipality != null) {
				muncipalityRepository.deleteById(id);
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- LanguageDaoImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- MuncipalitydaoImpl delete Dao END -----> ");
		return response;
	}

}

	