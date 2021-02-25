package com.jacaranda.services.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jacaranda.common.DietImcConstants;
import com.jacaranda.model.DietAthlete;
import com.jacaranda.model.DietFriendRequest;
import com.jacaranda.model.DietGroup;
import com.jacaranda.model.DietImc;
import com.jacaranda.model.DietMailBox;
import com.jacaranda.model.DietPhysicalData;
import com.jacaranda.model.DietPrivateActivity;
import com.jacaranda.model.DietRegister;
import com.jacaranda.model.DietRequestStatus;
import com.jacaranda.model.DietScale;
import com.jacaranda.model.DietScaleImc;
import com.jacaranda.model.dto.DietAthleteDTO;
import com.jacaranda.repository.DietAthleteRepository;
import com.jacaranda.repository.DietFriendRequestRepository;
import com.jacaranda.repository.DietImcRepository;
import com.jacaranda.repository.DietMailBoxRepository;
import com.jacaranda.repository.DietPhysicalDataRepository;
import com.jacaranda.repository.DietScaleImcRepository;
import com.jacaranda.security.model.DietUser;
import com.jacaranda.security.repository.DietUserRepository;
import com.jacaranda.services.DietAthleteServiceI;

@Service("athleteService")
public class DietAthleteServiceImpl implements DietAthleteServiceI {

	@Autowired
	DietAthleteRepository athleteRepo;

	@Autowired
	DietImcRepository imcRepo;

	@Autowired
	DietPhysicalDataRepository physicalDataRepo;

	@Autowired
	DietUserRepository userRepo;

	@Autowired
	DietScaleImcRepository scaleImcRepo;

	@Autowired
	DietMailBoxRepository mailBoxRepo;

	@Autowired
	DietFriendRequestRepository friendRequestRepo;

	@Override
	public DietAthlete getAthlete(String username) {
		DietUser user = userRepo.findByUsername(username).get();

		return user.getAthleteId();
	}

	@Override
	public DietAthlete signUpPrincipalData(String username, DietAthleteDTO athleteDto) {

		/** Obtenemos usuario por nombre de usuario */
		DietUser user = userRepo.findByUsername(username).get();

		/** Creamos el atleta con el que trabajaremos */
		DietAthlete athlete = new DietAthlete();

		/** Creamos datos fisicos del atleta */
		DietPhysicalData physicalData = new DietPhysicalData();

		/** Creamos entidad imc que pertenece a los datos fisicos del atleta */
		DietImc imc = new DietImc();

		/** Creamos buzon de atleta */
		DietMailBox mailBox = new DietMailBox();

		/** Asignamos datos al atleta */
		athlete.setName(athleteDto.getName());
		athlete.setSurname(athleteDto.getSurname());
		athlete.setBirthDay(LocalDate.now());
		athlete.setGamePoints(0.0);
		athlete.setTotalPoints(0.0);
		athlete.setTotalWeightDifference(0.0);

		/** Inicializando listas del atleta */
		List<String> friends = new ArrayList<String>();
		athlete.setFriends(friends);

		List<DietGroup> groups = new ArrayList<DietGroup>();
		athlete.setGroups(groups);

		List<DietPrivateActivity> privateActivities = new ArrayList<DietPrivateActivity>();
		athlete.setPrivateActivities(privateActivities);

		/** Asignamos a los datos fisicos del atleta */
		physicalData.setWeight(athleteDto.getWeight());
		physicalData.setHeight(athleteDto.getHeight());

		/** Inicializamos lista de los datos fisicos del atleta */
		List<DietRegister> registers = new ArrayList<DietRegister>();
		physicalData.setRegisters(registers);

		/** Asogmamos a la entidad imc de los datos fisicos del atleta */
		imc.setImcValue(athleteDto.getWeight() / (athleteDto.getHeight() * athleteDto.getHeight()));

		imc.setScales(makeScales(athleteDto.getHeight()));

		imc.setActualScale(scaleCalculation(athleteDto.getWeight(), imc.getScales()));

		/** Inicializamos lista de solicitudes de amistad al atleta */
		List<DietFriendRequest> friendRequests = new ArrayList<DietFriendRequest>();
		mailBox.setFriendRequests(friendRequests);

		/** Guardamos entidades y asignamos las correspondientes entre ellas */
		mailBoxRepo.save(mailBox);

		imcRepo.save(imc);
		physicalDataRepo.save(physicalData);
		athleteRepo.save(athlete);

		physicalData.setImc(imc);
		physicalDataRepo.save(physicalData);

		athlete.setMailBox(mailBox);
		athlete.setPhysicalData(physicalData);
		athleteRepo.save(athlete);

		user.setAthleteId(athlete);
		userRepo.save(user);

		return user.getAthleteId();
	}

