package com.linhtch90.authexample.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {
    @Value("${app.secret.key}")
	private String secret_key;
    
    // code to generate Token
	public String generateToken(String subject) {
		
		return Jwts.builder()
				.setId("tk9931")
				.setSubject(subject)
				.setIssuer("ABC_Ltd")
				.setAudience("XYZ_Ltd")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1)))
				.signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encode(secret_key.getBytes()))
				.compact();
	}
	
	//code to get Claims
	public Claims getClaims(String token) {
		
		return Jwts.parser()
				.setSigningKey(Base64.getEncoder().encode(secret_key.getBytes()))
				.parseClaimsJws(token)
				.getBody();
	}

	// check if token is valid
	public boolean isValidToken(String token) {
		return getClaims(token).getExpiration().after(new Date(System.currentTimeMillis()));
	}

	// check if token is valid as per username
	public boolean isValidToken(String token, String username) {
		String tokenUsername = getClaims(token).getSubject();
		return (username.equals(tokenUsername) && !isTokenExpired(token));
	}

	// check if token is expired
	public boolean isTokenExpired(String token) {
		return getClaims(token).getExpiration().before(new Date(System.currentTimeMillis()));
	}

	//code to get expiration date
	public Date getExpirationDate(String token) {
		return getClaims(token).getExpiration();
	}
	
	//code to get expiration date
	public String getSubject(String token) {
		return getClaims(token).getSubject();
	}
}
