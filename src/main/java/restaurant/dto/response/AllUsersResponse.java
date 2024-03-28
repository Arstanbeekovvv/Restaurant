package restaurant.dto.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import restaurant.enums.Role;

import java.time.LocalDate;

@Builder
public record AllUsersResponse(
        Long id,
        String lastName,
        String firstName,
        LocalDate dateOfBirth,
        String email,
        String phoneNumber,
        @Enumerated(EnumType.STRING)
        Role role,
        int experience
) {
}
