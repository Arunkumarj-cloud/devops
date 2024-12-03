package com.oasys.uppcl_user__master_management.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.TalukMasterEntity;



public interface TalukMasterRepository extends JpaRepository<TalukMasterEntity, UUID> {
	
	@Query("select a from TalukMasterEntity a where a.id =:id ")
	 List <TalukMasterEntity> findByTalukId(@Param("id") UUID id);

	Optional<TalukMasterEntity> getById(UUID id);

	@Query("SELECT a.id FROM TalukMasterEntity a WHERE a.districtId.id = :districtId AND UPPER(a.talukName) = :name AND a.talukCode = :code")
	UUID getByDistrictIdAndNameAndCode( @Param("districtId") UUID districtId,@Param("name")  String upperCase,@Param("code")  String talukCode);

	@Query("select a from TalukMasterEntity a where  a.districtId.id =:districtId and a.talukName =:name and a.id not in :id ")
	Optional<TalukMasterEntity> checkDistrictIdandTalukNameForUpdate(@Param("districtId") UUID districtId,@Param("name") String talukName,@Param("id") UUID id  );

	@Query("select a from TalukMasterEntity a where a.status = true ORDER by a.talukName ASC")
	 List <TalukMasterEntity> getAllActive();

	@Query("select a from TalukMasterEntity a where a.talukCode= :talukCode and a.id not in :id")
	 List<TalukMasterEntity> checkCodeUpdate(@Param("talukCode") String talukCode,@Param("id") UUID id);

	
	@Query("select a from TalukMasterEntity a where a.talukCode= :talukCode")
	 Optional<TalukMasterEntity> checkCode(@Param("talukCode") String talukCode);
	
	@Query("select a from TalukMasterEntity a where a.talukName =:name and a.districtId.id =:id ")
	Optional<TalukMasterEntity> checkDistrictIdandTalukName(@Param("id") UUID id ,@Param("name") String talukName);
	
	@Query("select a from TalukMasterEntity a where a.status = true and a.districtId.id =:districtId ORDER by a.talukName ASC")
	List<TalukMasterEntity> findByDistrictId(@Param("districtId") UUID districtId);
	
	@Query("select b from TalukMasterEntity b")
	Page<TalukMasterEntity> getAllWithPage(Pageable pageable);

	@Query("select b from TalukMasterEntity b where (UPPER(b.talukName) LIKE %:value% or "
			+ "UPPER(b.talukCode) LIKE %:value% or UPPER(b.districtId.districtName) LIKE %:value% )")
	Page<TalukMasterEntity> getAllWithPageAndSearch(Pageable pageable,@Param("value") String search);

	@Query("select b from TalukMasterEntity b WHERE b.createdDate BETWEEN :fromDate AND :toDate")
	Page<TalukMasterEntity> getAllWithPageAndTime(Pageable pageable, @Param("fromDate") Date fromDate,@Param("toDate") Date toDate);

	@Query("select b from TalukMasterEntity b where (UPPER(b.talukName) LIKE %:value% or UPPER(b.talukCode)"
			+ " LIKE %:value% or UPPER(b.districtId.districtName) LIKE %:value% ) "
			+ "AND (b.createdDate BETWEEN :fromDate AND :toDate)")
	Page<TalukMasterEntity> getAllWithPageAndSearchAndTime(Pageable pageable,@Param("value") String search,@Param("fromDate") Date fromDate,
			@Param("toDate") Date toDate);

	


}
