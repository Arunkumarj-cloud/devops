package com.oasys.uppcl_user__master_management.service.impl;



import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.TemplateDao;
import com.oasys.uppcl_user__master_management.dto.TemplateDTO;
import com.oasys.uppcl_user__master_management.service.TemlpateService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TemlpateServiceImple implements TemlpateService {

	@Autowired
	TemplateDao templateDao;

	@Override
	public BaseDTO createTemplate(TemplateDTO templateDTO) {
		//log.info("<----------Started TemlpateServiceImple.createTemplate()-----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = templateDao.createTemplate(templateDTO);
		} catch (Exception e) {
			log.error("<----------Exception at  TemlpateServiceImple.createTemplate()-----> ", e);
		}
		//log.info("<----------Ended TemlpateServiceImple.createTemplate()-----> ");
		return response;
	}

	@Override
	public BaseDTO getAll() {
		//log.info("<----------Started TemlpateServiceImple.getAll()-----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = templateDao.getAll();
		} catch (Exception e) {
			log.error("<----------Exception at  TemlpateServiceImple.getAll()-----> ", e);
		}
		//log.info("<----------Ended TemlpateServiceImple.getAll()-----> ");
		return response;
	}
	
	@Override
	public BaseDTO get() {
		BaseDTO response = new BaseDTO();
		try {
			response = templateDao.get();
		} catch (Exception e) {
			log.error("<----------Exception at  TemlpateServiceImple.get()-----> ", e);
		}return response;
	}

	@Override
	public BaseDTO getById(UUID id) {
		//log.info("<----------Started TemlpateServiceImple.getById()-----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = templateDao.getById(id);
		} catch (Exception e) {
			log.error(" Exception atTemlpateServiceImple.getById() ");
		}
		//log.info("<----------Ended TemlpateServiceImple.getById()-----> ");
		return response;
	}

	@Override
	public BaseDTO getByName(String name) {
		//log.info("<----------Started TemlpateServiceImple.getByName()-----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = templateDao.getByName(name);

		} catch (Exception e) {
			log.error(" Exception atTemlpateServiceImple.getByName() ", e);
		}
		//log.info("<----------Ended TemlpateServiceImple.getByName()-----> ");
		return response;
	}

	@Override
	public BaseDTO lazyList(PaginationRequestDTO pageData) {
		//log.info("<----------Started TemlpateServiceImple.lazyList()-----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = templateDao.lazyList(pageData);
		} catch (Exception e) {
			log.error(" Exception atTemlpateServiceImple.lazyList() ", e);
		}
		//log.info("<----------Ended TemlpateServiceImple.lazyList()-----> ");
		return response;
	}
	
	@Override
	public BaseDTO lazyList1(PaginationRequestDTO pageData) {
		BaseDTO response = new BaseDTO();
		try {
			response = templateDao.lazyList1(pageData);
		} catch (Exception e) {
			log.error(" Exception atTemlpateServiceImple.lazyList1() ", e);
		}return response;
	}

	@Override
	public BaseDTO update(UUID id, TemplateDTO templateDTO) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started TemlpateServiceImple.update() ===>");
		try {
			response = templateDao.update(id, templateDTO);
		} catch (Exception e) {
			log.error("<===Exception  TemlpateServiceImple.update() ===> ");
		}
		//log.info("<=== Ended TemlpateServiceImple.update() ===>");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		//log.info(" <-----TemlpateServiceImple.getAllActive() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = templateDao.getAllActive();
		} catch (Exception e) {
			log.error("<===Exception  TemlpateServiceImple.update() ===> ");
		}
		//log.info(" <-----TemlpateServiceImple.getAllActive() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO softDelete(UUID id) {
		//log.info(" <-----TemlpateServiceImple.softDelete() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = templateDao.softDelete(id);
		} catch (Exception e) {
			log.error("<===Exception  TemlpateServiceImple.update() ===> ");
		}
		//log.info(" <-----TemlpateServiceImple.softDelete() Service END -----> ");
		return response;
	}

}
