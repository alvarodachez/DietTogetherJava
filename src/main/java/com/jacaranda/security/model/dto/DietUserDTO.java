package com.jacaranda.security.model.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.jacaranda.security.model.DietRole;

@JsonInclude(Include.NON_NULL)
public class DietUserDTO {

	private String username;

	private String password;

	private Set<DietRole> roles;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<DietRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<DietRole> roles) {
		this.roles = roles;
	}
}
