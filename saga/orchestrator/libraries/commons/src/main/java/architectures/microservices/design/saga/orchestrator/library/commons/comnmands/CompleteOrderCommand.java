package architectures.microservices.design.saga.orchestrator.library.commons.comnmands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
@Data
public class CompleteOrderCommand {
    @TargetAggregateIdentifier
    private String orderId;
    private String orderStatus;
}
