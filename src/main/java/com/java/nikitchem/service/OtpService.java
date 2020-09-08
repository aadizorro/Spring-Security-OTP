package com.java.nikitchem.service;

import java.util.Map;

import com.java.nikitchem.dto.AuthRequest;
import com.java.nikitchem.dto.AuthResponse;
import com.java.nikitchem.exception.OtpException;
import com.java.nikitchem.exception.ResourceNotFoundException;

public interface OtpService {
	
	public boolean sendOtp(String phoneNumber);

	AuthResponse verifyOtpRequest(Object obj) throws OtpException, ResourceNotFoundException;

	AuthResponse verifyAuthRequest(AuthRequest authRequest) throws OtpException, ResourceNotFoundException;

}
