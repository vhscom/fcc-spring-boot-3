create table if not exists run (
  id serial primary key,
  title varchar not null,
  started_on timestamp not null,
  completed_on timestamp not null,
  miles int not null
);