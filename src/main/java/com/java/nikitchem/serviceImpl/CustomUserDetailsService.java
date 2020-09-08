package com.java.nikitchem.serviceImpl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.java.nikitchem.dao.UserDao;
import com.java.nikitchem.exception.ResourceNotFoundException;
import com.java.nikitchem.model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
		
		User user;
		try {
			user = userDao.getUserByPhone(phoneNumber);
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			throw new UsernameNotFoundException(phoneNumber);
		}
		return new org.springframework.security.core.userdetails.User(user.getPhoneNumber(),null, new ArrayList<>());
	}

}
