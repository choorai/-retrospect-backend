CREATE TABLE user (
    user_id VARCHAR(320) NOT NULL PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(5) NOT NULL,
    department VARCHAR(50) NOT NULL,
    position VARCHAR(50) NOT NULL,
    company_name VARCHAR(50) NOT NULL
);
