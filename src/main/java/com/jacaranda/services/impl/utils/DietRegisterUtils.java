package com.jacaranda.services.impl.utils;

import java.util.List;

import com.jacaranda.common.DietImcConstants;
import com.jacaranda.model.DietScale;
import com.jacaranda.model.DietScaleImc;

public class DietRegisterUtils {

	public static DietScale scaleCalculation(Double weight, List<DietScaleImc> scalesImc) {

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

	public static Double gamePointInverseCalculation(DietScale actualScale) {

		Double res = 0.0;
		if (actualScale == DietScale.NORMALWEIGHT) {
			res = DietImcConstants.OBESIDAD4_POINTS;
		} else if (actualScale == DietScale.OVERWEIGHT_ONE) {
			res = DietImcConstants.OBESIDAD3_POINTS;
		} else if (actualScale == DietScale.OVERWEIGHT_TWO) {
			res = DietImcConstants.OBESIDAD2_POINTS;
		} else if (actualScale == DietScale.OBESITY_ONE) {
			res = DietImcConstants.OBESIDAD1_POINTS;
		} else if (actualScale == DietScale.OBESITY_TWO) {
			res = DietImcConstants.SOBREPESO2_POINTS;
		} else if (actualScale == DietScale.OBESITY_THREE) {
			res = DietImcConstants.SOBREPESO1_POINTS;
		} else {
			res = DietImcConstants.NORMOPESO_POINTS;

		}

		return res;
	}

	public static Double gamePointCalculation(DietScale actualScale) {

		Double res = 0.0;
		if (actualScale == DietScale.NORMALWEIGHT) {
			res = DietImcConstants.NORMOPESO_POINTS;
		} else if (actualScale == DietScale.OVERWEIGHT_ONE) {
			res = DietImcConstants.SOBREPESO1_POINTS;
		} else if (actualScale == DietScale.OVERWEIGHT_TWO) {
			res = DietImcConstants.SOBREPESO2_POINTS;
		} else if (actualScale == DietScale.OBESITY_ONE) {
			res = DietImcConstants.OBESIDAD1_POINTS;
		} else if (actualScale == DietScale.OBESITY_TWO) {
			res = DietImcConstants.OBESIDAD2_POINTS;
		} else if (actualScale == DietScale.OBESITY_THREE) {
			res = DietImcConstants.OBESIDAD3_POINTS;
		} else {
			res = DietImcConstants.OBESIDAD4_POINTS;
		}

		return res;
	}
}
