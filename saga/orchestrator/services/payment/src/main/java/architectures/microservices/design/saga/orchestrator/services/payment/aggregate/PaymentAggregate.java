package architectures.microservices.design.saga.orchestrator.services.payment.aggregate;

import architectures.microservices.design.saga.orchestrator.library.commons.comnmands.ValidatePaymentCommand;
import architectures.microservices.design.saga.orchestrator.library.commons.events.PaymentProcessedEvent;
import architectures.microservices.design.saga.orchestrator.library.commons.model.CardDetails;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@Slf4j
public class PaymentAggregate {
    @AggregateIdentifier
    private String paymentId;
    private String orderId;
    private String paymentStatus;

    @CommandHandler
    public PaymentAggregate(ValidatePaymentCommand command) {
        // Validate payment details
        log.info("validate payment command for: OrderId: {}, PaymentId: {}", command.getOrderId(), command.getPaymentId());

        PaymentProcessedEvent paymentProcessedEvent = new PaymentProcessedEvent();
        BeanUtils.copyProperties(command, paymentProcessedEvent);
        AggregateLifecycle.apply(paymentProcessedEvent);
        log.info("PaymentProcessedEvent applied");
    }

    @EventSourcingHandler
    public void on(PaymentProcessedEvent event) {
        this.paymentId = event.getPaymentId();
        this.orderId = event.getOrderId();
    }
}
