package com.jacaranda.services.impl;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jacaranda.model.DietPrivateActivity;
import com.jacaranda.model.DietPrivateActivityMode;
import com.jacaranda.model.DietPrivateRegisterMode;
import com.jacaranda.model.DietRegister;
import com.jacaranda.model.comparator.DietRegisterCreateDateComparator;
import com.jacaranda.model.dto.DietPrivateActivityClassicDto;
import com.jacaranda.model.dto.DietPrivateActivityDto;
import com.jacaranda.model.dto.DietPrivateActivityProgressiveDto;
import com.jacaranda.model.dto.DietProgressBarDto;
import com.jacaranda.repository.DietAthleteRepository;
import com.jacaranda.repository.DietPhysicalDataRepository;
import com.jacaranda.repository.DietPrivateActivityRepository;
import com.jacaranda.repository.DietRegisterRepository;
import com.jacaranda.security.model.DietUser;
import com.jacaranda.security.repository.DietUserRepository;
import com.jacaranda.services.DietPrivateActivityServiceI;
import com.jacaranda.services.impl.utils.DietRegisterUtils;

import io.jsonwebtoken.lang.Collections;

@Service("privateActivityService")
public class DietPrivateActivityServiceImpl implements DietPrivateActivityServiceI {

	@Autowired
	DietUserRepository userRepo;

	@Autowired
	DietAthleteRepository athleteRepo;

	@Autowired
	DietRegisterRepository registerRepo;

	@Autowired
	DietPrivateActivityRepository privateActivityRepo;

	@Autowired
	DietPhysicalDataRepository physicalDataRepo;

	@Override
	public DietPrivateActivity createPrivateActivity(String username, DietPrivateActivity privateActivity) {

		DietUser user = userRepo.findByUsername(username).get();

		if (user.getAthleteId().getActualGroup() == null
				|| user.getAthleteId().getActualGroup().getEnabled() == Boolean.FALSE) {
			DietPrivateActivity privateActivityToCreate = new DietPrivateActivity();

			privateActivityToCreate.setAthlete(username);
			privateActivityToCreate.setCreateDate(LocalDate.now());
			privateActivityToCreate.setEnabled(Boolean.TRUE);
			privateActivityToCreate.setPrivateActivityMode(privateActivity.getPrivateActivityMode());
			privateActivityToCreate.setPrivateRegisterMode(privateActivity.getPrivateRegisterMode());
			privateActivityToCreate.setWeightObjective(privateActivity.getWeightObjective());
			privateActivityToCreate.setExpireDate(privateActivity.getExpireDate());

			if (privateActivityToCreate.getPrivateRegisterMode() == DietPrivateRegisterMode.PROGRESSIVE) {
				privateActivityToCreate.setActualProgressiveDate(LocalDate.now().plusWeeks(1L));
				privateActivityToCreate.setNextProgressiveDate(LocalDate.now().plusWeeks(2L));
				List<DietRegister> registers = new ArrayList<>();
				privateActivityToCreate.setRegisters(registers);
			}

			privateActivityRepo.save(privateActivityToCreate);

			if (user.getAthleteId().getActualPrivateActivity() != null
					&& user.getAthleteId().getActualPrivateActivity().getEnabled() == Boolean.FALSE) {
				user.getAthleteId().getPrivateActivities().add(user.getAthleteId().getActualPrivateActivity());
			} else if (user.getAthleteId().getActualPrivateActivity() == null) {
				user.getAthleteId().setActualPrivateActivity(privateActivityToCreate);
			}

			athleteRepo.save(user.getAthleteId());
			userRepo.save(user);
		}

		return user.getAthleteId().getActualPrivateActivity();
	}

	@Override
	public DietRegister createRegister(String username, DietRegister register) {

		DietUser user = userRepo.findByUsername(username).get();
		DietRegister registerToCreate = new DietRegister();

		if (user.getAthleteId().getActualPrivateActivity() != null
				&& user.getAthleteId().getActualPrivateActivity().getEnabled() == Boolean.TRUE) {
			if (user.getAthleteId().getActualPrivateActivity()
					.getPrivateRegisterMode() == DietPrivateRegisterMode.PROGRESSIVE) {

				registerToCreate = this.createProgressiveRegister(user, username, register);
			} else if (user.getAthleteId().getActualPrivateActivity()
					.getPrivateRegisterMode() == DietPrivateRegisterMode.CLASSIC) {

				registerToCreate = this.createClassicRegister(user, username, register);
			}
		}

		return registerToCreate;
	}

