<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>MVC 게시판</title>
<jsp:include page="header.jsp"/>
<script src="../resources/js/modifyform.js"></script>  <!-- 보이는 칸은 js에서 해줘야함 -->
	<style>
	 .container{width:60%}
	 h1{font-size:1.5rem; text-align:center; color:#1a92b9}
	 #upfile{display:none}  /*파일첨부 칸 안보이게 숨김 */
	</style>
<script>
if('${result}'=='passFail'){
	alert("비밀번호가 다릅니다.")
}
</script>
</head>
<body>
<%--게시판 수정 --%>
 <div class="container">
  <form action="modifyAction" method="post" enctype="multipart/form-data" name="modifyform"> 
  	<input type="hidden" name="BOARD_NUM" value="${boarddata.BOARD_NUM }">  <!-- 수정할 번호의 게시판을 넘겨주는 쿼리 -->
  	<h1>MVC 게시판-수정</h1>
  	<div class="form-group">
  		<label for="board_name">글쓴이</label>   <!-- 라벨for 이름과 id가 똑같아야됨-->
  		<input type="text" name="BOARD_NAME" class="form-control" value="${boarddata.BOARD_NAME }" readOnly>
  	</div>
  	<div class="form-group">
	  	<label for="board_subject">제목</label>   <!-- 라벨for 이름과 id가 똑같아야됨-->
  		<textarea name="BOARD_SUBJECT" id="board_subject" rows="1"
  			   class="form-control" maxlength="100">${boarddata.BOARD_SUBJECT }</textarea>
  	</div>
  		<div class="form-group">
	  	<label for="board_content">내용</label>   <!-- 라벨for 이름과 id가 똑같아야됨-->
  		<textarea name="BOARD_CONTENT" id="board_content"
  				  rows="15" class="form-control">${boarddata.BOARD_CONTENT }</textarea>
  	</div>
  	<%--원문글인 경우에만 파일 첨부 수정 가능합니다. --%>
  	<c:if test="${boarddata.BOARD_RE_LEV==0 }">
  	<div class="form-group">
  	  <label for="board_file">파일 첨부</label>
    		<label for="upfile">
    		 	<img src = "../resources/image/attach.png" alt="파일첨부" width="20px">
    		</label>
  	  <input type="file" id="upfile" name="uploadfile">
  	  <span id="filevalue">${boarddata.BOARD_ORIGINAL }</span>
  	  <img src="../resources/image/remove.png" alt="파일삭제" width="10px" class="remove">
  	</div>
  	</c:if>
  	
  	<div class="form-group">
	  	<label for="board_pass">비밀번호</label>   <!-- 라벨for 이름과 id가 똑같아야됨-->
  		<input name="BOARD_PASS" id="board_pass" type="password" size="10" maxlength="30"
  			   class="form-control" placeholder="Enter board_pass">
  	</div>
  	
  	<div class="form-group">
  		<button type=submit class="btn btn-primary">수정</button>
  		<button type=reset class="btn btn-danger" onClick="history.go(-1)">취소</button>
  	</div>
  </form>
 </div>
</body>
</html>