package com.jacaranda.model.dto;

import java.util.Set;

import com.jacaranda.security.model.DietRole;

public class DietAthleteRankingDto {

	private String name;
	
	private Double gamePoints;
	
	private String username;
	
	private  Set<DietRole> roles;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the gamePoints
	 */
	public Double getGamePoints() {
		return gamePoints;
	}

	/**
	 * @param gamePoints the gamePoints to set
	 */
	public void setGamePoints(Double gamePoints) {
		this.gamePoints = gamePoints;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the roles
	 */
	public Set<DietRole> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<DietRole> roles) {
		this.roles = roles;
	}
	
	
}
