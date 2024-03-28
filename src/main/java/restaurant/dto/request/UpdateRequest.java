package restaurant.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import restaurant.validation.*;

import java.time.LocalDate;

public record UpdateRequest(
        String lastName,
        String firstName,
        @DateOfBirthValidation
        LocalDate dateOfBirth,
        @Email @EmailValidation
        String email,
        @PasswordValidation
        String password,
        @PhoneNumberValidation
        String phoneNumber,
        @ExperienceValidation
        @NotNull
        Integer experience
) {
}
