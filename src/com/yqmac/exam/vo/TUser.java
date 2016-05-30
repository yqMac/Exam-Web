package com.yqmac.exam.vo;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * TUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_user" )
public class TUser implements java.io.Serializable {

	// Fields

	private Integer id;
	private TClass TClass;
	private TRole TRole;
	private String username;
	private String password;
	private String nickname;
	private Set<TUserGrade> TUserGrades = new HashSet<TUserGrade>(0);
	private Set<TPaperUser> TPaperUsers = new HashSet<TPaperUser>(0);

	// Constructors

	/** default constructor */
	public TUser() {
	}

	/** full constructor */
	public TUser(TClass TClass, TRole TRole, String username, String password,
			String nickname, Set<TUserGrade> TUserGrades,
			Set<TPaperUser> TPaperUsers) {
		this.TClass = TClass;
		this.TRole = TRole;
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.TUserGrades = TUserGrades;
		this.TPaperUsers = TPaperUsers;
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
	@JoinColumn(name = "class_id")
	public TClass getTClass() {
		return this.TClass;
	}

	public void setTClass(TClass TClass) {
		this.TClass = TClass;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	public TRole getTRole() {
		return this.TRole;
	}

	public void setTRole(TRole TRole) {
		this.TRole = TRole;
	}

	@Column(name = "username", length = 50)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "nickname", length = 50)
	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TUser")
	@LazyCollection(LazyCollectionOption.EXTRA)
	public Set<TUserGrade> getTUserGrades() {
		return this.TUserGrades;
	}

	public void setTUserGrades(Set<TUserGrade> TUserGrades) {
		this.TUserGrades = TUserGrades;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TUser")
	@LazyCollection(LazyCollectionOption.EXTRA)
	public Set<TPaperUser> getTPaperUsers() {
		return this.TPaperUsers;
	}

	public void setTPaperUsers(Set<TPaperUser> TPaperUsers) {
		this.TPaperUsers = TPaperUsers;
	}

}