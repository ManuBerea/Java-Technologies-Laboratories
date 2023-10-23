<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Upload DIMACS Graph</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #e7eff6;
            text-align: center;
            margin-top: 40px;
        }

        h1 {
            color: #2c3e50;
        }

        form {
            background-color: #ecf0f1;
            border: 1px solid #bdc3c7;
            padding: 20px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            width: 50%;
            margin: 0 auto;
        }

        label {
            display: block;
            font-weight: bold;
            margin-bottom: 10px;
            color: #34495e;
        }

        input[type="file"] {
            display: block;
            width: 95%;
            padding: 12px;
            margin: 10px 0 20px;
            border: 1px solid #bdc3c7;
            border-radius: 4px;
        }

        input[type="submit"] {
            background-color: #3498db;
            color: white;
            padding: 12px 24px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }

        input[type="submit"]:hover {
            background-color: #2980b9;
        }
    </style>
</head>
<body>
<h1>Graph Upload Portal</h1>
<form action="${pageContext.request.contextPath}/processing" method="post" enctype="multipart/form-data">
    <label for="graphFile">Please choose a DIMACS Format Graph File:</label>
    <input type="file" name="graphFile" id="graphFile" required><br>
    <input type="submit" value="Submit">
</form>
</body>
</html>
