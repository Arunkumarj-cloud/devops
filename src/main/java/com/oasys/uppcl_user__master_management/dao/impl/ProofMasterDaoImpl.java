package com.oasys.uppcl_user__master_management.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.ProofMasterDao;
import com.oasys.uppcl_user__master_management.dto.ProofTypeProofMappingDTO;
import com.oasys.uppcl_user__master_management.entity.ProofMasterEntity;
import com.oasys.uppcl_user__master_management.entity.ProofTypeMasterEntity;
import com.oasys.uppcl_user__master_management.entity.ProofTypeProofMappingEntity;
import com.oasys.uppcl_user__master_management.entity.ReportQueriesEntity;
import com.oasys.uppcl_user__master_management.repository.ProofMasterRepository;
import com.oasys.uppcl_user__master_management.repository.ProofTypeProofMappingRepository;
import com.oasys.uppcl_user__master_management.repository.ReportQueriesRepository;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class ProofMasterDaoImpl implements ProofMasterDao {

	@Autowired
	ProofMasterRepository proofMasterRepository;

	@Autowired
	ProofTypeProofMappingRepository repository;

	@Autowired
	ReportQueriesRepository reportQueriesRepository;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	ObjectMapper objectMapper;

	@Override
	public ProofMasterEntity save(ProofMasterEntity proofEntity) {
		//log.info(" <----- Proof save Dao STARTED -----> ");
			proofEntity = proofMasterRepository.save(proofEntity);
		//log.info(" <----- Proof save Dao END -----> ");
		return proofEntity;
	}

	@Override
	public ProofTypeProofMappingEntity saveProofTypeProofMapping(ProofTypeProofMappingEntity mappingEntity) {
		//log.info(" <----- Proof save Dao STARTED -----> ");
			mappingEntity = repository.save(mappingEntity);
		//log.info(" <----- Proof save Dao END -----> ");
		return mappingEntity;
	}

	public Boolean validate(ProofTypeProofMappingDTO mappingDTO) {
		boolean check = false;
		ProofTypeMasterEntity proofType = new ProofTypeMasterEntity();
		proofType.setId(mappingDTO.getProofTypeId().getId());
		List<ProofTypeProofMappingEntity> list = repository.findProofTypeId(proofType);
		for (ProofTypeProofMappingEntity listDTO : list) {
			if (listDTO.getProofId().getId().toString().contentEquals(mappingDTO.getProofId().getId().toString())) {
				check = true;
				break;
			} else {
				check = false;
			}
		}
		return check;
	}

	@Override
	public ProofTypeProofMappingEntity deleteProofMapping(ProofTypeProofMappingEntity deleteProofMapping) {
		//log.info(" <----- Proof save Dao STARTED -----> ");
			repository.delete(deleteProofMapping);
		//log.info(" <----- Proof save Dao END -----> ");
		return deleteProofMapping;
	}

	@Override
	public List<ProofMasterEntity> getAll() {
		//log.info(" <----- Proof getAll Dao STARTED -----> ");
		List<ProofMasterEntity> proof = new ArrayList<ProofMasterEntity>();		
			proof = proofMasterRepository.findAll();
		//log.info(" <----- Proof getAll Dao END -----> ");
		return proof;
	}

	@Override
	public List<ProofMasterEntity> getAllActive() {
		//log.info(" <----- Proof getAllActive Dao STARTED -----> ");
		List<ProofMasterEntity> proof = new ArrayList<ProofMasterEntity>();
			proof = proofMasterRepository.getAllActive();
		//log.info(" <----- Proof getAllActive Dao END -----> ");
		return proof;
	}

	@Override
	public ProofMasterEntity delete(UUID id) {
		//log.info(" <----- Proof delete Dao STARTED -----> ");
		ProofMasterEntity proof = new ProofMasterEntity();
			proofMasterRepository.deleteById(id);
		//log.info(" <----- Proof delete Dao END -----> ");
		return proof;
	}

	@Override
	public ProofMasterEntity getById(UUID id) {
		//log.info(" <----- Proof getById Dao STARTED -----> ");
		ProofMasterEntity proof = new ProofMasterEntity();
			proof = proofMasterRepository.getOne(id);
		//log.info(" <----- Proof getById Dao END -----> ");
		return proof;
	}

	@Override
	public List<ProofTypeProofMappingEntity> getByProofId(ProofMasterEntity id) {
		//log.info(" <----- Proof getById Dao STARTED -----> ");
		List<ProofTypeProofMappingEntity> proof = new ArrayList<ProofTypeProofMappingEntity>();
			proof = repository.findProofId(id);
		//log.info(" <----- Proof getById Dao END -----> ");
		return proof;
	}

	@Override
	public List<Map<String, Object>> getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- Proof getLazyList Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		int pageSize = 0;
		int totalRecords = 0;
		int totalPages = 0;

		List<Map<String, Object>> totalListOfDataReport = null;
		List<Map<String, Object>> listCount = null;
			ReportQueriesEntity reportQuery = reportQueriesRepository.getReportbyName("Proof_search_pagination");
			if (reportQuery == null) {
				//log.info("Proof_search_pagination  Not Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
			ReportQueriesEntity reportQueryCount = reportQueriesRepository
					.getReportbyName("Proof_search_pagination_count");
			if (reportQueryCount == null) {
				//log.info("Proof_search_pagination_count  Not Found..");
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
			}
			if (requestData.getPageNo() != null && requestData.getPaginationSize() != null) {
				if (requestData.getPageNo() != 0) {
					pageSize = (requestData.getPageNo() * requestData.getPaginationSize());
				}
			}
			if (requestData.getSortField() != null) {
				if (requestData.getSortField().toUpperCase().equals("PROOFNAME"))
					query = query.replace(":shortField", "proof");
				else if (requestData.getSortField().toUpperCase().equals("PROOFTYPE"))
					query = query.replace(":shortField", "proof_type_name");
				else if (requestData.getSortField().toUpperCase().equals("DESCRIPTION"))
					query = query.replace(":shortField", "description");
				else if (requestData.getSortField().toUpperCase().equals("CREATEDDATE"))
					query = query.replace(":shortField", "createdDate");
				else if (requestData.getSortField().toUpperCase().equals("STATUS"))
					query = query.replace(":shortField", "status");
			} else
				query = query.replace(":shortField", "proof");

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

			Map<String, Object> count = new HashMap<String, Object>();
			count.put("totalRecords", totalRecords);
			count.put("totalPages", totalPages);
			count.put("pageNo", requestData.getPageNo());
			totalListOfDataReport.add(count);
		//log.info(" <----- Proof getLazyList Dao END -----> ");
		return totalListOfDataReport;
	}
	
	@Override
	public List<ProofTypeProofMappingEntity> getByProofType(UUID id) {
		//log.info(" <----- Proof getByProofType Dao STARTED -----> ");
		List<ProofTypeProofMappingEntity> proofEntity = new ArrayList<ProofTypeProofMappingEntity>();
			ProofTypeMasterEntity proofType = new ProofTypeMasterEntity();
			proofType.setId(id);
			proofEntity = repository.findByProofTypeId(proofType);
		//log.info(" <----- Proof getByProofType Dao END -----> ");
		return proofEntity;
	}

	@Override
	public List<ProofMasterEntity> exceptId(UUID id) {
		//log.info(" <----- Proof exceptId Dao STARTED -----> ");
		List<ProofMasterEntity> proofEntity = new ArrayList<ProofMasterEntity>();
			proofEntity = proofMasterRepository.findByIdNotIn(id);
		//log.info(" <----- Proof exceptId Dao END -----> ");
		return proofEntity;
	}

	public ProofMasterEntity validateProofName(String name) {
		ProofMasterEntity entity = proofMasterRepository.findByProofNameIgnoreCase(name);
		return entity;
	}

}
