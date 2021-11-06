package architectures.microservices.design.saga.orchestrator.services.shipment.command.api.aggregate;

import architectures.microservices.design.saga.orchestrator.library.commons.comnmands.ShipOrderCommand;
import architectures.microservices.design.saga.orchestrator.library.commons.events.OrderShippedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
public class ShipmentAggregate {
    @AggregateIdentifier
    private String shipmentId;
    private String orderId;
    private String shipmentStatus;

    @CommandHandler
    public ShipmentAggregate(ShipOrderCommand shipOrderCommand) {
        // validate command
        OrderShippedEvent orderShippedEvent = OrderShippedEvent.builder()
                .shipmentId(shipOrderCommand.getShipmentId())
                .orderId(shipOrderCommand.getOrderId())
                .shipmentStatus("COMPLETED")
                .build();
        AggregateLifecycle.apply(orderShippedEvent);
    }

    @EventSourcingHandler
    public void on(OrderShippedEvent orderShippedEvent) {
        this.shipmentId = orderShippedEvent.getShipmentId();
        this.orderId = orderShippedEvent.getOrderId();
        this.shipmentStatus = orderShippedEvent.getShipmentStatus();
    }
}
