INSERT INTO category (name) VALUES
    ('SUV'),
    ('Sedan'),
    ('Hatchback'),
    ('Convertible');

INSERT INTO brand (name) VALUES
    ('Toyota'),
    ('Honda'),
    ('BMW'),
    ('Ford');

INSERT INTO model (name, brand_Id) VALUES
    ('Camry', 1),       -- Toyota
    ('Accord', 2),      -- Honda
    ('3 Series', 3),    -- BMW
    ('Focus', 4);       -- Ford

INSERT INTO car (object_Id, brand_Id, production_year, model_Id) VALUES
    ('ABC123', 1, 2018, 1),    -- Toyota Camry
    ('DEF456', 2, 2020, 2),    -- Honda Accord
    ('GHI789', 3, 2019, 3),    -- BMW 3 Series
    ('JKL012', 4, 2017, 4);    -- Ford Focus

INSERT INTO car_category (car_Id, category_Id) VALUES
    (1, 1),    -- SUV (Toyota Camry)
    (2, 2),    -- Sedan (Honda Accord)
    (3, 3),    -- Hatchback (BMW 3 Series)
    (4, 2),    -- Sedan (Ford Focus)
    (4, 3);    -- Hatchback (Ford Focus)