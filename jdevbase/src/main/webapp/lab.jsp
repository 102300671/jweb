<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="uk.infy.jianying.model.Exercise" %>
<%
    List<Exercise> exercises = (List<Exercise>) request.getAttribute("exercises");
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <title>上机作业 - Java学习平台</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .exercise-card { 
            background: white; border: 1px solid #ddd; border-radius: 8px; 
            padding: 20px; margin: 15px 0; 
        }
        .exercise-card h3 { margin-top: 0; color: #28a745; }
    </style>
</head>
<body>
    <h1>💻 上机作业</h1>
    <p>选择要查看的上机作业：</p>
    
    <% for (Exercise exercise : exercises) { %>
        <div class="exercise-card">
            <h3>
                <!-- 修复链接路径 - 直接使用相对路径 -->
                <a href="<%= exercise.getId() %>/">
                    <%= exercise.getTitle() %>
                </a>
            </h3>
            <p><%= exercise.getDescription() %></p>
        </div>
    <% } %>
    
    <div style="margin-top: 30px;">
        <!-- 修复返回链接 -->
        <a href="<%= contextPath %>/">← 返回首页</a>
    </div>
</body>
</html>