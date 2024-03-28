package restaurant.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import restaurant.enums.RestType;
import restaurant.validation.*;

import java.time.LocalDate;

@Builder
public record RestaurantRequest(@NotBlank(message = "the name must be unique")
                                String name,
                                @NotBlank(message = "the location should not be empty")
                                String location,
                                @Enumerated(EnumType.STRING)
                                RestType restType,
                                @ExperienceValidation
                                int service,
                                @NotBlank(message = "the last name should not be empty")
                                String lastName,
                                @NotBlank(message = "the first name should not be empty")
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
                                int experience
) {
}
