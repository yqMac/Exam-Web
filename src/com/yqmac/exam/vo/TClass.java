package com.yqmac.exam.vo;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * TClass entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_class" )
public class TClass implements java.io.Serializable {

	// Fields

	private Integer id;
	private String className;
	private Set<TUser> TUsers = new HashSet<TUser>(0);

	// Constructors

	/** default constructor */
	public TClass() {
	}

	/** full constructor */
	public TClass(String className, Set<TUser> TUsers) {
		this.className = className;
		this.TUsers = TUsers;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "class_name", length = 50)
	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TClass")
	@LazyCollection(LazyCollectionOption.EXTRA)
	public Set<TUser> getTUsers() {
		return this.TUsers;
	}

	public void setTUsers(Set<TUser> TUsers) {
		this.TUsers = TUsers;
	}

}