package com.marketplace.domain.product;

import java.util.UUID;

public record ProductDTO(
        String title,
        String description,
        Integer price,
        UUID categoryId,
        UUID userId
) {
}
