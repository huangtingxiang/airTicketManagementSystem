package com.jdxiang.airTicket.personal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jdxiang.airTicket.MainActivity;
import com.jdxiang.airTicket.R;
import com.jdxiang.airTicket.entity.Visitor;
import com.jdxiang.airTicket.flightManagement.FlightPayforActivity;
import com.jdxiang.airTicket.httpService.BaseHttpService;
import com.jdxiang.airTicket.httpService.CommonService;
import com.jdxiang.airTicket.httpService.DownloadImageTask;
import com.jdxiang.airTicket.httpService.UserService;
import com.jdxiang.airTicket.httpService.VisitorService;
import com.jdxiang.airTicket.login.LoginActivity;
import com.jdxiang.airTicket.ui.SimpleActivity;
import com.jdxiang.airTicket.ui.notifications.NotificationsFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MyCountActivity extends AppCompatActivity {

    TextView nameTextView; // 姓名

    TextView phoneTextView; // 手机号

    TextView userNameTextView; // 用户名

    TextView idCardTextView; // 身份证

    CircleImageView circleImageView; // 头像

    CommonService commonService = CommonService.getInstance();

    VisitorService visitorService = VisitorService.getInstance();
    UserService userService = new UserService();

    Visitor visitor;

    private static final int NameCode = 0;
    private static final int PhoneNumber = 1;
    private static final int IdCard = 2;
    private static final int UserName = 3;
    private static final int PassWord = 4;
    private static final int ImageCode = 5;
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 0;


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
        circleImageView = findViewById(R.id.personImage);

        visitorService.getCurrentVisitor((result -> {
            visitor = (Visitor) result.getData();
            nameTextView.setText(visitor.getName());
            phoneTextView.setText(visitor.getPhoneNumber());
            userNameTextView.setText(visitor.getUser().getUserName());
            idCardTextView.setText(visitor.getIdCard());
            if (visitor.getImageUrl() != null && !visitor.getImageUrl().equals("")) {
                String urlString = BaseHttpService.BASE_HOST + visitor.getImageUrl();
                new DownloadImageTask(circleImageView)
                        .execute(urlString);
            }
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

        // 修改头像
        findViewById(R.id.visitorImageContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 判断是否有相册权限
                if (ContextCompat.checkSelfPermission(MyCountActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MyCountActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_CODE);
                } else {
                    openCamera();
                }

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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(MyCountActivity.this, "请允许打开相册权限", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void openCamera() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, ImageCode);
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
            case ImageCode:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = intent.getData();

                    String filePath = getPath(selectedImage);
                    String file_extn = filePath.substring(filePath.lastIndexOf(".") + 1);
                    if (file_extn.equals("img") || file_extn.equals("jpg") || file_extn.equals("jpeg") || file_extn.equals("gif") || file_extn.equals("png")) {
                        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                        if (filePath != null) {
                            File file = new File(filePath);
                            MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");
                            RequestBody req = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file))
                                    .build();
                            visitorService.upload(req, (response) -> {
                                String urlString = BaseHttpService.BASE_HOST + response.getData();
                                new DownloadImageTask(circleImageView)
                                        .execute(urlString);
                                System.out.println(urlString);
                            });


                        }
                    }
                }
                break;
            default:
                break;
        }
    }


    public String getPath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        String imagePath = cursor.getString(column_index);

        return imagePath;
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
