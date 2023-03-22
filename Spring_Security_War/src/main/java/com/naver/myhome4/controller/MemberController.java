package com.naver.myhome4.controller;

import java.io.PrintWriter;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.naver.myhome4.domain.MailVO;
import com.naver.myhome4.domain.Member;
import com.naver.myhome4.service.MemberService;
import com.naver.myhome4.task.SendMail;

/*
	@Component를 이용해서 스프링 컨테이너가 해당 클래스 객체를 생성하도록 설정할 수 있지만
	 모든 클래스에 @Component를 할당하면 어떤 클래스가 어떤 역할을 수행하는지 파악하기
	 어렵습니다. 스프링 프레임워크에서는 이런 클래스들을 분류하기 위해서
	@Component를 상속하여 다음과 같은 세 개의 애노테이션을 제공합니다.
	
	*순서이해하기
	*1. Client가 Request를 보낸다.
	*2. Request URL에 알맞은 Controller가 수신을 받는다.
	*3. Controller는 넘어온 요청을 처리하기 위해 Service를 호출한다.
	*4. Service는 알맞은 정보를 가공(=비즈니스 로직 수행)하여 Controller에게 데이터를 넘긴다.
	*5. Controller는 Service의 결과물을 Client에게 전달해준다.
	
	
	1. @Controller - 사용자의 요청을 제어하는 Controller 클래스. 
	2. @Repository - 데이터 베이스 연동을 처리하는 DAO클래스. 
	3. @Service - 비즈니스 로직을 처리하는 Service 클래스. 
* */

//이 컨트롤러는 회원가입 시 비밀번호 암호화와 로그인 처리 후 메일 전송하는 컨트롤러 입니다.
@Controller
@RequestMapping(value = "member") // http://localhost:8089/myhome4/member/로 시작하는 주소 매핑
public class MemberController {
	// import org.slf4j.Logger;
	// import org.slf4j.LoggerFactory; 요거 임폴트해야함
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	private MemberService memberservice; // 컨트롤러는 서비스를 이용해야함. 멤버서비스 쓰려면 다오와 impl 만들어져 있어야 오류 안남.
	private PasswordEncoder passwordEncoder;
	private SendMail sendMail;

	@Autowired // ������� ����
	public MemberController(MemberService memberservice, PasswordEncoder passwordEncoder, SendMail sendMail) {
		this.memberservice = memberservice;
		this.passwordEncoder = passwordEncoder;
		this.sendMail = sendMail;
	}

	/*
	 * @CookieValue(value="saveid", required=false) Cookie readCookie 이름이 saveid인
	 * 쿠키를 Cookie 타입으로 전달받습니다. *쿠키 자료형으로 readcookie에 저장해라. 지정한 이름의 쿠키가 존재하지 않을 수도 있기
	 * 때문에 required=false로 설정해야 합니다. *쿠키의 값이 있을수도 없을수도 있기 때문에 required=false 즉, id
	 * 기억하기를 선택할 수도, 선택하지 않을 수도 있기 때문에 required-false로 설정해야 합니다. required=true 상태에서
	 * 지정한 이름을 가진 쿠키가 존재하지 않으면 스프링 MVC는 익셉션을 발생시킵니다.
	 */
	// http://localhost:8089/myhome4/member/login
	// 로그인 폼이동
	/*
	 * ModelAndView는 컴포넌트 즉 객체 방식으로 작성되고 돌려준다. 그래서 인자가 없으며 돌려주는 데이터형도 ModelAndView다.
	 * 데이터 추가는 addObject(key, value)로 추가하며, 페이지 이동값은 setViewName으로 페이지를 세팅한다. return
	 * 값은 ModelAndView로 돌려준다. 데이터와 이동하고자 하는 View Page를 같이 저장.
	 */
	// 주소 치고 들어올 수 있게 하면 get.(로그인, 회원가입) 데이터 넘어오는건 post.
	//LoginFailHandler 작성 후
	
