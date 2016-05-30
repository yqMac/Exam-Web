package com.yqmac.exam.vo;

import javax.persistence.*;

/**
 * TRolerightrelation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_rolerightrelation" )
public class TRolerightrelation implements java.io.Serializable {

	// Fields

	private Integer id;
	private TRole TRole;
	private TRight TRight;

	// Constructors

	/** default constructor */
	public TRolerightrelation() {
	}

	/** full constructor */
	public TRolerightrelation(TRole TRole, TRight TRight) {
		this.TRole = TRole;
		this.TRight = TRight;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	public TRole getTRole() {
		return this.TRole;
	}

	public void setTRole(TRole TRole) {
		this.TRole = TRole;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "right_id")
	public TRight getTRight() {
		return this.TRight;
	}

	public void setTRight(TRight TRight) {
		this.TRight = TRight;
	}

}