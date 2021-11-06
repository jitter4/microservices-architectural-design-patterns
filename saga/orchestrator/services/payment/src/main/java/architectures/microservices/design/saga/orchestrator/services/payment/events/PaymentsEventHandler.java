package architectures.microservices.design.saga.orchestrator.services.payment.events;

import architectures.microservices.design.saga.orchestrator.library.commons.events.PaymentProcessedEvent;
import architectures.microservices.design.saga.orchestrator.services.payment.data.Payment;
import architectures.microservices.design.saga.orchestrator.services.payment.data.PaymentRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PaymentsEventHandler {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentsEventHandler(final PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @EventHandler
    public void on(PaymentProcessedEvent event) {
        Payment payment = Payment.builder()
                .paymentId(event.getPaymentId())
                .orderId(event.getOrderId())
                .paymentStatus("COMPLETED")
                .timeStamp(new Date())
                .build();
        this.paymentRepository.save(payment);
    }
}
