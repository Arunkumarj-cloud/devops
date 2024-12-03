package com.oasys.uppcl_user__master_management.dto;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.oasys.uppcl_user__master_management.dto.FaqDTO;

import lombok.Data;
@Data
@Service
public class FaqRequestDTO {
	List<FaqDTO> list ;

	List<UUID> listOfIds;
}
