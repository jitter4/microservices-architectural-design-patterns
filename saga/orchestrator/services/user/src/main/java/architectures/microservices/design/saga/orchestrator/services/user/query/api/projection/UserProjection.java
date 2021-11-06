package architectures.microservices.design.saga.orchestrator.services.user.query.api.projection;

import architectures.microservices.design.saga.orchestrator.library.commons.model.CardDetails;
import architectures.microservices.design.saga.orchestrator.library.commons.model.User;
import architectures.microservices.design.saga.orchestrator.library.commons.queries.GetUserPaymentDetailsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class UserProjection {

    @QueryHandler
    public User getUserPaymentDetails(GetUserPaymentDetailsQuery query) {
        // Ideally fetch from db
        CardDetails cardDetails = CardDetails.builder()
                .name("shubham")
                .validUntilMonth(01)
                .validUntilYear(25)
                .cardNumber("1234567890")
                .cvv(123)
                .build();
        return User.builder()
                .userId(query.getUserId())
                .firstName("shubham")
                .lastName("v")
                .cardDetails(cardDetails)
                .build();
    }

}
