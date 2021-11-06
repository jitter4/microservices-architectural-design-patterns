package architectures.microservices.design.saga.orchestrator.library.commons.queries;

import lombok.NoArgsConstructor;
import lombok.Value;

@Value(staticConstructor = "of")
public class GetUserPaymentDetailsQuery {
    private String userId;
}
