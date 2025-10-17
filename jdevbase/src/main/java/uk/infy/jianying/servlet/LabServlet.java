package uk.infy.jianying.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/lab/*")
public class LabServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        System.out.println("=== LabServlet 调试信息 ===");
        System.out.println("完整路径: " + request.getRequestURI());
        System.out.println("路径信息: " + pathInfo);
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // 显示上机作业列表
            System.out.println("显示上机作业列表");
            uk.infy.jianying.service.ExerciseService exerciseService = new uk.infy.jianying.service.ExerciseService();
            request.setAttribute("exercises", exerciseService.getLabExercises());
            request.getRequestDispatcher("/lab.jsp").forward(request, response);
            return;
        }
        
        // 解析路径，如 /lab01
        String[] pathParts = pathInfo.split("/");
        System.out.println("路径分割: " + java.util.Arrays.toString(pathParts));
        
        if (pathParts.length >= 2) {
            // 修复路径拼接 - 直接使用路径部分，不重复添加 "lab"
            String exerciseId = pathParts[1];
            System.out.println("实验ID: " + exerciseId);
            
            uk.infy.jianying.service.ExerciseService exerciseService = new uk.infy.jianying.service.ExerciseService();
            uk.infy.jianying.model.Exercise exercise = exerciseService.getExercise("lab", exerciseId);
            
            if (exercise != null) {
                System.out.println("找到实验: " + exercise.getTitle());
                request.setAttribute("exercise", exercise);
                request.getRequestDispatcher("/exercise.jsp").forward(request, response);
                return;
            } else {
                System.out.println("未找到实验: lab/" + exerciseId);
            }
        }
        
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}