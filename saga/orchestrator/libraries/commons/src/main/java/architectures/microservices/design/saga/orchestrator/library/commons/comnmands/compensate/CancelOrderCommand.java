package architectures.microservices.design.saga.orchestrator.library.commons.comnmands.compensate;

import lombok.Builder;
import lombok.Data;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
@Value
public class CancelOrderCommand {
    @TargetAggregateIdentifier
    private String orderId;
    private String orderStatus = "CANCELED";
}
