package architectures.microservices.design.saga.orchestrator.services.order.command.api.aggreagate;

import architectures.microservices.design.saga.orchestrator.library.commons.comnmands.CompleteOrderCommand;
import architectures.microservices.design.saga.orchestrator.library.commons.events.OrderCompletedEvent;
import architectures.microservices.design.saga.orchestrator.services.order.command.api.commands.CreateOrderCommand;
import architectures.microservices.design.saga.orchestrator.services.order.command.api.data.Order;
import architectures.microservices.design.saga.orchestrator.services.order.command.api.events.OrderCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Builder
@Data
@Aggregate
@AllArgsConstructor
@NoArgsConstructor
public class OrderAggregate {
    @AggregateIdentifier
    private String orderId;

    private String productId;
    private String userid;
    private String addressId;
    private Integer quantity;
    private String orderStatus;

    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand) {
        // validate
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        BeanUtils.copyProperties(createOrderCommand, orderCreatedEvent);
        AggregateLifecycle.apply(orderCreatedEvent);
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent orderCreatedEvent) {
        BeanUtils.copyProperties(orderCreatedEvent, this);
    }

    @CommandHandler
    public OrderAggregate(CompleteOrderCommand command) {
        // validate
        OrderCompletedEvent event = new OrderCompletedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(OrderCompletedEvent event) {
//        this.orderId = event.getOrderId();
        this.orderStatus = event.getOrderStatus();
    }
}
