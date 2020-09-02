CREATE TABLE tree
(
    id   bigserial primary key,
    kind text
);

CREATE TABLE fruit
(
    id       bigserial primary key,
    tree_id  bigint not null references tree (id) on delete cascade,
    ripeness text
);