-- DROP TABLE board_user IF EXISTS;
-- DROP TABLE board_team IF EXISTS;
-- DROP TABLE board_member IF EXISTS;
-- DROP TABLE social_board IF EXISTS; 
-- DROP TABLE posting IF EXISTS;
-- DROP TABLE posting_comment IF EXISTS;
-- DROP TABLE board_seq IF EXISTS;

CREATE TABLE IF NOT EXISTS board_user (
    user_email      VARCHAR(50)     PRIMARY KEY,
    user_name       VARCHAR(10)     NOT NULL,
    phone_number    VARCHAR(15)     NOT NULL,  
    reg_date        DATETIME        NOT NULL        -- 조회는 하지 않는 데이터 입력일시 참고 목적
);

CREATE TABLE IF NOT EXISTS board_team (
    team_id         CHAR(3)         PRIMARY KEY,
    team_name       VARCHAR(30)     NOT NULL,
    admin_email     VARCHAR(50)     NOT NULL,
    reg_date        DATETIME        NOT NULL,        -- 조회는 하지 않는 데이터 입력일시 참고 목적
    UNIQUE KEY(team_name)
);

CREATE TABLE IF NOT EXISTS board_member (
    member_id       VARCHAR(53)     PRIMARY KEY,
    team_id         CHAR(3)         NOT NULL,
    member_email    VARCHAR(50)     NOT NULL,
    reg_date        DATETIME        NOT NULL,        -- 조회는 하지 않는 데이터 입력일시 참고 목적
    UNIQUE KEY (team_id, member_email)
);

CREATE TABLE IF NOT EXISTS social_board (
    board_id                CHAR(3)         PRIMARY KEY,
    board_name              VARCHAR(20)     NOT NULL,
    commentable_yn          CHAR(1)         NOT NULL,
    team_id                 CHAR(3)         NOT NULL,
    create_date             DATETIME        NOT NULL,
    UNIQUE KEY (board_name)
);

CREATE TABLE IF NOT EXISTS posting (
    posting_id              CHAR(8)         PRIMARY KEY,
    board_id                CHAR(3)         NOT NULL,
    title                   VARCHAR(100)    NOT NULL,
    writer_email            VARCHAR(50)     NOT NULL,
    read_count              INTEGER         NOT NULL,
    reg_date                DATETIME        NOT NULL,
    commentable_yn          CHAR(1)         NOT NULL,
    anonymous_comment_yn    CHAR(1)         NOT NULL,
    contents                VARCHAR(5000)
);
        
CREATE TABLE IF NOT EXISTS posting_comment (
    comment_id              INTEGER         PRIMARY KEY AUTO_INCREMENT,
    posting_id              VARCHAR(8)      NOT NULL,
    sequence                INTEGER         NOT NULL,
    comment                 VARCHAR(1000)   NOT NULL,
    writer_email            VARCHAR(50)    NOT NULL,
    written_time            DATETIME        NOT NULL
);

CREATE TABLE IF NOT EXISTS board_seq (
    seq_name                VARCHAR(20)     PRIMARY KEY,
    next_seq                INTEGER         NOT NULL
);

INSERT INTO board_seq (seq_name, next_seq)
VALUES
    ('team_id', 1),
    ('board_id', 1),
    ('comment_id', 1);

COMMIT;

