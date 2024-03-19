package com.uni.questionview.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.MappedSuperclassType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.Instant;

@StaticMetamodel(AbstractAuditingEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class AbstractAuditingEntity_ {

	
	/**
	 * @see com.uni.questionview.domain.AbstractAuditingEntity#createdDate
	 **/
	public static volatile SingularAttribute<AbstractAuditingEntity, Instant> createdDate;
	
	/**
	 * @see com.uni.questionview.domain.AbstractAuditingEntity#createdBy
	 **/
	public static volatile SingularAttribute<AbstractAuditingEntity, String> createdBy;
	
	/**
	 * @see com.uni.questionview.domain.AbstractAuditingEntity#lastModifiedDate
	 **/
	public static volatile SingularAttribute<AbstractAuditingEntity, Instant> lastModifiedDate;
	
	/**
	 * @see com.uni.questionview.domain.AbstractAuditingEntity#lastModifiedBy
	 **/
	public static volatile SingularAttribute<AbstractAuditingEntity, String> lastModifiedBy;
	
	/**
	 * @see com.uni.questionview.domain.AbstractAuditingEntity
	 **/
	public static volatile MappedSuperclassType<AbstractAuditingEntity> class_;

	public static final String CREATED_DATE = "createdDate";
	public static final String CREATED_BY = "createdBy";
	public static final String LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String LAST_MODIFIED_BY = "lastModifiedBy";

}

