package architectures.microservices.design.saga.orchestrator.services.order.command.api.controller;

import architectures.microservices.design.saga.orchestrator.services.order.command.api.commands.CreateOrderCommand;
import architectures.microservices.design.saga.orchestrator.services.order.command.api.model.OrderDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderCommandController {

    private final CommandGateway commandGateway;

    @Autowired
    public OrderCommandController(final CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PutMapping
    public String createOrder(@RequestBody OrderDTO orderDTO) {
        CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                .orderId(UUID.randomUUID().toString())
                .productId(orderDTO.getProductId())
                .userid(orderDTO.getUserid())
                .addressId(orderDTO.getAddressId())
                .quantity(orderDTO.getQuantity())
                .orderStatus("CREATED")
                .build();
        return this.commandGateway.sendAndWait(createOrderCommand);
    }
}
