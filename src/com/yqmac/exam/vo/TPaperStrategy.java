package com.yqmac.exam.vo;

import javax.persistence.*;

/**
 * TPaperStrategy entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_paper_strategy" )
public class TPaperStrategy implements java.io.Serializable {

	// Fields

	private Integer id;
	private TExam TExam;
	private TQuesBank TQuesBank;
	private TQuesLib TQuesLib;
	private TQuesPoint TQuesPoint;
	private TQuesType TQuesType;
	private Integer quesCount;

	// Constructors

	/** default constructor */
	public TPaperStrategy() {
	}

	/** full constructor */
	public TPaperStrategy(TQuesType TQuesType, TQuesPoint TQuesPoint,
			TExam TExam, TQuesLib TQuesLib, TQuesBank TQuesBank,
			Integer quesCount) {
		this.TQuesType = TQuesType;
		this.TQuesPoint = TQuesPoint;
		this.TExam = TExam;
		this.TQuesLib = TQuesLib;
		this.TQuesBank = TQuesBank;
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
	@JoinColumn(name = "point_id")
	public TQuesPoint getTQuesPoint() {
		return this.TQuesPoint;
	}

	public void setTQuesPoint(TQuesPoint TQuesPoint) {
		this.TQuesPoint = TQuesPoint;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "exam_id")
	public TExam getTExam() {
		return this.TExam;
	}

	public void setTExam(TExam TExam) {
		this.TExam = TExam;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lib_id")
	public TQuesLib getTQuesLib() {
		return this.TQuesLib;
	}

	public void setTQuesLib(TQuesLib TQuesLib) {
		this.TQuesLib = TQuesLib;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bank_id")
	public TQuesBank getTQuesBank() {
		return this.TQuesBank;
	}

	public void setTQuesBank(TQuesBank TQuesBank) {
		this.TQuesBank = TQuesBank;
	}

	@Column(name = "quesCount")
	public Integer getQuesCount() {
		return this.quesCount;
	}

	public void setQuesCount(Integer quesCount) {
		this.quesCount = quesCount;
	}

	@Override
	public String toString() {
		return "TPaperStrategy{" +
				"id=" + id +
				", TQuesType=" + TQuesType +
				", TQuesPoint=" + TQuesPoint +
				", TExam=" + TExam +
				", TQuesLib=" + TQuesLib +
				", TQuesBank=" + TQuesBank +
				", quesCount=" + quesCount +
				'}';
	}
}