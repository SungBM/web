<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>회원관리 시스템 로그인 페이지</title>
<link href="${pageContext.request.contextPath }/resources/css/login.css" type="text/css" rel="stylesheet" >
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.6.3.js"></script>
<script>
	if("${result}" == 'joinSuccess'){
		alert("회원가입을 축하합니다.")
	}else if("${loginfail}" == 'loginFailMsg'){	//이 경고문을 한번만 보고싶어서 세션에 담긴 값을 지움. 컨트롤러에서.
		alert("아이디나 비밀번호 오류 입니다.")
	}
	
	$(function(){
		$(".join").click(function(){
			location.href="${pageContext.request.contextPath}/member/join";   //이 방식으로 넘기는거 GET방식임.
		});
	})
</script>
</head>
<body>
 <form name="loginform" action="${pageContext.request.contextPath }/member/loginProcess" method="post">
  <h1>로그인</h1>
  <hr>
  <b>아이디</b>
  <input type="text" name="id" placeholder="Enter id" id="id" required>
  
  <b>Password</b>
   	<input type="password" name="password" placeholder="Enter password"  required>
   	<label>
   		<input type="checkbox" name="remember-me" style="margin-bottom:15px"> 로그인 유지하기
   	</label>
   	
   <div class="clearfix">
   	<button type="submit" class="submitbtn">로그인</button>
   	<button type="button" class="join">회원가입</button>
   </div>
   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
 </form>
</body>
</html>