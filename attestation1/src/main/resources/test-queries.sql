--Запросы для таблицы cars
select * from tire.cars;
select * from tire.cars c where c.id=?;
select * from tire.cars c where c.car_number=?;
insert into tire.cars (client_id, car_name, car_model, car_number) values (?,?,?,?);
delete from tire.cars c where c.id=?;
update tire.cars c set car_number=? where c.id=?;

--Запросы для таблицы clients
select * from tire.clients;
insert into tire.clients (first_name, midl_name, last_name, date_of_birth, phone_number) values (?,?,?,?,?);
delete from tire.clients c where c.id=?;
update tire.clients c set phone_number=? where c.id=?;

--Запросы для таблицы orders
select * from tire.orders;
insert into tire.orders (cost_of_work, car_id, client_id) values (?,?,?);
delete from tire.orders c where c.client_id=?;
update tire.orders c set cost_of_work=? where c.client_id=?;\

--Запросы для таблицы clients_x_cars
select * from tire.clients_x_cars;
delete from tire.clients_x_cars cc where cc.id=?;
update tire.clients_x_cars cc set car_id=?, client_id=? where cc.id=?;

--Запросы с объединением таблиц
select ca.car_name, ca.car_model, ca.car_number,
cl.first_name, cl.midl_name, cl.last_name
from "tire".cars ca
left join "tire".clients cl on cl.id = ca.client_id;