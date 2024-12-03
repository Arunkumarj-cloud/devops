package com.oasys.uppcl_user__master_management.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.oasys.cexception.InvalidRequestException;
import com.oasys.cexception.NoRecoerdFoundException;
import com.oasys.config.Constants;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.entity.ServiceCategoryEntity;
import com.oasys.uppcl_user__master_management.entity.ServiceCategoryFeeMappingHistoryEntity;
import com.oasys.uppcl_user__master_management.response.ResponseMessageConstant;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class ServiceCategoryFeeMappingHistoryRepositoryImpl {
	@PersistenceContext
	private EntityManager entityManager;

	public Long getCountBySearchFields(PaginationRequestDTO requestDTO) {
		log.info("getting total count by search fields :: {}", requestDTO.toString());
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<ServiceCategoryFeeMappingHistoryEntity> from = cq.from(ServiceCategoryFeeMappingHistoryEntity.class);
		cq.select(cb.count(from));
		List<Predicate> list = new ArrayList<>();
		addCriteria(cb, list, requestDTO, from);
		cq.where(cb.and(list.toArray(new Predicate[list.size()])));
		return entityManager.createQuery(cq).getSingleResult();
	}

	public List<ServiceCategoryFeeMappingHistoryEntity> getRecordsByFilterDTO(PaginationRequestDTO requestDTO) {
		log.info("getting all records by search fields :: {}", requestDTO.toString());
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ServiceCategoryFeeMappingHistoryEntity> cq = cb.createQuery(ServiceCategoryFeeMappingHistoryEntity.class);
		Root<ServiceCategoryFeeMappingHistoryEntity> from = cq.from(ServiceCategoryFeeMappingHistoryEntity.class);
		List<Predicate> list = new ArrayList<>();
		TypedQuery<ServiceCategoryFeeMappingHistoryEntity> typedQuery = null;
		addCriteria(cb, list, requestDTO, from);
		cq.where(cb.and(list.toArray(new Predicate[list.size()])));
		cq.distinct(true);

		if (StringUtils.isBlank(requestDTO.getSortField())) {
			requestDTO.setSortField(Constants.CREATED_DATE);
		}
		
		if(requestDTO.getSortField().equalsIgnoreCase(Constants.SERVICE_CATEGORY_NAME)) {
			Join<ServiceCategoryFeeMappingHistoryEntity, ServiceCategoryEntity> serviceCategoryEntity = from.join(Constants.SERVICE_CATEGORY_ENTITY, JoinType.LEFT);
			if (StringUtils.isNotBlank(requestDTO.getSortOrder())
					&& requestDTO.getSortOrder().contentEquals(Constants.ASC)) {
				cq.orderBy(cb.asc(serviceCategoryEntity.get(Constants.NAME)));

			} else {
				cq.orderBy(cb.desc(serviceCategoryEntity.get(Constants.NAME)));

			}
		}else {
			if (StringUtils.isNotBlank(requestDTO.getSortOrder())
					&& requestDTO.getSortOrder().contentEquals(Constants.ASC)) {
				cq.orderBy(cb.asc(from.get(requestDTO.getSortField())));

			} else {
				cq.orderBy(cb.desc(from.get(requestDTO.getSortField())));

			}
		}

	

		if (Objects.nonNull(requestDTO.getPaginationSize()) && Objects.nonNull(requestDTO.getPaginationSize())) {
			typedQuery = entityManager.createQuery(cq)
					.setFirstResult((requestDTO.getPageNo() * requestDTO.getPaginationSize()))
					.setMaxResults(requestDTO.getPaginationSize());
		} else {
			typedQuery = entityManager.createQuery(cq);
		}

		List<ServiceCategoryFeeMappingHistoryEntity> data = typedQuery.getResultList();
		if (CollectionUtils.isEmpty(data)) {
			log.info("no data found corresponding to :: {}", requestDTO.toString());
			throw new NoRecoerdFoundException(ResponseMessageConstant.NO_RECOERD_FOUND.getMessage());
		}
		return data;
	}

	private void addCriteria(CriteriaBuilder cb, List<Predicate> list, PaginationRequestDTO requestDTO,
			Root<ServiceCategoryFeeMappingHistoryEntity> from) {
		Join<ServiceCategoryFeeMappingHistoryEntity, ServiceCategoryEntity> serviceEntity = from.join(Constants.SERVICE_CATEGORY_ENTITY, JoinType.INNER);

		if (StringUtils.isNotBlank(requestDTO.getSearch())) {
			requestDTO.setSearch(requestDTO.getSearch().toUpperCase());
			Double amount = null;
			try {
				amount = Double.parseDouble(requestDTO.getSearch());
			} catch (NumberFormatException nfe) {
				amount = null;
			}
			if (Objects.isNull(amount)) {
				list.add(cb.like(cb.upper(serviceEntity.get(Constants.NAME)),
						Constants.PERCENTAGE + requestDTO.getSearch() + Constants.PERCENTAGE));

			} else {
				list.add(cb.or(
						cb.like(cb.upper(serviceEntity.get(Constants.NAME)),
								Constants.PERCENTAGE + requestDTO.getSearch() + Constants.PERCENTAGE),
						cb.equal(from.get(Constants.UPDATED_AMOUNT), requestDTO.getSearch()),
						cb.equal(from.get(Constants.PREVIOUS_AMOUNT), requestDTO.getSearch())));

			}

		} else {
			log.info("filters ::" + requestDTO.getFilters());

			if (StringUtils.isNotBlank(requestDTO.getFromDate()) && StringUtils.isNotBlank(requestDTO.getToDate())) {
				Boolean isSearchFieldFound = Boolean.FALSE;
				if (Objects.nonNull(requestDTO.getFilters())) {
					if (Objects.nonNull(requestDTO.getFilters().get(Constants.SEARCH_BY_DATE))
							&& !requestDTO.getFilters().get(Constants.SEARCH_BY_DATE).toString().trim().isEmpty()
							&& Constants.MODIFIED_DATE
									.equals(requestDTO.getFilters().get(Constants.SEARCH_BY_DATE).toString())) {

						Date fromDateTime;
						Date toDateTime;
						try {
							fromDateTime = new SimpleDateFormat(Constants.DATE_FORMAT)
									.parse(requestDTO.getFromDate() + Constants.SPACE + Constants.START_TIME);
							toDateTime = new SimpleDateFormat(Constants.DATE_FORMAT)
									.parse(requestDTO.getToDate() + Constants.SPACE + Constants.END_TIME);

						} catch (ParseException e) {
							log.error("parsing error :: {}", e);
						//	throw new InvalidRequestException(ResponseMessageConstant.INVALID_REQUEST_PARAMETER
							//		.getMessage(new Object[] { Constants.FROM_OR_TO_DATE }));
						}

						//list.add(cb.between(from.get(Constants.MODIFIED_DATE), fromDateTime, toDateTime));
						isSearchFieldFound = Boolean.TRUE;
					}

				}
				if (Boolean.FALSE.equals(isSearchFieldFound)) {
					Date fromDateTime;
					Date toDateTime;
					try {
						fromDateTime = new SimpleDateFormat(Constants.DATE_FORMAT)
								.parse(requestDTO.getFromDate() + Constants.SPACE + Constants.START_TIME);
						toDateTime = new SimpleDateFormat(Constants.DATE_FORMAT)
								.parse(requestDTO.getToDate() + Constants.SPACE + Constants.END_TIME);

					} catch (ParseException e) {
						log.error("parsing error :: {}", e);
					//	throw new InvalidRequestException(ResponseMessageConstant.INVALID_REQUEST_PARAMETER
						//		.getMessage(new Object[] { Constants.FROM_OR_TO_DATE }));
					}

				//	list.add(cb.between(from.get(Constants.CREATED_DATE), fromDateTime, toDateTime));
				}
			}

			if (Objects.nonNull(requestDTO.getFilters())) {

				if (Objects.nonNull(requestDTO.getFilters().get(Constants.SERVICE_CATEGORY_ID))
						&& !requestDTO.getFilters().get(Constants.SERVICE_CATEGORY_ID).toString().trim().isEmpty()) {

					UUID serviceId = UUID
							.fromString((requestDTO.getFilters().get(Constants.SERVICE_CATEGORY_ID).toString()));

					list.add(cb.equal(serviceEntity.get(Constants.ID), serviceId));

				}

				if (Objects.nonNull(requestDTO.getFilters().get(Constants.PREVIOUS_AMOUNT))
						&& !requestDTO.getFilters().get(Constants.PREVIOUS_AMOUNT).toString().trim().isEmpty()) {

					Double amount = Double.valueOf(requestDTO.getFilters().get(Constants.PREVIOUS_AMOUNT).toString());

					list.add(cb.equal(from.get(Constants.PREVIOUS_AMOUNT), amount));

				}
				if (Objects.nonNull(requestDTO.getFilters().get(Constants.UPDATED_AMOUNT))
						&& !requestDTO.getFilters().get(Constants.UPDATED_AMOUNT).toString().trim().isEmpty()) {

					Double amount = Double.valueOf(requestDTO.getFilters().get(Constants.UPDATED_AMOUNT).toString());

					list.add(cb.equal(from.get(Constants.UPDATED_AMOUNT), amount));

				}

			}
		}

	}
}
