package com.yqmac.exam.vo;

import com.sun.org.apache.bcel.internal.generic.FLOAD;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * TPaperQues entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_paper_ques" )
public class TPaperQues implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer examId;
	private Integer bankId;
	private Integer LibId;
	private Integer pointId;
	private Integer typeId;
	private Integer quesId;
	private String quesContent;
	private float score;
	private int  diffDegr;
	private String options;
	private int optionNum;
	private String answer;
	private Timestamp createTime;

	public TPaperQues() {
	}

	public TPaperQues(Integer id) {
		this.id = id;
	}

	@Column(name = "exam_id")
	public Integer getExamId() {
		return examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "bank_id")
	public Integer getBankId() {
		return bankId;
	}


	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}
	@Column(name = "lib_id")
	public Integer getLibId() {
		return LibId;
	}

	public void setLibId(Integer libId) {
		LibId = libId;
	}
	@Column(name = "point_id")
	public Integer getPointId() {
		return pointId;
	}

	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}
	@Column(name = "type_id")
	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	@Column(name = "ques_id")
	public Integer getQuesId() {
		return quesId;
	}

	public void setQuesId(Integer quesId) {
		this.quesId = quesId;
	}
	@Column(name = "ques_content")
	public String getQuesContent() {
		return quesContent;
	}

	public void setQuesContent(String quesContent) {
		this.quesContent = quesContent;
	}
	@Column(name = "score")
	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}
	@Column(name = "diff_degr")
	public int getDiffDegr() {
		return diffDegr;
	}

	public void setDiffDegr(int diffDegr) {
		this.diffDegr = diffDegr;
	}
	@Column(name = "options")
	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}
	@Column(name = "option_num")
	public int getOptionNum() {
		return optionNum;
	}

	public void setOptionNum(int optionNum) {
		this.optionNum = optionNum;
	}
	@Column(name = "answer")
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Column(name = "create_time")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}