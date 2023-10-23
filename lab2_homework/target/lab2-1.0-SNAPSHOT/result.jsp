<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="graph" scope="request" type="com.example.lab2.model.Output" />
<!DOCTYPE html>
<html>
<head>
    <title>Graph Information</title>
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

        p {
            font-size: 16px;
            margin: 10px 0;
        }

        .info-box {
            background-color: #ecf0f1;
            border: 1px solid #bdc3c7;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            width: 50%;
            margin: 0 auto;
            padding: 20px;
            text-align: left;
        }
    </style>
</head>
<body>
<h1>Graph Analysis Results</h1>
<div class="info-box">
    <p>Order (Number of Vertices): ${graph.order}</p>
    <p>Size (Number of Edges): ${graph.size}</p>
    <p>Number of Connected Components: ${graph.numberOfConnectedComponents}</p>
</div>
</body>
</html>