	@Override
	public DietPrivateActivityDto getPrivateActivity(String username) {

		DietUser user = userRepo.findByUsername(username).get();

		if (user.getAthleteId().getActualPrivateActivity() == null) {
			return null;
		}

		DietPrivateActivity privateActivity = user.getAthleteId().getActualPrivateActivity();

		if (privateActivity.getPrivateRegisterMode() == DietPrivateRegisterMode.CLASSIC) {
			DietPrivateActivityClassicDto privateActivityDto = new DietPrivateActivityClassicDto();

			privateActivityDto.setAthlete(privateActivity.getAthlete());
			privateActivityDto.setId(privateActivity.getId());
			privateActivityDto.setCreateDate(privateActivity.getCreateDate());
			privateActivityDto.setExpireDate(privateActivity.getExpireDate());
			privateActivityDto.setEnabled(privateActivity.getEnabled());
			privateActivityDto.setPrivateActivityMode(privateActivity.getPrivateActivityMode());
			privateActivityDto.setWeightObjective(privateActivity.getWeightObjective());

			privateActivityDto.setRegisterMode(DietPrivateRegisterMode.CLASSIC);

			List<DietRegister> registers = new ArrayList<>();

			if (user.getAthleteId().getPhysicalData().getLastRegister() != null) {
				registers.add(user.getAthleteId().getPhysicalData().getLastRegister());
			}

			if (user.getAthleteId().getPhysicalData().getRegisters() != null
					&& !Collections.isEmpty(user.getAthleteId().getPhysicalData().getRegisters())) {
				LocalDate date = privateActivity.getCreateDate();

				user.getAthleteId().getPhysicalData().getRegisters().stream().forEach((r) -> {
					if (date.isBefore(r.getWeightDate())) {
						registers.add(r);
					}
				});

			}

			privateActivityDto.setTotalRegisters(registers);

			return privateActivityDto;

		} else {
			DietPrivateActivityProgressiveDto privateActivityDto = new DietPrivateActivityProgressiveDto();

			privateActivityDto.setAthlete(privateActivity.getAthlete());
			privateActivityDto.setId(privateActivity.getId());
			privateActivityDto.setCreateDate(privateActivity.getCreateDate());
			privateActivityDto.setExpireDate(privateActivity.getExpireDate());
			privateActivityDto.setEnabled(privateActivity.getEnabled());
			privateActivityDto.setPrivateActivityMode(privateActivity.getPrivateActivityMode());
			privateActivityDto.setWeightObjective(privateActivity.getWeightObjective());

			privateActivityDto.setRegisterMode(DietPrivateRegisterMode.PROGRESSIVE);

			List<DietRegister> registers = new ArrayList<>();

			if (user.getAthleteId().getPhysicalData().getLastRegister() != null) {
				registers.add(user.getAthleteId().getPhysicalData().getLastRegister());
			}

			if (user.getAthleteId().getPhysicalData().getRegisters() != null
					&& !Collections.isEmpty(user.getAthleteId().getPhysicalData().getRegisters())) {
				LocalDate date = privateActivity.getCreateDate();

				user.getAthleteId().getPhysicalData().getRegisters().stream().forEach((r) -> {
					if (date.isBefore(r.getWeightDate())) {
						registers.add(r);
					}
				});

			}

			privateActivityDto.setTotalRegisters(registers);

			privateActivityDto.setDaylyRegisters(privateActivity.getRegisters());

			return privateActivityDto;
		}

	}