	@Override
	public DietFriendRequest sendFriendRequest(String claimantUsername, String requestedUsername) {

		DietUser claimantUser = userRepo.findByUsername(claimantUsername).get();
		DietUser requestedUser = userRepo.findByUsername(requestedUsername).get();
		DietFriendRequest friendRequest = new DietFriendRequest();

		if (!(claimantUser.getAthleteId().getFriends().contains(requestedUser.getAthleteId()))) {

			friendRequest.setRequestDate(LocalDate.now());
			friendRequest.setRequestStatus(DietRequestStatus.PENDING);
			friendRequest.setClaimantAthlete(claimantUsername);
			friendRequest.setRequestedAthlete(requestedUsername);

			friendRequestRepo.save(friendRequest);

			requestedUser.getAthleteId().getMailBox().getFriendRequests().add(friendRequest);

			mailBoxRepo.save(requestedUser.getAthleteId().getMailBox());

			athleteRepo.save(requestedUser.getAthleteId());

			userRepo.save(requestedUser);
		}

		return friendRequest;
	}

	@Override
	public DietFriendRequest acceptFriendRequest(Long id) {
		DietFriendRequest request = friendRequestRepo.findById(id).get();
		DietUser claimantUser = userRepo.findByUsername(request.getClaimantAthlete()).get();
		DietUser requestedUser = userRepo.findByUsername(request.getRequestedAthlete()).get();

		if (request.getRequestStatus() == DietRequestStatus.PENDING) {
			request.setRequestStatus(DietRequestStatus.ACCEPTED);

			friendRequestRepo.save(request);

			claimantUser.getAthleteId().getFriends().add(requestedUser.getUsername());
			athleteRepo.save(claimantUser.getAthleteId());
			userRepo.save(claimantUser);
			requestedUser.getAthleteId().getFriends().add(claimantUser.getUsername());
			athleteRepo.save(requestedUser.getAthleteId());
			userRepo.save(requestedUser);
		}

		return friendRequestRepo.findById(id).get();
	}

	@Override
	public DietFriendRequest rejectFriendRequest(Long id) {

		DietFriendRequest request = friendRequestRepo.findById(id).get();

		request.setRequestStatus(DietRequestStatus.REJECTED);

		friendRequestRepo.save(request);

		return friendRequestRepo.findById(id).get();
	}

	@Override
	public List<String> getAthleteFriends(String username) {
		DietAthlete athlete = userRepo.findByUsername(username).get().getAthleteId();
		return athlete.getFriends();
	}
	
	

	@Override
	public List<DietFriendRequest> getFriendsRequests(String username) {
		
		return userRepo.findByUsername(username).get().getAthleteId().getMailBox().getFriendRequests();
	}

