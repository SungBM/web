drop table member CASCADE CONSTRAINTS purge;
--1. index.jsp���� �����մϴ�.
--2. ������ ���� admin, ��� 1234�� ����ϴ�.
--3. ����� ������ 3�� ����ϴ�.
--4. cmdâ���� ���̺� �����!!

create table member3(
	id		 varchar2(15),
	password varchar2(60),   --��ȣȭ�� �ϸ� password�� 60�� �ʿ��մϴ�.
	name 	 varchar2(15),  
	age		 Number(2),
	gender	 varchar2(5),
	email	 varchar2(30),
	auth varchar2(50) not null,  --ȸ���� role(����)�� �����ϴ� ������ �⺻���� 'ROLE_MEMBER' �Դϴ�.
	PRIMARY KEY(id)
);

select * from member3