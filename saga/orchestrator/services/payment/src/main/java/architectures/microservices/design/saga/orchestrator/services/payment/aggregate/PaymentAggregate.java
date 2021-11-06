package architectures.microservices.design.saga.orchestrator.services.payment.aggregate;

import architectures.microservices.design.saga.orchestrator.library.commons.comnmands.ValidatePaymentCommand;
import architectures.microservices.design.saga.orchestrator.library.commons.comnmands.compensate.CancelPaymentCommand;
import architectures.microservices.design.saga.orchestrator.library.commons.events.PaymentCancelledEvent;
import architectures.microservices.design.saga.orchestrator.library.commons.events.PaymentProcessedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
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

    @CommandHandler
    public PaymentAggregate(CancelPaymentCommand command) {
        // Validate payment details
        log.info("CancelPaymentCommand for: OrderId: {}, PaymentId: {}", command.getOrderId(), command.getPaymentId());

        PaymentCancelledEvent paymentCancelledEvent = new PaymentCancelledEvent();
        BeanUtils.copyProperties(command, paymentCancelledEvent);
        AggregateLifecycle.apply(paymentCancelledEvent);

        log.info("CancelPaymentCommand applied");
    }

    @EventSourcingHandler
    public void on(PaymentCancelledEvent event) {
        this.paymentId = event.getPaymentId();
        this.orderId = event.getOrderId();
        this.paymentStatus = event.getPaymentStatus();
    }
}
