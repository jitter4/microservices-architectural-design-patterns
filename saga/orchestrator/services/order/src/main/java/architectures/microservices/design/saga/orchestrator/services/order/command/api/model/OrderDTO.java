package architectures.microservices.design.saga.orchestrator.services.order.command.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private String productId;
    private String userid;
    private String addressId;
    private Integer quantity;
}
