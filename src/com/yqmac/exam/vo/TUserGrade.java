package com.yqmac.exam.vo;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * TUserGrade entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_user_grade" )
public class TUserGrade implements java.io.Serializable {

	// Fields

	private Integer id;
	private TUser TUser;
	private TExam TExam;
	private Integer userState;
	private Timestamp begaintime;
	private Timestamp endtime;
	private Float grade;
	private Float gradeObjective;
	private Float gradeSubjective;
	private Integer munual;
	private Integer spend;
	private String grader;
	private Set<TUserAnswer> TUserAnswers = new HashSet<TUserAnswer>(0);

	// Constructors

	/** default constructor */
	public TUserGrade() {
	}

	/** full constructor */
	public TUserGrade(TUser TUser, TExam TExam, Integer userState,
			Timestamp begaintime, Timestamp endtime, Float grade,
			Float gradeObjective, Float gradeSubjective, Integer munual,
			Integer spend, String grader, Set<TUserAnswer> TUserAnswers) {
		this.TUser = TUser;
		this.TExam = TExam;
		this.userState = userState;
		this.begaintime = begaintime;
		this.endtime = endtime;
		this.grade = grade;
		this.gradeObjective = gradeObjective;
		this.gradeSubjective = gradeSubjective;
		this.munual = munual;
		this.spend = spend;
		this.grader = grader;
		this.TUserAnswers = TUserAnswers;
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

	@Column(name = "user_state")
	public Integer getUserState() {
		return this.userState;
	}

	public void setUserState(Integer userState) {
		this.userState = userState;
	}

	@Column(name = "begaintime", length = 19)
	public Timestamp getBegaintime() {
		return this.begaintime;
	}

	public void setBegaintime(Timestamp begaintime) {
		this.begaintime = begaintime;
	}

	@Column(name = "endtime", length = 19)
	public Timestamp getEndtime() {
		return this.endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	@Column(name = "grade", precision = 12, scale = 0)
	public Float getGrade() {
		return this.grade;
	}

	public void setGrade(Float grade) {
		this.grade = grade;
	}

	@Column(name = "grade_objective", precision = 12, scale = 0)
	public Float getGradeObjective() {
		return this.gradeObjective;
	}

	public void setGradeObjective(Float gradeObjective) {
		this.gradeObjective = gradeObjective;
	}

	@Column(name = "grade_subjective", precision = 12, scale = 0)
	public Float getGradeSubjective() {
		return this.gradeSubjective;
	}

	public void setGradeSubjective(Float gradeSubjective) {
		this.gradeSubjective = gradeSubjective;
	}

	@Column(name = "munual")
	public Integer getMunual() {
		return this.munual;
	}

	public void setMunual(Integer munual) {
		this.munual = munual;
	}

	@Column(name = "spend")
	public Integer getSpend() {
		return this.spend;
	}

	public void setSpend(Integer spend) {
		this.spend = spend;
	}

	@Column(name = "grader", length = 50)
	public String getGrader() {
		return this.grader;
	}

	public void setGrader(String grader) {
		this.grader = grader;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TUserGrade")
	@LazyCollection(LazyCollectionOption.EXTRA)
	public Set<TUserAnswer> getTUserAnswers() {
		return this.TUserAnswers;
	}

	public void setTUserAnswers(Set<TUserAnswer> TUserAnswers) {
		this.TUserAnswers = TUserAnswers;
	}

}