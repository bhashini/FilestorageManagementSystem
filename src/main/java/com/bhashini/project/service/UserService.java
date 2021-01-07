package com.bhashini.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import com.bhashini.project.jpa.User;
import com.bhashini.project.repository.FileRepository;
import com.bhashini.project.repository.UserRepository;


@RestController
public class UserService {
	
	String loginPw;
	
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	FileRepository fileRepository;

	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncorder;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public User createUser(User user) {
		User newUser = new User();
		if(this.userRepository.findById(user.getEmail()).isPresent())
		{
			newUser = null;
			
		}
		else {
		
		 user.setPassword(bcryptPasswordEncorder.encode(user.getPassword()));
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
	
	public User loginUser(String email, String password) {
		
		if(this.userRepository.findById(email).isPresent())
		{
			this.loginPw =	this.userRepository.findById(email).get().getPassword();
		
			if(bcryptPasswordEncorder.matches(password, this.loginPw))
			{
				return this.userRepository.findById(email).get();
			}
			else {
				return null;
			}
			
		}
		else {
		 return null;
		}
		
	}


	@Transactional(propagation = Propagation.REQUIRED)
	public User updateUser(User newUser, String email) {

		return this.userRepository.findById(email).map(user -> {

			
			user.setName(newUser.getName());
			user.setPassword(bcryptPasswordEncorder.encode(newUser.getPassword()));
			user.setBname(newUser.getBname());

			return this.userRepository.save(user);
		}).orElse(null);

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteUser(String email) {
		this.userRepository.deleteById(email);
	}

		

}
