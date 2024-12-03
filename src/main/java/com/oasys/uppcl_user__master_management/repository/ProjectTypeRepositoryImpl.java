package com.oasys.uppcl_user__master_management.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.oasys.cexception.NoRecoerdFoundException;
import com.oasys.config.Constants;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.entity.ProjectTypeEntity;
import com.oasys.uppcl_user__master_management.response.ResponseMessageConstant;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Repository
public class ProjectTypeRepositoryImpl {
	@PersistenceContext
	private EntityManager entityManager;

	public Long getCountBySearchFields(PaginationRequestDTO requestDTO) {
		log.info("getting total count by search fields :: {}", requestDTO.toString());
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<ProjectTypeEntity> from = cq.from(ProjectTypeEntity.class);
		cq.select(cb.count(from));
		List<Predicate> list = new ArrayList<>();
		addCriteria(cb, list, requestDTO, from);
		cq.where(cb.and(list.toArray(new Predicate[list.size()])));
		return entityManager.createQuery(cq).getSingleResult();
	}

	public List<ProjectTypeEntity> getRecordsByFilterDTO(PaginationRequestDTO requestDTO) {
		log.info("getting all records by search fields :: {}", requestDTO.toString());
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProjectTypeEntity> cq = cb.createQuery(ProjectTypeEntity.class);
		Root<ProjectTypeEntity> from = cq.from(ProjectTypeEntity.class);
		List<Predicate> list = new ArrayList<>();
		TypedQuery<ProjectTypeEntity> typedQuery = null;
		addCriteria(cb, list, requestDTO, from);
		cq.where(cb.and(list.toArray(new Predicate[list.size()])));
		cq.distinct(true);

		if (StringUtils.isBlank(requestDTO.getSortField())) {
			requestDTO.setSortField(Constants.CREATED_DATE);
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

		List<ProjectTypeEntity> data = typedQuery.getResultList();
		if (CollectionUtils.isEmpty(data)) {
			log.info("no data found corresponding to :: {}", requestDTO.toString());
			throw new NoRecoerdFoundException(ResponseMessageConstant.NO_RECOERD_FOUND.getMessage());
		}
		return data;
	}

	private void addCriteria(CriteriaBuilder cb, List<Predicate> list, PaginationRequestDTO requestDTO,
			Root<ProjectTypeEntity> from) {

		if (StringUtils.isNotBlank(requestDTO.getSearch())) {
			requestDTO.setSearch(requestDTO.getSearch().toUpperCase());
			list.add(cb.or(
					cb.like(cb.upper(from.get(Constants.PINCODE)),
							Constants.PERCENTAGE + requestDTO.getSearch() + Constants.PERCENTAGE)));

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
				//			throw new InvalidRequestException(ResponseMessageConstant.INVALID_REQUEST_PARAMETER
				//					.getMessage(new Object[] { Constants.FROM_OR_TO_DATE }));
						}

				//		list.add(cb.between(from.get(Constants.MODIFIED_DATE), fromDateTime, toDateTime));
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
		//				throw new InvalidRequestException(ResponseMessageConstant.INVALID_REQUEST_PARAMETER
		//						.getMessage(new Object[] { Constants.FROM_OR_TO_DATE }));
					}

		//
					//list.add(cb.between(from.get(Constants.CREATED_DATE), fromDateTime, toDateTime));
				}
			}

			if (Objects.nonNull(requestDTO.getFilters())) {
				if (Objects.nonNull(requestDTO.getFilters().get(Constants.PINCODE))
						&& !requestDTO.getFilters().get(Constants.PINCODE).toString().trim().isEmpty()) {

					String pincode = (requestDTO.getFilters().get(Constants.PINCODE).toString());

					list.add(cb.equal(from.get(Constants.PINCODE), pincode));

				}

				if (Objects.nonNull(requestDTO.getFilters().get(Constants.STATUS_PARAM))
						&& !requestDTO.getFilters().get(Constants.STATUS_PARAM).toString().trim().isEmpty()) {

					Boolean status = Boolean.valueOf(requestDTO.getFilters().get(Constants.STATUS_PARAM).toString());

					list.add(cb.equal(from.get(Constants.STATUS_PARAM), status));

				}

			}
		}

	}
}
