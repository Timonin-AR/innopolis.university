package innopolis.services;

import innopolis.entity.Product;
import innopolis.repositopry.impl.ProductRepositoryImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.OptionalDouble;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepositoryImpl productRepository;


    public void createProduct(Product product){
        productRepository.create(product);
    }

    public Integer getOrdersCount(){
       return productRepository.findAll().size();
    }

    public OptionalDouble getAverageSum() {
        return productRepository.findAll().stream().mapToInt(Product::getSum).average();
    }

}
