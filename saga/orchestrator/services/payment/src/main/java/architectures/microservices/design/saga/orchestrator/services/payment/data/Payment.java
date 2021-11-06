package architectures.microservices.design.saga.orchestrator.services.payment.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    private String paymentId;

    private String orderId;
    private Date timeStamp;
    private String paymentStatus;

}
