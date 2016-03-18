<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>${genre}</title>
</head>
<body>
    <h1>Musikk-anbefalinger</h1>
    <center>Laget av ronesp13</center>
    <br/>
    <p>Du valgte ${genre}; da b&oslash;r du kanskje fors&oslash;ke en av disse:</p>
    <ul>
    <c:forEach items="${albumList}" var="album">
        <li>${album}</li>
    </c:forEach>
    </ul>
    <a href="<c:url value="/MusicChoiceHTML.html"/>">Nytt s&oslash;k</a>
</body>
</html>
