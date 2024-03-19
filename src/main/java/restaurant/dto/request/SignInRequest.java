package restaurant.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import restaurant.enums.Role;
import restaurant.validation.*;

import java.time.LocalDate;

public record SignInRequest(@EmailValidation @Email
                            String email,
                            @PasswordValidation
                            String password
)
{}
