package com.oasys.uppcl_user__master_management.dao.impl;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.BlockDao;
import com.oasys.uppcl_user__master_management.dto.BlockDTO;
import com.oasys.uppcl_user__master_management.entity.BlockMasterEntity;
import com.oasys.uppcl_user__master_management.entity.DistrictMasterEntity;
import com.oasys.uppcl_user__master_management.entity.ReportQueriesEntity;
import com.oasys.uppcl_user__master_management.repository.BlockRepository;
import com.oasys.uppcl_user__master_management.repository.DistrictMasterRepository;
import com.oasys.uppcl_user__master_management.repository.ReportQueriesRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class BlockDaoImpl implements BlockDao {
	@Autowired
	BlockRepository blockRepository;

	@Autowired
	DistrictMasterRepository districtMasterRepository;

	@Autowired
	ReportQueriesRepository reportQueriesRepository;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	MessageSource messageSource;

	@Override
	public BaseDTO create(BlockDTO blockDTO) {
		//log.info(" <----- BlockDaoImpl.create() Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			if (null != blockDTO) {
				List<BlockMasterEntity> list = blockRepository.checkCode(blockDTO.getBlockCode());
				if (list.isEmpty()) {
					BlockMasterEntity blockMasterEntity = new BlockMasterEntity();
					if (blockDTO.getBlockCode() != "") {
						//log.info("block Code - {} Is Not Null..", blockDTO.getBlockCode());
						if (blockDTO.getBlockName() != "") {
							//log.info("block name - {} Is Not Null..", blockDTO.getBlockName());
							if (blockDTO.getStatus() != null) {
								//log.info(" status - {} Is Not Null..", blockDTO.getStatus());
								if (blockDTO.getDistrictId() != null) {
									if (districtMasterRepository.findById(blockDTO.getDistrictId().getId())
											.isPresent()) {
										blockMasterEntity.setBlockName(blockDTO.getBlockName());
										blockMasterEntity.setBlockCode(blockDTO.getBlockCode());
										blockMasterEntity.setStatus(blockDTO.getStatus());
										blockMasterEntity.setDistrictId(blockDTO.getDistrictId());
										blockRepository.save(blockMasterEntity);
										String msg = messageSource.getMessage(
												ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
										response.setMessage(blockDTO.getBlockName() + " " + msg);
										response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
										//log.info("Block - {} Created Successfully.");
									} else {
										String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,
												Locale.US);
										response.setMessage(msg);
										response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
										//log.warn("District Not Found");
									}
								} else {
									response.setMessage("District Required");
									response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
									//log.warn("District Required");
								}
							} else {
								response.setMessage("Status Required");
								response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
								//log.warn("Status Required");
							}
						} else {
							response.setMessage("Block Name Required");
							response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
							//log.warn("Block Name Required");
						}
					} else {
						response.setMessage("Block Code Required");
						response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
						//log.warn("Block Code Required");
					}
				} else {
					String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
					response.setMessage(blockDTO.getBlockCode() + " " + msg);
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.info("Block Code - {}  Already Exist", blockDTO.getBlockCode());
				}
			} else {
				response.setMessage("Empty Data");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("Empty Data....");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- BlockDaoImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Add Block");
		} catch (Exception e) {
			log.error("<---- BlockDaoImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- BlockDaoImpl.create() Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- BlockDaoImpl.getById() Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			Optional<BlockMasterEntity> optional = blockRepository.findById(id);
			if (optional.isPresent()) {
			//	log.info("Block Id - {}", id);
				response.setResponseContent(optional);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get Block Details..");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- BlockDaoImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- BlockDaoImpl.getById() Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAll() {
		//log.info(" <----- BlockDaoImpl.getAll() Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<BlockMasterEntity> list = blockRepository.findAll();

			if (list.isEmpty()) {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setResponseContents(list);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				//log.info("Get all Block Details");
			}
		} catch (Exception e) {
			log.error("<----- BlockDaoImpl.getAll() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- BlockDaoImpl.getAll() Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		//log.info(" <----- BlockDaoImpl.getAllActive() Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<BlockMasterEntity> list = blockRepository.getAllActive();

			if (list.isEmpty()) {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setResponseContents(list);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,
						Locale.US);
				response.setMessage(msg);
				//log.info("Get All Active Block Details");
			}

		} catch (Exception e) {
			log.error("<----- BlockDaoImpl.getAllActive() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,
					Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- BlockDaoImpl.getAllActive() Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- BlockDaoImpl.getLazyList() Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		int pageSize = 0;
		int totalRecords = 0;
		int totalPages = 0;
		List<Map<String, Object>> totalListOfDataReport = null;
		List<Map<String, Object>> listCount = null;
		try {

			ReportQueriesEntity reportQuery = reportQueriesRepository.getReportbyName("Block_search_pagination");
			if (reportQuery == null) {
				////log.error("Block_search_pagination  Not Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
			ReportQueriesEntity reportQueryCount = reportQueriesRepository
					.getReportbyName("Block_search_pagination_count");
			if (reportQueryCount == null) {
				//log.error("Block_search_pagination_count  Not Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
			String query = reportQuery.getDataQuery();
			String queryCount = reportQueryCount.getDataQuery();
			if (requestData.getSearch() != null) {
				query = query.replace(":search", requestData.getSearch());
				queryCount = queryCount.replace(":search", requestData.getSearch());
			} else {
				query = query.replace(":search", "");
				queryCount = queryCount.replace(":search", "");
			}
			listCount = jdbcTemplate.queryForList(queryCount);
			if (listCount.size() > 0 && listCount.get(0).get("count") != null) {
				totalRecords = Integer.parseInt(listCount.get(0).get("count").toString());
				totalPages = Math.round(totalRecords / requestData.getPaginationSize());
				if( (totalRecords % requestData.getPaginationSize()) != 0) {
										totalPages++;
									}				
				//log.info("totalpages -{}",totalPages);
			}
			if (requestData.getPageNo() != null && requestData.getPaginationSize() != null) {
				if (requestData.getPageNo() != 0) {
					pageSize = (requestData.getPageNo() * requestData.getPaginationSize());
				}
			}
			if (requestData.getSortField() != null) {
				if (requestData.getSortField().toUpperCase().equals("BLOCKNAME"))
					query = query.replace(":shortField", "block_name");
				else if (requestData.getSortField().toUpperCase().equals("BLOCKCODE"))
					query = query.replace(":shortField", "block_code");
				else if (requestData.getSortField().toUpperCase().equals("DISTRICTNAME"))
					query = query.replace(":shortField", "district_name");
				else if (requestData.getSortField().toUpperCase().equals("STATENAME"))
					query = query.replace(":shortField", "state_name");

				else if (requestData.getSortField().toUpperCase().equals("STATUS"))
					query = query.replace(":shortField", "status");
			} else
				query = query.replace(":shortField", "block_name");

			if (requestData.getSortOrder() != null) {
				if (requestData.getSortOrder().toUpperCase().startsWith("A"))
					query = query.replace(":shortOrder", "ASC");
				else
					query = query.replace(":shortOrder", "DESC");

			}

			query = query.replace(":limit", +pageSize + "," + requestData.getPaginationSize());
			//log.info("<=========== query " + query);
			totalListOfDataReport = jdbcTemplate.queryForList(query);
			for (Map<String, Object> entity : totalListOfDataReport) {
				for (Map.Entry<String, Object> entry : entity.entrySet()) {
					if (entry.getKey().equals("id")) {
						entry.setValue(objectMapper.convertValue(entry.getValue(), UUID.class));
					}
				}
			}

			if (totalListOfDataReport != null && totalListOfDataReport.size() > 0) {
				response.setResponseContents(totalListOfDataReport);
				response.setNumberOfElements(requestData.getPaginationSize());
				response.setTotalRecords(Long.parseLong(totalRecords + ""));
				response.setTotalPages(totalPages);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Get the Details Fetched Successfully");
			} else {
				//log.warn("No Records Found");
				response.setMessage("No Records Found");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}

		} catch (Exception e) {
			log.error("<----- BlockDaoImpl.getLazyList() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- BlockDaoImpl.getLazyList() Dao END -----> ");
		return response;
	}
	

	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- BlockDaoImpl.delete() Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			BlockMasterEntity blockMasterEntity = blockRepository.getOne(id);
			if (blockMasterEntity != null) {
				blockRepository.delete(blockMasterEntity);
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,
						Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted Block details");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- BlockDaoImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- BlockDaoImpl.delete() Dao END -----> ");
		return response;
	}

	public BaseDTO softDelete(UUID id) {
		//log.info(" <-----  BlockDaoImpl.softDelete() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			BlockMasterEntity blockMasterEntity = blockRepository.getOne(id);
			if (blockMasterEntity != null) {
				blockMasterEntity.setStatus(false);
				blockRepository.save(blockMasterEntity);
				response.setMessage("Successfully Deleted");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- BlockDaoImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Block");
		}
		//log.info(" <-----  BlockDaoImpl.softDelete() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getByDistrictId(UUID id) {
		//log.info(" <----- BlockDaoImpl.getByDistrictId() Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			DistrictMasterEntity entity = new DistrictMasterEntity(id);
			List<BlockMasterEntity> list = blockRepository.findByDistrictId(entity);
			if (!list.isEmpty()) {
				response.setResponseContents(list);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get Block Details..");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- BlockDaoImpl.getByDistrictId() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- BlockDaoImpl.getByDistrictId() Dao END -----> ");
		return response;
	}

	private BlockMasterEntity updatedValues(BlockMasterEntity blockMasterEntity, BlockDTO blockDTO) {
		BlockMasterEntity blockMasterEntity2 = blockMasterEntity;
		blockMasterEntity2.setBlockCode(blockDTO.getBlockCode());
		blockMasterEntity2.setBlockName(blockDTO.getBlockName());
		blockMasterEntity2.setStatus(blockDTO.getStatus());
		blockMasterEntity2.setDistrictId(blockDTO.getDistrictId());
		return blockMasterEntity2;
	}

	@Override
	public BaseDTO update(UUID id, BlockDTO blockDTO) {
		//log.info(" <----- BlockDaoImpl.update() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			boolean check = false;
			Optional<BlockMasterEntity> optional = blockRepository.findById(id);
			if (optional.isPresent()) {
				BlockMasterEntity blockName = new BlockMasterEntity();
				blockName = optional.get();
				List<BlockMasterEntity> checkCode = blockRepository.checkCode(blockDTO.getBlockCode());
				for (BlockMasterEntity entity : checkCode) {
					if (entity.getBlockCode().equals(blockDTO.getBlockCode())) {
						if(id.equals(entity.getId())) {
							check = false;
						}else		
						check = true;
					} else {
						check = false;
					}
				}

				if (check) {
					String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
					response.setMessage(blockDTO.getBlockCode() + " " + msg);
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.warn("Block Code is already exists - {} ", blockDTO.getBlockCode());
				} else {
					blockName.setBlockCode(blockDTO.getBlockCode());
					blockName.setBlockName(blockDTO.getBlockName());
					blockName.setDistrictId(blockDTO.getDistrictId());
					blockName.setStatus(blockDTO.getStatus());
					BlockMasterEntity afterUpdate = blockRepository.save(blockName);
					response.setResponseContent(afterUpdate);
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(blockDTO.getBlockName() + " " + msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Block - {} Updated Successfully", blockDTO.getBlockName(),blockDTO.getBlockCode());
				}

			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- BlockDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());

		} catch (Exception e) {
			log.error("<---- BlockDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info(" <----- BlockDaoImpl.update() END -----> ");
		return response;
	}


}
