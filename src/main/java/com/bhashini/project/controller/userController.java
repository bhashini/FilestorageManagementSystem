package com.bhashini.project.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bhashini.project.jpa.File;
import com.bhashini.project.jpa.FileTag;
import com.bhashini.project.jpa.User;
import com.bhashini.project.service.FileService;
import com.bhashini.project.service.FileTagService;
import com.bhashini.project.service.UserService;

@RestController
@CrossOrigin
public class userController {

	@Autowired
	UserService userService;
	
	@Autowired
	FileService fileService;

	@Autowired
	FileTagService filetagService;
	

	
	@PostMapping("/create/user")
	public User createUser(@RequestBody User user) {
		
		return this.userService.createUser(user);
	}

	@PutMapping("/update/user/{email}")
	public User updateUser(@RequestBody User user, @PathVariable("email") String email) {

		return this.userService.updateUser(user, email);
	}

	@GetMapping("/find/user/{email}")
	public User findUser(@PathVariable("email") String email) throws Exception
	{
		return this.userService.findUser(email);
	}
	@GetMapping("/find/user/login/{email}/{password}")
	public User loginUser(@PathVariable("email") String email,@PathVariable("password") String password) throws Exception
	{
		return this.userService.loginUser(email,password);
	}
	
	@DeleteMapping("/delete/user/{email}")
	public void deleteUser(@PathVariable("email") String email)
	{
		this.userService.deleteUser(email);
	}
	
	/*  ------ File ----*/
	
	@PostMapping("/file/create/{email}/{fileName}")
	public File createFile(@PathVariable("email") String email,@PathVariable("fileName") String filename,@RequestParam(value = "file") MultipartFile newfile) throws IOException{
		
		List<File> files = this.fileService.listFiles();
		for(File f:files)
		{
			System.out.println("file No: "+f.getFileIdentifier());
		}
		Integer fileIdentifier;
		
		//Auto Increment fileIdentifier
		if(files != null) {
		long count = files.stream().count();
		Stream<File> stream = files.stream();   
		File lastFile = stream.skip(count - 1).findFirst().get();
		Integer lastFileId = lastFile.getFileIdentifier();
		System.out.println("last File IdNo: "+lastFileId);
		lastFileId +=1;
		fileIdentifier = lastFileId;
		System.out.println("New File IdNo: "+fileIdentifier);
		}
		else {
			fileIdentifier = 1;
		}

		
		File fileNew = new File(fileIdentifier,filename);
		System.out.println("filename: "+fileNew.getFileName());
		return this.fileService.createFile(email,fileNew,newfile);

	}
	
	@GetMapping("/file/list/{email}")
	public List<File> listFile(@PathVariable("email") String email)
	{
		return this.fileService.listFileByUser(email);
	}
	

	@PutMapping("/file/update/{fileIdentifier}")
	public File updateFile(@PathVariable("fileIdentifier") Integer fileIdentifier, @RequestBody File file)
	{
		return this.fileService.updateFile(file, fileIdentifier);
	}
	
	@DeleteMapping("/file/delete/{fileIdentifier}")
	public void deleteFile(@PathVariable("fileIdentifier") Integer fileIdentifier,@RequestParam String fileName)
	{
		this.fileService.deleteFile(fileIdentifier,fileName);
	}

	
	@GetMapping("/file/downloadFile/{filename}")
	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable("filename") String fileName)
			throws IOException { 
		return this.fileService.downloadFile(fileName);
	}
	
	
	/*  ------ File Tag ----*/
	@PostMapping("/create/filetag/{fileIdentifier}/{tagName}")
	public FileTag createFileTag( @PathVariable("fileIdentifier") Integer fileIdentifier,@PathVariable("tagName") String tagName)
	{
		
		return this.filetagService.createFileTag(fileIdentifier, tagName);
	}
	
	@GetMapping("/list/filetag/id/{fileIdentifier}")
	public List<String> listFileTagById(@PathVariable("fileIdentifier") Integer fileIdentifier)
	{
		return this.filetagService.listFileTagById(fileIdentifier);
	}
	
	@GetMapping("/list/filetag/tagname/{tagName}")
	public List<Integer> listFileTagByTagName(@PathVariable("tagName") String tagName)
	{
		return this.filetagService.listFileTagByName(tagName);
	}
	
	@DeleteMapping("/delete/filetag/{tagName}/{fileIdentifier}")
	public void deleteFileTag(@PathVariable("tagName") String tagName,@PathVariable("fileIdentifier") Integer fileIdentifier)
	{
		this.filetagService.deleteFileTag(tagName,fileIdentifier);
	}
}
