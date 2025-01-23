package innopolis.repositopry;

import innopolis.entity.Product;

import java.util.List;

public interface ProductRepository {

    void create(Product product);
    List<Product> findAll();
}
