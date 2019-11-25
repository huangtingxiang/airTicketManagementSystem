package com.jdxiang.airTicket.flightManagement;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jdxiang.airTicket.MainActivity;
import com.jdxiang.airTicket.R;
import com.jdxiang.airTicket.entity.FlightManagement;
import com.jdxiang.airTicket.entity.TicketPrice;
import com.jdxiang.airTicket.entity.Visitor;
import com.jdxiang.airTicket.httpService.BaseHttpService;
import com.jdxiang.airTicket.httpService.TicketOrderService;
import com.jdxiang.airTicket.httpService.VisitorService;
import com.jdxiang.airTicket.login.RegisterActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class FlightPayforActivity extends AppCompatActivity {
    Long flightId;
    Long ticketPriceId;

    TicketOrderService ticketOrderService = TicketOrderService.getInstance();


    VisitorService visitorService = VisitorService.getInstance();

    Button flightReserveBtn;

    Calendar now;

    public static FlightManagement flightManagement = new FlightManagement();

    public static TicketPrice ticketPrice = new TicketPrice();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_payfor);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        flightReserveBtn = findViewById(R.id.flightReserveBtn);

        // 获取订单信息
        Intent intent = getIntent();
        flightId = intent.getLongExtra("flightId", 0);
        ticketPriceId = intent.getLongExtra("ticketPriceId", 0);


        // 发起预定航班请求
        // 展示loading  请求返回后关闭
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("请求中......");
        pDialog.setCancelable(false);
        pDialog.show();
        ticketOrderService.subscribeOrder(flightId, ticketPriceId, (response) -> {
            pDialog.cancel();
            if (!BaseHttpService.assertSuccessResponse(response.getResponse())) {
                new SweetAlertDialog(FlightPayforActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("预定失败!")
                        .setConfirmText("确定").show();
            } else {
                // 设置倒计时
                now = Calendar.getInstance();
                now.set(Calendar.MINUTE, 5);
                now.set(Calendar.SECOND, 0);
                new SweetAlertDialog(FlightPayforActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("预定成功!")
                        .setConfirmText("确定").show();
                int totalTime = 300;
                new CountDownTimer(1000 * 60 * 5, 1000) {
                    public void onTick(long millisUntilFinished) {
                        String time = new SimpleDateFormat("mm:ss", Locale.CHINA).format(now.getTime());
                        flightReserveBtn.setText("去支付: " + time);
                        now.add(Calendar.SECOND, -1);
                    }

                    public void onFinish() {
                        // 倒计时结束 无法支付订单
                        flightReserveBtn.setEnabled(false);
                    }
                }.start();
            }
        });

        // 获取当前登陆旅客信息
        visitorService.getCurrentVisitor((response) -> {
            Visitor visitor = (Visitor) response.getData();
            ((TextView) findViewById(R.id.personNameTextView)).setText(visitor.getName());
            ((TextView) findViewById(R.id.idCardTextView)).setText(visitor.getIdCard());
            ((TextView) findViewById(R.id.phoneNumberTextView)).setText(visitor.getPhoneNumber());
        });

        // 设置航班信息
        ((TextView) findViewById(R.id.companyNameTextView)).setText(flightManagement.getPlane().getAirlineCompany().getName());
        ((TextView) findViewById(R.id.startTimeTextView)).setText(new SimpleDateFormat("HH:mm", Locale.CHINA).format(flightManagement.getStartTime()));
        ((TextView) findViewById(R.id.arrivalTimeTextView)).setText(new SimpleDateFormat("HH:mm", Locale.CHINA).format(flightManagement.getArrivalTime()));
        ((TextView) findViewById(R.id.startAirPortTextView)).setText(flightManagement.getStartingAirPort().getName());
        ((TextView) findViewById(R.id.arrivalAirPortTextView)).setText(flightManagement.getDestinationAirPort().getName());
        ((TextView) findViewById(R.id.planeNameTextView)).setText(flightManagement.getPlane().getName());
        ((TextView) findViewById(R.id.shipSpaceNameTextView)).setText(ticketPrice.getShipSpace().getDescribed());
        ((TextView) findViewById(R.id.shipSpacePriceTextView)).setText(ticketPrice.getPrice().toString());


        TextView textView = findViewById(R.id.flight_title_text);
        textView.setText("支付订单");
        textView.setTextColor(getResources().getColor(R.color.white));

        LinearLayout linearLayout = findViewById(R.id.flightManagementTitleContainer);
        linearLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }
}
