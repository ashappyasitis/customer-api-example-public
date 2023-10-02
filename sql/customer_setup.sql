# schema 생성
create schema customer collate utf8mb4_general_ci;

# 고객사 table 생성
drop table tb_customer;

create table tb_customer
(
    customer_code  bigint auto_increment comment '고객사코드'
        primary key,
    customer_type  varchar(16)                          not null comment '고객사구분 (ENTERPRISE:기업,INDIVIDUAL:개인)',
    company_name   varchar(255)                         not null comment '고객사명, 상호, 브랜드 명',
    contract_type  varchar(16)                          null comment '계약형태(DIRECT: 직접계약)',
    operation_type varchar(16)                          null comment '운영형태(DIRECT: 직영)',
    is_enabled     tinyint(1) default 1                 not null comment '가용 여부 - true: 1 (가용 상태), false: 0 (삭제 상태)',
    created_by     varchar(36)                          null comment '등록자',
    created_at     timestamp  default CURRENT_TIMESTAMP not null comment '등록시간',
    updated_by     varchar(36)                          null comment '수정자',
    updated_at     timestamp  default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '수정시간',
    constraint tb_customer_company_name_uindex
        unique (company_name)
)
    comment '고객사';