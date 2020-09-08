package com.java.nikitchem.daoImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.java.nikitchem.dao.UserDao;
import com.java.nikitchem.exception.ResourceNotFoundException;
import com.java.nikitchem.model.OtpRequest;
import com.java.nikitchem.model.User;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public User getUserByPhone(String phoneNumber) throws ResourceNotFoundException {
		List<User> userList = sessionFactory.getCurrentSession().createQuery("From User where phoneNumber=:phoneNumber")
				.setParameter("phoneNumber", phoneNumber).getResultList();
		if (userList.size() > 0) {
			return userList.get(0);
		} else {
			throw new ResourceNotFoundException("Phone number is not associated with any User.");
		}
	}
	
	@Override
	public User getUserById(int id) {
		List<User> userList = sessionFactory.getCurrentSession().createQuery("From User where id=:id")
				.setParameter("id", id).getResultList();
		if (userList.size() > 0) {
			return userList.get(0);
		} 
		return null;
	}
}
