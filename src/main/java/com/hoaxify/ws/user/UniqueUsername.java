package com.hoaxify.ws.user;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UniqueUsernameValidator.class}) //
public @interface UniqueUsername {

    String message() default "Username must be unique";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

}

