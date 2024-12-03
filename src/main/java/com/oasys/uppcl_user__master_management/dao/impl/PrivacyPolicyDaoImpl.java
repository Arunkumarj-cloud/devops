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
import com.oasys.uppcl_user__master_management.dao.PrivacyPolicyDao;
import com.oasys.uppcl_user__master_management.entity.PrivacyPolicyEntity;
import com.oasys.uppcl_user__master_management.repository.PrivacyPolicyRepository;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class PrivacyPolicyDaoImpl implements PrivacyPolicyDao {

	@Autowired
	PrivacyPolicyRepository privacyPolicyRepository;

	@Override
	public PrivacyPolicyEntity save(PrivacyPolicyEntity privacyPolicyEntity) {
		//log.info(" <----- Privacy Policy save Dao STARTED -----> ");
		try {
			privacyPolicyEntity = privacyPolicyRepository.save(privacyPolicyEntity);
		} catch (DataIntegrityViolationException e) {
			log.error("<---- PrivacyPolicyDaoImpl.create() ----> EXCEPTION", e);
		} catch (Exception e) {
			log.error("<---- PrivacyPolicyDaoImpl.create() ----> EXCEPTION", e);
		}
		//log.info(" <----- Privacy Policy save Dao END -----> ");
		return privacyPolicyEntity;
	}

	@Override
	public PrivacyPolicyEntity delete(UUID id) {
		//log.info(" <----- Privacy Policy delete Dao STARTED -----> ");
		PrivacyPolicyEntity privacyPolicyEntity = new PrivacyPolicyEntity();
		try {
			privacyPolicyRepository.deleteById(id);
		} catch (Exception e) {
			log.error("<---- PrivacyPolicyDaoImpl.delete() ----> EXCEPTION", e);
		}
		//log.info(" <----- Privacy Policy delete Dao END -----> ");
		return privacyPolicyEntity;
	}

	@Override
	public PrivacyPolicyEntity getById(UUID id) {
		//log.info(" <----- Privacy Policy getById Dao STARTED -----> ");
		PrivacyPolicyEntity privacyPolicyEntity = new PrivacyPolicyEntity();
		try {
			privacyPolicyEntity = privacyPolicyRepository.findOne(id);
		} catch (Exception e) {
			log.error("<---- PrivacyPolicyDaoImpl.getById() ----> EXCEPTION", e);
		}
		//log.info(" <----- Privacy Policy getById Dao END -----> ");
		return privacyPolicyEntity;
	}

	@Override
	public List<PrivacyPolicyEntity> getAll() {
		//log.info(" <----- Privacy Policy getAll Dao STARTED -----> ");
		List<PrivacyPolicyEntity> privacyPolicyEntity = new ArrayList<PrivacyPolicyEntity>();
		try {
			privacyPolicyEntity = privacyPolicyRepository.findAll();
		} catch (Exception e) {
			log.error("<----- PrivacyPolicyDaoImpl.getAll() ----->", e);
		}
		//log.info(" <----- Privacy Policy getAll Dao END -----> ");
		return privacyPolicyEntity;
	}

	@Override
	public Page<PrivacyPolicyEntity> getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- Privacy Policy getLazyList Dao STARTED -----> ");
		Pageable pageRequest;
		Page<PrivacyPolicyEntity> privacyPolicyEntity = null;
		try {
			if (requestData.getSearch() != null) {
				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.ASC, requestData.getSortField()));
					privacyPolicyEntity = privacyPolicyRepository.search(pageRequest, requestData.getSearch().toUpperCase());
				} else {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.DESC, requestData.getSortField()));
					privacyPolicyEntity = privacyPolicyRepository.search(pageRequest, requestData.getSearch().toUpperCase());
				}
			} else {

				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					privacyPolicyEntity = privacyPolicyRepository.findAll(PageRequest.of(requestData.getPageNo(),
							requestData.getPaginationSize(), Sort.by(Direction.ASC, requestData.getSortField())));
				} else {
					privacyPolicyEntity = privacyPolicyRepository.findAll(PageRequest.of(requestData.getPageNo(),
							requestData.getPaginationSize(), Sort.by(Direction.DESC, requestData.getSortField())));
				}
			}
		} catch (Exception e) {
			log.error("<----- PrivacyPolicyDaoImpl.getLazyList() ----->", e);
		}
		//log.info(" <----- Privacy Policy getLazyList Dao END -----> ");
		return privacyPolicyEntity;
	}

	@Override
	public List<PrivacyPolicyEntity> getAllActive() {
		//log.info(" <----- Privacy Policy getAllActive Dao STARTED -----> ");
		List<PrivacyPolicyEntity> privacyPolicyEntity = new ArrayList<PrivacyPolicyEntity>();
		try {
			privacyPolicyEntity = privacyPolicyRepository.getAllActive();
		} catch (Exception e) {
			log.error("<----- PrivacyPolicyDaoImpl.getAllActive() ----->", e);
		}
		//log.info(" <----- Privacy Policy getAllActive Dao END -----> ");
		return privacyPolicyEntity;
	}

	@Override
	public List<PrivacyPolicyEntity> exceptId(UUID id) {
		//log.info(" <----- update Privacy Policy Dao STARTED -----> ");
		List<PrivacyPolicyEntity> privacyPolicyEntity = new ArrayList<PrivacyPolicyEntity>();
		try {
			privacyPolicyEntity = privacyPolicyRepository.getByExceptId(id);
		} catch (Exception e) {
			log.error("<---- PrivacyPolicyDaoImpl.update() ----> EXCEPTION", e);
		}
		//log.info(" <----- update Privacy Policy Dao END -----> ");
		return privacyPolicyEntity;
	}

	@Override
	public PrivacyPolicyEntity getOne() {
		PrivacyPolicyEntity privacyPolicyEntity = new PrivacyPolicyEntity();
		try {
			privacyPolicyEntity = privacyPolicyRepository.getOne();
		} catch (Exception e) {
			log.error("<----- PrivacyPolicyDaoImpl.getOne() ----->", e);
		}
		return privacyPolicyEntity;
	}

}
