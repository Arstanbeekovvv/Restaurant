package restaurant.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import restaurant.enums.RestType;
import restaurant.validation.ExperienceValidation;

public record RestaurantRequestUpdate(@NotBlank(message = "the name must be unique")
                                      String name,
                                      @NotBlank(message = "the location should not be empty")
                                      String location,
                                      @Enumerated(EnumType.STRING)
                                      RestType restType,
                                      @ExperienceValidation
                                      int service
                                      ) {
}
