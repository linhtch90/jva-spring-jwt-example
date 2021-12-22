package com.linhtch90.authexample.jwt;

import io.jsonwebtoken.Claims;

public class JWTTest {
    
	public static void main(String[] args) {
		
		// code to test generated Token
		String token= new JWTUtil().generateToken("TokenSubject");
		System.out.println("------------------------TOKEN----------------------------------------------------");
		System.out.println(token);
		System.out.println();
		System.out.println("------------------------CLAIMS----------------------------------------------------");
		
		//code to test parsed token : Claims
		
		Claims claims= new JWTUtil().getClaims(token);
		
		System.out.println("Token ID: "+claims.getId());
		System.out.println("Token Subject: "+claims.getSubject());
		System.out.println("Token Issuer: "+claims.getIssuer());
		System.out.println("Token Issue Date: "+claims.getIssuedAt());
		System.out.println("Token Expiration Date: "+claims.getExpiration());
		System.out.println("Token Audience: "+claims.getAudience());
	}
}
