package com.jacaranda.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jacaranda.model.DietAthlete;
import com.jacaranda.model.DietFriendRequest;
import com.jacaranda.model.dto.DietAthleteDTO;
import com.jacaranda.services.impl.DietAthleteServiceImpl;

@RestController
@RequestMapping("/athlete")
@CrossOrigin(origins = "*")
public class DietAthleteController {

	@Autowired
	DietAthleteServiceImpl athleteService;
	
	@GetMapping("/{username}")
	public ResponseEntity<DietAthlete> getAthlete(@PathVariable String username){
		return ResponseEntity.status(HttpStatus.OK).body(athleteService.getAthlete(username));
	}

	@PostMapping("/sign-up-data/{username}")
	public ResponseEntity<DietAthlete> signUpPrincipalData(@PathVariable String username,
			@RequestBody DietAthleteDTO athleteDTO) {

		return ResponseEntity.status(HttpStatus.CREATED).body(athleteService.signUpPrincipalData(username, athleteDTO));

	}
	
	@PostMapping("/send-friend-request/{claimant}&&{requested}")
	public ResponseEntity<DietFriendRequest> sendFriendRequest(@PathVariable("claimant") String claimantUsername, @PathVariable("requested") String requestedUsername){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(athleteService.sendFriendRequest(claimantUsername, requestedUsername));
	}
	
	@PostMapping("/accept-friend-request/{id}")
	public ResponseEntity<DietFriendRequest> acceptFriendRequest(@PathVariable("id") String id){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(athleteService.acceptFriendRequest(Long.valueOf(id)));
	}
	
	@PostMapping("/reject-friend-request/{id}")
	public ResponseEntity<DietFriendRequest> rejectFriendRequest(@PathVariable("id") String id){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(athleteService.rejectFriendRequest(Long.valueOf(id)));
	}
	
	@GetMapping("/get-friends/{username}")
	public ResponseEntity<List<String>> getAthleteFriends(@PathVariable("username")String username){
		
		return ResponseEntity.status(HttpStatus.OK).body(athleteService.getAthleteFriends(username));
	}
	
	@GetMapping("/get-friends-request/{username}")
	public ResponseEntity<List<DietFriendRequest>> getFriendsRequests(@PathVariable("username")String username){
		
		return ResponseEntity.status(HttpStatus.OK).body(athleteService.getFriendsRequests(username));
	}
	
	@GetMapping("/get-athletes-by-initials/{initials}")
	public ResponseEntity<List<String>> getAthletesByInitials(@PathVariable("initials")String initials){
		return ResponseEntity.status(HttpStatus.OK).body(athleteService.getAthletesByInitials(initials));
	}
}
