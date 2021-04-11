package com.jacaranda.services.impl;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jacaranda.common.DietExceptionCode;
import com.jacaranda.exceptions.DietGroupException;
import com.jacaranda.model.DietBoostAthlete;
import com.jacaranda.model.DietBoostDay;
import com.jacaranda.model.DietGroup;
import com.jacaranda.model.DietGroupRequest;
import com.jacaranda.model.DietRequestStatus;
import com.jacaranda.model.dto.DietProgressBarDto;
import com.jacaranda.repository.DietAthleteRepository;
import com.jacaranda.repository.DietBoostDayRepository;
import com.jacaranda.repository.DietGroupRepository;
import com.jacaranda.repository.DietGroupRequestRepository;
import com.jacaranda.repository.DietMailBoxRepository;
import com.jacaranda.security.model.DietRole;
import com.jacaranda.security.model.DietUser;
import com.jacaranda.security.repository.DietUserRepository;
import com.jacaranda.services.DietGroupServiceI;

@Service("groupService")
public class DietGroupServiceImpl implements DietGroupServiceI {

	@Autowired
	private DietUserRepository userRepo;

	@Autowired
	private DietAthleteRepository athleteRepo;

	@Autowired
	private DietGroupRepository groupRepo;

	@Autowired
	private DietGroupRequestRepository groupRequestRepo;

	@Autowired
	private DietMailBoxRepository mailBoxRepo;

	@Autowired
	private DietBoostDayRepository boostDayRepo;

	@Override
	public DietGroup createGroup(String username, DietGroup group) throws DietGroupException {

		// Obtener usuario que crea el grupo
		DietUser user = userRepo.findByUsername(username).get();

		// Comprobación que verifica si el usuario tiene un grupo en su variable actual
		// group y que si lo tiene no este activo
		if (user.getAthleteId().getActualGroup() != null
				&& !(user.getAthleteId().getActualGroup().getEnabled() == Boolean.TRUE)) {

			// Asigna el rol de Group Manager al usuario que crea el grupo
			user.getRoles().add(DietRole.GROUP_MANAGER);

			// Inicializamos el grupo que se va a crear
			DietGroup groupToCreate = new DietGroup();

			// Insertamos los valores del grupo
			groupToCreate.setName(group.getName());
			groupToCreate.setExpireDate(group.getExpireDate());
			groupToCreate.setChallengeType(group.getChallengeType());
			groupToCreate.setEnabled(Boolean.TRUE);
			groupToCreate.setCreationDate(LocalDate.now());

			// Inicializamos y añadimos el creador del grupo a la lista de atletas
			List<String> athletes = new ArrayList<String>();
			athletes.add(user.getUsername());
			groupToCreate.setAthletes(athletes);

			// Inicializamos el dia del empujon y asignamos sus valores
			DietBoostDay boostDay = new DietBoostDay();

			boostDay.setActualDate(groupToCreate.getCreationDate().plusWeeks(1L));
			boostDay.setNextDate(boostDay.getActualDate().plusWeeks(1L));

			// Inicializamos array de atletas con empujon y se le asigna al dia del empujon
			List<DietBoostAthlete> boostAthletes = new ArrayList<DietBoostAthlete>();
			boostDay.setBoostAthletes(boostAthletes);

			// Guardamos el dia del empujon en base de datos
			boostDayRepo.save(boostDay);

			// Asignamos el dia del empujon al grupo
			groupToCreate.setBoostDay(boostDay);

			// Se manda la solicitud de grupo a los amigos seleccionados en la creacion del
			// grupo
			for (String requested : group.getAthletes()) {
				this.sendGroupRequest(username, requested);
			}

			// Se guarda el grupo en base de datos
			groupRepo.save(groupToCreate);

			// Se añade el grupo anterior a la lista de grupos del atleta
			user.getAthleteId().getGroups().add(user.getAthleteId().getActualGroup());

			// Se mete en el grupo actual del atleta el grupo que acabamos de crear
			user.getAthleteId().setActualGroup(groupToCreate);
		}
		// Comprobacion si el atleta es la primera vez que crea un grupo
		else if (user.getAthleteId().getActualGroup() == null) {

			// Asigna el rol de Group Manager al usuario que crea el grupo
			user.getRoles().add(DietRole.GROUP_MANAGER);

			// Inicializamos el grupo que se va a crear
			DietGroup groupToCreate = new DietGroup();

			// Insertamos los valores del grupo
			groupToCreate.setName(group.getName());
			groupToCreate.setExpireDate(group.getExpireDate());
			groupToCreate.setChallengeType(group.getChallengeType());
			groupToCreate.setEnabled(Boolean.TRUE);
			groupToCreate.setCreationDate(LocalDate.now());

			// Inicializamos y añadimos el creador del grupo a la lista de atletas
			List<String> athletes = new ArrayList<String>();
			athletes.add(user.getUsername());
			groupToCreate.setAthletes(athletes);

			// Inicializamos el dia del empujon y asignamos sus valores
			DietBoostDay boostDay = new DietBoostDay();

			boostDay.setActualDate(groupToCreate.getCreationDate().plusWeeks(1L));
			boostDay.setNextDate(boostDay.getActualDate().plusWeeks(1L));

			// Inicializamos array de atletas con empujon y se le asigna al dia del empujon
			List<DietBoostAthlete> boostAthletes = new ArrayList<DietBoostAthlete>();
			boostDay.setBoostAthletes(boostAthletes);

			// Guardamos el dia del empujon en base de datos
			boostDayRepo.save(boostDay);

			// Asignamos el dia del empujon al grupo
			groupToCreate.setBoostDay(boostDay);

			// Se manda la solicitud de grupo a los amigos seleccionados en la creacion del
			// grupo
			for (String requested : group.getAthletes()) {
				this.sendGroupRequest(username, requested);
			}

			// Se guarda el grupo en base de datos
			groupRepo.save(groupToCreate);

			// Se asigna al grupo actual del atleta el grupo que se acaba de crear
			user.getAthleteId().setActualGroup(groupToCreate);
		} else {
			throw new DietGroupException(DietExceptionCode.ALREDY_IN_GROUP);
		}

		// Guardamos el atleta en base de datos
		athleteRepo.save(user.getAthleteId());
		// Guardamos el usuario en base de datos
		userRepo.save(user);

		return user.getAthleteId().getActualGroup();
	}

