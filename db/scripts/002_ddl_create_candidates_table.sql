create TABLE candidate (
   id SERIAL PRIMARY KEY,
   name text,
   city_id integer,
   description text,
   created date,
   visible boolean,
   photo bytea
);
