package com.yqmac.exam.vo;

import javax.persistence.*;

/**
 * TPaperQuesCount entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_paper_ques_count" )
public class TPaperQuesCount implements java.io.Serializable {

	// Fields

	private Integer id;
	private TQuesType TQuesType;
	private TPaperStrategy TPaperStrategy;
	private Integer quesCount;

	// Constructors

	/** default constructor */
	public TPaperQuesCount() {
	}

	/** full constructor */
	public TPaperQuesCount(TQuesType TQuesType, TPaperStrategy TPaperStrategy,
			Integer quesCount) {
		this.TQuesType = TQuesType;
		this.TPaperStrategy = TPaperStrategy;
		this.quesCount = quesCount;
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
	@JoinColumn(name = "type_id")
	public TQuesType getTQuesType() {
		return this.TQuesType;
	}

	public void setTQuesType(TQuesType TQuesType) {
		this.TQuesType = TQuesType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "paper_id")
	public TPaperStrategy getTPaperStrategy() {
		return this.TPaperStrategy;
	}

	public void setTPaperStrategy(TPaperStrategy TPaperStrategy) {
		this.TPaperStrategy = TPaperStrategy;
	}

	@Column(name = "quesCount")
	public Integer getQuesCount() {
		return this.quesCount;
	}

	public void setQuesCount(Integer quesCount) {
		this.quesCount = quesCount;
	}

}