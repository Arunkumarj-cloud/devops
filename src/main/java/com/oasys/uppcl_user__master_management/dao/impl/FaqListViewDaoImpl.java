//package com.oasys.uppcl_user__master_management.dao.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//
//import com.oasys.uppcl_user__master_management.dao.FaqListViewDao;
//import com.oasys.uppcl_user__master_management.entity.FaqListViewEntity;
//import com.oasys.uppcl_user__master_management.repository.FaqListViewRepository;
//
//
//
//public class FaqListViewDaoImpl implements FaqListViewDao{
//
//	@Autowired
//	FaqListViewRepository faqListViewRepository;
//
//	
//			@Override
//			public Page<FaqListViewEntity> search(Pageable page,String key) {
//				//log.info("<---FaqListViewDaoImpl  search Start --->");
//				Page<FaqListViewEntity> entity = faqListViewRepository.search(page, key);
//				//log.info("<--- Application Dao Impl delete End --->");
//				return entity;
//			}
//			
//			@Override
//			public Page<FaqListViewEntity> findall1(Pageable page) {
//				//log.info("<--- FaqListViewDaoImpl  findall1 Start --->");
//				Page<FaqListViewEntity> entity = faqListViewRepository.findAll(page);
//				//log.info("<--- FaqListViewDaoImpl findall1 End --->");
//				return entity;
//			}		
//}

