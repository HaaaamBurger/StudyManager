package org.studymanager.main.model.auth.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.io.Serial;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * This class needs for AuthenticationController to return
 * readable response.
 *
 * @author Melnyk Oleksandr
 * @version 1.0
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthRequest implements Serializable {
  @Serial
  static final long serialVersionUID = 983278960376890372L;

  //  @Pattern(
  //          regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$\n",
  //          message = "Must be a valid password"
  //  )
  @NotNull(message = "Password cannot be empty")
  String password;

  @Pattern(regexp = "[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}", message = "Must be a valid e-mail address")
  @NotNull(message = "Email cannot be empty")
  String email;
}
