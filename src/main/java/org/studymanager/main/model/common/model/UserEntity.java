package org.studymanager.main.model.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.studymanager.main.common.UserType;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Getter
@Setter
@SuperBuilder
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@MappedSuperclass
public abstract class UserEntity implements Serializable {
  @Serial
  private static final long serialVersionUID = 2309850186389251526L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  String id;

  @Pattern(regexp = "[A-Z][a-z]+",
          message = "must start with a capital letter followed by one or more lowercase letters")
  @Column(name = "first_name")
  String firstName;

  @Pattern(regexp = "[A-Z][a-z]+",
          message = "Must start with a capital letter followed by one or more lowercase letters")
  @Column(name = "last_name")
  String lastName;

  @Enumerated(EnumType.STRING)
  @JsonProperty("user_type")
  @Column(name = "user_type")
  UserType userType;

  @EqualsAndHashCode.Exclude
  @JsonProperty("created_at")
  @Column(name = "created_at")
  LocalDateTime createdAt;

  @EqualsAndHashCode.Exclude
  @JsonProperty("updated_at")
  @Column(name = "updated_at")
  LocalDateTime updatedAt;

}