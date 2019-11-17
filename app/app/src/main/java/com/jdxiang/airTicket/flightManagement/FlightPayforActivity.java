package com.jdxiang.airTicket.flightManagement;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jdxiang.airTicket.R;

public class FlightPayforActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_payfor);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }


        TextView textView = findViewById(R.id.flight_title_text);
        textView.setText("支付订单");
        textView.setTextColor(getResources().getColor(R.color.white));

        LinearLayout linearLayout = findViewById(R.id.flightManagementTitleContainer);
        linearLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }
}
