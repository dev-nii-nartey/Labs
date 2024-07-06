<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Array List</title>
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
        h1, h2 {
            text-align: center;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid #ccc;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
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
            text-align: center;
            margin-top: 20px;
        }
        .button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Array List</h1>
    <table>
        <thead>
            <tr>
                <th>Array</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="entry" items="${arrayEntries}">
                <tr>
                    <td>${entry.numbers}</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/arrays/delete/${entry.id}" method="post" style="display:inline;">
                            <input type="submit" value="Delete" class="button">
                        </form>
                        <form action="${pageContext.request.contextPath}/sort" method="post" style="display:inline;">
                            <input type="hidden" name="numbers" value="${entry.numbers}">

                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <a href="${pageContext.request.contextPath}/sort" class="button">Go Back to Sort Form</a>
</div>
</body>
</html>
