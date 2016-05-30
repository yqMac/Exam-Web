package com.yqmac.exam.vo;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * TQuesType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_ques_type" )
public class TQuesType implements java.io.Serializable {

	// Fields

	private Integer id;
	private String typeName;
	private Set<TQues> TQueses = new HashSet<TQues>(0);
	private Set<TPaperStrategy> TPaperStrategies = new HashSet<TPaperStrategy>(
			0);

	// Constructors

	/** default constructor */
	public TQuesType() {
	}

	/** full constructor */
	public TQuesType(String typeName, Set<TQues> TQueses,
			Set<TPaperStrategy> TPaperStrategies) {
		this.typeName = typeName;
		this.TQueses = TQueses;
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

	@Column(name = "type_name", length = 50)
	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TQuesType")
	@LazyCollection(LazyCollectionOption.EXTRA)
	public Set<TQues> getTQueses() {
		return this.TQueses;
	}

	public void setTQueses(Set<TQues> TQueses) {
		this.TQueses = TQueses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TQuesType")
	@LazyCollection(LazyCollectionOption.EXTRA)
	public Set<TPaperStrategy> getTPaperStrategies() {
		return this.TPaperStrategies;
	}

	public void setTPaperStrategies(Set<TPaperStrategy> TPaperStrategies) {
		this.TPaperStrategies = TPaperStrategies;
	}

}