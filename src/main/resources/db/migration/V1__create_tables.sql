create table user (
    id int(11) not null auto_increment primary key,
    name varchar(100) not null,
    email varchar(100) not null unique,
    password varchar(255) not null
) engine=InnoDB;

create table role (
    id int(11) not null auto_increment primary key,
    name varchar(100) not null
) engine=InnoDB;

create table privilege (
    id int(11) not null auto_increment primary key,
    name varchar(100) not null
) engine=InnoDB;

create table user_roles (
    user_id int(11) not null,
    roles_id int(11) not null,
    primary key (user_id, roles_id),
    foreign key (user_id) references user(id),
    foreign key (roles_id) references role(id)
) engine=InnoDB;

create table role_privileges (
    role_id int(11) not null,
    privileges_id int(11) not null,
    primary key (role_id, privileges_id),
    foreign key (role_id) references role(id),
    foreign key (privileges_id) references privilege(id)
) engine=InnoDB;

create table example (
    id int(11) not null auto_increment primary key,
    name varchar(100) not null
) engine=InnoDB;
