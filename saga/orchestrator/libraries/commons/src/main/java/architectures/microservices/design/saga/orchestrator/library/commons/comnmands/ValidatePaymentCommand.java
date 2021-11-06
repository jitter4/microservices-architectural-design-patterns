package architectures.microservices.design.saga.orchestrator.library.commons.comnmands;

import architectures.microservices.design.saga.orchestrator.library.commons.model.CardDetails;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
@Data
public class ValidatePaymentCommand {

    @TargetAggregateIdentifier
    private String paymentId;
    private String orderId;
    private CardDetails cardDetails;
}
