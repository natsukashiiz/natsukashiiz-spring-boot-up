CREATE TABLE IF NOT EXISTS ii_categories
(
    id      BIGINT UNSIGNED AUTO_INCREMENT      NOT NULL PRIMARY KEY COMMENT 'id',
    name    VARCHAR(20)                         NOT NULL COMMENT 'name',
    version INT UNSIGNED                        NOT NULL DEFAULT 0 COMMENT 'version',
    cdt     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'created time',
    udt     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'updated time'
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000
    COMMENT 'table categories';

CREATE TABLE IF NOT EXISTS ii_users
(
    id       BIGINT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY COMMENT 'id',
    email    VARCHAR(30)                    NOT NULL UNIQUE COMMENT 'email',
    username VARCHAR(30)                    NOT NULL UNIQUE COMMENT 'username',
    password VARCHAR(255)                   NOT NULL COMMENT 'password',
    state    INTEGER                        NOT NULL DEFAULT 10 COMMENT '10:NORMAL, 20:DISABLED, 30:DESTROYED',
    version  INT UNSIGNED                   NOT NULL DEFAULT 0 COMMENT 'version',
    cdt      TIMESTAMP                               DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'created time',
    udt      TIMESTAMP                               DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'updated time'
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000
    COMMENT 'table users';

CREATE TABLE IF NOT EXISTS ii_blogs
(
    id          BIGINT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY COMMENT 'id',
    title       VARCHAR(50)                    NOT NULL COMMENT 'titile',
    content     LONGTEXT                       NOT NULL COMMENT 'content',
    category_id BIGINT UNSIGNED                NOT NULL COMMENT 'category id',
    uid         BIGINT UNSIGNED                NOT NULL COMMENT 'user id',
    publish     tinyint(1)                     NOT NULL DEFAULT 0 COMMENT 'publish',
    version     INT UNSIGNED                   NOT NULL DEFAULT 0 COMMENT 'version',
    cdt         TIMESTAMP                               DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'created time',
    udt         TIMESTAMP                               DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'updated time'
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000
    COMMENT 'table blogs';

CREATE TABLE IF NOT EXISTS ii_bookmarks
(
    id      BIGINT UNSIGNED AUTO_INCREMENT      NOT NULL PRIMARY KEY COMMENT 'id',
    blog_id BIGINT UNSIGNED                     NOT NULL COMMENT 'blog id',
    uid     BIGINT UNSIGNED                     NOT NULL COMMENT 'user id',
    version INT UNSIGNED                        NOT NULL DEFAULT 0 COMMENT 'version',
    cdt     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'created time',
    udt     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'updated time'
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000
    COMMENT 'table bookmarks';

CREATE TABLE IF NOT EXISTS ii_sign_history
(
    id      BIGINT UNSIGNED AUTO_INCREMENT      NOT NULL PRIMARY KEY COMMENT 'id',
    device  INTEGER                             NOT NULL COMMENT 'blog id',
    ipv4    VARCHAR(32)                         NOT NULL COMMENT 'blog id',
    ua      VARCHAR(255)                        NOT NULL COMMENT 'user id',
    uid     BIGINT UNSIGNED                     NOT NULL COMMENT 'user id',
    version INT UNSIGNED                        NOT NULL DEFAULT 0 COMMENT 'version',
    cdt     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'created time',
    udt     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'updated time'
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000
    COMMENT 'table sign history';

CREATE TABLE IF NOT EXISTS m_devices
(
    id   BIGINT UNSIGNED AUTO_INCREMENT      NOT NULL PRIMARY KEY COMMENT 'id',
    `name` VARCHAR(20)                         NOT NULL COMMENT 'device name'
) ENGINE = InnoDB
    COMMENT 'table devices';

