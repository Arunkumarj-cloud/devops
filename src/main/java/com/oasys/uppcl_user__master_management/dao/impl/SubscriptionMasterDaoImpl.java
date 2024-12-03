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
import com.oasys.uppcl_user__master_management.dao.SubscriptionMasterDao;
import com.oasys.uppcl_user__master_management.entity.SubscriptionMasterEntity;
import com.oasys.uppcl_user__master_management.repository.SubscriptionMasterRepository;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class SubscriptionMasterDaoImpl implements SubscriptionMasterDao {
	@Autowired
	SubscriptionMasterRepository repository;
	
	public SubscriptionMasterEntity save(SubscriptionMasterEntity subscription) {
		//log.info(" <----- SubscriptionMaster save Dao STARTED -----> ");
		try {
			subscription = repository.save(subscription);

		} catch (DataIntegrityViolationException e) {
			log.error("<---- SubscriptionMasterDaoImpl.save() ----> EXCEPTION", e);
		} catch (Exception e) {

			log.error("<---- SubscriptionMasterDaoImpl.save() ----> EXCEPTION", e);
		}
		//log.info(" <----- SubscriptionMaster save Dao END -----> ");
		return subscription;
	}

	public SubscriptionMasterEntity getById(UUID id) {
		//log.info(" <----- SubscriptionMaster getById Dao STARTED -----> ");
		SubscriptionMasterEntity subscription = new SubscriptionMasterEntity();
		try {
			subscription = repository.getOne(id);

		} catch (Exception e) {
			log.error("<---- SubscriptionMasterDaoImpl.getById() ----> EXCEPTION", e);
		}
		//log.info(" <----- SubscriptionMaster getById Dao END -----> ");
		return subscription;
	}

	public List<SubscriptionMasterEntity> getAll() {
		//log.info(" <----- SubscriptionMaster getAll Dao STARTED -----> ");
		List<SubscriptionMasterEntity> subscription = new ArrayList<SubscriptionMasterEntity>();
		try {
			subscription = repository.findAll();

		} catch (Exception e) {
			log.error("<---- SubscriptionMasterDaoImpl.getAll() ----> EXCEPTION", e);
		}
		//log.info(" <----- SubscriptionMaster getAll Dao END -----> ");
		return subscription;
	}

	public List<SubscriptionMasterEntity> getAllActive() {
		//log.info(" <----- SubscriptionMaster getAllActive Dao STARTED -----> ");
		List<SubscriptionMasterEntity> subscription = new ArrayList<SubscriptionMasterEntity>();
		try {
			subscription = repository.findByIsActiveTrue();

		} catch (Exception e) {
			log.error("<---- SubscriptionMasterDaoImpl.getAll() ----> EXCEPTION", e);
		}
		//log.info(" <----- SubscriptionMaster getAllActive Dao END -----> ");
		return subscription;
	}

	public Page<SubscriptionMasterEntity> getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- SubscriptionMaster getLazyList Dao STARTED -----> ");
		Pageable pageRequest;
		Page<SubscriptionMasterEntity> subscription = null;
		try {
			if (requestData.getSearch() != null) {
				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.ASC, requestData.getSortField()));
					subscription = repository.search(pageRequest, requestData.getSearch());
				} else {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.DESC, requestData.getSortField()));
					subscription = repository.search(pageRequest, requestData.getSearch());
				}
			} else {

				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					subscription = repository.findAll(PageRequest.of(requestData.getPageNo(),
							requestData.getPaginationSize(), Sort.by(Direction.DESC, requestData.getSortField())));
				} else {
					subscription = repository.findAll(PageRequest.of(requestData.getPageNo(),
							requestData.getPaginationSize(), Sort.by(Direction.DESC, requestData.getSortField())));
				}
			}
		} catch (Exception e) {
			log.error("<----- SubscriptionMasterDaoImpl.getLazyList() ----->", e);
		}
		//log.info(" <----- SubscriptionMaster getLazyList Dao END -----> ");
		return subscription;
	}

	public SubscriptionMasterEntity delete(UUID id) {
		//log.info(" <----- SubscriptionMaster delete Dao STARTED -----> ");
		SubscriptionMasterEntity subscription = new SubscriptionMasterEntity();
		try {
			repository.deleteById(id);
		} catch (Exception e) {
			log.error("<---- SubscriptionMasterDaoImpl.getById() ----> EXCEPTION", e);
		}
		//log.info(" <----- SubscriptionMaster delete Dao END -----> ");
		return subscription;
	}

	public SubscriptionMasterEntity validateSubscriptionName(String subscriptionName) {
		//log.info(" <----- SubscriptionMaster validateSubscriptionName Dao STARTED -----> ");
		SubscriptionMasterEntity subscription = new SubscriptionMasterEntity();
		try {
			subscription = repository.findBySubscriptionNameIgnoreCase(subscriptionName);
		} catch (Exception e) {
			log.error("<---- SubscriptionMasterDaoImpl.validateSubscriptionName() ----> EXCEPTION", e);
		}
		//log.info(" <----- SubscriptionMaster validateSubscriptionName Dao END -----> ");
		return subscription;
	}

	public SubscriptionMasterEntity validateIsDefault() {
		//log.info(" <----- SubscriptionMaster validateIsDefault Dao STARTED -----> ");
		SubscriptionMasterEntity subscription = new SubscriptionMasterEntity();
		try {
			subscription = repository.findByIsDefaultTrue();
		} catch (Exception e) {
			log.error("<---- SubscriptionMasterDaoImpl.validateIsDefault() ----> EXCEPTION", e);
		}
		//log.info(" <----- SubscriptionMaster validateIsDefault Dao END -----> ");
		return subscription;
	}

	public List<SubscriptionMasterEntity> exceptId(UUID id) {
		//log.info(" <----- SubscriptionMaster exceptId Dao STARTED -----> ");
		List<SubscriptionMasterEntity> subscription = new ArrayList<SubscriptionMasterEntity>();
		try {
			subscription = repository.findByIdNotEqualToId(id);

		} catch (Exception e) {
			log.error("<---- SubscriptionMasterDaoImpl.exceptId() ----> EXCEPTION", e);
		}
		//log.info(" <----- SubscriptionMaster exceptId Dao END -----> ");
		return subscription;

	}

	@Override
	public List<SubscriptionMasterEntity> getDefault() {
		//log.info(" <----- SubscriptionMaster exceptId Dao STARTED -----> ");
		List<SubscriptionMasterEntity> list = null;
		try {
			list = repository.getDefault();

		} catch (Exception e) {
			log.error("<---- SubscriptionMasterDaoImpl.getDefault() ----> EXCEPTION", e);
		}
		//log.info(" <----- SubscriptionMaster exceptId Dao END -----> ");
		return list;
	}

	@Override
	public List<SubscriptionMasterEntity> check(String name) {
		//log.info(" <----- SubscriptionMaster exceptId Dao STARTED -----> ");
		List<SubscriptionMasterEntity> list = null;
		try {
			list = repository.check(name);

		} catch (Exception e) {
			log.error("<---- SubscriptionMasterDaoImpl.check() ----> EXCEPTION", e);
		}
		//log.info(" <----- SubscriptionMaster exceptId Dao END -----> ");
		return list;
	}

}
