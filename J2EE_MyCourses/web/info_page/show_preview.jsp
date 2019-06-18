<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/2/10
  Time: 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<script type="text/javascript">
    function showPreview(source) {
        var file = source.files[0];
        if (window.FileReader) {
            var fr = new FileReader();
            console.log(fr);
            var portrait = document.getElementById('portrait');
            fr.onloadend = function (ev) { portrait.src = ev.target.result; };
            fr.readAsDataURL(file);
            portrait.style.display = 'block';
        }
    }
</script>
