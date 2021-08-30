package com.example.gradequery;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.*;
import jxl.read.biff.BiffException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        EditText userName = findViewById(R.id.editTextPersonName);
        EditText studentId = findViewById(R.id.editTextStudentId);
        List<User> students = getUserFromSheet();

        button.setOnClickListener((View v) -> {
            String userName_s = userName.getText().toString();
            String studentId_s = studentId.getText().toString();
            if (userName_s.equals("") || studentId_s.equals("")) {
                Toast.makeText(this, "请输入完整信息", Toast.LENGTH_SHORT).show();
            } else {
                search(students, userName_s, studentId_s);
            }
        });
    }

    /**
     * 从excel中读取数据
     *
     * @return 用户信息库
     */
    public List<User> getUserFromSheet() {
        List<User> users = new ArrayList<>();
        int i = 1;
        Cell U_CLASS, U_NAME, U_ID, U_GRADE, U_REMARKS;
        try {
            Workbook book = Workbook.getWorkbook(getAssets().open("总表.xls"));//此处

            Sheet sheet = book.getSheet(0);
            while (i < sheet.getRows()) {
                U_CLASS = sheet.getCell(0, i);
                U_NAME = sheet.getCell(1, i);
                U_ID = sheet.getCell(2, i);
                U_GRADE = sheet.getCell(3, i);
                U_REMARKS = sheet.getCell(4, i);

                User user = new User(U_CLASS.getContents(), U_NAME.getContents(),
                        Long.parseLong(U_ID.getContents()), U_GRADE.getContents(), U_REMARKS.getContents());
                users.add(user);
                i++;
            }
            book.close();
        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * 查询算法
     *
     * @param list   用户信息库
     * @param s_name 输入的姓名
     * @param s_id   输入的学号
     */
    public void search(List<User> list, String s_name, String s_id) {

        char[] c_id = s_id.toCharArray();
        for (char c : c_id) {//但凡学号中有一位不是数字
            if (!Character.isDigit(c)) {
                Toast.makeText(this, "学号格式有误，请检查后重新输入", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        long l_id = Long.parseLong(s_id);

        for (User u : list) {
            if (u.getStudentId() == l_id && u.getName().equals(s_name)) {
                print(u);//显示班级姓名学号成绩备注
                return;
            }
        }
        //如果没有return出去就说明信息不符
        Toast.makeText(this, "输入信息有误，请检查后重新输入", Toast.LENGTH_SHORT).show();
    }

    /**
     * 使用intent向下一个活动传字符串
     *
     * @param u 查询到的用户对象
     */
    public void print(User u) {
        Intent intent = new Intent(this, PrintActivity.class);
        intent.putExtra("info", u.toString());
        startActivity(intent);
    }
}