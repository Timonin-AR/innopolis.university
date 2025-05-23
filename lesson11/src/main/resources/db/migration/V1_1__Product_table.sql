create table if not exists product (
    orderId int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    articleId int not null,
    count int not null,
    sum int not null,
    orderDate timestamp not null
);

insert into product(articleId, count, sum, orderDate)
values (1, 10, 500, current_timestamp),
       (2, 20, 1000, current_timestamp),
       (3, 30, 1500, current_timestamp),
       (4, 40, 2000, current_timestamp),
       (5, 50, 2500, current_timestamp);