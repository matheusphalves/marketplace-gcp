package com.marketplace.domain.user;

import java.util.UUID;

public record UserUpdateResponseDTO(
        UUID id,
        String name
) {
}
