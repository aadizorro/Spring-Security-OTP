package com.java.nikitchem.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.java.nikitchem.model.User;
import com.java.nikitchem.utilities.AuthConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class TokenProvider {

	private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

	private AuthConfig authConfig;

	public TokenProvider(AuthConfig authConfig) {
		this.authConfig = authConfig;
	}

	public String createTokenForUser(User user) {
		Map<String, Object> claims = new HashMap<>();
		return generateToken(claims, user.getPhoneNumber());

	}

	private String generateToken(Map<String, Object> claims, String phoneNumber) {
		return Jwts.builder().setClaims(claims).setSubject(phoneNumber).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 438)))
				.signWith(SignatureAlgorithm.HS256, authConfig.getTOKEN_SECRET()).compact();
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(authConfig.getTOKEN_SECRET()).parseClaimsJws(token).getBody();
	}

	public String getUserIdFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(authConfig.getTOKEN_SECRET()).parseClaimsJws(token).getBody();

		return claims.getSubject();
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token,Claims::getExpiration);
	}
	
	public String extractUserId(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}
	
	public boolean validateToken(String authToken, UserDetails userDetails) {
		try {
			Jwts.parser().setSigningKey(authConfig.getTOKEN_SECRET()).parseClaimsJws(authToken);
			final String phoneNumber = getUserIdFromToken(authToken);
			return (!isTokenExpired(authToken) && phoneNumber.equals(userDetails.getUsername()));
		} catch (SignatureException ex) {
			logger.error("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			logger.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			logger.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty.");
		}
		return false;
	}
}
