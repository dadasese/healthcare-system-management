DROP TABLE IF EXISTS patient;
CREATE TABLE IF NOT EXISTS patient (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         last_name VARCHAR(100) NOT NULL,
                         phone_number VARCHAR(20) NOT NULL,
                         email VARCHAR(150) NOT NULL UNIQUE,
                         address VARCHAR(255) NOT NULL,
                         birth_date DATE NOT NULL,
                         created_at DATE NOT NULL
);

INSERT INTO patient (name, last_name, phone_number, email, address, birth_date, created_at) VALUES
('John', 'Doe', '555-1234', 'john.doe@example.com', '123 Main St, Springfield', '1988-05-12', '2025-01-10'),
('Jane', 'Smith', '555-5678', 'jane.smith@example.com', '456 Oak Ave, Metropolis', '1992-11-23', '2025-01-11'),
('Carlos', 'Martinez', '555-9012', 'carlos.martinez@example.com', '789 Pine Rd, Gotham', '1985-07-07', '2025-01-12'),
('Emily', 'Johnson', '555-3456', 'emily.johnson@example.com', '321 Cedar Blvd, Star City', '1999-02-28', '2025-01-13'),
('Michael', 'Brown', '555-7890', 'michael.brown@example.com', '654 Maple Ln, Central City', '1978-09-15', '2025-01-14');