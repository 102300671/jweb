package uk.infy.jianying.service;

import uk.infy.jianying.model.Exercise;
import uk.infy.jianying.model.CodeExample;
import java.util.*;
import java.io.*;
import java.nio.file.*;

public class ExerciseService {
    
    public List<Exercise> getCeExercises() {
        List<Exercise> exercises = new ArrayList<>();
        
        Exercise ce01 = new Exercise("ce01", "ce", "课堂练习 1: 基础语法");
        ce01.setDescription("Java基础语法练习，包括变量、数据类型、运算符等");
        ce01.setCodeExamples(loadCodeExamples("ce", "ce01"));
        
        Exercise ce02 = new Exercise("ce02", "ce", "课堂练习 2: 控制结构");
        ce02.setDescription("条件语句和循环结构的练习");
        ce02.setCodeExamples(loadCodeExamples("ce", "ce02"));
        
        // 添加子练习
        Exercise ce021 = new Exercise("ce02-1", "ce", "条件语句练习");
        ce021.setDescription("if-else语句练习");
        ce021.setCodeExamples(loadCodeExamples("ce", "ce02/ce02-1"));
        
        Exercise ce022 = new Exercise("ce02-2", "ce", "循环结构练习");
        ce022.setDescription("for/while循环练习");
        ce022.setCodeExamples(loadCodeExamples("ce", "ce02/ce02-2"));
        
        ce02.setSubExercises(Arrays.asList(ce021, ce022));
        
        Exercise ce03 = new Exercise("ce03", "ce", "课堂练习 3: 数组和字符串");
        ce03.setDescription("数组操作和字符串处理的练习");
        ce03.setCodeExamples(loadCodeExamples("ce", "ce03"));
        
        Exercise ce04 = new Exercise("ce04", "ce", "课堂练习 4: 面向对象");
        ce04.setDescription("类和对象的基本概念");
        ce04.setCodeExamples(loadCodeExamples("ce", "ce04"));
        
        Exercise ce05 = new Exercise("ce05", "ce", "课堂练习 5: 综合练习");
        ce05.setDescription("综合运用所学知识");
        ce05.setCodeExamples(loadCodeExamples("ce", "ce05"));
        
        exercises.add(ce01);
        exercises.add(ce02);
        exercises.add(ce03);
        exercises.add(ce04);
        exercises.add(ce05);
        
        return exercises;
    }
    
    public List<Exercise> getLabExercises() {
        List<Exercise> exercises = new ArrayList<>();
        
        Exercise lab01 = new Exercise("lab01", "lab", "实验 1: 环境配置和Hello World");
        lab01.setDescription("Java开发环境配置和第一个程序");
        lab01.setCodeExamples(loadCodeExamples("lab", "lab01"));
        
        Exercise lab02 = new Exercise("lab02", "lab", "实验 2: 面向对象编程基础");
        lab02.setDescription("类、对象、方法的定义和使用");
        lab02.setCodeExamples(loadCodeExamples("lab", "lab02"));
        
        Exercise lab03 = new Exercise("lab03", "lab", "实验 3: 集合框架使用");
        lab03.setDescription("List、Set、Map等集合类的使用");
        lab03.setCodeExamples(loadCodeExamples("lab", "lab03"));
        
        exercises.add(lab01);
        exercises.add(lab02);
        exercises.add(lab03);
        
        return exercises;
    }
    
