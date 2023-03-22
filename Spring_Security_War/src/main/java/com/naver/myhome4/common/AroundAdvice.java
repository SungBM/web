package com.naver.myhome4.common;

import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

//Advice : 횡단 관심에 해당하는 공통 기능을 의미하며 독립된 클래스의 메서드로 작성됩니다.
//Around Advice : 비즈니스 메서드 실행 전이나 실행 후에 수행되는 Advice 입니다.
public class AroundAdvice {
	private static final Logger logger = LoggerFactory.getLogger(AroundAdvice.class);
	/*
	 1. PreceedingJoinPoint 인터페이스는 JoinPoint를 상속했기 때문에 JoinPoint가 가진 모든 메서드를 지원합니다.
	 2. Around Advice에서만 ProceedingJoinPoint를 매개변수로 사용하는데 이 곳에서 proceed()메서드가 필요하기 때문입니다.
	 3. Around Advice인 경우 비즈니스 메서드 실행 전과 후에 실행 되는데 비즈니스 메서드를 호출하기 위해서는
	 	ProceedingJoinPoint의 proceed() 메서드가 필요합니다.
	 	즉, 클라이언트의 요청을 가로챈 어드바이스는 클라이언트가 호출한 비즈니스 메서드(ServiceImpl의 get으로 시작하는 메서드)를
	 	호출하기 위해 ProceedingJoinPoint 객체를 매개 변수로 받아 proceed()메서드를 통해서
	 	비즈니스 메서드를 호출할 수 있습니다.
	 	proceed()메서드 실행 후 메소드의 반환값을 리턴해야 합니다.
	 * */
	//서비스임플에 겟리스트카운트 실행. 겟보드리스트. 
	//이것을 실행하기 전과 후 실행해야 할 것들 호출
	public Object aroundLog(ProceedingJoinPoint proceeding)throws Throwable{ //반환형의 자료형은 모든 담을 수 있는 Object로 되어 있음.
		logger.info("====================================================");
		logger.info("[Around Advice의 before] : 비즈니스 메서드 수행 전입니다.");
		logger.info("====================================================");
		StopWatch sw = new StopWatch();
		sw.start();  //스탑워치 시작
		
		//이 코드의 이전과 이후에 공통 기능을 위한 코드를 위치시키면 됩니다.
				//대상 객체의 메서드(예-BoardServiceImpl.getListCount())를 호출합니다.
		Object result = proceeding.proceed(); //얘를 기점으로 앞에 하기 전, 뒤에 후 메서드 작성
		
		sw.stop();  //스탐워치 끝. 시간을 알 수 있음.
		logger.info("====================================================");
		logger.info("[Around Advice의 after]: 비즈니스 메서드 수행 후 입니다.");
		
		//호출되는 메서드에 대한 정보를 구합니다.
		Signature sig = proceeding.getSignature();
		
		/*
		 * org.aspectj.lang.Signature 인터페이스는 호출되는 메서드와 관련된 정보를 제공합니다.
		 * String getName() : 메서드의 이름을 구한다.
		 * */
		
		/*
		 * 1. proceeding.getTarget().getClass().getSimpleName() : Target클래스의 이름을 가져옵니다.
		 * 2. Object[] getArgs() : 클라이언트가 메서드를 호출할 때 넘겨준 인자 목록을 Object 배열로 리턴합니다.
		 * */
		
		 //BoardServiceImpl.getBoardList([1, 10])
		logger.info("[Around Advice�� after]: " + proceeding.getTarget().getClass().getSimpleName() + "."
					+sig.getName()+"("+Arrays.toString(proceeding.getArgs())+")");
		
		//getBoardList() �޼ҵ� ���� �ð� : 16(ms)��
		logger.info("[Around Advice의 after]: "
				+ proceeding.getSignature().getName() + "()메소드 수행 시간 :"
				+ sw.getTotalTimeMillis() + "(ms)초");
		
		//com.naver.myhome4.service.BoardServiceImpl
		logger.info("[Around Advice�� after]: "
				+ proceeding.getTarget().getClass().getName());
		
		logger.info("proceeding.proceed() 실행 후 반환값 :" + result);
		logger.info("=====================================================");
		return result;  //값을 항상 해줘야됨. 
		
	}
	//각 메서드의 실행시간이 얼마나 될까?는 around에 가장 좋은 예.메서드 전과 후 스탑워치처럼 시간을 구할 수 있어서.
}
