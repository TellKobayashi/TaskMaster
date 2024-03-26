-- Migrations have failed validationの時はflywayRepairを実行する。
CREATE TABLE users (
    id                  INT         NOT NULL AUTO_INCREMENT,
    name                VARCHAR(16) NOT NULL UNIQUE KEY,
    display_name        VARCHAR(16) NOT NULL,
    password            varchar(16) NOT NULL,
    created_at          DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    last_logged_in_at   DATETIME,
    PRIMARY KEY (`id`)
)