    public Exercise getExercise(String type, String id) {
        // 创建基础练习对象
        Exercise exercise = new Exercise(id, type, "练习 " + id);
        
        // 设置具体信息
        Map<String, String> titles = new HashMap<>();
        titles.put("ce01", "课堂练习 1: 基础语法");
        titles.put("ce02", "课堂练习 2: 控制结构");
        titles.put("ce02-1", "条件语句练习");
        titles.put("ce02-2", "循环结构练习");
        titles.put("ce03", "课堂练习 3: 数组和字符串");
        titles.put("ce04", "课堂练习 4: 面向对象");
        titles.put("ce05", "课堂练习 5: 综合练习");
        titles.put("lab01", "实验 1: 环境配置和Hello World");
        titles.put("lab02", "实验 2: 面向对象编程基础");
        titles.put("lab03", "实验 3: 集合框架使用");
        
        Map<String, String> descriptions = new HashMap<>();
        descriptions.put("ce01", "Java基础语法练习，包括变量、数据类型、运算符等基本概念的学习和实践。");
        descriptions.put("ce02", "掌握Java中的条件语句(if-else)和循环结构(for/while)的使用方法。");
        descriptions.put("ce02-1", "通过实际编程练习if-else语句的各种用法和嵌套条件判断。");
        descriptions.put("ce02-2", "学习for循环、while循环的使用场景，掌握循环控制语句break和continue。");
        descriptions.put("ce03", "学习数组的声明、初始化和操作，掌握字符串的常用方法。");
        descriptions.put("ce04", "理解面向对象的基本概念，学习类和对象的创建与使用。");
        descriptions.put("ce05", "综合运用Java基础知识解决实际问题。");
        descriptions.put("lab01", "完成Java开发环境配置，编写并运行第一个Java程序Hello World。");
        descriptions.put("lab02", "实践面向对象编程，定义类、创建对象、使用方法完成具体任务。");
        descriptions.put("lab03", "学习Java集合框架，掌握List、Set、Map等常用集合类的使用方法。");
        
        exercise.setTitle(titles.getOrDefault(id, "练习 " + id));
        exercise.setDescription(descriptions.getOrDefault(id, "这是 " + id + " 的详细内容"));
        
        // 加载代码示例
        exercise.setCodeExamples(loadCodeExamples(type, id));
        
        return exercise;
    }
    
