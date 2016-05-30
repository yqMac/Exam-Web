package com.yqmac.exam.vo;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * TExam entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_exam" )
public class TExam implements java.io.Serializable {

	// Fields

	private Integer id;
	private String examName;
	private Integer examTime;
	private Timestamp begaintime;
	private Timestamp endtime;
	private Integer manual;
	private Integer seepaper;
	private Integer savepaper;
	private Integer entermaxtimes;
	private Integer isorder;
	private Integer moveouttimes;
	private Integer showtype;
	private Integer ispartuser;
	private Set<TUserGrade> TUserGrades = new HashSet<TUserGrade>(0);
	private Set<TPaperUser> TPaperUsers = new HashSet<TPaperUser>(0);
	private Set<TPaperStrategy> TPaperStrategies = new HashSet<TPaperStrategy>(
			0);

	// Constructors

	/** default constructor */
	public TExam() {
	}
public TExam(int id ){
	this.id = id;

}
	/** full constructor */
	public TExam(String examName, Integer examTime, Timestamp begaintime,
			Timestamp endtime, Integer manual, Integer seepaper,
			Integer savepaper, Integer entermaxtimes, Integer isorder,
			Integer moveouttimes, Integer showtype, Integer ispartuser,
			Set<TUserGrade> TUserGrades, Set<TPaperUser> TPaperUsers,
			 Set<TPaperStrategy> TPaperStrategies) {
		this.examName = examName;
		this.examTime = examTime;
		this.begaintime = begaintime;
		this.endtime = endtime;
		this.manual = manual;
		this.seepaper = seepaper;
		this.savepaper = savepaper;
		this.entermaxtimes = entermaxtimes;
		this.isorder = isorder;
		this.moveouttimes = moveouttimes;
		this.showtype = showtype;
		this.ispartuser = ispartuser;
		this.TUserGrades = TUserGrades;
		this.TPaperUsers = TPaperUsers;
		this.TPaperStrategies = TPaperStrategies;
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

	@Column(name = "exam_name", length = 50)
	public String getExamName() {
		return this.examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	@Column(name = "exam_time")
	public Integer getExamTime() {
		return this.examTime;
	}

	public void setExamTime(Integer examTime) {
		this.examTime = examTime;
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

	@Column(name = "manual")
	public Integer getManual() {
		return this.manual;
	}

	public void setManual(Integer manual) {
		this.manual = manual;
	}

	@Column(name = "seepaper")
	public Integer getSeepaper() {
		return this.seepaper;
	}

	public void setSeepaper(Integer seepaper) {
		this.seepaper = seepaper;
	}

	@Column(name = "savepaper")
	public Integer getSavepaper() {
		return this.savepaper;
	}

	public void setSavepaper(Integer savepaper) {
		this.savepaper = savepaper;
	}

	@Column(name = "entermaxtimes")
	public Integer getEntermaxtimes() {
		return this.entermaxtimes;
	}

	public void setEntermaxtimes(Integer entermaxtimes) {
		this.entermaxtimes = entermaxtimes;
	}

	@Column(name = "isorder")
	public Integer getIsorder() {
		return this.isorder;
	}

	public void setIsorder(Integer isorder) {
		this.isorder = isorder;
	}

	@Column(name = "moveouttimes")
	public Integer getMoveouttimes() {
		return this.moveouttimes;
	}

	public void setMoveouttimes(Integer moveouttimes) {
		this.moveouttimes = moveouttimes;
	}

	@Column(name = "showtype")
	public Integer getShowtype() {
		return this.showtype;
	}

	public void setShowtype(Integer showtype) {
		this.showtype = showtype;
	}

	@Column(name = "ispartuser")
	public Integer getIspartuser() {
		return this.ispartuser;
	}

	public void setIspartuser(Integer ispartuser) {
		this.ispartuser = ispartuser;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TExam")
	@LazyCollection(LazyCollectionOption.EXTRA)
	public Set<TUserGrade> getTUserGrades() {
		return this.TUserGrades;
	}

	public void setTUserGrades(Set<TUserGrade> TUserGrades) {
		this.TUserGrades = TUserGrades;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TExam")
	@LazyCollection(LazyCollectionOption.EXTRA)
	public Set<TPaperUser> getTPaperUsers() {
		return this.TPaperUsers;
	}

	public void setTPaperUsers(Set<TPaperUser> TPaperUsers) {
		this.TPaperUsers = TPaperUsers;
	}


	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TExam")
	@LazyCollection(LazyCollectionOption.EXTRA)
	public Set<TPaperStrategy> getTPaperStrategies() {
		return this.TPaperStrategies;
	}

	public void setTPaperStrategies(Set<TPaperStrategy> TPaperStrategies) {
		this.TPaperStrategies = TPaperStrategies;
	}

	@Override
	public String toString() {
		return "TExam{" +
				"id=" + id +
				", examName='" + examName + '\'' +
				", examTime=" + examTime +
				", begaintime=" + begaintime +
				", endtime=" + endtime +
				", manual=" + manual +
				", seepaper=" + seepaper +
				", savepaper=" + savepaper +
				", entermaxtimes=" + entermaxtimes +
				", isorder=" + isorder +
				", moveouttimes=" + moveouttimes +
				", showtype=" + showtype +
				", ispartuser=" + ispartuser +
				'}';
	}
}