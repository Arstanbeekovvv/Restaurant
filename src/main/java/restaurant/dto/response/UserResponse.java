package restaurant.dto.response;

import lombok.Builder;
import restaurant.enums.Role;

import java.time.LocalDate;

@Builder
public record UserResponse(Long id,
                           String fullName,
                           LocalDate dateOfBirth,
                           String email,
                           String phoneNumber,
                           Role role,
                           int experience) {
}
