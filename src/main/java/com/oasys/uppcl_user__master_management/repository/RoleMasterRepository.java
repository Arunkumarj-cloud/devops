
package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.MerchantTypeMasterEntity;
import com.oasys.uppcl_user__master_management.entity.RoleMasterEntity;


public interface RoleMasterRepository extends JpaRepository<RoleMasterEntity, UUID> {

	@Query("SELECT a FROM RoleMasterEntity a WHERE a.status =1 ORDER BY a.roleName ASC")
	List<RoleMasterEntity> getAllactive();
	
	@Query("SELECT mm FROM RoleMasterEntity mm where UPPER(mm.roleName) LIKE %:value% or UPPER(mm.description) like %:value%")
	Page<RoleMasterEntity> search(Pageable pageable,@Param("value") String value);

	//@Query(nativeQuery = true,value="SELECT * FROM  merchant_type  WHERE id in (:list)")
	List<RoleMasterEntity> findByIdIn(List<UUID> list);

	@Query("SELECT a FROM RoleMasterEntity a WHERE roleName = :name")
	Optional<RoleMasterEntity> findByRoleName(@Param("name") String name);
	
	/*List<RoleMasterEntity> checkName(String roleName);*/
	@Query("SELECT a FROM RoleMasterEntity a WHERE UPPER(a.roleName) LIKE %:name%")
	List<RoleMasterEntity> findByName( @ Param ("name") String roleName);


	@Query("select a from RoleMasterEntity a where a.status = true and a.userType =:userType ORDER by a.roleName ASC")
	List<RoleMasterEntity> getByUserTypeId(@Param("userType") MerchantTypeMasterEntity userType);
	
	@Query("SELECT a FROM RoleMasterEntity a WHERE a.status=1 and REPLACE(upper(a.roleName),' ','') in ('districtDistributors','pincodeDistributors','Merchant','SuperPincodeDistributor','SuperDistrictDistributor')")
	List<RoleMasterEntity> findList();
	
	@Query("SELECT a FROM RoleMasterEntity a WHERE a.status=1 and REPLACE(upper(a.roleName),' ','') in ('districtDistributors','pincodeDistributors','Merchant','Admin','SuperPincodeDistributor','SuperDistrictDistributor')")
	List<RoleMasterEntity> findRoleList();
	
	
	@Query("SELECT a FROM RoleMasterEntity a WHERE a.status=1 and REPLACE(upper(a.roleName),' ','') in ('districtDistributors','Merchant','SuperDistrictDistributor')")
	List<RoleMasterEntity> findlist();
	
	@Query("SELECT a FROM RoleMasterEntity a WHERE a.status=1 and REPLACE(upper(a.roleName),' ','') in ('pincodeDistributors','Merchant','SuperPincodeDistributor')")
	List<RoleMasterEntity> findlist1();
	
	@Query("SELECT a FROM RoleMasterEntity a WHERE a.status=1 and REPLACE(upper(a.roleName),' ','') in ('Merchant')")
	List<RoleMasterEntity> findlist2();

	@Query("SELECT a FROM RoleMasterEntity a WHERE a.status=1 and REPLACE(upper(a.roleName),' ','') in ('districtDistributors','pincodeDistributors','Admin','SalesOfficer')")
	List<RoleMasterEntity> getActiveRoleByModel1();

	@Query("SELECT a FROM RoleMasterEntity a WHERE a.status=1 and REPLACE(upper(a.roleName),' ','') in ('SuperDistrictDistributor','SuperPincodeDistributor','Admin','SalesOfficer')")
	List<RoleMasterEntity> getActiveRoleByModel2();

	@Query("SELECT a FROM RoleMasterEntity a WHERE a.status=1 and REPLACE(upper(a.roleName),' ','') in ('districtDistributors','pincodeDistributors','Merchant')")
	List<RoleMasterEntity> getActiveRoleByAdminModel1();

	@Query("SELECT a FROM RoleMasterEntity a WHERE a.status=1 and REPLACE(upper(a.roleName),' ','') in ('pincodeDistributors','Merchant')")
	List<RoleMasterEntity> getActiveRoleByDistModel1();

	@Query("SELECT a FROM RoleMasterEntity a WHERE a.status=1 and REPLACE(upper(a.roleName),' ','') in ('Merchant')")
	List<RoleMasterEntity> getActiveRoleByPin();

	@Query("SELECT a FROM RoleMasterEntity a WHERE a.status=1 and REPLACE(upper(a.roleName),' ','') in ('SuperDistrictDistributor','SuperPincodeDistributor','Merchant')")
	List<RoleMasterEntity> getActiveRoleByAdminModel2();

	@Query("SELECT a FROM RoleMasterEntity a WHERE a.status=1 and REPLACE(upper(a.roleName),' ','') in ('SuperPincodeDistributor','Merchant')")
	
	List<RoleMasterEntity> getActiveRoleByDistModel2();
	
	@Query("SELECT a FROM RoleMasterEntity a WHERE a.status=1 and REPLACE(upper(a.userRoleName),' ','') in ('SuperDistributor','Distributor','Merchant')")
	List<RoleMasterEntity> getActiveRolesForKYCAdminUserListModel2();
	
	@Query("SELECT a FROM RoleMasterEntity a WHERE a.status=1 and REPLACE(upper(a.userRoleName),' ','') in ('DistrictDistributor','PincodeDistributor','Merchant')")
	List<RoleMasterEntity> getActiveRolesForKYCAdminUserListModel1();
	
	@Query("SELECT a FROM RoleMasterEntity a WHERE a.status=1 and a.userRoleName not in :userRoleNames ")
	List<RoleMasterEntity> getAllRolesNotInUserRoleList( @ Param ("userRoleNames") List<String> userRoleNames);
	
	@Query("SELECT a FROM RoleMasterEntity a WHERE a.status=1 and a.userRoleName in :userRoleNames ")
	List<RoleMasterEntity> getAllRolesInUserRoleList( @ Param ("userRoleNames") List<String> userRoleNames);

	@Query("SELECT a FROM RoleMasterEntity a WHERE a.status=1 and a.id in :roleIds ")
	List<RoleMasterEntity> getAllRolesByIds( @ Param ("roleIds") List<UUID> roleIds);
	
	@Query("SELECT a FROM RoleMasterEntity a WHERE upper(roleName) = :name")
	Optional<RoleMasterEntity> findByRoleNameIgnoreCase(@Param("name") String name);
	
	@Query("SELECT a FROM RoleMasterEntity a WHERE upper(userRoleName) = :name")
	Optional<RoleMasterEntity> findByUserRoleNameIgnoreCase(@Param("name") String name);
}
