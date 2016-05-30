package com.yqmac.exam.vo;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * TRight entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_right" )
public class TRight implements java.io.Serializable {

	// Fields

	private Integer id;
	private String rightName;
	private TRight parent;
	private String rightUrl;
	private Set<TRolerightrelation> TRolerightrelations = new HashSet<TRolerightrelation>(
			0);
	private Set<TRight> rights = new HashSet<>();
	// Constructors

	/** default constructor */
	public TRight() {
	}

	/** full constructor */
	public TRight(String rightName, TRight parent, String rightUrl,
			Set<TRolerightrelation> TRolerightrelations) {
		this.rightName = rightName;
		this.parent = parent;
		this.rightUrl = rightUrl;
		this.TRolerightrelations = TRolerightrelations;
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

	@Column(name = "right_name", length = 50)
	public String getRightName() {
		return this.rightName;
	}

	public void setRightName(String rightName) {
		this.rightName = rightName;
	}

	@JoinColumn(name= "parent_id")
	@ManyToOne(fetch = FetchType.LAZY)
	public TRight getParent() {
		return this.parent;
	}

	public void setParent(TRight parent) {
		this.parent = parent;
	}

	@Column(name = "right_url", length = 50)
	public String getRightUrl() {
		return this.rightUrl;
	}

	public void setRightUrl(String rightUrl) {
		this.rightUrl = rightUrl;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TRight")
	@LazyCollection(LazyCollectionOption.EXTRA)
	public Set<TRolerightrelation> getTRolerightrelations() {
		return this.TRolerightrelations;
	}

	public void setTRolerightrelations(
			Set<TRolerightrelation> TRolerightrelations) {
		this.TRolerightrelations = TRolerightrelations;
	}

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.EXTRA)
	public Set<TRight> getRights() {
		return rights;
	}

	public void setRights(Set<TRight> rights) {
		this.rights = rights;
	}
}