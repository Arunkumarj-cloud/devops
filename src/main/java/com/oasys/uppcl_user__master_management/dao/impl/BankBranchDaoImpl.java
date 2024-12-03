package com.oasys.uppcl_user__master_management.dao.impl;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.BankBranchDao;
import com.oasys.uppcl_user__master_management.entity.BankBranchMasterEntity;
import com.oasys.uppcl_user__master_management.entity.BankNameMasterEntity;
import com.oasys.uppcl_user__master_management.entity.ReportQueriesEntity;
import com.oasys.uppcl_user__master_management.repository.BankBranchRepository;
import com.oasys.uppcl_user__master_management.repository.ReportQueriesRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class BankBranchDaoImpl implements BankBranchDao {


	

	@Autowired
	BankBranchRepository branchRepository;

	@Autowired
	ReportQueriesRepository reportQueriesRepository;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	MessageSource messageSource;

	@Override
	public List<BankBranchMasterEntity> getAll() {
		//log.info(" <----- BankBranchDaoImpl.getAll() Dao STARTED -----> ");
		List<BankBranchMasterEntity> bankBranch = branchRepository.findAll();
		//log.info(" <----- BankBranchDaoImpl.getAll() Dao END -----> ");
		return bankBranch;
	}

	@Override
	public List<BankBranchMasterEntity> getAllActive() {
		//log.info(" <----- BankBranchDaoImpl.getAllActive() Dao STARTED -----> ");
		List<BankBranchMasterEntity> bankBranch = branchRepository.getAllActive();
		//log.info(" <----- BankBranchDaoImpl.getAllActive()  END -----> ");
		return bankBranch;
	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- BankBranchDaoImpl.getLazyList() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		int pageSize = 0;
		int totalRecords = 0;
		int totalPages = 0;
		List<Map<String, Object>> totalListOfDataReport = null;
		List<Map<String, Object>> listCount = null;
		try {
			ReportQueriesEntity reportQuery = reportQueriesRepository.getReportbyName("Bank_Branch_search_pagination");
			if (reportQuery == null) {
				//log.warn("Bank_Branch_search_pagination  Not Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
			ReportQueriesEntity reportQueryCount = reportQueriesRepository
					.getReportbyName("Bank_Branch_search_pagination_count");
			if (reportQueryCount == null) {
				//log.warn("Bank_Branch_search_pagination_count  Not Found..");
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
			//log.info("bank branch  count Retrieved sucessfully - {}");
			if (listCount.size() > 0 && listCount.get(0).get("count") != null) {
				totalRecords = Integer.parseInt(listCount.get(0).get("count").toString());
				totalPages = (totalRecords / requestData.getPaginationSize());
				if ((totalRecords % requestData.getPaginationSize()) != 0) {
					totalPages++;
				}
				//log.info("totalpages -{}", totalPages);
			}
			if (requestData.getPageNo() != null && requestData.getPaginationSize() != null) {
				if (requestData.getPageNo() != 0) {
					pageSize = (requestData.getPageNo() * requestData.getPaginationSize());
				}
			}
			if (requestData.getSortField() != null) {
				if (requestData.getSortField().toUpperCase().equals("BRANCHNAME"))
					query = query.replace(":shortField", "branch_name");
				else if (requestData.getSortField().toUpperCase().equals("ADDRESS"))
					query = query.replace(":shortField", "address");
				else if (requestData.getSortField().toUpperCase().equals("BRANCHIFSCCODE"))
					query = query.replace(":shortField", "branch_ifsc_code");
				else if (requestData.getSortField().toUpperCase().equals("CREATEDDATE"))
					query = query.replace(":shortField", "createdDate ");
				else if (requestData.getSortField().toUpperCase().equals("BRANCHCONTACTNUMBER"))
					query = query.replace(":shortField", "branch_contact_number");
				else if (requestData.getSortField().toUpperCase().equals("BANKNAME"))
					query = query.replace(":shortField", "bank_name");
				else if (requestData.getSortField().toUpperCase().equals("BANKTYPE"))
					query = query.replace(":shortField", "type");
				else if (requestData.getSortField().toUpperCase().equals("DISTRICTNAME"))
					query = query.replace(":shortField", "district_name");
				else if (requestData.getSortField().toUpperCase().equals("STATENAME"))
					query = query.replace(":shortField", "state_name");
				else if (requestData.getSortField().toUpperCase().equals("STDCODE"))
					query = query.replace(":shortField", "std_code");
				else if (requestData.getSortField().toUpperCase().equals("LANDLINENUMBER"))
					query = query.replace(":shortField", "landline_number");

				else if (requestData.getSortField().toUpperCase().equals("STATUS"))
					query = query.replace(":shortField", "status");
			} else
				query = query.replace(":shortField", "branch_name");
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
					if(entry.getKey().equals("stdCode")) {
						if(entry.getValue() !=null) {
							entry.setValue("0"+entry.getValue());
						}
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
				//log.info("Bank Branch get Details Sucessfully ");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			} else {
				//log.warn("No Records Found");
				response.setMessage("No Records Found - {}");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<----- BankBranchDaoImpl.getLazyList() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- BankBranchDaoImpl.getLazyList() END -----> ");
		return response;
	} 

	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- BankBranchDaoImpl.delete() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			BankBranchMasterEntity BankBranch = branchRepository.getOne(id);
			if (BankBranch != null) {
				branchRepository.delete(BankBranch);
				//log.info("Bank Branch  - {} Deleted Successfully", BankBranch.getId());
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,
						Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- BankBranchDaoImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- BankBranchDaoImpl.delete() END -----> ");
		return response;
	}

	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- BankBranch softDelete Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			BankBranchMasterEntity BankBranch = branchRepository.getOne(id);
			if (BankBranch != null) {
				BankBranch.setStatus(false);
				branchRepository.save(BankBranch);
				response.setMessage("Successfully Deleted");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- BankBranchServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete BankBranch");
		}
		//log.info(" <----- BankBranch softDelete Service END -----> ");
		return response;
	}

	@Override
	public Optional<BankBranchMasterEntity> findById(UUID id) {
		//log.info(" <--- BankBranchDaoImpl.findById() END -{} ", id);
		Optional<BankBranchMasterEntity> entity = branchRepository.findById(id);
		//log.info(" <--- BankBranchDaoImpl.findById() END ---> ");
		return entity;
	}

	@Override
	public Optional<BankBranchMasterEntity> findByIfscCode(String ifscCode) {
		log.info(" <--- BankBranchDaoImpl.findByIfscCode() -{} ", ifscCode);
		Optional<BankBranchMasterEntity> entity = branchRepository.findByIfscCode(ifscCode.toUpperCase());
		log.info(" <--- BankBranchDaoImpl.findByIfscCode() END ---> ");
		return entity;
	}

	@Override
	public BankBranchMasterEntity save(BankBranchMasterEntity master) {
		log.info(" <--- BankBranchDaoImpl.save() -{} ", master);
		BankBranchMasterEntity entity = branchRepository.save(master);
		log.info(" <--- BankBranchDaoImpl.save() END ---> ");
		return entity;
	}

	@Override
	public List<BankBranchMasterEntity> getByExcepted(UUID id) {
		log.info(" <--- BankBranchDaoImpl.getByExcepted() -{} ", id);
		List<BankBranchMasterEntity> entity = branchRepository.getByExcepted(id);
		log.info(" <--- BankBranchDaoImpl.getByExcepted() END ---> ");
		return entity;
	}

	@Override
	public List<BankBranchMasterEntity> getByBankNameId(BankNameMasterEntity id) {
		log.info(" <--- BankBranchDaoImpl.getByBankNameId() -{} ", id);
		List<BankBranchMasterEntity> branchName = branchRepository.getByBankName(id);
		log.info(" <--- BankBranchDaoImpl.getByBankNameId() END ---> ");
		return branchName;
	}
	

}
