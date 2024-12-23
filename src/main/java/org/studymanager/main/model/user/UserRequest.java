package org.studymanager.main.model.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRequest {
    @Pattern(regexp = "[A-Z][a-z]+",
            message = "must start with a capital letter followed by one or more lowercase letters")
    @NotBlank(message = "firstname cannot be blank")
    private String firstName;

    @Pattern(regexp = "[A-Z][a-z]+",
            message = "Must start with a capital letter followed by one or more lowercase letters")
    @NotBlank(message = "lastname cannot be blank")
    private String lastName;

    @Pattern(regexp = "[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}", message = "Must be a valid e-mail address")
    @NotBlank(message = "email cannot be blank")
    private String email;

    @NotNull(message = "Password cannot be empty")
    private String password;
}
