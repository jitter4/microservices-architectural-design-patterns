package architectures.microservices.design.saga.orchestrator.services.order.command.api.events;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class OrderCreatedEvent {
    @TargetAggregateIdentifier
    private String orderId;

    private String productId;
    private String userid;
    private String addressId;
    private Integer quantity;
    private String orderStatus;
}
