package com.oasys.uppcl_user__master_management.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.ReasonTypeDao;
import com.oasys.uppcl_user__master_management.entity.ReasonTypeEntity;
import com.oasys.uppcl_user__master_management.repository.ReasonTypeRepository;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class ReasonTypeDaoImpl implements ReasonTypeDao {

	@Autowired
	ReasonTypeRepository repository;

	public ReasonTypeEntity save(ReasonTypeEntity reasonType) {
		//log.info(" <----- ReasonType save Dao STARTED -----> ");
		try {
			reasonType = repository.save(reasonType);
		} catch (DataIntegrityViolationException e) {
			log.error("<---- ReasonTypeDaoImpl.save() ----> EXCEPTION", e);
		} catch (Exception e) {
			log.error("<---- ReasonTypeDaoImpl.save() ----> EXCEPTION", e);
		}
		//log.info(" <----- ReasonType save Dao END -----> ");
		return reasonType;
	}

	

	 public ReasonTypeEntity getById(UUID id)
	 {
		//log.info(" <----- ReasonType getById Dao STARTED -----> ");
		ReasonTypeEntity reasonType = new ReasonTypeEntity();
		try {
			reasonType = repository.getOne(id);

		} catch (Exception e) {
			log.error("<---- ReasonTypeDaoImpl.getById() ----> EXCEPTION", e);
		}
		//log.info(" <----- ReasonType getById Dao END -----> ");
		return reasonType;
	}

	public List<ReasonTypeEntity> getAll() 
	{
		//log.info(" <----- ReasonType getAll Dao STARTED -----> ");
		List<ReasonTypeEntity> reasonType = new ArrayList<ReasonTypeEntity>();
		try {
			reasonType = repository.findAll(Sort.by(Direction.ASC, "reasonType"));
		} catch (Exception e) {
			log.error("<---- ReasonTypeDaoImpl.getAll() ----> EXCEPTION", e);
		}
		//log.info(" <----- ReasonType getAll Dao END -----> ");
		return reasonType;
	}

	public List<ReasonTypeEntity> getAllActive() {
		//log.info(" <----- ReasonType getAllActive Dao STARTED -----> ");
		List<ReasonTypeEntity> reasonType = new ArrayList<ReasonTypeEntity>();
		try {
			reasonType = repository.findByStatusTrue(Sort.by(Direction.ASC, "reasonType"));

		} catch (Exception e) {
			log.error("<---- ReasonTypeDaoImpl.getAll() ----> EXCEPTION", e);
		}
		//log.info(" <----- ReasonType getAllActive Dao END -----> ");
		return reasonType;
	}

	public Page<ReasonTypeEntity> getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- ReasonType getLazyList Dao STARTED -----> ");
		Pageable pageRequest;
		Page<ReasonTypeEntity> reasonType = null;
		try {
			if (requestData.getSearch() != null) {
				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.ASC, requestData.getSortField()));
					reasonType = repository.search(pageRequest, requestData.getSearch());
				} else {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.DESC, requestData.getSortField()));
					reasonType = repository.search(pageRequest, requestData.getSearch());
				}
			} else {

				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					reasonType = repository.findAll(PageRequest.of(requestData.getPageNo(),
							requestData.getPaginationSize(), Sort.by(Direction.DESC, requestData.getSortField())));
				} else {
					reasonType = repository.findAll(PageRequest.of(requestData.getPageNo(),
							requestData.getPaginationSize(), Sort.by(Direction.DESC, requestData.getSortField())));
				}
			}
		} catch (Exception e) {
			log.error("<----- ReasonTypeDaoImpl.getLazyList() ----->", e);
		}
		//log.info(" <----- ReasonType getLazyList Dao END -----> ");
		return reasonType;
	}


	public ReasonTypeEntity delete(UUID id) {
		//log.info(" <----- ReasonType delete Dao STARTED -----> ");
		ReasonTypeEntity reason = new ReasonTypeEntity();
		try {
			repository.deleteById(id);
		} catch (Exception e) {
			log.error("<---- ReasonTypeDaoImpl.getById() ----> EXCEPTION", e);
		}
		//log.info(" <----- ReasonType delete Dao END -----> ");
		return reason;
	}

	public ReasonTypeEntity validateReasonType(String reasonType) {
		//log.info(" <----- ReasonType validateReasonType Dao STARTED -----> ");
		ReasonTypeEntity reason = new ReasonTypeEntity();
		try {
			reason = repository.findByReasonTypeIgnoreCase(reasonType);
		} catch (Exception e) {
			log.error("<---- ReasonTypeDaoImpl.validateReasonType() ----> EXCEPTION", e);
		}
		//log.info(" <----- ReasonType validateReasonType Dao END -----> ");
		return reason;
	}

	public List<ReasonTypeEntity> exceptId(UUID id) {
		//log.info(" <----- ReasonType exceptId Dao STARTED -----> ");
		List<ReasonTypeEntity> reasonTypeEntity = new ArrayList<ReasonTypeEntity>();
		try {
			reasonTypeEntity = repository.getByExceptId(id);

		} catch (Exception e) {
			log.error("<---- ReasonTypeDaoImpl.exceptId() ----> EXCEPTION", e);
		}
		//log.info(" <----- ReasonType exceptId Dao STARTED -----> ");
		return reasonTypeEntity;
	}
	
	public Optional<ReasonTypeEntity> findByReasonTypeId(UUID id) {
		//log.info(" <----- ReasonType getById Dao STARTED -----> ");
		Optional<ReasonTypeEntity> reasonType = repository.findById(id);
		//log.info(" <----- ReasonType getById Dao END -----> ");
		return reasonType;
	}

}
