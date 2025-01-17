package org.studymanager.main.model.common.dto;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class BaseRequest {
  @Pattern(regexp = "[A-Z][a-z]+",
          message = "must start with a capital letter followed by one or more lowercase letters")
  String firstName;

  @Pattern(regexp = "[A-Z][a-z]+",
          message = "Must start with a capital letter followed by one or more lowercase letters")
  String lastName;

  @Pattern(regexp = "[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}", message = "Must be a valid e-mail address")
  @NotBlank(message = "Email cannot be blank")
  String email;

  @NotBlank(message = "Password cannot be empty")
  String password;
}

