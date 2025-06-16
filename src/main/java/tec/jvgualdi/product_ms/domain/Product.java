package tec.jvgualdi.product_ms.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Document(collection = "products")
@Data @AllArgsConstructor @NoArgsConstructor
public class Product {

    @Id
    private UUID id;
    private String title;
    private String description;
    private List<String> imageUrl;
    private BigDecimal price;
    private Integer stock;
    private String category;
    private boolean active;

}
