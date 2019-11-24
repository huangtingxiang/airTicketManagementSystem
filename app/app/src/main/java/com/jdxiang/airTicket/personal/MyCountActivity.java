package com.jdxiang.airTicket.personal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jdxiang.airTicket.MainActivity;
import com.jdxiang.airTicket.R;
import com.jdxiang.airTicket.httpService.BaseHttpService;
import com.jdxiang.airTicket.login.LoginActivity;

public class MyCountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        setContentView(R.layout.activity_my_count);
        TextView textView = findViewById(R.id.flight_title_text);
        textView.setText("我的账户");

        Button button = (Button) findViewById(R.id.logout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 退出登陆 修改basehttp token信息 并修改数据库中token
                BaseHttpService.setToken("");
                SharedPreferences.Editor edit = MyCountActivity.this.getSharedPreferences("user_message", MODE_PRIVATE).edit();
                edit.putString("token", "");
                edit.apply();
                // 返回登陆界面
                Intent intent = new Intent(MyCountActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
