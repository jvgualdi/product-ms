package tec.jvgualdi.product_ms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tec.jvgualdi.product_ms.domain.dto.ProductRegisterRequest;
import tec.jvgualdi.product_ms.domain.dto.ProductResponse;
import tec.jvgualdi.product_ms.mapper.ProductMapper;
import tec.jvgualdi.product_ms.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductResponse getProductById(UUID id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toResponse(product);
    }


    public ProductResponse createProduct(ProductRegisterRequest productRegister, List<String> imageUrls) {
        var product = productMapper.toEntity(productRegister);
        product.setImageUrl(imageUrls);
        product.setActive(product.getPrice().compareTo(new BigDecimal("0.0")) > 0 && product.getStock() > 0);
        product.setId(UUID.randomUUID());
        product = productRepository.save(product);
        return productMapper.toResponse(product);
    }

    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        var products = productRepository.findAllByActiveTrue(pageable);
        return products.map(productMapper::toResponse);
    }

    public ProductResponse updateProduct(UUID id, ProductRegisterRequest productRegister, List<String> imageUrls) {
        var existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setTitle(productRegister.title());
        existing.setDescription(productRegister.description());
        existing.setPrice(productRegister.price());
        existing.setStock(productRegister.stock());
        existing.setCategory(productRegister.category());
        existing.setImageUrl(imageUrls);
        existing.setActive(productRegister.price().compareTo(BigDecimal.ZERO) > 0 && productRegister.stock() > 0);

        var saved = productRepository.save(existing);
        return productMapper.toResponse(saved);
    }

}
