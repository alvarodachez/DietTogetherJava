package com.jacaranda.services.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jacaranda.common.DietExceptionCode;
import com.jacaranda.exceptions.DietRegisterException;
import com.jacaranda.model.DietRegister;
import com.jacaranda.model.DietRegisterStatus;
import com.jacaranda.repository.DietAthleteRepository;
import com.jacaranda.repository.DietDishRepository;
import com.jacaranda.repository.DietGroupRepository;
import com.jacaranda.repository.DietPhysicalDataRepository;
import com.jacaranda.repository.DietRegisterRepository;
import com.jacaranda.security.model.DietRole;
import com.jacaranda.security.model.DietUser;
import com.jacaranda.security.repository.DietUserRepository;
import com.jacaranda.services.DietRegisterServiceI;
import com.jacaranda.services.impl.utils.DietRegisterUtils;

@Service("registerService")
public class DietRegisterServiceImpl implements DietRegisterServiceI {

	@Autowired
	DietUserRepository userRepo;

	@Autowired
	DietRegisterRepository registerRepo;

	@Autowired
	DietAthleteRepository athleteRepo;

	@Autowired
	DietPhysicalDataRepository physicalDataRepo;

	@Autowired
	DietGroupRepository groupRepo;

	@Autowired
	DietDishRepository dishRepo;

