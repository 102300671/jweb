package uk.infy.jianying.servlet;

import uk.infy.jianying.service.JavaExecutionService;
import uk.infy.jianying.model.ExecutionResult;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.servlet.http.Part;

@WebServlet("/code-runner")
@MultipartConfig(
    maxFileSize = 1024 * 1024,      // 1MB
    maxRequestSize = 1024 * 1024 * 5, // 5MB
    fileSizeThreshold = 1024 * 1024  // 1MB
)
public class CodeRunnerServlet extends HttpServlet {
    
    private JavaExecutionService executionService = new JavaExecutionService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 检查是否有预加载的代码
        String exampleCode = request.getParameter("code");
        if (exampleCode != null) {
            request.setAttribute("displayCode", exampleCode);
        } else {
            // 默认显示Hello World示例
            request.setAttribute("displayCode", getDefaultExample());
        }
        
        // 显示代码运行器页面
        request.getRequestDispatcher("/code-runner.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("run".equals(action)) {
            // 处理文件上传
            handleFileUpload(request, response);
        } else {
            // 其他操作（如加载示例）
            String exampleType = request.getParameter("example");
            if (exampleType != null) {
                request.setAttribute("displayCode", getExampleCode(exampleType));
            }
            request.getRequestDispatcher("/code-runner.jsp").forward(request, response);
        }
    }
    
    /**
     * 处理Java文件上传和执行
     */
    private void handleFileUpload(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            Part filePart = request.getPart("javaFile");
            
            if (filePart == null || filePart.getSize() == 0) {
                request.setAttribute("executionResult", "❌ 错误：请选择要上传的Java文件");
                request.setAttribute("isSuccess", false);
                request.setAttribute("displayCode", getDefaultExample());
                request.getRequestDispatcher("/code-runner.jsp").forward(request, response);
                return;
            }
            
            // 检查文件类型
            String fileName = getFileName(filePart);
            if (!fileName.toLowerCase().endsWith(".java")) {
                request.setAttribute("executionResult", "❌ 错误：请上传.java文件");
                request.setAttribute("isSuccess", false);
                request.setAttribute("displayCode", getDefaultExample());
                request.getRequestDispatcher("/code-runner.jsp").forward(request, response);
                return;
            }
            
            // 读取文件内容
            String fileContent = readFileContent(filePart);
            
            // 设置显示的代码
            request.setAttribute("displayCode", fileContent);
            request.setAttribute("uploadedFileName", fileName);
            
            // 执行代码
            ExecutionResult result = executionService.executeJavaCode(fileContent);
            
            // 设置结果属性
            request.setAttribute("executionResult", formatExecutionResult(result, fileName));
            request.setAttribute("isSuccess", result.isSuccess());
            
        } catch (Exception e) {
            request.setAttribute("executionResult", "❌ 上传错误：" + e.getMessage());
            request.setAttribute("isSuccess", false);
            request.setAttribute("displayCode", getDefaultExample());
        }
        
        request.getRequestDispatcher("/code-runner.jsp").forward(request, response);
    }
    
    /**
     * 获取上传的文件名
     */
    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] tokens = contentDisposition.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return "unknown.java";
    }
    
    /**
     * 读取上传文件的内容
     */
    private String readFileContent(Part part) throws IOException {
        try (InputStream inputStream = part.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();
        }
    }
    
    /**
     * 格式化执行结果
     */
    private String formatExecutionResult(ExecutionResult result, String fileName) {
        StringBuilder formatted = new StringBuilder();
        
        if (result.isSuccess()) {
            formatted.append("✅ 执行成功 - ").append(fileName).append("\n");
            formatted.append("=".repeat(60)).append("\n\n");
            if (result.getOutput() != null && !result.getOutput().isEmpty()) {
                formatted.append(result.getOutput());
            } else {
                formatted.append("程序执行完成，但没有输出。");
            }
        } else {
            formatted.append("❌ 执行失败 - ").append(fileName).append("\n");
            formatted.append("=".repeat(60)).append("\n\n");
            if (result.getError() != null && !result.getError().isEmpty()) {
                formatted.append(result.getError());
            } else {
                formatted.append("执行过程中发生未知错误。");
            }
        }
        
        return formatted.toString();
    }
    
    /**
     * 获取默认示例代码
     */
    private String getDefaultExample() {
        return getExampleCode("hello");
    }
    
    /**
     * 根据类型获取示例代码
     */
    private String getExampleCode(String type) {
        switch(type) {
            case "hello":
                return "public class HelloWorld {\n    public static void main(String[] args) {\n        System.out.println(\"Hello, Java Web!\");\n    }\n}";
            case "variables":
                return "public class VariablesExample {\n    public static void main(String[] args) {\n        int age = 25;\n        double salary = 5000.50;\n        String name = \"张三\";\n        System.out.println(\"姓名: \" + name);\n        System.out.println(\"年龄: \" + age);\n        System.out.println(\"薪资: \" + salary);\n    }\n}";
            case "loop":
                return "public class LoopExample {\n    public static void main(String[] args) {\n        for (int i = 1; i <= 5; i++) {\n            System.out.println(\"当前数字: \" + i);\n        }\n    }\n}";
            case "array":
                return "public class ArrayExample {\n    public static void main(String[] args) {\n        int[] numbers = {1, 2, 3, 4, 5};\n        for (int num : numbers) {\n            System.out.println(\"数组元素: \" + num);\n        }\n    }\n}";
            case "method":
                return "public class MethodExample {\n    public static void main(String[] args) {\n        greet(\"李四\");\n        int result = add(10, 20);\n        System.out.println(\"10 + 20 = \" + result);\n    }\n    \n    public static void greet(String name) {\n        System.out.println(\"你好, \" + name + \"!\");\n    }\n    \n    public static int add(int a, int b) {\n        return a + b;\n    }\n}";
            case "class":
                return "class Student {\n    private String name;\n    private int age;\n    \n    public Student(String name, int age) {\n        this.name = name;\n        this.age = age;\n    }\n    \n    public void displayInfo() {\n        System.out.println(\"姓名: \" + name);\n        System.out.println(\"年龄: \" + age);\n    }\n}\n\npublic class ClassExample {\n    public static void main(String[] args) {\n        Student student = new Student(\"王五\", 20);\n        student.displayInfo();\n    }\n}";
            default:
                return getDefaultExample();
        }
    }
}