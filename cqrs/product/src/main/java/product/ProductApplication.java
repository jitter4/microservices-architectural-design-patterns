package product;

import product.command.api.exception.ProductServiceEventsErrorHandler;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}

	@Autowired
	public void configure(EventProcessingConfigurer eventProcessingConfigurer) {
		eventProcessingConfigurer.registerListenerInvocationErrorHandler(
			"product",
				configuration -> new ProductServiceEventsErrorHandler()
		);
	}

}
