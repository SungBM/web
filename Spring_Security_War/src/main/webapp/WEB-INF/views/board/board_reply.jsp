<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<meta >
<html>
<head>
<title>MVC 게시판</title>
<jsp:include page="header.jsp"/>
<script src="js/reply.js"></script>  <!-- 보이는 칸은 js에서 해줘야함 -->
	<style>
	 .container{width:60%}
	 h1{font-size:1.5rem; text-align:center; color:#1a92b9}
	 label{font-weight:bold}
	</style>
</head>
<body>
 <div class="container">
  <form action="replyAction" method="post" name="boardform"> 
 	 <!-- replay는 ref, lev, seq를 꼭 챙겨야함 -->
  	<input type="hidden" name="BOARD_RE_REF" value="${boarddata.BOARD_RE_REF }">
  	<input type="hidden" name="BOARD_RE_LEV" value="${boarddata.BOARD_RE_LEV }">
  	<input type="hidden" name="BOARD_RE_SEQ" value="${boarddata.BOARD_RE_SEQ }">
  	<h1>MVC 게시판-Reply</h1>
  	<div class="form-group">
  		<label for="board_name">글쓴이</label>   <!-- 라벨for 이름과 id가 똑같아야됨-->
  		<input name="BOARD_NAME" id="board_name" type="text" class="form-control" value="${id}" readOnly>
  	</div>
  	<div class="form-group">
	  	<label for="board_subject">제목</label>   <!-- 라벨for 이름과 id가 똑같아야됨-->
  		<textarea name="BOARD_SUBJECT" id="board_subject" rows="1"
  			   class="form-control" maxlength="100">Re:${boarddata.BOARD_SUBJECT }</textarea>
  	</div>
  		<div class="form-group">
	  	<label for="board_content">내용</label>   <!-- 라벨for 이름과 id가 똑같아야됨-->
  		<textarea name="BOARD_CONTENT" id="board_content"
  				  rows="10" class="form-control"></textarea>
  	</div>
  	<div class="form-group">
	  	<label for="board_pass">비밀번호</label>   <!-- 라벨for 이름과 id가 똑같아야됨-->
  		<input name="BOARD_PASS" id="board_pass" type="password" 
  			   class="form-control">
  	</div>
  	
  	<div class="form-group">
  		<input type=submit class="btn btn-primary" value="등록">
  		<input type=button class="btn btn-danger" value="취소" onClick="history.go(-1)">
  	</div>
  	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
  </form>
 </div>
</body>
</html>
