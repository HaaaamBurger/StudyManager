package org.studymanager.main.security.permission;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for defining access checks on methods.
 *
 * <p>This annotation can be applied to methods to
 * specify the required access level
 * for execution. By default, the access level is set to "USER".
 *
 * @author Melnyk Oleksandr
 * @version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckAccess {

  /**
   * Specifies the required access level for the
   * annotated method.
   * This value can be used to define roles such as
   * "USER", "ADMIN", etc.
   * The default value is "USER".
   *
   * @return the access level as a string
   */
  String value() default "USER";
}
