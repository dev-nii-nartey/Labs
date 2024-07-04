<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sort Form</title>
</head>
<body>
<h1>Sort Form</h1>
<form action="${pageContext.request.contextPath}/sort" method="post">
    <label for="algorithm">Choose an algorithm:</label>
    <select id="algorithm" name="algorithm">
        <option value="heap">Heap Sort</option>
        <option value="quick">Quick Sort</option>
        <option value="merge">Merge Sort</option>
        <option value="radix">Radix Sort</option>
        <option value="bucket">Bucket Sort</option>
    </select>
    <br><br>
    <label for="numbers">Enter numbers (comma-separated):</label>
    <input type="text" id="numbers" name="numbers">
    <br><br>
    <input type="submit" value="Sort">
</form>
</body>
</html>
