package com.oasys.uppcl_user__master_management.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.oasys.cexception.NoRecoerdFoundException;
import com.oasys.config.BaseDTO;
import com.oasys.config.Constants;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.constant.PrivilegeConstant;
import com.oasys.feign.client.WorkflowManagementFeign;
import com.oasys.feign.dto.WorkflowBaseDTO;
import com.oasys.uppcl_user__master_management.dto.ProjectTypeRequestDTO;
import com.oasys.uppcl_user__master_management.dto.ProjectTypeResponseDTO;
import com.oasys.uppcl_user__master_management.dto.RoleResponse;
import com.oasys.uppcl_user__master_management.dto.WorkflowProjectDetailsDTO;
import com.oasys.uppcl_user__master_management.entity.MerchantTypeMasterEntity;
import com.oasys.uppcl_user__master_management.entity.ProjectTypeEntity;
import com.oasys.uppcl_user__master_management.entity.RoleMasterEntity;
import com.oasys.uppcl_user__master_management.mapper.ProjectTypeMapper;
import com.oasys.uppcl_user__master_management.mapper.RoleResponseMapper;
import com.oasys.uppcl_user__master_management.repository.MerchantTypeMasterRepository;
import com.oasys.uppcl_user__master_management.repository.ProjectTypeRepository;
import com.oasys.uppcl_user__master_management.repository.ProjectTypeRepositoryImpl;
import com.oasys.uppcl_user__master_management.repository.RoleMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseMessageConstant;
import com.oasys.uppcl_user__master_management.service.ProjectTypeService;
import com.oasys.uppcl_user__master_management.validation.ProjectTypeValidator;

import lombok.extern.log4j.Log4j2;
@Component
@Service
@Log4j2
public class ProjectTypeServiceImpl implements ProjectTypeService {

	@Autowired(required=true)
	private ProjectTypeValidator validator;

	@Autowired(required=true)
	private ProjectTypeRepository projectTypeRepository;

//	@Autowired
//	private Oauth2UserDetails oauth2UserDetails;

	@Autowired(required=true)
	private ProjectTypeMapper mapper;

	@Autowired(required=true)
	private RoleResponseMapper responseMapper;

	@Autowired(required=true)
	private ProjectTypeRepositoryImpl projectTypeRepositoryImpl;

	@Autowired(required=true)
	private RoleMasterRepository roleMasterRepository;

	@Autowired(required=true)
	private MerchantTypeMasterRepository merchantTypeRepository;

	@Autowired(required=true)
	private WorkflowManagementFeign workflowManagementFeign;

