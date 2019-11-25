package com.jdxiang.airTicket.flightManagement;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jdxiang.airTicket.R;
import com.jdxiang.airTicket.entity.FlightManagement;
import com.jdxiang.airTicket.entity.TicketPrice;
import com.jdxiang.airTicket.entity.Visitor;
import com.jdxiang.airTicket.httpService.VisitorService;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class FlightReserveActivity extends AppCompatActivity {

    Long flightId;
    Long ticketPriceId;

    public static TicketPrice ticketPrice = new TicketPrice();
    public static FlightManagement flightManagement = new FlightManagement();

    VisitorService visitorService = VisitorService.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_reserve);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // 设置标题栏
        ((TextView) findViewById(R.id.flight_title_text)).setText("预定航班");

        // 获取预定的航班和订单价钱
        Intent intent = getIntent();
        flightId = intent.getLongExtra("flightId", 0);
        ticketPriceId = intent.getLongExtra("ticketPriceId", 0);
        ((TextView) findViewById(R.id.shipSpaceNameTextView)).setText(ticketPrice.getShipSpace().getDescribed());
        ((TextView) findViewById(R.id.shipSpacePriceTextView)).setText(ticketPrice.getPrice().toString());
        String startHour = new SimpleDateFormat("HH:mm", Locale.CHINA).format(flightManagement.getStartTime());
        String startYear = new SimpleDateFormat("MM-dd", Locale.CHINA).format(flightManagement.getStartTime());
        ((TextView) findViewById(R.id.startDateTextView)).setText(startHour + "   " + startYear);


        // 获取当前登陆旅客信息
        visitorService.getCurrentVisitor((response) -> {
            Visitor visitor = (Visitor) response.getData();
            ((TextView) findViewById(R.id.personNameTextView)).setText(visitor.getName());
            ((TextView) findViewById(R.id.idCardTextView)).setText(visitor.getIdCard());
            ((TextView) findViewById(R.id.phoneNumberTextView)).setText(visitor.getPhoneNumber());
        });

        // 预定按钮
        Button button = findViewById(R.id.flightReserveBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlightPayforActivity.flightManagement = flightManagement;
                FlightPayforActivity.ticketPrice = ticketPrice;
                Intent intent = new Intent(FlightReserveActivity.this, FlightPayforActivity.class);
                intent.putExtra("flightId", flightId);
                intent.putExtra("ticketPriceId", ticketPriceId);
                startActivity(intent);
            }
        });
    }
}
