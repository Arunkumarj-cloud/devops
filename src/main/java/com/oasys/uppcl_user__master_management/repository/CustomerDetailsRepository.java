package com.oasys.uppcl_user__master_management.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.CustomerDetails;

public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails, UUID> {


	@Query("SELECT s FROM CustomerDetails s WHERE s.smartCardNumber=:smartCardNumber")
	Optional<CustomerDetails> findBySmartCard(@Param("smartCardNumber") String smartCardNumber);
	
}
