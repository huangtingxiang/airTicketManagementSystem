package com.jdxiang.airTicket.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jdxiang.airTicket.R;
import com.jdxiang.airTicket.entity.User;
import com.jdxiang.airTicket.entity.Visitor;
import com.jdxiang.airTicket.httpService.BaseHttpService;
import com.jdxiang.airTicket.httpService.UserService;

public class RegisterActivity extends AppCompatActivity {

    UserService userService = new UserService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        TextView textTitle = findViewById(R.id.flight_title_text);
        textTitle.setText("注册账号");

        // 注册按钮点击时 创建user用户
        Button registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener((View view) -> {
            // 创建用户实体
            // 提取信息
            String username = ((EditText) findViewById(R.id.userNameText)).getText().toString();
            String password = ((EditText) findViewById(R.id.passwordText)).getText().toString();
            String name = ((EditText) findViewById(R.id.nameText)).getText().toString();
            String phoneNumber = ((EditText) findViewById(R.id.phoneText)).getText().toString();
            String idCard = ((EditText) findViewById(R.id.idCardText)).getText().toString();
            User user = new User(username, password, new Visitor(name, idCard, phoneNumber));
            userService.register(new BaseHttpService.CallBack() {
                @Override
                public void onSuccess(BaseHttpService.HttpTask.CustomerResponse result) {
                    String token = result.getResponse().header(UserService.tokenHeader);
                    System.out.println(token);
                }
            }, user);
        });
    }
}
