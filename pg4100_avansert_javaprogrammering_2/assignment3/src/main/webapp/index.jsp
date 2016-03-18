<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>PG4100 assignment3</title>
    <meta charset="utf-8" />
</head>
<body>
<form action="<c:url value="/prime-check" />" method="get">
    <input type="text" name="prime" placeholder="Enter prime number" />
    <input type="submit" value="Check value" />
</form>
</body>
</html>