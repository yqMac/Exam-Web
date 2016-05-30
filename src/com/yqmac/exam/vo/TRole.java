package com.yqmac.exam.vo;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * TRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_role" )
public class TRole implements java.io.Serializable {

	// Fields

	private Integer id;
	private String roleName;
	private Set<TRolerightrelation> TRolerightrelations = new HashSet<TRolerightrelation>(
			0);
	private Set<TUser> TUsers = new HashSet<TUser>(0);

	// Constructors

	/** default constructor */
	public TRole() {
	}

	/** full constructor */
	public TRole(String roleName, Set<TRolerightrelation> TRolerightrelations,
			Set<TUser> TUsers) {
		this.roleName = roleName;
		this.TRolerightrelations = TRolerightrelations;
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

	@Column(name = "role_name", length = 50)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TRole")
	@LazyCollection(LazyCollectionOption.EXTRA)
	public Set<TRolerightrelation> getTRolerightrelations() {
		return this.TRolerightrelations;
	}

	public void setTRolerightrelations(
			Set<TRolerightrelation> TRolerightrelations) {
		this.TRolerightrelations = TRolerightrelations;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TRole")
	@LazyCollection(LazyCollectionOption.EXTRA)
	public Set<TUser> getTUsers() {
		return this.TUsers;
	}

	public void setTUsers(Set<TUser> TUsers) {
		this.TUsers = TUsers;
	}

}