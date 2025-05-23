-- 파이썬 코드 먼저 삽입 후에 이거 삽입

INSERT INTO user (id, phone_number, password, name, email, is_delayed, address1, address2, birth, created_at)
VALUES
    ('user01', '01011112222', 'pass1', '홍길동', 'user01@example.com', b'0', '서울시 강남구', '101동 101호', '1990-01-01', NOW()),
    ('user02', '01022223333', 'pass2', '김영희', 'user02@example.com', b'1', '서울시 서초구', '202동 202호', '1991-02-02', NOW()),
    ('user03', '01033334444', 'pass3', '박철수', 'user03@example.com', b'0', '서울시 송파구', '303동 303호', '1992-03-03', NOW()),
    ('user04', '01044445555', 'pass4', '이민호', 'user04@example.com', b'1', '서울시 강북구', '404동 404호', '1993-04-04', NOW()),
    ('user05', '01055556666', 'pass5', '최지우', 'user05@example.com', b'0', '서울시 마포구', '505동 505호', '1994-05-05', NOW()),
    ('user06', '01066667777', 'pass6', '한가인', 'user06@example.com', b'0', '서울시 은평구', '606동 606호', '1995-06-06', NOW()),
    ('user07', '01077778888', 'pass7', '장동건', 'user07@example.com', b'1', '서울시 도봉구', '707동 707호', '1996-07-07', NOW()),
    ('user08', '01088889999', 'pass8', '정우성', 'user08@example.com', b'0', '서울시 노원구', '808동 808호', '1997-08-08', NOW()),
    ('user09', '01099990000', 'pass9', '고소영', 'user09@example.com', b'1', '서울시 중랑구', '909동 909호', '1998-09-09', NOW()),
    ('user10', '01000001111', 'pass10', '송혜교', 'user10@example.com', b'0', '서울시 강서구', '100동 1001호', '1999-10-10', NOW());

INSERT INTO billing_key (user_id, billing_key, card_company, card_number, created_at)
VALUES
    ('user01', 'bk1', '국민카드', '1111-2222-3333-4444', NOW()),
    ('user02', 'bk2', '삼성카드', '2222-3333-4444-5555', NOW()),
    ('user03', 'bk3', '신한카드', '3333-4444-5555-6666', NOW()),
    ('user04', 'bk4', '롯데카드', '4444-5555-6666-7777', NOW()),
    ('user05', 'bk5', '현대카드', '5555-6666-7777-8888', NOW()),
    ('user06', 'bk6', '국민카드', '6666-7777-8888-9999', NOW()),
    ('user07', 'bk7', '삼성카드', '7777-8888-9999-0000', NOW()),
    ('user08', 'bk8', '신한카드', '8888-9999-0000-1111', NOW()),
    ('user09', 'bk9', '현대카드', '9999-0000-1111-2222', NOW()),
    ('user10', 'bk10', '롯데카드', '0000-1111-2222-3333', NOW());


INSERT INTO `condition` (name, created_at, updated_at) VALUES
                                                           ('S', NOW(), NOW()),
                                                           ('A', NOW(), NOW()),
                                                           ('B', NOW(), NOW());

-- ✅ 5. product_image
INSERT INTO product_image (product_idx, product_img_url, created_at, updated_at)
VALUES ('P001', 'https://example.com/image1.jpg', NOW(), NOW()),
       ('P002', 'https://example.com/image2.jpg', NOW(), NOW()),
       ('P003', 'https://example.com/image3.jpg', NOW(), NOW());

INSERT INTO sale (name, description, created_at)
VALUES
    ('프리미엄렌탈1', '프리미엄 구독 상품 1', NOW()),
    ('프리미엄렌탈2', '프리미엄 구독 상품 2', NOW()),
    ('프리미엄렌탈3', '프리미엄 구독 상품 3', NOW()),
    ('프리미엄렌탈4', '프리미엄 구독 상품 4', NOW()),
    ('프리미엄렌탈5', '프리미엄 구독 상품 5', NOW()),
    ('프리미엄렌탈6', '프리미엄 구독 상품 6', NOW()),
    ('프리미엄렌탈7', '프리미엄 구독 상품 7', NOW()),
    ('프리미엄렌탈8', '프리미엄 구독 상품 8', NOW()),
    ('프리미엄렌탈9', '프리미엄 구독 상품 9', NOW()),
    ('프리미엄렌탈10', '프리미엄 구독 상품 10', NOW());


