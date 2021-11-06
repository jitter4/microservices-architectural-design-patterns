package architectures.microservices.design.saga.orchestrator.library.commons.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private CardDetails cardDetails;
}