	@Override
	@Transactional
	public BaseDTO add(ProjectTypeRequestDTO requestDTO) {
		WorkflowBaseDTO workflowBaseDTO = null;
		List<String> rolesList = new ArrayList<>();
		rolesList.add(PrivilegeConstant.SO_ROLE);
		rolesList.add(PrivilegeConstant.CO_ADMIN_ROLE);
		rolesList.add(PrivilegeConstant.FO_ADMIN_ROLE);
		BaseDTO responseDTO = validator.isRequestValid(requestDTO, Boolean.FALSE);
		if (ResponseMessageConstant.SUCCESS_RESPONSE.getErrorCode() != responseDTO.getStatusCode()) {
			return responseDTO;
		}
		responseDTO = new BaseDTO();
		Optional<ProjectTypeEntity> existingEntityOptional = projectTypeRepository
				.findByNameIgnoreCase(requestDTO.getName().trim().toUpperCase());
		if (existingEntityOptional.isPresent()) {
			log.error("invalid request parameter : {}", requestDTO.getName());
			responseDTO.setStatusCode(ResponseMessageConstant.ALREADY_EXIST.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.ALREADY_EXIST
					.getMessage(new Object[] { Constants.PROJECT_TYPE, requestDTO.getName() }));
			return responseDTO;

		}
		ProjectTypeEntity entity = new ProjectTypeEntity();
		entity.setName(requestDTO.getName().trim());
		entity.setStatus(requestDTO.getStatus());
//		entity.setCreatedBy(oauth2UserDetails.getId());
		entity.setRemarks(requestDTO.getRemarks());
		projectTypeRepository.save(entity);
		WorkflowProjectDetailsDTO workflowProjectDetailsDTO = new WorkflowProjectDetailsDTO();
		workflowProjectDetailsDTO.setApplicationId(entity.getId());
		workflowProjectDetailsDTO.setWorkflowName(entity.getName());
		workflowProjectDetailsDTO.setIsActive(entity.getStatus());
		List<RoleMasterEntity> roleEntityList = roleMasterRepository.getAllRolesInUserRoleList(rolesList);
		if (CollectionUtils.isEmpty(roleEntityList)) {
			responseDTO.setStatusCode(ResponseMessageConstant.NO_RECOERD_FOUND.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.NO_RECOERD_FOUND.getMessage());
			return responseDTO;
		}
		List<RoleResponse> responseList = roleEntityList.stream().map(responseMapper::convertEntityToResponseDTO)
				.collect(Collectors.toList());
		workflowProjectDetailsDTO.setRoleResponses(responseList);
		try {
			workflowBaseDTO = workflowManagementFeign.updateWorkFlowDetails(workflowProjectDetailsDTO);
		} catch (Exception e) {
			log.error("error occurred while calling /workflowDetails/create-project Api :: {}", e);
			responseDTO.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			responseDTO.setMessage("Workflow Service Unavailable!");
			return responseDTO;
		}

		responseDTO.setResponseContent(mapper.convertEntityToResponseDTO(entity));
		responseDTO.setStatusCode(ResponseMessageConstant.SUCCESS_RESPONSE.getErrorCode());
		responseDTO.setMessage(ResponseMessageConstant.CREATE_SUCCESS_RESPONSE.getMessage());
		return responseDTO;
	}

	@Override
	@Transactional
	public BaseDTO update(ProjectTypeRequestDTO requestDTO) {
		BaseDTO responseDTO = validator.isRequestValid(requestDTO, Boolean.TRUE);
		if (ResponseMessageConstant.SUCCESS_RESPONSE.getErrorCode() != responseDTO.getStatusCode()) {
			return responseDTO;
		}
		responseDTO = new BaseDTO();
		Optional<ProjectTypeEntity> existingEntityOptional = projectTypeRepository
				.findByNameNotInId(requestDTO.getName().trim().toUpperCase(), requestDTO.getId());
		if (existingEntityOptional.isPresent()) {
			log.error("invalid request parameter : {}", requestDTO.getName());
			responseDTO.setStatusCode(ResponseMessageConstant.ALREADY_EXIST.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.ALREADY_EXIST
					.getMessage(new Object[] { Constants.PROJECT_TYPE, requestDTO.getName() }));
			return responseDTO;

		}
		existingEntityOptional = projectTypeRepository.findById(requestDTO.getId());
		if (!existingEntityOptional.isPresent()) {
			log.error("invalid request parameter : {}", requestDTO.getId());
			responseDTO.setStatusCode(ResponseMessageConstant.INVALID_REQUEST_PARAMETER.getErrorCode());
			responseDTO.setMessage(
					ResponseMessageConstant.INVALID_REQUEST_PARAMETER.getMessage(new Object[] { Constants.ID }));
			return responseDTO;
		}
		ProjectTypeEntity entity = existingEntityOptional.get();
		entity.setName(requestDTO.getName().trim());
		entity.setStatus(requestDTO.getStatus());
	//	entity.setModifiedBy(oauth2UserDetails.getId());
		if(StringUtils.isNotBlank(requestDTO.getRemarks())) {
			entity.setRemarks(requestDTO.getRemarks());
		}
		projectTypeRepository.save(entity);
		responseDTO.setResponseContent(mapper.convertEntityToResponseDTO(entity));
		responseDTO.setStatusCode(ResponseMessageConstant.SUCCESS_RESPONSE.getErrorCode());
		responseDTO.setMessage(ResponseMessageConstant.UPDAE_RESPONSE.getMessage());
		return responseDTO;
	}

