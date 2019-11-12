package com.jdxiang.airTicket.flightManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jdxiang.airTicket.R;
import com.jdxiang.airTicket.ui.home.HomeFragment;

import java.util.ArrayList;

public class FlightDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_detail);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // 设置推荐航班
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

        }

        @Override
        public int getItemCount() {
            return 5;
        }

        class SpaceListHolder extends RecyclerView.ViewHolder {

            public SpaceListHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}
