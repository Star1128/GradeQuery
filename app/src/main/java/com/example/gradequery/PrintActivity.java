package com.example.gradequery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PrintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        Intent intent = getIntent();
        String info = intent.getStringExtra("info");

//        int index = info.indexOf("备注") + 3;//作或者缺的位置
//        SpannableString alert = new SpannableString(info);
//        ForegroundColorSpan textAppearanceSpan = new ForegroundColorSpan(getResources().getColor(android.R.color.holo_red_light));
//        alert.setSpan(textAppearanceSpan, index, alert.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        TextView textView = findViewById(R.id.textView_print);
        textView.setText(info);
    }
}