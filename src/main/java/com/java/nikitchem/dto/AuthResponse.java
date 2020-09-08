package com.java.nikitchem.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AuthResponse {
	private String accessToken;

	public AuthResponse(String accessToken) {
		this.accessToken = accessToken;
	}
}
