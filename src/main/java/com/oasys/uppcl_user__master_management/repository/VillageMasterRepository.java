package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.VillageMasterEntity;


public interface VillageMasterRepository extends JpaRepository<VillageMasterEntity, UUID> {

	@Query("select a from VillageMasterEntity a where a.status = true ORDER by a.villageName")
	List<VillageMasterEntity> getAllActive();

	@Query("select a from VillageMasterEntity a where a.status = true ORDER by a.villageName")
	Page<VillageMasterEntity> getAllActive(Pageable pageable);

	VillageMasterEntity findByVillageNameIgnoreCase(String name);

	@Query("select a from VillageMasterEntity a where a.id != :id")
	List<VillageMasterEntity> findByIdNotIn(UUID id);
	
	
	@Query("SELECT a.id FROM VillageMasterEntity a WHERE a.talukId.id = :talukId AND UPPER(a.villageName) = :name AND a.code = :code")
	VillageMasterEntity findByTalukIdandVillagenameandCode(@Param("talukId") UUID talukId, @Param("name") String name, @Param("code") String code);
	

	@Query("select a from VillageMasterEntity a where a.status = true and a.talukId.id =:talukId ORDER by a.villageName ASC")
	List<VillageMasterEntity> findByTalukId(@Param("talukId") UUID talukId);
	
	@Query("SELECT a.id FROM VillageMasterEntity a WHERE a.code = :code")
	Optional<VillageMasterEntity> findByVillageCode( @Param("code") String code);
	
	
	@Query("select a from VillageMasterEntity a where a.code= :code and a.id not in :id")
	 List<VillageMasterEntity> checkCodeUpdate(@Param("code") String code,@Param("id") UUID id);
	
	
	@Query("SELECT a FROM VillageMasterEntity a WHERE a.talukId.id = :talukId AND UPPER(a.villageName) = :name AND a.code = :code and a.id not in :id")
	List<VillageMasterEntity> getByTalukIdAndNameAndCode1( @Param("talukId") UUID talukId,@Param("name")  String upperCase,@Param("code")  String code,@Param("id") UUID id);
	
	@Query("SELECT a.id FROM VillageMasterEntity a WHERE a.talukId.id = :talukId AND UPPER(a.villageName) = :name ")
	Optional<VillageMasterEntity> findByTalukIdandVillageName(@Param("talukId") UUID talukId, @Param("name") String name);
	
	@Query("select a from VillageMasterEntity a where  a.talukId.id =:talukId and a.villageName =:name and a.id not in :id ")
	Optional<VillageMasterEntity> checkTalukIdandVillageNameForUpdate(@Param("talukId") UUID talukId ,@Param("name") String villageName,@Param("id") UUID id );
	
}