	/**
	 * Metodo que calcula la escala del imc en la que se encuentra el atleta segun
	 * su peso
	 * 
	 * @param weight
	 * @param scalesImc
	 * @return
	 */
	private DietScale scaleCalculation(Double weight, List<DietScaleImc> scalesImc) {

		/** Variable a devolver */
		DietScale actualScale = DietScale.OBESITY_FOUR;

		/** Diferentes escalas del imc */
		Double scale1, scale2, scale3, scale4, scale5, scale6, scale7;

		/** Asignamos datos de cada escala del atleta */
		scale1 = scalesImc.get(0).getWeightScale();
		scale2 = scalesImc.get(1).getWeightScale();
		scale3 = scalesImc.get(2).getWeightScale();
		scale4 = scalesImc.get(3).getWeightScale();
		scale5 = scalesImc.get(4).getWeightScale();
		scale6 = scalesImc.get(5).getWeightScale();
		scale7 = scalesImc.get(6).getWeightScale();

		/**
		 * Calculo de la escala segun en que intervalo se encuentra el atleta con su
		 * peso
		 */
		if ((scale1 <= weight) && (weight < scale2)) {
			actualScale = DietScale.NORMALWEIGHT;
		} else if ((scale2 <= weight) && (weight < scale3)) {
			actualScale = DietScale.OVERWEIGHT_ONE;
		} else if ((scale3 <= weight) && (weight < scale4)) {
			actualScale = DietScale.OVERWEIGHT_TWO;
		} else if ((scale4 <= weight) && (weight < scale5)) {
			actualScale = DietScale.OBESITY_ONE;
		} else if ((scale5 <= weight) && (weight < scale6)) {
			actualScale = DietScale.OBESITY_TWO;
		} else if ((scale6 <= weight) && (weight < scale7)) {
			actualScale = DietScale.OBESITY_THREE;
		}

		return actualScale;
	}

	/**
	 * Asignacion de las escalas del imc al atleta segun su altura. Unicamente en el
	 * registro del atleta
	 * 
	 * @param height
	 * @return
	 */
	private List<DietScaleImc> makeScales(Double height) {

		List<DietScaleImc> scales = new ArrayList<DietScaleImc>();

		DietScaleImc scale1 = new DietScaleImc();
		scale1.setScale(DietScale.NORMALWEIGHT);
		scale1.setWeightScale(Math.pow(height, 2) * DietImcConstants.NORMALWEIGHT);
		DietScaleImc scale2 = new DietScaleImc();
		scale2.setScale(DietScale.OVERWEIGHT_ONE);
		scale2.setWeightScale(Math.pow(height, 2) * DietImcConstants.OVERWEIGHT_ONE);
		DietScaleImc scale3 = new DietScaleImc();
		scale3.setScale(DietScale.OVERWEIGHT_TWO);
		scale3.setWeightScale(Math.pow(height, 2) * DietImcConstants.OVERWEIGHT_TWO);
		DietScaleImc scale4 = new DietScaleImc();
		scale4.setScale(DietScale.OBESITY_ONE);
		scale4.setWeightScale(Math.pow(height, 2) * DietImcConstants.OBESITY_ONE);
		DietScaleImc scale5 = new DietScaleImc();
		scale5.setScale(DietScale.OBESITY_TWO);
		scale5.setWeightScale(Math.pow(height, 2) * DietImcConstants.OBESITY_TWO);
		DietScaleImc scale6 = new DietScaleImc();
		scale6.setScale(DietScale.OBESITY_THREE);
		scale6.setWeightScale(Math.pow(height, 2) * DietImcConstants.OBESITY_THREE);
		DietScaleImc scale7 = new DietScaleImc();
		scale7.setScale(DietScale.OBESITY_FOUR);
		scale7.setWeightScale(Math.pow(height, 2) * DietImcConstants.OBESITY_FOUR);

		scaleImcRepo.save(scale1);
		scaleImcRepo.save(scale2);
		scaleImcRepo.save(scale3);
		scaleImcRepo.save(scale4);
		scaleImcRepo.save(scale5);
		scaleImcRepo.save(scale6);
		scaleImcRepo.save(scale7);

		scales.add(scale1);
		scales.add(scale2);
		scales.add(scale3);
		scales.add(scale4);
		scales.add(scale5);
		scales.add(scale6);
		scales.add(scale7);

		return scales;

	}

}
