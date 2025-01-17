package org.studymanager.main.model.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.studymanager.main.common.UserRole;
import org.studymanager.main.common.UserType;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@SuperBuilder
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class BaseResponse implements Serializable {

  private final static long serialVersionUID = 5678924298360253647L;

  String id;

  @JsonProperty("first_name")
  String firstName;

  @JsonProperty("last_name")
  String lastName;

  String email;

  UserRole role;

  @JsonProperty("user_type")
  UserType userType;

  @JsonProperty("created_at")
  LocalDateTime createdAt;

  @JsonProperty("updated_at")
  LocalDateTime updatedAt;
}


