package architectures.microservices.design.saga.orchestrator.services.order.command.api.events;

import architectures.microservices.design.saga.orchestrator.library.commons.events.OrderCompletedEvent;
import architectures.microservices.design.saga.orchestrator.library.commons.events.compensate.OrderCanceledEvent;
import architectures.microservices.design.saga.orchestrator.services.order.command.api.data.Order;
import architectures.microservices.design.saga.orchestrator.services.order.command.api.data.OrderRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderEventsHandler {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderEventsHandler(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @EventHandler
    public void on(OrderCreatedEvent event) {
        Order order = new Order();
        BeanUtils.copyProperties(event, order);
        this.orderRepository.save(order);
    }

    @EventHandler
    public void on(OrderCompletedEvent event) {
        Order order = this.orderRepository.findById(event.getOrderId()).get();
        order.setOrderStatus(event.getOrderStatus());
        this.orderRepository.save(order);
    }

    @EventHandler
    public void on(OrderCanceledEvent event) {
        Order order = this.orderRepository.findById(event.getOrderId()).get();
        order.setOrderStatus(event.getOrderStatus());
        this.orderRepository.save(order);
    }

}