-- ✅ 8. item
INSERT INTO item (count, condition_idx, itemlocation_idx, product_idx, created_at, updated_at)
VALUES (50, 1, 1, 'P001', NOW(), NOW()),
       (30, 2, 2, 'P001', NOW(), NOW()),
       (40, 1, 1, 'P002', NOW(), NOW()),
       (20, 2, 2, 'P002', NOW(), NOW()),
       (60, 1, 1, 'P003', NOW(), NOW()),
       (10, 2, 2, 'P003', NOW(), NOW());


INSERT INTO subscribe (user_id, is_delayed, billing_key_idx, created_at)
VALUES
    ('user01', b'0', 1, NOW()),
    ('user02', b'1', 2, NOW()),
    ('user03', b'0', 3, NOW()),
    ('user04', b'0', 4, NOW()),
    ('user05', b'1', 5, NOW()),
    ('user06', b'0', 6, NOW()),
    ('user07', b'1', 7, NOW()),
    ('user08', b'0', 8, NOW()),
    ('user09', b'0', 9, NOW()),
    ('user10', b'1', 10, NOW());

INSERT INTO item (count, condition_idx, itemlocation_idx, product_idx, created_at, updated_at)
VALUES (10, 2, 3, 'P001', NOW(), NOW()),
       (30, 1, 3, 'P002', NOW(), NOW()),
       (70, 2, 3, 'P003', NOW(), NOW());
-- 👤 유저
INSERT INTO user (id, phone_number, password, name, email, is_delayed, address1, address2, birth, created_at, updated_at)
VALUES ('user001', '010-1234-5678', 'securePassword123!', '홍길동', 'hong@example.com', b'1', '서울시 강남구', '101호', '1990-05-01', NOW(), NOW());

INSERT INTO subscribe_detail (subscribe_idx, sale_idx, period, price, start_at, end_at, status, created_at)
VALUES
    (1, 1, 3, 30000, '2025-11-01', '2025-02-01', 'SUBSCRIBING', NOW()),
    (2, 2, 6, 55000, '2025-10-01', '2025-04-01', 'RETURNING', NOW()),
    (3, 3, 12, 99000, '2025-03-01', '2025-03-01', 'CANCELED', NOW()),
    (4, 4, 1, 10000, '2025-01-01', '2025-02-01', 'SUBSCRIBING', NOW()),
    (5, 5, 3, 27000, '2025-12-01', '2025-03-01', 'SUBSCRIBING', NOW()),
    (6, 6, 6, 48000, '2025-02-01', '2025-08-01', 'RETURN_REQUESTED', NOW()),
    (7, 7, 12, 88000, '2025-09-01', '2025-09-01', 'SUBSCRIBING', NOW()),
    (8, 8, 1, 12000, '2025-03-01', '2025-04-01', 'SUBSCRIBING', NOW()),
    (9, 9, 3, 31000, '2025-01-01', '2025-04-01', 'RETURNING', NOW()),
    (10, 10, 6, 53000, '202-04-01', '2025-10-01', 'SUBSCRIBING', NOW());


INSERT INTO product (code, name, description, manufacturer, created_at) VALUES
                                                                            ('P1001', '무선청소기', '강력한 무선청소기', 'LG전자', '2024-01-01'),
                                                                            ('P1002', '공기청정기', 'HEPA 필터 내장', '삼성전자', '2024-01-01'),
                                                                            ('P1003', '안마의자', '전신 안마 가능', '바디프랜드', '2024-01-01'),
                                                                            ('P1004', '캡슐커피머신', '다양한 커피 제공', '네스프레소', '2024-01-01'),
                                                                            ('P1005', 'TV 50인치', '4K UHD 스마트 TV', 'LG전자', '2024-01-01'),
                                                                            ('P1006', '전자레인지', '20L 디지털', '삼성전자', '2024-01-01'),
                                                                            ('P1007', '빔프로젝터', 'FHD 해상도 지원', '뷰소닉', '2024-01-01'),
                                                                            ('P1008', '냉장고', '300L 양문형', '위니아', '2024-01-01'),
                                                                            ('P1009', '세탁기', '통돌이 방식', '삼성전자', '2024-01-01'),
                                                                            ('P1010', '에어프라이어', '10L 대용량', '쿠쿠전자', '2024-01-01');

