package innopolis.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Product {
    private Integer orderId;
    private Integer articleId;
    private Integer count;
    private Integer sum;
    private Timestamp orderDate;
}
