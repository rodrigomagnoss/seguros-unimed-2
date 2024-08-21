package br.com.segurosunimed.gestaocarteiras.commons.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private static final String[] AUTH_WHITELIST = {
    		// Application
    		"/**/login", 
//    		"/v2/api-docs", 
    		"/configuration/**", 
//    		"/swagger*/**", 
    		"/webjars/**",
    		
            // -- Swagger UI v2
            "/v2/api-docs",
//            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
//            "/configuration/security",
            "/swagger-ui.html",
            
            "/actuator/**",

//            // -- Swagger UI v3 (OpenAPI)
//            "/v3/api-docs/**",
//            "/swagger-ui/**"
            
            // other public endpoints of your API may be appended to this array
    };    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
//	                .cors()
//	                .and()
                .csrf()
                .disable()
                .authorizeRequests()
//                .antMatchers(HttpMethod.POST).permitAll()
//                .antMatchers(HttpMethod.GET).permitAll()
//                .antMatchers(HttpMethod.PUT).permitAll()
//                .antMatchers(HttpMethod.DELETE).permitAll()
                //.antMatchers(AUTH_WHITELIST).hasAnyAuthority("ADMIN")
                .antMatchers(AUTH_WHITELIST).permitAll()
       	     	.antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
                //.and()
                //.addFilterBefore(new JwtLoginFilter("/api/autenticacao", authenticationManager(), service, variaveisApp, serviceUser), UsernamePasswordAuthenticationFilter.class)	
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		String username = System.getenv("WS_INTERNAL_USER");
		String password = System.getenv("WS_INTERNAL_PASS");

		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth.inMemoryAuthentication().withUser(username).password(encoder.encode(password)).roles("WS_INTERNAL_ROLE");
	}

}
