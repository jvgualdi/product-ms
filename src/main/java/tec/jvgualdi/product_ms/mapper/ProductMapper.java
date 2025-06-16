package tec.jvgualdi.product_ms.mapper;

import org.mapstruct.Mapper;
import tec.jvgualdi.product_ms.domain.Product;
import tec.jvgualdi.product_ms.domain.dto.ProductRegisterRequest;
import tec.jvgualdi.product_ms.domain.dto.ProductResponse;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductRegisterRequest productDto);

    ProductResponse toResponse(Product product);
}
