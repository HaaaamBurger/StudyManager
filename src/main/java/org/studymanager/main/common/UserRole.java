package org.studymanager.main.common;

import org.springframework.security.core.GrantedAuthority;

/**
 * Enum representing the different roles a user can have within the system.
 * Implements the {@link GrantedAuthority} interface to be
 * used in Spring Security for role-based access control.
 *
 * <p>There are two roles defined:
 * <ul>
 *   <li>{@code USER} - Regular user with basic access rights.</li>
 *   <li>{@code ADMIN} - Administrator with elevated privileges.</li>
 * </ul>
 *
 * <p>The {@link #getAuthority()} method returns the string
 * representation of the role to be used in Spring Security authorization checks.
 *
 * @author Oleksandr Melnyk
 * @version 1.0
 */
public enum UserRole implements GrantedAuthority {
  USER,
  ADMIN;

  @Override
  public String getAuthority() {
    return this.name();
  }
}