	//<security:remember-me> 설정 후 로그인 유지를 위한 쿠키의 값 수정
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv, @CookieValue(value = "remember-me", required = false) Cookie readCookie,
								HttpSession session, Principal userPrincipal) {
		if (readCookie != null) {
			logger.info("����� ���̵� : " + userPrincipal.getName());  //principal.getName() : 로그인한 아이디
			mv.setViewName("redirect:/board/list");  //로그인 성공했다는 전제하에 이동. 관리자페이지에 Application-Cookies에 리멤버미값 들어왔는지 확인.
		}else {
			mv.setViewName("member/member_loginForm");
			mv.addObject("loginfail", session.getAttribute("loginfail")); //세션에 저장된 값을 한 번만 실행될 수 있도록 mv에 저장하고 (이 이름으로 된 값을 꺼내겠다.)
			session.removeAttribute("loginfail");  //세션의 값은 제거합니다. 안지우면 계속 경고문뜸
		}
		return mv;
	}

	// http://localhost:8089/myhome4/member/join
	// 회원가입 폼 이동
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join() {
		return "member/member_joinForm"; // WEB-INF/views/member/member_joinForm.jsp
	}

	// 회원가입폼에서 아이디 검사
	@RequestMapping(value = "/idcheck", method = RequestMethod.GET)
	public void idcheck(@RequestParam("id") String id, // member_joinForm에서 가져온 id값이 String형 id로 저장됨.
			HttpServletResponse response) throws Exception {
		int result = memberservice.isId(id);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result);
	}

	// 회원가입처리
	@RequestMapping(value = "/joinProcess", method = RequestMethod.POST)
	public String joinProcess(Member member, RedirectAttributes rattr, Model model, HttpServletRequest request) {

		// 비밀번호 암호화 추가.
		String encPassword = passwordEncoder.encode(member.getPassword());
		logger.info(encPassword);
		member.setPassword(encPassword);

		int result = memberservice.insert(member);

		// result=0; //에러페이지 확인용
				/*
				 * 스프링에서 제공하는 RedirectAttributes는 기존의 Servlet에서 사용되던 response.sendRedirect()를
				 * 사용할 때와 동일한 용도로 사용합니다. 리다이렉트로 전송하면 파라미터를 전달하고자 할 때 addAttribute()나
				 * addFlashAttribute()를 사용합니다. 예) response.sendRedirect("/test?result=1"); =>
				 * rattr.addAttribute("result", 1);
				 */
				// 삽입이 된 경우
		if (result == 1) {

			MailVO vo = new MailVO();// 객체생성
			vo.setTo(member.getEmail());// 누구에게 보낼지
			vo.setContent(member.getId() + "�� ȸ�� ������ ���ϵ帳�ϴ�.");// 내용 변경
			sendMail.sendMail(vo);// 메서드 호출. 파란색글씨면 필드에 있는것임. 선언 후 사용.

			rattr.addFlashAttribute("result", "joinSuccess");
			return "redirect:login";
		} else {
			model.addAttribute("url", request.getRequestURI());
			model.addAttribute("message", "회원 가입 실패");
			return "error/error";
		}
	}

	/*
	 * //로그인처리 시큐리티가 대신함. 없어도됨
	 * 
	 * @RequestMapping(value = "/loginProcess", method = RequestMethod.POST) public
	 * String loginProcess(@RequestParam("id") String id, @RequestParam("password")
	 * String password,
	 * 
	 * @RequestParam(value="remember", defaultValue="", required=false) String
	 * remember, HttpServletResponse response, HttpSession session,
	 * RedirectAttributes rattr){
	 * 
	 * int result = memberservice.isId(id, password); //isId메서드로 값 넘김
	 * logger.info("결과 : " + result);
	 * 
	 * 
	 * RedirectAttributes는 리다이렉트가 발생하기 전에 모든 플래시 속성을 세션에 복사한다. 리다이렉션 이후에는 저장된 플래시
	 * 속성을 세션에서 모델로 이동시킨다. 헤더에 파라미터를 붙이지 않기 때문에 URL에 노출되지 않는다. RedirectAttributes가
	 * 제공하는 메소드 addFlashAttribute는 리다이렉트 직전 플래시에 저장하는 메소드다. 리다이렉트 이후에 소멸한다.
	 * 
	 * if(result == 1) { //로그인 성공 session.setAttribute("id", id); Cookie savecookie
	 * = new Cookie("saveid",id); if(!remember.equals("")) { //체크를 했다.
	 * savecookie.setMaxAge(60*60); logger.info("쿠키저장 : 60*60"); }else {
	 * logger.info("쿠키저장 : 0"); savecookie.setMaxAge(0); }
	 * response.addCookie(savecookie); //응답으로 실어 보내야 값 전달됨.
	 * 
	 * return "redirect:/board/list"; }else { rattr.addFlashAttribute("result",
	 * result); return "redirect:login"; } }
	 */

	// 로그아웃 - 시큐리티가 처리함
		/*
		 * @RequestMapping(value = "/logout", method = RequestMethod.GET) public String
		 * loginout(HttpSession session) { session.invalidate(); return
		 * "redirect:login"; }
		 */
		  
		//회원정보수정
		@RequestMapping(value="/update", method=RequestMethod.GET)
		public ModelAndView member_update(Principal principal,
										  ModelAndView mv) {
			String id = principal.getName(); //principal에 저장되어 있는 id값을 가져옴 
			
			if(id == null) {
				mv.setViewName("redirect:login");
				logger.info("id is null");
			} else {
				Member m = memberservice.member_info(id);
				mv.setViewName("member/member_updateForm");
				mv.addObject("memberinfo", m);
			}
			
			return mv;
		}

		// 수정처리
	@RequestMapping(value = "/updateProcess", method = RequestMethod.POST)
	public String updateProcess(Member member, Model model, HttpServletRequest request, RedirectAttributes rattr) {
		int result = memberservice.update(member);
		if (result == 1) {
			rattr.addFlashAttribute("result", "updateSuccess"); // Success한번만 보여주면 되는 것. 새로고침하면 updateSuccess없음.
			return "redirect:/board/list";
		} else {
			model.addAttribute("url", request.getRequestURL()); // model 에러페이지는 유지가 되어야함.
			model.addAttribute("message", "���� ���� ����"); // 새로고침해도 정보 수정 실패 정보가 남아있어야함. 모델은 정보가 계쏙 넘어있음.
			return "error/error"; // 요청을 받고 줄 때 구조를 생각해서 위의 구조를 작성해야함.
		}
	}

	/*
	 * 1. header.jsp에서 이동하는 경우 / 파라미터 없음
	 * href="${pageContext.request.contextPath}/member/list"
	 * 
	 * 2. member_list.jsp에서 이동하는 경우 <a
	 * href="list?page=2&search_field=-1&search_word=" class="page-link">2</a>
	 */

	@RequestMapping(value = "/list")
	public ModelAndView memberList(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "limit", defaultValue = "3", required = false) int limit, ModelAndView mv,
			@RequestParam(value = "search_field", defaultValue = "-1", required = false) int index, // search_field��
																									// �����ͼ� index�̸�����
																									// ����Ұ�
			@RequestParam(value = "search_word", defaultValue = "", required = false) String search_word) {

		int listcount = memberservice.getSearchListCount(index, search_word); // �� ����Ʈ ���� �޾ƿ�

		// 총 페이지 수
		int maxpage = (listcount + limit - 1) / limit;

		int startpage = ((page - 1) / 10) * 10 + 1;

		int endpage = startpage + 10 - 1;

		if (endpage > maxpage)
			endpage = maxpage;

		List<Member> list = memberservice.getSearchList(index, search_word, page, limit); // ��� �������� �ֵ�. limit�� �ٺ��⿡ �̿�

		mv.setViewName("member/member_list");
		mv.addObject("page", page);
		mv.addObject("maxpage", maxpage);
		mv.addObject("startpage", startpage);
		mv.addObject("endpage", endpage);
		mv.addObject("listcount", listcount);
		mv.addObject("memberlist", list);
		mv.addObject("search_field", index);
		mv.addObject("search_word", search_word);
		return mv;
	}

	// 회원의 개인 정보
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public ModelAndView member_info(@RequestParam("id") String id, ModelAndView mv, HttpServletRequest request) { // URL��
																													// �Ȱ����ðŸ�
																													// ������
		Member m = memberservice.member_info(id);
		// m=null; 오류확인하는 값
		if (m != null) {
			mv.setViewName("member/member_info");
			mv.addObject("memberinfo", m);
		} else {
			mv.addObject("url", request.getRequestURL()); // 에러났을 때 화면에 출력되는 URL주소
			mv.addObject("message", "�ش� ������ �����ϴ�.");
			mv.setViewName("error/error");
		}
		return mv;
	}

	// 회원삭제
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String member_delete(String id) {
		memberservice.delete(id);
		return "redirect:list";
	}

}
