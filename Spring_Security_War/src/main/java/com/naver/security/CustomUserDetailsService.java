package com.naver.security;

import java.util.ArrayList;

import java.util.Collection;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.naver.myhome4.domain.Member;
import com.naver.myhome4.mybatis.mapper.MemberMapper;

/*
1. UserDetailsService 인터페이스는 DB에서 유저 정보를 불러오는 loadUserByUsername()이 존재합니다.
   이를 구현하는 클래스는 DB에서 유저의 정보를 가져와서 UserDetails 타입으로 리턴해 주는 작업을 합니다.
   
2. UserDetails는 인터페이스로 Security에서 사용자의 정보를 담는 인터페이스입니다.

3. UserDetails 인터페이스를 구현한 클래스는 실제로 사용자의 정보와 사용자가 가진 권한의 정보를 처리해서 반환하게 됩니다.
	예) UserDetails user = new User(username, users.getPassword(), roles);  
		id, 테이블에 있는 password(), 권한 리턴해.
* */
//@Service
public class CustomUserDetailsService implements UserDetailsService{
	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	//db에 갔다오겠다
	@Autowired
	private MemberMapper dao;
	
	@Override
	//시큐리티에 UserDetails라는 형이 있음.
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{ //테이블이 최종 원하는 자료는 UserDetails형. 같은 형으로 return해줘야함.
		logger.info("username은 로그인 시 입력한 값 : " + username);
		Member users = dao.isId(username);  //아이디 체크하고 왔어. member형인 users에 id담김
		// Member.java에 있는 모든 애들 다 가져올 수 있음. id, password, name, age, auth.... 등 
		if(users==null) {
			logger.info("username " + username + " not found");
			throw new UsernameNotFoundException("username " + username + "not found");
		}
		
		//GrantedAuthority : 인증 개체에 부여된 권한을 나타내기 위한 인터페이스로 이를 구현한 구현체는 생성자에 권한을 문자열로 넣어주면 됩니다.
				//SimpleGrantedAuthority : GrantedAuthority의 구현체입니다.
			Collection<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();  //권한이 한개가 아니고 여러개일 수 이쓰니까 arraylits
			//권한을 받아오기 위해서는 Collection을 사용해야함
			roles.add(new SimpleGrantedAuthority(users.getAuth())); //Member.java에 있는 auth권한은 ROLE_MEMBER야. 유저가 가지고 있는 권한을 add로 담음.
			
			UserDetails user = new User(username, users.getPassword(), roles);  //시큐리티가 원하는거는 UserDetails형으로 반환임. User 임플리먼츠해서 들어가면 자료형 볼 수 있음
			//userDetails를 반환하기 위해서 //username은 위에서 받고 있어. admin의 비번도 가져와. (member.java 테이블에 있는 비번) roles은 권한   //리턴할거면 이름, 비번, 권한까지 알아서와.
			//SimpleGrantedAuthority 권한을 가져오기 위해 사용하는 구현체.
			return user;
	}
}
