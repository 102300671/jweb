<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String displayCode = (String) request.getAttribute("displayCode");
    String executionResult = (String) request.getAttribute("executionResult");
    Boolean isSuccess = (Boolean) request.getAttribute("isSuccess");
    String uploadedFileName = (String) request.getAttribute("uploadedFileName");
    String contextPath = request.getContextPath();
    
    if (displayCode == null) {
        displayCode = "public class HelloWorld {\n    public static void main(String[] args) {\n        System.out.println(\\\"Hello, Java Web!\\\");\n    }\n}";
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Java代码在线运行器</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background: white;
            border-radius: 15px;
            box-shadow: 0 20px 40px rgba(0,0,0,0.1);
            overflow: hidden;
        }
        .header {
            background: #2c3e50;
            color: white;
            padding: 25px;
            text-align: center;
        }
        .header h1 {
            font-size: 2.5rem;
            margin-bottom: 10px;
        }
        .header p {
            opacity: 0.8;
            font-size: 1.1rem;
        }
        .content {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 0;
            min-height: 600px;
        }
        .editor-section, .output-section {
            padding: 25px;
        }
        .editor-section {
            background: #f8f9fa;
            border-right: 2px solid #e9ecef;
        }
        .output-section {
            background: #1a1a1a;
            color: #00ff00;
            font-family: 'Courier New', monospace;
        }
        .section-title {
            font-size: 1.3rem;
            margin-bottom: 15px;
            color: #2c3e50;
            font-weight: 600;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .output-title {
            color: #00ff00;
        }
        .code-display {
            width: 100%;
            height: 300px;
            font-family: 'Courier New', monospace;
            font-size: 14px;
            border: 2px solid #ddd;
            border-radius: 8px;
            padding: 15px;
            background: white;
            line-height: 1.5;
            overflow-y: auto;
            resize: none;
            color: #333;
        }
        .code-display:read-only {
            background: #f8f9fa;
            color: #495057;
        }
        .output-area {
            width: 100%;
            height: 400px;
            background: #1a1a1a;
            color: #00ff00;
            border: 2px solid #333;
            border-radius: 8px;
            padding: 15px;
            overflow-y: auto;
            white-space: pre-wrap;
            font-size: 14px;
            line-height: 1.5;
        }
        .upload-section {
            background: white;
            border: 2px dashed #007bff;
            border-radius: 8px;
            padding: 20px;
            text-align: center;
            margin-top: 20px;
            transition: all 0.3s ease;
        }
        .upload-section:hover {
            background: #f8f9ff;
            border-color: #0056b3;
        }
        .upload-section.dragover {
            background: #e3f2fd;
            border-color: #2196f3;
        }
        .file-input {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .button-group {
            margin-top: 20px;
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
        }
        .btn {
            padding: 12px 24px;
            border: none;
            border-radius: 6px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 8px;
        }
        .btn-run {
            background: #28a745;
            color: white;
        }
        .btn-run:hover {
            background: #218838;
            transform: translateY(-2px);
        }
        .btn-example {
            background: #17a2b8;
            color: white;
        }
        .btn-example:hover {
            background: #138496;
            transform: translateY(-2px);
        }
        .btn-back {
            background: #6c757d;
            color: white;
        }
        .btn-back:hover {
            background: #5a6268;
            transform: translateY(-2px);
        }
        .examples-section {
            margin-top: 25px;
            padding: 20px;
            background: white;
            border-top: 2px solid #e9ecef;
        }
        .examples-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 15px;
            margin-top: 15px;
        }
        .example-card {
            background: #f8f9fa;
            padding: 15px;
            border-radius: 8px;
            border: 1px solid #dee2e6;
            cursor: pointer;
            transition: all 0.3s ease;
        }
        .example-card:hover {
            background: #e9ecef;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }
        .example-card h4 {
            color: #2c3e50;
            margin-bottom: 8px;
        }
        .example-card p {
            color: #6c757d;
            font-size: 0.9rem;
            line-height: 1.4;
        }
        .nav-buttons {
            padding: 20px;
            background: #f8f9fa;
            border-top: 2px solid #e9ecef;
            display: flex;
            justify-content: space-between;
        }
        .file-info {
            background: #e7f3ff;
            border: 1px solid #b3d9ff;
            border-radius: 4px;
            padding: 10px;
            margin: 10px 0;
            font-size: 0.9rem;
        }
        .file-info.success {
            background: #d4edda;
            border-color: #c3e6cb;
            color: #155724;
        }
        .file-info.error {
            background: #f8d7da;
            border-color: #f5c6cb;
            color: #721c24;
        }
        @media (max-width: 768px) {
            .content {
                grid-template-columns: 1fr;
            }
            .editor-section {
                border-right: none;
                border-bottom: 2px solid #e9ecef;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>🚀 Java代码在线运行器</h1>
            <p>上传Java文件并在线运行，查看实时执行结果</p>
        </div>
        
        <form method="post" action="<%= contextPath %>/code-runner" enctype="multipart/form-data" id="uploadForm">
            <div class="content">
                <div class="editor-section">
                    <h2 class="section-title">
                        📝 代码展示区
                        <% if (uploadedFileName != null) { %>
                            <span style="font-size: 0.9rem; color: #28a745;">(当前文件: <%= uploadedFileName %>)</span>
                        <% } %>
                    </h2>
                    
                    <!-- 只读的代码展示框 -->
                    <textarea class="code-display" readonly><%= displayCode %></textarea>
                    
                    <!-- 文件上传区域 -->
                    <div class="upload-section" id="uploadSection">
                        <h3>📁 上传Java文件运行</h3>
                        <p>选择或拖拽 .java 文件到此区域</p>
                        <input type="file" name="javaFile" accept=".java" class="file-input" id="fileInput" required>
                        <p style="font-size: 0.9rem; color: #6c757d; margin: 10px 0;">
                            支持单个 .java 文件，最大 1MB
                        </p>
                        
                        <div class="button-group">
                            <button type="submit" name="action" value="run" class="btn btn-run">
                                ▶️ 运行代码
                            </button>
                        </div>
                    </div>
                    
                    <% if (uploadedFileName != null) { %>
                        <div class="file-info <%= isSuccess != null && isSuccess ? "success" : "error" %>">
                            📄 当前文件: <strong><%= uploadedFileName %></strong>
                            <% if (isSuccess != null) { %>
                                - <strong><%= isSuccess ? "✅ 执行成功" : "❌ 执行失败" %></strong>
                            <% } %>
                        </div>
                    <% } %>
                </div>
                
                <div class="output-section">
                    <h2 class="section-title output-title">
                        <% if (isSuccess != null) { %>
                            <% if (isSuccess) { %>
                                ✅ 执行结果
                            <% } else { %>
                                ❌ 执行结果
                            <% } %>
                        <% } else { %>
                            📤 执行结果
                        <% } %>
                    </h2>
                    <div class="output-area" id="outputArea">
                        <% if (executionResult != null) { %>
                            <%= executionResult %>
                        <% } else { %>
// 运行结果将显示在这里
// 上传Java文件并点击"运行代码"查看执行结果
                        <% } %>
                    </div>
                </div>
            </div>
        </form>
        
        <div class="examples-section">
            <h2 class="section-title">💡 代码示例</h2>
            <p>点击下面的示例查看代码（仅供查看，运行请上传文件）：</p>
            
            <div class="examples-grid">
                <div class="example-card" onclick="loadExample('hello')">
                    <h4>Hello World</h4>
                    <p>经典的Java入门程序</p>
                </div>
                <div class="example-card" onclick="loadExample('variables')">
                    <h4>变量和数据类型</h4>
                    <p>学习Java的基本数据类型</p>
                </div>
                <div class="example-card" onclick="loadExample('loop')">
                    <h4>循环结构</h4>
                    <p>for循环和while循环示例</p>
                </div>
                <div class="example-card" onclick="loadExample('array')">
                    <h4>数组操作</h4>
                    <p>数组的声明、初始化和遍历</p>
                </div>
                <div class="example-card" onclick="loadExample('method')">
                    <h4>方法定义</h4>
                    <p>学习如何定义和调用方法</p>
                </div>
                <div class="example-card" onclick="loadExample('class')">
                    <h4>类和对象</h4>
                    <p>面向对象编程基础</p>
                </div>
            </div>
        </div>
        
        <div class="nav-buttons">
            <a href="<%= contextPath %>/" class="btn btn-back">← 返回首页</a>
            <div>
                <a href="<%= contextPath %>/ce/" class="btn btn-example">📚 课堂练习</a>
                <a href="<%= contextPath %>/lab/" class="btn btn-example">💻 上机作业</a>
            </div>
        </div>
    </div>

    <script>
        // 加载代码示例到展示框
        function loadExample(type) {
            let code = '';
            
            switch(type) {
                case 'hello':
                    code = `public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, Java Web!");
    }
}`;
                    break;
                case 'variables':
                    code = `public class VariablesExample {
    public static void main(String[] args) {
        int age = 25;
        double salary = 5000.50;
        String name = "张三";
        System.out.println("姓名: " + name);
        System.out.println("年龄: " + age);
        System.out.println("薪资: " + salary);
    }
}`;
                    break;
                case 'loop':
                    code = `public class LoopExample {
    public static void main(String[] args) {
        for (int i = 1; i <= 5; i++) {
            System.out.println("当前数字: " + i);
        }
    }
}`;
                    break;
                case 'array':
                    code = `public class ArrayExample {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5};
        for (int num : numbers) {
            System.out.println("数组元素: " + num);
        }
    }
}`;
                    break;
                case 'method':
                    code = `public class MethodExample {
    public static void main(String[] args) {
        greet("李四");
        int result = add(10, 20);
        System.out.println("10 + 20 = " + result);
    }
    
    public static void greet(String name) {
        System.out.println("你好, " + name + "!");
    }
    
    public static int add(int a, int b) {
        return a + b;
    }
}`;
                    break;
                case 'class':
                    code = `class Student {
    private String name;
    private int age;
    
    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public void displayInfo() {
        System.out.println("姓名: " + name);
        System.out.println("年龄: " + age);
    }
}

public class ClassExample {
    public static void main(String[] args) {
        Student student = new Student("王五", 20);
        student.displayInfo();
    }
}`;
                    break;
            }
            
            document.querySelector('.code-display').value = code;
            
            // 清除之前的文件信息和结果
            document.querySelectorAll('.file-info').forEach(el => el.remove());
            document.getElementById('outputArea').textContent = '// 运行结果将显示在这里\n// 上传Java文件并点击"运行代码"查看执行结果';
        }
        
        // 拖拽上传功能
        const uploadSection = document.getElementById('uploadSection');
        const fileInput = document.getElementById('fileInput');
        
        uploadSection.addEventListener('dragover', (e) => {
            e.preventDefault();
            uploadSection.classList.add('dragover');
        });
        
        uploadSection.addEventListener('dragleave', () => {
            uploadSection.classList.remove('dragover');
        });
        
        uploadSection.addEventListener('drop', (e) => {
            e.preventDefault();
            uploadSection.classList.remove('dragover');
            
            const files = e.dataTransfer.files;
            if (files.length > 0) {
                const file = files[0];
                if (file.name.toLowerCase().endsWith('.java')) {
                    fileInput.files = files;
                    
                    // 显示文件信息
                    showFileInfo(file.name);
                    
                    // 自动读取文件内容并显示（可选）
                    readFileContent(file);
                } else {
                    alert('请上传 .java 文件');
                }
            }
        });
        
        // 文件选择变化事件
        fileInput.addEventListener('change', function() {
            if (this.files.length > 0) {
                const file = this.files[0];
                showFileInfo(file.name);
                readFileContent(file);
            }
        });
        
        function showFileInfo(fileName) {
            // 移除现有的文件信息
            document.querySelectorAll('.file-info').forEach(el => el.remove());
            
            const fileInfo = document.createElement('div');
            fileInfo.className = 'file-info';
            fileInfo.innerHTML = `📄 已选择文件: <strong>${fileName}</strong> - 点击"运行代码"执行`;
            
            document.querySelector('.editor-section').insertBefore(
                fileInfo, 
                document.querySelector('.button-group')
            );
        }
        
        function readFileContent(file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                document.querySelector('.code-display').value = e.target.result;
            };
            reader.readAsText(file);
        }
        
        // 表单提交验证
        document.getElementById('uploadForm').addEventListener('submit', function(e) {
            const fileInput = document.getElementById('fileInput');
            if (!fileInput.files.length) {
                e.preventDefault();
                alert('请选择要上传的Java文件');
                return false;
            }
            
            const file = fileInput.files[0];
            if (!file.name.toLowerCase().endsWith('.java')) {
                e.preventDefault();
                alert('请上传 .java 文件');
                return false;
            }
            
            // 显示加载状态
            const runButton = document.querySelector('.btn-run');
            const originalText = runButton.innerHTML;
            runButton.innerHTML = '⏳ 运行中...';
            runButton.disabled = true;
            
            // 恢复按钮状态（如果页面重新加载，这个不会执行）
            setTimeout(() => {
                runButton.innerHTML = originalText;
                runButton.disabled = false;
            }, 5000);
        });
    </script>
</body>
</html>