package com.bhashini.project.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Entity
@Table(name="user")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
@JsonIgnoreProperties({ "files" })
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="email", unique=true, nullable=false)
	private String email;
	
	@Column(name="password", nullable=false, length=45)
	private String password;
	
	@Column(name="name", nullable=true, length=45)
	private String name;
	

	@Column(name="bname", nullable=true, length=45)
	private String bname;

	//orphanRemoval=true - this is used to remove a record from file table through the related user email is exits in the User table
	 @OneToMany(mappedBy="user", cascade=CascadeType.ALL, orphanRemoval=true)
	 private List<File> files;
	
	

	public User(String email, String password, String name, String bname, List<File> files) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.bname = bname;
		this.files = files;
	}

	public User() {
		super();
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}


	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", password=" + password + ", name=" + name + ", bname=" + bname + ", files="
				+ files + "]";
	}

	
}
