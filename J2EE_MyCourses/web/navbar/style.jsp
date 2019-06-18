<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/2/7
  Time: 21:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<style>
    body {
        margin: 0;
    }

    ul {
        list-style-type: none;
        margin: 0;
        padding: 0;
        width: 25%;
        background-color: #f1f1f1;
        position: fixed;
        height: 100%;
        overflow: auto;
    }

    li a {
        display: block;
        color: #000;
        padding: 8px 16px;
        text-decoration: none;
    }

    li a.active {
        background-color: #4CAF50;
        color: white;
    }

    li a:hover:not(.active) {
        background-color: #555;
        color: white;
    }
</style>
