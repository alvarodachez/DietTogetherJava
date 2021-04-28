package com.jacaranda.model.comparator;

import java.util.Comparator;

import com.jacaranda.model.DietRegister;

public class DietRegisterCreateDateComparator implements Comparator<DietRegister> {

	@Override
	public int compare(DietRegister arg0, DietRegister arg1) {

		int res = 0;

		if (arg0.getWeightDate().isBefore(arg1.getWeightDate())) {
			res = 1;
		} else if (arg0.getWeightDate().isAfter(arg1.getWeightDate())) {
			res = -1;
		}

		return res;
	}

}
