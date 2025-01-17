package org.studymanager.main.model.auth.dto;

import java.io.Serial;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * This class needs for AuthenticationController to input
 * request body.
 *
 * @author Melnyk Oleksandr
 * @version 1.0
 */
@Getter
@Setter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthResponse implements Serializable {
  @Serial
  static final long serialVersionUID = 4249802753055318534L;

  String message;

  Object body;
}
