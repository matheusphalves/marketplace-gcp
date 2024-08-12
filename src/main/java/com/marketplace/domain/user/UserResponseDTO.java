package com.marketplace.domain.user;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String emailAddress
) {
}
