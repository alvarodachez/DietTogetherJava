package com.jacaranda.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.jacaranda.security.common.SecurityConstants;
import com.jacaranda.security.filter.JWTAuthenticationFilter;
import com.jacaranda.security.filter.JWTAuthorizationFilter;
import com.jacaranda.security.model.DietRole;
import com.jacaranda.security.services.impl.DietUserServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JWTAuthorizationFilter jwtAuthorizationFilter;

	@Autowired
	private DietUserServiceImpl userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable().authorizeRequests()
				.antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
				.antMatchers(HttpMethod.POST, SecurityConstants.LOG_IN).permitAll()
				.antMatchers(HttpMethod.GET, "/athlete/*").permitAll()
				.antMatchers(HttpMethod.POST, "/athlete/sign-up-data/*").permitAll()
				.antMatchers(HttpMethod.POST, "/athlete/send-friend-request/*").hasRole(DietRole.USER.name())
				.antMatchers(HttpMethod.POST, "/athlete/accept-friend-request/*").hasRole(DietRole.USER.name())
				.antMatchers(HttpMethod.POST, "/athlete/reject-friend-request/*").hasRole(DietRole.USER.name())
				.antMatchers(HttpMethod.POST, "/group/create-group/*").hasRole(DietRole.USER.name())
				.antMatchers(HttpMethod.POST, "/group/send-group-request/*").hasRole(DietRole.GROUP_MANAGER.name())
				.antMatchers(HttpMethod.POST, "/group/accept-group-request/*").hasRole(DietRole.USER.name())
				.antMatchers(HttpMethod.POST, "/group/reject-group-request/*").hasRole(DietRole.USER.name())
				.antMatchers(HttpMethod.POST, "/group/get-group-request/*").hasRole(DietRole.USER.name())
				.antMatchers(HttpMethod.POST, "/register/create-register/*").hasRole(DietRole.USER.name())
				.antMatchers(HttpMethod.GET, "/register/get-registers/*").hasRole(DietRole.USER.name())
				.and().addFilter(new JWTAuthenticationFilter(authenticationManagerBean()))
				.addFilterBefore(jwtAuthorizationFilter, BasicAuthenticationFilter.class).sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
	}

}
