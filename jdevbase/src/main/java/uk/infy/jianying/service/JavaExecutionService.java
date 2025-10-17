package uk.infy.jianying.service;

import uk.infy.jianying.model.ExecutionResult;
import javax.tools.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.concurrent.*;

public class JavaExecutionService {
    
    private static final long TIMEOUT_SECONDS = 10;
    
    public ExecutionResult executeJavaCode(String javaCode) {
        // 提取类名
        String className = extractClassName(javaCode);
        if (className == null) {
            return new ExecutionResult(false, "", "错误：无法从代码中提取类名。请确保代码包含一个public类。");
        }
        
        // 创建临时目录
        File tempDir = createTempDirectory();
        if (tempDir == null) {
            return new ExecutionResult(false, "", "错误：无法创建临时目录。");
        }
        
        try {
            // 编译Java代码
            CompilationResult compilationResult = compileJavaCode(javaCode, className, tempDir);
            if (!compilationResult.isSuccess()) {
                return new ExecutionResult(false, "", "编译错误：\n" + compilationResult.getErrorOutput());
            }
            
            // 执行Java代码
            return executeCompiledClass(className, tempDir);
            
        } catch (Exception e) {
            return new ExecutionResult(false, "", "执行错误：" + e.getMessage());
        } finally {
            // 清理临时文件
            deleteTempDirectory(tempDir);
        }
    }
    
    private String extractClassName(String javaCode) {
        // 简单的类名提取逻辑
        if (javaCode.contains("public class")) {
            int start = javaCode.indexOf("public class") + 12;
            int end = javaCode.indexOf("{", start);
            if (end > start) {
                String className = javaCode.substring(start, end).trim();
                // 移除可能的extends/implements
                if (className.contains(" ")) {
                    className = className.substring(0, className.indexOf(" "));
                }
                return className;
            }
        }
        return "Main"; // 默认类名
    }
    
    private File createTempDirectory() {
        try {
            File tempDir = File.createTempFile("java_exec_", Long.toString(System.nanoTime()));
            if (!tempDir.delete() || !tempDir.mkdir()) {
                return null;
            }
            return tempDir;
        } catch (IOException e) {
            return null;
        }
    }
    
    private void deleteTempDirectory(File tempDir) {
        if (tempDir != null && tempDir.exists()) {
            File[] files = tempDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
            tempDir.delete();
        }
    }
    
    private CompilationResult compileJavaCode(String javaCode, String className, File outputDir) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            return new CompilationResult(false, "错误：无法获取Java编译器。请确保运行在JDK环境中。");
        }
        
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
        
        // 创建内存中的Java文件
        JavaFileObject javaFileObject = new JavaSourceFromString(className, javaCode);
        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(javaFileObject);
        
        // 设置编译选项
        List<String> options = Arrays.asList(
            "-d", outputDir.getAbsolutePath(),
            "-cp", outputDir.getAbsolutePath()
        );
        
        // 执行编译
        JavaCompiler.CompilationTask task = compiler.getTask(
            null, fileManager, diagnostics, options, null, compilationUnits
        );
        
        boolean success = task.call();
        
        // 收集诊断信息
        StringBuilder errorOutput = new StringBuilder();
        for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
            errorOutput.append(diagnostic.toString()).append("\n");
        }
        
        try {
            fileManager.close();
        } catch (IOException e) {
            errorOutput.append("文件管理器关闭错误：").append(e.getMessage());
        }
        
        return new CompilationResult(success, errorOutput.toString());
    }
    
    private ExecutionResult executeCompiledClass(String className, File classPath) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<ExecutionResult> future = executor.submit(() -> {
            try {
                // 设置类路径
                URLClassLoader classLoader = new URLClassLoader(
                    new URL[]{classPath.toURI().toURL()}
                );
                
                // 加载类
                Class<?> loadedClass = classLoader.loadClass(className);
                
                // 重定向标准输出
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                PrintStream originalOut = System.out;
                PrintStream originalErr = System.err;
                
                try {
                    System.setOut(new PrintStream(outputStream, true, "UTF-8"));
                    System.setErr(new PrintStream(outputStream, true, "UTF-8"));
                    
                    // 查找main方法
                    Method mainMethod = loadedClass.getMethod("main", String[].class);
                    
                    // 调用main方法
                    mainMethod.invoke(null, (Object) new String[]{});
                    
                    // 恢复标准输出
                    System.setOut(originalOut);
                    System.setErr(originalErr);
                    
                    String output = outputStream.toString("UTF-8");
                    return new ExecutionResult(true, output, "");
                    
                } catch (Exception e) {
                    System.setOut(originalOut);
                    System.setErr(originalErr);
                    String output = outputStream.toString("UTF-8") + "\n运行时错误：" + e.getCause().getMessage();
                    return new ExecutionResult(false, output, e.getCause().getMessage());
                } finally {
                    classLoader.close();
                }
                
            } catch (Exception e) {
                return new ExecutionResult(false, "", "类加载错误：" + e.getMessage());
            }
        });
        
        try {
            return future.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            return new ExecutionResult(false, "", "错误：代码执行超时（超过" + TIMEOUT_SECONDS + "秒）");
        } catch (Exception e) {
            return new ExecutionResult(false, "", "执行错误：" + e.getMessage());
        } finally {
            executor.shutdownNow();
        }
    }
    
    // 内部类：编译结果
    private static class CompilationResult {
        private final boolean success;
        private final String errorOutput;
        
        public CompilationResult(boolean success, String errorOutput) {
            this.success = success;
            this.errorOutput = errorOutput;
        }
        
        public boolean isSuccess() { return success; }
        public String getErrorOutput() { return errorOutput; }
    }
    
    // 内部类：Java源代码对象（内存中）
    private static class JavaSourceFromString extends SimpleJavaFileObject {
        final String code;
        
        JavaSourceFromString(String name, String code) {
            super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.code = code;
        }
        
        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return code;
        }
    }
}