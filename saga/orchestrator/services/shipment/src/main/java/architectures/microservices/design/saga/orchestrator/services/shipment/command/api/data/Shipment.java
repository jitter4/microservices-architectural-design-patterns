package architectures.microservices.design.saga.orchestrator.services.shipment.command.api.data;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Shipment {
    @Id
    private String shipmentId;
    private String orderId;
    private String shipmentStatus;
}
