package com.oasys.uppcl_user__master_management.repository;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.oasys.cexception.NoRecoerdFoundException;
import com.oasys.config.Constants;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.constant.ActionType;
import com.oasys.uppcl_user__master_management.entity.PackageHistory;
import com.oasys.uppcl_user__master_management.response.ResponseMessageConstant;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Repository
public class PackageHistoryRepositoryImpl {
	@PersistenceContext
	private EntityManager entityManager;

	public Long getCountBySearchFields(PaginationRequestDTO requestDTO) {
		log.info("getting total count by search fields :: {}", requestDTO.toString());
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<PackageHistory> from = cq.from(PackageHistory.class);
		cq.select(cb.count(from));
		List<Predicate> list = new ArrayList<>();
		addCriteria(cb, list, requestDTO, from);
		cq.where(cb.and(list.toArray(new Predicate[list.size()])));
		return entityManager.createQuery(cq).getSingleResult();

	}

	public List<PackageHistory> getRecordsByFilterDTO(PaginationRequestDTO requestDTO) {
		log.info("getting all records by search fields :: {}", requestDTO.toString());
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<PackageHistory> cq = cb.createQuery(PackageHistory.class);
		Root<PackageHistory> from = cq.from(PackageHistory.class);
		List<Predicate> list = new ArrayList<>();
		TypedQuery<PackageHistory> typedQuery = null;
		addCriteria(cb, list, requestDTO, from);
		cq.where(cb.and(list.toArray(new Predicate[list.size()])));
		cq.distinct(true);

		if (StringUtils.isBlank(requestDTO.getSortField())) {
			requestDTO.setSortField(Constants.CREATED_DATE);
		}
		if (Objects.isNull(requestDTO.getPageNo())) {
			requestDTO.setPageNo(0);
		}
		if (Objects.isNull(requestDTO.getPaginationSize())) {
			requestDTO.setPaginationSize(20);
		}
		if (StringUtils.isNotBlank(requestDTO.getSortOrder())
				&& requestDTO.getSortOrder().contentEquals(Constants.ASC)) {
			cq.orderBy(cb.asc(from.get(requestDTO.getSortField())));

		} else {
			cq.orderBy(cb.desc(from.get(requestDTO.getSortField())));

		}

		if (Objects.nonNull(requestDTO.getPaginationSize()) && Objects.nonNull(requestDTO.getPaginationSize())) {
			typedQuery = entityManager.createQuery(cq)
					.setFirstResult((requestDTO.getPageNo() * requestDTO.getPaginationSize()))
					.setMaxResults(requestDTO.getPaginationSize());
		} else {
			typedQuery = entityManager.createQuery(cq);
		}

		List<PackageHistory> data = typedQuery.getResultList();
		if (CollectionUtils.isEmpty(data)) {
			log.info("no data found corresponding to :: {}", requestDTO.toString());
			throw new NoRecoerdFoundException(ResponseMessageConstant.NO_RECOERD_FOUND.getMessage());
		}
		return data;
	}

	private void addCriteria(CriteriaBuilder cb, List<Predicate> list, PaginationRequestDTO requestDTO,
			Root<PackageHistory> from) {

		if (StringUtils.isNotBlank(requestDTO.getSearch())) {
			requestDTO.setSearch(requestDTO.getSearch().toUpperCase());
			if(EnumUtils.isValidEnum(ActionType.class, requestDTO.getSearch().toUpperCase())) {
				ActionType actionType = ActionType.valueOf(requestDTO.getSearch().toUpperCase());
				if (Objects.nonNull(actionType)) {
					list.add(cb.equal(from.get(Constants.ACTION_TYPE), actionType));
				} 
			}else {
				list.add(cb.or(
						cb.like(cb.upper(from.get(Constants.SERVICE_NAME)),
								Constants.PERCENTAGE + requestDTO.getSearch() + Constants.PERCENTAGE),
						cb.like(cb.upper(from.get(Constants.PACKAGE_NAME)),
								Constants.PERCENTAGE + requestDTO.getSearch() + Constants.PERCENTAGE),
						cb.like(cb.upper(from.get(Constants.REMARKS)),
								Constants.PERCENTAGE + requestDTO.getSearch() + Constants.PERCENTAGE)));
			}

			return;
		}
		log.info("filters ::" + requestDTO.getFilters());

		if (StringUtils.isNotBlank(requestDTO.getFromDate()) && StringUtils.isNotBlank(requestDTO.getToDate())) {

			try {
				list.add(cb.between(from.get(Constants.ACTION_DATE), LocalDate.parse(requestDTO.getFromDate()).atTime(0, 0, 0),
						LocalDate.parse(requestDTO.getToDate()).atTime(23, 59, 59)));
			} catch (DateTimeParseException e) {
				log.info("no data found corresponding to :: {}", requestDTO.toString());
				throw new NoRecoerdFoundException(ResponseMessageConstant.NO_RECOERD_FOUND.getMessage());
			}

		}

		if (Objects.nonNull(requestDTO.getFilters()) && Objects.nonNull(requestDTO.getFilters().get(Constants.SERVICE_NAME))
				&& !requestDTO.getFilters().get(Constants.SERVICE_NAME).toString().trim().isEmpty()) {

			String serviceCategoryName = (requestDTO.getFilters().get(Constants.SERVICE_NAME).toString());

			list.add(cb.equal(from.get(Constants.SERVICE_NAME), serviceCategoryName));
		}
	}
}
