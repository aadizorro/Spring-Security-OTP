package com.java.nikitchem.utilities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class TwilioConfig {
	
	@Value("${twilio.account_sid}")
	private String ACCOUNT_SID;
	
	@Value("${twilio.auth_token}")
	private String AUTH_TOKEN;
	
	@Value("${twilio.trial_number}")
	private String TRIAL_NUMBER;

}
