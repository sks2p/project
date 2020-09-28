<%@ page import="com.kb.www.common.LoginManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    LoginManager lm = LoginManager.getInstance();
    String id = lm.getMemberId(session);
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시판</title>
</head>
<body>
<a href="/list.do">게시판이동</a>
<%if(id == null){%>
<a href="/join.do">회원가입</a>
<a href="/login.do">로그인</a>
<%} else {%>
<a href="/logout.do">로그아웃</a>
<%}%>
</body>
</html>