	@Override
	public BaseDTO getById(UUID id) {
		BaseDTO responseDTO = new BaseDTO();
		Optional<ProjectTypeEntity> entity = projectTypeRepository.findById(id);
		if (!entity.isPresent()) {
			log.error("invalid request parameter : {}", id);
			responseDTO.setStatusCode(ResponseMessageConstant.NOT_FOUND.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.NOT_FOUND.getMessage(new Object[] { Constants.ID }));
			return responseDTO;
		}
		responseDTO.setStatusCode(ResponseMessageConstant.SUCCESS_RESPONSE.getErrorCode());
		responseDTO.setMessage(ResponseMessageConstant.SEARCH_RESPONSE.getMessage());
		responseDTO.setResponseContent(mapper.convertEntityToResponseDTO(entity.get()));
		return responseDTO;
	}

	@Override
	public BaseDTO getBySearchFilter(PaginationRequestDTO requestDTO) {
		log.info("GoldLoanPincodeServiceImpl getBySearchFilter() :: {}", requestDTO);
		BaseDTO baseDTO = new BaseDTO();
		Long count = projectTypeRepositoryImpl.getCountBySearchFields(requestDTO);
		log.info("total count :: {}", count);
		if (count > 0) {
			List<ProjectTypeEntity> projectTypeEntityList = projectTypeRepositoryImpl.getRecordsByFilterDTO(requestDTO);
			if (CollectionUtils.isEmpty(projectTypeEntityList)) {
				log.info("no data found corresponding to :: {}", requestDTO.toString());
				throw new NoRecoerdFoundException(ResponseMessageConstant.NO_RECOERD_FOUND.getMessage());
			}
			List<ProjectTypeResponseDTO> responseList = projectTypeEntityList.stream()
					.map(mapper::convertEntityToResponseDTO).collect(Collectors.toList());
			baseDTO.setResponseContent(responseList);
			baseDTO.setTotalRecords(Objects.nonNull(count) ? count.longValue() : null);
			baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			baseDTO.setMessage(ResponseMessageConstant.SEARCH_RESPONSE.getMessage());
			return baseDTO;
		} else {
			log.info("no data found corresponding to :: {}", requestDTO.toString());
			throw new NoRecoerdFoundException(ResponseMessageConstant.NO_RECOERD_FOUND.getMessage());
		}
	}

	public BaseDTO getDistributor(UUID roleId , UUID  userTypeId) {
		BaseDTO responseDTO = new BaseDTO();
		String agentType = Constants.BLANK;
		if (Objects.isNull(roleId)) {
			responseDTO.setStatusCode(ResponseMessageConstant.MANDATORY_PARAMETER_MISSING.getErrorCode());
			responseDTO.setMessage(
					ResponseMessageConstant.MANDATORY_PARAMETER_MISSING.getMessage(new Object[] { Constants.ROLE_ID }));
			return responseDTO;
		}
		Optional<RoleMasterEntity> roleMasterEntity = roleMasterRepository.findById(roleId);
		if (!roleMasterEntity.isPresent()) {
			responseDTO.setStatusCode(ResponseMessageConstant.INVALID_REQUEST_PARAMETER.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.INVALID_REQUEST_PARAMETER
					.getMessage(new Object[] { Constants.ROLE_ID }));
			return responseDTO;
		}
		if (Objects.nonNull(userTypeId)) {
			Optional<MerchantTypeMasterEntity> merchantType = merchantTypeRepository
					.findById(userTypeId);
			if (!merchantType.isPresent()) {
				agentType = merchantType.get().getAgentType();
			}
		}
		if (Constants.DISTRIBUTOR.equalsIgnoreCase(roleMasterEntity.get().getUserRoleName())
				|| Constants.SUPER_DISTRIBUTOR.equalsIgnoreCase(roleMasterEntity.get().getUserRoleName())) {
			if (StringUtils.isNotBlank(agentType) && !Constants.INDIVIDUAL.contentEquals(agentType)) {
				responseDTO.setStatusCode(ResponseMessageConstant.INVALID_REQUEST_PARAMETER.getErrorCode());
				responseDTO.setMessage(ResponseMessageConstant.INVALID_REQUEST_PARAMETER
						.getMessage(new Object[] { Constants.USER_TYPE_ID }));
				return responseDTO;
			}
			Optional<ProjectTypeEntity> projectTypeOptional = projectTypeRepository.getAdminApplicationdetails();
			if (!projectTypeOptional.isPresent()) {
				responseDTO.setStatusCode(ResponseMessageConstant.NOT_FOUND.getErrorCode());
				responseDTO.setMessage(ResponseMessageConstant.NOT_FOUND.getMessage());
				return responseDTO;
			}
			projectTypeOptional.get().setName(Constants.DISTRIBUTOR);
			responseDTO.setResponseContent(mapper.convertEntityToResponseDTO(projectTypeOptional.get()));
			responseDTO.setMessage("Distributor Data Fetched Sucessfully");
			responseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			return responseDTO;
		}else {
			responseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			responseDTO.setMessage("No Record Found");
		}
		return responseDTO;
	}
	
