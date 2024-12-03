package com.oasys.uppcl_user__master_management.dao.impl;



import java.util.ArrayList;
import java.util.List;
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
import com.oasys.uppcl_user__master_management.dao.TermsAndConditionsDao;
import com.oasys.uppcl_user__master_management.entity.TermsAndConditionsEntity;
import com.oasys.uppcl_user__master_management.repository.TermsAndConditionsRepository;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class TermsAndConditionsDaoImpl implements TermsAndConditionsDao {

	@Autowired
	TermsAndConditionsRepository termsAndConditionsRepository;

	// @Override
	// public TermsAndConditionsEntity validateTermsAndConditions(String
	// description) {
	// TermsAndConditionsEntity termsAndConditionsEntity =
	// termsAndConditionsRepository.getByDescription(description);
	// return termsAndConditionsEntity;
	// }

	@Override
	public TermsAndConditionsEntity save(TermsAndConditionsEntity termsAndConditionsEntity) {
		//log.info(" <----- Terms and conditions save Dao STARTED -----> ");
		try {
			termsAndConditionsEntity = termsAndConditionsRepository.save(termsAndConditionsEntity);
		} catch (DataIntegrityViolationException e) {
			log.error("<---- TermsAndConditionsDaoImpl.create() ----> EXCEPTION", e);
		} catch (Exception e) {
			log.error("<---- TermsAndConditionsDaoImpl.create() ----> EXCEPTION", e);
		}
		//log.info(" <----- Terms and conditions save Dao END -----> ");
		return termsAndConditionsEntity;
	}

	@Override
	public TermsAndConditionsEntity getById(UUID id) {
		//log.info(" <----- Terms and conditions getById Dao STARTED -----> ");
		TermsAndConditionsEntity termsAndConditionsEntity = new TermsAndConditionsEntity();
		try {
			termsAndConditionsEntity = termsAndConditionsRepository.findOne(id);
		} catch (Exception e) {
			log.error("<---- TermsAndConditionsDaoImpl.getById() ----> EXCEPTION", e);
		}
		//log.info(" <----- Terms and conditions getById Dao END -----> ");
		return termsAndConditionsEntity;
	}

	@Override
	public TermsAndConditionsEntity delete(UUID id) {
		//log.info(" <----- Terms and conditions delete Dao STARTED -----> ");
		TermsAndConditionsEntity termsAndConditionsEntity = new TermsAndConditionsEntity();
		try {
			termsAndConditionsRepository.deleteById(id);
		} catch (Exception e) {
			log.error("<---- TermsAndConditionsDaoImpl.delete() ----> EXCEPTION", e);
		}
		//log.info(" <----- Terms and conditions delete Dao END -----> ");
		return termsAndConditionsEntity;
	}

	@Override
	public Page<TermsAndConditionsEntity> getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- Terms and conditions getLazyList Dao STARTED -----> ");
		Pageable pageRequest;
		Page<TermsAndConditionsEntity> termsAndConditionsEntity = null;
		try {
			if (requestData.getSearch() != null) {
				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.ASC, requestData.getSortField()));
					termsAndConditionsEntity = termsAndConditionsRepository.search(pageRequest,
							requestData.getSearch());
				} else {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.DESC, requestData.getSortField()));
					termsAndConditionsEntity = termsAndConditionsRepository.search(pageRequest,
							requestData.getSearch());
				}
			} else {

				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					termsAndConditionsEntity = termsAndConditionsRepository
							.findAll(PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
									Sort.by(Direction.ASC, requestData.getSortField())));
				} else {
					termsAndConditionsEntity = termsAndConditionsRepository
							.findAll(PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
									Sort.by(Direction.DESC, requestData.getSortField())));
				}
			}
		} catch (Exception e) {
			log.error("<----- TermsAndConditionsDaoImpl.getLazyList() ----->", e);
		}
		//log.info(" <----- Terms and conditions getLazyList Dao END -----> ");
		return termsAndConditionsEntity;
	}

	@Override
	public List<TermsAndConditionsEntity> exceptId(UUID id) {
		//log.info(" <----- Terms and conditions update Terms and conditions Dao STARTED -----> ");
		List<TermsAndConditionsEntity> termsAndConditionsEntity = new ArrayList<TermsAndConditionsEntity>();
		try {
			termsAndConditionsEntity = termsAndConditionsRepository.getByExceptId(id);

		} catch (Exception e) {
			log.error("<---- TermsAndConditionsDaoImpl.update() ----> EXCEPTION", e);

		}
		//log.info(" <----- Terms and conditions update Terms and conditions Dao END -----> ");
		return termsAndConditionsEntity;
	}

	@Override
	public List<TermsAndConditionsEntity> getAll() {
		//log.info(" <----- Terms and conditions getAll Dao STARTED -----> ");
		List<TermsAndConditionsEntity> termsAndConditionsEntity = new ArrayList<TermsAndConditionsEntity>();
		try {
			termsAndConditionsEntity = termsAndConditionsRepository.getAll();
		} catch (Exception e) {
			log.error("<----- TermsAndConditionsDaoImpl.getAll() ----->", e);
		}
		//log.info(" <----- Terms and conditions getAll Dao END -----> ");
		return termsAndConditionsEntity;
	}

	@Override
	public List<TermsAndConditionsEntity> getAllActive() {
		//log.info(" <----- Terms and conditions getAllActive Dao STARTED -----> ");
		List<TermsAndConditionsEntity> termsAndConditionsEntity = new ArrayList<TermsAndConditionsEntity>();
		try {
			termsAndConditionsEntity = termsAndConditionsRepository.getAllActive();
		} catch (Exception e) {
			log.error("<----- TermsAndConditionsDaoImpl.getAllActive() ----->", e);
		}
		//log.info(" <----- Terms and conditions getAllActive Dao END -----> ");
		return termsAndConditionsEntity;
	}

	@Override
	public TermsAndConditionsEntity getOne() {
				TermsAndConditionsEntity termsAndConditionsEntity = new TermsAndConditionsEntity();
				try {
					termsAndConditionsEntity = termsAndConditionsRepository.getOne();
				} catch (Exception e) {
					log.error("<----- TermsAndConditionsDaoImpl.getOne() ----->", e);
				}
				return termsAndConditionsEntity;
	}

}
