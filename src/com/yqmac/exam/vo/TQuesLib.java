package com.yqmac.exam.vo;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * TQuesLib entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_ques_lib" )
public class TQuesLib implements java.io.Serializable {

	// Fields

	private Integer id;
	private TQuesBank TQuesBank;
	private String libName;
	private Set<TQues> TQueses = new HashSet<TQues>(0);
	private Set<TPaperStrategy> TPaperStrategies = new HashSet<TPaperStrategy>(
			0);
	private Set<TQuesPoint> TQuesPoints = new HashSet<TQuesPoint>(0);

	// Constructors

	/** default constructor */
	public TQuesLib() {
	}

	/** full constructor */
	public TQuesLib(TQuesBank TQuesBank, String libName, Set<TQues> TQueses,
			Set<TPaperStrategy> TPaperStrategies,
			Set<TQuesPoint> TQuesPoints) {
		this.TQuesBank = TQuesBank;
		this.libName = libName;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bank_id")
	public TQuesBank getTQuesBank() {
		return this.TQuesBank;
	}

	public void setTQuesBank(TQuesBank TQuesBank) {
		this.TQuesBank = TQuesBank;
	}

	@Column(name = "lib_name", length = 50)
	public String getLibName() {
		return this.libName;
	}

	public void setLibName(String libName) {
		this.libName = libName;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TQuesLib")
	@LazyCollection(LazyCollectionOption.EXTRA)
	public Set<TQues> getTQueses() {
		return this.TQueses;
	}

	public void setTQueses(Set<TQues> TQueses) {
		this.TQueses = TQueses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TQuesLib")
	@LazyCollection(LazyCollectionOption.EXTRA)
	public Set<TPaperStrategy> getTPaperStrategies() {
		return this.TPaperStrategies;
	}

	public void setTPaperStrategies(Set<TPaperStrategy> TPaperStrategies) {
		this.TPaperStrategies = TPaperStrategies;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TQuesLib")
	@LazyCollection(LazyCollectionOption.EXTRA)
	public Set<TQuesPoint> getTQuesPoints() {
		return this.TQuesPoints;
	}

	public void setTQuesPoints(Set<TQuesPoint> TQuesPoints) {
		this.TQuesPoints = TQuesPoints;
	}

}