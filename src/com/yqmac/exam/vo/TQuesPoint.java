package com.yqmac.exam.vo;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * TQuesPoint entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_ques_point" )
public class TQuesPoint implements java.io.Serializable {

	// Fields

	private Integer id;
	private TQuesLib TQuesLib;
	private TQuesBank TQuesBank;
	private String pointName;
	private Set<TPaperStrategy> TPaperStrategies = new HashSet<TPaperStrategy>(
			0);
	private Set<TQues> TQueses = new HashSet<TQues>(0);

	// Constructors

	/** default constructor */
	public TQuesPoint() {
	}

	/** full constructor */
	public TQuesPoint(TQuesLib TQuesLib, TQuesBank TQuesBank, String pointName,
			Set<TPaperStrategy> TPaperStrategies, Set<TQues> TQueses) {
		this.TQuesLib = TQuesLib;
		this.TQuesBank = TQuesBank;
		this.pointName = pointName;
		this.TPaperStrategies = TPaperStrategies;
		this.TQueses = TQueses;
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

	@Column(name = "point_name", length = 50)
	public String getPointName() {
		return this.pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TQuesPoint")
	@LazyCollection(LazyCollectionOption.EXTRA)
	public Set<TPaperStrategy> getTPaperStrategies() {
		return this.TPaperStrategies;
	}

	public void setTPaperStrategies(Set<TPaperStrategy> TPaperStrategies) {
		this.TPaperStrategies = TPaperStrategies;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TQuesPoint")
	@LazyCollection(LazyCollectionOption.EXTRA)
	public Set<TQues> getTQueses() {
		return this.TQueses;
	}

	public void setTQueses(Set<TQues> TQueses) {
		this.TQueses = TQueses;
	}

}