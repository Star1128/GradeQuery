package com.example.gradequery;

/**
 * NOTE:
 *
 * @author wxc 2021/7/28
 * @version 1.0.0
 */
public class User {
    private final String classId;
    private final String name;
    private final Long studentId;
    private final String grade;
    private final String remarks;

    public User(String classId, String name, Long studentId, String grade, String remarks) {
        this.classId = classId;
        this.name = name.replaceAll(" ","");//防止出现“王  芳”
        this.studentId = studentId;
        this.grade = grade;
        this.remarks = remarks;
    }

    public String getName() {
        return name;
    }

    public Long getStudentId() {
        return studentId;
    }

    public String toString(){
        return String.format("班级：%s\n姓名：%s\n学号：%s\n成绩：%s\n备注：%s",classId,name,studentId,grade,remarks);
    }
}
