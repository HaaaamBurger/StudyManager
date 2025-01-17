package org.studymanager.main.common.aspect;

import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.studymanager.main.common.UserRole;
import org.studymanager.main.model.common.model.UserEntity;
import org.studymanager.main.security.permission.CheckAccess;

/**
 * This class intercepts the call to
 * the CheckAccess annotation and checks
 * whether permissions are available.
 *
 * @author Melnyk Oleksandr
 * @version 1.0
 */
@Aspect
@Component
public class SecurityCheckAccessAspect {

  /**
   * Method that checks weather admin wants to get
   * this resource or user wants to get its own data.
   *
   * @param checkAccess         CheckAccess annotation
   * @param proceedingJoinPoint JoinPoint for proceeding flow or not
   * @return Object
   */
  @Around("@annotation(checkAccess)")
  public Object checkAccess(
          ProceedingJoinPoint proceedingJoinPoint,
          CheckAccess checkAccess
  ) throws Throwable {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null) {
      throw new SecurityException("You're not authorized!!");
    }

    if (authentication.getAuthorities().stream()
            .anyMatch(grantedAuthority -> grantedAuthority
                    .toString()
                    .equals(UserRole.ADMIN.name())
            )
    ) {
      return proceedingJoinPoint.proceed();
    }

    UserEntity user = (UserEntity) authentication.getPrincipal();
    boolean isCurrentUser = Arrays.stream(proceedingJoinPoint.getArgs())
            .anyMatch(o -> o.equals(user.getId()));

    if (!isCurrentUser) {
      throw new AccessDeniedException("Access denied. Not enough rights!!");
    }

    return proceedingJoinPoint.proceed();
  }
}