	@Override
	public DietProgressBarDto getProgressBar(String username) {

		DietUser user = userRepo.findByUsername(username).get();

		DietPrivateActivity privateActivity = user.getAthleteId().getActualPrivateActivity();

		DietProgressBarDto progressBarInfo = new DietProgressBarDto();

		// Obtencion de los dias restantes

		long daysLeft = 0;

		daysLeft = DAYS.between(LocalDate.now(), privateActivity.getExpireDate());

		progressBarInfo.setDaysLeft(daysLeft);

		// Obtencion del porcentaje transcurrido

		long totalDays = 0;

		long completedDays = 0;

		Double percentage = 0.0;

		totalDays = DAYS.between(privateActivity.getCreateDate(), privateActivity.getExpireDate());

		completedDays = DAYS.between(privateActivity.getCreateDate(), LocalDate.now());

		percentage = (Double.valueOf(completedDays) / Double.valueOf(totalDays)) * 100.0;

		progressBarInfo.setTotalPercentage(percentage);

		return progressBarInfo;
	}

	@Scheduled(cron = "0 0 2 * * *", zone = "Europe/Madrid")
	// @Scheduled(cron = "0 */1 * * * *", zone = "Europe/Madrid")
	public void checkProgressiveRegister() {

		LocalDate localDate = LocalDate.now();// For reference
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedString = localDate.format(formatter);

		List<DietPrivateActivity> privateActivities = privateActivityRepo.findByEnabledProgressiveOnDate(localDate);

		if (privateActivities != null && !privateActivities.isEmpty()) {

			for (DietPrivateActivity privateActivity : privateActivities) {

				List<DietRegister> privateActivityRegisters = registerRepo
						.findByPrivateActivity(String.valueOf(privateActivity.getId()));

				if (!privateActivityRegisters.isEmpty()) {

					List<DietRegister> registers = new ArrayList<>();
					privateActivityRegisters.sort(new DietRegisterCreateDateComparator());

					registers.addAll(privateActivityRegisters);

					DietRegister registerToCreate = new DietRegister();

					if (registers.size() > 1) {
						Double averageWeight = registers.stream().filter(
								register -> register.getWeightDate().isEqual(privateActivity.getActualProgressiveDate())
										|| (register.getWeightDate()
												.isAfter(privateActivity.getActualProgressiveDate().minusWeeks(1L))
												&& register.getWeightDate().isBefore(LocalDate.now())))
								.mapToDouble(register -> register.getWeight()).average().getAsDouble(); // peta

						registerToCreate.setWeight(averageWeight);
					} else {
						registerToCreate.setWeight(registers.get(0).getWeight());
					}

					registerToCreate.setAthlete(privateActivity.getAthlete());
					registerToCreate.setNextDateRegister(LocalDate.now().plusWeeks(1L));
					registerToCreate.setRegisterStatus(null);
					registerToCreate.setWeightDate(LocalDate.now());

					DietUser user = userRepo.findByUsername(privateActivity.getAthlete()).get();

					if (user.getAthleteId().getPhysicalData().getLastRegister() != null) {

						registerToCreate.setWeightDifference(
								Math.round((user.getAthleteId().getPhysicalData().getLastRegister().getWeight()
										- registerToCreate.getWeight()) * 100.0) / 100.0);

						user.getAthleteId().getPhysicalData().getRegisters()
								.add(user.getAthleteId().getPhysicalData().getLastRegister());

						user.getAthleteId().getPhysicalData().setLastRegister(registerRepo.save(registerToCreate));

					} else {
						registerToCreate.setWeightDifference(Math.round(
								(user.getAthleteId().getPhysicalData().getWeight() - registerToCreate.getWeight())
										* 100)
								/ 100.0);

						user.getAthleteId().getPhysicalData().setLastRegister(registerRepo.save(registerToCreate));
					}

					user.getAthleteId().getPhysicalData().setWeight(registerToCreate.getWeight());

					// Cambiamos el valor del imc del atlta con el nuevo valor del peso del registro
					user.getAthleteId().getPhysicalData().getImc().setImcValue(registerToCreate.getWeight()
							/ Math.pow(user.getAthleteId().getPhysicalData().getHeight(), 2));

					// Se calcula en que baremo esta el atleta tras el registro
//					user.getAthleteId().getPhysicalData().getImc().setActualScale(DietRegisterUtils.scaleCalculation(
//							registerToCreate.getWeight(), user.getAthleteId().getPhysicalData().getImc().getScales()));

					if (privateActivity.getPrivateActivityMode() == DietPrivateActivityMode.LOSE) {

						if (user.getAthleteId().getPhysicalData().getLastRegister().getWeightDifference() < 0) {

							user.getAthleteId()
									.setGamePoints(user.getAthleteId().getGamePoints() + (((user.getAthleteId()
											.getPhysicalData().getLastRegister().getWeightDifference() * 1000) / 100)
											* DietRegisterUtils.gamePointInverseCalculation(
													user.getAthleteId().getPhysicalData().getImc().getActualScale())));
						} else {

							user.getAthleteId()
									.setGamePoints(user.getAthleteId().getGamePoints() + (((user.getAthleteId()
											.getPhysicalData().getLastRegister().getWeightDifference() * 1000) / 100)
											* DietRegisterUtils.gamePointCalculation(
													user.getAthleteId().getPhysicalData().getImc().getActualScale())));
						}
					} else if (privateActivity.getPrivateActivityMode() == DietPrivateActivityMode.GAIN) {
						if (user.getAthleteId().getPhysicalData().getLastRegister().getWeightDifference() < 0) {

							user.getAthleteId()
									.setGamePoints(user.getAthleteId().getGamePoints() + (((user.getAthleteId()
											.getPhysicalData().getLastRegister().getWeightDifference() * 1000) / 100)
											* DietRegisterUtils.gamePointCalculation(
													user.getAthleteId().getPhysicalData().getImc().getActualScale())));
						} else {

							user.getAthleteId()
									.setGamePoints(user.getAthleteId().getGamePoints() + (((user.getAthleteId()
											.getPhysicalData().getLastRegister().getWeightDifference() * 1000) / 100)
											* DietRegisterUtils.gamePointInverseCalculation(
													user.getAthleteId().getPhysicalData().getImc().getActualScale())));
						}
					}

					privateActivity.setActualProgressiveDate(privateActivity.getNextProgressiveDate());
					privateActivity.setNextProgressiveDate(privateActivity.getNextProgressiveDate().plusWeeks(1L));

					privateActivityRepo.save(privateActivity);

					physicalDataRepo.save(user.getAthleteId().getPhysicalData());

					// Se guarda el atleta en base de datos
					athleteRepo.save(user.getAthleteId());

					// Se guarda el usuario en base de datos
					userRepo.save(user);

				}
			}
		}

	}