    /**
     * 加载指定练习的代码示例
     */
    private List<CodeExample> loadCodeExamples(String type, String id) {
        List<CodeExample> examples = new ArrayList<>();
        
        try {
            // 构建正确的资源路径 - 修复路径拼接问题
            String resourcePath = "exercises/" + type + "/" + id;
            System.out.println("正在加载资源路径: " + resourcePath); // 调试信息
            
            // 获取资源目录
            ClassLoader classLoader = getClass().getClassLoader();
            java.net.URL resourceUrl = classLoader.getResource(resourcePath);
            
            if (resourceUrl != null) {
                System.out.println("找到资源: " + resourceUrl); // 调试信息
                
                if ("file".equals(resourceUrl.getProtocol())) {
                    // 文件系统路径
                    Path exercisesPath = Paths.get(resourceUrl.toURI());
                    System.out.println("文件系统路径: " + exercisesPath); // 调试信息
                    
                    // 遍历目录中的所有Java文件
                    if (Files.exists(exercisesPath) && Files.isDirectory(exercisesPath)) {
                        Files.walk(exercisesPath)
                            .filter(path -> path.toString().endsWith(".java"))
                            .forEach(path -> {
                                try {
                                    String content = new String(Files.readAllBytes(path));
                                    String fileName = path.getFileName().toString();
                                    String relativePath = exercisesPath.relativize(path).toString();
                                    
                                    CodeExample example = new CodeExample();
                                    example.setFileName(fileName);
                                    example.setContent(content);
                                    example.setFilePath(relativePath);
                                    
                                    examples.add(example);
                                    System.out.println("成功加载文件: " + fileName); // 调试信息
                                } catch (IOException e) {
                                    System.err.println("读取文件失败: " + path + ", 错误: " + e.getMessage());
                                }
                            });
                    } else {
                        System.err.println("路径不存在或不是目录: " + exercisesPath);
                    }
                } else {
                    // 对于非文件系统，使用简单的资源加载
                    System.out.println("使用简单资源加载"); // 调试信息
                    loadCodeExamplesSimple(classLoader, resourcePath, examples);
                }
            } else {
                System.err.println("资源未找到: " + resourcePath);
                // 尝试使用简单资源加载作为备选
                loadCodeExamplesSimple(classLoader, resourcePath, examples);
            }
        } catch (Exception e) {
            System.err.println("加载代码示例失败: " + type + "/" + id + ", 错误: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("总共加载了 " + examples.size() + " 个代码示例"); // 调试信息
        return examples;
    }
    
    /**
     * 简单的资源加载方法
     */
    private void loadCodeExamplesSimple(ClassLoader classLoader, String resourcePath, List<CodeExample> examples) {
        // 预定义的代码文件列表
        String[] javaFiles = getPredefinedJavaFiles(resourcePath);
        
        for (String javaFile : javaFiles) {
            // 修复路径拼接 - 确保没有重复的目录
            String fullPath = resourcePath + "/" + javaFile;
            System.out.println("尝试加载: " + fullPath); // 调试信息
            
            try (InputStream inputStream = classLoader.getResourceAsStream(fullPath)) {
                if (inputStream != null) {
                    String content = new String(inputStream.readAllBytes());
                    
                    CodeExample example = new CodeExample();
                    example.setFileName(javaFile);
                    example.setContent(content);
                    example.setFilePath(fullPath);
                    
                    examples.add(example);
                    System.out.println("成功加载资源文件: " + javaFile); // 调试信息
                } else {
                    System.err.println("资源文件未找到: " + fullPath);
                }
            } catch (IOException e) {
                System.err.println("读取资源文件失败: " + fullPath + ", 错误: " + e.getMessage());
            }
        }
    }
    
    /**
     * 获取预定义的Java文件列表
     */
    private String[] getPredefinedJavaFiles(String resourcePath) {
        // 根据路径返回对应的Java文件列表
        System.out.println("获取预定义文件列表: " + resourcePath); // 调试信息
        
        // 修复路径匹配逻辑
        if (resourcePath.equals("exercises/ce/ce01")) {
            return new String[]{"Love.java"};
        } else if (resourcePath.equals("exercises/ce/ce02")) {
            return new String[]{}; // ce02 目录本身没有Java文件，只有子目录
        } else if (resourcePath.equals("exercises/ce/ce02/ce02-1")) {
            return new String[]{"Tem.java"};
        } else if (resourcePath.equals("exercises/ce/ce02/ce02-2")) {
            return new String[]{"Grade.java"};
        } else if (resourcePath.equals("exercises/ce/ce03")) {
            return new String[]{"Maxin.java", "SumN.java"};
        } else if (resourcePath.equals("exercises/ce/ce04")) {
            return new String[]{"AboveAvg.java", "MaxSalary.java", "MaxSalaryStr.java"};
        } else if (resourcePath.equals("exercises/ce/ce05")) {
            return new String[]{"C.java", "Info_function.java", "Info_judge.java", "Info_print.java", "Max.java", "Student.java"};
        } else if (resourcePath.equals("exercises/lab/lab01")) {
            return new String[]{"B.java", "NumberInverse.java", "Swap.java"};
        } else if (resourcePath.equals("exercises/lab/lab02")) {
            return new String[]{"BMI.java", "Friend.java", "PerNum.java", "Stat.java"};
        } else if (resourcePath.equals("exercises/lab/lab03")) {
            return new String[]{"MinExchange.java", "PrimeChecker.java", "PrimePrint.java", "Sort.java"};
        } else {
            System.err.println("未找到预定义文件列表: " + resourcePath);
        }
        
        return new String[0];
    }
    
    /**
     * 获取所有练习的分类映射
     */
    public Map<String, List<Exercise>> getExercisesByCategory() {
        Map<String, List<Exercise>> categorized = new HashMap<>();
        
        List<Exercise> ceExercises = getCeExercises();
        List<Exercise> labExercises = getLabExercises();
        
        categorized.put("课堂练习", ceExercises);
        categorized.put("上机作业", labExercises);
        
        return categorized;
    }
    
    /**
     * 根据文件路径获取具体的代码内容
     */
    public String getCodeContent(String filePath) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            // 修复路径 - 确保没有重复前缀
            String fullPath = filePath.startsWith("exercises/") ? filePath : "exercises/" + filePath;
            System.out.println("获取代码内容: " + fullPath); // 调试信息
            
            try (InputStream inputStream = classLoader.getResourceAsStream(fullPath)) {
                if (inputStream != null) {
                    return new String(inputStream.readAllBytes());
                }
            }
        } catch (IOException e) {
            System.err.println("读取代码文件失败: " + filePath + ", 错误: " + e.getMessage());
        }
        return "// 代码文件未找到: " + filePath;
    }
}