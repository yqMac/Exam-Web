package com.yqmac.exam.vo;

import javax.persistence.*;

/**
 * TPaperUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_paper_user" )
public class TPaperUser implements java.io.Serializable {

	// Fields

	private Integer id;
	private TUser TUser;
	private TExam TExam;

	// Constructors

	/** default constructor */
	public TPaperUser() {
	}

	/** full constructor */
	public TPaperUser(TUser TUser, TExam TExam) {
		this.TUser = TUser;
		this.TExam = TExam;
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
	@JoinColumn(name = "user_id")
	public TUser getTUser() {
		return this.TUser;
	}

	public void setTUser(TUser TUser) {
		this.TUser = TUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "exam_id")
	public TExam getTExam() {
		return this.TExam;
	}

	public void setTExam(TExam TExam) {
		this.TExam = TExam;
	}

}