package br.com.segurosunimed.gestaocarteiras.commons.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.segurosunimed.gestaocarteiras.commons.dto.response.ErrorDTO;
import br.com.segurosunimed.gestaocarteiras.commons.service.JwtTokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtAuthenticationFilter extends GenericFilterBean {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            Authentication auth = jwtTokenService.getAuthentication((HttpServletRequest) request, (HttpServletResponse) response);
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException e) {
            HttpServletResponse hsr = (HttpServletResponse) response;
            hsr.setStatus(HttpStatus.FORBIDDEN.value());
            hsr.setContentType("application/json");
            List<ErrorDTO> errors = new ArrayList<>();
            errors.add(new ErrorDTO("token_is_not_valid", "Seu login expirou, por favor efetue o login novamente!"));
            hsr.getWriter().write(mapper.writeValueAsString(errors));
            hsr.getWriter().flush();
            hsr.getWriter().close();
        }
    }
}
