package innopolis.controllers;

import innopolis.entity.Product;
import innopolis.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/avg")
    private String getAvgSum() {
        return "Среднее чек: " + productService.getAverageSum().orElseThrow(() -> new IllegalStateException("Не получилось получить значение"));
    }

    @GetMapping("/count")
    private String getCountOrder() {
        return "Количество заказов: " + productService.getOrdersCount();
    }

    @PostMapping(value = "/product", consumes = MediaType.APPLICATION_JSON_VALUE)
    private void createProduct(@RequestBody Product product){
        productService.createProduct(product);
    }

}
