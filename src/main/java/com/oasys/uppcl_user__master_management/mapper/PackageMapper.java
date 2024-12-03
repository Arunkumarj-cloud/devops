package com.oasys.uppcl_user__master_management.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oasys.clients.userservice.utils.CommonUtil;
import com.oasys.uppcl_user__master_management.dto.PackageResponseDTO;
import com.oasys.uppcl_user__master_management.dto.ServiceCategoryResponseDTO;
import com.oasys.uppcl_user__master_management.entity.PackageEntity;
import com.oasys.uppcl_user__master_management.repository.ServiceCategoryFeeMappingRepository;

@Component
public class PackageMapper {

	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private ServiceCategoryFeeMappingRepository serviceFeeMappingRepository;

	public PackageResponseDTO convertEntityToResponseDTO(PackageEntity entity) {
		PackageResponseDTO responseDTO = commonUtil.modalMap(entity, PackageResponseDTO.class);
		if (CollectionUtils.isNotEmpty(entity.getServceCategoryList())) {
			List<ServiceCategoryResponseDTO> serviceCategoryList = new ArrayList<>();
			entity.getServceCategoryList().forEach(serviceCategory -> {
				ServiceCategoryResponseDTO serviceCategoryResponseDTO = new ServiceCategoryResponseDTO();
				serviceCategoryResponseDTO.setId(serviceCategory.getId());
				serviceCategoryResponseDTO.setCategoryName(serviceCategory.getName());
				serviceCategoryResponseDTO
						.setAmount(serviceFeeMappingRepository.findAmountByServiceCategoryId(serviceCategory.getId()));
				serviceCategoryResponseDTO.setStatus(serviceCategory.getStatus());
				serviceCategoryList.add(serviceCategoryResponseDTO);
			});
			responseDTO.setServiceCategoryList(serviceCategoryList);
		}
		return responseDTO;
		}

}
