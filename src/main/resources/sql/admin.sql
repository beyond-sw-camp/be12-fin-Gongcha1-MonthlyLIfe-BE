-- ✅ 1. category
INSERT INTO category (idx, name, created_at, updated_at)
VALUES (1, '전자제품', NOW(), NOW()),
       (2, '가전제품', NOW(), NOW()),
       (3, '생활용품', NOW(), NOW());

-- ✅ 2. product
INSERT INTO product (code, name, manufacturer, description, created_at, updated_at)
VALUES ('P001', '무선 헤드폰', '삼성', '블루투스 지원 헤드폰입니다.', NOW(), NOW()),
       ('P002', '전자 칫솔', 'LG', '스마트 진동 칫솔입니다.', NOW(), NOW()),
       ('P003', '로봇 청소기', '다이슨', 'AI 기반 스마트 청소기입니다.', NOW(), NOW());

-- ✅ 3. sale
INSERT INTO sale (idx, name, description, category_idx, created_at, updated_at)
VALUES (1, '헤드폰 렌탈', '삼성 헤드폰 월 렌탈', 1, NOW(), NOW()),
       (2, '칫솔 렌탈', 'LG 전자 칫솔 렌탈', 2, NOW(), NOW()),
       (3, '청소기 렌탈', '다이슨 청소기 렌탈', 1, NOW(), NOW());

-- ✅ 4. sale_has_product
INSERT INTO sale_has_product (idx, sale_idx, product_code, created_at, updated_at)
VALUES (1, 1, 'P001', NOW(), NOW()),
       (2, 2, 'P002', NOW(), NOW()),
       (3, 3, 'P003', NOW(), NOW());

-- ✅ 5. product_image
INSERT INTO product_image (product_idx, product_img_url, created_at, updated_at)
VALUES ('P001', 'https://example.com/image1.jpg', NOW(), NOW()),
       ('P002', 'https://example.com/image2.jpg', NOW(), NOW()),
       ('P003', 'https://example.com/image3.jpg', NOW(), NOW());

-- ✅ 6. condition
INSERT INTO `condition` (idx, name, created_at, updated_at)
VALUES (1, '정상', NOW(), NOW()),
       (2, '수리중', NOW(), NOW());

-- ✅ 7. item_location
INSERT INTO item_location (idx, name, created_at, updated_at)
VALUES (1, '창고', NOW(), NOW()),
       (2, '대여중', NOW(), NOW());

-- ✅ 8. item
INSERT INTO item (count, condition_idx, itemlocation_idx, product_idx, created_at, updated_at)
VALUES (50, 1, 1, 'P001', NOW(), NOW()),
       (30, 2, 2, 'P001', NOW(), NOW()),
       (40, 1, 1, 'P002', NOW(), NOW()),
       (20, 2, 2, 'P002', NOW(), NOW()),
       (60, 1, 1, 'P003', NOW(), NOW()),
       (10, 2, 2, 'P003', NOW(), NOW());


INSERT INTO item_location (idx, name, created_at, updated_at)
VALUES (3, '수리중', NOW(), NOW());

INSERT INTO item (count, condition_idx, itemlocation_idx, product_idx, created_at, updated_at)
VALUES (10, 2, 3, 'P001', NOW(), NOW()),
       (30, 1, 3, 'P002', NOW(), NOW()),
       (70, 2, 3, 'P003', NOW(), NOW());
-- 👤 유저
INSERT INTO user (id, phone_number, password, name, email, is_delayed, address1, address2, birth, created_at, updated_at)
VALUES ('user001', '010-1234-5678', 'securePassword123!', '홍길동', 'hong@example.com', b'1', '서울시 강남구', '101호', '1990-05-01', NOW(), NOW());

-- 💳 결제
INSERT INTO payment (card_number, created_at, updated_at)
VALUES (12345678, NOW(), NOW());

-- 🛍 판매 상품
INSERT INTO sale (name, description, created_at, updated_at)
VALUES ('프리미엄 노트북 렌탈', '고성능 노트북 월 렌탈 상품', NOW(), NOW());

-- 📦 구독
INSERT INTO subscribe ( created_at, updated_at, version, user_id, is_delayed)
VALUES ( NOW(), NOW(), 0, 'user001', false);

-- 📄 구독 상세
INSERT INTO subscribe_detail (price, created_at, updated_at, sale_idx, start_at, end_at, version, subscribe_idx)
VALUES (99000, NOW(), NOW(), 1, '2025-04-01 00:00:00', '2025-07-01 00:00:00', 0, 1);

-- 🚚 렌탈 배송
INSERT INTO rental_delivery (
    created_at, updated_at, subscribe_detail_idx,
    address1, address2, courier_company, delivered_at,
    delivery_memo, postal_code, recipient_name, recipient_phone,
    shipped_at, status, tracking_number
)
VALUES (
           NOW(), NOW(), 1,
           '서울시 강남구', '101호', 'CJ대한통운', '2025-04-03 15:00:00',
           '문 앞에 놔주세요', '04524', '홍길동', '010-1234-5678',
           '2025-04-02 10:00:00', '배송중', 'TRK123456789'
       );
