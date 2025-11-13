-- Create table
CREATE TABLE IF NOT EXISTS `users` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at DATETIME NOT NULL
);

-- Insert admin user
INSERT INTO `users` (email, password, role, status, created_at)
SELECT 'admin@hsm.com',
       '$2b$12$7hoRZfJrRKD2nIm2vHLs7OBETy.LWenXXMLKf99W8M4PUwO6KB7fu',
       'ADMIN',
       'ACTIVE',
       NOW()
WHERE NOT EXISTS (
    SELECT 1
    FROM `users`
    WHERE email = 'admin@hsm.com'
);