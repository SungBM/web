<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
 <meta name="_csrf" content="${_csrf.token }">
<title>회원관리 시스템 회원수정 페이지</title>
<link href="../resources/css/join.css" type="text/css" rel="stylesheet">
<script src="../resources/js/jquery-3.6.3.js"></script>
<style>
h3{text-align: conter; color: #1a92b9;}
input[type=file]{display:none;}
</style>
</head>
<body>
<jsp:include page="../board/header.jsp" />
	<form name="joinform" action="updateProcess" method="post" >
		<h3>회원정보수정페이지</h3>
		<hr>
		<b>아이디</b>
		<input type="text" name="id" value="${memberinfo.id }" readOnly>  <!-- ${memberinfo.id }는 controller에서 넘긴 값 -->
		
		<b>비밀번호</b>
		<input type="password" name="password" value="${memberinfo.password }" readOnly>
		
		<b>이름</b>
		<input type="text" name="name" value="${memberinfo.name }" placeholder="Enter name" required>
		
		<b>나이</b>
		<input type="text" name="age" value="${memberinfo.age }" placeholder="Enter age" maxLength="2">
		
		<b>성별</b>
		<div>
			<input type="radio" name="gender" value="남" ><span>남자</span>
			<input type="radio" name="gender" value="여" ><span>여자</span>
		</div>
		
		<b>이메일 주소</b>
		<input type="text" name="email" value="${memberinfo.email }" placeholder="Enter email" required>
		<span id="email_message"></span>

	
		<div class="clearfix">
			<button type="submit" class="submitbtn">수정</button>
			<button type="reset" class="cancelbtn">취소</button>
		</div>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
	</form>
<script>
	$("input[value='${memberinfo.gender}']").prop("checked", true); //성별체크
	
	$(".cancelbtn").click(function(){
		history.back();
	});
	
	//처음 화면 로드시 보여줄 이메일은 이미 체크 완료된 것이므로 기본 checkemail=true입니다.
	let checkemail=true;
	$("input:eq(6)").on('keyup',
		function(){
		$("#email_message").empty();
		const pattern = /^\w+@\w+[.]\w{3}$/;
		const email = $("input:eq(6)").val();
		if(!pattern.test(email)){
			$("#email_message").css('color', 'red').html("이메일 형식이 맞지 않습니다.");
		}else{
			$("#email_message").css('color', 'green').html("이메일형식에 맞습니다.");
			checkemail=true;
		}
	});	 //email keyup이벤트 처리 끝
	
	$('form[name=updateform]').submit(function(){  //위에 name이랑 맞춰야함. 
		if(!$.isNumeric($("input[name='age']").val())){
			alert("나이는 숫자를 입력하세요");
			$("input[name='age']").val('').focus();
			return false;
		}
		if(!checkemail){
			alert("email 형식을 확인하세요");
			$("input:eq(6)").focus();
			return false;
		}
	}) //submit()
	
	$('input[type=file]').change(function(event){
		const inputfile = $(this).val().split('\\');
		const filename=inputfile[inputfile.length - 1];  //inputfile.length - 1 = 2
		
		const pattern = /(gif|jpg|jpeg|png)$/i;  // i(ignore case)는 대소문자 무시를 의미
		if(pattern.test(filename)){
			$('#filename').text(filename);
			
			const reader = new FileReader();	//파일을 읽기 위한 객체 생성
			
			//DataURL 형식(접두사 data:가 붙은 URL이며 바이너리 파일을 Base64로 인코딩하여 ASCII 문자열 형식으로 변환한 것)
			//파일을 읽어옵니다. (참고-Base64인코딩은 바이너리 데이터를 Text로 변경하는 Encoding입니다.)
			//읽어온 결과는 reader객체의 result 속성에 저장됩니다.
			//event.target.files[0] : 파일리스트에서 첫번째 객체를 가져옵니다.
			//event.target은 input[type=file]이 객체임. files = 여러개 가능. 파일 2개 올리려면 [0, 1]
			reader.readAsDataURL(event.target.files[0]);
			
			//onload읽어라. 데이터 URL형식으로. this=reader임.
			reader.onload = function(){
				$("#showImage >img").attr('src', this.result);
			};
		}else{
			alert('이미지 파일(gif, jpg, jpeg, png)이 아닌 경우는 무시됩니다.');
			$('#filename').text('');
			$('#showImage > img').attr('src', 'image/profile.png');
			$(this).val('')
			$('input[name=check]').val('');
		}
	}) //change()
	
</script>
</body>
</html>