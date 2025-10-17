package uk.infy.jianying.model;

import java.util.List;
import java.util.ArrayList;

public class Exercise {
    private String id;
    private String type;
    private String title;
    private String description;
    private String content;
    private List<CodeExample> codeExamples;
    private List<Exercise> subExercises;
    
    // 构造函数
    public Exercise() {
        this.codeExamples = new ArrayList<>();
        this.subExercises = new ArrayList<>();
    }
    
    public Exercise(String id, String type, String title) {
        this();
        this.id = id;
        this.type = type;
        this.title = title;
    }
    
    // Getter 和 Setter 方法
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public List<CodeExample> getCodeExamples() {
        return codeExamples;
    }
    
    public void setCodeExamples(List<CodeExample> codeExamples) {
        this.codeExamples = codeExamples;
    }
    
    public List<Exercise> getSubExercises() {
        return subExercises;
    }
    
    public void setSubExercises(List<Exercise> subExercises) {
        this.subExercises = subExercises;
    }
}