	@Override
	public BaseDTO getAllActive() {
		BaseDTO responseDTO = new BaseDTO();
		List<ProjectTypeEntity> projectTypeList =projectTypeRepository.getAllActive();
		if(CollectionUtils.isEmpty(projectTypeList)) {
			responseDTO.setStatusCode(ResponseMessageConstant.NOT_FOUND.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.NOT_FOUND.getMessage(new Object[] { Constants.ID }));
			return responseDTO;
		}
		responseDTO.setStatusCode(ResponseMessageConstant.SUCCESS_RESPONSE.getErrorCode());
		responseDTO.setMessage(ResponseMessageConstant.SEARCH_RESPONSE.getMessage());
		List<ProjectTypeResponseDTO> responseList = projectTypeList.stream()
				.map(mapper::convertEntityToResponseDTO).collect(Collectors.toList());
		responseDTO.setResponseContents(responseList);
		return responseDTO;
	}
	
	@Override
	public BaseDTO getByName(String name) {
		BaseDTO responseDTO = new BaseDTO();
		Optional<ProjectTypeEntity> entity = projectTypeRepository.findByNameIgnoreCase(name.toUpperCase());
		if (!entity.isPresent()) {
			log.error("invalid request parameter : {}", name);
			responseDTO.setStatusCode(ResponseMessageConstant.NOT_FOUND.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.NOT_FOUND.getMessage(new Object[] { Constants.ID }));
			return responseDTO;
		}
		responseDTO.setStatusCode(ResponseMessageConstant.SUCCESS_RESPONSE.getErrorCode());
		responseDTO.setMessage(ResponseMessageConstant.SEARCH_RESPONSE.getMessage());
		responseDTO.setResponseContent(mapper.convertEntityToResponseDTO(entity.get()));
		return responseDTO;
	}
	
	@Override
	public BaseDTO getAll() {
		BaseDTO responseDTO = new BaseDTO();
		List<ProjectTypeEntity> projectTypeList =projectTypeRepository.getAll();
		if(CollectionUtils.isEmpty(projectTypeList)) {
			responseDTO.setStatusCode(ResponseMessageConstant.NOT_FOUND.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.NOT_FOUND.getMessage(new Object[] { Constants.ID }));
			return responseDTO;
		}
		responseDTO.setStatusCode(ResponseMessageConstant.SUCCESS_RESPONSE.getErrorCode());
		responseDTO.setMessage(ResponseMessageConstant.SEARCH_RESPONSE.getMessage());
		List<ProjectTypeResponseDTO> responseList = projectTypeList.stream()
				.map(mapper::convertEntityToResponseDTO).collect(Collectors.toList());
		responseDTO.setResponseContents(responseList);
		return responseDTO;
	}

}
