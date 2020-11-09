create sequence if not exists seq_id_category_category;
create table if not exists tb_category (
    id_category bigint not null default nextval('seq_id_category_category'),
    name varchar(33) not null,
    primary key(id_category)
);

create sequence if not exists seq_id_product_product;
create table if not exists tb_product (
    id_product bigint not null default nextval('seq_id_product_product'),
    name varchar(33) not null,
    description text not null,
    value decimal not null,
    primary key(id_product)
);

create table if not exists tb_category_product (
    id_category bigint not null,
    id_product  bigint not null,
    foreign key (id_category) references tb_category (id_category),
    foreign key (id_product) references tb_product (id_product)
);