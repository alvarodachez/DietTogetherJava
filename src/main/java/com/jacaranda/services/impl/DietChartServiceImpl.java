package com.jacaranda.services.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jacaranda.model.DietGroup;
import com.jacaranda.model.DietRegister;
import com.jacaranda.model.dto.DietChartGroupPointsDto;
import com.jacaranda.model.dto.DietChartRegisterDto;
import com.jacaranda.model.dto.DietRegisterDto;
import com.jacaranda.repository.DietAthleteRepository;
import com.jacaranda.security.model.DietUser;
import com.jacaranda.security.repository.DietUserRepository;
import com.jacaranda.services.DietChartServiceI;

@Service("chartService")
public class DietChartServiceImpl implements DietChartServiceI {

	@Autowired
	DietUserRepository userRepo;

	@Autowired
	DietAthleteRepository athleteRepo;

	@Override
	public List<DietChartRegisterDto> totalProfileRegisters(String username) {

		// Obtenciond el usuario desde base de datos
		DietUser user = userRepo.findByUsername(username).get();

		// Se inicializa la lista de registros para la grafica que se van a devolver
		List<DietChartRegisterDto> totalProfileRegisters = new ArrayList<>();

		// Se crea el unico que va a existir ya que solo estamos en el usuario del
		// perfil
		DietChartRegisterDto chartRegisterDto = new DietChartRegisterDto();

		// Asignamos el nombre de usuario para estos datos
		chartRegisterDto.setName(username);

		// Se inicializa la lista donde van a ir los datos de los registros
		List<DietRegisterDto> registersDto = new ArrayList<>();

		if (user.getAthleteId().getPhysicalData().getRegisters() != null
				&& !user.getAthleteId().getPhysicalData().getRegisters().isEmpty()) {
			// Recorremos los registros del atleta
			for (DietRegister r : user.getAthleteId().getPhysicalData().getRegisters()) {
				DietRegisterDto registerDto = new DietRegisterDto();

				registerDto.setName(r.getWeightDate());
				registerDto.setValue(r.getWeight());

				registersDto.add(registerDto);

			}
		}

		if (user.getAthleteId().getPhysicalData().getLastRegister() != null) {

			// Añadimos los datos del ultimo registro del usuario.
			DietRegisterDto registerDto = new DietRegisterDto();
			registerDto.setName(user.getAthleteId().getPhysicalData().getLastRegister().getWeightDate());
			registerDto.setValue(user.getAthleteId().getPhysicalData().getLastRegister().getWeight());

			registersDto.add(registerDto);

		}

		// Añadimos los registros al dato de la grafica
		chartRegisterDto.setSeries(registersDto);

		// Añadimos el dato de la grafica a la lista de datos
		totalProfileRegisters.add(chartRegisterDto);

		return totalProfileRegisters;
	}

	@Override
	public List<DietChartRegisterDto> totalProfileWeightDifferenceRegisters(String username) {

		// Obtenciond el usuario desde base de datos
		DietUser user = userRepo.findByUsername(username).get();

		// Se inicializa la lista de registros para la grafica que se van a devolver
		List<DietChartRegisterDto> totalProfileRegisters = new ArrayList<>();

		// Se crea el unico que va a existir ya que solo estamos en el usuario del
		// perfil
		DietChartRegisterDto chartRegisterDto = new DietChartRegisterDto();

		// Asignamos el nombre de usuario para estos datos
		chartRegisterDto.setName(username);

		// Se inicializa la lista donde van a ir los datos de los registros
		List<DietRegisterDto> registersDto = new ArrayList<>();

		if (user.getAthleteId().getPhysicalData().getRegisters() != null
				&& !user.getAthleteId().getPhysicalData().getRegisters().isEmpty()) {
			// Recorremos los registros del atleta
			for (DietRegister r : user.getAthleteId().getPhysicalData().getRegisters()) {
				DietRegisterDto registerDto = new DietRegisterDto();

				registerDto.setName(r.getWeightDate());
				registerDto.setValue(r.getWeightDifference());

				registersDto.add(registerDto);

			}
		}

		if (user.getAthleteId().getPhysicalData().getLastRegister() != null) {

			// Añadimos los datos del ultimo registro del usuario.
			DietRegisterDto registerDto = new DietRegisterDto();
			registerDto.setName(user.getAthleteId().getPhysicalData().getLastRegister().getWeightDate());
			registerDto.setValue(user.getAthleteId().getPhysicalData().getLastRegister().getWeightDifference());

			registersDto.add(registerDto);

		}

		// Añadimos los registros al dato de la grafica
		chartRegisterDto.setSeries(registersDto);

		// Añadimos el dato de la grafica a la lista de datos
		totalProfileRegisters.add(chartRegisterDto);

		return totalProfileRegisters;
	}

	@Override
	public List<DietChartRegisterDto> totalGroupRegisters(String username) {

		DietUser user = userRepo.findByUsername(username).get();

		DietGroup group = user.getAthleteId().getActualGroup();

		List<String> athletes = group.getAthletes();

		LocalDate createDate = group.getCreationDate();

		List<DietChartRegisterDto> chartData = new ArrayList<DietChartRegisterDto>();

		for (String a : athletes) {
			DietUser athlete = userRepo.findByUsername(a).get();

			DietChartRegisterDto chartRegister = new DietChartRegisterDto();
			chartRegister.setName(a);

			List<DietRegisterDto> registersDto = new ArrayList<DietRegisterDto>();

			if (athlete.getAthleteId().getPhysicalData().getRegisters() != null
					&& !athlete.getAthleteId().getPhysicalData().getRegisters().isEmpty()) {

				for (DietRegister r : athlete.getAthleteId().getPhysicalData().getRegisters()) {

					if (r.getWeightDate().isAfter(createDate) || r.getWeightDate().equals(createDate)) {
						DietRegisterDto registerDto = new DietRegisterDto();
						registerDto.setName(r.getWeightDate());
						registerDto.setValue(r.getWeight());

						registersDto.add(registerDto);
					}
				}

			}

			if (athlete.getAthleteId().getPhysicalData().getLastRegister() != null) {
				if (athlete.getAthleteId().getPhysicalData().getLastRegister().getWeightDate().isAfter(createDate)
						|| athlete.getAthleteId().getPhysicalData().getLastRegister().getWeightDate()
								.equals(createDate)) {
					DietRegisterDto registerDto = new DietRegisterDto();
					registerDto.setName(athlete.getAthleteId().getPhysicalData().getLastRegister().getWeightDate());
					registerDto.setValue(athlete.getAthleteId().getPhysicalData().getLastRegister().getWeight());
					registersDto.add(registerDto);
				}
			}

			chartRegister.setSeries(registersDto);
			chartData.add(chartRegister);
		}
		return chartData;
	}

	@Override
	public List<DietChartGroupPointsDto> pointsGroup(String username) {

		DietUser user = userRepo.findByUsername(username).get();

		List<DietChartGroupPointsDto> groupPoints = new ArrayList<DietChartGroupPointsDto>();

		if (user.getAthleteId().getActualGroup() != null) {

			DietGroup group = user.getAthleteId().getActualGroup();

			List<String> athletes = group.getAthletes();

			for (String athlete : athletes) {

				DietChartGroupPointsDto groupPoint = new DietChartGroupPointsDto();

				groupPoint.setName(athlete);

				groupPoint.setValue(userRepo.findByUsername(athlete).get().getAthleteId().getGamePoints());

				groupPoints.add(groupPoint);
			}

		}
		return groupPoints;
	}

}
