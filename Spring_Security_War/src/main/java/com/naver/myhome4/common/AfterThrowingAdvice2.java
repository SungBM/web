package com.naver.myhome4.common;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/*
 * Advice : 횡단 관심에 해당하는 공통 기능을 의미하며 독립된 클래스의 메서드로 작성됩니다.
 * after-throwing : 타겟 메소드가 수행 중 예외를 던지게 되면 어드바이스 기능을 수행합니다.
 
 * BoardServiceImpl.java에서 getBoardList()안에
   double i = 1/0;
   추가합니다.
 * */
@Service
@Aspect
public class AfterThrowingAdvice2 {
	private static final Logger logger = LoggerFactory.getLogger(AfterThrowingAdvice2.class);
	
	//throwing = "exp"의 exp와 Throwable exp의 매개변수 이름을 일치시켜야 합니다.
	@AfterThrowing(
			pointcut = "execution(* com.naver.myhome4..*Impl.get*(..))",
			throwing = "exp"
			)
	public void afterThrowingLog(Throwable exp) {	//메서드 실행하면 리턴값(=결과값)을 보여줘라.  
	
		logger.info("====================================================");
		logger.info("[AfterThrowing2] : 비즈니스 로직 수행 중 오류가 발생하면 동작합니다.");
		logger.info("ex : " + exp.toString());
		logger.info("====================================================");
	}
}
