package com.jacaranda.model.dto;

import java.util.Set;

import com.jacaranda.model.DietUserRole;


public class DietUserDTO {

private String username;
	
	private String password;
	
	private Set<DietUserRole> roles;

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

	public Set<DietUserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<DietUserRole> roles) {
		this.roles = roles;
	}
}
