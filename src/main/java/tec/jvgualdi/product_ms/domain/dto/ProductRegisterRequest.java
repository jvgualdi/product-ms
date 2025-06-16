package tec.jvgualdi.product_ms.domain.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ProductRegisterRequest(@NotBlank String title, @NotBlank String description,
                                     @NotNull @DecimalMin("0.0") BigDecimal price, @NotNull Integer stock,
                                     @NotEmpty String category) {
}
