<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="header.jsp"/>
	<script src="../resources/js/writeform.js"></script>  <!-- 보이는 칸은 js에서 해줘야함 -->
	<style>
	 h1{font-size:1.5rem; text-align:center; color:#1a92b9}
	 .container{width:60%}
	 label{font-weight:bold}
	 #upfile{display:none}  /*파일첨부 칸 안보이게 숨김 */
	 img{width:20px}
	</style>
</head>
<body>
 <div class="container">
  <form action="add" method="post" enctype="multipart/form-data" name="boardform">  <!-- 업로드는 post방식으로 -->
  	<h1>MVC 게시판-write 페이지</h1>
  	<div class="form-group">
  		<label for="board_name">글쓴이</label>   <!-- 라벨for 이름과 id가 똑같아야됨-->
  		<input name="BOARD_NAME" id="board_name" value="${username }" readOnly
  			   type="text"  class="form-control" placeholder="Enter board_name">
  	</div>
  	<div class="form-group">
	  	<label for="board_pass">비밀번호</label>   <!-- 라벨for 이름과 id가 똑같아야됨-->
  		<input name="BOARD_PASS" id="board_pass" type="password" maxlength="30"
  			   class="form-control" placeholder="Enter board_pass">
  	</div>
  	<div class="form-group">
	  	<label for="board_subject">제목</label>   <!-- 라벨for 이름과 id가 똑같아야됨-->
  		<input name="BOARD_SUBJECT" id="board_subject" type="text" maxlength="100"
  			   class="form-control" placeholder="Enter board_subject">
  	</div>
  	<div class="form-group">
	  	<label for="board_content">내용</label>   <!-- 라벨for 이름과 id가 똑같아야됨-->
  		<textarea name="BOARD_CONTENT" id="board_content"
  				  rows="10" class="form-control"></textarea>
  	</div>
  	<div class="form-group">
  	  <label for="board_file">파일 첨부</label>
  	  <label for="upfile">
  	  	<img src="../resources/image/attach.png" alt="파일첨부">
  	  </label>
  	  <input type="file" id="upfile" name="uploadfile">
  	  <span id="filevalue"></span>
  	</div>
  	<div class="form-group">
  		<button type=submit class="btn btn-primary">등록</button>
  		<button type=reset class="btn btn-danger">취소</button>
  	</div>
  	   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
  </form>
 </div>
</body>
</html>