	private DietRegister createProgressiveRegister(DietUser user, String username, DietRegister register) {

		DietRegister registerToCreate = new DietRegister();

		if (!user.getAthleteId().getActualPrivateActivity().getRegisters().isEmpty()) {

			user.getAthleteId().getActualPrivateActivity().getRegisters().sort(new DietRegisterCreateDateComparator());

			if (user.getAthleteId().getActualPrivateActivity().getRegisters()
					.get(user.getAthleteId().getActualPrivateActivity().getRegisters().size() - 1).getWeightDate()
					.isBefore(LocalDate.now())) {

				registerToCreate.setAthlete(username);
				registerToCreate.setRegisterStatus(null);
				registerToCreate.setNextDateRegister(LocalDate.now().plusDays(1L));
				registerToCreate.setWeightDate(LocalDate.now());
				registerToCreate.setWeight(register.getWeight());
				registerToCreate.setWeightDifference(Math.round((user.getAthleteId().getActualPrivateActivity()
						.getRegisters().get(user.getAthleteId().getActualPrivateActivity().getRegisters().size() - 1)
						.getWeight() - registerToCreate.getWeight()) * 100.0) / 100.0);

				user.getAthleteId().getActualPrivateActivity().getRegisters().add(registerRepo.save(registerToCreate));
				privateActivityRepo.save(user.getAthleteId().getActualPrivateActivity());
				athleteRepo.save(user.getAthleteId());
				userRepo.save(user);
			}
		} else {
			registerToCreate.setAthlete(username);
			registerToCreate.setRegisterStatus(null);
			registerToCreate.setNextDateRegister(LocalDate.now().plusDays(1L));
			registerToCreate.setWeightDate(LocalDate.now());
			registerToCreate.setWeight(register.getWeight());
			registerToCreate.setWeightDifference(Math
					.round((user.getAthleteId().getPhysicalData().getWeight() - registerToCreate.getWeight()) * 100.0)
					/ 100.0);

			user.getAthleteId().getActualPrivateActivity().getRegisters().add(registerRepo.save(registerToCreate));
			privateActivityRepo.save(user.getAthleteId().getActualPrivateActivity());
			athleteRepo.save(user.getAthleteId());
			userRepo.save(user);
		}

		return registerToCreate;

	}

