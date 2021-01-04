package com.bhashini.project.jpa;

import java.io.Serializable;
//import java.util.List;

//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
//import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name="file")
@NamedQuery(name="File.findAll", query="SELECT f FROM File f")
public class File implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="fileIdentifier", unique=true, nullable=false)
	private Integer fileIdentifier;
	
	@Column(nullable=true, length=45)
	private String fileName;
	
	@Column(name="Location", nullable=true, length=45)
	private String location;

	@Column(nullable=true, length=45)
	private String s3fileName;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="email")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;
	
	
	
	public File() {
		super();
	}


	public File(Integer fileIdentifier, String fileName) {
		super();
		this.fileIdentifier = fileIdentifier;
		this.fileName = fileName;
	
		
	}


	public Integer getFileIdentifier() {
		return fileIdentifier;
	}

	public void setFileIdentifier(Integer fileIdentifier) {
		this.fileIdentifier = fileIdentifier;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	public String getS3fileName() {
		return s3fileName;
	}


	public void setS3fileName(String s3fileName) {
		this.s3fileName = s3fileName;
	}


	@Override
	public String toString() {
		return "File [fileIdentifier=" + fileIdentifier + ", fileName=" + fileName + ", location=" + location
				+ ", s3fileName=" + s3fileName + ", user=" + user + "]";
	}

}
