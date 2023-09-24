CREATE TABLE tb_user
(
    id                      BIGINT NOT NULL
        PRIMARY KEY,
    name                    VARCHAR(255) NULL,
    password                VARCHAR(60) NULL,
    password_security_level VARCHAR(20) NULL,
    score                   INT NULL,
    user_id                 BIGINT NULL,
    CONSTRAINT
        FOREIGN KEY (user_id) REFERENCES tb_user (id)
);