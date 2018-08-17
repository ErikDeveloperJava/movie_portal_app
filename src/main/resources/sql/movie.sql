create database movie_website character set utf8 collate utf8_general_ci;

use movie_website;

create table country(
  id int not null auto_increment primary key ,
  name varchar(255) not null ,
  ru_name varchar(255) not null ,
  arm_name varchar(255) not null
)engine InnoDB character set utf8 collate utf8_general_ci;

create table genre(
  id int not null auto_increment primary key ,
  name varchar(255) not null,
  ru_name varchar(255) not null ,
  arm_name varchar(255) not null
)engine InnoDB character set utf8 collate utf8_general_ci;

create table movie(
  id int not null auto_increment primary key ,
  title varchar(255) not null ,
  description text not null ,
  actors varchar(255) not null ,
  producer varchar(255) not null ,
  premier_date date not null ,
  language varchar(255) not null ,
  year int(4) not null ,
  register_date DATETIME not null ,
  profile_image varchar(255) not null ,
  video varchar(255) not null ,
  country_id int not null ,
  foreign key (country_id) references country(id)on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;

create table movie_genre(
  movie_id int not null ,
  genre_id int not null ,
  foreign key (movie_id) references movie(id) on delete cascade ,
  foreign key (genre_id) references genre(id) on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;

create table user(
  id int not null auto_increment primary key ,
  username varchar(255) not null ,
  password varchar(255) not null ,
  name varchar(255) not null ,
  role varchar(255) not null
)engine InnoDB character set utf8 collate utf8_general_ci;

create table comment(
  id int not null auto_increment primary key ,
  comment text not null ,
  added_date date,
  user_id int not null ,
  movie_id int not null ,
  foreign key (user_id) references user(id)on delete cascade ,
  foreign key (movie_id) references movie(id)on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;

create table bookmark(
  user_id int not null ,
  movie_id int not null ,
  foreign key (user_id) references user(id)on delete cascade ,
  foreign key (movie_id) references movie(id)on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;
