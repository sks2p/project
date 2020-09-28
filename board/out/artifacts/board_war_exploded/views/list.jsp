<%@ page import="java.util.ArrayList" %>
<%@ page import="com.kb.www.vo.ArticleVo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ArrayList<ArticleVo> list = (ArrayList<ArticleVo>) request.getAttribute("list");
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>목록</title>
    <script>
        function goDetail(num) {
            location.href="/detail.do?num=" + num;
        }
    </script>
</head>
<body>
<table>
    <tr>
        <td>번호</td>
        <td>제목</td>
        <td>조회수</td>
        <td>작성자</td>
        <td>작성일</td>
    </tr>
    <%for (int i = 0; i < list.size(); i++) {%>
    <tr onclick="goDetail(<%=list.get(i).getNum()%>)">
        <td><%=list.get(i).getNum()%></td>
        <td><%=list.get(i).getSubject()%></td>
        <td><%=list.get(i).getHit()%></td>
        <td><%=list.get(i).getId()%></td>
        <td><%=list.get(i).getWdate()%></td>
    </tr>
    <%} %>
</table>
<button onclick="location.href='/write.do'">글쓰기</button>
</body>
</html>












