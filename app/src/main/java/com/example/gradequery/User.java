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
    private final String studentId;
    private final String grade;

    public User(String classId, String name, String studentId, String grade) {
        this.classId = classId;
        this.name = name.replaceAll(" ","");//防止出现“王  芳”
        this.studentId = studentId;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public String getStudentId() {
        return studentId;
    }

    public String toString(){
        return String.format("班级：%s\n姓名：%s\n学号：%s\n成绩：%s",classId,name,studentId,grade);

    }
}
