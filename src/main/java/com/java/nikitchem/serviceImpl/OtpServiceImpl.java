package com.java.nikitchem.serviceImpl;

import java.net.HttpURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.nikitchem.dao.OtpDao;
import com.java.nikitchem.dao.UserDao;
import com.java.nikitchem.dto.AuthRequest;
import com.java.nikitchem.dto.AuthResponse;
import com.java.nikitchem.exception.OtpException;
import com.java.nikitchem.exception.ResourceNotFoundException;
import com.java.nikitchem.model.OtpRequest;
import com.java.nikitchem.model.User;
import com.java.nikitchem.service.OtpService;
import com.java.nikitchem.utilities.OneTimePasswordAuthenticationToken;
import com.java.nikitchem.utilities.SmsConfig;
import com.java.nikitchem.utilities.TwilioConfig;

@Service
public class OtpServiceImpl implements OtpService {

	@Autowired
	private SmsConfig smsConfig;

	@Autowired
	private OtpDao otpDao;
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private TokenProvider tokenProvider;

	@Override
	public boolean sendOtp(String phoneNumber) {
		int otp = ((int) (Math.random() * (10000 - 1000))) + 1000;
		HttpURLConnection connection = null;
		int otpMessage = otp;
		if (isPhoneNumberValid(phoneNumber)) {
			try {
				/*
				 * URL url = new
				 * URL(String.format(smsConfig.getSMS_URL(),smsConfig.getSMS_USERNAME(),
				 * smsConfig.getSMS_PASSWORD(),smsConfig.getSMS_SENDER_ID(),phoneNumber,
				 * otpMessage)); connection = (HttpURLConnection) url.openConnection();
				 * connection.setUseCaches(false); connection.setDoOutput(true); //Send Request
				 * DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
				 * dos.close();
				 */

				OtpRequest otpRequest = new OtpRequest();
				otpRequest.setPhoneNumber(phoneNumber);
				otpRequest.setOtp(otp);
				otpRequest.setExpirytime(System.currentTimeMillis() + 600000);

				int id = otpDao.saveGeneratedOtp(otpRequest);
				if (id > 0) {
					return true;
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		/*
		 * // Receive Response InputStream is = connection.getInputStream();
		 * BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		 * StringBuilder response = new StringBuilder(); String line; while ((line =
		 * rd.readLine()) != null) { response.append(line); response.append('\r'); }
		 * rd.close(); System.out.println(response.toString());
		 * 
		 * 
		 * } catch (MalformedURLException e) {
		 * 
		 * e.printStackTrace(); } catch (IOException e) {
		 * 
		 * e.printStackTrace(); } finally { if (connection != null) {
		 * connection.disconnect(); } }
		 */

		return false;

	}

	@Override
	public AuthResponse verifyOtpRequest(Object obj) throws OtpException, ResourceNotFoundException {
		System.out.println(obj);

		return null;
	}

	@Override
	public AuthResponse verifyAuthRequest(AuthRequest authRequest) throws OtpException, ResourceNotFoundException {
		OtpRequest otpRequest = new OtpRequest();
		otpRequest.setPhoneNumber(authRequest.getPhoneNumber());
		otpRequest.setOtp(authRequest.getOtp());
		System.out.println(otpRequest.toString());
		OtpRequest otpInfo = otpDao.verifyOtp(otpRequest);

		if (otpInfo != null) {
			if (otpRequest.getOtp() == otpInfo.getOtp()) {
				{
					User user = userDao.getUserByPhone(otpRequest.getPhoneNumber());
					return new AuthResponse(tokenProvider.createTokenForUser(user));
				}
			} else {
				throw new OtpException("Invalid OTP");
			}
		}
		return null;

	}

	private final TwilioConfig twilioConfig;

	@Autowired
	public OtpServiceImpl(TwilioConfig twilioConfig) {
		this.twilioConfig = twilioConfig;
	}

	/*
	 * @Override public void sendOtp(OtpRequest otpRequest) {
	 * 
	 * if (isPhoneNumberValid(otpRequest.getPhoneNumber())) { PhoneNumber to = new
	 * PhoneNumber(otpRequest.getPhoneNumber()); PhoneNumber from = new
	 * PhoneNumber(twilioConfig.getTRIAL_NUMBER()); String message =
	 * otpRequest.getOtp();
	 * 
	 * MessageCreator creator = Message.creator(to, from, message);
	 * creator.create();
	 * 
	 * } else { throw new IllegalArgumentException("Phone Number [" +
	 * otpRequest.getPhoneNumber() + "] is not valid."); }
	 * 
	 * 
	 * }
	 * 
	 * public boolean sendOtp(String phoneNumber) { try {
	 * 
	 * 
	 * 
	 * OtpRequest otpRequest = new OtpRequest();
	 * otpRequest.setPhoneNumber("+91"+phoneNumber);
	 * otpRequest.setOtp(String.valueOf(((int) (Math.random() * (10000 - 1000))) +
	 * 1000)); otpRequest.setExpirytime(System.currentTimeMillis() + 600000);
	 * 
	 * Message.creator(new PhoneNumber(otpRequest.getPhoneNumber()), new
	 * PhoneNumber(twilioConfig.getTRIAL_NUMBER()), "Your OTP is " +
	 * otpRequest.getOtp()).create();
	 * 
	 * 
	 * return true; } catch (Exception e) { System.out.println(e); return false; } }
	 * 
	 */
	private boolean isPhoneNumberValid(String phoneNumber) {
		// TODO: Implement Phone Number validator using google library.
		return true;
	}

}
