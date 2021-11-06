package architectures.microservices.design.saga.orchestrator.services.shipment.command.api.events;

import architectures.microservices.design.saga.orchestrator.library.commons.events.OrderShippedEvent;
import architectures.microservices.design.saga.orchestrator.services.shipment.command.api.data.Shipment;
import architectures.microservices.design.saga.orchestrator.services.shipment.command.api.data.ShipmentRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ShipmentsEventHandler {

    private final ShipmentRepository shipmentRepository;

    @Autowired
    public ShipmentsEventHandler(final ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @EventHandler
    public void on(OrderShippedEvent event) {
        Shipment shipment = new Shipment();
        BeanUtils.copyProperties(event, shipment);
        this.shipmentRepository.save(shipment);
    }
}