INSERT INTO item_location (name, created_at, updated_at) VALUES
                                                             ('창고', NOW(), NOW()),
                                                             ('대여중', NOW(), NOW()),
                                                             ('수리중', NOW(), NOW());

INSERT INTO item (product_code, condition_idx, itemlocation_idx, count, created_at) VALUES
                                                                                       ('P1001', 1, 1, 5, '2024-01-10'),
                                                                                       ('P1002', 1, 1, 4, '2024-01-10'),
                                                                                       ('P1003', 2, 1, 3, '2024-01-10'),
                                                                                       ('P1004', 1, 1, 6, '2024-01-10'),
                                                                                       ('P1005', 2, 2, 2, '2024-01-10'),
                                                                                       ('P1006', 1, 2, 4, '2024-01-10'),
                                                                                       ('P1007', 1, 2, 2, '2024-01-10'),
                                                                                       ('P1008', 2, 2, 1, '2024-01-10'),
                                                                                       ('P1009', 1, 3, 3, '2024-01-10'),
                                                                                       ('P1010', 1, 3, 7, '2024-01-10');


INSERT INTO rental_delivery (subscribe_detail_idx, address1, address2, courier_company, tracking_number, recipient_name, recipient_phone, delivered_at, shipped_at, status, created_at) VALUES
                                                                                                                                                                                            (1, '서울특별시 강남구', '101동 101호', 'CJ대한통운', 'TRK000001', '홍길동1', '010-1234-0001', '2024-01-02', '2024-01-01', '배송완료', '2024-01-01'),
                                                                                                                                                                                            (2, '서울특별시 마포구', '102동 102호', '롯데택배', 'TRK000002', '홍길동2', '010-1234-0002', '2024-01-03', '2024-01-02', '배송완료', '2024-01-02'),
                                                                                                                                                                                            (3, '서울특별시 종로구', '103동 103호', '한진택배', 'TRK000003', '홍길동3', '010-1234-0003', '2024-01-04', '2024-01-03', '배송완료', '2024-01-03'),
                                                                                                                                                                                            (4, '서울특별시 노원구', '104동 104호', '우체국택배', 'TRK000004', '홍길동4', '010-1234-0004', '2024-01-05', '2024-01-04', '배송완료', '2024-01-04'),
                                                                                                                                                                                            (5, '서울특별시 송파구', '105동 105호', 'CJ대한통운', 'TRK000005', '홍길동5', '010-1234-0005', '2024-01-06', '2024-01-05', '배송완료', '2024-01-05'),
                                                                                                                                                                                            (6, '서울특별시 성북구', '106동 106호', '롯데택배', 'TRK000006', '홍길동6', '010-1234-0006', '2024-01-07', '2024-01-06', '배송완료', '2024-01-06'),
                                                                                                                                                                                            (7, '서울특별시 중구', '107동 107호', '한진택배', 'TRK000007', '홍길동7', '010-1234-0007', '2024-01-08', '2024-01-07', '배송완료', '2024-01-07'),
                                                                                                                                                                                            (8, '서울특별시 동작구', '108동 108호', '우체국택배', 'TRK000008', '홍길동8', '010-1234-0008', '2024-01-09', '2024-01-08', '배송완료', '2024-01-08'),
                                                                                                                                                                                            (9, '서울특별시 관악구', '109동 109호', 'CJ대한통운', 'TRK000009', '홍길동9', '010-1234-0009', '2024-01-10', '2024-01-09', '배송완료', '2024-01-09'),
                                                                                                                                                                                            (10, '서울특별시 은평구', '110동 110호', '롯데택배', 'TRK000010', '홍길동10', '010-1234-0010', '2024-01-11', '2024-01-10', '배송완료', '2024-01-10');


INSERT INTO rental_delivery (
    subscribe_detail_idx, address1, address2, courier_company,
    delivered_at, delivery_memo, postal_code, recipient_name,
    recipient_phone, shipped_at, status, created_at, updated_at
) VALUES
      (1, '서울시 강남구', '101동 1101호', 'CJ대한통운', '2024-04-01 10:00:00', '경비실에 맡겨주세요', '06101', '홍길동', '01012345678', '2024-03-31 08:00:00', 'SHIPPING', NOW(), NOW()),
      (2, '부산시 해운대구', '305동 504호', '한진택배', '2024-04-10 14:00:00', '문 앞에 놔주세요', '48093', '임꺽정', '01098765432', '2024-04-09 16:00:00', 'SHIPPING', NOW(), NOW()),
      (3, '대구시 중구', '2층 201호', '롯데택배', '2024-04-15 18:30:00', '집 앞에 조심히', '41933', '이몽룡', '01055558888', '2024-04-14 12:30:00', 'PREPARING', NOW(), NOW());


