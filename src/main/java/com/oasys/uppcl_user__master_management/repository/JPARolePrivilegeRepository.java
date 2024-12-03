package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.RolePrivilegeEntity;
import com.oasys.uppcl_user__master_management.entity.RolePrivilegePK;

public interface JPARolePrivilegeRepository extends JpaRepository<RolePrivilegeEntity, RolePrivilegePK> {
	@Modifying
	@Query("delete from RolePrivilegeEntity b where b.rolePrivilegePK.roleId = :roleId")
	public void deletePrivilegesOfRole(@Param("roleId") UUID roleId);
	
	@Modifying
	@Query("select b.rolePrivilegePK.privilegeName from RolePrivilegeEntity b where b.rolePrivilegePK.roleId = :roleId")
	public List<String> getPrivilegesOfRole(@Param("roleId") UUID roleId);
		
	
	@Query("select obj.rolePrivilegePK.privilegeName from RolePrivilegeEntity obj")
    Set<String> findMyPrivilages();
	
	@Query("select count(obj) from RolePrivilegeEntity obj where obj.rolePrivilegePK.privilegeName=:name")
    Long findPrivilagesCount(@Param("name") String name);

	@Query("select b.rolePrivilegePK.privilegeName from RolePrivilegeEntity b where b.rolePrivilegePK.roleId = :roleId and b.rolePrivilegePK.privilegeName=:privilegeName")
	Optional<RolePrivilegeEntity> findByRoleAndPermission(@Param("roleId") UUID roleId,@Param("privilegeName") String privilegeName);

}
