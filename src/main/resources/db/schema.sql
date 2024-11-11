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

CREATE TABLE if NOT EXISTS card
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    content    VARCHAR(255),
    type       enum('KEEP','PROBLEM','TRY'),
    retrospect_room_id BIGINT,
    user_id BIGINT,
    created_at datetime(6),
    updated_at datetime(6),
    CONSTRAINT fk_retrospect_room FOREIGN KEY (retrospect_room_id) REFERENCES retrospect_room(id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES user(id)
);
