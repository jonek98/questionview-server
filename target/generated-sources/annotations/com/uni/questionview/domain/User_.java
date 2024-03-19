package com.uni.questionview.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.Instant;

@StaticMetamodel(User.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class User_ extends com.uni.questionview.domain.AbstractAuditingEntity_ {

	
	/**
	 * @see com.uni.questionview.domain.User#lastName
	 **/
	public static volatile SingularAttribute<User, String> lastName;
	
	/**
	 * @see com.uni.questionview.domain.User#resetDate
	 **/
	public static volatile SingularAttribute<User, Instant> resetDate;
	
	/**
	 * @see com.uni.questionview.domain.User#login
	 **/
	public static volatile SingularAttribute<User, String> login;
	
	/**
	 * @see com.uni.questionview.domain.User#activationKey
	 **/
	public static volatile SingularAttribute<User, String> activationKey;
	
	/**
	 * @see com.uni.questionview.domain.User#resetKey
	 **/
	public static volatile SingularAttribute<User, String> resetKey;
	
	/**
	 * @see com.uni.questionview.domain.User#authorities
	 **/
	public static volatile SetAttribute<User, Authority> authorities;
	
	/**
	 * @see com.uni.questionview.domain.User#firstName
	 **/
	public static volatile SingularAttribute<User, String> firstName;
	
	/**
	 * @see com.uni.questionview.domain.User#password
	 **/
	public static volatile SingularAttribute<User, String> password;
	
	/**
	 * @see com.uni.questionview.domain.User#langKey
	 **/
	public static volatile SingularAttribute<User, String> langKey;
	
	/**
	 * @see com.uni.questionview.domain.User#imageUrl
	 **/
	public static volatile SingularAttribute<User, String> imageUrl;
	
	/**
	 * @see com.uni.questionview.domain.User#id
	 **/
	public static volatile SingularAttribute<User, Long> id;
	
	/**
	 * @see com.uni.questionview.domain.User
	 **/
	public static volatile EntityType<User> class_;
	
	/**
	 * @see com.uni.questionview.domain.User#email
	 **/
	public static volatile SingularAttribute<User, String> email;
	
	/**
	 * @see com.uni.questionview.domain.User#activated
	 **/
	public static volatile SingularAttribute<User, Boolean> activated;

	public static final String LAST_NAME = "lastName";
	public static final String RESET_DATE = "resetDate";
	public static final String LOGIN = "login";
	public static final String ACTIVATION_KEY = "activationKey";
	public static final String RESET_KEY = "resetKey";
	public static final String AUTHORITIES = "authorities";
	public static final String FIRST_NAME = "firstName";
	public static final String PASSWORD = "password";
	public static final String LANG_KEY = "langKey";
	public static final String IMAGE_URL = "imageUrl";
	public static final String ID = "id";
	public static final String EMAIL = "email";
	public static final String ACTIVATED = "activated";

}

