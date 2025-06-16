package tec.jvgualdi.product_ms.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tec.jvgualdi.product_ms.domain.dto.ProductRegisterRequest;
import tec.jvgualdi.product_ms.domain.dto.ProductResponse;
import tec.jvgualdi.product_ms.service.ImageStorageService;
import tec.jvgualdi.product_ms.service.ProductService;

import java.net.URI;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ImageStorageService imageService;

    public ProductController(ProductService productService, ImageStorageService imageService) {
        this.productService = productService;
        this.imageService = imageService;
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable UUID id) {
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponse> create(
            @RequestPart("product") @Valid ProductRegisterRequest productRegister,
            @RequestPart("images") List<MultipartFile> images
    ) {
        List<String> imageUrls = imageService.uploadAll(images);
        ProductResponse created = productService.createProduct(productRegister, imageUrls);
        return ResponseEntity.created(
                        URI.create("/product/" + created.id()))
                .body(created);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(@PageableDefault(size = 5) Pageable pageable ){
        Page<ProductResponse> products = productService.getAllProducts(pageable);
        return ResponseEntity.ok(products);
    }

}
