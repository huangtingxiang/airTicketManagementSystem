package com.jdxiang.airTicket.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jdxiang.airTicket.MainActivity;
import com.jdxiang.airTicket.R;
import com.jdxiang.airTicket.entity.User;
import com.jdxiang.airTicket.entity.Visitor;
import com.jdxiang.airTicket.httpService.BaseHttpService;
import com.jdxiang.airTicket.httpService.UserService;

import cn.pedant.SweetAlert.SweetAlertDialog;

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
            // 展示等待框
            SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("请求中......");
            pDialog.setCancelable(false);
            pDialog.show();
            userService.register(new BaseHttpService.CallBack() {
                @Override
                public void onSuccess(BaseHttpService.HttpTask.CustomerResponse result) {
                    pDialog.cancel();
                    if (result.getResponse().code() == 500) {
                        new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("服务器错误!")
                                .setConfirmText("确定").show();
                    }
                    if (result.getResponse().code() >= 200 && result.getResponse().code() < 300) {
                        String token = result.getResponse().header(UserService.tokenHeader);
                        // 将token存储到文件中 并修改basehttp 中的token值
                        SharedPreferences.Editor edit = RegisterActivity.this.getSharedPreferences("user_message", MODE_PRIVATE).edit();
                        edit.putString("token", token);
                        edit.putString("username", username);
                        edit.putString("password", password);
                        edit.apply();
                        BaseHttpService.setToken(token);
                        new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("注册成功!")
                                .setConfirmText("确定")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                }).show();
                    }
                }
            }, user);
        });
    }
}
