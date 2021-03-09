package com.jacaranda.services;

import java.util.List;

import com.jacaranda.exceptions.DietGroupException;
import com.jacaranda.model.DietGroup;
import com.jacaranda.model.DietGroupRequest;
import com.jacaranda.model.dto.DietProgressBarDto;

public interface DietGroupServiceI {

	public DietGroup createGroup(String username, DietGroup group) throws DietGroupException;

	public DietGroupRequest sendGroupRequest(String claimantUsername, String requestedUsername);

	public DietGroupRequest acceptGroupRequest(Long id) throws DietGroupException;

	public DietGroupRequest rejectGroupRequest(Long id);
	
	public List<DietGroupRequest> getGroupRequests(String username);
	
	public DietProgressBarDto getProgressBar(String username);
}
