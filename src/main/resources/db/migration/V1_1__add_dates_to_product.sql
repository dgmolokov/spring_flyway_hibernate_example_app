alter table product
  add column created_at timestamp without time zone not null default now(),
  add column updated_at timestamp without time zone not null default now();