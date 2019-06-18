<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/2/13
  Time: 10:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<script type="text/javascript">
    function addCourseware() {
        var coursewares = document.getElementById("coursewares");
        var tmp = document.createElement('p');
        tmp.id = '0';
        if (coursewares.childElementCount > 0)
            tmp.id = '' + (parseInt(coursewares.lastElementChild.id) + 1);
        tmp.innerHTML = '<input type="file" name="files"><input type="button" value="x" onclick="deleteCourseware(' + tmp.id + ')">';
        coursewares.appendChild(tmp);
    }
    function deleteCourseware(id) {
        document.getElementById(id).remove();
    }
</script>
