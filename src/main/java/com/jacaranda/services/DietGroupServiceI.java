package com.jacaranda.services;

import java.util.List;

import com.jacaranda.model.DietGroup;
import com.jacaranda.model.DietGroupRequest;

public interface DietGroupServiceI {

	public DietGroup createGroup(String username, DietGroup group);

	public DietGroupRequest sendGroupRequest(String claimantUsername, String requestedUsername);

	public DietGroupRequest acceptGroupRequest(Long id);

	public DietGroupRequest rejectGroupRequest(Long id);
	
	public List<DietGroupRequest> getGroupRequests(String username);
}
