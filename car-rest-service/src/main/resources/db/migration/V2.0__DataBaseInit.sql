CREATE TABLE IF NOT EXISTS Category(
id SERIAL PRIMARY KEY,
name TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS Brand (
id SERIAL PRIMARY KEY,
name TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS Model (
id SERIAL PRIMARY KEY,
name TEXT NOT NULL UNIQUE,
brand_Id int,
FOREIGN KEY (brand_Id) REFERENCES Brand (id)
);

CREATE TABLE IF NOT EXISTS Car(
id SERIAL PRIMARY KEY,
object_Id text NOT NULL,
brand_Id int,
FOREIGN KEY (brand_Id) REFERENCES Brand (id),
productionYear int,
model_Id int,
FOREIGN KEY (model_Id) REFERENCES Model (id)
);

CREATE TABLE IF NOT EXISTS CarToCategoryReferences(
car_Id int,
FOREIGN KEY (car_Id) REFERENCES Car (id),
category_Id int,
FOREIGN KEY (category_Id) REFERENCES Category (id)
);
