package com.example.gradequery;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.ethan.ethanutils.KeyboardUtil;
import com.example.gradequery.databinding.ActivityMainBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.*;
import jxl.read.biff.BiffException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Ethan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        List<User> students = getUserFromSheet();

        binding.button.setOnClickListener((View v) -> {
            String userName = binding.editTextPersonName.getText().toString();
            String studentId = binding.editTextStudentId.getText().toString();
            if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(studentId)) {
                Toast.makeText(this, "请输入完整信息", Toast.LENGTH_SHORT).show();
            } else {
                search(students, userName, studentId);
            }
        });

        // TODO: 2021/11/30
        binding.home.setOnTouchListener((View v, MotionEvent event) -> {
            //            binding.home.setFocusable(true);
            //            binding.home.setFocusableInTouchMode(true);//使用触摸可以重获焦点
            //            binding.home.requestFocus();
            KeyboardUtil.closeKeyboard(this);
            return false;
        });
    }

    /**
     * 从excel中读取数据
     *
     * @return 用户信息集合
     */
    public List<User> getUserFromSheet() {
        List<User> users = new ArrayList<>();
        Cell U_CLASS, U_NAME, U_ID, U_GRADE;
        try {
            Workbook book = Workbook.getWorkbook(getAssets().open("总表.xls"));//此处每次需修改

            Sheet sheet = book.getSheet(0);//拿到第1个sheet表
            for (int i = 1; i < sheet.getRows(); i++) {//从第2行开始,因为第1行是列名
                //读取每行信息,i是列,i1是行
                U_CLASS = sheet.getCell(1, i);
                U_NAME = sheet.getCell(2, i);
                U_ID = sheet.getCell(3, i);
                U_GRADE = sheet.getCell(4, i);

                Log.d(TAG, "getUserFromSheet: U_CLASS = " + U_CLASS.getContents());
                Log.d(TAG, "getUserFromSheet: U_NAME = " + U_NAME.getContents());
                Log.d(TAG, "getUserFromSheet: U_ID = " + U_ID.getContents());
                Log.d(TAG, "getUserFromSheet: U_GRADE = " + U_GRADE.getContents());

                //如果名字/学号为空,那么直接跳过此条目(班级和成绩可以为空)
                if (TextUtils.isEmpty(U_NAME.getContents()) || TextUtils.isEmpty(U_ID.getContents()))
                    continue;

                //防止出现空格干扰
                String s_class = U_CLASS.getContents().replaceAll(" ", "");
                String s_name = U_NAME.getContents().replaceAll(" ", "");
                String s_id = U_ID.getContents().replaceAll(" ", "");
                String s_grade = U_GRADE.getContents().replaceAll(" ", "");

                User user = new User(s_class, s_name, s_id, s_grade);
                users.add(user);
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

        for (User u : list) {
            if (u.getStudentId().equalsIgnoreCase(s_id) && u.getName().equals(s_name)) {
                print(u);//显示班级姓名学号成绩
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