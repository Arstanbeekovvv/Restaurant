package restaurant.dto.request;

import lombok.Builder;

@Builder
public record CategoryRequest(Long id,
                              String name) {
}
