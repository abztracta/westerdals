<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Error</title>
        <meta charset="UTF-8" />
    </head>
    <body>
        <h1 style="color: red">An error has occurred!</h1>
        <p>${error_message}</p>
        <center>Laget av ronesp13</center>
        <a href="<c:url value="/MusicChoiceHTML.html"/>">Nytt s&oslash;k</a>
    </body>
</html>
