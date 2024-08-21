package br.com.segurosunimed.gestaocarteiras.commons.audit;

import java.io.IOException;
import java.util.Base64;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuditLoggedUser {

	
	public static Authentication getLoggedUser(){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication;
	}
	
	public static String getLoggedUserName(){
		
		Authentication authentication = getLoggedUser();
		String currentPrincipalName = authentication.getName();
		return currentPrincipalName;
	}
	

	public static String getUserType(){
		
		String userType = getTokenAttributeAsString("type");
		
		return userType;
	}

	public static String getUserToken(){
		
		String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
		return token;
	}

	public static Object getTokenAttribute(String attribute){

		String token = getUserToken();
		return getTokenAttribute(token, attribute);
		
	}

	public static String getTokenAttributeAsString(String attribute){

		String token = getUserToken();
		return (String) getTokenAttributeAsString(token, attribute);
		
	}

	public static Object getTokenAttribute(String token, String attribute){
		
		JsonNode tokenAttributes = getTokenAttributes(token);
		Object tokenAttribute = tokenAttributes.path(attribute);
		return tokenAttribute;

	}

	public static String getTokenAttributeAsString(String token, String attribute){
		
		JsonNode tokenAttributes = getTokenAttributes(token);
		String tokenAttribute = tokenAttributes.path(attribute).asText();
		return tokenAttribute;
	}

	public static JsonNode getTokenAttributes(String token){
		
		JsonNode tokenAttributes = null;
		try {
			String[] chunks = token.split("\\.");
			Base64.Decoder decoder = Base64.getUrlDecoder();
			ObjectMapper mapper = new ObjectMapper();
			tokenAttributes = mapper.readTree(decoder.decode(chunks[1]));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tokenAttributes;
	}

}
