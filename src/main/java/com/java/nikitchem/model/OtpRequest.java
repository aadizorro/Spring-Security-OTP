package com.java.nikitchem.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NonNull;

@Data
@Entity
public class OtpRequest {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String phoneNumber; // Destination
	private int otp;
	private long expirytime;
	

}
