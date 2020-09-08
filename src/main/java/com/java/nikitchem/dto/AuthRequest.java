package com.java.nikitchem.dto;

import lombok.Data;

@Data
public class AuthRequest {
	
	private String phoneNumber;
	private int otp;

}
