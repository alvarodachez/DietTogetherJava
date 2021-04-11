package com.jacaranda.model.comparator;

import java.util.Comparator;

import com.jacaranda.model.DietAthlete;

public class DietAthleteWeightComparator implements Comparator<DietAthlete>{

	@Override
	public int compare(DietAthlete o1, DietAthlete o2) {
		if(o1.getPhysicalData().getWeight() < o2.getPhysicalData().getWeight()) {
			return 1;
		}else if(o1.getPhysicalData().getWeight() > o2.getPhysicalData().getWeight()) {
			return -1;
		}else {
			return 0;
		}
		
	}

}
