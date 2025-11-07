-- V1: Initial schema for ramen project

-- 라멘집 테이블
CREATE TABLE IF NOT EXISTS ramen_shop
(
    ramen_shop_id BIGINT       NOT NULL AUTO_INCREMENT,
    name          VARCHAR(255) NOT NULL,
    address       VARCHAR(255) NOT NULL,
    closed_days   VARCHAR(50),
    open_time     TIME,
    close_time    TIME,
    break_start   TIME,
    break_end     TIME,
    tags          VARCHAR(255),
    instagram_url VARCHAR(255),
    visit_count   INT DEFAULT 0,
    review_count  INT DEFAULT 0,
    image_url     VARCHAR(500),
    PRIMARY KEY (ramen_shop_id)
);

-- 이벤트 메뉴
CREATE TABLE IF NOT EXISTS event_menu
(
    event_menu_id BIGINT       NOT NULL AUTO_INCREMENT,
    ramen_shop_id BIGINT       NOT NULL,
    name          VARCHAR(255) NOT NULL,
    description   TEXT,
    price         INT,
    badge_text    VARCHAR(50),
    start_date    DATE,
    end_date      DATE,
    image_url     VARCHAR(500),
    PRIMARY KEY (event_menu_id)
);

-- 일반 메뉴
CREATE TABLE IF NOT EXISTS menu
(
    menu_id       BIGINT       NOT NULL AUTO_INCREMENT,
    ramen_shop_id BIGINT       NOT NULL,
    name          VARCHAR(255) NOT NULL,
    price         INT,
    is_signature  BOOLEAN DEFAULT FALSE,
    image_url     VARCHAR(500),
    PRIMARY KEY (menu_id)
);

-- 회원
CREATE TABLE IF NOT EXISTS member
(
    member_id                BIGINT       NOT NULL AUTO_INCREMENT,
    nickname                 VARCHAR(100) NOT NULL,
    image_url                VARCHAR(500),
    visited_restaurant_count INT DEFAULT 0,
    visit_count              INT DEFAULT 0,
    photo_count              INT DEFAULT 0,
    bookmark_count           INT DEFAULT 0,
    PRIMARY KEY (member_id)
);

-- 사진
CREATE TABLE IF NOT EXISTS poorf_picture
(
    photo_id      BIGINT       NOT NULL AUTO_INCREMENT,
    ramen_shop_id BIGINT       NOT NULL,
    member_id     BIGINT       NOT NULL,
    image_url     VARCHAR(500) NOT NULL,
    upload_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (photo_id)
);

-- 지역
CREATE TABLE IF NOT EXISTS region
(
    region_id     BIGINT       NOT NULL AUTO_INCREMENT,
    ramen_shop_id BIGINT       NOT NULL,
    region_name   VARCHAR(100) NOT NULL,
    total         INT DEFAULT 0,
    PRIMARY KEY (region_id)
);

-- 북마크
CREATE TABLE IF NOT EXISTS bookmark
(
    bookmark_id   BIGINT NOT NULL AUTO_INCREMENT,
    member_id     BIGINT NOT NULL,
    ramen_shop_id BIGINT NOT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (bookmark_id)
);

-- 투표
CREATE TABLE IF NOT EXISTS vote
(
    vote_id       BIGINT NOT NULL AUTO_INCREMENT,
    menu_id       BIGINT NOT NULL,
    ramen_shop_id BIGINT NOT NULL,
    member_id     BIGINT NOT NULL,
    vote_date     DATE DEFAULT CURRENT_DATE,
    PRIMARY KEY (vote_id)
);
