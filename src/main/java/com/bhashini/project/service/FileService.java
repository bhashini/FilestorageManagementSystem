package com.bhashini.project.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.lang.Object;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;
import com.bhashini.project.jpa.File;
import com.bhashini.project.repository.FileRepository;
import com.bhashini.project.repository.FileTagRepository;
import com.bhashini.project.repository.UserRepository;


@RestController
public class FileService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	FileRepository fileRepository;
	
	@Autowired
	FileTagRepository filetagRepository;
	
	@Autowired
	FileTagService filetagService;
	
	private AmazonS3 s3Client;

	@Value("${amazonProperties.bucketName}")
	private String bucketName;
	@Value("${amazonProperties.accessKey}")
	private String accessKey;
	@Value("${amazonProperties.secretKey}")
	private String secretKey;
	
	@PostConstruct
	private void initializeAmazon() {
		AWSCredentials awscredintials = new BasicAWSCredentials(this.accessKey, this.secretKey);

		s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awscredintials))
				.withRegion(Regions.CA_CENTRAL_1).build();
	}


	
	public ResponseEntity<ByteArrayResource> downloadFile(String fileName) throws IOException {
		byte[] content = null;
		final S3Object s3Object = this.s3Client.getObject(this.bucketName, fileName);
		final S3ObjectInputStream stream = s3Object.getObjectContent();
		try {
			content = IOUtils.toByteArray(stream);
			s3Object.close();
		} catch (IOException ex) {
			System.out.println("IO Error message: " + ex);
		}

		final ByteArrayResource resource = new ByteArrayResource(content);
		return ResponseEntity.ok().contentLength(content.length).header("Content-type", "application/octet-stream")
				.header("Content-disposition", "attachment; filename=\"" + fileName + "\"").body(resource);
	}




	public String generateFileName(MultipartFile multiPart) {
		return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
	}

	public java.io.File convertMultiPartToFile(MultipartFile file) throws IOException {
		java.io.File convFile = new java.io.File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	

	
	@Transactional(propagation = Propagation.REQUIRED)
	public File createFile(String email, File newFile, MultipartFile multipartFile)throws IOException {
		java.io.File file = convertMultiPartToFile(multipartFile);
		String files3Name = generateFileName(multipartFile);
		newFile.setS3fileName(files3Name);

		s3Client.putObject(new PutObjectRequest(this.bucketName, files3Name, file));
		System.out.println("File upload is successful");
		System.out.println("File Name in fileservice:"+newFile.getFileName());
		return this.userRepository.findById(email).map(user -> {
			newFile.setUser(user);
			return fileRepository.save(newFile);
		}).orElseThrow();
	}
	
//	public List<String> listFile() {
//		List<String> keys = new ArrayList<>();
//		ObjectListing ol = s3Client.listObjects(this.bucketName);
//		List<S3ObjectSummary> files = ol.getObjectSummaries();
//		for (S3ObjectSummary os : files) {
//			keys.add(os.getKey());
//		}
//		return keys;
//	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<File> listFileByUser(String email)
	{
				
		List<File> newList = new ArrayList<>();
		List<File> fileList =  this.fileRepository.findAll();
		for(File f : fileList)
		{
			if(f.getUser() == this.userRepository.findById(email).orElseThrow() )
			{
				newList.add(f);
			
			}
		}
		return newList;
	}
	
	public List<File> listFiles()
	{
		List<File> fileList =  this.fileRepository.findAll(Sort.by("fileIdentifier"));
		
		return fileList;
	} 
	

	@Transactional(propagation = Propagation.REQUIRED)
	public File updateFile(File newFile, Integer fileIdentifier)
	{
		return this.fileRepository.findById(fileIdentifier).map(file -> {
			file.setFileName(newFile.getFileName());
			file.setLocation(newFile.getLocation());
			file.setUser(newFile.getUser());
			
			return this.fileRepository.save(file);
		}).orElseThrow();
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteFile(Integer fileIdentifier, String filename)
	{
		String s3fileName = "";
		File file = this.fileRepository.findById(fileIdentifier).orElseThrow();
		s3fileName = file.getS3fileName();
		try {
			s3Client.deleteObject(this.bucketName, s3fileName);
		} catch (AmazonServiceException ex) {
			System.out.println(ex.getErrorMessage());
			System.exit(1);
		}
		
		this.fileRepository.deleteById(fileIdentifier);
	}
	
	
}
