<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/2/9
  Time: 23:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<script type="text/javascript">
    function sure(message, elementId) {
        var yes = confirm(message);
        if (yes)
            document.getElementById(elementId).submit();
    }
</script>
