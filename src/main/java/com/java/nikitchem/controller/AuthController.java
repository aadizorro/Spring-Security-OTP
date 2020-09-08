package com.java.nikitchem.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.java.nikitchem.dto.AuthRequest;
import com.java.nikitchem.dto.AuthResponse;
import com.java.nikitchem.exception.OtpException;
import com.java.nikitchem.exception.ResourceNotFoundException;
import com.java.nikitchem.service.OtpService;

@CrossOrigin
@RestController
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private OtpService otpService;

	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public ResponseEntity<Object> generateOtp(@RequestBody Map<String, String> mobile) {

		boolean status = otpService.sendOtp(mobile.get("mobile"));
		System.out.println(mobile.get("mobile"));
		if (status) {
			return new ResponseEntity<>("OTP is sent successfully", HttpStatus.OK);
		}
		return new ResponseEntity<Object>(null, HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<AuthResponse> verifyOtp(@RequestBody AuthRequest authRequest) {
		
		try {
			return new ResponseEntity<AuthResponse>(otpService.verifyAuthRequest(authRequest), HttpStatus.OK);
			
		} catch (OtpException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<AuthResponse>(HttpStatus.FORBIDDEN);
			
		} catch (ResourceNotFoundException e) {
			
			logger.error(e.getMessage());
			return new ResponseEntity<AuthResponse>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello(){
		return "JWT working in action";
	}

}
