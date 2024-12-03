package com.oasys.uppcl_user__master_management.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oasys.config.BaseDTO;
import com.oasys.uppcl_user__master_management.dto.PrivilegeChildDto;
import com.oasys.uppcl_user__master_management.dto.PrivilegeDto;
import com.oasys.uppcl_user__master_management.service.PrivilegeService;

@RestController
@RequestMapping("/permissions/v1/")
public class PrivilegeController {

	@Autowired
	private PrivilegeService privilegeService;

	@GetMapping("/all")
//	@PreAuthorize(PrivilegeConstant.USER_PERMISSIONS)
	public BaseDTO getAllPermission() {
		return privilegeService.viewAllPrivilege();
	}
	
	@GetMapping("/all/role/{roleId}")
//	@PreAuthorize(PrivilegeConstant.USER_PERMISSIONS)
	public BaseDTO get(@PathVariable("roleId") UUID roleId) {
		return privilegeService.getAllAndAssignPrivilage(roleId);
	}

	@PostMapping("/priv")
//	@PreAuthorize(PrivilegeConstant.USER_PERMISSIONS)
	public BaseDTO addPermission(@Valid @RequestBody PrivilegeDto permissions) {
		return privilegeService.addPrivilege(permissions);
	}

	@PutMapping("permission/{permissionName}")
//	@PreAuthorize(PrivilegeConstant.USER_PERMISSIONS)
	public BaseDTO addChilds(@PathVariable("permissionName") String permissionName,
			 @NotEmpty(message = "103") @RequestBody List<@Valid PrivilegeChildDto> permissions) {
		return privilegeService.addChilds(permissionName, permissions);
	}

	@DeleteMapping("/{permissionName}")
//	@PreAuthorize(PrivilegeConstant.USER_PERMISSIONS)
	public BaseDTO deletePrivilege(@PathVariable("permissionName") String permissionName) {
		return privilegeService.deletePrivilege(permissionName);
	}
	
	
	@PostMapping("/Add/Privilage")
//	@PreAuthorize(PrivilegeConstant.USER_PERMISSIONS)
	public BaseDTO addnewPermission(@Valid @RequestBody PrivilegeDto permissions) {
		return privilegeService.addNewPrivilege(permissions);
	}

}