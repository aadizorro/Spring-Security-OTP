package com.java.nikitchem.utilities;

import java.util.ArrayList;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class OneTimePasswordAuthenticationToken extends AbstractAuthenticationToken{

	

	public OneTimePasswordAuthenticationToken(String phoneNumber, int otp) {
		super(new ArrayList<>());
	}

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}

}
