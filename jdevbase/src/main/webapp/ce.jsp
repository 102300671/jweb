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
    <title>课堂练习 - Java学习平台</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .exercise-card { 
            background: white; border: 1px solid #ddd; border-radius: 8px; 
            padding: 20px; margin: 15px 0; 
        }
        .exercise-card h3 { margin-top: 0; color: #007bff; }
        .sub-exercises { margin-left: 20px; margin-top: 10px; }
        .sub-exercise { 
            background: #f8f9fa; padding: 10px; margin: 5px 0; border-radius: 4px;
        }
    </style>
</head>
<body>
    <h1>📚 课堂练习</h1>
    <p>选择要查看的课堂练习：</p>
    
    <% for (Exercise exercise : exercises) { %>
        <div class="exercise-card">
            <h3>
                <!-- 修复链接路径 - 直接使用相对路径 -->
                <a href="<%= exercise.getId() %>/">
                    <%= exercise.getTitle() %>
                </a>
            </h3>
            <p><%= exercise.getDescription() %></p>
            
            <% if (exercise.getSubExercises() != null && !exercise.getSubExercises().isEmpty()) { %>
                <div class="sub-exercises">
                    <strong>子练习：</strong>
                    <% for (Exercise sub : exercise.getSubExercises()) { %>
                        <div class="sub-exercise">
                            <!-- 修复子练习链接路径 -->
                            <a href="<%= exercise.getId() %>/<%= sub.getId() %>/">
                                <%= sub.getTitle() %>
                            </a>
                        </div>
                    <% } %>
                </div>
            <% } %>
        </div>
    <% } %>
    
    <div style="margin-top: 30px;">
        <a href="<%= contextPath %>/">← 返回首页</a>
    </div>
</body>
</html>