package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.oasys.uppcl_user__master_management.entity.BlockMasterEntity;
import com.oasys.uppcl_user__master_management.entity.GramPanchayat;


@Service
public interface GramPanchayatRepository extends JpaRepository<GramPanchayat, UUID>{

	@Query("SELECT gp FROM GramPanchayat gp WHERE gp.status = true and gp.blockId =:id ORDER by gp.panchayatName")
	public List<GramPanchayat> getByBlockId(@Param("id") BlockMasterEntity id);
	
	@Query("select a from GramPanchayat a where  a.blockId.id =:blockid and a.panchayatName = :name and  a.panchayatCode = :code ")
	Optional<GramPanchayat> findByBlockIdAndPanchayatNameAndPanchayatCode(@Param("blockid") UUID blockid,@Param("name")  String name,@Param("code") String code);

	@Query("select a from GramPanchayat a where a.panchayatCode =:code")
	List<GramPanchayat> checkPanchayatCode(@Param("code") String code);
}
