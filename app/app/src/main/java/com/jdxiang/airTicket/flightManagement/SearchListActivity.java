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
import android.widget.Button;
import android.widget.TextView;

import com.jdxiang.airTicket.R;
import com.jdxiang.airTicket.ui.home.HomeFragment;

import java.util.ArrayList;

public class SearchListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        TextView textView = findViewById(R.id.flight_title_text);
        textView.setText("天津到上海");
        // 设置搜索航班列表
        RecyclerView listView = findViewById(R.id.flight_search_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(linearLayoutManager);
        listView.setAdapter(new FlightSearchListAdapter());
    }

    class FlightSearchListAdapter extends RecyclerView.Adapter<SearchListActivity.FlightSearchListAdapter.FlightSearchListHolder> {

        @NonNull
        @Override
        public SearchListActivity.FlightSearchListAdapter.FlightSearchListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_management_search_list_item, parent, false);
            return new SearchListActivity.FlightSearchListAdapter.FlightSearchListHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchListActivity.FlightSearchListAdapter.FlightSearchListHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }

        class FlightSearchListHolder extends RecyclerView.ViewHolder {

            public FlightSearchListHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}
