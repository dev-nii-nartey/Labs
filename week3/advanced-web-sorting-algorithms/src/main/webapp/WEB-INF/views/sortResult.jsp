<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sort Result</title>
</head>
<body>
<h1>Sort Result</h1>
<p>Sorted Array:</p>
<p>${sortedArray.toString()}</p>
<a href="${pageContext.request.contextPath}/sort">Go back</a>
</body>
</html>