	@Override
	public DietGroupRequest sendGroupRequest(String claimantUsername, String requestedUsername) {

		DietUser claimantUser = userRepo.findByUsername(claimantUsername).get();
		DietUser requestedUser = userRepo.findByUsername(requestedUsername).get();
		DietGroupRequest groupRequest = new DietGroupRequest();

		if ((claimantUser.getAthleteId().getFriends().contains(requestedUser.getUsername()))) {

			if (!(requestedUser.getAthleteId().getActualGroup() != null
					&& requestedUser.getAthleteId().getActualGroup().getEnabled() == Boolean.TRUE)) {
				groupRequest.setRequestDate(LocalDate.now());
				groupRequest.setRequestStatus(DietRequestStatus.PENDING);
				groupRequest.setClaimantAthlete(claimantUsername);
				groupRequest.setRequestedAthlete(requestedUsername);

				groupRequestRepo.save(groupRequest);

				requestedUser.getAthleteId().getMailBox().getGroupRequests().add(groupRequest);

				mailBoxRepo.save(requestedUser.getAthleteId().getMailBox());

				athleteRepo.save(requestedUser.getAthleteId());

				userRepo.save(requestedUser);
			}

		}

		return groupRequest;

	}

	@Override
	public DietGroupRequest acceptGroupRequest(Long id) throws DietGroupException {

		DietGroupRequest request = groupRequestRepo.findById(id).get();
		DietUser claimantUser = userRepo.findByUsername(request.getClaimantAthlete()).get();
		DietUser requestedUser = userRepo.findByUsername(request.getRequestedAthlete()).get();

		DietGroup group = claimantUser.getAthleteId().getActualGroup();

		if (requestedUser.getAthleteId().getActualGroup() != null
				&& requestedUser.getAthleteId().getActualGroup().getEnabled() == Boolean.TRUE) {
			request.setRequestStatus(DietRequestStatus.REJECTED);
			groupRequestRepo.save(request);

			throw new DietGroupException(DietExceptionCode.ALREDY_IN_GROUP);

		} else {
			if (request.getRequestStatus() == DietRequestStatus.PENDING) {
				request.setRequestStatus(DietRequestStatus.ACCEPTED);

				groupRequestRepo.save(request);

				group.getAthletes().add(requestedUser.getUsername());

				groupRepo.save(group);

				if (requestedUser.getAthleteId().getActualGroup() != null
						&& requestedUser.getAthleteId().getActualGroup().getEnabled() == Boolean.FALSE) {
					requestedUser.getAthleteId().getGroups().add(requestedUser.getAthleteId().getActualGroup());

					requestedUser.getAthleteId().setActualGroup(group);

				} else {
					requestedUser.getAthleteId().setActualGroup(group);
				}
				athleteRepo.save(requestedUser.getAthleteId());
				userRepo.save(requestedUser);
			}
		}

		return groupRequestRepo.findById(id).get();
	}

