package com.bhashini.project.jpa;

import java.io.Serializable;

import javax.persistence.Embeddable;
@Embeddable
public class FileTagPK implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tagName;
	
	private Integer fileIdentifier;

	public FileTagPK() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FileTagPK(String tagName, Integer fileIdentifier) {
		super();
		this.tagName = tagName;
		this.fileIdentifier = fileIdentifier;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Integer getFileIdentifier() {
		return fileIdentifier;
	}

	public void setFileIdentifier(Integer fileIdentifier) {
		this.fileIdentifier = fileIdentifier;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileIdentifier == null) ? 0 : fileIdentifier.hashCode());
		result = prime * result + ((tagName == null) ? 0 : tagName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileTagPK other = (FileTagPK) obj;
		if (fileIdentifier == null) {
			if (other.fileIdentifier != null)
				return false;
		} else if (!fileIdentifier.equals(other.fileIdentifier))
			return false;
		if (tagName == null) {
			if (other.tagName != null)
				return false;
		} else if (!tagName.equals(other.tagName))
			return false;
		return true;
	}

	

	
	
}
