package com.luxoft.bankapp.service;

import com.luxoft.bankapp.annotations.NoDB;
import com.luxoft.bankapp.model.Client;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Sergey Popov on 4/10/2014.
 */
public class TestService {
	/**
	 * Данный метод должен анализировать поля классов o1 и o2
	 * Он должен сравнивать все поля с помощью equals,
	 * за исключением тех полей, которые помечены аннотацией
	 * @NoDB
	 * и возвращать true, если все поля совпали.
	 * также он должен уметь сравнивать коллекции.
	 */
	public static boolean isEquals(Object o1, Object o2) throws IllegalAccessException {

		if (!o1.getClass().getName().equals(o2.getClass().getName())) {
			return false;
		}

		Field[] oFields = o1.getClass().getDeclaredFields();

		for (int i = 0; i < oFields.length; i++) {
			oFields[i].setAccessible(true);
			if (oFields[i].getAnnotation(NoDB.class) != null) {
				continue;
			}

			// if collection
			if (Collection.class.isAssignableFrom(oFields[i].getType())) {
                Object[] o1Collect = ((Collection)oFields[i].get(o1)).toArray();
                Object[] o2Collect = ((Collection)oFields[i].get(o2)).toArray();
				if (oFields[i].get(o1) instanceof Set<?>) {
					return ((Set)o1Collect[i]).containsAll(((Set)o2Collect[i]));
				} else if (oFields[i].get(o1) instanceof List<?>) {
					for (int k = 0; k < o1Collect.length; k++) {
						boolean result = o1Collect[k] == null ?
								o2Collect[k] == null :
								TestService.isEquals(o1Collect[k], o2Collect[k]);
						if (!result) {
							return false;
						}
					}
				}
			} else { // if not collection
				boolean result = oFields[i].get(o1) == null ?
						oFields[i].get(o2) == null :
						oFields[i].get(o1).equals(oFields[i].get(o2));
				if (!result) {
					return false;
				}
			}
		}
		return true;
	}
}
