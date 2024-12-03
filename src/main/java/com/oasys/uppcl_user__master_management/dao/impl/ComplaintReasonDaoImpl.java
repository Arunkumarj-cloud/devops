package com.oasys.uppcl_user__master_management.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.ComplaintReasonDao;
import com.oasys.uppcl_user__master_management.dto.ComplaintReasonDTO;
import com.oasys.uppcl_user__master_management.entity.ComplaintReasonEntity;
import com.oasys.uppcl_user__master_management.repository.ComplaintReasonRepository;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class ComplaintReasonDaoImpl implements ComplaintReasonDao{

	@Autowired
	ComplaintReasonRepository complaintReasonRepository;

	@Override
	public ComplaintReasonEntity save(ComplaintReasonEntity complaintReasonEntity) {
		//log.info(" <----- ComplaintReasonDaoImpl.save() STARTED -----> ");
		try {
			complaintReasonEntity = complaintReasonRepository.save(complaintReasonEntity);
		} catch (Exception e) {
			log.error("<---- ComplaintReasonDaoImpl.save() ----> EXCEPTION", e);
		}
		//log.info(" <----- ComplaintReasonDaoImpl.save() ENDED -----> ");
		return complaintReasonEntity;
	}

	@Override
	public List<ComplaintReasonEntity> getAll() {
		//log.info(" <----- ComplaintReasonDaoImpl.getAll() STARTED -----> ");
		List<ComplaintReasonEntity> complaintReasonEntityList = new ArrayList<ComplaintReasonEntity>();
		try {
			complaintReasonEntityList = complaintReasonRepository.findAll();

		} catch (Exception e) {
			log.error("<---- ComplaintReasonDaoImpl.getAll() ----> EXCEPTION", e);
		}
		//log.info(" <----- ComplaintReasonDaoImpl.getAll() ENDED -----> ");
		return complaintReasonEntityList;
	}

	@Override
	public List<ComplaintReasonEntity> getAllActive() {
		//log.info(" <----- ComplaintReasonDaoImpl.getAllActive() STARTED -----> ");
		List<ComplaintReasonEntity> complaintReasonEntityList = new ArrayList<ComplaintReasonEntity>();
		try {
			complaintReasonEntityList = complaintReasonRepository.findByStatusTrue();

		} catch (Exception e) {
			log.error("<---- ComplaintReasonDaoImpl.getAll() ----> EXCEPTION", e);
		}
		//log.info(" <----- ComplaintReasonDaoImpl.getAllActive() ENDED -----> ");
		return complaintReasonEntityList;
	}

	@Override
	public BaseDTO update(UUID id, ComplaintReasonDTO complaintReasonDTO) {

		return null;
	}

	@Override
	public ComplaintReasonEntity getById(UUID id) {
		//log.info(" <----- ComplaintReasonDaoImpl.getById() STARTED -----> ");
		ComplaintReasonEntity complaintReasonEntity = new ComplaintReasonEntity();
		try {
			complaintReasonEntity = complaintReasonRepository.getOne(id);
		} catch (Exception e) {
			log.error("<---- ComplaintReasonDaoImpl.getById() ----> EXCEPTION", e);
		}
		//log.info(" <----- ComplaintReasonDaoImpl.getById() ENDED -----> ");
		return complaintReasonEntity;
	}

	@Override
	public ComplaintReasonEntity delete(UUID id) {
		//log.info(" <----- ComplaintReasonDaoImpl.getById() STARTED -----> ");
		ComplaintReasonEntity complaintReasonEntity = new ComplaintReasonEntity();
		try {
			complaintReasonRepository.deleteById(id);
		} catch (Exception e) {
			log.error("<---- ComplaintReasonDaoImpl.getById() ----> EXCEPTION", e);
		}
		//log.info(" <----- ComplaintReasonDaoImpl.getById() ENDED -----> ");
		return complaintReasonEntity;
	}

	@Override
	public Page<ComplaintReasonEntity> getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- ComplaintReasonDaoImpl.getLazyList() STARTED -----> ");
		Pageable pageRequest;
		Page<ComplaintReasonEntity> complaintReasonEntity = null;
		try {
			if (requestData.getSearch() != null) {
				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.ASC, requestData.getSortField()));
					complaintReasonEntity = complaintReasonRepository.search(pageRequest, requestData.getSearch());
				} else {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.DESC, requestData.getSortField()));
					complaintReasonEntity = complaintReasonRepository.search(pageRequest, requestData.getSearch());
				}
			} else {

				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					complaintReasonEntity = complaintReasonRepository.findAll(PageRequest.of(requestData.getPageNo(),
							requestData.getPaginationSize(), Sort.by(Direction.ASC, requestData.getSortField())));
				} else {
					complaintReasonEntity = complaintReasonRepository.findAll(PageRequest.of(requestData.getPageNo(),
							requestData.getPaginationSize(), Sort.by(Direction.DESC, requestData.getSortField())));
				}
			}
		} catch (Exception e) {
			log.error("<----- ComplaintReasonDaoImpl.getLazyList() ----->", e);
			e.printStackTrace();
		}
		//log.info(" <----- ComplaintReasonDaoImpl.getLazyList() ENDED -----> ");
		return complaintReasonEntity;
	}

}
