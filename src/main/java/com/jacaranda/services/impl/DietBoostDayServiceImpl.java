package com.jacaranda.services.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.jacaranda.model.DietAthlete;
import com.jacaranda.model.DietBoostAthlete;
import com.jacaranda.model.DietBoostDay;
import com.jacaranda.model.DietGroup;
import com.jacaranda.model.DietRegister;
import com.jacaranda.model.comparator.DietAthleteWeightComparator;
import com.jacaranda.repository.DietAthleteRepository;
import com.jacaranda.repository.DietBoostAthleteRepository;
import com.jacaranda.repository.DietBoostDayRepository;
import com.jacaranda.repository.DietGroupRepository;
import com.jacaranda.security.repository.DietUserRepository;
import com.jacaranda.services.DietBoostDayServiceI;

@Service("boostDayService")
public class DietBoostDayServiceImpl implements DietBoostDayServiceI {

	@Autowired
	private DietGroupRepository groupRepo;

	@Autowired
	private DietAthleteRepository athleteRepo;

	@Autowired
	private DietBoostDayRepository boostDayRepo;

	@Autowired
	private DietUserRepository userRepo;

	@Autowired
	private DietBoostAthleteRepository boostAthleteRepo;

	@Scheduled(cron = "0 0 1 * * *", zone = "Europe/Madrid")
	// @Scheduled(cron = "0 */1 * * * *", zone = "Europe/Madrid")
	public void checkBoostDay() {

		System.out.println("HOLA GENTE");
		// Obtención de grupos activos
		List<DietGroup> groupsEnabled = groupRepo.findByEnabled();

		System.out.println(groupsEnabled.size());
		// Fecha actual
		LocalDate actualDate = LocalDate.now();

		// Se recorren los grupos activos
		for (DietGroup group : groupsEnabled) {

			// Fecha del proximo boost day
			LocalDate actualBoostDayDate = group.getBoostDay().getActualDate();

			// Comprobacion que verifica si la fecha de ahora es igual que la del boostday
			if (actualDate.equals(actualBoostDayDate)) {

				// Se obtiene el dia del empujon del grupo
				DietBoostDay boostDay = group.getBoostDay();

				// Se obtienen los atletas con el minimo de puntos del grupo
				List<DietAthlete> athletesLowPoints = athleteRepo.findByMinimumPoints(group.getId());

				// Atleta con el menor numero de puntos y mayor peso
				DietAthlete athleteLowPoints = new DietAthlete();

				// Stream para fijar el atleta con mas peso de los que tienen los puntos iguales
				athleteLowPoints = athletesLowPoints.stream().min(new DietAthleteWeightComparator()).get();

				// Obtenemos el reto de peso de la funcion privada getWeightAverage
				Double weightChallenge = this.getWeightAverage(athleteLowPoints);

				// Si es el primer dia del empujon, es decir no hay ningun atleta que ya lo haya
				// sido
				if (boostDay.getBoostAthlete() == null) {
					// Se crea el nuevo BoostAthlete
					DietBoostAthlete boostAthlete = new DietBoostAthlete();

					// Se asignan los valores del username y el reto de peso
					boostAthlete.setUsername(userRepo.findUsernameByAthleteId(athleteLowPoints.getId()));
					boostAthlete.setWeightChallenge(weightChallenge);

					// Se inicializa la lista de fechas y se añade la fecha
					List<LocalDate> dates = new ArrayList<LocalDate>();
					dates.add(boostDay.getActualDate());

					// Se asigna la lista de fechas al BoostAthlete
					boostAthlete.setBoostDates(dates);

					// Se guarda el BoostAthlete en base de datos y se le asigna al BoostDay
					boostDay.setBoostAthlete(boostAthleteRepo.save(boostAthlete));

					// Se restablece el ciclo de fechas para el BoostDay
					boostDay.setActualDate(boostDay.getNextDate());
					boostDay.setNextDate(boostDay.getNextDate().plusWeeks(1L));

					// Se guarda el BoostDay en base de datos
					boostDayRepo.save(boostDay);

					// Se guarda el grupo en base de datos
					groupRepo.save(group);
				}
				// Si ya hay en el BoostDay un BoostAthlete
				else {

					// Si la lista de atletas del BoostDay esta vacia y el BoostAthlete que existe
					// coincide con el de la nueva semana
					if (CollectionUtils.isEmpty(boostDay.getBoostAthletes()) && boostDay.getBoostAthlete().getUsername()
							.compareTo(userRepo.findUsernameByAthleteId(athleteLowPoints.getId())) == 0) {

						// Añadimos al atleta la nueva fecha y el nuevo reto de peso
						boostDay.getBoostAthlete().getBoostDates().add(boostDay.getActualDate());
						boostDay.getBoostAthlete().setWeightChallenge(weightChallenge);

						// Actualizamos los datos del BoostAthlete
						boostAthleteRepo.save(boostDay.getBoostAthlete());

						// Se restablece el ciclo de las fechas para el BoostDay
						boostDay.setActualDate(boostDay.getNextDate());
						boostDay.setNextDate(boostDay.getNextDate().plusWeeks(1L));

						// Se guarda el BoostDay en Base de datos
						boostDayRepo.save(boostDay);

						// Se guarda el grupo en base de datos
						groupRepo.save(group);
					}
					// Si la lista de atletas del BoostDay esta vacia y el BoostAthlete de la semana
					// es un nuevo BoostAthlete
					else if (CollectionUtils.isEmpty(boostDay.getBoostAthletes())) {

						// Se añada a la lista de atletas del BoostDay el BoostAthlete que ya existia
						boostDay.getBoostAthletes().add(boostDay.getBoostAthlete());

						// Se crea el nuevo BoostAthlete
						DietBoostAthlete boostAthlete = new DietBoostAthlete();

						// Se asignan los valores del username y el reto de peso
						boostAthlete.setUsername(userRepo.findUsernameByAthleteId(athleteLowPoints.getId()));
						boostAthlete.setWeightChallenge(weightChallenge);

						// Se inicializa la lista de fechas y se añade la fecha
						List<LocalDate> dates = new ArrayList<LocalDate>();
						dates.add(boostDay.getActualDate());

						// Se asigna la lista de fechas al BoostAthlete
						boostAthlete.setBoostDates(dates);

						// Se guarda el BoostAthlete en base de datos y se le asigna al BoostDay
						boostDay.setBoostAthlete(boostAthleteRepo.save(boostAthlete));

						// Se restablece el ciclo de fechas para el BoostDay
						boostDay.setActualDate(boostDay.getNextDate());
						boostDay.setNextDate(boostDay.getNextDate().plusWeeks(1L));

						// Se guarda el BoostDay en base de datos
						boostDayRepo.save(boostDay);

						// Se guarda el grupo en base de datos
						groupRepo.save(group);

					}
					// Si la lista esta llena pero el atleta que ya es BoostAthlete es el mismo de
					// nuevo
					else if (boostDay.getBoostAthlete().getUsername()
							.compareTo(userRepo.findUsernameByAthleteId(athleteLowPoints.getId())) == 0) {

						// Añadimos al atleta la nueva fecha y el nuevo reto de peso
						boostDay.getBoostAthlete().getBoostDates().add(boostDay.getActualDate());
						boostDay.getBoostAthlete().setWeightChallenge(weightChallenge);

						// Actualizamos los datos del BoostAthlete
						boostAthleteRepo.save(boostDay.getBoostAthlete());

						// Se restablece el ciclo de las fechas para el BoostDay
						boostDay.setActualDate(boostDay.getNextDate());
						boostDay.setNextDate(boostDay.getNextDate().plusWeeks(1L));

						// Se guarda el BoostDay en Base de datos
						boostDayRepo.save(boostDay);

						// Se guarda el grupo en base de datos
						groupRepo.save(group);

					}
					// Si la lista esta llena y el atleta no es el BoostAthlete actual
					else {

						// Comprobacion si el BoostAthlete de la semana esta en la lista o es alguien
						// nuevo
						Boolean existsInList = Boolean.FALSE;

						DietBoostAthlete athleteSelected = new DietBoostAthlete();

						for (DietBoostAthlete boostAthlete : boostDay.getBoostAthletes()) {
							if (boostAthlete.getUsername()
									.compareTo(userRepo.findUsernameByAthleteId(athleteLowPoints.getId())) == 0) {
								existsInList = Boolean.TRUE;
								athleteSelected = boostAthlete;
							}
						}

						// Si existe en la lista
						if (existsInList) {

							// Añadimos el BoostAthlete actual a la lista de BoostAthletes
							boostDay.getBoostAthletes().add(boostDay.getBoostAthlete());
							// Borramos de la lista al BoostAthlete seleccionado de la semana
							boostDay.getBoostAthletes().remove(athleteSelected);
							// Asignamos el BoostAthlete seleccionado al BoostAthlete actual
							boostDay.setBoostAthlete(athleteSelected);

							// Añadimos la nueva fecha de cuando ha sido el BoostDay y el nuevo reto de peso
							boostDay.getBoostAthlete().getBoostDates().add(boostDay.getActualDate());
							boostDay.getBoostAthlete().setWeightChallenge(weightChallenge);

							// Se guarda el BoostAthlete en la base de datos
							boostAthleteRepo.save(boostDay.getBoostAthlete());

							// Se restablece el ciclo de las fechas para el BoostDay
							boostDay.setActualDate(boostDay.getNextDate());
							boostDay.setNextDate(boostDay.getNextDate().plusWeeks(1L));

							// Se guarda el BoostDay en Base de datos
							boostDayRepo.save(boostDay);

							// Se guarda el grupo en base de datos
							groupRepo.save(group);

						}
						// Si no existe en la lista
						else {
							// Se añade a la lista
							boostDay.getBoostAthletes().add(boostDay.getBoostAthlete());

							DietBoostAthlete boostAthlete = new DietBoostAthlete();

							// Se asignan los valores del username y el reto de peso
							boostAthlete.setUsername(userRepo.findUsernameByAthleteId(athleteLowPoints.getId()));
							boostAthlete.setWeightChallenge(weightChallenge);

							// Se inicializa la lista de fechas y se añade la fecha
							List<LocalDate> dates = new ArrayList<LocalDate>();
							dates.add(boostDay.getActualDate());

							// Se asigna la lista de fechas al BoostAthlete
							boostAthlete.setBoostDates(dates);

							// Se guarda el BoostAthlete en base de datos y se le asigna al BoostDay
							boostDay.setBoostAthlete(boostAthleteRepo.save(boostAthlete));

							// Se restablece el ciclo de fechas para el BoostDay
							boostDay.setActualDate(boostDay.getNextDate());
							boostDay.setNextDate(boostDay.getNextDate().plusWeeks(1L));

							// Se guarda el BoostDay en base de datos
							boostDayRepo.save(boostDay);

							// Se guarda el grupo en base de datos
							groupRepo.save(group);
						}
					}
				}

			}

		}
	}

	/**
	 * Metodo que devuelve el peso reto del atleta sacado de la media de sus
	 * registros
	 * 
	 * @param athlete
	 * @return
	 */
	private Double getWeightAverage(DietAthlete athlete) {
		Double weightAverage = 0.0;

		// Lista de los registros del atleta
		List<DietRegister> registers = athlete.getPhysicalData().getRegisters();

//		List<DietRegister> registers = new ArrayList<>();
//		registers = registerServiceI.getRegistersByUsername(userRepo.findUsernameByAthleteId(athlete.getId()));

		if (athlete.getPhysicalData().getRegisters() == null) {
			registers = new ArrayList<>();
		}

		if (CollectionUtils.isEmpty(registers)) {
			weightAverage = 0.5;
		} else if (registers.size() == 1) {
			weightAverage = Math.abs(registers.get(0).getWeightDifference() * 0.5);
		} else {
			weightAverage = Math.abs(
					// Stream para obtener la media de diferencias de peso
					registers.stream().mapToDouble(register -> register.getWeightDifference()).average().getAsDouble());
		}

		return weightAverage;
	}

}
