create sequence _default_sequence start with 1 increment by 50;
create sequence _game_score_sequence start with 1 increment by 50;
create sequence _game_sequence start with 1 increment by 50;

create table faction
(
    id           int          not null
        primary key,
    created_date datetime(6)  null,
    hash_id      binary(255)  null,
    updated_date datetime(6)  null,
    code         varchar(255) null,
    name         varchar(255) null,
    parent_id    int          null,
    constraint UK_2arcl47hkpwlyve7xwf1ad2so
        unique (code),
    constraint UK_cypsf6k1lnj4jnb2aafim3q0o
        unique (hash_id),
    constraint FK3t7nasg185guilu22ycgkqxg2
        foreign key (parent_id) references faction (id)
);

create table game
(
    id            int         not null
        primary key,
    created_date  datetime(6) null,
    hash_id       binary(255) null,
    updated_date  datetime(6) null,
    current_round int         not null,
    status        int         null,
    total_rounds  int         not null,
    constraint UK_dlea5foxkaexk6uyewmtxoji1
        unique (hash_id)
);

create table objective
(
    id           int          not null
        primary key,
    created_date datetime(6)  null,
    hash_id      binary(255)  null,
    updated_date datetime(6)  null,
    description  longtext     null,
    max_points   int          not null,
    name         varchar(255) null,
    scoring_type int          null,
    constraint UK_elb4fs3o7sv6rfbfio4o0dy3r
        unique (hash_id)
);

create table user
(
    id               int          not null
        primary key,
    created_date     datetime(6)  null,
    hash_id          binary(255)  null,
    updated_date     datetime(6)  null,
    email            varchar(255) null,
    telegram_account varchar(255) null,
    telegram_user_id int          null,
    constraint UK_8bwnqp9uiys7tg0bkpi1scydo
        unique (hash_id)
);

create table player
(
    created_date datetime(6)  null,
    hash_id      binary(255)  null,
    updated_date datetime(6)  null,
    name         varchar(255) null,
    user_id      int          not null
        primary key,
    constraint UK_am7ta8fq9yqv4bl4daa0ricwy
        unique (hash_id),
    constraint FKj57d4kgk8qu7i73lu7xsbq8fb
        foreign key (user_id) references user (id)
);

create table player_game
(
    id            int         not null
        primary key,
    created_date  datetime(6) null,
    hash_id       binary(255) null,
    updated_date  datetime(6) null,
    max_points    int         not null,
    scored_points int         not null,
    faction_id    int         not null,
    game_id       int         not null,
    player_id     int         not null,
    constraint UK_59gbj9q1ysrd3sqr0atkqwpr2
        unique (hash_id),
    constraint FK64mlbua6kybw1lalg2mot80d0
        foreign key (faction_id) references faction (id),
    constraint FKcmhljkag2nj6gd364m5ntim12
        foreign key (player_id) references player (user_id),
    constraint FKjakjk5h0inrg1j3dbwevfpym8
        foreign key (game_id) references game (id)
);

create table game_score
(
    id             bigint      not null
        primary key,
    created_date   datetime(6) null,
    hash_id        binary(255) null,
    updated_date   datetime(6) null,
    objective_id   int         not null,
    player_game_id int         not null,
    constraint UK_cfpjhqd8wxq0er0kcp2mfshh4
        unique (hash_id),
    constraint FKhtgghnejkyarrgq0vnpgqfsop
        foreign key (player_game_id) references player_game (id),
    constraint FKodm3xjlddhff163sd5frav0ui
        foreign key (objective_id) references objective (id)
);