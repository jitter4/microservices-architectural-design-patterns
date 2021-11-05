package product.commons.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class ProductDTO {
    private String name;
    private BigDecimal price;
    private Integer quantity;
}
