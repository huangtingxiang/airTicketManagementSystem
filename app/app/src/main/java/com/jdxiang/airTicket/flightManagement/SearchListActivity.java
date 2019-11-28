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
            listView.setAdapter(new FlightSearchListAdapter(flightManagements));
        });

    }

    class FlightSearchListAdapter extends RecyclerView.Adapter<SearchListActivity.FlightSearchListAdapter.FlightSearchListHolder> {

        FlightManagement[] flightManagements;

        FlightSearchListAdapter(FlightManagement[] flightManagements) {
            this.flightManagements = flightManagements;
        }

        @NonNull
        @Override
        public SearchListActivity.FlightSearchListAdapter.FlightSearchListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_management_search_list_item, parent, false);
            // 点击搜索的航班列表时显示航班详情
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = listView.getChildAdapterPosition(v);
                    FlightDetailActivity.flightManagement = flightManagements[position];
                    Intent intent = new Intent(SearchListActivity.this, FlightDetailActivity.class);
                    intent.putExtra("startPlaceName", startName);
                    intent.putExtra("destinationName", destinationName);
                    startActivity(intent);
                }
            });
            return new SearchListActivity.FlightSearchListAdapter.FlightSearchListHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchListActivity.FlightSearchListAdapter.FlightSearchListHolder holder, int position) {
            FlightManagement flightManagement = flightManagements[position];
            holder.startAirPort.setText(flightManagement.getStartingAirPort().getName());
            holder.startTextView.setText(new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(flightManagement.getStartTime()));
            holder.endAirPort.setText(flightManagement.getDestinationAirPort().getName());
            holder.endTextView.setText(new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(flightManagement.getArrivalTime()));
            holder.planeNameTextView.setText(flightManagement.getPlane().getName());
            String urlString = BaseHttpService.BASE_HOST + flightManagement.getPlane().getAirlineCompany().getIcon();
            new DownloadImageTask(holder.companyImage)
                    .execute(urlString);
            // 查找主舱位
            TicketPrice primaryTicketPrice = null;
            for (TicketPrice ticketPrice :
                    flightManagement.getTicketPrices()) {
                if (ticketPrice.getShipSpace().getPrimaried()) {
                    primaryTicketPrice = ticketPrice;
                    break;
                }
            }
            if (primaryTicketPrice != null) {
                holder.ticketShipSpaceTextView.setText(primaryTicketPrice.getShipSpace().getDescribed());
                holder.ticketPriceTextView.setText(primaryTicketPrice.getPrice().toString());
            }
        }

        @Override
        public int getItemCount() {
            return this.flightManagements.length;
        }

        class FlightSearchListHolder extends RecyclerView.ViewHolder {

            TextView startTextView;

            TextView endTextView;

            TextView startAirPort;

            TextView endAirPort;

            TextView planeNameTextView;

            TextView ticketShipSpaceTextView;

            TextView ticketPriceTextView;

            ImageView companyImage;

            public FlightSearchListHolder(@NonNull View itemView) {
                super(itemView);
                startTextView = itemView.findViewById(R.id.startTime);
                endTextView = itemView.findViewById(R.id.endTime);
                startAirPort = itemView.findViewById(R.id.startAirPort);
                endAirPort = itemView.findViewById(R.id.endAirPort);
                planeNameTextView = itemView.findViewById(R.id.planeNameTextView);
                ticketShipSpaceTextView = itemView.findViewById(R.id.ticketShipSpaceTextView);
                ticketPriceTextView = itemView.findViewById(R.id.ticketPriceTextView);
                companyImage = itemView.findViewById(R.id.companyImage);
            }
        }
    }
}
