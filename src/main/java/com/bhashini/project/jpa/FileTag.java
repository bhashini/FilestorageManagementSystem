package com.bhashini.project.jpa;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;




@Entity 
@Table(name="file_tag")
@NamedQuery(name="FileTag.findAll", query="SELECT t FROM FileTag t")
public class FileTag {

	private static final long serialVersionUID = 1L;
	

	@EmbeddedId
	private FileTagPK filetagPK;
	
	@MapsId("fileIdentifier")
	@ManyToOne
	@JoinColumn(name="fileIdentifier",nullable=false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private File file;

	
	
	public FileTag() {
		super();
		// TODO Auto-generated constructor stub
	}



	public FileTag(FileTagPK filetagPK, File file) {
		super();
		this.filetagPK = filetagPK;
		this.file = file;
	}



	public FileTagPK getFiletagPK() {
		return filetagPK;
	}



	public void setFiletagPK(FileTagPK filetagPK) {
		this.filetagPK = filetagPK;
	}



	public File getFile() {
		return file;
	}



	public void setFile(File file) {
		this.file = file;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	@Override
	public String toString() {
		return "FileTag [filetagPK=" + filetagPK + ", file=" + file + "]";
	}
	
	
	
}
