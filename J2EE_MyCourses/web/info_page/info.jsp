<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2018/12/30
  Time: 18:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<jsp:useBean id="info" type="edu.nju.wsql.beans.InfoBean" scope="request" />
<head>
    <title><%= info.getTitle() %></title>
</head>
<body>
<h1  align=center><%= info.getH1() %></h1>
<div align=center style='padding:10px'>
    <%= info.getContent() %>
</div>
<br>
<form method="get" action="<%= info.getReturnUrl() %>">
    <div align=center style='padding:10px'>
        <input type='submit' value='返回'>
    </div>
</form>
</body>
</html>
