package architectures.microservices.design.saga.orchestrator.library.commons.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CardDetails {
    private String name;
    private String cardNumber;
    private Integer validUntilMonth;
    private Integer validUntilYear;
    private Integer cvv;

}
