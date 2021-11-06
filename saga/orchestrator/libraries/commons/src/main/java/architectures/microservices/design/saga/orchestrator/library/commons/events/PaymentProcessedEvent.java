package architectures.microservices.design.saga.orchestrator.library.commons.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentProcessedEvent {
    private String paymentId;
    private String orderId;
}
