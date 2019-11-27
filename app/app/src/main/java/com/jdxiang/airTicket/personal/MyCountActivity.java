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
import com.jdxiang.airTicket.entity.Visitor;
import com.jdxiang.airTicket.flightManagement.FlightPayforActivity;
import com.jdxiang.airTicket.httpService.BaseHttpService;
import com.jdxiang.airTicket.httpService.UserService;
import com.jdxiang.airTicket.httpService.VisitorService;
import com.jdxiang.airTicket.login.LoginActivity;
import com.jdxiang.airTicket.ui.SimpleActivity;
import com.jdxiang.airTicket.ui.notifications.NotificationsFragment;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MyCountActivity extends AppCompatActivity {

    TextView nameTextView; // 姓名

    TextView phoneTextView; // 手机号

    TextView userNameTextView; // 用户名

    TextView idCardTextView; // 身份证

    VisitorService visitorService = VisitorService.getInstance();
    UserService userService = new UserService();

    Visitor visitor;

    private static final int NameCode = 0;
    private static final int PhoneNumber = 1;
    private static final int IdCard = 2;
    private static final int UserName = 3;
    private static final int PassWord = 4;


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

        // 获取当前登陆用户 设置信息
        nameTextView = findViewById(R.id.nameTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        userNameTextView = findViewById(R.id.userNameTextView);
        idCardTextView = findViewById(R.id.idCardTextView);

        visitorService.getCurrentVisitor((result -> {
            visitor = (Visitor) result.getData();
            nameTextView.setText(visitor.getName());
            phoneTextView.setText(visitor.getPhoneNumber());
            userNameTextView.setText(visitor.getUser().getUserName());
            idCardTextView.setText(visitor.getIdCard());
        }));

        // 修改姓名
        findViewById(R.id.nameContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建简单活动 用于接收充值金额
                Intent intent = new Intent(MyCountActivity.this, SimpleActivity.class);
                intent.putExtra(SimpleActivity.TITLE, "修改姓名");
                intent.putExtra(SimpleActivity.HINT, "请输入姓名");
                startActivityForResult(intent, NameCode);
            }
        });

        // 修改手机号
        findViewById(R.id.phoneContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建简单活动 用于接收充值金额
                Intent intent = new Intent(MyCountActivity.this, SimpleActivity.class);
                intent.putExtra(SimpleActivity.TITLE, "修改手机号");
                intent.putExtra(SimpleActivity.HINT, "请输入手机号码");
                startActivityForResult(intent, PhoneNumber);
            }
        });

        // 修改身份证
        findViewById(R.id.idCardContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建简单活动 用于接收充值金额
                Intent intent = new Intent(MyCountActivity.this, SimpleActivity.class);
                intent.putExtra(SimpleActivity.TITLE, "修改身份证号码");
                intent.putExtra(SimpleActivity.HINT, "请输入身份证号码");
                startActivityForResult(intent, IdCard);
            }
        });

        // 修改邮箱
        findViewById(R.id.userNameContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建简单活动 用于接收充值金额
                Intent intent = new Intent(MyCountActivity.this, SimpleActivity.class);
                intent.putExtra(SimpleActivity.TITLE, "修改邮箱");
                intent.putExtra(SimpleActivity.HINT, "请输入邮箱");
                startActivityForResult(intent, UserName);
            }
        });
        // 修改邮箱
        findViewById(R.id.passwordContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建简单活动 用于接收充值金额
                Intent intent = new Intent(MyCountActivity.this, SimpleActivity.class);
                intent.putExtra(SimpleActivity.TITLE, "修改密码");
                intent.putExtra(SimpleActivity.HINT, "请输入密码");
                startActivityForResult(intent, PassWord);
            }
        });


        Button button = (Button) findViewById(R.id.logout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 退出登陆 修改basehttp token信息 并修改数据库中token
                logout();
                // 返回登陆界面
                Intent intentToLogin = new Intent(MyCountActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentToLogin);
            }
        });
    }

    /**
     * 重写方法 当子活动结束时调用 充值
     *
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case NameCode:
                if (resultCode == RESULT_OK) {
                    // 修改姓名
                    String name = intent.getStringExtra(SimpleActivity.FILED);
                    visitorService.changeName(visitor.getId(), name, (response) -> {
                        if (BaseHttpService.assertSuccessResponse(response.getResponse())) {
                            nameTextView.setText(name);
                        }
                    });
                }
                break;
            case PhoneNumber:
                if (resultCode == RESULT_OK) {
                    // 修改姓名
                    String phoneNumber = intent.getStringExtra(SimpleActivity.FILED);
                    visitorService.changePhoneNumber(visitor.getId(), phoneNumber, (response) -> {
                        if (BaseHttpService.assertSuccessResponse(response.getResponse())) {
                            phoneTextView.setText(phoneNumber);
                        }
                    });
                }
                break;
            case IdCard:
                if (resultCode == RESULT_OK) {
                    // 修改姓名
                    String idCard = intent.getStringExtra(SimpleActivity.FILED);
                    visitorService.changePhoneNumber(visitor.getId(), idCard, (response) -> {
                        if (BaseHttpService.assertSuccessResponse(response.getResponse())) {
                            idCardTextView.setText(idCard);
                        }
                    });
                }
                break;
            case UserName:
                if (resultCode == RESULT_OK) {
                    // 修改姓名
                    String userName = intent.getStringExtra(SimpleActivity.FILED);
                    userService.resetUserName(visitor.getUser().getId(), userName, (response) -> {
                        if (BaseHttpService.assertSuccessResponse(response.getResponse())) {
                            logoutAndToLogin();
                        }
                    });
                }
                break;
            case PassWord:
                if (resultCode == RESULT_OK) {
                    // 修改姓名
                    String password = intent.getStringExtra(SimpleActivity.FILED);
                    userService.resetPassWord(visitor.getUser().getId(), password, (response) -> {
                        logoutAndToLogin();
                    });
                }
                break;
            default:
                break;
        }
    }

    private void logout() {
        // 退出登陆 修改basehttp token信息 并修改数据库中token
        BaseHttpService.setToken("");
        SharedPreferences.Editor edit = MyCountActivity.this.getSharedPreferences("user_message", MODE_PRIVATE).edit();
        edit.putString("token", "");
        edit.apply();
    }

    private void logoutAndToLogin() {
        // 退出登陆 修改basehttp token信息 并修改数据库中token
        logout();
        // 返回登陆界面
        new SweetAlertDialog(MyCountActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("请重新登陆!")
                .setConfirmText("确定").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                Intent intentToLogin = new Intent(MyCountActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentToLogin);
            }
        }).show();
    }
}
