package com.oasys.uppcl_user__master_management.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


import com.oasys.uppcl_user__master_management.entity.PackageHistory;




public interface PackageHistoryRepository extends JpaRepository<PackageHistory, UUID>{

	
}
