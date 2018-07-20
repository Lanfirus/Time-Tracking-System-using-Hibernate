package ua.training.tts.controller.util;

import ua.training.tts.model.entity.Employee;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to help implement authorization system.
 * Accepted roles are used to define what resources are available for what account roles.
 * Boolean attribute allows to deal with guests (users with no account roles).
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessRights {
    Employee.AccountRole[] acceptedRoles();
    boolean isAvailableForGuests();
}
