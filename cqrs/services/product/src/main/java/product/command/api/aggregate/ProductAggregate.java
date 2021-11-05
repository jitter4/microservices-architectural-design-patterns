package product.command.api.aggregate;

import product.command.api.commands.CreateProductCommand;
import product.command.api.events.ProductCreatedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Aggregate
@NoArgsConstructor
public class ProductAggregate {

    @AggregateIdentifier
    private String productId;

    private String name;
    private BigDecimal price;
    private Integer quantity;

    @CommandHandler
    public ProductAggregate(final CreateProductCommand createProductCommand) {
        // validate
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
        BeanUtils.copyProperties(createProductCommand, productCreatedEvent);
        AggregateLifecycle.apply(productCreatedEvent);
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        this.productId = productCreatedEvent.getProductId();
        this.quantity = productCreatedEvent.getQuantity();
        this.name = productCreatedEvent.getName();
        this.price = productCreatedEvent.getPrice();
    }
}
