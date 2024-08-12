package com.marketplace.domain.category;

import java.util.UUID;

public record CategoryResponseDTO(
        UUID id,
        String title,
        String description
) {
}
