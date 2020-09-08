package com.java.nikitchem.dao;

import com.java.nikitchem.model.OtpRequest;

public interface OtpDao {
	public int saveGeneratedOtp(OtpRequest otpRequest);
	public OtpRequest verifyOtp(OtpRequest otpRequest);
}
