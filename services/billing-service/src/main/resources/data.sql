DROP TABLE IF EXISTS billing;
CREATE TABLE IF NOT EXISTS billing (
                         id SERIAL PRIMARY KEY,
                         patient_id BIGINT NOT NULL,
                         name VARCHAR(20) NOT NULL,
                         email VARCHAR(150) NOT NULL UNIQUE,
                         status VARCHAR(20) NOT NULL
);