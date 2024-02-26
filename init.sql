CREATE TABLE product
(
    id         VARCHAR(127) NOT NULL,
    brand_name VARCHAR(127) NOT NULL,
    top        INT          NOT NULL,
    outer      INT          NOT NULL,
    pants      INT          NOT NULL,
    sneakers   INT          NOT NULL,
    bag        INT          NOT NULL,
    cap        INT          NOT NULL,
    socks      INT          NOT NULL,
    accessory  INT          NOT NULL,
    created    timestamp(3) NOT NULL,
    modified   timestamp(3),
    deleted    timestamp(3)
);

INSERT INTO product (id, brand_name, top, outer, pants, sneakers, bag, cap, socks, accessory, created)
VALUES ('product-b1b4fe1d-c14e-44c9-9619-43ed62e8c7c4', 'A', 11200, 5500, 4200, 9000, 2000, 1700, 1800, 2300, CURRENT_TIMESTAMP),
       ('product-ee298cf9-fca8-40c8-9015-e7e43e300320', 'B', 10500, 5900, 3800, 9100, 2100, 2000, 2000, 2200, CURRENT_TIMESTAMP),
       ('product-6176a7e4-7392-4e85-aba5-ebe3c2a490bc', 'C', 10000, 6200, 3300, 9200, 2200, 1900, 2200, 2100, CURRENT_TIMESTAMP),
       ('product-d0ff9c20-2a38-4f4d-8dc2-23a85fbc05f0', 'D', 10100, 5100, 3000, 9500, 2500, 1500, 2400, 2000, CURRENT_TIMESTAMP),
       ('product-45f4b8b6-7f97-481a-bf20-3868a6f4a7c3', 'E', 10700, 5000, 3800, 9900, 2300, 1800, 2100, 2100, CURRENT_TIMESTAMP),
       ('product-6c7e37d1-1d99-42a5-b38b-f4c9f5d9c64f', 'F', 11200, 7200, 4000, 9300, 2100, 1600, 2300, 1900, CURRENT_TIMESTAMP),
       ('product-c06b05a0-0897-4d20-a243-6b80183302c3', 'G', 10500, 5800, 3900, 9000, 2200, 1700, 2100, 2000, CURRENT_TIMESTAMP),
       ('product-adcdddcf-c736-4ba1-b7f6-9cf8deea8bea', 'H', 10800, 6300, 3100, 9700, 2100, 1600, 2000, 2000, CURRENT_TIMESTAMP),
       ('product-42b19244-a2de-47c1-b716-0ea0ccb8a50e', 'I', 11400, 6700, 3200, 9500, 2400, 1700, 1700, 2400, CURRENT_TIMESTAMP);










