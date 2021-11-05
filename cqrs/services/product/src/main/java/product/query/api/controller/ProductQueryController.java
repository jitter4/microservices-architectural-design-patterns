package product.query.api.controller;

import product.commons.model.ProductDTO;
import product.query.api.controller.queries.GetProductsQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductQueryController {

    private final QueryGateway queryGateway;

    @Autowired
    public ProductQueryController(final QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        GetProductsQuery getProductsQuery = new GetProductsQuery();
        return this.queryGateway
                .query(getProductsQuery, ResponseTypes.multipleInstancesOf(ProductDTO.class))
                .join();
    }

}
