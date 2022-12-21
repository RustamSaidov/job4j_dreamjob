create TABLE post (
   id SERIAL PRIMARY KEY,
   name text,
   city_id integer,
   description text,
   date date,
   visible boolean
);
