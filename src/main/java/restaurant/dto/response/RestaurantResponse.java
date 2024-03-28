package restaurant.dto.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import restaurant.enums.RestType;
import restaurant.validation.ExperienceValidation;

@Builder
public record RestaurantResponse(Long id,
                                 String name,
                                 String location,
                                 RestType restType,
                                 int numberOfEmployees,
                                 int service
                                 )
{}
