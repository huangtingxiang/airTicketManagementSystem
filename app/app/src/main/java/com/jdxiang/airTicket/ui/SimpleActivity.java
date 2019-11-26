package com.jdxiang.airTicket.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jdxiang.airTicket.R;

public class SimpleActivity extends AppCompatActivity {

    public static final int SimpleCode = 1;

    public static final String TITLE = "title";

    public static final String HINT = "hint";

    public static final String FILED = "filed";

    EditText filedEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // 获取信息 显示
        Intent intent = getIntent();
        String title = intent.getStringExtra(TITLE);
        String hint = intent.getStringExtra(HINT);
        filedEditText = findViewById(R.id.filedEdit);
        ((TextView) findViewById(R.id.titleTextView)).setText(title);
        filedEditText.setHint(hint);

        // 点击返回时 取消充值
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                SimpleActivity.this.finish();
            }
        });

        // 点击保存时 返回数据
        findViewById(R.id.saveBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取字段值 返回;
                Intent intentDate = new Intent();
                String price = filedEditText.getText().toString();
                intentDate.putExtra(FILED, filedEditText.getText().toString());
                setResult(RESULT_OK, intentDate);
                finish();
            }
        });
    }
}
