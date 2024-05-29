CREATE TABLE IF NOT EXISTS category(
id SERIAL PRIMARY KEY,
name TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS brand (
id SERIAL PRIMARY KEY,
name TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS model (
id SERIAL PRIMARY KEY,
name TEXT NOT NULL,
brand_Id int,
FOREIGN KEY (brand_Id) REFERENCES brand (id)
);

CREATE TABLE IF NOT EXISTS car(
id SERIAL PRIMARY KEY,
object_Id text NOT NULL,
brand_Id int,
FOREIGN KEY (brand_Id) REFERENCES brand (id),
production_year int,
model_Id int,
FOREIGN KEY (model_Id) REFERENCES model (id)
);

CREATE TABLE IF NOT EXISTS car_category(
car_Id int,
FOREIGN KEY (car_Id) REFERENCES car (id),
category_Id int,
FOREIGN KEY (category_Id) REFERENCES category (id)
);
