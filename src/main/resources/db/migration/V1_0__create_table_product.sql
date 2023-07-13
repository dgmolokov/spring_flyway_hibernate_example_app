drop table if exists product;
create table product (
  id bigserial primary key,
  name varchar(300) not null,
  price numeric not null
);