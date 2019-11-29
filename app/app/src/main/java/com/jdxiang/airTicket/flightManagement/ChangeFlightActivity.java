package com.jdxiang.airTicket.flightManagement;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jdxiang.airTicket.R;
import com.jdxiang.airTicket.adapter.FlightListAdapter;
import com.jdxiang.airTicket.entity.FlightManagement;
import com.jdxiang.airTicket.httpService.FlightManagementService;
import com.jdxiang.airTicket.ui.home.FlightOneWayTripFragment;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Locale;

public class ChangeFlightActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView startTextLabel;
    Calendar searchDate; // 搜索时间
    String startName = "";
    String destinationName = "";
    FlightManagementService flightManagementService = FlightManagementService.getInstance();
    Long startPlaceId;
    Long destinationId;
    RecyclerView listView;
    Long orderId;
    Double originPrice;
    FlightListAdapter flightListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_flight);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // 从intent 中获取数据
        Intent intent = getIntent();
        startPlaceId = intent.getLongExtra("startPlaceId", 0);
        destinationId = intent.getLongExtra("destinationId", 0);
        Long startTime = intent.getLongExtra("startTime", 0);
        orderId = intent.getLongExtra("orderId", 0);
        startName = intent.getStringExtra("startPlaceName");
        destinationName = intent.getStringExtra("destinationName");
        originPrice = intent.getDoubleExtra("originPrice", 0);
        ((TextView) findViewById(R.id.flight_title_text)).setText("改签");
        TextView textView = findViewById(R.id.startAndArrivePlace);
        textView.setText(startName + "到" + destinationName);

        //  点击时间选择时
        startTextLabel = findViewById(R.id.startDate);
        searchDate = Calendar.getInstance();
        searchDate.setTimeInMillis(startTime);
        setDateLabel(searchDate.get(Calendar.YEAR), searchDate.get(Calendar.MONTH), searchDate.get(Calendar.DAY_OF_MONTH));
        findViewById(R.id.dateContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        ChangeFlightActivity.this,
                        searchDate.get(Calendar.YEAR), // Initial year selection
                        searchDate.get(Calendar.MONTH), // Initial month selection
                        searchDate.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );
                dpd.show(getSupportFragmentManager(), "出发日期");
                dpd.setLocale(Locale.CHINA);
                dpd.setAccentColor(getResources().getColor(R.color.mdtp_accent_color));
                dpd.setOkText("确定");
                dpd.setCancelText("取消");
            }
        });

        // 从开始时间进行排序
        // 设置搜索航班列表
        listView = findViewById(R.id.flight_search_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(linearLayoutManager);
        flightListAdapter = new FlightListAdapter(new FlightManagement[0], new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = listView.getChildAdapterPosition(v);
                ChangeFlightDetailActivity.flightManagement = flightListAdapter.getByPosition(position);
                Intent intent = new Intent(ChangeFlightActivity.this, ChangeFlightDetailActivity.class);
                intent.putExtra("startPlaceName", startName);
                intent.putExtra("destinationName", destinationName);
                intent.putExtra("orderId", orderId);
                intent.putExtra("originPrice", originPrice);
                startActivity(intent);
            }
        });
        listView.setAdapter(flightListAdapter);
        reloadFlight();
    }

    private void setDateLabel(int year, int month, int day) {
        searchDate.set(Calendar.YEAR, year);
        searchDate.set(Calendar.MONTH, month);
        searchDate.set(Calendar.DAY_OF_MONTH, day);
        startTextLabel.setText(year + "年" + (month + 1) + "月" + day + "日");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        setDateLabel(year, monthOfYear, dayOfMonth);
        reloadFlight();
    }

    private void reloadFlight() {
        // 请求后台
        flightManagementService.searchFlight(startPlaceId, destinationId, searchDate.getTime().getTime(), (response) -> {
            FlightManagement[] flightManagements = (FlightManagement[]) response.getData();
            // 从开始时间进行排序
            // 设置搜索航班列表
            flightListAdapter.setFlightManagements(flightManagements);
            flightListAdapter.notifyDataSetChanged();
        });
    }
}
