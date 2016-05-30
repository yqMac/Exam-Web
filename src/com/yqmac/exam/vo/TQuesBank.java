package com.yqmac.exam.vo;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * TQuesBank entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_ques_bank" )
public class TQuesBank implements java.io.Serializable {

	// Fields

	private Integer id;
	private String bankName;
	private Set<TQuesLib> TQuesLibs = new HashSet<TQuesLib>(0);
	private Set<TQues> TQueses = new HashSet<TQues>(0);
	private Set<TPaperStrategy> TPaperStrategies = new HashSet<TPaperStrategy>(
			0);
	private Set<TQuesPoint> TQuesPoints = new HashSet<TQuesPoint>(0);

	// Constructors

	/** default constructor */
	public TQuesBank() {
	}

	/** full constructor */
	public TQuesBank(String bankName, Set<TQuesLib> TQuesLibs, Set<TQues> TQueses,
			Set<TPaperStrategy> TPaperStrategies, Set<TQuesPoint> TQuesPoints) {
		this.bankName = bankName;
		this.TQuesLibs = TQuesLibs;
		this.TQueses = TQueses;
		this.TPaperStrategies = TPaperStrategies;
		this.TQuesPoints = TQuesPoints;
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

	@Column(name = "bank_name", length = 50)
	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TQuesBank")
	@LazyCollection(LazyCollectionOption.EXTRA)
	public Set<TQuesLib> getTQuesLibs() {
		return this.TQuesLibs;
	}

	public void setTQuesLibs(Set<TQuesLib> TQuesLibs) {
		this.TQuesLibs = TQuesLibs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TQuesBank")
	@LazyCollection(LazyCollectionOption.EXTRA)
	public Set<TQues> getTQueses() {
		return this.TQueses;
	}

	public void setTQueses(Set<TQues> TQueses) {
		this.TQueses = TQueses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TQuesBank")
	@LazyCollection(LazyCollectionOption.EXTRA)
	public Set<TPaperStrategy> getTPaperStrategies() {
		return this.TPaperStrategies;
	}

	public void setTPaperStrategies(Set<TPaperStrategy> TPaperStrategies) {
		this.TPaperStrategies = TPaperStrategies;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TQuesBank")
	@LazyCollection(LazyCollectionOption.EXTRA)
	public Set<TQuesPoint> getTQuesPoints() {
		return this.TQuesPoints;
	}

	public void setTQuesPoints(Set<TQuesPoint> TQuesPoints) {
		this.TQuesPoints = TQuesPoints;
	}


	@Override
	public String toString() {
		return "TQuesBank{" +
				"id=" + id +
				", bankName='" + bankName + '\'' +
				'}';
	}
}