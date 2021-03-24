package com.jacaranda.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jacaranda.model.DietDay;
import com.jacaranda.model.DietDayRegime;
import com.jacaranda.model.DietDish;
import com.jacaranda.model.DietMeal;
import com.jacaranda.model.DietMealRegime;
import com.jacaranda.repository.DietAthleteRepository;
import com.jacaranda.repository.DietDayRegimeRepository;
import com.jacaranda.repository.DietDishRepository;
import com.jacaranda.repository.DietMealRegimeRepository;
import com.jacaranda.repository.DietRegimeRepository;
import com.jacaranda.security.model.DietUser;
import com.jacaranda.security.repository.DietUserRepository;
import com.jacaranda.services.DietRegimeServiceI;

@Service("regimeService")
public class DietRegimeServiceImpl implements DietRegimeServiceI {

	@Autowired
	DietUserRepository userRepo;

	@Autowired
	DietAthleteRepository athleteRepo;

	@Autowired
	DietRegimeRepository regimeRepo;

	@Autowired
	DietDishRepository dishRepo;
	
	@Autowired
	DietMealRegimeRepository mealRepo;
	
	@Autowired
	DietDayRegimeRepository dayRegimeRepo;

	@Override
	public DietDish createDish(String username, DietDish dish) {

		DietUser user = userRepo.findByUsername(username).get();

		DietDish dishToCreate = new DietDish();

		dishToCreate.setName(dish.getName());
		dishToCreate.setDescription(dish.getDescription());
		dishToCreate.setCategories(dish.getCategories());

		if (user.getAthleteId().getRegime().getDishes() == null) {
			List<DietDish> dishes = new ArrayList<>();

			dishes.add(dishRepo.save(dishToCreate));
			user.getAthleteId().getRegime().setDishes(dishes);

		} else {
			user.getAthleteId().getRegime().getDishes().add(dishRepo.save(dishToCreate));
		}

		athleteRepo.save(user.getAthleteId());
		userRepo.save(user);

		return userRepo.findByUsername(username).get().getAthleteId().getRegime().getDishes()
				.get(userRepo.findByUsername(username).get().getAthleteId().getRegime().getDishes().size() - 1);
	}

	@Override
	public List<DietDish> getDishesByUsername(String username) {

		if (userRepo.findByUsername(username).get().getAthleteId().getRegime().getDishes() == null) {
			return null;
		} else {
			return userRepo.findByUsername(username).get().getAthleteId().getRegime().getDishes();
		}

	}

	@Override
	public List<DietDayRegime> createRegimeStructure(String username) {
		
		DietUser user = userRepo.findByUsername(username).get();
		
		if(user.getAthleteId().getRegime().getDays().isEmpty()) {
			
			user.getAthleteId().getRegime().setDays(this.createDayRegimeStructure());
			
			athleteRepo.save(user.getAthleteId());
			userRepo.save(user);
			
		}
		return userRepo.findByUsername(username).get().getAthleteId().getRegime().getDays();
	}
	
	
	
	@Override
	public DietMealRegime addDishToDay(String username, Long meal, Long dish) {

		DietUser user = userRepo.findByUsername(username).get();
		DietDish dishToAdd = dishRepo.findById(dish).get();
		DietMealRegime mealRegime = mealRepo.findById(meal).get();
		
		if(user.getAthleteId().getRegime().getDishes().contains(dishToAdd)) {
			mealRegime.setDish(dishToAdd);
			
			mealRepo.save(mealRegime);
			athleteRepo.save(user.getAthleteId());
			userRepo.save(user);
		}
		
		
		return mealRepo.findById(meal).get();
	}

	private List<DietMealRegime> createMealRegimeStructure(){
		List<DietMealRegime> out = new ArrayList<DietMealRegime>();
		
		DietMealRegime meal1 = new DietMealRegime();
		meal1.setMeal(DietMeal.BREAKFAST);
		
		DietMealRegime meal2 = new DietMealRegime();
		meal2.setMeal(DietMeal.MID_MORNING);
		
		DietMealRegime meal3 = new DietMealRegime();
		meal3.setMeal(DietMeal.LUNCH);
		
		DietMealRegime meal4 = new DietMealRegime();
		meal4.setMeal(DietMeal.SNACK);
		
		DietMealRegime meal5 = new DietMealRegime();
		meal5.setMeal(DietMeal.DINNER);
		
		out.add(mealRepo.save(meal1));
		out.add(mealRepo.save(meal2));
		out.add(mealRepo.save(meal3));
		out.add(mealRepo.save(meal4));
		out.add(mealRepo.save(meal5));
		
		return out;
	}
	
	private List<DietDayRegime> createDayRegimeStructure(){
		List<DietDayRegime> out = new ArrayList<DietDayRegime>();
		
		DietDayRegime day1 = new DietDayRegime();
		day1.setDay(DietDay.MONDAY);
		day1.setMeals(this.createMealRegimeStructure());
		
		DietDayRegime day2 = new DietDayRegime();
		day2.setDay(DietDay.TUESDAY);
		day2.setMeals(this.createMealRegimeStructure());
		
		DietDayRegime day3 = new DietDayRegime();
		day3.setDay(DietDay.WEDNESDAY);
		day3.setMeals(this.createMealRegimeStructure());
		
		DietDayRegime day4 = new DietDayRegime();
		day4.setDay(DietDay.THURSDAY);
		day4.setMeals(this.createMealRegimeStructure());
		
		DietDayRegime day5 = new DietDayRegime();
		day5.setDay(DietDay.FRIDAY);
		day5.setMeals(this.createMealRegimeStructure());
		
		DietDayRegime day6 = new DietDayRegime();
		day6.setDay(DietDay.SATURDAY);
		day6.setMeals(this.createMealRegimeStructure());
		
		DietDayRegime day7 = new DietDayRegime();
		day7.setDay(DietDay.SUNDAY);
		day7.setMeals(this.createMealRegimeStructure());
		
		out.add(dayRegimeRepo.save(day1));
		out.add(dayRegimeRepo.save(day2));
		out.add(dayRegimeRepo.save(day3));
		out.add(dayRegimeRepo.save(day4));
		out.add(dayRegimeRepo.save(day5));
		out.add(dayRegimeRepo.save(day6));
		out.add(dayRegimeRepo.save(day7));
		
		return out;
	}

}
