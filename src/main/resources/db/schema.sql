CREATE TABLE if NOT EXISTS retrospect_room
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    subject    VARCHAR(255),
    details    TEXT,
    type       ENUM('KPT'),
    time_limit TIME,
    share_link VARCHAR(255),
    created_at datetime(6),
    updated_at datetime(6)
);
