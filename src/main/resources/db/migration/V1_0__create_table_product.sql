drop table if exists product;
create table product (
  id bigserial primary key,
  name varchar(300) not null,
  price numeric not null,
  create_time timestamp without time zone not null default now(),
  update_time timestamp without time zone not null default now()
);