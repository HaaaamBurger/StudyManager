package org.studymanager.main.model.user;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mapping.model.SnakeCaseFieldNamingStrategy;
import org.studymanager.main.common.UserRole;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@JsonView(SnakeCaseFieldNamingStrategy.class)
public class UserResponse {
    private String id;

    @Pattern(regexp = "[A-Z][a-z]+",
            message = "must start with a capital letter followed by one or more lowercase letters")
    private String firstName;

    @Pattern(regexp = "[A-Z][a-z]+",
            message = "Must start with a capital letter followed by one or more lowercase letters")
    private String lastName;

    @Pattern(regexp = "[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}", message = "Must be a valid e-mail address")
    private String email;

    private UserRole role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
