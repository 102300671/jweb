<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="uk.infy.jianying.model.Exercise" %>
<%@ page import="uk.infy.jianying.model.CodeExample" %>
<%
    Exercise exercise = (Exercise) request.getAttribute("exercise");
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <title><%= exercise.getTitle() %> - Java学习平台</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .exercise-content { 
            background: white; border: 1px solid #ddd; border-radius: 8px; 
            padding: 30px; margin: 20px 0; 
        }
        .code-example { 
            background: #f5f5f5; padding: 15px; border-radius: 5px; 
            margin: 15px 0; font-family: monospace;
        }
        .btn { 
            background: #007bff; color: white; padding: 10px 20px; 
            text-decoration: none; border-radius: 4px; display: inline-block;
        }
    </style>
</head>
<body>
    <h1><%= exercise.getTitle() %></h1>
    
    <div class="exercise-content">
        <h2>练习描述</h2>
        <p><%= exercise.getDescription() %></p>
        
        <% if (exercise.getCodeExamples() != null && !exercise.getCodeExamples().isEmpty()) { %>
            <h2>相关代码示例</h2>
            <% for (CodeExample example : exercise.getCodeExamples()) { %>
                <div class="code-example">
                    <h4><%= example.getFileName() %></h4>
                    <pre><%= example.getContent() %></pre>
                    <a href="<%= contextPath %>/code-runner?code=<%= java.net.URLEncoder.encode(example.getContent(), "UTF-8") %>" 
                       class="btn" target="_blank">
                        在代码运行器中打开
                    </a>
                </div>
            <% } %>
        <% } %>
    </div>
    
    <div style="margin-top: 20px;">
        <!-- 修复返回链接 -->
        <% if ("ce".equals(exercise.getType())) { %>
            <a href="<%= contextPath %>/ce/">← 返回课堂练习列表</a>
        <% } else { %>
            <a href="<%= contextPath %>/lab/">← 返回上机作业列表</a>
        <% } %>
        &nbsp;|&nbsp;
        <a href="<%= contextPath %>/">返回首页</a>
    </div>
</body>
</html>