create table persistent_logins(
  username varchar(64) not null,
  series   varchar(64) primary key,  --기기, 브라우저별 쿠키를 구분할 고유 값
  token    varchar(64) not null,  --브라우저가 가지고 있는 쿠키의 값을 검증할 인증 값
  last_used timestamp not null -- 가장 최신 자동 로그인 시간
);


select * from persistent_logins;

//테이블 컬럼 이름과 양식은 시큐리티에서 가지고 있어 정해진 것임.
//로그인 유지하기