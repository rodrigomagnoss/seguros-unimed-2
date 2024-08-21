package br.com.segurosunimed.gestaocarteiras.commons.service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenService {

	public static final String AUTHORIZATION = "Authorization";
	public static final long EXPIRATION_TIME = 8 * 60 * 60 * 1000; // 8 horas
	public static final String AUTHORITIES = "authorities";
    public static final String TOKEN_TYPE = "Bearer ";
    public static final String GC_SESSION_ID = "gc-session-id";
    public static final String MICROSERVICE = "microservice";
    public static final String LOGGED_USER = "logged-user";
    
    @Value("${security.jwt.secret}")
    protected String gestaoJwtSecret;

    public String createToken(Map<String, Object> claims, String username) {

        return Jwts.builder().
                setClaims(claims).
                setSubject(username).
                setIssuedAt(new Date(System.currentTimeMillis())).
                setExpiration(new Date(System.currentTimeMillis() + +EXPIRATION_TIME)).
                signWith(SignatureAlgorithm.HS256, gestaoJwtSecret).compact();
    }

    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {

        UsernamePasswordAuthenticationToken result = null;
        String authorization = request.getHeader(AUTHORIZATION);
        String usuario = "";
        
        if (StringUtils.isNotBlank(authorization) && StringUtils.contains(authorization, TOKEN_TYPE)) {
            String tokenWithoutBearer = authorization.replace(TOKEN_TYPE, "");

            Claims body = Jwts.parser()
                    .setSigningKey(gestaoJwtSecret)
                    .parseClaimsJws(tokenWithoutBearer)
                    .getBody();

//            Claims body = Jwts.parser()
//            		.setSigningKey(gestaoJwtSecret).build()
//            		.parseSignedClaims(tokenWithoutBearer).getPayload();
            
            usuario = body.getSubject();
            
            String gcSessionId = new String(Hex.encodeHexString(tokenWithoutBearer.getBytes()).getBytes(),Charsets.UTF_8);
            
            ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(request);
//            servletRequestAttributes.setAttribute(GC_SESSION_ID, DigestUtils.sha1Hex(tokenWithoutBearer), 0);
            servletRequestAttributes.setAttribute(GC_SESSION_ID, gcSessionId, 0);
            servletRequestAttributes.setAttribute(LOGGED_USER, usuario, 0);
            servletRequestAttributes.setAttribute(MICROSERVICE, request.getContextPath().substring(1), 0);

            RequestContextHolder.setRequestAttributes(servletRequestAttributes, true);

            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            if (null != body.get(AUTHORITIES)) {
                Map<String, List<String>> authorities = (Map<String, List<String>>) body.get(AUTHORITIES);
                for (Map.Entry<String, List<String>> entry : authorities.entrySet()) {
                    for (String scope : entry.getValue()) {
                        grantedAuthorities.add(new SimpleGrantedAuthority(entry.getKey() + "|" + scope));
                    }
                }
            }
            if (usuario != null) {
                result = new UsernamePasswordAuthenticationToken(usuario, null, grantedAuthorities);
            }
            
        }else {
        
    		//Gerar credenciais para acessos internos de webservices
    		if(StringUtils.contains(authorization, "WS_INTERNAL")) {
        	    
    			String base64Credentials = authorization.substring("Basic WS_INTERNAL".length()).trim();
        	    byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        	    String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        	    // credentials = username:password
        	    final String[] credentialsValues = credentials.split(":", 2);
        	    
        	    usuario = credentialsValues[0]; 
        	    String password = credentialsValues[0];
                List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                grantedAuthorities.add(new SimpleGrantedAuthority("WS_INTERNAL_ROLE|FULL_ACCESS"));
        	    
                if (usuario != null) {
                    result = new UsernamePasswordAuthenticationToken(usuario, password, grantedAuthorities);
                }
    		}
        }

        return result;
    }
}
