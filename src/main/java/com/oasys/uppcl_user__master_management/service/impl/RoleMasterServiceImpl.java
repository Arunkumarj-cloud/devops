package com.oasys.uppcl_user__master_management.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.oasys.cexception.NoRecoerdFoundException;
import com.oasys.clients.userservice.utils.CommonUtil;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.RoleMasterDao;
import com.oasys.uppcl_user__master_management.dto.PrivilegeChildDto;
import com.oasys.uppcl_user__master_management.dto.PrivilegeDto;
import com.oasys.uppcl_user__master_management.dto.RoleMasterDTO;
import com.oasys.uppcl_user__master_management.dto.RoleMasterResponseDto;
import com.oasys.uppcl_user__master_management.entity.PrivilegeEntity;
import com.oasys.uppcl_user__master_management.entity.RoleMasterEntity;
import com.oasys.uppcl_user__master_management.entity.RolePrivilegeEntity;
import com.oasys.uppcl_user__master_management.entity.RolePrivilegePK;
import com.oasys.uppcl_user__master_management.repository.JPARolePrivilegeRepository;
import com.oasys.uppcl_user__master_management.repository.RoleMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.response.ResponseMessageConstant;
import com.oasys.uppcl_user__master_management.service.RoleMasterService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class RoleMasterServiceImpl implements RoleMasterService {

	@Autowired
	RoleMasterDao roleMasterDao;
	
	@Autowired
	RoleMasterRepository roleMasterRepository;

	@Autowired
	MessageSource messageSource;

	@Autowired
	private JPARolePrivilegeRepository jpaRolePrivilegeRepository;
	
	@Value("${default.super.role}")
	private String defaultAdminRoleName;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Override
	public BaseDTO create(RoleMasterDTO roleMaster) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started RoleMasterServiceImpl.create()===>");
		try {
			response = roleMasterDao.create(roleMaster);
		}catch(DataIntegrityViolationException e) {
			log.error(" Exception RoleMasterServiceImpl.create() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( msg);
		}catch (Exception e) {
			log.error(" Exception RoleMasterServiceImpl.create() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( msg);
		}
		return response;
	}

	@Override
	public BaseDTO getAll() {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started RoleMasterServiceImpl.getAll()===>");
		try {
			response = roleMasterDao.getAll();
		} catch (Exception e) {
			log.error(" Exception RoleMasterServiceImpl.getAll() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( msg);
		}
		return response;
	}
	
	@Override
	public BaseDTO getAllRole(){
		BaseDTO response = new BaseDTO();
		try {
			response=roleMasterDao.getAllRole();
		}catch(Exception e) {
			log.error(" Exception RoleMasterServiceImpl.getAllRole() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( msg);
		}
		return response;
	}

	@Override
	public BaseDTO update(UUID id, RoleMasterDTO roleDTO) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started RoleMasterServiceImpl.update() ===>");
		try {
			response = roleMasterDao.update(id, roleDTO);
		}catch(DataIntegrityViolationException e) {
			log.error(" Exception RoleMasterServiceImpl.update()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( msg);
		}catch (Exception e) {
			log.error(" Exception RoleMasterServiceImpl.update()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( msg);
		}
		//log.info("<=== Ended RoleMasterServiceImpl.update() ===>");
		return response;
	}
	
	@Override
	public BaseDTO getById(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started RoleMasterServiceImpl.getById()===>");
		try {
			response = roleMasterDao.getById(id);
		} catch (Exception e) {
			log.error(" Exception RoleMasterServiceImpl.getById() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended RoleMasterServiceImpl.getById() ===>");
		return response;
	}

	@Override
	public BaseDTO delete(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started RoleMasterServiceImpl.delete() ===>");
		try {
			response = roleMasterDao.delete(id);
		} catch (Exception e) {
			log.error(" Exception RoleMasterServiceImpl.delete()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended RoleMasterServiceImpl.delete() ===>");
		return response;
	}
	
	@Override
	public BaseDTO getAllActive() {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started RoleMasterServiceImpl.getAllActive()===>");
		try {
			response = roleMasterDao.getAllActive();
		} catch (Exception e) {
			log.error(" Exception RoleMasterServiceImpl.getAllActive() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended RoleMasterServiceImpl.getAllActive() ===>");
		return response;
	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO pageData) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started RoleMasterServiceImpl.getAllLazy()===>");
		try {
			response = roleMasterDao.getLazyList(pageData);
		} catch (Exception e) {
			log.error(" Exception RoleMasterServiceImpl.getAllLazy() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended RoleMasterServiceImpl.getAllLazy() ===>");
		return response;
	}

	@Override
	public BaseDTO getByName(String name) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started RoleMasterServiceImpl.getByName()===>");
		try {
			response = roleMasterDao.getByName(name);
		} catch (Exception e) {
			log.error(" Exception RoleMasterServiceImpl.getByName() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended RoleMasterServiceImpl.getByName() ===>");
		return response;
	}

	@Override
	public BaseDTO softDelete(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started RoleMasterServiceImpl.softDelete()===>");
		try {
			RoleMasterEntity roleMasterEntity = roleMasterRepository.getOne(id);
			if (roleMasterEntity != null) {
				if (roleMasterEntity.getStatus() == true) {
					roleMasterEntity.setStatus(false);
					roleMasterEntity = roleMasterRepository.save(roleMasterEntity);
					response.setMessage("Successfully Deleted");
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Deleted");
				} else {
					response.setMessage("Unable to Delete Role Master Details..");
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					//log.warn("Unable to Delete Role Master Details..");
				}
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- RoleMasterServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Terms and Conditions Master Details");
		}
		//log.info("<=== Ended RoleMasterServiceImpl.softDelete() ===>");
		return response;
	}

	
	@Override
	public BaseDTO getByUserTypeId(UUID id) {
		//log.info(" <----- RoleMasterServiceImpl.getByUserTypeId() Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = roleMasterDao.getByUserTypeId(id);
		} catch (Exception e) {
			log.error("<---- DistrictMasterServiceImpl.getById() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//baseDTO.setMessage("Unable to Get District Master");
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- RoleMasterServiceImpl.getByUserTypeId() Service END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO getAllRoleList(){
		BaseDTO response = new BaseDTO();
		try {
			response=roleMasterDao.getAllRoleList();
		}catch(Exception e) {
			log.error(" Exception RoleMasterServiceImpl.getAllRoleList() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( msg);
		}
		return response;
	}
	
	@Override
	public BaseDTO getRoleList(UUID id) {
		BaseDTO response = new BaseDTO();
		try {
			response = roleMasterDao.getRoleList(id);
		} catch (Exception e) {
			log.error(" Exception RoleMasterServiceImpl.getRoleList() " + e);
		}
		return response;
	}

	@Override
	public BaseDTO getRoleCreator() {
		BaseDTO response = new BaseDTO();
		try {
			response = roleMasterDao.getRoleCreator();
		} catch (Exception e) {
			log.error(" Exception RoleMasterServiceImpl.getRoleCreator() " + e);
		}
		return response;
	}

	@Override
	public BaseDTO getRoleCreation(UUID id) {
		BaseDTO response = new BaseDTO();
		try {
			response = roleMasterDao.getRoleCreation(id);
		} catch (Exception e) {
			log.error(" Exception RoleMasterServiceImpl.getRoleCreation() " + e);
		}
		return response;
	}
	
	@Override
	public BaseDTO getActiveUserListForKYCAdmin() {
		BaseDTO response = new BaseDTO();
		try {
			response = roleMasterDao.getActiveUserListForKYCAdmin();
		} catch (Exception e) {
			log.error(" Exception RoleMasterServiceImpl.getRoleCreation() " + e);
		}
		return response;
	}
	
	@Override
	@Transactional
	public BaseDTO mappedPrivilegeToRole(final UUID roleId, final Set<String> privilege) {

		Optional<RoleMasterEntity> rolOptional = roleMasterRepository.findById(roleId);

		if (!rolOptional.isPresent()) {
			throw new NoRecoerdFoundException("No role found for id:"+roleId);
		}
		/*
		 * if (rolOptional.get().getRoleName().equals(defaultAdminRoleName)) { throw new
		 * AccessDeniedException("Access denied to update default role"); }
		 */
		if(rolOptional.get().getRoleName().equals(defaultAdminRoleName))
		{
			privilege.add("USER_PERMISSIONS");
		}
		jpaRolePrivilegeRepository.deletePrivilegesOfRole(roleId);
		saveRolePrivilegeEntity(rolOptional.get(),
				privilege.stream().map(name -> new PrivilegeEntity(name)).collect(Collectors.toList()));
		return new BaseDTO(ResponseMessageConstant.SUCCESS_RESPONSE);
	}
	
	
	@Override
	@Transactional
	public BaseDTO mappedPrivilegeToDefaultRole(final PrivilegeDto privilegeDto) {

		Optional<RoleMasterEntity> rolOptional= roleMasterRepository.findByRoleName(defaultAdminRoleName);
		
		if(!rolOptional.isPresent())
		{
			throw new NoRecoerdFoundException("No default role found with name:"+defaultAdminRoleName);
		}
		List<PrivilegeEntity> privilegeEntitys = new LinkedList<>();
		if (privilegeDto.isDefaultAssignToSuper()) {
			privilegeEntitys.add(new PrivilegeEntity(privilegeDto.getPrivilegeName()));
			privilegeEntitys.addAll(privilegeDto.getChilds().stream()
					.map(obj -> new PrivilegeEntity(obj.getChildPrivilegeName())).collect(Collectors.toList()));
		} else {
			privilegeEntitys.addAll(privilegeDto.getChilds().stream().filter(PrivilegeChildDto::isChildDefaultAssignToAdmin)
					.map(obj -> new PrivilegeEntity(obj.getChildPrivilegeName())).collect(Collectors.toList()));

			if (privilegeEntitys.size() > 0) {
				privilegeEntitys.add(new PrivilegeEntity(privilegeDto.getPrivilegeName()));
			}
		}
		
		saveRolePrivilegeEntity(rolOptional.get(), privilegeEntitys);
		return new BaseDTO(ResponseMessageConstant.SUCCESS_RESPONSE);
	}
	
	private void saveRolePrivilegeEntity(RoleMasterEntity roles,List<PrivilegeEntity> privilege )
	{
		List<RolePrivilegeEntity> rolePrivilegeMappings = privilege.stream().map(obj -> {
			RolePrivilegePK rolePrivilegePK = new RolePrivilegePK();
			rolePrivilegePK.setRoleId(roles.getId());
			rolePrivilegePK.setPrivilegeName(obj.getPrivilegeName());
			RolePrivilegeEntity rolePrivilegeEntity = new RolePrivilegeEntity();
			rolePrivilegeEntity.setRolePrivilegePK(rolePrivilegePK);
			RoleMasterEntity role = new RoleMasterEntity();
			role.setId(roles.getId());
			rolePrivilegeEntity.setRole(role);

			rolePrivilegeEntity.setPrivilege(obj);

			rolePrivilegeEntity.setRole(role);
			return rolePrivilegeEntity;
		}).collect(Collectors.toList());

		jpaRolePrivilegeRepository.saveAll(rolePrivilegeMappings);
	}
	
	
	@Override
	public BaseDTO getPrivilegeOfRole(final UUID roleId) 
	{
		Optional<RoleMasterEntity> rolOptional = roleMasterRepository.findById(roleId);

		if (!rolOptional.isPresent()) {
			throw new NoRecoerdFoundException("No role found for id:"+roleId);
		}
		
		RoleMasterResponseDto roleMasterResponseDto= commonUtil.modalMap(rolOptional.get(), RoleMasterResponseDto.class);
		roleMasterResponseDto.setPermissions(jpaRolePrivilegeRepository.getPrivilegesOfRole(roleId));
		BaseDTO commonResponse=new BaseDTO(ResponseMessageConstant.SUCCESS_RESPONSE);
		commonResponse.setResponseContent(roleMasterResponseDto);
		return commonResponse;	
	}
	
	@Override
	public BaseDTO getAllRolesInUserRoleList(List<String> userRoleNames) {
		BaseDTO response = new BaseDTO();

		List<RoleMasterEntity> roleEntityList = roleMasterRepository.getAllRolesInUserRoleList(userRoleNames);
		if (Objects.nonNull(roleEntityList) && !roleEntityList.isEmpty()) {
			response.setResponseContents(roleEntityList);
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
		} else {
			response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			response.setMessage("No Record Found");
		}
		return response;
	}
	
	@Override
	public BaseDTO getAllRolesNotInUserRoleList(List<String> userRoleNames) {
		BaseDTO response = new BaseDTO();

		List<RoleMasterEntity> roleEntityList = roleMasterRepository.getAllRolesNotInUserRoleList(userRoleNames);
		if (Objects.nonNull(roleEntityList) && !roleEntityList.isEmpty()) {
			response.setResponseContents(roleEntityList);
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
		} else {
			response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			response.setMessage("No Record Found");
		}
		return response;
	}
	
	@Override
	public BaseDTO getAllRolesByIds(List<UUID> roleIds) {
		BaseDTO response = new BaseDTO();

		List<RoleMasterEntity> roleEntityList = roleMasterRepository.getAllRolesByIds(roleIds);
		if (Objects.nonNull(roleEntityList) && !roleEntityList.isEmpty()) {
			response.setResponseContents(roleEntityList);
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
		} else {
			response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			response.setMessage("No Record Found");
		}
		return response;
		
	}
	@Override
	@Transactional
	public BaseDTO mappedAddPrivilegeToDefaultRole(String privilegeName, PrivilegeEntity privilegeEntity,
			List<PrivilegeChildDto> privilegeDtos) {

		Optional<RoleMasterEntity> rolOptional = roleMasterRepository.findByRoleName(defaultAdminRoleName);

		if (!rolOptional.isPresent()) {
			throw new NoRecoerdFoundException("No default role found with name:" + defaultAdminRoleName);
		}

		Optional<RolePrivilegeEntity> obj = jpaRolePrivilegeRepository
				.findByRoleAndPermission(rolOptional.get().getId(), privilegeName);
		List<PrivilegeEntity> privilegeEntitys = new LinkedList<>();
		if (obj.isPresent()) {
			privilegeEntitys.add(new PrivilegeEntity(privilegeEntity.getPrivilegeName()));
			privilegeEntitys.addAll(privilegeDtos.stream().filter(PrivilegeChildDto::isChildDefaultAssignToAdmin)
					.map(dto -> new PrivilegeEntity(dto.getChildPrivilegeName())).collect(Collectors.toList()));

		} else {
			privilegeEntitys.addAll(privilegeDtos.stream().filter(PrivilegeChildDto::isChildDefaultAssignToAdmin)
					.map(dto -> new PrivilegeEntity(dto.getChildPrivilegeName())).collect(Collectors.toList()));

			if (privilegeEntitys.size() > 0) {
				privilegeEntitys.add(new PrivilegeEntity(privilegeName));
			}

		}
		saveRolePrivilegeEntity(rolOptional.get(), privilegeEntitys);
		return new BaseDTO(ResponseMessageConstant.SUCCESS_RESPONSE);

	}
}

