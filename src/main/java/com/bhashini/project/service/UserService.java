package com.bhashini.project.service;




import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;


import com.bhashini.project.jpa.User;
import com.bhashini.project.repository.FileRepository;
import com.bhashini.project.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@RestController
public class UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	FileRepository fileRepository;

	
	@Transactional(propagation = Propagation.REQUIRED)
	public User createUser(User user) {
		User newUser = new User();
		if(this.userRepository.findById(user.getEmail()).isPresent())
		{
			newUser = null;
			
		}
		else {
		 newUser = user; 
		 this.userRepository.save(user);
		
		}
		return newUser;
	}
	

	
	
	//ERROR - handle when the user is not exits
	@Transactional(readOnly = true)
	public User findUser(String email) throws Exception {
		User findUser = new User();
		if(this.userRepository.findById(email).isPresent())
		{
			findUser = this.userRepository.findById(email).get();
			
		}
		else {
		 
		 findUser=null;
		
		}
		return findUser;
		
	}


	@Transactional(propagation = Propagation.REQUIRED)
	public User updateUser(User newUser, String email) {

		return this.userRepository.findById(email).map(user -> {

			
			user.setName(newUser.getName());
			user.setPassword(newUser.getPassword());
			user.setBname(newUser.getBname());

			return this.userRepository.save(user);
		}).orElse(null);

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteUser(String email) {
		this.userRepository.deleteById(email);
	}

		

}
