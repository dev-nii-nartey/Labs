<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sort Result</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 90%; /* Adjusted width */
            max-width: 1200px; /* Max width for larger screens */
            margin: 50px auto;
            padding: 20px;
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1, h2, p {
            text-align: center;
        }
        h1, h2 {
            font-family: 'Brush Script MT', cursive; /* Calligraphy font */
        }
        .button {
            display: block;
            width: 100%;
            padding: 15px;
            font-size: 18px;
            background-color: #007BFF;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin-top: 20px;
            text-align: center;
        }
        .button:hover {
            background-color: #0056b3;
        }
        .info {
            font-style: italic;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Sort Result</h1>
    <h2>Original Array:</h2>
    <p>${originalArray}</p>

    <h2>Sorted Array:</h2>
    <p>${sortedArray}</p>

    <h2>Algorithm Used: ${chosenAlgorithm}</h2>
    <p class="info">
        The ${chosenAlgorithm} algorithm </br>
        ${timeComplexity}.
    </p>

    <h2>Try Another Algorithm:</h2>
    <c:forEach var="entry" items="${alternateAlgorithms}">
        <form action="${pageContext.request.contextPath}/sort" method="post" style="display:inline;">
            <input type="hidden" name="algorithm" value="${entry.key}">
            <input type="hidden" name="numbers" value="${originalArray}">
            <input type="submit" value="${entry.value}" class="button">
        </form>
    </c:forEach>

    <form action="${pageContext.request.contextPath}/sort" method="get">
        <input type="submit" value="Go Back" class="button">
    </form>
    <a href="${pageContext.request.contextPath}/arrays" class="button">View Saved Arrays</a>
</div>
</body>
</html>
