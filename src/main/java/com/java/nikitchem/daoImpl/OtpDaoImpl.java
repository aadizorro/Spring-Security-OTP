package com.java.nikitchem.daoImpl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.java.nikitchem.dao.OtpDao;
import com.java.nikitchem.model.OtpRequest;

@Repository
@Transactional
public class OtpDaoImpl implements OtpDao {

	@Autowired
	SessionFactory sessionFactory;


	@Override
	public OtpRequest verifyOtp(OtpRequest otpRequest) {
		OtpRequest request = (OtpRequest) sessionFactory.getCurrentSession().createQuery("From OtpRequest where phoneNumber = :phoneNumber").setParameter("phoneNumber", otpRequest.getPhoneNumber()).getResultList().get(0);
		
		if(request != null) {
			return request;
		}
		return null;
	}
	
	@Override
	public int saveGeneratedOtp(OtpRequest otpRequest) {
		
		int id = (int)sessionFactory.getCurrentSession().save(otpRequest);
		System.out.println(id);
		return id;
	}

}
