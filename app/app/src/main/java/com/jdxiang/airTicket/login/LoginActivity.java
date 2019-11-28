package com.jdxiang.airTicket.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.jdxiang.airTicket.MainActivity;
import com.jdxiang.airTicket.R;
import com.jdxiang.airTicket.entity.User;
import com.jdxiang.airTicket.entity.Visitor;
import com.jdxiang.airTicket.httpService.BaseHttpService;
import com.jdxiang.airTicket.httpService.UserService;
import com.jdxiang.airTicket.httpService.VisitorService;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class
LoginActivity extends AppCompatActivity {

    VisitorService visitorService = new VisitorService();

    UserService userService = new UserService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        setContentView(R.layout.activity_login);
        Button loginBtn = findViewById(R.id.loginBtn);
        // 登陆按钮点击时
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 进行用户登陆
                String password = ((TextView) findViewById(R.id.passwordText)).getText().toString();
                String username = ((TextView) findViewById(R.id.userNameText)).getText().toString();
                User user = new User(username, password, null);
                userService.login((response) -> {
                    User user1 = (User) response.getData();
                    UserService.loginUser = user1;
                    // 登陆成功
                    if (response.getResponse().code() >= 200 && response.getResponse().code() < 300) {
                        // 存储token 用户名 密码
                        String token = response.getResponse().header(UserService.tokenHeader);
                        SharedPreferences.Editor edit = LoginActivity.this.getSharedPreferences("user_message", MODE_PRIVATE).edit();
                        BaseHttpService.setToken(token);
                        edit.putString("token", token);
                        edit.putString("username", username);
                        edit.putString("password", password);
                        edit.apply();
                        // 进入主页面
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        // 登陆失败 提示错误
                        new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("用户名或密码错误!")
                                .setConfirmText("确定").show();
                    }
                }, user);
            }
        });
        Button registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener((View v) -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // 读取token 设置basehttp
        SharedPreferences pre = getSharedPreferences("user_message", MODE_PRIVATE);
        String token = pre.getString("token", "");
        String username = pre.getString("username", "");
        String password = pre.getString("password", "");
        if (token != null && !token.equals("")) {
            BaseHttpService.setToken(token);
            // 如果能获取到当前登陆旅客 则跳过登陆界面
            visitorService.getCurrentVisitor((response) -> {
                // 设置当前登陆用户
                Visitor visitor = (Visitor) response.getData();
                UserService.loginUser = visitor.getUser();
                // 登陆成功 直接进入主页面
                if (response.getResponse().code() >= 200 && response.getResponse().code() < 300) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }
        TextView userTextView = findViewById(R.id.userNameText);
        TextView passwordTextView = findViewById(R.id.passwordText);
        userTextView.setText(username);
        passwordTextView.setText(password);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//设置透明导航栏
        }
    }
}
