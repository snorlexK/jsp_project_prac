show tables;

select * from member;

desc member;

drop table member;

create table member(
	email varchar(40) primary key not null,
	password varchar(20) not null,
	nickname varchar(30) not null,
	birthday date null,
	phone varchar(11) null,
	address varchar(255) null,
	referer varchar(40) null,
	nickname_latest_changed datetime null
);

insert into member values('admin', '1234', '관리자', '1997-12-06', '01056917488', '서울시 강남구', null, null);

delete from member where email = 'email@naver.com';