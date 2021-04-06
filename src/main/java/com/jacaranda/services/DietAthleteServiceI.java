package com.jacaranda.services;

import java.util.List;

import com.jacaranda.exceptions.DietRequestException;
import com.jacaranda.model.DietAthlete;
import com.jacaranda.model.DietFriendRequest;
import com.jacaranda.model.dto.DietAthleteDTO;
import com.jacaranda.model.dto.DietAthleteRankingDto;

public interface DietAthleteServiceI {

	public DietAthlete getAthlete(String username);

	public DietAthlete signUpPrincipalData(String username, DietAthleteDTO athleteDto);

	public DietFriendRequest sendFriendRequest(String claimantUsername, String requestedUsername)
			throws DietRequestException;

	public DietFriendRequest acceptFriendRequest(Long id);

	public DietFriendRequest rejectFriendRequest(Long id);

	public List<String> getAthleteFriends(String username);

	public List<DietFriendRequest> getFriendsRequests(String username);

	public List<String> getAthletesByInitials(String initials);

	public DietAthleteRankingDto getAthleteRanking(String username);
}
