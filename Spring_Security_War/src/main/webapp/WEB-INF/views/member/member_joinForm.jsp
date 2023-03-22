<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>회원관리 시스템 회원가입 페이지</title>
<link href="${pageContext.request.contextPath }/resources/css/join.css" type="text/css" rel="stylesheet" >
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.6.3.js"></script>
<script>
	$(function(){
		let checkid=false;
		let checkemail=false;
		
		$("input[name=id]").on('keyup', function(){
			$("#message").empty(); //처음에 pattern에 적합하지 않은 경우 메시지 출력 후 적합한 데이터를 입력해도
								   //계속 같은 데이터 출력하므로 이벤트 시작할 때마다 비워둡니다.
			//[A-Za-z0-9_]의 의미는 \w
			const pattern = /^\w{5,12}$/;
			const id = $("input:eq(0)").val();
			if(!pattern.test(id)){
				$("#message").css('color','red').html("영어 숫자 _로 5~12자 가능합니다.");
				checkid=false;
				return;
			}
			
			$.ajax({
				url : "idcheck",  // 상대경로. 타입을 따로 주지 않아서 get 방식. id값을 컨트롤러로 보내고
				data : {"id" : id},  // "id"는 컨트롤러에 param으로 가는 id임.
				success : function(resp){
					if(resp == -1){ //db에 해당 id가 없는 경우
						$("#message").css('color','green').html("사용 가능한 아이디입니다.")
						checkid=true;
					}else{ //db에 해당 id가 있는 경우(0)
						$("#message").css('color', 'blue').html("사용중인 아이디입니다.")
						checkid=false;
						}
					}
				
				})//ajax end
			});//id keyup end

	
			$("input[name=email]").on('keyup', function(){
				//$("#email_message").empty();
				//[A-Za-z0-9_]와 동일한 것이 \w입니다.
				//+는 1회 이상 반복을 의미하고 {1,}와 동일합니다.
				//\w+는 [A-Za-z0-9_]를 1개이상 사용하라는 의미입니다.
				const pattern = /^\w+@\w+[.]\w{3}$/;
				const email_value = $(this).val();
				console.log(email_value)
				if(!pattern.test(email_value)){
					$("#email_message").css('color', 'red').html("이메일 형식이 맞지 않습니다.");
					checkemail=false;
				}else{
					$("#email_message").css('color', 'green').html("이메일 형식에 맞습니다.")
					checkemail=true;
				}
			}); //on
			
			$('form').submit(function(){
				if(!$.isNumeric($("input[name='age']").val())){
					alert("나이는 숫자를 입력하세요");
					$("input[name='age']").val('').focus();
					return false;
				}
				
				if(!checkid){
					alert("사용 가능한 id로 입력하세요.");
					$("input[name=id]").val('').focus();
					return false;
				}
				
				if(!checkemail){
					alert("email 형식을 확인하세요.");
					$("input[name=email]").focus();
					return false;
				}
			});//submit
	})//ready
</script>

</head>
<link rel="shortcut icon" href="#">
<body>
	<form name="joinform" action="joinProcess" method="post">
		<h1>회원가입</h1>
		<hr>
		<b>아이디</b>
		<input type="text" name="id" placeholder="Enter id" maxLength="12" required>
		<span id="message"></span>
		
		<b>비밀번호</b>
		<input type="password" name="password" placeholder="Enter password" required>
		
		<b>이름</b>
		<input type="text" name="name" placeholder="Enter name" maxLength="15" required>
		
		<b>나이</b><input type="text" name="age" placeholder="Enter age" maxLength="2" required>
		
		<b>성별</b>
		<div>
			<input type="radio" name="gender" value="남" checked><span>남자</span>
			<input type="radio" name="gender" value="여"><span>여자</span>
		</div>
		
		<b>이메일 주소</b>
		<input type="text" name="email" placeholder="Enter email" required>
		<span id="email_message"></span>
		<div class="clearfix">
			<button type="submit" class="submitbtn">회원가입</button>
			<button type="reset" class="cancelbtn">다시작성</button>
		</div>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">    <!-- 폼 안에 추가!!! -->
	</form>
</body>
</html>