package com.yqmac.exam.vo;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * TQues entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_ques" )
public class TQues implements java.io.Serializable {

	// Fields

	private Integer id;
	private TQuesType TQuesType;
	private TQuesPoint TQuesPoint;
	private TQuesLib TQuesLib;
	private TQuesBank TQuesBank;
	private String quesContent;
	private Float score;
	private Integer diffDegr;
	private String options;
	private Integer optionNum;
	private String answer;
	private Timestamp createTime;
	// Constructors

	/** default constructor */
	public TQues() {
	}

	public TQues(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public TQues(TQuesType TQuesType, TQuesPoint TQuesPoint, TQuesLib TQuesLib,
			TQuesBank TQuesBank, String quesContent, Float score,
			Integer diffDegr, String options, Integer optionNum, String answer,
			Timestamp createTime) {
		this.TQuesType = TQuesType;
		this.TQuesPoint = TQuesPoint;
		this.TQuesLib = TQuesLib;
		this.TQuesBank = TQuesBank;
		this.quesContent = quesContent;
		this.score = score;
		this.diffDegr = diffDegr;
		this.options = options;
		this.optionNum = optionNum;
		this.answer = answer;
		this.createTime = createTime;
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

	@Column(name = "ques_content", length = 1000)
	public String getQuesContent() {
		return this.quesContent;
	}

	public void setQuesContent(String quesContent) {
		this.quesContent = quesContent;
	}

	@Column(name = "score", precision = 12, scale = 0)
	public Float getScore() {
		return this.score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	@Column(name = "diff_degr")
	public Integer getDiffDegr() {
		return this.diffDegr;
	}

	public void setDiffDegr(Integer diffDegr) {
		this.diffDegr = diffDegr;
	}

	@Column(name = "options", length = 1000)
	public String getOptions() {
		return this.options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	@Column(name = "option_num")
	public Integer getOptionNum() {
		return this.optionNum;
	}

	public void setOptionNum(Integer optionNum) {
		this.optionNum = optionNum;
	}

	@Column(name = "answer", length = 50)
	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Column(name = "create_time", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}