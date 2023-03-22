package com.naver.security;


import java.io.IOException;

import javax.security.sasl.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;


//AuthenticationFailureHandler : 로그인 실패 후 처리할 작업을 해야할 때 사용하는 인터페이스입니다. 그럼 가지고 있는 추상 메서드가 있을 것. (시큐리티에 있는 객체임.)
//@Service
public class LoginFailHandler implements AuthenticationFailureHandler{

public static final Logger logger = LoggerFactory.getLogger(LoginFailHandler.class);

	
	@Override //우리는 세선에 있는 정보를 가져올거야. 
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.core.AuthenticationException exception) throws IOException, ServletException {
		HttpSession session = request.getSession();
		logger.info(exception.getMessage());
		logger.info("로그인 실패");
		session.setAttribute("loginfail", "loginFailMsg");   //실패한거를 loginfail키로 inginfailmas를 세션에 담았다.
		String url = request.getContextPath() + "/member/login";  //주소로 가라.
		response.sendRedirect(url);   //세션에 정보담아서 이동. //담은 값을 가져가. 
		
	}

}
