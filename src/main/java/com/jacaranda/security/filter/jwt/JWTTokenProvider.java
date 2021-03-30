package com.jacaranda.security.filter.jwt;

import static com.jacaranda.security.common.SecurityConstants.EXPIRATION_TIME;

import java.util.Date;

import javax.crypto.SecretKey;

import com.jacaranda.security.common.SecurityConstants;
import com.jacaranda.security.model.DietUser;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JWTTokenProvider {

	private static SecretKey key; 
	
	public static String generateToken(DietUser user) {
        return Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.setSubject(user.getId().toString())
				.claim("expirationTime", EXPIRATION_TIME)
				.setId(user.getId().toString())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.claim("roles", user.getRoles())
//				.claim("updateTime", user.getUpdateTime())//Example
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(getKey(), SignatureAlgorithm.HS512)
				.compact();
	}
	
	
	public static boolean validateToken(String token) {
		boolean valid = false;
		try {
			Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token);
			valid = true;
		}catch (Exception e) {
			//ToDo report on error type
		}
		return valid;
	}
	
	public static SecretKey getKey() {
		if(key== null) {
			key = Keys.hmacShaKeyFor(SecurityConstants.SECRET.getBytes());
		}
		return key;
	}
	
}
