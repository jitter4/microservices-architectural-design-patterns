package product.query.api.controller.projections;

import product.commons.data.ProductRepository;
import product.commons.model.ProductDTO;
import product.query.api.controller.queries.GetProductsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductProjection {

    private final ProductRepository productRepository;

    @Autowired
    public ProductProjection(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @QueryHandler
    public List<ProductDTO> handle(final GetProductsQuery getProductsQuery) {
        return this.productRepository.findAll()
                .stream()
                .map(product ->
                        ProductDTO.builder()
                        .name(product.getName())
                        .price(product.getPrice())
                        .quantity(product.getQuantity())
                        .build()
                ).collect(Collectors.toList());
    }
}
