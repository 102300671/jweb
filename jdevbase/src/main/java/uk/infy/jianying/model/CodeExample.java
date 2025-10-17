package uk.infy.jianying.model;

public class CodeExample {
    private String fileName;
    private String filePath;
    private String content;
    
    // 默认构造函数
    public CodeExample() {}
    
    // 带参数构造函数
    public CodeExample(String fileName, String filePath, String content) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.content = content;
    }
    
    // Getter 和 Setter 方法
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
}