	private DietRegister createClassicRegister(DietUser user, String username, DietRegister register) {

		DietRegister registerToCreate = new DietRegister();

		// Se setean los datos obtenidos del front
		registerToCreate.setWeight(register.getWeight());
		registerToCreate.setWeightDate(LocalDate.now());
		registerToCreate.setNextDateRegister(LocalDate.now().plusWeeks(1L));
		registerToCreate.setRegisterStatus(null);
		registerToCreate.setAthlete(username);

		// Comprobacion para ver si existia ya un registro anterior
		if (user.getAthleteId().getPhysicalData().getLastRegister() == null) {

			// Al ser el primer registro se setea la diferencia de peso con el peso que puso
			// el usuario al registrarse
			registerToCreate.setWeightDifference(Math
					.round((user.getAthleteId().getPhysicalData().getWeight() - registerToCreate.getWeight()) * 100.0)
					/ 100.0);

			// Guardamos registro en la base de datos

			// Añadimos el registro como ultimo registro al atleta
			user.getAthleteId().getPhysicalData().setLastRegister(registerRepo.save(registerToCreate));

			// Cambiamos el peso del atleta al nuevo registro
			user.getAthleteId().getPhysicalData().setWeight(registerToCreate.getWeight());

			// Cambiamos el valor del imc del atlta con el nuevo valor del peso del registro
			user.getAthleteId().getPhysicalData().getImc().setImcValue(
					registerToCreate.getWeight() / Math.pow(user.getAthleteId().getPhysicalData().getHeight(), 2));

			// Se calcula en que baremo esta el atleta tras el registro
			user.getAthleteId().getPhysicalData().getImc().setActualScale(DietRegisterUtils.scaleCalculation(
					registerToCreate.getWeight(), user.getAthleteId().getPhysicalData().getImc().getScales()));

			// Se calcula el puntaje del atleta segun su baremo y la diferencia de peso
			if (user.getAthleteId().getActualPrivateActivity()
					.getPrivateActivityMode() == DietPrivateActivityMode.LOSE) {

				if (user.getAthleteId().getPhysicalData().getLastRegister().getWeightDifference() < 0) {

					user.getAthleteId().setGamePoints(user.getAthleteId().getGamePoints()
							+ (((user.getAthleteId().getPhysicalData().getLastRegister().getWeightDifference() * 1000)
									/ 100)
									* DietRegisterUtils.gamePointInverseCalculation(
											user.getAthleteId().getPhysicalData().getImc().getActualScale())));
				} else {

					user.getAthleteId().setGamePoints(user.getAthleteId().getGamePoints()
							+ (((user.getAthleteId().getPhysicalData().getLastRegister().getWeightDifference() * 1000)
									/ 100)
									* DietRegisterUtils.gamePointCalculation(
											user.getAthleteId().getPhysicalData().getImc().getActualScale())));
				}
			} else if (user.getAthleteId().getActualPrivateActivity()
					.getPrivateActivityMode() == DietPrivateActivityMode.GAIN) {
				if (user.getAthleteId().getPhysicalData().getLastRegister().getWeightDifference() < 0) {

					user.getAthleteId().setGamePoints(user.getAthleteId().getGamePoints()
							+ (((user.getAthleteId().getPhysicalData().getLastRegister().getWeightDifference() * 1000)
									/ 100)
									* DietRegisterUtils.gamePointCalculation(
											user.getAthleteId().getPhysicalData().getImc().getActualScale())));
				} else {

					user.getAthleteId().setGamePoints(user.getAthleteId().getGamePoints()
							+ (((user.getAthleteId().getPhysicalData().getLastRegister().getWeightDifference() * 1000)
									/ 100)
									* DietRegisterUtils.gamePointInverseCalculation(
											user.getAthleteId().getPhysicalData().getImc().getActualScale())));
				}
			}

			// Se guardan los datos fisicos en base de datos
			physicalDataRepo.save(user.getAthleteId().getPhysicalData());

			// Se guarda el atleta en base de datos
			athleteRepo.save(user.getAthleteId());

			// Se guarda el usuario en base de datos
			userRepo.save(user);

		} else {
			// Se calcula una fecha una semana despues del ultimo registro
			LocalDate dateLimit = user.getAthleteId().getPhysicalData().getLastRegister().getWeightDate().plusWeeks(1L)
					.minusDays(1L);

			// Se hacen las operaciones si la fecha del registro es posterior a dateLimit
			if (registerToCreate.getWeightDate().isAfter(dateLimit)) {

				// Se setea la diferencia de peso contando el ultimo registro
				registerToCreate.setWeightDifference(
						Math.round((user.getAthleteId().getPhysicalData().getLastRegister().getWeight()
								- registerToCreate.getWeight()) * 100.0) / 100.0);

				// Se guarda el registro en base de datos

				// Se añade a la lista de registros el ultimo registro que existia
				user.getAthleteId().getPhysicalData().getRegisters()
						.add(user.getAthleteId().getPhysicalData().getLastRegister());

				// Se sustituye el ultimo registro que existia por el nuevo
				user.getAthleteId().getPhysicalData().setLastRegister(registerRepo.save(registerToCreate));

				// Cambiamos el peso del atleta al nuevo registro
				user.getAthleteId().getPhysicalData().setWeight(registerToCreate.getWeight());

				// Cambiamos el valor del imc del atlta con el nuevo valor del peso del registro
				user.getAthleteId().getPhysicalData().getImc().setImcValue(
						registerToCreate.getWeight() / Math.pow(user.getAthleteId().getPhysicalData().getHeight(), 2));

				// Se calcula en que baremo esta el atleta tras el registro
				user.getAthleteId().getPhysicalData().getImc().setActualScale(DietRegisterUtils.scaleCalculation(
						registerToCreate.getWeight(), user.getAthleteId().getPhysicalData().getImc().getScales()));

				// Se calcula el puntaje del atleta segun su baremo y la diferencia de peso
				if (user.getAthleteId().getActualPrivateActivity()
						.getPrivateActivityMode() == DietPrivateActivityMode.LOSE) {

					if (user.getAthleteId().getPhysicalData().getLastRegister().getWeightDifference() < 0) {

						user.getAthleteId()
								.setGamePoints(user.getAthleteId().getGamePoints() + (((user.getAthleteId()
										.getPhysicalData().getLastRegister().getWeightDifference() * 1000) / 100)
										* DietRegisterUtils.gamePointInverseCalculation(
												user.getAthleteId().getPhysicalData().getImc().getActualScale())));
					} else {

						user.getAthleteId()
								.setGamePoints(user.getAthleteId().getGamePoints() + (((user.getAthleteId()
										.getPhysicalData().getLastRegister().getWeightDifference() * 1000) / 100)
										* DietRegisterUtils.gamePointCalculation(
												user.getAthleteId().getPhysicalData().getImc().getActualScale())));
					}
				} else if (user.getAthleteId().getActualPrivateActivity()
						.getPrivateActivityMode() == DietPrivateActivityMode.GAIN) {
					if (user.getAthleteId().getPhysicalData().getLastRegister().getWeightDifference() < 0) {

						user.getAthleteId()
								.setGamePoints(user.getAthleteId().getGamePoints() + (((user.getAthleteId()
										.getPhysicalData().getLastRegister().getWeightDifference() * 1000) / 100)
										* DietRegisterUtils.gamePointCalculation(
												user.getAthleteId().getPhysicalData().getImc().getActualScale())));
					} else {

						user.getAthleteId()
								.setGamePoints(user.getAthleteId().getGamePoints() + (((user.getAthleteId()
										.getPhysicalData().getLastRegister().getWeightDifference() * 1000) / 100)
										* DietRegisterUtils.gamePointInverseCalculation(
												user.getAthleteId().getPhysicalData().getImc().getActualScale())));
					}
				}

				// Se guardan los datos fisicos del atleta
				physicalDataRepo.save(user.getAthleteId().getPhysicalData());

				// Se guarda el atleta en base de datos
				athleteRepo.save(user.getAthleteId());

				// Se guarda el usuario en base de datos
				userRepo.save(user);

			}
		}

		return registerToCreate;

	}

}
