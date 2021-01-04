package com.bhashini.project.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import com.bhashini.project.jpa.File;
import com.bhashini.project.jpa.FileTag;
import com.bhashini.project.jpa.FileTagPK;
import com.bhashini.project.repository.FileRepository;
import com.bhashini.project.repository.FileTagRepository;

@RestController
public class FileTagService {

	@Autowired
	FileTagRepository filetagRepository;
	
	@Autowired
	FileRepository fileRepository;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public FileTag createFileTag(Integer fileIdentifier, String tagName)
	{
		FileTagPK ftPK = new FileTagPK(tagName,fileIdentifier);
		File f = this.fileRepository.findById(fileIdentifier).get();
		FileTag ft = new FileTag(ftPK,f);
		return this.filetagRepository.save(ft);
				

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> listFileTagById(Integer fileIdentifier)
	{
		
		List<String> tagName = new ArrayList<>();
		List<FileTag> allTags = this.filetagRepository.findAll();
		
		
		for(FileTag ft : allTags)
		{
			if(ft.getFile() == this.fileRepository.findById(fileIdentifier).orElseThrow())
			{
				
					tagName.add(ft.getFiletagPK().getTagName());
			
			}
		}
		return tagName;
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Integer> listFileTagByName(String tagName)
	{
		
		List<Integer> fileIds = new ArrayList<>();
		List<FileTag> allTags = this.filetagRepository.findAll();
		
		
		for(FileTag ft : allTags)
		{
			if((ft.getFiletagPK().getTagName()).equalsIgnoreCase(tagName))
			{
				
				fileIds.add(ft.getFiletagPK().getFileIdentifier());
			
			}
		}
		return fileIds;
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteFileTag(String tagName,Integer fileIdentifier)
	{
		FileTagPK ft = new FileTagPK(tagName, fileIdentifier);
		this.filetagRepository.deleteById(ft);
	}
}
