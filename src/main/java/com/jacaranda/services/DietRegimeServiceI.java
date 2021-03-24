package com.jacaranda.services;

import java.util.List;

import com.jacaranda.model.DietDayRegime;
import com.jacaranda.model.DietDish;
import com.jacaranda.model.DietMealRegime;

public interface DietRegimeServiceI {

	public DietDish createDish(String username, DietDish dish);
	
	public List<DietDish> getDishesByUsername(String username);
	
	public List<DietDayRegime> createRegimeStructure(String username);
	
	public DietMealRegime addDishToDay(String username, Long meal,Long dish);
	
	public List<DietDayRegime> getDayRegime(String username);
}
