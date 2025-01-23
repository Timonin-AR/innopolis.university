package innopolis.actuator;

import innopolis.repositopry.ProductRepository;
import innopolis.services.ProductService;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomActuator {
    private final Tag bussinesTag = Tag.of("business", "metric");

    @Bean
    public MeterBinder bindCountOrders(ProductRepository productRepository) {
        return meterRegistry -> meterRegistry.gauge("count_orders", List.of(bussinesTag), productRepository.findAll().size());
    }

    @Bean
    public MeterBinder bindAvgSum(ProductService ProductService) {
        return meterRegistry -> meterRegistry.gauge("avg_sum", List.of(bussinesTag), ProductService.getAverageSum().getAsDouble());
    }


}
