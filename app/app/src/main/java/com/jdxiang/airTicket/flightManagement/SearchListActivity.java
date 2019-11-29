package com.jdxiang.airTicket.flightManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jdxiang.airTicket.R;
import com.jdxiang.airTicket.adapter.FlightListAdapter;
import com.jdxiang.airTicket.entity.FlightManagement;
import com.jdxiang.airTicket.entity.Page;
import com.jdxiang.airTicket.entity.ShipSpace;
import com.jdxiang.airTicket.entity.TicketPrice;
import com.jdxiang.airTicket.httpService.BaseHttpService;
import com.jdxiang.airTicket.httpService.DownloadImageTask;
import com.jdxiang.airTicket.httpService.FlightManagementService;
import com.jdxiang.airTicket.ui.home.FlightOneWayTripFragment;
import com.jdxiang.airTicket.ui.home.HomeFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class SearchListActivity extends AppCompatActivity {

    FlightManagementService flightManagementService = FlightManagementService.getInstance();

    RecyclerView listView;

    String startName = "";
    String destinationName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // 从intent 中获取数据
        Intent intent = getIntent();
        Long startPlaceId = intent.getLongExtra("startPlaceId", 0);
        Long destinationId = intent.getLongExtra("destinationId", 0);
        Long startTime = intent.getLongExtra("startTime", 0);
        startName = intent.getStringExtra("startPlaceName");
        destinationName = intent.getStringExtra("destinationName");
        TextView textView = findViewById(R.id.flight_title_text);
        textView.setText(startName + "到" + destinationName);

        // 请求后台
        flightManagementService.searchFlight(startPlaceId, destinationId, startTime, (response) -> {
            FlightManagement[] flightManagements = (FlightManagement[]) response.getData();
            // 从开始时间进行排序
            // 设置搜索航班列表
            listView = findViewById(R.id.flight_search_list);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            listView.setLayoutManager(linearLayoutManager);
            listView.setAdapter(new FlightListAdapter(flightManagements, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = listView.getChildAdapterPosition(v);
                    FlightDetailActivity.flightManagement = flightManagements[position];
                    Intent intent = new Intent(SearchListActivity.this, FlightDetailActivity.class);
                    intent.putExtra("startPlaceName", startName);
                    intent.putExtra("destinationName", destinationName);
                    startActivity(intent);
                }
            }));
        });

    }
}
