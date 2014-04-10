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
		Class<?> o1Class = o1.getClass();
		Class<?> o2Class = o2.getClass();

		if (!o1Class.getName().equals(o2Class.getName())) {
			return false;
		}

		Field[] o1Fields = o1Class.getDeclaredFields();
		Field[] o2Fields = o2Class.getDeclaredFields();

		for (int i = 0; i < o1Fields.length; i++) {
			o1Fields[i].setAccessible(true);
			if (o1Fields[i].getAnnotation(NoDB.class) != null) {
				continue;
			}

            if (!o1Fields[i].equals(o2Fields[i])) {
                return false;
            }

			if (Collection.class.isAssignableFrom(o1Fields[i].getType())) {
                Object[] o1Collect = ((Collection)o1Fields[i].get(o1)).toArray();
                Object[] o2Collect = ((Collection)o1Fields[i].get(o2)).toArray();
                for (int k = 0; k < o1Collect.length; k++) {
                    if (o1Collect[k] == null && o2Collect[k] == null) {
                        continue;
                    } else if (o1Collect[k] == null || o2Collect[k] == null) {
                        return false;
                    } else if (!o1Collect[k].equals(o2Collect[k])) {
                        return false;
                    }
                }
			}
		}
		return true;
	}
}
