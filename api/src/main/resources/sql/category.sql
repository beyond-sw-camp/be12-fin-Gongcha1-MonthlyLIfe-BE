-- 📁 상위 카테고리 (parent_idx = NULL)
INSERT INTO category (name, parent_idx, icon_url) VALUES
('거실 가구', NULL, NULL),   -- idx = 1
('침실 가구', NULL, NULL),     -- idx = 2
('주방/식당 가구', NULL, NULL), -- idx = 3
('사무실 가구', NULL, NULL);     -- idx = 4

-- 📦 하위 카테고리
-- 거실 가구 (parent_idx = 1)
INSERT INTO category (name, parent_idx, icon_url) VALUES
('소파', 1, 'https://cnclib.com/web/product/big/201906/079f247d4cf5f2bcdc2753ec8df53cb1.jpg'),
('책장', 1, NULL),
('TV 스탠드', 1, NULL);

-- 침실 가구 (parent_idx = 2)
INSERT INTO category (name, parent_idx, icon_url) VALUES
('침대', 2, 'https://sitem.ssgcdn.com/58/31/01/item/1000025013158_i1_1200.jpg'),
('서랍장', 2, 'https://gaguclub.co.kr/web/product/big/202211/1f52ee1bcc0a3d89510bb7c335751905.jpg'),
('행거', 2, NULL);

-- 주방/식당 가구 (parent_idx = 3)
INSERT INTO category (name, parent_idx, icon_url) VALUES
('식탁', 3, 'https://m.hanyangmall.com/web/product/big/201902/becb83edbe938773f4a76b37a1ace0ef.jpg'),
('식탁 의자', 3, NULL),
('미니 테이블', 3, NULL);

-- 사무실 가구 (parent_idx = 4)
INSERT INTO category (name, parent_idx, icon_url) VALUES
('책상', 4, 'https://godomall.speedycdn.net/634854f35f19a5e1ce9d74d8f4e988d0/goods/186800750/image/detail/186800750_detail_085.jpg'),
('의자', 4, 'https://www.emons.co.kr/shop/data/goods/1590559118_5417_148876335870l0.jpg');
-- 3단계: 세부 카테고리
-- 소파



INSERT INTO category (name, parent_idx) VALUES
('2인용 소파', 5),
('3인용 소파', 5),
('코너 소파', 5);

-- 책장
INSERT INTO category (name, parent_idx) VALUES
('벽걸이형 책장', 6),
('자유형 책장', 6);

-- TV 스탠드
INSERT INTO category (name, parent_idx) VALUES
('벽걸이형', 7),
('독립형', 7);

-- 침대
INSERT INTO category (name, parent_idx) VALUES
('싱글 침대', 8),
('더블 침대', 8),
('퀸 침대', 8);

-- 서랍장
INSERT INTO category (name, parent_idx) VALUES
('2단 서랍장', 9),
('3단 서랍장', 9);

-- 행거
INSERT INTO category (name, parent_idx) VALUES
('일반 행거', 10),
('상부장 행거', 10);

-- 식탁
INSERT INTO category (name, parent_idx) VALUES
('4인용', 11),
('6인용', 11),
('8인용', 11);

-- 식탁 의자
INSERT INTO category (name, parent_idx) VALUES
('패브릭 의자', 12),
('가죽 의자', 12);

-- 미니 테이블
INSERT INTO category (name, parent_idx) VALUES
('원형 미니', 13),
('사각 미니', 13);

-- 책상
INSERT INTO category (name, parent_idx) VALUES
('일반 책상', 14),
('코너 책상', 14);

-- 의자
INSERT INTO category (name, parent_idx) VALUES
('메쉬 의자', 15),
('인체공학 의자', 15);