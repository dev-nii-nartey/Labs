<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>ARRAY-ARRANGER</title>
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
        h1 {
            color: white;
            background-color: #007BFF;
            padding: 10px;
            border-radius: 5px;
        }
        label {
            font-weight: bold;
            font-size: 16px;
        }
        input[type="text"] {
            width: calc(100% - 22px);
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 16px;
        }
        select {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 16px;
            font-family: 'Courier New', Courier, monospace;
            font-weight: bold;
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
        }
        .button:hover {
            background-color: #0056b3;
        }
        .error {
            color: red;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>ARRAY-ARRANGER</h1>
    <form action="sort" method="post">
        <label for="numbers">Enter numbers to sort (comma-separated):</label>
        <input type="text" id="numbers" name="numbers" required>

        <label for="algorithm">Algorithm Selector:</label>
        <select id="algorithm" name="algorithm">
            <option value="heap">Heap Sort</option>
            <option value="quick">Quick Sort</option>
            <option value="merge">Merge Sort</option>
            <option value="radix">Radix Sort</option>
            <option value="bucket">Bucket Sort</option>
        </select>

        <button type="submit" class="button">Sort</button>
        </br>
        <a href="${pageContext.request.contextPath}/arrays" class="button">View Saved Arrays</a>
    </form>
    <c:if test="${not empty errorMessage}">
        <p class="error">${errorMessage}</p>
    </c:if>


</div>
</body>
</html>
