package com.marketplace.domain.product;

import java.util.UUID;

public record ProductUpdateDTO(
        String title,
        String description,
        Integer price,
        UUID categoryId,
        UUID userId
) {
}
