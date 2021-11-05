package product.command.api.controller;

import product.command.api.commands.CreateProductCommand;
import product.commons.model.ProductDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductCommandController {

    private final CommandGateway commandGateway;

    @Autowired
    public ProductCommandController(final CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String addProduct(@RequestBody ProductDTO productDTO) {
        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .productId(UUID.randomUUID().toString())
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .quantity(productDTO.getQuantity())
                .build();
        String result = this.commandGateway.sendAndWait(createProductCommand);
        return result;
    }

}
