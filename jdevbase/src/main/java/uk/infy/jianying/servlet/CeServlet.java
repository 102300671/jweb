package uk.infy.jianying.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/ce/*")
public class CeServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        System.out.println("=== CeServlet 调试信息 ===");
        System.out.println("完整路径: " + request.getRequestURI());
        System.out.println("路径信息: " + pathInfo);
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // 显示课堂练习列表
            System.out.println("显示课堂练习列表");
            uk.infy.jianying.service.ExerciseService exerciseService = new uk.infy.jianying.service.ExerciseService();
            request.setAttribute("exercises", exerciseService.getCeExercises());
            request.getRequestDispatcher("/ce.jsp").forward(request, response);
            return;
        }
        
        // 解析路径，如 /ce01 或 /ce02/ce02-1
        String[] pathParts = pathInfo.split("/");
        System.out.println("路径分割: " + java.util.Arrays.toString(pathParts));
        
        if (pathParts.length >= 2) {
            // 修复路径拼接 - 直接使用路径部分，不重复添加 "ce"
            String exerciseId = pathParts[1];
            System.out.println("练习ID: " + exerciseId);
            
            // 如果是多级路径，如 /ce02/ce02-1
            if (pathParts.length > 2) {
                exerciseId = pathParts[1] + "/" + pathParts[2];
                System.out.println("多级练习ID: " + exerciseId);
            }
            
            uk.infy.jianying.service.ExerciseService exerciseService = new uk.infy.jianying.service.ExerciseService();
            uk.infy.jianying.model.Exercise exercise = exerciseService.getExercise("ce", exerciseId);
            
            if (exercise != null) {
                System.out.println("找到练习: " + exercise.getTitle());
                request.setAttribute("exercise", exercise);
                request.getRequestDispatcher("/exercise.jsp").forward(request, response);
                return;
            } else {
                System.out.println("未找到练习: ce/" + exerciseId);
            }
        }
        
        // 未找到，显示404
        System.out.println("显示404错误");
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}