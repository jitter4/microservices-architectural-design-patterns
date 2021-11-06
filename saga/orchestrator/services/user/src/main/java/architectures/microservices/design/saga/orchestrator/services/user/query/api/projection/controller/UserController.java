package architectures.microservices.design.saga.orchestrator.services.user.query.api.projection.controller;

import architectures.microservices.design.saga.orchestrator.library.commons.model.User;
import architectures.microservices.design.saga.orchestrator.library.commons.queries.GetUserPaymentDetailsQuery;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private transient final QueryGateway queryGateway;

    @Autowired
    public UserController(final QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("{userId}")
    public User getUserPaymentDetails(@PathVariable String userId) {
        return this.queryGateway.query(GetUserPaymentDetailsQuery.of(userId), User.class)
                .join();
    }

}