	@Override
	public DietGroupRequest rejectGroupRequest(Long id) {
		DietGroupRequest request = groupRequestRepo.findById(id).get();

		request.setRequestStatus(DietRequestStatus.REJECTED);

		groupRequestRepo.save(request);

		return groupRequestRepo.findById(id).get();

	}

	@Override
	public List<DietGroupRequest> getGroupRequests(String username) {

		return userRepo.findByUsername(username).get().getAthleteId().getMailBox().getGroupRequests();
	}

	@Override
	public DietProgressBarDto getProgressBar(String username) {

		DietUser user = userRepo.findByUsername(username).get();

		DietGroup group = user.getAthleteId().getActualGroup();

		DietProgressBarDto progressBarInfo = new DietProgressBarDto();

		// Obtencion de los dias restantes

		long daysLeft = 0;

		daysLeft = DAYS.between(LocalDate.now(), group.getExpireDate());

		progressBarInfo.setDaysLeft(daysLeft);

		// Obtencion del porcentaje transcurrido

		long totalDays = 0;

		long completedDays = 0;

		Double percentage = 0.0;

		totalDays = DAYS.between(group.getCreationDate(), group.getExpireDate());

		completedDays = DAYS.between(group.getCreationDate(), LocalDate.now());

		percentage = (Double.valueOf(completedDays) / Double.valueOf(totalDays)) * 100.0;

		progressBarInfo.setTotalPercentage(percentage);

		return progressBarInfo;
	}

	@Override
	public List<DietGroup> getOutGroup(String username) {

		DietUser user = userRepo.findByUsername(username).get();

		if (user.getAthleteId().getActualGroup() != null) {

			DietGroup group = user.getAthleteId().getActualGroup();

			user.getAthleteId().getGroups().add(user.getAthleteId().getActualGroup());
			user.getAthleteId()
					.setTotalPoints(user.getAthleteId().getTotalPoints() + user.getAthleteId().getGamePoints());
			user.getAthleteId().setGamePoints(0.0);
			group.getAthletes().remove(username);

			if (user.getRoles().contains(DietRole.GROUP_MANAGER)) {

				List<String> usersInGroup = new ArrayList<>();
				usersInGroup.addAll(group.getAthletes());

				if (usersInGroup.size() >= 1) {
					DietUser userToUpgrade = userRepo.findByUsername(usersInGroup.get(0)).get();

					userToUpgrade.getRoles().add(DietRole.GROUP_MANAGER);

					athleteRepo.save(userToUpgrade.getAthleteId());
					userRepo.save(userToUpgrade);

				}

				user.getRoles().remove(DietRole.GROUP_MANAGER);

			}

			user.getAthleteId().setActualGroup(null);
			groupRepo.save(group);
		}

		athleteRepo.save(user.getAthleteId());
		userRepo.save(user);

		return user.getAthleteId().getGroups();
	}

}
