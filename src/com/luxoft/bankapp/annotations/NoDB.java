package com.luxoft.bankapp.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Sergey Popov on 4/10/2014.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target (ElementType.FIELD)
public @interface NoDB {
}
