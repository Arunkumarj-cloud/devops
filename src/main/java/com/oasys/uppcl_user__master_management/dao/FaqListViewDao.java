package com.oasys.uppcl_user__master_management.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.oasys.uppcl_user__master_management.entity.FaqListViewEntity;



@Service
public interface FaqListViewDao {
Page<FaqListViewEntity> search(Pageable page,String key);
	
	Page<FaqListViewEntity> findall1(Pageable page);

}