	/**
	 * En este metodo creamos el registro, y se actualizan los datos del atleta
	 * convenientes
	 */
	@Override
	public DietRegister createRegister(String username, DietRegister register) throws DietRegisterException {
		DietUser user = userRepo.findByUsername(username).get();

		DietRegister registerToCreate = new DietRegister();

		// Se setean los datos obtenidos del front
		registerToCreate.setWeight(register.getWeight());
		registerToCreate.setWeightDate(LocalDate.now());
		registerToCreate.setNextDateRegister(LocalDate.now().plusWeeks(1L));
		registerToCreate.setRegisterStatus(DietRegisterStatus.PENDING);
		registerToCreate.setAthlete(username);

		// Comprobacion para ver si existia ya un registro anterior
		if (user.getAthleteId().getPhysicalData().getLastRegister() == null) {

			// Al ser el primer registro se setea la diferencia de peso con el peso que puso
			// el usuario al registrarse
			registerToCreate.setWeightDifference(Math
					.round((user.getAthleteId().getPhysicalData().getWeight() - registerToCreate.getWeight()) * 100.0)
					/ 100.0);

			// Comprobacion si es BoostAthlete
			if (user.getAthleteId().getActualGroup().getBoostDay().getBoostAthlete() != null) {
				if (user.getAthleteId().getActualGroup().getBoostDay().getBoostAthlete().getUsername()
						.compareTo(user.getUsername()) == 0) {

					if (registerToCreate.getWeightDifference() >= user.getAthleteId().getActualGroup().getBoostDay()
							.getBoostAthlete().getWeightChallenge()) {
						registerToCreate.setWeightDifference(registerToCreate.getWeightDifference() + user
								.getAthleteId().getActualGroup().getBoostDay().getBoostAthlete().getWeightChallenge());
					}
				}
			}

			// Guardamos registro en la base de datos
			registerRepo.save(registerToCreate);

			// Añadimos el registro como ultimo registro al atleta
			user.getAthleteId().getPhysicalData().setLastRegister(registerToCreate);

			// Cambiamos el peso del atleta al nuevo registro
			user.getAthleteId().getPhysicalData().setWeight(registerToCreate.getWeight());

			// Cambiamos el valor del imc del atlta con el nuevo valor del peso del registro
			user.getAthleteId().getPhysicalData().getImc().setImcValue(
					registerToCreate.getWeight() / Math.pow(user.getAthleteId().getPhysicalData().getHeight(), 2));

			// Se calcula en que baremo esta el atleta tras el registro
			user.getAthleteId().getPhysicalData().getImc().setActualScale(DietRegisterUtils.scaleCalculation(
					registerToCreate.getWeight(), user.getAthleteId().getPhysicalData().getImc().getScales()));

			// Se calcula el puntaje del atleta segun su baremo y la diferencia de peso
//			if (user.getAthleteId().getPhysicalData().getLastRegister().getWeightDifference() < 0) {
//
//				user.getAthleteId().setGamePoints(user.getAthleteId().getGamePoints()
//						+ (((user.getAthleteId().getPhysicalData().getLastRegister().getWeightDifference() * 1000)
//								/ 100)
//								* this.gamePointInverseCalculation(
//										user.getAthleteId().getPhysicalData().getImc().getActualScale())));
//			} else {
//
//				user.getAthleteId().setGamePoints(user.getAthleteId().getGamePoints()
//						+ (((user.getAthleteId().getPhysicalData().getLastRegister().getWeightDifference() * 1000)
//								/ 100)
//								* this.gamePointCalculation(
//										user.getAthleteId().getPhysicalData().getImc().getActualScale())));
//			}

			user.getAthleteId().getActualGroup().getRegistersToVerify().add(registerToCreate);
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

				// Comprobacion si es BoostAthlete
				if (user.getAthleteId().getActualGroup().getBoostDay().getBoostAthlete() != null) {
					if (user.getAthleteId().getActualGroup().getBoostDay().getBoostAthlete().getUsername()
							.compareTo(user.getUsername()) == 0) {

						if (registerToCreate.getWeightDifference() >= user.getAthleteId().getActualGroup().getBoostDay()
								.getBoostAthlete().getWeightChallenge()) {
							registerToCreate
									.setWeightDifference(registerToCreate.getWeightDifference() + user.getAthleteId()
											.getActualGroup().getBoostDay().getBoostAthlete().getWeightChallenge());
						}
					}
				}

				// Se guarda el registro en base de datos
				registerRepo.save(registerToCreate);

				// Se añade a la lista de registros el ultimo registro que existia
				user.getAthleteId().getPhysicalData().getRegisters()
						.add(user.getAthleteId().getPhysicalData().getLastRegister());

				// Se sustituye el ultimo registro que existia por el nuevo
				user.getAthleteId().getPhysicalData().setLastRegister(registerToCreate);

				// Cambiamos el peso del atleta al nuevo registro
				user.getAthleteId().getPhysicalData().setWeight(registerToCreate.getWeight());

				// Cambiamos el valor del imc del atlta con el nuevo valor del peso del registro
				user.getAthleteId().getPhysicalData().getImc().setImcValue(
						registerToCreate.getWeight() / Math.pow(user.getAthleteId().getPhysicalData().getHeight(), 2));

				// Se calcula en que baremo esta el atleta tras el registro
				user.getAthleteId().getPhysicalData().getImc().setActualScale(DietRegisterUtils.scaleCalculation(
						registerToCreate.getWeight(), user.getAthleteId().getPhysicalData().getImc().getScales()));

				user.getAthleteId().getActualGroup().getRegistersToVerify().add(registerToCreate);

				groupRepo.save(user.getAthleteId().getActualGroup());

				// Se calcula el puntaje del atleta segun su baremo y la diferencia de peso

//				if (user.getAthleteId().getPhysicalData().getLastRegister().getWeightDifference() < 0) {
//
//					user.getAthleteId().setGamePoints(user.getAthleteId().getGamePoints()
//							+ (((user.getAthleteId().getPhysicalData().getLastRegister().getWeightDifference() * 1000)
//									/ 100)
//									* this.gamePointInverseCalculation(
//											user.getAthleteId().getPhysicalData().getImc().getActualScale())));
//				} else {
//
//					user.getAthleteId().setGamePoints(user.getAthleteId().getGamePoints()
//							+ (((user.getAthleteId().getPhysicalData().getLastRegister().getWeightDifference() * 1000)
//									/ 100)
//									* this.gamePointCalculation(
//											user.getAthleteId().getPhysicalData().getImc().getActualScale())));
//				}

				// Se guardan los datos fisicos del atleta
				physicalDataRepo.save(user.getAthleteId().getPhysicalData());

				// Se guarda el atleta en base de datos
				athleteRepo.save(user.getAthleteId());

				// Se guarda el usuario en base de datos
				userRepo.save(user);

			} else {
				throw new DietRegisterException(DietExceptionCode.ONE_REGISTER_PER_WEEK);
			}
		}
		return user.getAthleteId().getPhysicalData().getLastRegister();
	}

	@Override
	public DietRegister verifyRegister(String username, Long id) {

		DietUser manager = userRepo.findByUsername(username).get();

		DietRegister register = registerRepo.findById(id).get();

		DietUser groupMember = userRepo.findByUsername(register.getAthlete()).get();

		if (manager.getRoles().contains(DietRole.GROUP_MANAGER) && (groupMember.getAthleteId().getPhysicalData()
				.getRegisters().contains(register)
				|| groupMember.getAthleteId().getPhysicalData().getLastRegister().getId() == register.getId())) {

			groupMember.getAthleteId().getActualGroup().getRegistersToVerify().remove(register);

			register.setRegisterStatus(DietRegisterStatus.VERIFIED);

			if (register.getWeightDifference() < 0) {

				groupMember.getAthleteId()
						.setGamePoints(groupMember.getAthleteId().getGamePoints()
								+ (((register.getWeightDifference() * 1000) / 100) * DietRegisterUtils.gamePointInverseCalculation(
										groupMember.getAthleteId().getPhysicalData().getImc().getActualScale())));
			} else {

				groupMember.getAthleteId()
						.setGamePoints(groupMember.getAthleteId().getGamePoints()
								+ (((register.getWeightDifference() * 1000) / 100) * DietRegisterUtils.gamePointCalculation(
										groupMember.getAthleteId().getPhysicalData().getImc().getActualScale())));
			}

			groupRepo.save(groupMember.getAthleteId().getActualGroup());
			registerRepo.save(register);
			athleteRepo.save(groupMember.getAthleteId());
			userRepo.save(groupMember);

		}

		return registerRepo.findById(id).get();
	}

	@Override
	public DietRegister declineRegister(String username, Long id) {

		DietUser manager = userRepo.findByUsername(username).get();

		DietRegister register = registerRepo.findById(id).get();

		DietUser groupMember = userRepo.findByUsername(register.getAthlete()).get();

		if (manager.getRoles().contains(DietRole.GROUP_MANAGER) && (groupMember.getAthleteId().getPhysicalData()
				.getRegisters().contains(register)
				|| groupMember.getAthleteId().getPhysicalData().getLastRegister().getId() == register.getId())) {

			groupMember.getAthleteId().getActualGroup().getRegistersToVerify().remove(register);

			register.setRegisterStatus(DietRegisterStatus.DECLINED);

			groupRepo.save(groupMember.getAthleteId().getActualGroup());
			registerRepo.save(register);
			athleteRepo.save(groupMember.getAthleteId());
			userRepo.save(groupMember);

		}

		return registerRepo.findById(id).get();
	}

	@Override
	public List<DietRegister> getRegistersToVerify(String username) {
		DietUser manager = userRepo.findByUsername(username).get();

		// Se comprueba si el usuario tiene el rol GROUP_MANAGER
		if (manager.getRoles().contains(DietRole.GROUP_MANAGER)) {

			// Si lo tiene, se devuelve la lista de registros por verificar
			return manager.getAthleteId().getActualGroup().getRegistersToVerify();

		} else {

			// Si NO lo tiene, se devuelve null
			return null;
		}

	}

	@Override
	public List<DietRegister> getRegistersByUsername(String username) {
		DietUser user = userRepo.findByUsername(username).get();

		List<DietRegister> registers = new ArrayList<DietRegister>();
		registers.add(user.getAthleteId().getPhysicalData().getLastRegister());
		registers.addAll(user.getAthleteId().getPhysicalData().getRegisters());

		return registers;
	}

}