INSERT INTO repair_request (
    subscribe_detail_idx, description, status, created_at, updated_at
) VALUES
      (1, '청소기 작동 불량', '1', NOW(), NOW()),
      (2, '안마의자 리모컨 고장', '2', NOW(), NOW());


INSERT INTO repair_image (
    repair_request_idx, url
) VALUES
      (1, 'https://example.com/images/repair1.jpg'),
      (2, 'https://example.com/images/repair2.jpg');


INSERT INTO return_delivery (
    pickup_date, subscribe_detail_idx, address1, address2, return_location,
    description, subscribe_name, subscribe_phone, status,
    created_at, updated_at
) VALUES
      ('2024-04-20 10:00:00', 1, '서울시 마포구', '202호','BEFORE_RETURN', '청소기 반품 요청', '홍길동', '01012345678', 'RETURN_REQUESTED', NOW(), NOW()),
      ('2024-04-10 09:00:00', 2, '부산시 해운대구', '305동 504호','BEFORE_RETURN', '안마의자 수리 신청', '임꺽정', '01098765432', 'REPAIR_REQUESTED', NOW(), NOW());

INSERT INTO payment (payment_id, is_paid, price, subscribe_idx, created_at)
VALUES
    ('P20230501', b'1', 49000, 1, '2023-05-01 10:00:00'),
    ('P20230502', b'0', 59000, 2, '2023-05-02 11:30:00'),
    ('P20230503', b'1', 55000, 3, '2023-05-03 09:15:00'),
    ('P20230504', b'1', 60000, 4, '2023-05-04 14:45:00'),
    ('P20230505', b'0', 52000, 5, '2023-05-05 12:00:00'),
    ('P20230506', b'1', 48000, 6, '2023-05-06 13:25:00'),
    ('P20230507', b'1', 51000, 7, '2023-05-07 15:40:00'),
    ('P20230508', b'0', 53000, 8, '2023-05-08 08:30:00'),
    ('P20230509', b'1', 47000, 9, '2023-05-09 16:50:00'),
    ('P20230510', b'0', 50000, 10, '2023-05-10 10:10:00');

INSERT INTO sale_has_product (idx, condition_idx, created_at, updated_at, sale_idx, product_code) VALUES
                                                                                                      (1, 1, NOW(), NOW(), 1, 'P1001'),
                                                                                                      (2, 2, NOW(), NOW(), 2, 'P1002'),
                                                                                                      (3, 3, NOW(), NOW(), 3, 'P1003'),
                                                                                                      (4, 1, NOW(), NOW(), 4, 'P1004'),
                                                                                                      (5, 2, NOW(), NOW(), 5, 'P1005'),
                                                                                                      (6, 3, NOW(), NOW(), 1, 'P1006'),
                                                                                                      (7, 1, NOW(), NOW(), 2, 'P1007'),
                                                                                                      (8, 2, NOW(), NOW(), 3, 'P1008'),
                                                                                                      (9, 3, NOW(), NOW(), 4, 'P1009'),
                                                                                                      (10, 1, NOW(), NOW(), 5, 'P1010'),
                                                                                                      (11, 2, NOW(), NOW(), 1, 'P1001'),
                                                                                                      (12, 3, NOW(), NOW(), 2, 'P1002'),
                                                                                                      (13, 1, NOW(), NOW(), 3, 'P1003'),
                                                                                                      (14, 2, NOW(), NOW(), 4, 'P1004'),
                                                                                                      (15, 3, NOW(), NOW(), 5, 'P1005'),
                                                                                                      (16, 1, NOW(), NOW(), 1, 'P1006'),
                                                                                                      (17, 2, NOW(), NOW(), 2, 'P1007'),
                                                                                                      (18, 3, NOW(), NOW(), 3, 'P1008'),
                                                                                                      (19, 1, NOW(), NOW(), 4, 'P1009'),
                                                                                                      (20, 2, NOW(), NOW(), 5, 'P1010');
