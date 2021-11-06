package architectures.microservices.design.saga.orchestrator.services.order.command.api.saga;

import architectures.microservices.design.saga.orchestrator.library.commons.comnmands.CompleteOrderCommand;
import architectures.microservices.design.saga.orchestrator.library.commons.comnmands.ShipOrderCommand;
import architectures.microservices.design.saga.orchestrator.library.commons.comnmands.ValidatePaymentCommand;
import architectures.microservices.design.saga.orchestrator.library.commons.comnmands.compensate.CancelOrderCommand;
import architectures.microservices.design.saga.orchestrator.library.commons.comnmands.compensate.CancelPaymentCommand;
import architectures.microservices.design.saga.orchestrator.library.commons.events.OrderCompletedEvent;
import architectures.microservices.design.saga.orchestrator.library.commons.events.OrderShippedEvent;
import architectures.microservices.design.saga.orchestrator.library.commons.events.PaymentCancelledEvent;
import architectures.microservices.design.saga.orchestrator.library.commons.events.PaymentProcessedEvent;
import architectures.microservices.design.saga.orchestrator.library.commons.events.compensate.OrderCanceledEvent;
import architectures.microservices.design.saga.orchestrator.library.commons.model.User;
import architectures.microservices.design.saga.orchestrator.library.commons.queries.GetUserPaymentDetailsQuery;
import architectures.microservices.design.saga.orchestrator.services.order.command.api.events.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Saga
@Slf4j
public class OrderProcessingSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    private void handle(OrderCreatedEvent event) {
        log.info("Order created event: {}", event.getOrderId());

        GetUserPaymentDetailsQuery getUserPaymentDetailsQuery = GetUserPaymentDetailsQuery.of(event.getUserid());

        User user = null;
        try {
            user = this.queryGateway.query(getUserPaymentDetailsQuery, User.class)
                    .join();
        } catch (final Exception ex) {
            log.error(ex.getMessage());
            this.cancelOrderCommand(event.getOrderId());
        }

        ValidatePaymentCommand validatePaymentCommand =
                ValidatePaymentCommand.builder()
                        .paymentId(UUID.randomUUID().toString())
                        .orderId(event.getOrderId())
                        .cardDetails(user.getCardDetails())
                        .build();
        this.commandGateway.sendAndWait(validatePaymentCommand);
    }

    private void cancelOrderCommand(String orderId) {
        CancelOrderCommand cancelOrderCommand = CancelOrderCommand.builder()
                .orderId(orderId)
                .build();
        this.commandGateway.sendAndWait(cancelOrderCommand);
    }

    @SagaEventHandler(associationProperty = "orderId")
    private void handle(PaymentProcessedEvent event) {
        log.info("PaymentProcessedEvent event: {}", event.getOrderId());
        try {

            if (true)
                throw new Exception();

            ShipOrderCommand shipOrderCommand =
                    ShipOrderCommand.builder()
                            .shipmentId(UUID.randomUUID().toString())
                            .orderId(event.getOrderId())
                            .build();
            this.commandGateway.sendAndWait(shipOrderCommand);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            // Start compensating transaction
            this.cancelPaymentCommand(event);
        }
    }

    private void cancelPaymentCommand(PaymentProcessedEvent event) {
        CancelPaymentCommand cancelPaymentCommand = CancelPaymentCommand.builder()
                .paymentId(event.getPaymentId())
                .orderId(event.getOrderId())
                .build();
        this.commandGateway.sendAndWait(cancelPaymentCommand);
    }

    @SagaEventHandler(associationProperty = "orderId")
    private void handle(OrderShippedEvent event) {
        log.info("OrderShippedEvent event: {}", event.getOrderId());
        try {
            CompleteOrderCommand command =
                    CompleteOrderCommand.builder()
                            .orderId(event.getOrderId())
                            .orderStatus("APPROVED")
                            .build();
            this.commandGateway.sendAndWait(command);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            // Start compensating transaction
        }
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    private void handle(OrderCompletedEvent event) {
        log.info(String.format("OrderCompletedEvent event: {}", event.getOrderId()));
    }

    @SagaEventHandler(associationProperty = "orderId")
    private void handle(OrderCanceledEvent event) {
        log.info("OrderCanceledEvent OrderId: {}", event.getOrderId());

//            AllertCancellationCommand command =
//                    AllertCancellationCommand.builder()
//                            .orderId(event.getOrderId())
//                            .orderStatus("APPROVED")
//                            .build();
//            this.commandGateway.sendAndWait(command);

    }

    @SagaEventHandler(associationProperty = "orderId")
    private void handle(PaymentCancelledEvent event) {
        log.info("PaymentCancelledEvent OrderId: {}", event.getOrderId());
//            AllertCancellationCommand command =
//                    AllertCancellationCommand.builder()
//                            .orderId(event.getOrderId())
//                            .orderStatus("APPROVED")
//                            .build();
//            this.commandGateway.sendAndWait(command);
        this.cancelOrderCommand(event.getOrderId());
    }

}
