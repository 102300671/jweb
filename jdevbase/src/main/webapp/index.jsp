<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Java学习平台</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .nav-card { 
            background: white; border: 2px solid #007bff; border-radius: 10px; 
            padding: 30px; margin: 20px 0; text-align: center; text-decoration: none;
            color: #007bff; display: block; transition: all 0.3s;
        }
        .nav-card:hover { 
            background: #007bff; color: white; transform: translateY(-5px); 
            box-shadow: 0 10px 20px rgba(0,123,255,0.3);
        }
        .container { max-width: 600px; margin: 0 auto; }
    </style>
</head>
<body>
    <div class="container">
        <h1 style="text-align: center;">🎯 Java学习平台</h1>
        
        <!-- 修复所有链接，使用正确的上下文路径 -->
        <a href="<%= contextPath %>/ce/" class="nav-card">
            <h2>📚 课堂练习</h2>
            <p>CE01, CE02, CE03...</p>
        </a>
        
        <a href="<%= contextPath %>/lab/" class="nav-card">
            <h2>💻 上机作业</h2>
            <p>LAB01, LAB02, LAB03...</p>
        </a>
        
        <a href="<%= contextPath %>/code-runner" class="nav-card">
            <h2>🚀 代码运行器</h2>
            <p>在线编写和运行Java代码</p>
        </a>
    </div>
</body>
</html>