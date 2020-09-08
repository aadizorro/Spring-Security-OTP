package com.java.nikitchem.dao;

import com.java.nikitchem.exception.ResourceNotFoundException;
import com.java.nikitchem.model.OtpRequest;
import com.java.nikitchem.model.User;

public interface UserDao {
	public User getUserByPhone(String phoneNumber) throws ResourceNotFoundException;

	User getUserById(int id);
}
