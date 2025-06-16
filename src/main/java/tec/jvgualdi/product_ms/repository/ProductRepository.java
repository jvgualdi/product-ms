package tec.jvgualdi.product_ms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import tec.jvgualdi.product_ms.domain.Product;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends MongoRepository<Product, UUID> {

    List<Product> findByCategory(String category);
    Page<Product> findAllByActiveTrue(Pageable pageable);
    List<Product> findByActiveFalse();
    List<Product> findByTitleContainingIgnoreCase(String title);
}
