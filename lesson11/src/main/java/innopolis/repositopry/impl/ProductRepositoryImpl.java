package innopolis.repositopry.impl;

import innopolis.config.DataBaseConfig;
import innopolis.entity.Product;
import innopolis.repositopry.ProductRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(DataBaseConfig.getPostgresqlDataSource());

    private final String SELECT_ALL_PRODUCTS = """
            select * from "order".product""";
    private final String INSERT_PRODUCT = """
            insert into "order".product(articleId, count, sum, orderDate) values (?, ?, ?, current_timestamp)""";

    @Override
    public void create(Product product) {
        if (product.getSum() <= 0) throw new RuntimeException("Сумма заказа должна быть строго больше 0");
        else jdbcTemplate.update(INSERT_PRODUCT, product.getArticleId(), product.getCount(), product.getSum());
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query(SELECT_ALL_PRODUCTS, new BeanPropertyRowMapper<>(Product.class));
    }
}
