package tec.jvgualdi.product_ms.domain.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductResponse( UUID id,
         String title,
         String description,
         List<String>imageUrl,
         BigDecimal price,
         Integer stock,
         String category,
         boolean active) {
}
