package com.jdxiang.airTicket.personal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jdxiang.airTicket.R;
import com.jdxiang.airTicket.entity.Visitor;
import com.jdxiang.airTicket.httpService.BaseHttpService;
import com.jdxiang.airTicket.httpService.VisitorService;
import com.jdxiang.airTicket.login.RegisterActivity;
import com.jdxiang.airTicket.ui.SimpleActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MyWalletActivity extends AppCompatActivity {

    VisitorService visitorService = VisitorService.getInstance();

    Visitor visitor;

    TextView balanceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        balanceTextView = findViewById(R.id.balanceTextView);

        // 设置余额
        visitorService.getCurrentVisitor((result -> {
            visitor = (Visitor) result.getData();
            setBalanceText();
        }));

        // 发送充值请求
        findViewById(R.id.rechargeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建简单活动 用于接收充值金额
                Intent intent = new Intent(MyWalletActivity.this, SimpleActivity.class);
                intent.putExtra(SimpleActivity.TITLE, "充值");
                intent.putExtra(SimpleActivity.HINT, "请输入充值金额");
                startActivityForResult(intent, SimpleActivity.SimpleCode);
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
            case SimpleActivity.SimpleCode:
                if (resultCode == RESULT_OK) {
                    String priceString = intent.getStringExtra(SimpleActivity.FILED);
                    Double price = Double.valueOf(priceString);
                    visitorService.recharge(Double.valueOf(price), (response) -> {
                        if (BaseHttpService.assertSuccessResponse(response.getResponse())) {
                            visitor.setBalance(visitor.getBalance() + price);
                            setBalanceText();
                            new SweetAlertDialog(MyWalletActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("充值成功!")
                                    .setConfirmText("确定").show();
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

    private void setBalanceText() {
        balanceTextView.setText("¥" + visitor.getBalance());
    }
}
