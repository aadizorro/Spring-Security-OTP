package com.java.nikitchem.utilities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class SmsConfig {
	
	@Value("${sms.username}")
	private String SMS_USERNAME;
	
	@Value("${sms.password}")
	private String SMS_PASSWORD;
	
	@Value("${sms.sender_id}")
	private String SMS_SENDER_ID;
	
	private String SMS_URL = "http://www.smsjust.com/sms/user/urlsms.php?username=%s&pass=%s&senderid=%s&dest_mobileno=%s&message=Login_OTP:%d&response=Y";

}
