package architectures.microservices.design.saga.orchestrator.services.order.command.api.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
}
