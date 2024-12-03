package com.oasys.uppcl_user__master_management.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oasys.cexception.DublicateRequestlException;
import com.oasys.cexception.InvalidRequestException;
import com.oasys.cexception.NoRecoerdFoundException;
import com.oasys.config.BaseDTO;
import com.oasys.uppcl_user__master_management.dto.PrivilegeChildDto;
import com.oasys.uppcl_user__master_management.dto.PrivilegeDto;
import com.oasys.uppcl_user__master_management.dto.PrivilegeResponseDto;
import com.oasys.uppcl_user__master_management.entity.PrivilegeEntity;
import com.oasys.uppcl_user__master_management.entity.PrivilegenameEntity;
import com.oasys.uppcl_user__master_management.entity.RoleMasterEntity;
import com.oasys.uppcl_user__master_management.repository.JPAPrivilegeRepository;
import com.oasys.uppcl_user__master_management.repository.JPARolePrivilegeRepository;
import com.oasys.uppcl_user__master_management.repository.PrivilageRepository;
import com.oasys.uppcl_user__master_management.repository.RoleMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseMessageConstant;
import com.oasys.uppcl_user__master_management.service.PrivilegeService;
import com.oasys.uppcl_user__master_management.service.RoleMasterService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class PrivilegeServiceImpl implements PrivilegeService {

	@Autowired
	JPAPrivilegeRepository jpaPrivilegeRepository;
	@Autowired
	PrivilageRepository privilageRepository;

	@Autowired
	JPARolePrivilegeRepository jpaRolePrivilegeRepository;

	@Autowired
	private RoleMasterService roleMasterService;
	
	@Autowired
	private RoleMasterRepository roleMasterRepository;

	@Override
	@Transactional
	public BaseDTO addPrivilege(PrivilegeDto privilegeDto) {

		Optional<PrivilegeEntity>  optional = jpaPrivilegeRepository.findById(privilegeDto.getPrivilegeName());
		
		if(optional.isPresent())
		{
			throw new DublicateRequestlException("This Permission Already Exist");
		}
		PrivilegeEntity groupEntity = new PrivilegeEntity(privilegeDto.getPrivilegeName().trim(),privilegeDto.getDisplayName().trim());
		groupEntity.setPrivilegeName(privilegeDto.getPrivilegeName());
		groupEntity.setChildren(
				privilegeDto.getChilds().stream().map(childDto -> { 
				
					PrivilegeEntity chilEntity = new PrivilegeEntity(childDto.getChildPrivilegeName(),childDto.getChildDisplayName());
					chilEntity.setParent(groupEntity);
					return chilEntity;
					
				}).collect(Collectors.toSet()));

		jpaPrivilegeRepository.save(groupEntity);
		roleMasterService.mappedPrivilegeToDefaultRole(privilegeDto);
		return new BaseDTO(ResponseMessageConstant.SUCCESS_RESPONSE);
	}
	
	
	@Override
	@Transactional
	public BaseDTO addChilds(String privilegeName, List<PrivilegeChildDto> privilegeDtos) {

		Optional<PrivilegeEntity>  optional = jpaPrivilegeRepository.
				findById(privilegeName);
		
		if(!optional.isPresent())
		{
			throw new NoRecoerdFoundException("Permission :- "+privilegeName+" not found in database");
		}
		
		optional.get().setChildren(
				privilegeDtos.stream().map(childDto -> { 
				
					PrivilegeEntity chilEntity = new PrivilegeEntity(childDto.getChildPrivilegeName(),childDto.getChildDisplayName());
					chilEntity.setParent(optional.get());
					return chilEntity;
					
				}).collect(Collectors.toSet()));

		jpaPrivilegeRepository.save(optional.get());
		roleMasterService.mappedAddPrivilegeToDefaultRole(privilegeName ,optional.get() ,privilegeDtos);

		return new BaseDTO(ResponseMessageConstant.SUCCESS_RESPONSE);
	}

	@Override
	public BaseDTO deletePrivilege(String privileges) {
		BaseDTO response =new BaseDTO();

		Long count = jpaRolePrivilegeRepository.findPrivilagesCount(privileges);
		if (count > 0) {
			Optional<PrivilegeEntity> privilegeEntity = jpaPrivilegeRepository.findById(privileges);
			if (privilegeEntity.isPresent()) {
				jpaPrivilegeRepository.delete(privilegeEntity.get());
				response.setResponseContent(ResponseMessageConstant.SUCCESS_RESPONSE);
		}else {
			throw new InvalidRequestException(ResponseMessageConstant.DELETE_PRIVIELGE_ERROR, privileges);

		}
		
		}
		return response; 
	}

	@Override
	public BaseDTO viewAllPrivilege() {

		BaseDTO baseDTO = new BaseDTO(ResponseMessageConstant.SUCCESS_RESPONSE);
		List<PrivilegeEntity> listPrivilegeEntities = jpaPrivilegeRepository.findAllByOrderByCreatedDateAsc();
		baseDTO.setResponseContent(getPrivilegeResponseDto(listPrivilegeEntities,Collections.emptyList()));
		return baseDTO;
	}

	private List<PrivilegeResponseDto> getPrivilegeResponseDto(List<PrivilegeEntity> listPrivilegeEntities, List<String> assignRoles) {
		List<PrivilegeResponseDto> resp = new LinkedList<>();
		Map<String, PrivilegeResponseDto> map = new HashMap<>();
		List<PrivilegeEntity> unprocessedEntities = new LinkedList<>();
		
		for(PrivilegeEntity obj:listPrivilegeEntities) {
			PrivilegeResponseDto privilegeResponseDto = new PrivilegeResponseDto(obj.getPrivilegeName(),
					obj.getDisplayName());
			privilegeResponseDto.setAssign(assignRoles.contains(obj.getPrivilegeName()));
			map.put(obj.getPrivilegeName(), privilegeResponseDto);

			if (obj.getParent() == null) {
				resp.add(privilegeResponseDto);
			} else {
				if(map.containsKey(obj.getParent().getPrivilegeName())) {
					map.get(obj.getParent().getPrivilegeName()).getChilds().add(privilegeResponseDto);
				}else {
					unprocessedEntities.add(obj);
				}
				

			}
			
		}
//		listPrivilegeEntities.forEach(obj -> {
//
//			PrivilegeResponseDto privilegeResponseDto = new PrivilegeResponseDto(obj.getPrivilegeName(),
//					obj.getDisplayName());
//			privilegeResponseDto.setAssign(assignRoles.contains(obj.getPrivilegeName()));
//			map.put(obj.getPrivilegeName(), privilegeResponseDto);
//
//			if (obj.getParent() == null) {
//				resp.add(privilegeResponseDto);
//			} else {
//				if(map.containsKey(obj.getParent().getPrivilegeName())) {
//					map.get(obj.getParent().getPrivilegeName()).getChilds().add(privilegeResponseDto);
//				}else {
//					unprocessedEntities.add(obj);
//				}
//				
//
//			}
//
//		});
		unprocessedEntities.forEach(entity->{
			  if(map.containsKey(entity.getParent().getPrivilegeName())) {
				 
					map.get(entity.getParent().getPrivilegeName()).getChilds().add(map.get(entity.getPrivilegeName()));
					
			  }
			 
		});
		
		return resp;

	}


	@Override
	public BaseDTO getAllAndAssignPrivilage(UUID roleId) {
		BaseDTO baseDTO = new BaseDTO(ResponseMessageConstant.SUCCESS_RESPONSE);
		Optional<RoleMasterEntity> rolOptional = roleMasterRepository.findById(roleId);

		if (!rolOptional.isPresent()) {
			throw new NoRecoerdFoundException("No role found for id:"+roleId);
		}
	   List<String> permissons = jpaRolePrivilegeRepository.getPrivilegesOfRole(roleId);
	   List<PrivilegeEntity> listPrivilegeEntities = jpaPrivilegeRepository.findAllByOrderByCreatedDateAsc();
	   baseDTO.setResponseContent(getPrivilegeResponseDto(listPrivilegeEntities,permissons));
	   return baseDTO;
	}
	
	@Override
	@Transactional
	public BaseDTO addNewPrivilege(PrivilegeDto privilegeDto) {

		Optional<PrivilegeEntity>  optional = jpaPrivilegeRepository.findById(privilegeDto.getPrivilegeName());
		
		if(optional.isPresent())
		{
			throw new DublicateRequestlException("This Permission Already Exist");
		}
		if(privilegeDto.getParentName()!=null) {
		Optional<PrivilegeEntity>  parentname = jpaPrivilegeRepository.findById(privilegeDto.getParentName());
if(parentname.isPresent()) {
		
	PrivilegenameEntity groupEntity = new PrivilegenameEntity(privilegeDto.getPrivilegeName().trim(),privilegeDto.getDisplayName().trim());
		groupEntity.setPrivilegeName(privilegeDto.getPrivilegeName());
		if(privilegeDto.getParentName()!=null) {
		groupEntity.setParent(privilegeDto.getParentName());
}
//		groupEntity.setChildren(
//				privilegeDto.getChilds().stream().map(childDto -> { 
//				
//					PrivilegeEntity chilEntity = new PrivilegeEntity(childDto.getPrivilegeName(),childDto.getDisplayName());
//					chilEntity.setParent(groupEntity);
//					return chilEntity;
//					
//				}).collect(Collectors.toSet()));

		privilageRepository.save(groupEntity);
//		roleMasterService.mappedPrivilegeToDefaultRole(privilegeDto);
}else {
	throw new DublicateRequestlException("Parent Permission Not Avilable");

}
		}else {

	
	PrivilegenameEntity groupEntity = new PrivilegenameEntity(privilegeDto.getPrivilegeName().trim(),privilegeDto.getDisplayName().trim());
		groupEntity.setPrivilegeName(privilegeDto.getPrivilegeName());
		if(privilegeDto.getParentName()!=null) {
		groupEntity.setParent(privilegeDto.getParentName());
}
//		groupEntity.setChildren(
//				privilegeDto.getChilds().stream().map(childDto -> { 
//				
//					PrivilegeEntity chilEntity = new PrivilegeEntity(childDto.getPrivilegeName(),childDto.getDisplayName());
//					chilEntity.setParent(groupEntity);
//					return chilEntity;
//					
//				}).collect(Collectors.toSet()));

		privilageRepository.save(groupEntity);
//		roleMasterService.mappedPrivilegeToDefaultRole(privilegeDto);

}
		return new BaseDTO(ResponseMessageConstant.SUCCESS_RESPONSE);
	}

}
