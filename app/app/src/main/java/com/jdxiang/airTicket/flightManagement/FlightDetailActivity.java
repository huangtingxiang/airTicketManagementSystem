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
import android.widget.TextView;

import com.jdxiang.airTicket.R;
import com.jdxiang.airTicket.entity.FlightManagement;
import com.jdxiang.airTicket.entity.TicketPrice;
import com.jdxiang.airTicket.ui.home.HomeFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class FlightDetailActivity extends AppCompatActivity {
    String startName;
    String destinationName;

    public static FlightManagement flightManagement = new FlightManagement();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_detail);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        // 获取标题
        Intent intent = getIntent();
        startName = intent.getStringExtra("startPlaceName");
        destinationName = intent.getStringExtra("destinationName");
        TextView titleTextView = findViewById(R.id.flight_title_text);
        titleTextView.setText(startName + "到" + destinationName);

        // 设置航班信息
        ((TextView) findViewById(R.id.companyNameTextView)).setText(flightManagement.getPlane().getAirlineCompany().getName());
        ((TextView) findViewById(R.id.startTimeTextView)).setText(new SimpleDateFormat("HH:mm", Locale.CHINA).format(flightManagement.getStartTime()));
        ((TextView) findViewById(R.id.arrivalTimeTextView)).setText(new SimpleDateFormat("HH:mm", Locale.CHINA).format(flightManagement.getArrivalTime()));
        ((TextView) findViewById(R.id.startAirPortTextView)).setText(flightManagement.getStartingAirPort().getName());
        ((TextView) findViewById(R.id.arrivalAirPortTextView)).setText(flightManagement.getDestinationAirPort().getName());
        ((TextView) findViewById(R.id.planeNameTextView)).setText(flightManagement.getPlane().getName());



        // 设置舱位列表
        RecyclerView listView = findViewById(R.id.spaceList);
        listView.setNestedScrollingEnabled(false);
        listView.setFocusable(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(linearLayoutManager);
        listView.setAdapter(new SpaceListAdapter());

    }

    class SpaceListAdapter extends RecyclerView.Adapter<SpaceListAdapter.SpaceListHolder> {

        @NonNull
        @Override
        public SpaceListAdapter.SpaceListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_management_space_item, parent, false);
            return new SpaceListAdapter.SpaceListHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SpaceListAdapter.SpaceListHolder holder, int position) {
            // 设置信息
            TicketPrice ticketPrice = flightManagement.getTicketPrices().get(position);
            holder.price.setText(ticketPrice.getPrice().toString());
            holder.name.setText(ticketPrice.getShipSpace().getDescribed());
            // 点击预定按钮时进入预定界面
            holder.view.findViewById(R.id.flightReserveBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FlightReserveActivity.flightManagement = flightManagement;
                    FlightReserveActivity.ticketPrice = flightManagement.getTicketPrices().get(position);
                    Intent intent = new Intent(FlightDetailActivity.this, FlightReserveActivity.class);
                    intent.putExtra("flightId", flightManagement.getId());
                    intent.putExtra("ticketPriceId", flightManagement.getTicketPrices().get(position).getId());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return flightManagement.getTicketPrices().size();
        }

        class SpaceListHolder extends RecyclerView.ViewHolder {

            TextView price;
            TextView name;

            View view;

            public SpaceListHolder(@NonNull View itemView) {
                super(itemView);
                view = itemView;
                price = itemView.findViewById(R.id.shipSpacePriceTextView);
                name = itemView.findViewById(R.id.shipSpaceNameTextView);
            }
        }
    }
}
