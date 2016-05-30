package com.yqmac.exam.vo;

import javax.persistence.*;

/**
 * TUserAnswer entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_user_answer" )
public class TUserAnswer implements java.io.Serializable {

	// Fields
	private Integer id;
	private TExam TExam;
	private TPaperQues TPaperQues;
	private TUserGrade TUserGrade;
	private String answer;
	private Float gradeAuto;
	private int needhandle;

	private Float gradeHandle;

	// Constructors

	/** default constructor */
	public TUserAnswer() {
	}

	/** full constructor */
	public TUserAnswer(TPaperQues TPaperQues, TUserGrade TUserGrade,
			String answer, Float gradeHandle) {
		this.TPaperQues = TPaperQues;
		this.TUserGrade = TUserGrade;
		this.answer = answer;
		this.gradeHandle = gradeHandle;
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
	@JoinColumn(name = "paper_ques_id")
	public TPaperQues getTPaperQues() {
		return this.TPaperQues;
	}

	public void setTPaperQues(TPaperQues TPaperQues) {
		this.TPaperQues = TPaperQues;
	}


	@JoinColumn(name = "exam_id")
	@ManyToOne(fetch = FetchType.LAZY)
	public TExam getTExam() {
		return TExam;
	}

	public void setTExam(TExam TExam) {
		this.TExam = TExam;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_grade_id")
	public TUserGrade getTUserGrade() {
		return this.TUserGrade;
	}

	public void setTUserGrade(TUserGrade TUserGrade) {
		this.TUserGrade = TUserGrade;
	}

	@Column(name = "answer", length = 1000)
	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Column(name = "grade_handle", precision = 12, scale = 0)
	public Float getGradeHandle() {
		return this.gradeHandle;
	}

	public void setGradeHandle(Float gradeHandle) {
		this.gradeHandle = gradeHandle;
	}


	@Column(name = "grade_auto")
	public Float getGradeAuto() {
		return gradeAuto;
	}

	public void setGradeAuto(Float gradeAuto) {
		this.gradeAuto = gradeAuto;
	}

	@Column(name = "needhandle")
	public int getNeedhandle() {
		return needhandle;
	}

	public void setNeedhandle(int needhandle) {
		this.needhandle = needhandle;
	}
}