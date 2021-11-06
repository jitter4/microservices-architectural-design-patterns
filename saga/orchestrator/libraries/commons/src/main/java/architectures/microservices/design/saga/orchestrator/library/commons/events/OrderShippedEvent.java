package architectures.microservices.design.saga.orchestrator.library.commons.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderShippedEvent {
    private String shipmentId;
    private String orderId;
    private String shipmentStatus;
}
