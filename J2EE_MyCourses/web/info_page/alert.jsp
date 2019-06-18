<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/2/9
  Time: 23:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String alert = (String) session.getAttribute("alert");
    if (alert != null && ! alert.equals("")) {
%>
<script type="text/javascript">
    alert('<%= alert %>');
</script>
<%
    }
    session.removeAttribute("alert");
%>
