package com.oasys.uppcl_user__master_management.controller;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.RoleMasterDTO;
import com.oasys.uppcl_user__master_management.service.RoleMasterService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("role")
@Log4j2
public class RoleMasterController {

	@Autowired
	RoleMasterService roleMasterService;
	
	//@PreAuthorize(PrivilegeConstant.ADD_USER_ROLE)
	@PostMapping("/create")
	public BaseDTO create(@Valid @RequestBody RoleMasterDTO roleMaster) {
		//log.info("<== Started RoleMasterController.create ==>");
		BaseDTO response = roleMasterService.create(roleMaster);
		return response;
	}
	
	
	/* @PreAuthorize("#oauth2.hasScope('Admin')") */
	@GetMapping("/getAll")
	public BaseDTO getAll() {
		//log.info("<== Started RoleMasterController.getAll ==>");
		BaseDTO response = roleMasterService.getAll();
		//log.info("<== Ended RoleMasterController.getAll ==>");
		return response;
	}
	
	
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getAllRole")
	public BaseDTO getAllRole() {
		BaseDTO response = roleMasterService.getAllRole();
		return response;
	}
	
	

	//@PreAuthorize(PrivilegeConstant.UPDATE_USER_ROLE)
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable("id") UUID id,@Valid @RequestBody RoleMasterDTO roleDTO) {
		//log.info("<== Started RoleMasterController.update ==>");
		BaseDTO response = roleMasterService.update(id,roleDTO);
		//log.info("<== Ended RoleMasterController.update ==>");
		return response;
	}
	
	//@PreAuthorize("isAuthenticated()")
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		//log.info("<== Started RoleMasterController.update ==>");
		BaseDTO response = roleMasterService.getById(id);
		//log.info("<== Ended RoleMasterController.update ==>");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/delete/{id}")
	public BaseDTO delete(@PathVariable UUID id) {
		//log.info("<== Started RoleMasterController.delete ==>");
		BaseDTO response = roleMasterService.delete(id);
		//log.info("<== Ended RoleMasterController.delete ==>");
		return response;
	}
	
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
		BaseDTO response = new BaseDTO();
		//log.info("<== Started RoleMasterController.getLazyList ==>");
		response = roleMasterService.getLazyList(requestData);
		//log.info("<== Ended RoleMasterController.getLazyList ==>");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin') or #oauth2.hasScope('Third Party') or #oauth2.hasScope('Sales Officer') or #oauth2.hasScope('Customer Support Officer') or #oauth2.hasScope('Finance Officer') "
	//		+ "or #oauth2.hasScope('TenantAdmin') or #oauth2.hasScope('districtDistributors') or #oauth2.hasScope('pincodeDistributors')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<== Started RoleMasterController.getAllActive ==>");
		BaseDTO response = roleMasterService.getAllActive();
		//log.info("<== Ended RoleMasterController.getAllActive ==>");
		return response;
	}
	
//	@PreAuthorize("#oauth2.hasScope('Admin') or #oauth2.hasScope('Third Party') or #oauth2.hasScope('Sales Officer') or #oauth2.hasScope('Customer Support Officer') or #oauth2.hasScope('Finance Officer')"
//			+ " or #oauth2.hasScope('TenantAdmin') or #oauth2.hasScope('districtDistributors') or #oauth2.hasScope('pincodeDistributors') ")
//	
	//@PreAuthorize("isAuthenticated()")
	@GetMapping("/getbyname/{name}")
	public BaseDTO getByName(@PathVariable("name") String name) {
		//log.info("<== Started RoleMasterController.getByName ==>");
		BaseDTO response = roleMasterService.getByName(name);
		//log.info("<== Ended RoleMasterController.getByName ==>");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<== Started RoleMasterController.softDelete ==>");
		BaseDTO response = roleMasterService.softDelete(id);
		//log.info("<== Ended RoleMasterController.softDelete ==>");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin') or #oauth2.hasScope('TenantAdmin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getByUserTypeId/{id}")
	public BaseDTO getByUserTypeId(@PathVariable("id") UUID id) {
		//log.info("<--- Started RoleMasterController.getByUserTypeId --->");
		BaseDTO baseDTO = roleMasterService.getByUserTypeId(id);
		//log.info("<--- Ended RoleMasterController.getByUserTypeId --->");
		return baseDTO;
	}

	//@PreAuthorize("#oauth2.hasScope('Sales Officer') or #oauth2.hasScope('Customer Support Officer') or #oauth2.hasScope('Finance Officer')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getAllRoleList")
	public BaseDTO getAllRoleList() {
		BaseDTO response = roleMasterService.getAllRoleList();
		return response;
	}
	
	//@PreAuthorize("isAuthenticated()")
	@GetMapping("/getRoleList/{id}")
	public BaseDTO getRoleList(@PathVariable("id") UUID id) {
		BaseDTO response = roleMasterService.getRoleList(id);
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@GetMapping("/getrolecreator")
	public BaseDTO getRoleCreator() {
		BaseDTO response = roleMasterService.getRoleCreator();
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@GetMapping("/getrolecreation/{id}")
	public BaseDTO getRoleCreation(@PathVariable("id") UUID id) {
		BaseDTO response = roleMasterService.getRoleCreation(id);
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('KYC Admin') or #oauth2.hasScope('Customer Care Executive')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/kyc-admin/getallactive")
	public BaseDTO getActiveUserList() {
		return roleMasterService.getActiveUserListForKYCAdmin();
	}
	
	@PostMapping("/permissions/{roleId}")
	//@PreAuthorize(PrivilegeConstant.USER_PERMISSIONS)
	public BaseDTO assignPrivilegeToRole(@PathVariable("roleId") UUID roleId,@RequestBody(required = true) Set<String> privileges) {
		return roleMasterService.mappedPrivilegeToRole(roleId, privileges);
	}
	
	
	@GetMapping("/permissions/{roleId}")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	public BaseDTO getPrivilege(@PathVariable("roleId") UUID roleId) {
		return roleMasterService.getPrivilegeOfRole(roleId);
	}
	
	@GetMapping("/not-in-list")
	public BaseDTO getAllRolesNotInUserRoleList(@RequestParam("userRolesList") List<String> rolesList) {
		return roleMasterService.getAllRolesNotInUserRoleList(rolesList);
	}
	
	@GetMapping("/in-list")
	public BaseDTO getAllRolesInUserRoleList(@RequestParam("userRolesList") List<String> rolesList) {
		return roleMasterService.getAllRolesInUserRoleList(rolesList);
	}
	
	@GetMapping("/byId/in-list")
	public BaseDTO getAllRolesById(@RequestParam("roleIds") List<UUID> roleIds) {
		return roleMasterService.getAllRolesByIds(roleIds);
		
	}
	

}
