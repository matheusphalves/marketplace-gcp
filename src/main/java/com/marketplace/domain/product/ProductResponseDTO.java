package com.marketplace.domain.product;

import java.util.UUID;

public record ProductResponseDTO(
        UUID id,
        String title,
        String description,
        Integer price,
        UUID categoryId,
        UUID userId
) {
}
