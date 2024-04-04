CREATE TABLE member (
    member_id VARCHAR(50) PRIMARY KEY,
    password VARCHAR(50) NOT NULL,
    nickname VARCHAR(50) NOT NULL,
    age INT NULL
);

CREATE TABLE article (
    article_id IDENTITY PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    member_id VARCHAR(50),
    FOREIGN KEY (member_id) REFERENCES member(member_id) ON DELETE CASCADE
);

INSERT INTO member (member_id, password, nickname, age)
VALUES
('jony123', 'jonyjony1212', '조니', 40),
('sylvie456', 'ssvv999', '실비', 20),
('nana777', 'nn111', '나나', 4);

INSERT INTO article (title, content, created_at, updated_at, member_id)
VALUES
('안녕 얘들아!', '나는 새로 온 전학생 조니야', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'jony123'),
('수학책 빌려줄사람?', '다음 시간 수학인데 깜빡하고 책 안가져옴 ㅠㅠ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'sylvie456'),
('실비야 사실 널 좋아해!', 'ㅈㄱㄴ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'nana777');